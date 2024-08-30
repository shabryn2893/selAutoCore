package io.github.shabryn2893.selAutoCore.driverFactory;

import java.util.List;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeDriverManager extends DriverManager{

	@Override
	public void launchBrowser(boolean isHeadlessMode) {
		EdgeOptions options= new EdgeOptions();
		options.setAcceptInsecureCerts(true);
		options.addArguments("--incognito");
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		options.setExperimentalOption("excludeSwitches", List.of("disable-popup-blocking"));
		if(isHeadlessMode==true) {options.addArguments("--headless");}
		driver=new EdgeDriver(options);
		
	}

}
