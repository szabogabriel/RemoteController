package remote;

import java.awt.AWTException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Main {

	private RuntimeParams params;
	private DatagramSocket socket;
	private VirtualMouse mouse;
	private VirtualKeyboard keyboard;

	public static void main(String[] args) throws SocketException, AWTException {
		new Main(args).run();
	}

	public Main(String[] args) throws SocketException, AWTException {
		params = new RuntimeParams(args);
		socket = new DatagramSocket(params.getPort());
		System.out.println("Socket created on port " + params.getPort());
		mouse = new VirtualMouse(params.getMouseClickTimeout(), params.getMouseMoveSleep());
		keyboard = new VirtualKeyboard(params.getButtonPressTimeout());
	}

	private void run() {
		boolean finished = false;
		byte[] receivingDataBuffer = new byte[1024];
		byte[] sendingDataBuffer = new byte[1024];

		DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
		System.out.println("Waiting for a client to connect...");

		while (!finished) {
			try {
				socket.receive(inputPacket);
				String receivedData = new String(inputPacket.getData()).substring(0, inputPacket.getLength()).trim();
				
				finished = handleData(receivedData);

				if (params.isEcho()) {
					InetAddress senderAddress = inputPacket.getAddress();
					int senderPort = inputPacket.getPort();
	
					sendingDataBuffer = ("<< " + receivedData + " >>\n").getBytes();
					
					DatagramPacket outputPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, senderAddress, senderPort);
	
					socket.send(outputPacket);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		socket.close();
	}
	
	private boolean handleData(String receivedData) {
		if (receivedData.startsWith("m")) {
			mouse.handleMouseEvent(receivedData);
		} else
		if (receivedData.startsWith("k")) {
			keyboard.handleKeyEvent(receivedData);
		}
		return "finish".equals(receivedData);
	}

}
