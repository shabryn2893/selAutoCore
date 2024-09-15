package io.github.shabryn2893.uidriverfactory;

/**
 *
 * Create a instance of ChromeDriverManager or FirefoxDriverManager or
 * EdgeDriverManager or SafariDriverManager based on the browser type input
 */
public class DriverManagerFactory {
	
	private DriverManagerFactory() {}
	/**
	 * Get instance of ChromeDriverManager or FirefoxDriverManager or
	 * EdgeDriverManager or SafariDriverManager based on the browser type input
	 * 
	 * @param browserType This can have values like CHROME,FIREFOX or EDGE
	 * @return DriverManager reference
	 */
	public static DriverManager getManager(String browserType) {
		DriverManager driverManager;
		switch (browserType.toUpperCase()) {
		case "CHROME":
			driverManager = new ChromeDriverManager();
			break;
		case "FIREFOX":
			driverManager = new FirefoxDriverManager();
			break;
		case "EDGE":
			driverManager = new EdgeDriverManager();
			break;
		default:
			driverManager = new SafariDriverManager();
			break;
		}
		return driverManager;
	}

}
