package remote.gui.network;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.net.NetworkInterface;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import remote.gui.KeyValueView;

public class NetworkInterfacesView extends JPanel {

	private static final long serialVersionUID = 7032788818143021892L;
	
	private NetworkInterfaceModel model_networkInterface = new NetworkInterfaceModel();
	private NetworkInterfaceView view_networkInterface = new NetworkInterfaceView(model_networkInterface);
	
	private DefaultComboBoxModel<String> model_networkInterfaces = new DefaultComboBoxModel<>();
	private KeyValueView<JComboBox<String>> kvv_interfaces = new KeyValueView<>("Network interfaces", new Dimension(150, 30), new Dimension(200, 30), new JComboBox<>(model_networkInterfaces));
	
	public NetworkInterfacesView() {
		setLayout(new BorderLayout());
		
		add(kvv_interfaces, BorderLayout.NORTH);
		add(view_networkInterface, BorderLayout.CENTER);
		
		NetworkUtil.getNetworkInterfaceNames().stream().forEach(model_networkInterfaces::addElement);
		
		kvv_interfaces.getView().addActionListener(this::updateNetworkModelView);
	}
	
	private void updateNetworkModelView(ActionEvent e) {
		String currentlySelectedInterfaceDisplayName = model_networkInterfaces.getSelectedItem().toString();
		NetworkInterface currentNetworkInterface = NetworkUtil.getNetworkInterfaceByDisplayName(currentlySelectedInterfaceDisplayName);
		model_networkInterface.setNetworkInterface(currentNetworkInterface);
	}
	
}
