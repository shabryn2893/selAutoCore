package io.github.shabryn2893.selAutoCore.uiCore;

public class UIToolFactory {
	
	static IActionUI actionUI=null;

	
	public static IActionUI getToolInstance(String toolName) {
		switch (toolName.toUpperCase()) {
		case "SELENIUM":{
			System.out.println("Create Instance for "+toolName);
			actionUI = new UIActionsSelenium();
			break;
		}
		default:{
			System.out.println("Unsupported UI Driver Name: " + toolName);
		}	
		}
		return actionUI;
	}	
}
