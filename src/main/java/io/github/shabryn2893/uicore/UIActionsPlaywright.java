package io.github.shabryn2893.uicore;

import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.SelectOption;

import io.github.shabryn2893.locatorfactory.ElementLocator;
import io.github.shabryn2893.locatorfactory.LocatorFactory;
import io.github.shabryn2893.utils.LoggerUtils;

public class UIActionsPlaywright implements IActionUI {
	private static final Logger logger = LoggerUtils.getLogger(UIActionsPlaywright.class);
	private Browser browser;
	private BrowserContext context;
	private Page page;
	private Locator element;

	public UIActionsPlaywright(Browser browser) {
		this.browser = browser;
		this.context = browser.newContext();
		this.page = context.newPage();
	}

	private Locator findElement(String locatorType, String locatorValue) {
		ElementLocator<Locator> locator = LocatorFactory.getLocator(page);
		return locator.locateElement(locatorType, locatorValue);
	}

	private List<Locator> findElements(String locatorType, String locatorValue) {
		ElementLocator<Locator> locator = LocatorFactory.getLocator(page);
		return locator.locateElements(locatorType, locatorValue);
	}

	@Override
	public void maximizeScreen() {
		this.executeJSAction("window.moveTo(0, 0); window.resizeTo(screen.width, screen.height);");
	}

	@Override
	public void deleteAllCookies() {
		this.context.clearCookies();
	}

	@Override
	public void closeCurrentTabWindow() {
		this.page.close();

	}

	@Override
	public void closeBrowser() {
		this.browser.close();

	}

	@Override
	public void openURL(String url) {
		this.page.navigate(url);

	}

	@Override
	public void click(String locatorType, String locatorValue, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			this.element = findElement(locatorType, locatorValue);
			this.element.click();
		} else {
			logger.error("WebElement {} is not clickable.", locatorValue);
			assert false;
		}

	}

	@Override
	public void type(String locatorType, String locatorValue, String textToEnter, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			this.element = findElement(locatorType, locatorValue);
			this.element.fill(textToEnter);
		} else {
			logger.error("WebElement {} is not enabled.", locatorValue);
			assert false;
		}

	}

	@Override
	public void waitForPageLoad(int timeInSeconds) {
		this.page.waitForFunction(
				"() => window.performance.getEntriesByType('resource').every(entry => entry.initiatorType === 'img' || entry.initiatorType === 'script' || entry.initiatorType === 'link' || entry.initiatorType === 'fetch' || entry.initiatorType === 'xmlhttprequest') && document.readyState === 'complete'");
	}

	@Override
	public void waitForElement(int seconds) {
		this.page.waitForTimeout(seconds);

	}

	@Override
	public boolean isElementDisplayedOrEnabledOrSelected(String locatorType, String locatorValue, String stateType) {
		this.element = findElement(locatorType, locatorValue);
		boolean status = false;
		switch (stateType.toUpperCase()) {
		case "DISPLAYED": {
			status = this.element.isVisible();
			break;
		}
		case "ENABLED": {
			status = this.element.isEnabled();
			break;
		}
		case "SELECTED": {
			status = this.element.isChecked();
			break;
		}
		default:
			logger.error("Unsupported state Type:{} ", stateType);
			assert false;
		}
		return status;
	}

	@Override
	public String getAttributeValue(String locatorType, String locatorValue, String attributeName, int maxWaitTime) {
		String attributeValue = null;
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			this.element = findElement(locatorType, locatorValue);
			attributeValue = this.element.getAttribute(attributeName);
		} else {
			logger.info("Unable to find attribute value as Web Element is not present in the DOM");
			assert false;
		}
		return attributeValue;
	}

	@Override
	public String getURL() {
		return this.page.url();
	}

	@Override
	public String takeScreenshot(String screenshotPath) {
		byte[] buffer = this.page
				.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
		return Base64.getEncoder().encodeToString(buffer);

	}

	@Override
	public String takeScreenshot(String locatorType, String locatorValue, String screenshotPath) {
		this.element = findElement(locatorType, locatorValue);
		byte[] buffer = this.element.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
		return Base64.getEncoder().encodeToString(buffer);
	}

	@Override
	public void jsClick(String locatorType, String locatorValue, int maxWaitTime) {
		String jsSelector;
		switch (locatorType.toUpperCase()) {
		case "CSS":
			jsSelector = "document.querySelector('" + locatorValue + "')";
			break;
		case "XPATH":
			jsSelector = "document.evaluate(\"" + locatorValue
					+ "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue";
			break;
		default:
			throw new IllegalArgumentException("Unsupported locator type: " + locatorType);
		}
		
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			this.executeJSAction(jsSelector + ".click()");
		} else {
			logger.info("Unable to perform JSClick: Web Element is not present");
			assert false;
		}	
	}

	@Override
	public String getText(String locatorType, String locatorValue, int maxWaitTime) {
		String textValue = null;
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			this.element = findElement(locatorType, locatorValue);
			textValue = this.element.textContent().trim();
		} else {
			logger.info("Unable to get Text: Web Element is not present");
			assert false;
		}
		return textValue;
	}

	@Override
	public void scrollToElement(String locatorType, String locatorValue, String scrollType, int maxWaitTime) {

		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			this.element = findElement(locatorType, locatorValue);
			if (scrollType.equalsIgnoreCase("NORMAL")) {
				this.element.scrollIntoViewIfNeeded();
			} else {
				this.executeJSAction("window.scrollTo(0, document.body.scrollHeight);");
			}

		} else {
			logger.info("Unable to perform scroll: Web Element is not present");
			assert false;
		}

	}

	@Override
	public boolean isElementPresent(String locatorType, String locatorValue) {
		boolean status = false;
		if (!(this.findElements(locatorType, locatorValue).isEmpty())
				&& this.isElementDisplayedOrEnabledOrSelected(locatorType, locatorValue, "DISPLAYED")
				&& this.isElementDisplayedOrEnabledOrSelected(locatorType, locatorValue, "ENABLED")) {
			status = true;
		}
		return status;
	}

	@Override
	public boolean waitUntillElementAppear(String locatorType, String locatorValue, int maxWaitTime) {
		boolean status = true;
		long startTime;
		long endTime;
		startTime = System.currentTimeMillis();
		try {
			while (!(this.isElementPresent(locatorType, locatorValue))) {
				logger.info("Waiting for Element {} to be appear...", locatorValue);
				this.waitForElement(1);
				endTime = System.currentTimeMillis();
				if (endTime - startTime > maxWaitTime * 1000) {
					break;
				}
			}

		} catch (Exception e) {
			status = false;
			logger.error("Element: {} is not appear within the specified timeout", locatorValue);
			e.printStackTrace();
			assert false;
		}
		return status;
	}

	@Override
	public boolean waitUntillElementDisappear(String locatorType, String locatorValue, int maxWaitTime) {
		boolean status = true;
		long startTime;
		long endTime;
		startTime = System.currentTimeMillis();
		try {
			while ((this.isElementPresent(locatorType, locatorValue))) {
				logger.info("Waiting for Element {} to be disappear...", locatorValue);
				this.waitForElement(1);
				endTime = System.currentTimeMillis();
				if (endTime - startTime > maxWaitTime * 1000) {
					break;
				}
			}

		} catch (Exception e) {
			status = false;
			logger.error("Element: {} is not disappear within the specified timeout", locatorValue);
			e.printStackTrace();
			assert false;
		}
		return status;
	}

	@Override
	public void navigateTo(String direction) {
		switch (direction.toUpperCase()) {

		case "FORWARD":
			this.page.goForward();
			break;
		case "BACK":
			this.page.goBack();
			break;
		case "REFRESH":
			this.page.reload();
			break;
		default:
			logger.error("Unspported Direction: {}", direction);
			assert false;

		}

	}

	public void switchToNewWindowTabWhenClicked(String locatorType, String locatorValue, int maxWaitTime) {
		Page newPage = this.context.waitForPage(() -> click(locatorType, locatorValue, maxWaitTime));
		newPage.waitForLoadState();
		this.page = newPage;
	}

	@Override
	public void switchToOpenedTabWindow(int windowTabIndex) {
		List<Page> allPages = this.context.pages();
		this.page = allPages.get(windowTabIndex);
	}

	@Override
	public void createNewWindowTabSwitch(String type) {
		Page newPage = this.page.waitForPopup(() -> page.click("a[target='_blank']"));
		newPage.waitForLoadState();
		this.page = newPage;

	}

	@Override
	public void hoverElement(String locatorType, String locatorValue, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			this.element = this.findElement(locatorType, locatorValue);
			this.element.hover();
		} else {
			logger.error("Unable to hover WebElement {}", locatorValue);
			assert false;
		}

	}

	@Override
	public void rightClickElement(String locatorType, String locatorValue, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			this.element = this.findElement(locatorType, locatorValue);
			this.element.click(new Locator.ClickOptions().setButton(MouseButton.RIGHT));
		} else {
			logger.error("Unable to perfom right click on WebElement {}", locatorValue);
			assert false;
		}
	}

	@Override
	public void doubleClickElement(String locatorType, String locatorValue, int maxWaitTime) {

		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			this.element = this.findElement(locatorType, locatorValue);
			this.element.dblclick();
		} else {
			logger.error("Unable to perfom double click on WebElement {}", locatorValue);
			assert false;
		}

	}

	@Override
	public String getPageTitle() {
		return this.page.title();
	}

	@Override
	public void switchToParenTabWindowIframe() {
		this.page.mainFrame();
	}

	@Override
	public void dragAndDrop(String locatorType, String sourceLocatorValue, String targetLocatorValue) {
		Locator source = this.findElement(locatorType, sourceLocatorValue);
		Locator target = this.findElement(locatorType, sourceLocatorValue);
		source.dragTo(target);

	}

	@Override
	public void typeUsingKeyboard(String locatorType, String locatorValue, int maxWaitTime, String textToType) {
		this.click(locatorType, locatorValue, maxWaitTime);
		this.page.keyboard().type(textToType);

	}

	@Override
	public void pressKeyCombination(String combinationKeysName) {
		if (combinationKeysName.contains(",")) {
			String[] keys = combinationKeysName.split(",");
			this.page.keyboard().down(KeysName.getKey(this.page, keys[0]));
			this.page.keyboard().press(keys[1].toUpperCase());
			page.keyboard().up(KeysName.getKey(this.page, keys[0]));
		} else {
			this.page.keyboard().press((KeysName.getKey(this.page, combinationKeysName.toUpperCase())));
		}

	}

	@Override
	public void selectFromDropdown(String locatorType, String locatorValue, String type, String value) {
		Locator dropdown = this.findElement(locatorType, locatorValue);

		switch (type.toUpperCase()) {
		case "VISIBLE_TEXT":
			dropdown.selectOption(value);
			break;
		case "INDEX":
			dropdown.selectOption(new SelectOption().setIndex(0));
			break;
		case "VALUE":
			dropdown.selectOption(new SelectOption().setValue(value));
			break;
		default:
			throw new IllegalArgumentException("Invalid selection type: " + type);
		}

	}

	@Override
	public void switchToFrame(String locatorType, String locatorValue) {
		Locator frame = this.findElement(locatorType, locatorValue);
		if (frame.count() > 0) {
			frame.first().waitFor();
			page.frameLocator(locatorValue);
		} else {
			throw new PlaywrightException("Iframe not found: " + locatorValue);
		}

	}

	@Override
	public Object executeJSAction(String script, Object... args) {
		return this.page.evaluate(script, args);
	}

}
