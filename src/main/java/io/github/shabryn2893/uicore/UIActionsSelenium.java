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
 * Implements the methods of IActionUI interface.
 * 
 * @author shabbir rayeen UIActionsSelenium
 */
public class UIActionsSelenium implements IActionUI {
	private static final Logger logger = LoggerUtils.getLogger(UIActionsSelenium.class);
	private WebDriver driver;
	private WebElement element = null;
	private Actions action = null;

	/**
	 * Default constructor for the UIActionsSelenium class. Initializes a new
	 * instance with default values.
	 */
	public UIActionsSelenium(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
	}
	
	/**
	 * Locate web element in the DOM .
	 *
	 * @param locatorType  - This can have values like
	 *                     ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.d
	 * 
	 * @return It returns web element if finds else throw NoSuchElementException.
	 * 
	 *         findElement("XPATH","//button[@name='login']");
	 */

	private WebElement findElement(String locatorType, String locatorValue) {
		ElementLocator<WebElement> locator = LocatorFactory.getLocator(driver);
		return locator.locateElement(locatorType, locatorValue);
	}

	/**
	 * Locate web elements in the DOM .
	 *
	 * @param locatorType  - This can have values like
	 *                     ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.d
	 * 
	 * @return It returns List of match web elements if find, else return empty
	 *         list.
	 * 
	 *         findElements("XPATH","//button[@name='login']");
	 */
	
	private List<WebElement> findElements(String locatorType, String locatorValue) {
		ElementLocator<WebElement>  locator = LocatorFactory.getLocator(driver);
		return locator.locateElements(locatorType, locatorValue);
	}

	@Override
	public void maximizeScreen() {
		driver.manage().window().maximize();
	}

	@Override
	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	/**
	 * Close the current opened tab or window.
	 *
	 * closeCurrentTabWindow();
	 */
	@Override
	public void closeCurrentTabWindow() {
		driver.close();
	}

	/**
	 * Close the browse window.
	 *
	 * 
	 * 
	 * closeBrowser();
	 */
	@Override
	public void closeBrowser() {
		driver.quit();
	}

	/**
	 * Open the url in the browser window.
	 * 
	 * @param url - This take application url as parameter
	 * 
	 * 
	 *            openURL("https://www.google.com");
	 */
	@Override
	public void openURL(String url) {
		logger.info("Opening url:{}", url);
		driver.get(url);
	}

	/**
	 * Click on any web element.
	 *
	 * @param locatorType  - This can have values like
	 *                     ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.
	 * @param maxWaitTime  -maximum waiting to perform click.
	 * 
	 * 
	 *                     click("XPATH","//button[@name='login']",100);
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
	 * Enter text inside the web element: textbox or textarea .
	 *
	 * @param locatorType  - This can have values like
	 *                     ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.
	 * @param textToEnter  - Value that you want to type inside textbox or textarea
	 * @param maxWaitTime  -maximum waiting to enter text.
	 * 
	 * 
	 *                     type("XPATH","//input[@name='username']","abc",100);
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
	 * Configures and applies a fluent wait for a WebDriver instance.
	 * 
	 * <p>
	 * This method creates a {@link FluentWait} instance with the specified polling
	 * interval and timeout, and then applies the given condition to wait for. The
	 * wait will ignore {@link NoSuchElementException} during the waiting period.
	 * </p>
	 *
	 * @param function    A {@link Function} that takes a {@link WebDriver} instance
	 *                    and returns a {@code Boolean}. This function represents
	 *                    the condition to wait for. The wait will continue until
	 *                    this condition returns {@code true} or the timeout
	 *                    expires.
	 * @param maxWaitTime The maximum amount of time to wait for the condition, in
	 *                    seconds.
	 */
	public void setFluentWait(Function<WebDriver, Boolean> function, int maxWaitTime) {
		FluentWait<WebDriver> fluentWait = new FluentWait<>(driver);
		fluentWait.pollingEvery(Duration.ofMillis(500));
		fluentWait.withTimeout(Duration.ofSeconds(maxWaitTime));
		fluentWait.ignoring(NoSuchElementException.class);
		fluentWait.until(function);
	}

	/**
	 * Configures and applies a WebDriver wait with the specified condition.
	 * 
	 * <p>
	 * This method creates a {@link WebDriverWait} instance with the specified
	 * timeout and applies the given condition to wait for. The wait will use the
	 * provided condition to determine when to stop waiting.
	 * </p>
	 *
	 * @param function    A {@link Function} that takes a {@link WebDriver} instance
	 *                    and returns a {@code Boolean}. This function represents
	 *                    the condition to wait for. The wait will continue until
	 *                    this condition returns {@code true} or the timeout
	 *                    expires.
	 * @param maxWaitTime The maximum amount of time to wait for the condition, in
	 *                    seconds.
	 */
	public void setWebDriverWait(Function<WebDriver, Boolean> function, int maxWaitTime) {
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitTime));
		wait.until(function);

	}

	/**
	 * Wait for the page load.
	 *
	 * @param time - waiting time for page load.
	 * 
	 * 
	 *             waitForPageLoad(100);
	 */
	@Override
	public void waitForPageLoad(int time) {
		Function<WebDriver, Boolean> function = wDriver -> {
			String readyState = (String)this.executeJSAction("return document.readyState");
			logger.info("Current Window State:{}", readyState);
			return "complete".equals(readyState);
		};
		setWebDriverWait(function, time);
	}

	/**
	 * Wait for the page load.
	 *
	 * @param seconds - hard wait time for web element. waitForElement(10);
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
	 * Checks whether web element is displayed or enabled or selected.
	 *
	 * @param locatorType  - This can have values like
	 *                     ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.
	 * @param stateType    - This can values like DISPLAYED,ENABLED,SELECTED
	 * @return It returns boolean.
	 * 
	 *         isElementDisplayedOrEnabledOrSelected("XPATH","//button[@name='login']","DISPLAYED");
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
	 * Gets the attribute value based on the attribute name of the web element.
	 *
	 * @param locatorType   - This can have values like
	 *                      ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue  - Here you need to pass locator value based on the
	 *                      locator type selected.
	 * @param attributeName - This can values like value,class,id
	 * @param maxWaitTime   - maximum waiting time.
	 * @return It returns String.
	 * 
	 *         getAttributeValue("XPATH","//button[@name='login']","value",100);
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
	 * Gets the current url.
	 * 
	 * @return It returns String.
	 * 
	 *         getURL();
	 */
	@Override
	public String getURL() {
		return driver.getCurrentUrl();

	}

	/**
	 * Takes the screenshot of the visible web page.
	 * 
	 * @param screenshotPath -location where to store screenshot
	 * @return It returns screenshot in BASE64 format.
	 * 
	 *         takeScreenshot("./screenshot/abc.jpeg");
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
	 * Takes the screenshot of the specific web element.
	 * 
	 * @param locatorType    - This can have values like
	 *                       ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue   - Here you need to pass locator value based on the
	 *                       locator type selected.
	 * @param screenshotPath -location where to store screenshot
	 * @return It returns screenshot in BASE64 format.
	 * 
	 *         takeScreenshot("XPATH","//button[@name='login']","./screenshot/abc.jpeg");
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
	 * Perform click using javaScript executor.
	 *
	 * @param locatorType  - This can have values like
	 *                     ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.d
	 * @param maxWaitTime  - maximum waiting time.
	 * 
	 * 
	 *                     jsClick("XPATH","//button[@name='login']","value",100);
	 */
	@Override
	public void jsClick(String locatorType, String locatorValue, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			this.executeJSAction("arguments[0].click();",element);
		} else {
			logger.info("Unable to perform JSClick: Web Element is not present");
			assert false;
		}

	}

	/**
	 * Gets the text of any web element except text-box .
	 *
	 * @param locatorType  - This can have values like
	 *                     ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.d
	 * @param maxWaitTime  - maximum waiting time.
	 * @return It returns web element text.
	 * 
	 *         getText("XPATH","//button[@name='login']",100);
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
	 * Perform scroll operation till specific web element .
	 *
	 * @param locatorType  This can have values like
	 *                     ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue Here you need to pass locator value based on the locator
	 *                     type selected.d
	 * @param scrollType   NORMAL-Perform using Action class JS - Perform using
	 *                     JavaScript Executor
	 * @param maxWaitTime  maximum waiting time.
	 * 
	 * 
	 *                     scrollToElement("XPATH","//button[@name='login']","JS",100);
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
	 * Checks for web element presence in the DOM .
	 *
	 * @param locatorType  - This can have values like
	 *                     ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.d
	 * 
	 * @return It returns element state in form of true or false.
	 * 
	 *         isElementPresent("XPATH","//button[@name='login']");
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
	 * Wait till specific web element appears in the DOM.
	 *
	 * @param locatorType  - This can have values like
	 *                     ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.
	 * @param maxWaitTime  - maximum waiting time.
	 * @return It returns boolean value true or false.
	 * 
	 *         waitUntillElementAppear("XPATH","//button[@name='login']",100);
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
	 * Wait till specific web element disappears from the DOM.
	 *
	 * @param locatorType  - This can have values like
	 *                     ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.
	 * @param maxWaitTime  - maximum waiting time.
	 * @return It returns boolean value true or false.
	 * 
	 *         waitUntillElementDisappear("XPATH","//button[@name='login']",100);
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
	 * Perform navigation operation like page forward,back or refresh.
	 * 
	 * @param direction - This can have values like FORWARD,BACK, or REFRESH
	 * 
	 * 
	 *                  navigateTo("FORWARD");
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
			logger.error("Unspported Direction: {}", direction);
			assert false;

		}

	}
	
	public void switchToNewWindowTabWhenClicked(String locatorType, String locatorValue,int maxWaitTime) {
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
	 * Switch on the opened tab or window based on index value.
	 * 
	 * @param windowTabIndex - accept integer value
	 * 
	 * 
	 *                       switchToOpenedTabWindow(0);
	 */
	@Override
	public void switchToOpenedTabWindow(int windowTabIndex) {
		ArrayList<String> openWindows = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(openWindows.get(windowTabIndex));
	}

	/**
	 * Opens new tab or window and switch to it.
	 * 
	 * @param type - WINDOW=Opens new window TAB=Opens new tab
	 * 
	 * 
	 *             createNewWindowTabSwitch("WINDOW");
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
	 * Perform mouse hover action on specific web element .
	 *
	 * @param locatorType  - This can have values like
	 *                     ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.d
	 * @param maxWaitTime  - maximum waiting time.
	 * 
	 *                     hoverElement("XPATH","//button[@name='login']",100);
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
	 * Perform mouse right click action on specific web element .
	 *
	 * @param locatorType  - This can have values like
	 *                     ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.d
	 * @param maxWaitTime  - maximum waiting time.
	 * 
	 *                     rightClickElement("XPATH","//button[@name='login']",100);
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
	 * Perform mouse double click action on specific web element .
	 *
	 * @param locatorType  - This can have values like
	 *                     ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.d
	 * @param maxWaitTime  - maximum waiting time.
	 * 
	 *                     doubleClickElement("XPATH","//button[@name='login']",100);
	 */
	@Override
	public void doubleClickElement(String locatorType, String locatorValue, int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			action.doubleClick(element).perform();
		} else {
			logger.info("Unable to do double : Web Element is not present");
			assert false;
		}

	}

	/**
	 * Gets title of web page
	 *
	 * @return It return title of page in string format.
	 * 
	 *         getPageTitle();
	 */
	@Override
	public String getPageTitle() {
		return driver.getTitle();
	}

	/**
	 * Switches back to Parent Tab or Window or Iframe.
	 *
	 * 
	 * switchToParenTabWindowIframe();
	 */
	@Override
	public void switchToParenTabWindowIframe() {
		driver.switchTo().defaultContent();
	}

	/**
	 * Perform switch to iFrame.
	 *
	 * @param locatorType  - This can have values like
	 *                     INDEX,NAMEORID,ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.d
	 * 
	 *                     switchFrame("INDEX","0");
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
	 * Perform drag and drop mouse action.
	 *
	 * @param locatorType        - This can have values like
	 *                           INDEX,NAMEORID,ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param sourceLocatorValue - Here you need to pass locator value based on the
	 *                           locator type selected.This source web element.
	 * @param targetLocatorValue - Here you need to pass locator value based on the
	 *                           locator type selected.This target web element.
	 * 
	 *                           dragAndDrop("XPATH","//div[@id='source']","//div[@id='target']");
	 */
	@Override
	public void dragAndDrop(String locatorType, String sourceLocatorValue, String targetLocatorValue) {
		action.dragAndDrop(this.findElement(locatorType, sourceLocatorValue),
				this.findElement(locatorType, targetLocatorValue)).perform();
	}

	/**
	 * Perform keyboard typing action.
	 *
	 * @param locatorType  - This can have values like
	 *                     INDEX,NAMEORID,ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the
	 *                     locator type selected.
	 * @param textToType   - text to type
	 * 
	 *                     typeUsingKeyboard("XPATH","//input[@id='username']","Welcome");
	 */
	@Override
	public void typeUsingKeyboard(String locatorType, String locatorValue,int maxWaitTime,String textToType) {
		
		if (this.waitUntillElementAppear(locatorType, locatorValue, maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			action.click(element).sendKeys(textToType).perform();
		} else {
			logger.info("Unable to type using Keyboard on Web Element: {}",locatorValue);
			assert false;
		}
		
	}

	/**
	 * Perform keyboard action.
	 *
	 * @param combinationKeysName - CTRL+C,CTRL+V,ENTER,TAB,A,B,C,D. etc...
	 * 
	 *                            pressKeyCombination("CTRL,C");
	 *                            pressKeyCombination("ENTER");
	 */
	@Override
	public void pressKeyCombination(String combinationKeysName) {
		if (combinationKeysName.contains(",")) {
			String[] keys = combinationKeysName.split(",");
			action.keyDown(KeysName.getKey(this.driver,keys[0])).sendKeys(keys[1]).keyUp(KeysName.getKey(this.driver,keys[0])).perform();
		} else {
			action.sendKeys(KeysName.getKey(this.driver,combinationKeysName)).perform();
		}
	}

	@Override
	public void selectFromDropdown(String locatorType, String locatorValue, String type, String value) {
		WebElement dropdownElement =this.findElement(locatorType, locatorValue);
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

	@Override
	public Object executeJSAction(String script, Object... args) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        return jsExecutor.executeScript(script, args);
	}

}
