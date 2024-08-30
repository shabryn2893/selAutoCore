package io.github.shabryn2893.selAutoCore.core;

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

import io.github.shabryn2893.selAutoCore.driverFactory.DriverManager;
import io.github.shabryn2893.selAutoCore.driverFactory.DriverManagerFactory;

public class UiActionsSelenium implements IActionUI {

	private DriverManager driverManager;
	private WebDriver driver;
	private static WebElement element = null;
	//private int maxWaitTime = ConfigProp.MAX_WAIT_TIME;
	private JavascriptExecutor jExecutor = null;
	private Actions action=null;

	@Override
	public void initializeDriver(String browserType, boolean isHeadlessMode) {
		driverManager = DriverManagerFactory.getManager(browserType);
		driver = driverManager.getDriver(isHeadlessMode);
		jExecutor = (JavascriptExecutor) driver;
		action=new Actions(driver);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	@Override
	public void closeCurrentTabWindow() {
		driver.close();
	}

	@Override
	public void closeBrowser() {
		driverManager.quitDriver();
	}

	@Override
	public void openURL(String url) {
		System.out.println("Opening url:" + url);
		driver.get(url);
	}

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

	public void setFluentWait(Function<WebDriver, Boolean> function,int maxWaitTime) {
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
		fluentWait.pollingEvery(Duration.ofMillis(500));
		fluentWait.withTimeout(Duration.ofSeconds(maxWaitTime));
		fluentWait.ignoring(NoSuchElementException.class);
		fluentWait.until(function);
	}

	public void setWebDriverWait(Function<WebDriver, Boolean> function, int maxWaitTime) {
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitTime));
		wait.until(function);

	}
	
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

	@Override
	public String getURL() {
		return driver.getCurrentUrl();

	}

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

	@Override
	public void switchToOpenedTabWindow(int windowTabIndex) {
		ArrayList<String> openWindows = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(openWindows.get(windowTabIndex));
	}

	@Override
	public void createNewWindowTabSwitch(String type) {
		if (type.equalsIgnoreCase("WINDOW")) {
			driver.switchTo().newWindow(WindowType.WINDOW);
		} else {
			driver.switchTo().newWindow(WindowType.TAB);
		}
	}

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

	@Override
	public String getPageTitle() {
		return driver.getTitle();
	}

	@Override
	public void switchToParenTabWindowIframe() {
		driver.switchTo().defaultContent();
	}

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
	
	@Override
	public void dragAndDrop(String locatorType, String sourceLocatorValue,String targetLocatorValue) {
		action.dragAndDrop(this.findElement(locatorType, sourceLocatorValue), this.findElement(locatorType, targetLocatorValue)).perform();
	}
	
	@Override
	public void typeUsingKeyboard(String locatorType, String locatorValue,String textToType) {
		action.click(this.findElement(locatorType, locatorValue)).sendKeys(textToType).perform();
	}
	
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
