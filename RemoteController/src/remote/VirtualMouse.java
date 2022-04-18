package remote;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;

public class VirtualMouse {

	private final Robot robot;
	private final int MOUSE_MOVE_SLEEP;
	private final int MOUSE_CLICK_TIMEOUT;

	public VirtualMouse(int mouseClickTimeout, int mouseMoveSleep) throws AWTException {
		this.MOUSE_CLICK_TIMEOUT = mouseClickTimeout;
		this.MOUSE_MOVE_SLEEP = mouseMoveSleep;
		robot = new Robot();
	}

	public void handleMouseEvent(String receivedData) {
		try {
			if (receivedData.startsWith("mm")) {
				handleMoveMouse(receivedData);
			} else if (receivedData.startsWith("mp")) {
				handleMousePressed(receivedData);
			} else if (receivedData.startsWith("mr")) {
				handleMouseReleased(receivedData);
			} else if (receivedData.startsWith("ms")) {
				handleMouseScrolled(receivedData);
			} else if (receivedData.startsWith("mc")) {
				handleMouseClick(receivedData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleMouseScrolled(String receivedData) {
		int version = Integer.parseInt(receivedData.substring(2).trim());
		mouseScroll(version);
	}

	private void handleMouseReleased(String receivedData) {
		int version = Integer.parseInt(receivedData.substring(2).trim());
		switch (version) {
		case 0:
			mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
			break;
		case 1:
			mouseRelease(MouseEvent.BUTTON2_DOWN_MASK);
			break;
		case 2:
			mouseRelease(MouseEvent.BUTTON3_DOWN_MASK);
			break;
		default:
			System.out.println("Invalid input: " + receivedData + ". Expected mc [0-2]");
		}
	}

	private void handleMousePressed(String receivedData) {
		int version = Integer.parseInt(receivedData.substring(2).trim());
		switch (version) {
		case 0:
			mousePress(MouseEvent.BUTTON1_DOWN_MASK);
			break;
		case 1:
			mousePress(MouseEvent.BUTTON2_DOWN_MASK);
			break;
		case 2:
			mousePress(MouseEvent.BUTTON3_DOWN_MASK);
			break;
		default:
			System.out.println("Invalid input: " + receivedData + ". Expected mc [0-2]");
		}
	}

	private void handleMoveMouse(String input) throws InterruptedException {
		String[] position = input.substring(2).trim().split("\s");
		if (position.length == 2) {
			int x = Integer.parseInt(position[0]);
			int y = Integer.parseInt(position[1]);
			moveMouse(x, y);
		} else {
			System.out.println("Invalid input: " + input + ". Expected: mm [x] [y]");
		}
	}

	private void handleMouseClick(String input) throws InterruptedException {
		int version = Integer.parseInt(input.substring(2).trim());
		switch (version) {
		case 0:
			mouseClick(MouseEvent.BUTTON1_DOWN_MASK);
			break;
		case 1:
			mouseClick(MouseEvent.BUTTON2_DOWN_MASK);
			break;
		case 2:
			mouseClick(MouseEvent.BUTTON3_DOWN_MASK);
			break;
		default:
			System.out.println("Invalid input: " + input + ". Expected mc [0-2]");
		}
	}

	private void moveMouse(int dx, int dy) throws InterruptedException {
		boolean finishedX = false, finishedY = false;
		int progressX = 0, progressY = 0;
		do {
			int moveX = (!finishedX) ? (dx < 0) ? -1 : ((dx > 0) ? 1 : 0) : 0;
			int moveY = (!finishedY) ? (dy < 0) ? -1 : ((dy > 0) ? 1 : 0) : 0;
			Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
			robot.mouseMove(moveX + mouseLocation.x, moveY + mouseLocation.y);
			progressX += moveX;
			progressY += moveY;
			finishedX = progressX == dx;
			finishedY = progressY == dy;
			if (MOUSE_MOVE_SLEEP > 0) {
				Thread.sleep(MOUSE_MOVE_SLEEP);
			}
		} while (!finishedX || !finishedY);
	}

	private void mouseClick(int type) throws InterruptedException {
		mousePress(type);
		Thread.sleep(MOUSE_CLICK_TIMEOUT);
		mouseRelease(type);
	}

	private void mousePress(int type) {
		robot.mousePress(type);
	}

	private void mouseRelease(int type) {
		robot.mouseRelease(type);
	}

	private void mouseScroll(int notches) {
		robot.mouseWheel(notches);
	}

}
