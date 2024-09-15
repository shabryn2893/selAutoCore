package io.github.shabryn2893.tests.ui;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.shabryn2893.uicore.IActionUI;
import io.github.shabryn2893.uicore.UIToolFactory;

/**
 * Sample UI Test
 */
public class SampleUITest {
	
	static IActionUI globalDriver = null;

	/**
	 * Initiating browser instances before launch.
	 */
	@BeforeTest
	public void setUp() {
		globalDriver=UIToolFactory.getUIToolInstance("SELENIUM");
		globalDriver.initializeDriver("CHROME", false);
	}
	
	/**
	 * Simple test case to launch browser.
	 */
	@Test
	public void test() {
		globalDriver.openURL("https://www.google.co.in/");
	}
	
	/**
	 * Closes the browser instance after launch
	 */
	@AfterTest
	public void tearDown() {
		globalDriver.closeBrowser();
	}

}
