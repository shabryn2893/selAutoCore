package io.github.shabryn2893.uidriverfactory;

import java.util.List;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * This manages Chrome browser.
 * 
 * @author shabbir rayeen
 */
public class ChromeDriverManager extends DriverManager {

	/**
     * Default constructor for the ChromeDriverManager class.
     * Initializes a new instance with default values.
     */
	public ChromeDriverManager() {
		// Default constructor does not initialize fields
	}
	/**
	 * This launch Chrome browser.
	 * 
	 * @param isHeadlessMode It take boolean value to enable or disable Headless
	 *                       mode.
	 */
	@Override
	public void launchBrowser(boolean isHeadlessMode) {
		ChromeOptions options = new ChromeOptions();
		options.setAcceptInsecureCerts(true);
		options.addArguments("--incognito");
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		options.setExperimentalOption("excludeSwitches", List.of("disable-popup-blocking"));
		if (isHeadlessMode)
			options.addArguments("--headless");
		driver = new ChromeDriver(options);
	}

}
