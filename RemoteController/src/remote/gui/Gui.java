package remote.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import remote.gui.network.NetworkInterfacesView;

public class Gui extends JFrame {
	
	private static final long serialVersionUID = -2264554281908688036L;
	
	public static void main(String [] args) {
		new Gui();
	}
	
	private NetworkInterfacesView view_networkInterfaces = new NetworkInterfacesView();
	
	public Gui() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		add(view_networkInterfaces, BorderLayout.NORTH);
		
		pack();
		setVisible(true);
		
		init();
	}
	
	private void init() {
		
	}

}
