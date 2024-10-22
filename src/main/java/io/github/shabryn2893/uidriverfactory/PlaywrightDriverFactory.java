package io.github.shabryn2893.uidriverfactory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightException;

import io.github.shabryn2893.uicore.IActionUI;
import io.github.shabryn2893.uicore.UIActionsPlaywright;

public class PlaywrightDriverFactory extends DriverManager {

	private String browserType;
	private boolean headless;

	public PlaywrightDriverFactory(String browserType, boolean headless) {
		this.browserType = browserType;
		this.headless = headless;
	}

	@Override
	public IActionUI createBrowser() {
		Browser browser = null;
		try (Playwright playwright = Playwright.create()) {

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
				throw new IllegalArgumentException("Unsupported Playwright browser type");
			}

		} catch (PlaywrightException e) {
			e.printStackTrace();
		}
		return new UIActionsPlaywright(browser);
	}

}
