package io.github.shabryn2893.locatorfactory;

import java.util.List;

/**
 * Interface for locating web elements in a generic way.
 * 
 * @param <T> the type of the element to be located (e.g., WebElement in
 *            Selenium).
 */
public interface ElementLocator<T> {

	/**
	 * Locates a single element based on the specified locator type and value.
	 *
	 * @param locatorType  the type of the locator (e.g., "id", "xpath",
	 *                     "cssSelector").
	 * @param locatorValue the value of the locator (e.g., the actual ID, XPath
	 *                     expression, or CSS selector).
	 * @return the located element of type T, or null if no element is found.
	 */
	T locateElement(String locatorType, String locatorValue);

	/**
	 * Locates multiple elements based on the specified locator type and value.
	 *
	 * @param locatorType  the type of the locator (e.g., "id", "xpath",
	 *                     "cssSelector").
	 * @param locatorValue the value of the locator (e.g., the actual ID, XPath
	 *                     expression, or CSS selector).
	 * @return a list of located elements of type T, or an empty list if no elements
	 *         are found.
	 */
	List<T> locateElements(String locatorType, String locatorValue);
}
