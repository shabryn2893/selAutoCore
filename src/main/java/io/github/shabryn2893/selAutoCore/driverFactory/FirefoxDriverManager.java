package io.github.shabryn2893.selAutoCore.driverFactory;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverManager extends DriverManager{

	@Override
	public void launchBrowser(boolean isHeadlessMode) {
		FirefoxOptions options= new FirefoxOptions();
		options.setAcceptInsecureCerts(true);
		options.addArguments("--incognito");
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		if(isHeadlessMode==true) {options.addArguments("-headless");}
		driver=new FirefoxDriver(options);
		
	}

}
