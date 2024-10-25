package io.github.shabryn2893.uicore;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import com.microsoft.playwright.Page;
import io.github.shabryn2893.utils.LoggerUtils;

/**
 * Utility class for handling keyboard key mappings across different automation
 * frameworks, including Selenium and Playwright. Provides methods to retrieve
 * key mappings for both frameworks.
 */
public class KeysName {

	private static final Logger logger = LoggerUtils.getLogger(KeysName.class);

	// Private constructor to prevent instantiation of utility class
	private KeysName() {
	}

	/**
	 * Retrieves the corresponding Selenium {@link Keys} enum value for a given key
	 * name.
	 * 
	 * @param keyName the name of the key (e.g., "ENTER", "TAB", "SHIFT").
	 * @return the corresponding {@link Keys} enum value or {@code null} if the key
	 *         is unsupported.
	 */
	private static Keys getSeleniumKey(String keyName) {
		try {
			return Keys.valueOf(keyName.toUpperCase());
		} catch (IllegalArgumentException e) {
			if (logger.isErrorEnabled()) {
				logger.error("Unsupported Selenium Key Name: {}", keyName);
			}
			return null;
		}
	}

	/**
	 * Retrieves the corresponding Playwright key string for a given key name.
	 * 
	 * @param keyName the name of the key (e.g., "ENTER", "TAB", "SHIFT").
	 * @return the corresponding key string or {@code null} if the key is
	 *         unsupported.
	 */
	private static String getPlaywrightKey(String keyName) {
		switch (keyName.toUpperCase()) {
		case "ENTER":
			return "Enter";
		case "TAB":
			return "Tab";
		case "SHIFT":
			return "Shift";
		case "CTRL":
			return "Control";
		case "ALT":
			return "Alt";
		case "ESCAPE":
			return "Escape";
		default:
			if (logger.isErrorEnabled()) {
				logger.error("Unsupported Playwright Key Name: {}", keyName);
			}
			return null;
		}
	}

	/**
	 * Retrieves the key mapping (either Selenium or Playwright) based on the
	 * provided driver type. If the driver object is an instance of {@link Page},
	 * Playwright key mappings will be returned. If the driver object is an instance
	 * of {@link WebDriver}, Selenium key mappings will be returned.
	 * 
	 * @param driverObject the driver object (either {@link Page} for Playwright or
	 *                     {@link WebDriver} for Selenium).
	 * @param keyName      the name of the key (e.g., "ENTER", "TAB", "SHIFT").
	 * @return the corresponding key mapping as a string or {@code null} if the key
	 *         or driver type is unsupported.
	 */
	public static String getKey(Object driverObject, String keyName) {
		if (driverObject instanceof Page) {
			return getPlaywrightKey(keyName);
		} else if (driverObject instanceof WebDriver) {
			Keys seleniumKey = getSeleniumKey(keyName);
			return seleniumKey != null ? seleniumKey.name() : null;
		} else {
			if (logger.isErrorEnabled()) {
				logger.error("Unsupported driver type: {} for key operations.", driverObject.getClass().getName());
			}
			return null;
		}
	}
}
