package io.github.shabryn2893.uidriverfactory;

import java.util.List;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;

import io.github.shabryn2893.uicore.IActionUI;
import io.github.shabryn2893.uicore.UIActionsSelenium;
import io.github.shabryn2893.utils.LoggerUtils;

/**
 * The {@code SeleniumDriverFactory} class is responsible for creating and
 * configuring browser instances using the Selenium WebDriver. It supports
 * multiple browsers such as Chrome, Firefox, Edge, and Safari, with options for
 * headless execution and incognito mode.
 * 
 * <p>
 * This class extends {@link DriverManager} and implements the
 * {@code createBrowser()} method to instantiate browser drivers using Selenium.
 * </p>
 * 
 * >Supported Browsers:
 * <ul>
 * <li>Chrome</li>
 * <li>Firefox</li>
 * <li>Edge</li>
 * <li>Safari</li>
 * </ul>
 * 
 * Example Usage:
 * 
 * <pre>{@code
 * SeleniumDriverFactory factory = new SeleniumDriverFactory("chrome", true);
 * IActionUI browserActions = factory.createBrowser();
 * }</pre>
 * 
 */
public class SeleniumDriverFactory extends DriverManager {

	/**
	 * Logger instance for logging important events.
	 */
	private static final Logger logger = LoggerUtils.getLogger(SeleniumDriverFactory.class);

	/**
	 * The type of browser to be launched (e.g., "CHROME", "FIREFOX", "EDGE",
	 * "SAFARI").
	 */
	private String browserType;

	/**
	 * Specifies whether the browser should be run in headless mode.
	 */
	private boolean headless;

	/**
	 * Command line argument to launch the browser in incognito mode.
	 */
	private String incognitoMode = "--incognito";

	/**
	 * Constructs a {@code SeleniumDriverFactory} with the specified browser type
	 * and headless mode.
	 * 
	 * @param browserType the type of the browser to be launched (e.g., "CHROME",
	 *                    "FIREFOX", "EDGE", "SAFARI")
	 * @param headless    whether the browser should run in headless mode
	 */
	public SeleniumDriverFactory(String browserType, boolean headless) {
		this.browserType = browserType;
		this.headless = headless;
	}

	/**
	 * Creates and returns a browser instance based on the specified browser type.
	 * 
	 * <p>
	 * This method configures the browser options for the supported browsers and
	 * launches them with the specified options such as headless mode and incognito
	 * mode.
	 * </p>
	 * 
	 * Supported Browser Types:
	 * <ul>
	 * <li>CHROME</li>
	 * <li>FIREFOX</li>
	 * <li>EDGE</li>
	 * <li>SAFARI</li>
	 * </ul>
	 * 
	 * <p>
	 * Note: Safari does not support headless mode. If headless is requested for
	 * Safari, a log message will be printed but the browser will not run in
	 * headless mode.
	 * </p>
	 * 
	 * @return an {@link IActionUI} instance to interact with the browser.
	 * @throws IllegalArgumentException if the browser type is not supported.
	 */
	@Override
	public IActionUI createBrowser() {
		WebDriver driver;

		switch (browserType.toUpperCase()) {
		case "CHROME":
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setAcceptInsecureCerts(true);
			chromeOptions.addArguments(incognitoMode);
			chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
			chromeOptions.setExperimentalOption("excludeSwitches", List.of("disable-popup-blocking"));
			if (headless) {
				chromeOptions.addArguments("--headless");
			}
			driver = new ChromeDriver(chromeOptions);
			break;
		case "FIREFOX":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setAcceptInsecureCerts(true);
			firefoxOptions.addArguments(incognitoMode);
			firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
			if (headless) {
				firefoxOptions.addArguments("-headless");
			}
			driver = new FirefoxDriver(firefoxOptions);
			break;
		case "EDGE":
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.setAcceptInsecureCerts(true);
			edgeOptions.addArguments(incognitoMode);
			edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
			edgeOptions.setExperimentalOption("excludeSwitches", List.of("disable-popup-blocking"));
			if (headless) {
				edgeOptions.addArguments("--headless");
			}
			driver = new EdgeDriver(edgeOptions);
			break;
		case "SAFARI":
			SafariOptions safariOptions = new SafariOptions();
			safariOptions.setAcceptInsecureCerts(true);
			safariOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
			if (headless) {
				logger.info("Safari browser does not support headless mode.");
			}
			driver = new SafariDriver(safariOptions);
			break;
		default:
			throw new IllegalArgumentException("Unsupported Selenium browser type");
		}

		return new UIActionsSelenium(driver);
	}
}
