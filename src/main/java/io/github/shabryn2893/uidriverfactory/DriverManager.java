package io.github.shabryn2893.uidriverfactory;

import io.github.shabryn2893.uicore.IActionUI;

/**
 * The {@code DriverManager} class is an abstract class responsible for managing
 * browser driver creation. It defines the contract for creating a browser by
 * implementing the {@link IActionUI} interface.
 * 
 * <p>
 * Subclasses are expected to provide specific implementations for different
 * browsers.
 * </p>
 * 
 * Example Usage:
 * 
 * <pre>{@code
 * public class ChromeDriverManager extends DriverManager {
 * 	@Override
 * 	public IActionUI createBrowser() {
 * 		// Implementation for Chrome browser
 * 	}
 * }
 * }</pre>
 * 
 */
public abstract class DriverManager {

	/**
	 * Creates and returns an instance of a browser driver.
	 * 
	 * <p>
	 * This method must be implemented by subclasses to provide the specific logic
	 * for creating different browser drivers.
	 * </p>
	 * 
	 * @return an {@link IActionUI} instance representing the browser driver.
	 */
	public abstract IActionUI createBrowser();
}
