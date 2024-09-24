package io.github.shabryn2893.apicore;

import org.slf4j.Logger;
import io.github.shabryn2893.utils.LoggerUtils;

/**
 * This class is for initiating API tool instance
 * 
 * @author shabbir rayeen
 */
public class APIToolFactory {

	private static final Logger logger = LoggerUtils.getLogger(APIToolFactory.class);
	static IActionAPI actionAPI = null;

	private APIToolFactory() {}
	/**
	 * Get the tool instance
	 *
	 * @param toolName - This can accept values like SELENIUM.
	 * @param baseURI  - API Base URI
	 * @return It return IActionUI reference. getAPIToolInstance("RESTASSURED");
	 */
	public static IActionAPI getAPIToolInstance(String toolName, String baseURI) {
		switch (toolName.toUpperCase()) {
		case "RESTASSURED": {
			logger.info("Create Instance for {}",toolName);
			actionAPI = new APIActionsRestAssured(baseURI);
			break;
		}
		default: {
			logger.error("Unsupported API Driver Name: {}",toolName);
		}
		}
		return actionAPI;
	}

}
