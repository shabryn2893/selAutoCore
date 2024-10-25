package io.github.shabryn2893.locatorfactory;

import java.util.List;
import org.slf4j.Logger;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;

import io.github.shabryn2893.utils.LoggerUtils;

/**
 * Implementation of the {@link ElementLocator} interface for locating elements
 * using Playwright.
 */
public class PlaywrightElementLocator implements ElementLocator<Locator> {

	private static final Logger logger = LoggerUtils.getLogger(PlaywrightElementLocator.class);
	private Page page;

	/**
	 * Constructs a PlaywrightElementLocator with the specified Playwright Page.
	 * 
	 * @param page the Playwright {@link Page} instance used for locating elements.
	 */
	public PlaywrightElementLocator(Page page) {
		this.page = page;
	}

	/**
	 * Locates a single element based on the specified locator type and value.
	 *
	 * @param locatorType  the type of the locator (e.g., "XPATH", "ID", "LABEL").
	 * @param locatorValue the value of the locator (e.g., the actual XPath
	 *                     expression, ID).
	 * @return the located {@link Locator}, or null if no element is found.
	 */
	@Override
	public Locator locateElement(String locatorType, String locatorValue) {
		Locator locator = null;
		try {
			locator = findElement(locatorType, locatorValue);
		} catch (TimeoutError e) {
			logger.info("TimeoutError: {}", e.getMessage());
			assert false;
		}
		return locator;
	}

	/**
	 * Locates multiple elements based on the specified locator type and value.
	 *
	 * @param locatorType  the type of the locator (e.g., "XPATH", "ID", "LABEL").
	 * @param locatorValue the value of the locator (e.g., the actual XPath
	 *                     expression, ID).
	 * @return a list of located {@link Locator} instances, or an empty list if no
	 *         elements are found.
	 */
	@Override
	public List<Locator> locateElements(String locatorType, String locatorValue) {
		List<Locator> locators = null;
		try {
			locators = findElements(locatorType, locatorValue);
		} catch (TimeoutError e) {
			logger.info("TimeoutError: {}", e.getMessage());
			assert false;
		}
		return locators;
	}

	/**
	 * Helper method to find a single element based on the locator type and value.
	 * 
	 * @param locatorType  the type of the locator.
	 * @param locatorValue the value of the locator.
	 * @return the found {@link Locator}.
	 */
	private Locator findElement(String locatorType, String locatorValue) {
		switch (locatorType.toUpperCase()) {
		case "XPATH":
			return this.page.locator(locatorValue);
		case "ID":
			return this.page.locator("#" + locatorValue);
		case "LABEL":
			return this.page.getByLabel(locatorValue);
		case "TEXT":
			return this.page.getByText(locatorValue);
		case "ALTTEXT":
			return this.page.getByAltText(locatorValue);
		case "TITLE":
			return this.page.getByTitle(locatorValue);
		case "TESTID":
			return this.page.getByTestId(locatorValue);
		case "PLACEHOLDER":
			return this.page.getByPlaceholder(locatorValue);
		default:
			logger.error("Unsupported locator type: {}", locatorType);
			return null;
		}
	}

	/**
	 * Helper method to find multiple elements based on the locator type and value.
	 * 
	 * @param locatorType  the type of the locator.
	 * @param locatorValue the value of the locator.
	 * @return a list of found {@link Locator} instances.
	 */
	private List<Locator> findElements(String locatorType, String locatorValue) {
		switch (locatorType.toUpperCase()) {
		case "XPATH":
			return this.page.locator(locatorValue).all();
		case "ID":
			return this.page.locator("#" + locatorValue).all();
		case "LABEL":
			return this.page.getByLabel(locatorValue).all();
		case "TEXT":
			return this.page.getByText(locatorValue).all();
		case "ALTTEXT":
			return this.page.getByAltText(locatorValue).all();
		case "TITLE":
			return this.page.getByTitle(locatorValue).all();
		case "TESTID":
			return this.page.getByTestId(locatorValue).all();
		case "PLACEHOLDER":
			return this.page.getByPlaceholder(locatorValue).all();
		default:
			logger.error("Unsupported locator type: {}", locatorType);
			return List.of(); // Return an empty list if the type is unsupported
		}
	}
}
