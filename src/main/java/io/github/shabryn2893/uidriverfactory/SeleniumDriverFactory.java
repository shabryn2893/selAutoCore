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

public class SeleniumDriverFactory extends DriverManager{
	
	private static final Logger logger = LoggerUtils.getLogger(SeleniumDriverFactory.class);
	private String browserType;
    private boolean headless;
    private String incognitoMode="--incognito";
    
    public SeleniumDriverFactory(String browserType, boolean headless) {
        this.browserType = browserType;
        this.headless = headless;
    }
    
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
        		if (headless)
        			chromeOptions.addArguments("--headless");
        		driver = new ChromeDriver(chromeOptions);
                break;
            case "FIREFOX":
            	FirefoxOptions firefoxOptions= new FirefoxOptions();
            	firefoxOptions.setAcceptInsecureCerts(true);
            	firefoxOptions.addArguments(incognitoMode);
            	firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        		if(headless)
        			firefoxOptions.addArguments("-headless");
        		driver=new FirefoxDriver(firefoxOptions);
                break;
            case "EDGE":
            	EdgeOptions edgeOptions= new EdgeOptions();
            	edgeOptions.setAcceptInsecureCerts(true);
            	edgeOptions.addArguments(incognitoMode);
            	edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            	edgeOptions.setExperimentalOption("excludeSwitches", List.of("disable-popup-blocking"));
        		if(headless)
        			edgeOptions.addArguments("--headless");
        		driver=new EdgeDriver(edgeOptions);
        		break;
            case "SAFARI":
            	SafariOptions safariOptions= new SafariOptions();
            	safariOptions.setAcceptInsecureCerts(true);
            	safariOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            	if(headless) {
            		logger.info("Safari browser does not support headless mode.");
            	}
        			
        		driver=new SafariDriver(safariOptions);
            	break;
            default:
                throw new IllegalArgumentException("Unsupported Selenium browser type");
        }

        return new UIActionsSelenium(driver);
    }

}
