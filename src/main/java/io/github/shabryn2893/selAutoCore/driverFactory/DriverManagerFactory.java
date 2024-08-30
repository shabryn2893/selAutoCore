package io.github.shabryn2893.selAutoCore.driverFactory;

public class DriverManagerFactory {
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
