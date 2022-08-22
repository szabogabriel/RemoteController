package remote.gui.network;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import remote.gui.KeyValueView;

public class NetworkInterfaceView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private DefaultListModel<String> modelInterfaceAddress = new DefaultListModel<>();
	
	private KeyValueView<JTextField> kvv_name = new KeyValueView<>("Name", new Dimension(100, 25), new Dimension(250, 25), new JTextField());
	private KeyValueView<JList<String>> kvv_address = new KeyValueView<>("Address", new Dimension(100, 25), new Dimension(250, 75), new JList<>(modelInterfaceAddress));
	
	private NetworkInterfaceModel model;

	public NetworkInterfaceView(NetworkInterfaceModel model) {
		setLayout(new BorderLayout());
		
		kvv_name.getView().setEditable(false);
		
		add(kvv_name, BorderLayout.NORTH);
		add(kvv_address, BorderLayout.SOUTH);
		
		this.model = model;
		model.setModelChangeEventListener(this::update);
	}
	
	private void update() {
		if (model != null) {
			kvv_name.getView().setText(model.getName());
			modelInterfaceAddress.removeAllElements();
			model.getInetAddress().forEach(modelInterfaceAddress::addElement);
		}
	}

}
