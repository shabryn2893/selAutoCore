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

/**
 * UIActionsPlaywright class provides implementations for browser interaction
 * using the Playwright library. It handles common web UI actions like clicking,
 * typing, navigating, etc., on web elements identified by various locators.
 */
public class UIActionsPlaywright implements IActionUI {
	private static final Logger logger = LoggerUtils.getLogger(UIActionsPlaywright.class);
	private Browser browser;
	private BrowserContext context;
	private Page page;
	private Locator element;

	/**
	 * Constructor to initialize UIActionsPlaywright with a given Browser instance.
	 *
	 * @param browser the Playwright Browser instance.
	 */
	public UIActionsPlaywright(Browser browser) {
		this.browser = browser;
		this.context = browser.newContext();
		this.page = context.newPage();
	}

	/**
	 * Finds a web element on the page using a locator type and value.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH).
	 * @param locatorValue the value of the locator.
	 * @return the found Locator element.
	 */
	private Locator findElement(String locatorType, String locatorValue) {
		ElementLocator<Locator> locator = LocatorFactory.getLocator(page);
		return locator.locateElement(locatorType, locatorValue);
	}

	/**
	 * Finds multiple web elements on the page using a locator type and value.
	 *
	 * @param locatorType  the type of locator.
	 * @param locatorValue the value of the locator.
	 * @return a list of Locator elements found.
	 */
	private List<Locator> findElements(String locatorType, String locatorValue) {
		ElementLocator<Locator> locator = LocatorFactory.getLocator(page);
		return locator.locateElements(locatorType, locatorValue);
	}

	/**
	 * Maximizes the browser window using a JavaScript command.
	 */
	@Override
	public void maximizeScreen() {
		this.executeJSAction("window.moveTo(0, 0); window.resizeTo(screen.width, screen.height);");
	}

	/**
	 * Clears all cookies from the browser context.
	 */
	@Override
	public void deleteAllCookies() {
		this.context.clearCookies();
	}

	/**
	 * Closes the current browser tab or window.
	 */
	@Override
	public void closeCurrentTabWindow() {
		this.page.close();
	}

	/**
	 * Closes the entire browser session.
	 */
	@Override
	public void closeBrowser() {
		this.browser.close();
	}

	/**
	 * Navigates the browser to the specified URL.
	 *
	 * @param url the URL to navigate to.
	 */
	@Override
	public void openURL(String url) {
		this.page.navigate(url);
	}

	/**
	 * Performs a click action on a web element after waiting for it to appear.
	 *
	 * @param locatorType  the type of locator (CSS, XPATH, etc.).
	 * @param locatorValue the value of the locator.
	 * @param maxWaitTime  the maximum wait time in seconds.
	 */
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

	/**
	 * Types the given text into a web element after waiting for it to appear.
	 *
	 * @param locatorType  the type of locator.
	 * @param locatorValue the value of the locator.
	 * @param textToEnter  the text to type.
	 * @param maxWaitTime  the maximum wait time in seconds.
	 */
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

	/**
	 * Waits for the page to fully load.
	 *
	 * @param timeInSeconds the time to wait in seconds.
	 */
	@Override
	public void waitForPageLoad(int timeInSeconds) {
		this.page.waitForLoadState();
		this.page.onLoad(p -> logger.info("Page loaded!"));
	}

	/**
	 * Waits for a specific duration of time.
	 *
	 * @param seconds the number of seconds to wait.
	 */
	@Override
	public void waitForElement(int seconds) {
		this.page.waitForTimeout(seconds);
	}

	/**
	 * Checks if an element is displayed, enabled, or selected based on the given
	 * state type.
	 *
	 * @param locatorType  the type of locator.
	 * @param locatorValue the value of the locator.
	 * @param stateType    the state to check (DISPLAYED, ENABLED, SELECTED).
	 * @return true if the element is in the specified state; false otherwise.
	 */
	@Override
	public boolean isElementDisplayedOrEnabledOrSelected(String locatorType, String locatorValue, String stateType) {
		this.element = findElement(locatorType, locatorValue);
		boolean status = false;
		switch (stateType.toUpperCase()) {
		case "DISPLAYED":
			status = this.element.isVisible();
			break;
		case "ENABLED":
			status = this.element.isEnabled();
			break;
		case "SELECTED":
			status = this.element.isChecked();
			break;
		default:
			logger.error("Unsupported state Type:{} ", stateType);
			assert false;
		}
		return status;
	}

	/**
	 * Retrieves the value of a specified attribute from a web element.
	 *
	 * @param locatorType   the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue  the value of the locator to find the element
	 * @param attributeName the name of the attribute whose value is to be retrieved
	 * @param maxWaitTime   the maximum time to wait for the element to appear
	 * @return the value of the specified attribute, or null if the element is not
	 *         found
	 */
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

	/**
	 * Retrieves the current URL of the page.
	 *
	 * @return the current URL as a String
	 */
	@Override
	public String getURL() {
		return this.page.url();
	}

	/**
	 * Takes a screenshot of the entire page and saves it to the specified path.
	 *
	 * @param screenshotPath the file path where the screenshot will be saved
	 * @return the Base64 encoded string of the screenshot image
	 */
	@Override
	public String takeScreenshot(String screenshotPath) {
		byte[] buffer = this.page
				.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
		return Base64.getEncoder().encodeToString(buffer);
	}

	/**
	 * Takes a screenshot of a specific web element and saves it to the specified
	 * path.
	 *
	 * @param locatorType    the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue   the value of the locator to find the element
	 * @param screenshotPath the file path where the screenshot will be saved
	 * @return the Base64 encoded string of the screenshot image
	 */
	@Override
	public String takeScreenshot(String locatorType, String locatorValue, String screenshotPath) {
		this.element = findElement(locatorType, locatorValue);
		byte[] buffer = this.element.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
		return Base64.getEncoder().encodeToString(buffer);
	}

	/**
	 * Performs a JavaScript click on a web element.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @param maxWaitTime  the maximum time to wait for the element to appear
	 */
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

	/**
	 * Retrieves the text content of a specified web element.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @param maxWaitTime  the maximum time to wait for the element to appear
	 * @return the text content of the element, or null if the element is not found
	 */
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

	/**
	 * Scrolls the page to a specified web element or to the bottom of the page.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @param scrollType   the type of scroll action ("NORMAL" for scrolling to the
	 *                     element, or to the bottom of the page)
	 * @param maxWaitTime  the maximum time to wait for the element to appear
	 */
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

	/**
	 * Checks if a specified web element is present in the DOM and is
	 * displayed/enabled.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @return true if the element is present, displayed, and enabled; false
	 *         otherwise
	 */
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

	/**
	 * Waits until a specified web element appears within a given time frame.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @param maxWaitTime  the maximum time to wait for the element to appear
	 * @return true if the element appears within the specified time; false
	 *         otherwise
	 */
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

	/**
	 * Waits until a specified web element disappears within a given time frame.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @param maxWaitTime  the maximum time to wait for the element to disappear
	 * @return true if the element disappears within the specified time; false
	 *         otherwise
	 */
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

	/**
	 * Navigates the browser in the specified direction (forward, back, or refresh).
	 *
	 * @param direction the direction to navigate ("FORWARD", "BACK", or "REFRESH")
	 */
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

	/**
	 * Switches to a new window or tab when an element is clicked.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @param maxWaitTime  the maximum time to wait for the element to appear
	 */
	public void switchToNewWindowTabWhenClicked(String locatorType, String locatorValue, int maxWaitTime) {
		Page newPage = this.context.waitForPage(() -> click(locatorType, locatorValue, maxWaitTime));
		newPage.waitForLoadState();
		this.page = newPage;
	}

	/**
	 * Switches to a previously opened tab or window based on its index.
	 *
	 * @param windowTabIndex the index of the window or tab to switch to
	 */
	@Override
	public void switchToOpenedTabWindow(int windowTabIndex) {
		List<Page> allPages = this.context.pages();
		this.page = allPages.get(windowTabIndex);
	}

	/**
	 * Creates a new window or tab and switches to it.
	 *
	 * @param type the type of action to create a new window or tab
	 */
	@Override
	public void createNewWindowTabSwitch(String type) {
		Page newPage = this.page.waitForPopup(() -> page.click("a[target='_blank']"));
		newPage.waitForLoadState();
		this.page = newPage;

	}

	/**
	 * Hovers over a specified web element.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @param maxWaitTime  the maximum time to wait for the element to appear
	 */
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

	/**
	 * Performs a right-click action on a specified web element.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @param maxWaitTime  the maximum time to wait for the element to appear
	 */
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

	/**
	 * Performs a double-click action on a specified web element.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @param maxWaitTime  the maximum time to wait for the element to appear
	 */
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

	/**
	 * Retrieves the title of the current page.
	 *
	 * @return the title of the page as a String
	 */
	@Override
	public String getPageTitle() {
		return this.page.title();
	}

	/**
	 * Switches to the main frame of the page from an iframe.
	 */
	@Override
	public void switchToParenTabWindowIframe() {
		this.page.mainFrame();
	}

	/**
	 * Drags a web element from a source locator to a target locator.
	 *
	 * @param locatorType        the type of locator (e.g., CSS, XPATH)
	 * @param sourceLocatorValue the value of the locator for the source element
	 * @param targetLocatorValue the value of the locator for the target element
	 */
	@Override
	public void dragAndDrop(String locatorType, String sourceLocatorValue, String targetLocatorValue) {
		Locator source = this.findElement(locatorType, sourceLocatorValue);
		Locator target = this.findElement(locatorType, sourceLocatorValue);
		source.dragTo(target);
	}

	/**
	 * Types text into a specified web element using the keyboard.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @param maxWaitTime  the maximum time to wait for the element to appear
	 * @param textToType   the text to be typed into the element
	 */
	@Override
	public void typeUsingKeyboard(String locatorType, String locatorValue, int maxWaitTime, String textToType) {
		this.click(locatorType, locatorValue, maxWaitTime);
		this.page.keyboard().type(textToType);
	}

	/**
	 * Presses a combination of keys on the keyboard.
	 *
	 * @param combinationKeysName the combination of keys to press, separated by a
	 *                            comma if multiple
	 */
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

	/**
	 * Selects an option from a dropdown element based on the specified selection
	 * type.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the dropdown element
	 * @param type         the method of selection ("VISIBLE_TEXT", "INDEX", or
	 *                     "VALUE")
	 * @param value        the value to select (depends on the selection type)
	 */
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

	/**
	 * Switches the context to a specified iframe element.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the iframe element
	 * @throws PlaywrightException if the iframe is not found
	 */
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

	/**
	 * Executes a specified JavaScript action on the current page.
	 *
	 * @param script the JavaScript code to execute
	 * @param args   optional arguments to pass to the script
	 * @return the result of the executed script
	 */
	@Override
	public Object executeJSAction(String script, Object... args) {
		return this.page.evaluate(script, args);
	}

}
