package io.github.shabryn2893.uicore;

import java.util.List;

import org.openqa.selenium.WebElement;

/**
 * Reusable function across framework for UI Automation.
 * 
 * @author shabbir rayeen
 */
public interface IActionUI {

	/**
	 * Initiate browser based on the parameter passed
	 *
	 * @param browserType    - This can accept values like CHROME,FIREFOX or EDGE.
	 * @param isHeadlessMode - This can accept values like true or false.
	 * 
	 *                       initializeDriver("CHROME",false);
	 */
	public void initializeDriver(String browserType, boolean isHeadlessMode);

	/**
	 * Close the current opened tab or window.
	 * 
	 * closeCurrentTabWindow();
	 */
	public void closeCurrentTabWindow();

	/**
	 * Close the browse window.
	 *
	 * 
	 * 
	 * closeBrowser();
	 */
	public void closeBrowser();

	/**
	 * Open the url in the browser window.
	 * 
	 * @param url - This take application url as parameter
	 * 
	 * 
	 *            openURL("https://www.google.com");
	 */
	public void openURL(String url);

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
	public void click(String locatorType, String locatorValue, int maxWaitTime);

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
	public void type(String locatorType, String locatorValue, String textToEnter, int maxWaitTime);

	/**
	 * Wait any web element until specific condition is matched.
	 *
	 * @param locatorType   - This can have values like
	 *                      ID,CLASS,TAG,XPATH,LINKTEXT,CSS,NAME,PARTIALLINKTEXT,SHADOWDOM
	 * @param locatorValue  - Here you need to pass locator value based on the
	 *                      locator type selected.
	 * @param conditionName - This can values like CLICKABLE,INVISIBLE,VISIBLE or
	 *                      SELECTED
	 * @param maxWaitTime   -maximum waiting to match specific condition.
	 * 
	 * 
	 *                      waitUntill("XPATH","//button[@name='login']","VISIBLE",100);
	 */
	public void waitUntill(String locatorType, final String locatorValue, final String conditionName, int maxWaitTime);

	/**
	 * Wait for the page load.
	 *
	 * @param timeInSeconds - waiting time for page load.
	 * 
	 * 
	 *                      waitForPageLoad(100);
	 */
	public void waitForPageLoad(int timeInSeconds);

	/**
	 * Wait for the page load.
	 *
	 * @param seconds - hard wait time for web element. waitForElement(10);
	 */
	public void waitForElement(int seconds);

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
	public boolean isElementDisplayedOrEnabledOrSelected(String locatorType, String locatorValue, String stateType);

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
	public String getAttributeValue(String locatorType, String locatorValue, String attributeName, int maxWaitTime);

	/**
	 * Gets the current URL.
	 * 
	 * @return It returns String.
	 * 
	 *         getURL();
	 */
	public String getURL();

	/**
	 * Takes the screenshot of the visible web page.
	 * 
	 * @param screenshotPath -location where to store screenshot
	 * @return It returns screenshot in BASE64 format.
	 * 
	 *         takeScreenshot("./screenshot/abc.jpeg");
	 */
	public String takeScreenshot(String screenshotPath);

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
	public String takeScreenshot(String locatorType, String locatorValue, String screenshotPath);

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
	public void jsClick(String locatorType, String locatorValue, int maxWaitTime);

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
	public String getText(String locatorType, String locatorValue, int maxWaitTime);

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
	public void scrollToElement(String locatorType, String locatorValue, String scrollType, int maxWaitTime);

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
	public boolean isElementPresent(String locatorType, String locatorValue);

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
	public WebElement findElement(String locatorType, String locatorValue);

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
	public List<WebElement> findElements(String locatorType, String locatorValue);

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
	public boolean waitUntillElementAppear(String locatorType, String locatorValue, int maxWaitTime);

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
	public boolean waitUntillElementDisappear(String locatorType, String locatorValue, int maxWaitTime);

	/**
	 * Perform navigation operation like page forward,back or refresh.
	 * 
	 * @param direction - This can have values like FORWARD,BACK, or REFRESH
	 * 
	 * 
	 *                  navigateTo("FORWARD");
	 */
	public void navigateTo(String direction);

	/**
	 * Switch on the opened tab or window based on index value.
	 * 
	 * @param windowTabIndex - accept integer value
	 * 
	 * 
	 *                       switchToOpenedTabWindow(0);
	 */
	public void switchToOpenedTabWindow(int windowTabIndex);

	/**
	 * Opens new tab or window and switch to it.
	 * 
	 * @param type - WINDOW=Opens new window TAB=Opens new tab
	 * 
	 * 
	 *             createNewWindowTabSwitch("WINDOW");
	 */
	public void createNewWindowTabSwitch(String type);

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
	public void hoverElement(String locatorType, String locatorValue, int maxWaitTime);

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
	public void rightClickElement(String locatorType, String locatorValue, int maxWaitTime);

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
	public void doubleClickElement(String locatorType, String locatorValue, int maxWaitTime);

	/**
	 * Gets title of web page
	 *
	 * @return It return title of page in string format.
	 * 
	 *         getPageTitle();
	 */
	public String getPageTitle();

	/**
	 * Switches back to Parent Tab or Window or Iframe.
	 *
	 * 
	 * switchToParenTabWindowIframe();
	 */
	public void switchToParenTabWindowIframe();

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
	public void switchFrame(String locatorType, String locatorValue);

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
	public void dragAndDrop(String locatorType, String sourceLocatorValue, String targetLocatorValue);

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
	public void typeUsingKeyboard(String locatorType, String locatorValue, String textToType);

	/**
	 * Perform keyboard action.
	 *
	 * @param combinationKeysName - CTRL+C,CTRL+V,ENTER,TAB,A,B,C,D. etc...
	 * 
	 *                            pressKeyCombination("CTRL,C");
	 *                            pressKeyCombination("ENTER");
	 */
	public void pressKeyCombination(String combinationKeysName);

}
