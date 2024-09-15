package io.github.shabryn2893.uidriverfactory;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

/**
 * This manages Safari browser.
 * 
 * @author shabbir rayeen
 */
public class SafariDriverManager extends DriverManager{

	/**
     * Default constructor for the SafariDriverManager class.
     * Initializes a new instance with default values.
     */
	public SafariDriverManager() {
		// Default constructor does not initialize fields
	}
	/**
	 * This launch Safari browser.
	 * 
	 * @param isHeadlessMode It take boolean value to enable or disable Headless
	 *                       mode.
	 */
	@Override
	public void launchBrowser(boolean isHeadlessMode) {
		SafariOptions options= new SafariOptions();
		options.setAcceptInsecureCerts(true);
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		driver=new SafariDriver(options);
		
	}

}
