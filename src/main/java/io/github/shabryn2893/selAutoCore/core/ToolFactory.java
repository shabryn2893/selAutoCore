package io.github.shabryn2893.selAutoCore.core;

public class ToolFactory {
	
	static IActionUI actionUI=null;

	
	public static IActionUI getToolInstance(String toolName) {
		switch (toolName.toUpperCase()) {
		case "SELENIUM":{
			System.out.println("Create Instance for "+toolName);
			actionUI = new UiActionsSelenium();
			break;
		}
		default:{
			System.out.println("Unsupported UI Driver Name: " + toolName);
		}	
		}
		return actionUI;
	}	
}
