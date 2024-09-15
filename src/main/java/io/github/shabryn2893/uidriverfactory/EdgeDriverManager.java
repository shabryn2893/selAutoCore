package io.github.shabryn2893.uidriverfactory;

import java.util.List;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
/**
 * This manages Edge browser.
 * 
 * @author shabbir rayeen
 */
public class EdgeDriverManager extends DriverManager{

	/**
     * Default constructor for the EdgeDriverManager class.
     * Initializes a new instance with default values.
     */
	public EdgeDriverManager() {
		// Default constructor does not initialize fields
	}
	/**
	 * This launch Edge browser.
	 * 
	 * @param isHeadlessMode It take boolean value to enable or disable Headless
	 *                       mode.
	 */
	@Override
	public void launchBrowser(boolean isHeadlessMode) {
		EdgeOptions options= new EdgeOptions();
		options.setAcceptInsecureCerts(true);
		options.addArguments("--incognito");
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		options.setExperimentalOption("excludeSwitches", List.of("disable-popup-blocking"));
		if(isHeadlessMode)
			options.addArguments("--headless");
		driver=new EdgeDriver(options);
		
	}

}
