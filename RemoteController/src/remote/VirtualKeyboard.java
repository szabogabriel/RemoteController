package remote;

import java.awt.AWTException;
import java.awt.Robot;

public class VirtualKeyboard {
	
	private final int BUTTON_PRESS_TIMEOUT;
	private final Robot robot;
	
	public VirtualKeyboard(int buttonPressTimeout) throws AWTException {
		this.BUTTON_PRESS_TIMEOUT = buttonPressTimeout;
		this.robot = new Robot();
	}
	
	public void handleKeyEvent(String input) {
		try {
			if (input.startsWith("kp")) {
				keyPress(readCode(input));
			} else
			if (input.startsWith("kr")) {
				keyRelease(readCode(input));
			} else
			if (input.startsWith("kt")) {
				keyType(readCode(input));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int readCode(String input) {
		return Integer.parseInt(input.substring(2).trim());
	}
	
	private void keyType(int key) throws InterruptedException {
		keyPress(key);
		Thread.sleep(BUTTON_PRESS_TIMEOUT);
		keyRelease(key);
	}
	
	private void keyPress(int key) {
		robot.keyPress(key);
	}
	
	private void keyRelease(int key) {
		robot.keyRelease(key);
	}

}
