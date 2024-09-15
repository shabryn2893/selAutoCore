package io.github.shabryn2893.apicore;

/**
 * This class is for initiating API tool instance
 * 
 * @author shabbir rayeen
 */
public class APIToolFactory {

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
			System.out.println("Create Instance for " + toolName);
			actionAPI = new APIActionsRestAssured(baseURI);
			break;
		}
		default: {
			System.out.println("Unsupported API Driver Name: " + toolName);
		}
		}
		return actionAPI;
	}

}
