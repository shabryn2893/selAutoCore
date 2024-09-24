package io.github.shabryn2893.uicore;

import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import io.github.shabryn2893.utils.LoggerUtils;

/**
 * This manages keys names and their action.
 * 
 * @author shabbir rayeen
 */
public class KeysName {
	
	private static final Logger logger = LoggerUtils.getLogger(KeysName.class);

	private KeysName() {
	}

	/**
	 * Get the Keys
	 *
	 * @param keyName - This can accept values like ENTER,TAB,CTRL,ALT,ESCAPE or
	 *                SHIFT.
	 * @return It return Keys reference. getKey("ENTER");
	 */
	public static Keys getKey(String keyName) {
		Keys key = null;
		switch (keyName.toUpperCase()) {
		case "ENTER":
			key = Keys.ENTER;
			break;
		case "TAB":
			key = Keys.TAB;
			break;
		case "SHIFT":
			key = Keys.SHIFT;
			break;
		case "CTRL":
			key = Keys.CONTROL;
			break;
		case "ALT":
			key = Keys.ALT;
			break;
		case "ESCAPE":
			key = Keys.ESCAPE;
			break;
		default:
			logger.error("Unsupported Key Name:{}",keyName);
		}
		return key;
	}

}
