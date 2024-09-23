package io.github.shabryn2893.uicore;

import org.slf4j.Logger;

import io.github.shabryn2893.utils.LoggerUtils;

/**
 * This manages tools instance creation.
 */
public class UIToolFactory {

	private static final Logger logger = LoggerUtils.getLogger(UIToolFactory.class);
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
			logger.info("Create Instance for {}", toolName);
			actionUI = new UIActionsSelenium();
		} else {
			 logger.error("Unsupported UI Driver Name: {}", toolName);
		}
		return actionUI;
	}
}
