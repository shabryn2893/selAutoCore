package io.github.shabryn2893.selAutoCore.uiCore;

import org.openqa.selenium.Keys;

public class KeysName {
	
	public static Keys getKey(String keyName) {
		Keys key = null;
		switch(keyName.toUpperCase()) {
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
			System.out.println("Unsupported Key Name: "+keyName);
		}
		return key;
	}

}
