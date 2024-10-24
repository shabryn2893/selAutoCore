package io.github.shabryn2893.tests.api;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.shabryn2893.apicore.APIToolFactory;
import io.github.shabryn2893.apicore.IActionAPI;

/**
 * Sample API Test
 */
public class SampleAPITest {

	static IActionAPI apiInstannce = null;

	/**
	 * Initiating API Tool and Base URI.
	 */
	@BeforeTest
	public void setUp() {
		apiInstannce = APIToolFactory.getAPIToolInstance("RESTASSURED",
				"https://practice-react.sdetunicorns.com/api/test");
	}

	/**
	 * Simple test case to get all brands.
	 */
	@Test
	public void test() {
		apiInstannce.getRequest("/brands");
		Assert.assertEquals(apiInstannce.getStatusCode(), 200);
		apiInstannce.printResponse();
	}

	/**
	 * Executes tearDown script.
	 */
	@AfterTest
	public void tearDown() {
		// Write here you tearDown script.
	}

}
