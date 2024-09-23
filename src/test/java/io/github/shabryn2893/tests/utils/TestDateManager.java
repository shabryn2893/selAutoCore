package io.github.shabryn2893.tests.utils;

import org.testng.annotations.Test;

import io.github.shabryn2893.utils.DateManager;

public class TestDateManager {

	@Test
	public  void testDateManager() {
		System.out.println("Next Day Date: "+DateManager.getDaysOut(1,"MMM-dd-yyyy"));
	}
}
