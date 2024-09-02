package io.github.shabryn2893.selAutoCore.apiCore;

public class APIToolFactory {

	static IActionAPI actionAPI = null;

	public static IActionAPI apiDriverInstance(String toolName, String baseURI) {
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
