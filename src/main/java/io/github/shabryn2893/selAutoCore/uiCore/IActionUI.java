package io.github.shabryn2893.selAutoCore.uiCore;


import java.util.List;

import org.openqa.selenium.WebElement;

public interface IActionUI {

	public void initializeDriver(String browserType, boolean isHeadlessMode);

	public void closeCurrentTabWindow();

	public void closeBrowser();

	public void openURL(String url);

	public void click(String locatorType, String locatorValue, int maxWaitTime);

	public void type(String locatorType, String locatorValue, String textToEnter, int maxWaitTime);

	public void waitForElement(int seconds);

	public boolean isElementDisplayedOrEnabledOrSelected(String locatorType, String locatorValue, String state);

	public boolean isElementPresent(String locatorType, String locatorValue);

	public String getAttributeValue(String locatorType, String locatorValue, String attributeName, int maxWaitTime);

	public String getText(String locatorType, String locatorValue, int maxWaitTime);

	public String getURL();

	public String takeScreenshot(String screenshotPath);

	public String takeScreenshot(String locatorType, String locatorValue, String screenshotPath);

	public void jsClick(String locatorType, String locatorValue, int maxWaitTime);

	public WebElement findElement(String locatorType, String locatorValue);

	public List<WebElement> findElements(String locatorType, String locatorValue);

	public void waitUntill(String locatorType, final String locatorValue, final String conditionName, int maxWaitTime);

	public void waitForPageLoad(int timeInSeconds);

	public void scrollToElement(String locatorType, String locatorValue, String scrollType, int maxWaitTime);

	public boolean waitUntillElementAppear(String locatorType, String locatorValue, int maxWaitTime);

	public boolean waitUntillElementDisappear(String locatorType, String locatorValue, int maxWaitTime);

	public void navigateTo(String direction);

	public void switchToOpenedTabWindow(int windowTabIndex);

	public void createNewWindowTabSwitch(String type);

	public void hoverElement(String locatorType, String locatorValue, int maxWaitTime);

	public void rightClickElement(String locatorType, String locatorValue, int maxWaitTime);

	public void doubleClickElement(String locatorType, String locatorValue, int maxWaitTime);

	public String getPageTitle();

	public void switchToParenTabWindowIframe();

	public void switchFrame(String locatorType, String locatorValue);

	public void dragAndDrop(String locatorType, String sourcelocatorValue, String targetlocatorValue);

	public void typeUsingKeyboard(String locatorType, String locatorValue, String textToType);

	public void pressKeyCombination(String combinationKeysName);

}
