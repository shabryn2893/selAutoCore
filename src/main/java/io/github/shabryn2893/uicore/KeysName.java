package io.github.shabryn2893.uicore;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.Keys;

/**
 * This manages keys names and their action.
 * 
 * @author shabbir rayeen
 */
public class KeysName {
	
	private static final Logger logger = Logger.getLogger(KeysName.class.getName());

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
			logger.log(Level.INFO,"Unsupported Key Name:{0}",keyName);
		}
		return key;
	}

}
