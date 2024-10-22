package io.github.shabryn2893.tests.ui;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.shabryn2893.uicore.IActionUI;
import io.github.shabryn2893.uidriverfactory.DriverFactory;
import io.github.shabryn2893.uidriverfactory.DriverManager;

/**
 * Sample UI Test
 */
public class SampleUITest {
	
	static IActionUI globalDriver = null;
	String toolName="PLAYWRIGHT";
	int maxWaitTime=100;
	
	/**
	 * This framework supports execution of test-cases in multiple tools.
	 * set the toolName="SELENIUM" to run test in selenium webdriver.
	 * set the toolName="PLAYWRIGHT" to run test in PLAYWRIGHT.
	 * */

	/**
	 * Initiating browser instances before launch.
	 */
	@BeforeTest
	public void setUp() {
		DriverManager driverManager=DriverFactory.getDriver("CHROME", toolName, false);
		globalDriver=driverManager.createBrowser();
	}
	
	/**
	 * Simple test case to launch browser.
	 */
	@Test
	public void test() {
		globalDriver.openURL("https://parabank.parasoft.com/parabank/index.htm");
		globalDriver.type("XPATH", "//input[@name='username']", "abcs@gmail.com", maxWaitTime);
		globalDriver.type("XPATH", "//input[@name='password']", "Welcome@122", maxWaitTime);
		globalDriver.click("XPATH", "//input[@value='Log In']", maxWaitTime);
		String actualPagetitle=globalDriver.getPageTitle();
		System.out.println("Actual Page Title: "+actualPagetitle);
		Assert.assertEquals(actualPagetitle, "ParaBank | Error");
	}
	
	/**
	 * Closes the browser instance after launch
	 */
	@AfterTest
	public void tearDown() {
		globalDriver.closeBrowser();
	}

}
