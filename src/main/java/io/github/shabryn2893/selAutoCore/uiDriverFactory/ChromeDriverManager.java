package io.github.shabryn2893.selAutoCore.uiDriverFactory;

import java.util.List;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverManager extends DriverManager{

	@Override
	public void launchBrowser(boolean isHeadlessMode) {
		ChromeOptions options= new ChromeOptions();
		options.setAcceptInsecureCerts(true);
		options.addArguments("--incognito");
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		options.setExperimentalOption("excludeSwitches", List.of("disable-popup-blocking"));
		if(isHeadlessMode==true) {options.addArguments("--headless");}
		driver=new ChromeDriver(options);
	}

}
