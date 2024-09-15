package io.github.shabryn2893.uidriverfactory;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * This manages Firefox browser.
 * 
 * @author shabbir rayeen
 */
public class FirefoxDriverManager extends DriverManager{

	/**
     * Default constructor for the FirefoxDriverManager class.
     * Initializes a new instance with default values.
     */
	public FirefoxDriverManager() {
		// Default constructor does not initialize fields
	}
	/**
	 * This launch Firefox browser.
	 * 
	 * @param isHeadlessMode It take boolean value to enable or disable Headless
	 *                       mode.
	 */
	@Override
	public void launchBrowser(boolean isHeadlessMode) {
		FirefoxOptions options= new FirefoxOptions();
		options.setAcceptInsecureCerts(true);
		options.addArguments("--incognito");
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		if(isHeadlessMode)
			options.addArguments("-headless");
		driver=new FirefoxDriver(options);
		
	}

}
