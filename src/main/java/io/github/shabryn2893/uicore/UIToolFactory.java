package io.github.shabryn2893.uicore;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This manages tools instance creation.
 */
public class UIToolFactory {

	private static final Logger logger = Logger.getLogger(UIToolFactory.class.getName());
	static IActionUI actionUI = null;

	private UIToolFactory() {
	}

	/**
	 * Get the tool instance
	 *
	 * @param toolName - This can accept values like SELENIUM.
	 * @return It return IActionUI reference. getUIToolInstance("SELENIUM");
	 */
	public static IActionUI getUIToolInstance(String toolName) {
		if (toolName.equalsIgnoreCase("SELENIUM")) {
			logger.log(Level.INFO, "Create Instance for {0}", toolName);
			actionUI = new UIActionsSelenium();
		} else {
			logger.log(Level.INFO, "Unsupported UI Driver Name: {0}", toolName);
		}
		return actionUI;
	}
}
