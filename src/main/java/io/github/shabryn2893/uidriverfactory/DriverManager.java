package io.github.shabryn2893.uidriverfactory;

import org.openqa.selenium.WebDriver;

/**
 * This class manages different browser.
 * 
 * @author shabbir rayeen
 */
public abstract class DriverManager {

	protected WebDriver driver;

	/**
     * Default constructor for the DriverManager class.
     * Initializes a new instance with default values.
     */
	protected DriverManager() {
		// Default constructor does not initialize fields
	}
	/**
	 * Initiates the browser
	 * 
	 * @param isHeadlessMode It take boolean value to enable or disable Headless
	 *                       mode.
	 */
	public abstract void launchBrowser(boolean isHeadlessMode);

	/**
	 * Close the browser window
	 */
	public void quitDriver() {
		if (null != driver) {
			driver.quit();
			driver = null;
		}

	}

	/**
	 * Get the instance of the browser
	 * 
	* @param isHeadlessMode It take boolean value to enable or disable Headless
	 *                       mode.
	 * @return it return the driver instance
	 */
	public WebDriver getDriver(boolean isHeadlessMode) {
		if (null == driver) {
			launchBrowser(isHeadlessMode);
		}
		return driver;
	}

}
