package io.github.shabryn2893.selAutoCore.uiCore;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import io.github.shabryn2893.selAutoCore.uiDriverFactory.DriverManager;
import io.github.shabryn2893.selAutoCore.uiDriverFactory.DriverManagerFactory;

public class UIActionsSelenium implements IActionUI {

	private DriverManager driverManager;
	private WebDriver driver;
	private static WebElement element = null;
	//private int maxWaitTime = ConfigProp.MAX_WAIT_TIME;
	private JavascriptExecutor jExecutor = null;
	private Actions action=null;

	/**
	 * Initiate browser based on the parameter passed
	 *
	 * @param browserType - This can accept values like CHROME,FIREFOX or EDGE.
	 * @param isHeadlessMode - This can accept values like true or false.
	 * @return It does not return anything.
	 * @example
	 * initializeDriver("CHROME",false);
	 */
	@Override
	public void initializeDriver(String browserType, boolean isHeadlessMode) {
		driverManager = DriverManagerFactory.getManager(browserType);
		driver = driverManager.getDriver(isHeadlessMode);
		jExecutor = (JavascriptExecutor) driver;
		action=new Actions(driver);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	
	/**
	 * Close the current opened tab or window.
	 *
	 * @return It does not return anything.
	 * @example
	 * closeCurrentTabWindow();
	 */
	@Override
	public void closeCurrentTabWindow() {
		driver.close();
	}
	
	/**
	 * Close the browse window.
	 *
	 * @return It does not return anything.
	 * @example
	 * closeBrowser();
	 */
	@Override
	public void closeBrowser() {
		driverManager.quitDriver();
	}

	/**
	 * Open the url in the browser window.
	 * @param url - This take application url as parameter
	 * @return It does not return anything.
	 * @example
	 * openURL("https://www.google.com");
	 */
	@Override
	public void openURL(String url) {
		System.out.println("Opening url:" + url);
		driver.get(url);
	}
	
	/**
	 * Click on any web element.
	 *
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.
	 * @param maxWaitTime -maximum waiting to perform click. 
	 * @return It does not return anything.
	 * @example
	 * click("XPATH","//button[@name='login']",100);
	 */
	@Override
	public void click(String locatorType, String locatorValue,int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue,maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			waitUntill(locatorType, locatorValue, "CLICKABLE",maxWaitTime);
			element.click();
		} else {
			System.out.println("WebElement [" + locatorValue + "] is not clickable.");
			assert false;
		}
	}
	
	/**
	 * Enter text inside the web element: textbox or textarea .
	 *
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.
	 * @param textToEnter - Value that you want to type inside textbox or textarea
	 * @param maxWaitTime -maximum waiting to enter text. 
	 * @return It does not return anything.
	 * @example
	 * type("XPATH","//input[@name='username']","abc",100);
	 */
	@Override
	public void type(String locatorType, String locatorValue, String textToEnter,int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue,maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			element.sendKeys(textToEnter);
		} else {
			System.out.println("WebElement [" + locatorValue + "] is not enabled.");
			assert false;
		}

	}

	/**
	 * Wait any web element until specific condition is matched.
	 *
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.
	 * @param conditionName - This can values like CLICKABLE,INVISIBLE,VISIBLE or SELECTED
	 * @param maxWaitTime -maximum waiting to match specific condition.
	 * @return It does not return anything.
	 * @example
	 * waitUntill("XPATH","//button[@name='login']","VISIBLE",100);
	 */
	@Override
	public void waitUntill(String locatorType, final String locatorValue, final String conditionName,int maxWaitTime) {
		try {
			element = findElement(locatorType, locatorValue);
			Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
				public Boolean apply(WebDriver driver) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitTime));
					switch (conditionName.toUpperCase()) {
					case "CLICKABLE": {
						wait.until(ExpectedConditions.elementToBeClickable(element));
						break;
					}
					case "INVISIBLE": {
						wait.until(ExpectedConditions.invisibilityOf(element));
						break;
					}
					case "VISIBLE": {
						wait.until(ExpectedConditions.visibilityOf(element));
						break;
					}
					case "SELECTED": {
						wait.until(ExpectedConditions.elementToBeSelected(element));
						break;
					}
					default:
						System.out.println("Unsupported wait untill action: " + conditionName);
						assert false;
					}
					return true;
				}
			};
			setFluentWait(function,maxWaitTime);
		} catch (Exception ex1) {
			ex1.printStackTrace();
		}

	}

	/**
	 * Apply Fluent wait to any web element
	 * @param function take function as parameter
	 * @param maxWaitTime - maximum waiting time.
	 * @return It does not return anything.
	 * @example
	 * Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
				public Boolean apply(WebDriver driver) {
				your actions.
				}
		}
	 * setFluentWait(function,100);
	 */
	public void setFluentWait(Function<WebDriver, Boolean> function,int maxWaitTime) {
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
		fluentWait.pollingEvery(Duration.ofMillis(500));
		fluentWait.withTimeout(Duration.ofSeconds(maxWaitTime));
		fluentWait.ignoring(NoSuchElementException.class);
		fluentWait.until(function);
	}


	/**
	 * Apply Web Driver Wait to any web element
	 * @param function take function as parameter
	 * @param maxWaitTime - maximum waiting time.
	 * @return It does not return anything.
	 * @example
	 * * Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
				public Boolean apply(WebDriver driver) {
				your actions.
				}
		}
	 * setWebDriverWait(function,100);
	 */
	public void setWebDriverWait(Function<WebDriver, Boolean> function, int maxWaitTime) {
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitTime));
		wait.until(function);

	}
	
	/**
	 * Wait for the page load.
	 *
	 * @param time - waiting time for page load.
	 * @return It does not return anything.
	 * @example
	 * waitForPageLoad(100);
	 */
	@Override
	public void waitForPageLoad(int time) {

		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				System.out.println("Current Window State  : "
						+ String.valueOf(jExecutor.executeScript("return document.readyState")));
				return String.valueOf(jExecutor.executeScript("return document.readyState")).equals("complete");
			}
		};

		setWebDriverWait(function, time);
	}

	/**
	 * Wait for the page load.
	 *
	 * @param seconds - hard wait time for web element.
	 * @return It does not return anything.
	 * @example
	 * waitForElement(10);
	 */
	@Override
	public void waitForElement(int seconds) {
		int time = seconds * 1000;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
			assert false;
		}

	}

	/**
	 * Checks whether web element is displayed or enabled or selected.
	 *
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.
	 * @param stateType - This can values like DISPLAYED,ENABLED,SELECTED
	 * @return It does not return anything.
	 * @example
	 * isElementDisplayedOrEnabledOrSelected("XPATH","//button[@name='login']","DISPLAYED");
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
			System.out.println("Unsupported state Type: " + stateType);
			assert false;
		}
		return status;
	}

	/**
	 * Gets the attribute value based on the attribute name of the web element.
	 *
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.
	 * @param attributeName - This can values like value,class,id
	 * @param maxWaitTime - maximum waiting time.
	 * @return It does not return anything.
	 * @example
	 * getAttributeValue("XPATH","//button[@name='login']","value",100);
	 */
	@Override
	public String getAttributeValue(String locatorType, String locatorValue, String attributeName,int maxWaitTime) {
		String attributeValue = null;
		waitUntill(locatorType, locatorValue, "VISIBLE",maxWaitTime);
		if (this.waitUntillElementAppear(locatorType, locatorValue,maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			attributeValue = element.getAttribute(attributeName);
		} else {
			System.out.println("Unable to find attribute value as Web Element is not present in the DOM");
			assert false;
		}
		return attributeValue;
	}

	/**
	 * Gets the current url.
	 * 
	 * @return It does not return anything.
	 * @example
	 * getURL();
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
	 * @example
	 * takeScreenshot("./screenshot/abc.jpeg");
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
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.
	 * @param screenshotPath -location where to store screenshot
	 * @return It returns screenshot in BASE64 format.
	 * @example
	 * takeScreenshot("XPATH","//button[@name='login']","./screenshot/abc.jpeg");
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
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.d
	 * @param maxWaitTime - maximum waiting time.
	 * @return It does not return anything.
	 * @example
	 * jsClick("XPATH","//button[@name='login']","value",100);
	 */
	@Override
	public void jsClick(String locatorType, String locatorValue,int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue,maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			jExecutor.executeScript("arguments[0].click();", element);
		} else {
			System.out.println("Unable to perform JSClick: Web Element is not present");
			assert false;
		}

	}
	
	/**
	 * Gets the text of any web element except text-box .
	 *
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.d
	 * @param maxWaitTime - maximum waiting time.
	 * @return It returns web element text.
	 * @example
	 * getText("XPATH","//button[@name='login']",100);
	 */
	@Override
	public String getText(String locatorType, String locatorValue,int maxWaitTime) {
		String textValue = null;
		if (this.waitUntillElementAppear(locatorType, locatorValue,maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			textValue = element.getText().trim();
		} else {
			System.out.println("Unable to get Text: Web Element is not present");
			assert false;
		}
		return textValue;
	}
	
	/**
	 * Perform scroll operation till specific web element .
	 *
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.d
	 * @param scrollType-NORMAL- Perform using Action class 
 						JS - Perform using JavaScript Executor
	 * @param maxWaitTime - maximum waiting time.
	 * @return It does not return anything.
	 * @example
	 * scrollToElement("XPATH","//button[@name='login']","JS",100);
	 */
	@Override
	public void scrollToElement(String locatorType, String locatorValue, String scrollType,int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue,maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			if (scrollType.equalsIgnoreCase("NORMAL")) {
				action.scrollToElement(element).perform();
			} else {
				jExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
			}

		} else {
			System.out.println("Unable to perform scroll: Web Element is not present");
			assert false;
		}

	}
	
	/**
	 * Checks for web element presence in the DOM .
	 *
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.d
	
	 * @return It returns element state in form of true or false.
	 * @example
	 * isElementPresent("XPATH","//button[@name='login']");
	 */
	@Override
	public boolean isElementPresent(String locatorType, String locatorValue) {
		boolean status = false;

		if (findElements(locatorType, locatorValue).size() > 0
				&& isElementDisplayedOrEnabledOrSelected(locatorType, locatorValue, "DISPLAYED")
				&& isElementDisplayedOrEnabledOrSelected(locatorType, locatorValue, "ENABLED")) {
			status = true;
		}
		return status;

	}

	/**
	 * Locate web element in the DOM .
	 *
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.d
	
	 * @return It returns web element  if finds else throw NoSuchElementException.
	 * @example
	 * findElement("XPATH","//button[@name='login']");
	 */
	@Override
	public WebElement findElement(String locatorType, String locatorValue) {
		WebElement element = null;
		try {
			switch (locatorType.toUpperCase()) {
			case "ID":
				element = driver.findElement(By.id(locatorValue));
				break;
			case "XPATH":
				element = driver.findElement(By.xpath(locatorValue));
				break;
			case "LINKTEXT":
				element = driver.findElement(By.linkText(locatorValue));
				break;
			case "CSS":
				element = driver.findElement(By.cssSelector(locatorValue));
				break;
			case "NAME":
				element = driver.findElement(By.name(locatorValue));
				break;
			case "TAG":
				element = driver.findElement(By.tagName(locatorValue));
				break;
			case "PARTIALLINKTEXT":
				element = driver.findElement(By.partialLinkText(locatorValue));
				break;
			case "SHADOWDOM":
				/*
				 * locatorValue=shadow-host-selector~shadow-element-selector shadow-host-selecto
				 * = shadowElementLocators[0] shadow-element-selector=shadowElementLocators[1]
				 */
				String[] shadowElementLocators = locatorValue.split("~");
				WebElement shadowHost = driver.findElement(By.cssSelector(shadowElementLocators[0]));
				WebElement shadowRoot = (WebElement) jExecutor.executeScript("return arguments[0].shadowRoot",
						shadowHost);
				element = shadowRoot.findElement(By.cssSelector(shadowElementLocators[0]));

			default:
				element = driver.findElement(By.className(locatorValue));
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			System.out.println("NoSuchElementException");
			assert false;
		} catch (WebDriverException e) {
			e.printStackTrace();
			System.out.println("WebDriverException");
			assert false;
		}

		return element;
	}

	/**
	 * Locate web elements in the DOM .
	 *
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.d
	
	 * @return It returns List of match web elements if find, else return empty list.
	 * @example
	 * findElements("XPATH","//button[@name='login']");
	 */
	@Override
	public List<WebElement> findElements(String locatorType, String locatorValue) {
		List<WebElement> elements = null;
		try {
			switch (locatorType.toUpperCase()) {
			case "ID":
				elements = driver.findElements(By.id(locatorValue));
				break;
			case "XPATH":
				elements = driver.findElements(By.xpath(locatorValue));
				break;
			case "LINKTEXT":
				elements = driver.findElements(By.linkText(locatorValue));
				break;
			case "CSS":
				elements = driver.findElements(By.cssSelector(locatorValue));
				break;
			case "NAME":
				elements = driver.findElements(By.name(locatorValue));
				break;
			case "TAG":
				elements = driver.findElements(By.tagName(locatorValue));
				break;
			case "PARTIALLINKTEXT":
				elements = driver.findElements(By.partialLinkText(locatorValue));
				break;
			default:
				elements = driver.findElements(By.className(locatorValue));
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			System.out.println("NoSuchElementException");
			assert false;
		} catch (WebDriverException e) {
			e.printStackTrace();
			System.out.println("WebDriverException");
			assert false;
		}
		return elements;

	}

	/**
	 * Wait till specific web element appears in the DOM.
	 *
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.
	 * @param maxWaitTime - maximum waiting time.
	 * @return It returns boolean value true or false.
	 * @example
	 * waitUntillElementAppear("XPATH","//button[@name='login']",100);
	 */
	@Override
	public boolean waitUntillElementAppear(String locatorType, String locatorValue,int maxWaitTime) {
		boolean status = true;
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		try {
			while (!(this.isElementPresent(locatorType, locatorValue))) {
				System.out.println("Waiting for Element[" + locatorValue + "] to be appear...");
				this.waitForElement(1);
				endTime = System.currentTimeMillis();
				if (endTime - startTime > maxWaitTime * 1000) {
					break;
				}
			}

		} catch (Exception e) {
			status = false;
			System.out.println("Element:: " + locatorValue + " is not appear within the specified timeout");
			e.printStackTrace();
			assert false;
		}
		return status;
	}

	/**
	 * Wait till specific web element disappears from the DOM.
	 *
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.
	 * @param maxWaitTime - maximum waiting time.
	 * @return It returns boolean value true or false.
	 * @example
	 * waitUntillElementDisappear("XPATH","//button[@name='login']",100);
	 */
	@Override
	public boolean waitUntillElementDisappear(String locatorType, String locatorValue,int maxWaitTime) {
		boolean status = true;
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		try {
			while ((this.isElementPresent(locatorType, locatorValue))) {
				System.out.println("Waiting for Element[" + locatorValue + "] to be disappear...");
				this.waitForElement(1);
				endTime = System.currentTimeMillis();
				if (endTime - startTime > maxWaitTime * 1000) {
					break;
				}
			}

		} catch (Exception e) {
			status = false;
			System.out.println("Element:: " + locatorValue + " is not disappear within the specified timeout");
			e.printStackTrace();
			assert false;
		}
		return status;
	}

	/**
	 * Perform navigation operation like page forward,back or refresh.
	 * 
	 * @param direction - This can have values like FORWARD,BACK, or REFRESH
	 * @return It does not returns anything.
	 * @example
	 * navigateTo("FORWARD");
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
			System.out.println("Unspported Direction: " + direction);
			assert false;

		}

	}

	/**
	 * Switch on the opened tab or window based on index value.
	 * 
	 * @param windowTabIndex - accept integer value
	 * @return It does not returns anything.
	 * @example
	 * switchToOpenedTabWindow(0);
	 */
	@Override
	public void switchToOpenedTabWindow(int windowTabIndex) {
		ArrayList<String> openWindows = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(openWindows.get(windowTabIndex));
	}

	/**
	 * Opens new tab or window and switch to it.
	 * 
	 * @param type - WINDOW=Opens new window
	 * 				TAB=Opens new tab
	 * @return It does not returns anything.
	 * @example
	 * createNewWindowTabSwitch("WINDOW");
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
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.d
	 * @param maxWaitTime - maximum waiting time.
	 * @return It does not return anything.
	 * @example
	 * hoverElement("XPATH","//button[@name='login']",100);
	 */
	@Override
	public void hoverElement(String locatorType, String locatorValue,int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue,maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			action.moveToElement(element).perform();
		} else {
			System.out.println("Unable to do hover: Web Element is not present");
			assert false;
		}

	}

	/**
	 * Perform mouse right click action on specific web element .
	 *
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.d
	 * @param maxWaitTime - maximum waiting time.
	 * @return It does not return anything.
	 * @example
	 * rightClickElement("XPATH","//button[@name='login']",100);
	 */
	@Override
	public void rightClickElement(String locatorType, String locatorValue,int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue,maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			action.contextClick(element).perform();
		} else {
			System.out.println("Unable to do right click: Web Element is not present");
			assert false;
		}

	}

	/**
	 * Perform mouse double click action on specific web element .
	 *
	 * @param locatorType - This can have values like ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.d
	 * @param maxWaitTime - maximum waiting time.
	 * @return It does not return anything.
	 * @example
	 * doubleClickElement("XPATH","//button[@name='login']",100);
	 */
	@Override
	public void doubleClickElement(String locatorType, String locatorValue,int maxWaitTime) {
		if (this.waitUntillElementAppear(locatorType, locatorValue,maxWaitTime)) {
			element = findElement(locatorType, locatorValue);
			action.doubleClick(element).perform();
		} else {
			System.out.println("Unable to do double : Web Element is not present");
			assert false;
		}

	}

	/**
	 * Gets title of web page
	 *
	 * @return It return title of page in string format.
	 * @example
	 * getPageTitle();
	 */
	@Override
	public String getPageTitle() {
		return driver.getTitle();
	}

	/**
	 * Switches back  to Parent Tab or Window or Iframe.
	 *
	 * @return It does not return anything.
	 * @example
	 * switchToParenTabWindowIframe();
	 */
	@Override
	public void switchToParenTabWindowIframe() {
		driver.switchTo().defaultContent();
	}

	/**
	 * Perform switch to iFrame.
	 *
	 * @param locatorType - This can have values like INDEX,NAMEORID,ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.d
	 * @return It does not return anything.
	 * @example
	 * switchFrame("INDEX","0");
	 */
	@Override
	public void switchFrame(String locatorType, String locatorValue) {

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
	 * @param locatorType - This can have values like INDEX,NAMEORID,ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param sourceLocatorValue - Here you need to pass locator value based on the locator type selected.This source web element.
	 * @param targetLocatorValue - Here you need to pass locator value based on the locator type selected.This target web element.
	 * @return It does not return anything.
	 * @example
	 * dragAndDrop("XPATH","//div[@id='source']","//div[@id='target']");
	 */
	@Override
	public void dragAndDrop(String locatorType, String sourceLocatorValue,String targetLocatorValue) {
		action.dragAndDrop(this.findElement(locatorType, sourceLocatorValue), this.findElement(locatorType, targetLocatorValue)).perform();
	}
	
	/**
	 * Perform keyboard typing action.
	 *
	 * @param locatorType - This can have values like INDEX,NAMEORID,ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue - Here you need to pass locator value based on the locator type selected.
	 * @param textToType - text to type.
	 * @return It does not return anything.
	 * @example
	 * typeUsingKeyboard("XPATH","//input[@id='username']","Welcome");
	 */
	@Override
	public void typeUsingKeyboard(String locatorType, String locatorValue,String textToType) {
		action.click(this.findElement(locatorType, locatorValue)).sendKeys(textToType).perform();
	}
	
	/**
	 * Perform keyboard action.
	 *
	 * @param combinationKeysName - CTRL+C,CTRL+V,ENTER,TAB,A,B,C,D. etc...
	 * @return It does not return anything.
	 * @example
	 * pressKeyCombination("CTRL,C");
	 * pressKeyCombination("ENTER");
	 */
	@Override
	public void pressKeyCombination(String combinationKeysName) {
		if(combinationKeysName.contains(",")) {
			String keys[]=combinationKeysName.split(",");
			action.keyDown(KeysName.getKey(keys[0])).sendKeys(keys[1]).keyUp(KeysName.getKey(keys[0])).perform();
		}else {
			action.sendKeys(KeysName.getKey(combinationKeysName)).perform();
		}
	}

}
