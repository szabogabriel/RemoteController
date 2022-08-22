package controller.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class RemoteControllerClient {
	
	private DatagramSocket socket = null;
	
	private InetAddress host;
	private int port;
	
	public RemoteControllerClient() {
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void setTargetConnection(String host, int port) {
		try {
			this.host = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.port = port;
	}
	
	public void sendMouseMoved(int dx, int dy){
		String message = "mm " + dx + " " + dy;
		send(message);
	}
	
	public void sendMousePressed(int mouseButton){
		String message = "mp " + mouseButton;
		send(message);
	}
	
	public void sendMouseReleased(int mouseButton){
		String message = "mr " + mouseButton;
		send(message);
	}
	
	public void sendMouseClicked(int mouseButton) {
		String message = "mc " + mouseButton;
		send(message);
	}
	
	public void sendMouseScrolled(int scrollTick) {
		String message = "ms " + scrollTick;
		send(message);
	}
	
	public void sendKeyPressed(int key) {
		String message = "kp " + key;
		send(message);
	}
	
	public void sendKeyReleased(int key){
		String message = "kr " + key;
		send(message);
	}
	
	public void sendKeyTyped(int key){
		String message = "kt " + key;
		send(message);
	}
	
	private void send(String message){
		try {
			socket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, host, port));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
