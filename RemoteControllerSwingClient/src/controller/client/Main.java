package controller.client;

import java.net.SocketException;

public class Main {
	
	public static void main(String [] args) throws SocketException {
		new Gui(new RemoteControllerClient());
	}

}
