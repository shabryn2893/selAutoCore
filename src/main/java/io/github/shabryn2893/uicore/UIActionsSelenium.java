package io.github.shabryn2893.uicore;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import io.github.shabryn2893.locatorfactory.ElementLocator;
import io.github.shabryn2893.locatorfactory.LocatorFactory;
import io.github.shabryn2893.utils.LoggerUtils;

/**
 * This class provides various UI action methods for Selenium WebDriver. It
 * includes functionalities for managing browser windows, interacting with web
 * elements, and executing JavaScript actions.
 */
public class UIActionsSelenium implements IActionUI {
	private static final Logger logger = LoggerUtils.getLogger(UIActionsSelenium.class);
	private WebDriver driver;
	private WebElement element = null;
	private Actions action = null;

	/**
	 * Constructs a UIActionsSelenium object with the specified WebDriver.
	 *
	 * @param driver the WebDriver instance used for browser interactions
	 */
	public UIActionsSelenium(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
	}

	/**
	 * Finds a web element using the specified locator type and value.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @return the located web element
	 */
	private WebElement findElement(String locatorType, String locatorValue) {
		ElementLocator<WebElement> locator = LocatorFactory.getLocator(driver);
		return locator.locateElement(locatorType, locatorValue);
	}

	/**
	 * Finds a list of web elements using the specified locator type and value.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the elements
	 * @return a list of located web elements
	 */
	private List<WebElement> findElements(String locatorType, String locatorValue) {
		ElementLocator<WebElement> locator = LocatorFactory.getLocator(driver);
		return locator.locateElements(locatorType, locatorValue);
	}

	/**
	 * Maximizes the current browser window.
	 */
	@Override
	public void maximizeScreen() {
		driver.manage().window().maximize();
	}

	/**
	 * Deletes all cookies from the current browser session.
	 */
	@Override
	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	/**
	 * Closes the current browser tab or window.
	 */
	@Override
	public void closeCurrentTabWindow() {
		driver.close();
	}

	/**
	 * Closes the browser and quits the WebDriver session.
	 */
	@Override
	public void closeBrowser() {
		driver.quit();
	}

	/**
	 * Opens the specified URL in the browser.
	 *
	 * @param url the URL to open
	 */
	@Override
	public void openURL(String url) {
		logger.info("Opening url:{}", url);
		driver.get(url);
	}

	/**
	 * Clicks on a web element located by the specified locator type and value.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @param maxWaitTime  the maximum time to wait for the element to be clickable
	 */
	@Override
	public void click(String locatorType, String locatorValue, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			element.click();
		} else {
			logger.error("WebElement {} is not clickable.", locatorValue);
			assert false;
		}
	}

	/**
	 * Types the specified text into a web element located by the specified locator
	 * type and value.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @param textToEnter  the text to enter into the element
	 * @param maxWaitTime  the maximum time to wait for the element to be enabled
	 */
	@Override
	public void type(String locatorType, String locatorValue, String textToEnter, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			element.sendKeys(textToEnter);
		} else {
			logger.error("WebElement {} is not enabled.", locatorValue);
			assert false;
		}
	}

	/**
	 * Sets a fluent wait for a specified condition to be met.
	 *
	 * @param function    the condition to wait for
	 * @param maxWaitTime the maximum time to wait
	 */
	public void setFluentWait(Function<WebDriver, Boolean> function, int maxWaitTime) {
		FluentWait<WebDriver> fluentWait = new FluentWait<>(driver);
		fluentWait.pollingEvery(Duration.ofMillis(500));
		fluentWait.withTimeout(Duration.ofSeconds(maxWaitTime));
		fluentWait.ignoring(NoSuchElementException.class);
		fluentWait.until(function);
	}

	/**
	 * Sets a WebDriver wait for a specified condition to be met.
	 *
	 * @param function    the condition to wait for
	 * @param maxWaitTime the maximum time to wait
	 */
	public void setWebDriverWait(Function<WebDriver, Boolean> function, int maxWaitTime) {
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitTime));
		wait.until(function);
	}

	/**
	 * Waits for the page to load completely within the specified time.
	 *
	 * @param time the maximum time to wait for the page to load
	 */
	@Override
	public void waitForPageLoad(int time) {
		Function<WebDriver, Boolean> function = wDriver -> {
			String readyState = (String) this.executeJSAction("return document.readyState");
			logger.info("Current Window State:{}", readyState);
			return "complete".equals(readyState);
		};
		setWebDriverWait(function, time);
	}

	/**
	 * Waits for a specified number of seconds.
	 *
	 * @param seconds the number of seconds to wait
	 */
	@Override
	public void waitForElement(int seconds) {
		int time = seconds * 1000;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			/* Clean up whatever needs to be handled before interrupting */
			Thread.currentThread().interrupt();
			e.printStackTrace();
			assert false;
		}
	}

	/**
	 * Checks if a web element is displayed, enabled, or selected based on the
	 * specified state type.
	 *
	 * @param locatorType  the type of locator (e.g., CSS, XPATH)
	 * @param locatorValue the value of the locator to find the element
	 * @param stateType    the state type to check ("DISPLAYED", "ENABLED", or
	 *                     "SELECTED")
	 * @return true if the element meets the specified condition, otherwise false
	 */
	@Override
	public boolean isElementDisplayedOrEnabledOrSelected(String locatorType, String locatorValue, String stateType) {
		element = findElement(locatorType, locatorValue);
		boolean status = false;
		switch (stateType.toUpperCase()) {
		case "DISPLAYED": {
			status = element.isDisplayed();
			break;
		}
		case "ENABLED": {
			status = element.isEnabled();
			break;
		}
		case "SELECTED": {
			status = element.isSelected();
			break;
		}
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
			element = findElement(locatorType, locatorValue);
			attributeValue = element.getAttribute(attributeName);
		} else {
			logger.info("Unable to find attribute value as Web Element is not present in the DOM");
			assert false;
		}
		return attributeValue;
	}

	/**
	 * Retrieves the current URL of the browser.
	 *
	 * @return the current URL as a String
	 */
	@Override
	public String getURL() {
		return driver.getCurrentUrl();
	}

	/**
	 * Takes a screenshot of the current browser window and saves it at the
	 * specified path.
	 * 
	 * @param screenshotPath the path where the screenshot will be saved
	 * @return the screenshot in Base64 format
	 */
	@Override
	public String takeScreenshot(String screenshotPath) {
		String screenshot = null;
		try {
			screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(screenshotPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return screenshot;
	}

	/**
	 * Takes a screenshot of the specified element and saves it at the provided
	 * path.
	 * 
	 * @param locatorType    the type of locator (e.g., id, xpath, cssSelector)
	 * @param locatorValue   the value of the locator
	 * @param screenshotPath the path where the screenshot will be saved
	 * @return the screenshot in Base64 format
	 */
	@Override
	public String takeScreenshot(String locatorType, String locatorValue, String screenshotPath) {
		String screenshot = null;
		try {
			screenshot = findElement(locatorType, locatorValue).getScreenshotAs(OutputType.BASE64);
			FileUtils.copyFile(findElement(locatorType, locatorValue).getScreenshotAs(OutputType.FILE),
					new File(screenshotPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return screenshot;
	}

	/**
	 * Performs a JavaScript click on the specified element.
	 * 
	 * @param locatorType  the type of locator (e.g., id, xpath, cssSelector)
	 * @param locatorValue the value of the locator
	 * @param maxWaitTime  the maximum wait time for the element to appear
	 */
	@Override
	public void jsClick(String locatorType, String locatorValue, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			this.executeJSAction("arguments[0].click();", element);
		} else {
			logger.info("Unable to perform JSClick: Web Element is not present");
			assert false;
		}
	}

	/**
	 * Retrieves the text of the specified element.
	 * 
	 * @param locatorType  the type of locator (e.g., id, xpath, cssSelector)
	 * @param locatorValue the value of the locator
	 * @param maxWaitTime  the maximum wait time for the element to appear
	 * @return the text of the element
	 */
	@Override
	public String getText(String locatorType, String locatorValue, int maxWaitTime) {
		String textValue = null;
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			textValue = element.getText().trim();
		} else {
			logger.info("Unable to get Text: Web Element is not present");
			assert false;
		}
		return textValue;
	}

	/**
	 * Scrolls to the specified element using either normal or JavaScript scrolling.
	 * 
	 * @param locatorType  the type of locator (e.g., id, xpath, cssSelector)
	 * @param locatorValue the value of the locator
	 * @param scrollType   the type of scrolling ("NORMAL" or JavaScript)
	 * @param maxWaitTime  the maximum wait time for the element to appear
	 */
	@Override
	public void scrollToElement(String locatorType, String locatorValue, String scrollType, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			if (scrollType.equalsIgnoreCase("NORMAL")) {
				action.scrollToElement(element).perform();
			} else {
				this.executeJSAction("arguments[0].scrollIntoView(true);", element);
			}
		} else {
			logger.info("Unable to perform scroll: Web Element is not present");
			assert false;
		}
	}

	/**
	 * Checks if the specified element is present, displayed, and enabled.
	 * 
	 * @param locatorType  the type of locator (e.g., id, xpath, cssSelector)
	 * @param locatorValue the value of the locator
	 * @return true if the element is present, displayed, and enabled; false
	 *         otherwise
	 */
	@Override
	public boolean isElementPresent(String locatorType, String locatorValue) {
		boolean status = false;
		if (!(findElements(locatorType, locatorValue).isEmpty())
				&& isElementDisplayedOrEnabledOrSelected(locatorType, locatorValue, "DISPLAYED")
				&& isElementDisplayedOrEnabledOrSelected(locatorType, locatorValue, "ENABLED")) {
			status = true;
		}
		return status;
	}

	/**
	 * Waits until the specified element appears within the given time.
	 * 
	 * @param locatorType  the type of locator (e.g., id, xpath, cssSelector)
	 * @param locatorValue the value of the locator
	 * @param maxWaitTime  the maximum wait time in seconds
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
	 * Waits until the specified element disappears within the given time.
	 * 
	 * @param locatorType  the type of locator (e.g., id, xpath, cssSelector)
	 * @param locatorValue the value of the locator
	 * @param maxWaitTime  the maximum wait time in seconds
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
	 * Navigates the browser based on the provided direction (FORWARD, BACK, or
	 * REFRESH).
	 * 
	 * @param direction the direction to navigate (e.g., FORWARD, BACK, REFRESH)
	 */
	@Override
	public void navigateTo(String direction) {
		switch (direction.toUpperCase()) {
		case "FORWARD":
			driver.navigate().forward();
			break;
		case "BACK":
			driver.navigate().back();
			break;
		case "REFRESH":
			driver.navigate().refresh();
			break;
		default:
			logger.error("Unsupported Direction: {}", direction);
			assert false;
		}
	}

	/**
	 * Switches to a newly opened window/tab when an element is clicked.
	 * 
	 * @param locatorType  the type of locator (e.g., id, xpath, cssSelector)
	 * @param locatorValue the value of the locator
	 * @param maxWaitTime  the maximum wait time for the element to appear
	 */
	public void switchToNewWindowTabWhenClicked(String locatorType, String locatorValue, int maxWaitTime) {
		click(locatorType, locatorValue, maxWaitTime);
		String originalWindow = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();
		for (String windowHandle : windowHandles) {
			if (!windowHandle.equals(originalWindow)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}
	}

	/**
	 * Switches to a specific window/tab based on its index.
	 * 
	 * @param windowTabIndex the index of the window/tab to switch to
	 */
	@Override
	public void switchToOpenedTabWindow(int windowTabIndex) {
		ArrayList<String> openWindows = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(openWindows.get(windowTabIndex));
	}

	/**
	 * Creates and switches to a new window or tab.
	 * 
	 * @param type the type of window to create ("WINDOW" or "TAB")
	 */
	@Override
	public void createNewWindowTabSwitch(String type) {
		if (type.equalsIgnoreCase("WINDOW")) {
			driver.switchTo().newWindow(WindowType.WINDOW);
		} else {
			driver.switchTo().newWindow(WindowType.TAB);
		}
	}

	/**
	 * Hovers over the specified element.
	 * 
	 * @param locatorType  the type of locator (e.g., id, xpath, cssSelector)
	 * @param locatorValue the value of the locator
	 * @param maxWaitTime  the maximum wait time for the element to appear
	 */
	@Override
	public void hoverElement(String locatorType, String locatorValue, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			action.moveToElement(element).perform();
		} else {
			logger.info("Unable to do hover: Web Element is not present");
			assert false;
		}
	}

	/**
	 * Right-clicks on the specified element.
	 * 
	 * @param locatorType  the type of locator (e.g., id, xpath, cssSelector)
	 * @param locatorValue the value of the locator
	 * @param maxWaitTime  the maximum wait time for the element to appear
	 */
	@Override
	public void rightClickElement(String locatorType, String locatorValue, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			action.contextClick(element).perform();
		} else {
			logger.info("Unable to do right click: Web Element is not present");
			assert false;
		}
	}

	/**
	 * Double-clicks on the specified element.
	 * 
	 * @param locatorType  the type of locator (e.g., id, xpath, cssSelector)
	 * @param locatorValue the value of the locator
	 * @param maxWaitTime  the maximum wait time for the element to appear
	 */
	@Override
	public void doubleClickElement(String locatorType, String locatorValue, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			action.doubleClick(element).perform();
		} else {
			logger.info("Unable to do double click: Web Element is not present");
			assert false;
		}
	}

	/**
	 * Retrieves the current page's title.
	 * 
	 * @return the page title as a String
	 */
	@Override
	public String getPageTitle() {
		return driver.getTitle();
	}

	/**
	 * Switches to the default content of the parent tab or window.
	 */
	@Override
	public void switchToParenTabWindowIframe() {
		driver.switchTo().defaultContent();
	}

	/**
	 * Switches to the specified frame using an index, name, ID, or element locator.
	 * 
	 * @param locatorType  the type of locator (e.g., id, xpath, cssSelector)
	 * @param locatorValue the value of the locator or the index of the frame
	 */
	@Override
	public void switchToFrame(String locatorType, String locatorValue) {
		switch (locatorType.toUpperCase()) {
		case "INDEX":
			driver.switchTo().frame(Integer.parseInt(locatorValue));
			break;
		case "NAMEORID":
			driver.switchTo().frame(locatorValue);
			break;
		default:
			driver.switchTo().frame(this.findElement(locatorType, locatorValue));
		}
	}

	/**
	 * Performs a drag and drop operation between two elements.
	 * 
	 * @param locatorType        the type of locator (e.g., id, xpath, cssSelector)
	 * @param sourceLocatorValue the value of the source element's locator
	 * @param targetLocatorValue the value of the target element's locator
	 */
	@Override
	public void dragAndDrop(String locatorType, String sourceLocatorValue, String targetLocatorValue) {
		action.dragAndDrop(this.findElement(locatorType, sourceLocatorValue),
				this.findElement(locatorType, targetLocatorValue)).perform();
	}

	/**
	 * Types text into an element using the keyboard.
	 * 
	 * @param locatorType  the type of locator (e.g., id, xpath, cssSelector)
	 * @param locatorValue the value of the locator
	 * @param maxWaitTime  the maximum wait time for the element to appear
	 * @param textToType   the text to type into the element
	 */
	@Override
	public void typeUsingKeyboard(String locatorType, String locatorValue, int maxWaitTime, String textToType) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			action.click(element).sendKeys(textToType).perform();
		} else {
			logger.info("Unable to type using Keyboard on Web Element: {}", locatorValue);
			assert false;
		}
	}

	/**
	 * Performs a keyboard key combination (e.g., Ctrl+C).
	 * 
	 * @param combinationKeysName the name of the key combination, separated by a
	 *                            comma (e.g., "CTRL,C")
	 */
	@Override
	public void pressKeyCombination(String combinationKeysName) {
		if (combinationKeysName.contains(",")) {
			String[] keys = combinationKeysName.split(",");
			action.keyDown(KeysName.getKey(this.driver, keys[0])).sendKeys(keys[1])
					.keyUp(KeysName.getKey(this.driver, keys[0])).perform();
		} else {
			action.sendKeys(KeysName.getKey(this.driver, combinationKeysName)).perform();
		}
	}

	/**
	 * Selects an option from a dropdown based on value, index, or visible text.
	 * 
	 * @param locatorType  the type of locator (e.g., id, xpath, cssSelector)
	 * @param locatorValue the value of the locator
	 * @param type         the selection type (VALUE, INDEX, or VISIBLE_TEXT)
	 * @param value        the value or text to select
	 */
	@Override
	public void selectFromDropdown(String locatorType, String locatorValue, String type, String value) {
		WebElement dropdownElement = this.findElement(locatorType, locatorValue);
		Select dropdown = new Select(dropdownElement);
		switch (type.toUpperCase()) {
		case "VALUE":
			dropdown.selectByValue(value);
			break;
		case "INDEX":
			dropdown.selectByIndex(Integer.parseInt(value));
			break;
		case "VISIBLE_TEXT":
			dropdown.selectByVisibleText(value);
			break;
		default:
			throw new IllegalArgumentException("Invalid selection type: " + type);
		}
	}

	/**
	 * Executes a JavaScript action on the browser.
	 * 
	 * @param script the JavaScript script to execute
	 * @param args   the arguments to pass to the script
	 * @return the result of the JavaScript execution
	 */
	@Override
	public Object executeJSAction(String script, Object... args) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript(script, args);
	}

}
