package io.github.shabryn2893.uidriverfactory;

/**
 * A factory class for creating instances of {@link DriverManager} based on the
 * specified browser type and automation tool.
 * <p>
 * This class provides a static method to retrieve a {@link DriverManager}
 * instance that can be used to manage browser sessions for either Selenium or
 * Playwright, allowing for flexible test automation.
 * </p>
 */
public class DriverFactory {

	/**
	 * Private constructor to prevent instantiation of the DriverFactory class.
	 */
	private DriverFactory() {
	}

	/**
	 * Gets an instance of {@link DriverManager} based on the provided browser type,
	 * tool name, and headless mode setting.
	 *
	 * @param browserType the type of browser to be used (e.g., "chrome",
	 *                    "firefox").
	 * @param toolName    the name of the automation tool to use (e.g., "SELENIUM",
	 *                    "PLAYWRIGHT").
	 * @param headless    a boolean indicating whether to run the browser in
	 *                    headless mode.
	 * @return an instance of {@link DriverManager} corresponding to the specified
	 *         tool.
	 * @throws IllegalArgumentException if the provided tool name is not recognized.
	 */
	public static DriverManager getDriver(String browserType, String toolName, boolean headless) {
		if (toolName.equalsIgnoreCase("SELENIUM")) {
			return new SeleniumDriverFactory(browserType, headless);
		} else {
			return new PlaywrightDriverFactory(browserType, headless);
		}
	}
}
