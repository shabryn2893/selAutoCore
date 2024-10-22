package io.github.shabryn2893.uicore;

/**
 * Provides reusable UI automation functions for use across the framework.
 * This interface defines methods for interacting with browser windows, elements, and performing 
 * common actions like clicks, navigation, waits, and more.
 * 
 * @author Shabbir Rayeen
 */
public interface IActionUI {

    /**
     * Maximizes the browser window to full screen.
     */
    public void maximizeScreen();

    /**
     * Deletes all browser cookies.
     */
    public void deleteAllCookies();

    /**
     * Closes the current browser tab or window.
     */
    public void closeCurrentTabWindow();

    /**
     * Closes the browser window.
     */
    public void closeBrowser();

    /**
     * Opens a URL in the browser window.
     * 
     * @param url the URL to open in the browser.
     */
    public void openURL(String url);

    /**
     * Clicks on a web element using the specified locator.
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator.
     * @param maxWaitTime  the maximum time to wait for the element to become clickable.
     */
    public void click(String locatorType, String locatorValue, int maxWaitTime);

    /**
     * Enters text into a web element, typically a text box or text area.
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator.
     * @param textToEnter  the text to enter into the element.
     * @param maxWaitTime  the maximum time to wait for the element to be ready for input.
     */
    public void type(String locatorType, String locatorValue, String textToEnter, int maxWaitTime);

    /**
     * Waits for the page to fully load within the specified time.
     *
     * @param timeInSeconds the maximum wait time in seconds for the page to load.
     */
    public void waitForPageLoad(int timeInSeconds);

    /**
     * Pauses execution for the specified number of seconds (hard wait).
     *
     * @param seconds the number of seconds to wait.
     */
    public void waitForElement(int seconds);

    /**
     * Checks if a web element is displayed, enabled, or selected.
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator.
     * @param stateType    the state to check for (e.g., DISPLAYED, ENABLED, SELECTED).
     * @return true if the element is in the specified state, false otherwise.
     */
    public boolean isElementDisplayedOrEnabledOrSelected(String locatorType, String locatorValue, String stateType);

    /**
     * Gets the value of a specified attribute from a web element.
     *
     * @param locatorType   the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue  the value of the locator.
     * @param attributeName the name of the attribute to retrieve.
     * @param maxWaitTime   the maximum time to wait for the element.
     * @return the value of the specified attribute.
     */
    public String getAttributeValue(String locatorType, String locatorValue, String attributeName, int maxWaitTime);

    /**
     * Gets the current URL of the page.
     *
     * @return the current URL as a string.
     */
    public String getURL();

    /**
     * Takes a screenshot of the visible portion of the webpage.
     *
     * @param screenshotPath the path where the screenshot will be saved.
     * @return the screenshot in Base64 format.
     */
    public String takeScreenshot(String screenshotPath);

    /**
     * Takes a screenshot of a specific web element.
     *
     * @param locatorType    the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue   the value of the locator.
     * @param screenshotPath the path where the screenshot will be saved.
     * @return the screenshot in Base64 format.
     */
    public String takeScreenshot(String locatorType, String locatorValue, String screenshotPath);

    /**
     * Performs a click action using JavaScript.
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator.
     * @param maxWaitTime  the maximum time to wait for the element to become clickable.
     */
    public void jsClick(String locatorType, String locatorValue, int maxWaitTime);

    /**
     * Gets the visible text of a web element.
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator.
     * @param maxWaitTime  the maximum time to wait for the element to be visible.
     * @return the text of the element.
     */
    public String getText(String locatorType, String locatorValue, int maxWaitTime);

    /**
     * Scrolls to a specific web element.
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator.
     * @param scrollType   the type of scrolling to perform (e.g., NORMAL, JS).
     * @param maxWaitTime  the maximum time to wait for the element to be scrollable.
     */
    public void scrollToElement(String locatorType, String locatorValue, String scrollType, int maxWaitTime);

    /**
     * Checks if a web element is present in the DOM.
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator.
     * @return true if the element is present, false otherwise.
     */
    public boolean isElementPresent(String locatorType, String locatorValue);

    /**
     * Waits until a specific web element appears in the DOM.
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator.
     * @param maxWaitTime  the maximum time to wait for the element.
     * @return true if the element appears within the specified time, false otherwise.
     */
    public boolean waitUntillElementAppear(String locatorType, String locatorValue, int maxWaitTime);

    /**
     * Waits until a specific web element disappears from the DOM.
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator.
     * @param maxWaitTime  the maximum time to wait for the element to disappear.
     * @return true if the element disappears within the specified time, false otherwise.
     */
    public boolean waitUntillElementDisappear(String locatorType, String locatorValue, int maxWaitTime);

    /**
     * Performs a navigation action (e.g., forward, back, or refresh).
     *
     * @param direction the direction of navigation (FORWARD, BACK, or REFRESH).
     */
    public void navigateTo(String direction);
    
    /**
     * Switches to a new window or tab when an element is clicked.
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator for the element to be clicked.
     * @param maxWaitTime  the maximum time to wait for the element to be clickable before switching to the new window/tab.
     */
    public void switchToNewWindowTabWhenClicked(String locatorType, String locatorValue, int maxWaitTime);

    /**
     * Switches to an open tab or window based on the index.
     *
     * @param windowTabIndex the index of the window or tab to switch to.
     */
    public void switchToOpenedTabWindow(int windowTabIndex);

    /**
     * Opens a new tab or window and switches to it.
     *
     * @param type the type of window to open (WINDOW or TAB).
     */
    public void createNewWindowTabSwitch(String type);

    /**
     * Performs a mouse hover action on a web element.
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator.
     * @param maxWaitTime  the maximum time to wait for the hover action.
     */
    public void hoverElement(String locatorType, String locatorValue, int maxWaitTime);

    /**
     * Performs a right-click action on a web element.
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator.
     * @param maxWaitTime  the maximum time to wait for the right-click action.
     */
    public void rightClickElement(String locatorType, String locatorValue, int maxWaitTime);

    /**
     * Performs a double-click action on a web element.
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator.
     * @param maxWaitTime  the maximum time to wait for the double-click action.
     */
    public void doubleClickElement(String locatorType, String locatorValue, int maxWaitTime);

    /**
     * Retrieves the title of the current page.
     *
     * @return the title of the current page as a string.
     */
    public String getPageTitle();

    /**
     * Switches the focus to the parent tab or window and then switches to the iframe within that tab or window.
     */
    public void switchToParenTabWindowIframe();

    /**
     * Drags an element from a source location and drops it onto a target location.
     *
     * @param locatorType        the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param sourceLocatorValue the value of the locator for the source element.
     * @param targetLocatorValue the value of the locator for the target element.
     */
    public void dragAndDrop(String locatorType, String sourceLocatorValue, String targetLocatorValue);

    /**
     * Enters text into a web element using keyboard actions.
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator for the target element.
     * @param maxWaitTime  the maximum time to wait for the element to be ready.
     * @param textToType   the text to type using the keyboard.
     */
    public void typeUsingKeyboard(String locatorType, String locatorValue, int maxWaitTime, String textToType);

    /**
     * Presses a combination of keys, such as Ctrl+C or Ctrl+V.
     *
     * @param combinationKeysName the name of the key combination (e.g., "CTRL+C", "CTRL+V").
     */
    public void pressKeyCombination(String combinationKeysName);

    /**
     * Selects a value from a dropdown list by the given method (value, index, or visible text).
     *
     * @param locatorType  the type of locator (e.g., ID, XPATH, CSS, etc.).
     * @param locatorValue the value of the locator for the dropdown element.
     * @param type         the method for selecting the value (e.g., "VALUE", "INDEX", "VISIBLE_TEXT").
     * @param value        the value to select from the dropdown.
     */
    public void selectFromDropdown(String locatorType, String locatorValue, String type, String value);
    
    /**
     * Switches to a specific iframe identified by the given locator or id or index.
     *
     * This method allows you to switch the context of the driver to the specified iframe
     * so that subsequent actions can be performed within that frame.
     *
     * @param locatorType  the type of locator used to identify the iframe (e.g., "CSS", "XPath").
     * @param locatorValue the value of the locator to find the iframe (e.g., the actual CSS selector or XPath expression).
     * @throws RuntimeException if the iframe is not found, cannot be switched to, or if the specified locator is invalid.
     */
    public void switchToFrame(String locatorType, String locatorValue);
    
    /**
     * Executes a JavaScript action in the web context.
     *
     * This method allows for the execution of arbitrary JavaScript code within the current page context. 
     * The script can perform various tasks such as manipulating the DOM, retrieving values, 
     * or executing functions defined on the page.
     *
     * @param script The JavaScript code to execute. This should be a valid JavaScript expression or statement.
     * @param args   Optional arguments to pass to the script. These can be used within the script 
     *               to provide dynamic values or to manipulate the execution context.
     * @return The result of the script execution. The type of the result may vary depending on the script executed. 
     *         If the script returns a value, it will be returned; otherwise, null will be returned.
     * @throws RuntimeException If there is an error executing the script, such as a syntax error 
     *                         or an issue with the execution context.
     */
    public Object executeJSAction(String script, Object... args);

}
