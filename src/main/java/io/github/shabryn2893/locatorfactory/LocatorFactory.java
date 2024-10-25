package io.github.shabryn2893.locatorfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Factory class for creating element locators for different web automation
 * frameworks. Supports both Playwright and Selenium by returning the
 * appropriate element locator implementation.
 */
public class LocatorFactory {

	private LocatorFactory() {
	}

	/**
	 * Creates an instance of {@link ElementLocator} for Playwright.
	 *
	 * @param page the Playwright {@link Page} instance used for locating elements.
	 * @return an {@link ElementLocator} instance configured for Playwright, using
	 *         {@link Locator}.
	 */
	public static ElementLocator<Locator> getLocator(Page page) {
		return new PlaywrightElementLocator(page);
	}

	/**
	 * Creates an instance of {@link ElementLocator} for Selenium.
	 *
	 * @param driver the Selenium {@link WebDriver} instance used for locating
	 *               elements.
	 * @return an {@link ElementLocator} instance configured for Selenium, using
	 *         {@link WebElement}.
	 */
	public static ElementLocator<WebElement> getLocator(WebDriver driver) {
		return new SeleniumElementLocator(driver);
	}
}
