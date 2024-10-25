package io.github.shabryn2893.uidriverfactory;

import org.slf4j.Logger;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightException;

import io.github.shabryn2893.uicore.IActionUI;
import io.github.shabryn2893.uicore.UIActionsPlaywright;
import io.github.shabryn2893.utils.LoggerUtils;

/**
 * The {@code PlaywrightDriverFactory} class is responsible for creating browser
 * instances using the Playwright library. This class allows the creation of
 * browsers such as Chrome, Edge, Firefox, and Safari, with options for headless
 * execution.
 * 
 * <p>
 * This class extends {@link DriverManager} and implements the
 * {@code createBrowser()} method to instantiate browser drivers.
 * </p>
 * 
 * Supported Browsers:
 * <ul>
 * <li>Chrome</li>
 * <li>Edge</li>
 * <li>Firefox</li>
 * <li>Safari</li>
 * </ul>
 * 
 * Example Usage:
 * 
 * <pre>{@code
 * PlaywrightDriverFactory factory = new PlaywrightDriverFactory("chrome", true);
 * IActionUI browserActions = factory.createBrowser();
 * }</pre>
 * 
 */
public class PlaywrightDriverFactory extends DriverManager {

	private static final Logger logger = LoggerUtils.getLogger(PlaywrightDriverFactory.class);
	/**
	 * The type of the browser to be launched (e.g., "CHROME", "EDGE", "FIREFOX",
	 * "SAFARI").
	 */
	private String browserType;

	/**
	 * Specifies whether the browser should be run in headless mode.
	 */
	private boolean headless;
	// Instance variable for Playwright
	private Playwright playwright;
	// Instance variable for Browser
	private Browser browser;

	/**
	 * Constructs a {@code PlaywrightDriverFactory} with the specified browser type
	 * and headless mode.
	 * 
	 * @param browserType the type of the browser to be launched (e.g., "CHROME",
	 *                    "EDGE", "FIREFOX", "SAFARI")
	 * @param headless    whether the browser should run in headless mode
	 */
	public PlaywrightDriverFactory(String browserType, boolean headless) {
		this.browserType = browserType;
		this.headless = headless;
	}

	/**
	 * Creates and returns a browser instance based on the specified browser type.
	 * 
	 * <p>
	 * This method uses the Playwright API to launch a browser and return a
	 * {@link UIActionsPlaywright} object for browser interactions.
	 * </p>
	 * 
	 * Supported Browser Types:
	 * <ul>
	 * <li>CHROME</li>
	 * <li>EDGE</li>
	 * <li>FIREFOX</li>
	 * <li>SAFARI</li>
	 * </ul>
	 * 
	 * @return an {@link IActionUI} instance to interact with the browser.
	 * @throws IllegalArgumentException if the browser type is not supported
	 */
	@Override
	public IActionUI createBrowser() {
		try {
			// Initialize Playwright instance
			playwright = Playwright.create();

			// Launch the appropriate browser based on the provided browser type
			switch (browserType.toUpperCase()) {
			case "CHROME":
				browser = playwright.chromium()
						.launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headless));
				break;
			case "EDGE":
				browser = playwright.chromium()
						.launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(headless));
				break;
			case "FIREFOX":
				browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
				break;
			case "SAFARI":
				browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
				break;
			default:
				throw new IllegalArgumentException("Unsupported Playwright browser type: " + browserType);
			}

		} catch (PlaywrightException e) {
			e.printStackTrace();
			logger.error("Failed to create browser instance:", e);

		} finally {
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				if (browser != null) {
					browser.close();
				}
				if (playwright != null) {
					playwright.close();
				}
			}));
		}
		return new UIActionsPlaywright(browser);
	}

}
