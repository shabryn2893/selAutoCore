package io.github.shabryn2893.selAutoCore.uiCore;

public class UIToolFactory {
	
	static IActionUI actionUI=null;

	/**
	 * Get the tool instance
	 *
	 * @param toolName - This can accept values like SELENIUM.
	 * @return It return IActionUI reference.
	 * @example
	 * getToolInstance("SELENIUM");
	 */
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
