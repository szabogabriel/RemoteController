package controller.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui extends JFrame {

	private static final long serialVersionUID = -6057399705505038665L;
	
	private final JPanel panelNorth = new JPanel();
	private final JPanel panelCenter = new JPanel();
	
	private final JCheckBox buttonDrag = new JCheckBox("Button drag");
	
	private final JTextField southText = new JTextField();
	
	private final RemoteControllerClient client;
	
	private int oldX = -1;
	private int oldY = -1;
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuConnection;
	private JMenuItem menuSettings;
	private JCheckBoxMenuItem menuConnect;
	
	private String hostname;
	private String port;
	private String local;
	
	public Gui(RemoteControllerClient clt) {
		this.client = clt;
		
		menuConnection = new JMenu("Connection");
		menuBar.add(menuConnection);
		menuSettings = new JMenuItem("Settings...");
		menuConnection.add(menuSettings);
		menuConnect = new JCheckBoxMenuItem("Connected");
		menuConnect.setSelected(false);
		menuConnection.add(menuConnect);
		setJMenuBar(menuBar);
		
		menuSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openHostSettings();
			}
		});
		
		menuConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (menuConnect.isSelected()) {
					client.connect(local, hostname, Integer.parseInt(port));
				} else {
					client.disconnect();
				}
			}
		});
		
		setLayout(new BorderLayout());
		panelNorth.setLayout(new FlowLayout());
		panelNorth.add(buttonDrag);
		panelCenter.setBorder(BorderFactory.createLineBorder(Color.black));
		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(southText, BorderLayout.SOUTH);
		
		setPreferredSize(new Dimension(500, 350));
		
		panelCenter.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				client.sendMouseClicked(getButtonCode(e));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if (buttonDrag.isSelected()) {
					client.sendMousePressed(getButtonCode(e));
				}
				updatePosition(e);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (buttonDrag.isSelected()) {
					client.sendMouseReleased(getButtonCode(e));
				}
			}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
		panelCenter.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getPoint().x;
				int y = e.getPoint().y;
				int dx = x - oldX;
				int dy = y - oldY;
				client.sendMouseMoved(dx, dy);
				updatePosition(e);
			}
			@Override public void mouseMoved(MouseEvent e) {}
		});
		southText.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				client.sendKeyTyped(e.getKeyCode());
				southText.setText("");
			}
			@Override
			public void keyPressed(KeyEvent e) {
				client.sendKeyPressed(e.getKeyCode());	
				southText.setText("");
			}
			@Override
			public void keyReleased(KeyEvent e) {
				client.sendKeyReleased(e.getKeyCode());	
				southText.setText("");
			}
		});
		
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void updatePosition(MouseEvent e) {
		oldX = e.getPoint().x;
		oldY = e.getPoint().y;
	}
	
	private int getButtonCode(MouseEvent e) {
		int ret = -1;
		switch (e.getButton()) {
		case MouseEvent.BUTTON1: ret = 0; break;
		case MouseEvent.BUTTON2: ret = 1; break;
		case MouseEvent.BUTTON3: ret = 2; break;
		}
		return ret;
	}
	
	private void openHostSettings() {
		String url = JOptionPane.showInputDialog(this, "Enter target hostname:port");
		local = url.substring(0, url.indexOf(';'));
		hostname = url.substring(url.indexOf(';') + 1, url.indexOf(':'));
		port = url.substring(url.indexOf(':') + 1);
	}
	
}
