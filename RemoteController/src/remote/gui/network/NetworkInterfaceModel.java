package remote.gui.network;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gabor
 *
 */
public class NetworkInterfaceModel {
	
	public static interface NetworkInterfaceChangeListener {
		public void modelContentChanged();
	}

	private String displayName = "";
	private String name = "";
	private List<String> inetAddress = new ArrayList<>();
	
	private NetworkInterfaceChangeListener listener;
	
	public void setNetworkInterface(NetworkInterface networkInterface) {
		if (networkInterface != null) {
			displayName = networkInterface.getDisplayName();
			name = networkInterface.getName();
			inetAddress = networkInterface.getInterfaceAddresses().stream().map(i -> i.toString()).collect(Collectors.toList());
			castModelChangedEvent();
		}
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getName() {
		return name;
	}

	public List<String> getInetAddress() {
		return inetAddress;
	}

	public void setModelChangeEventListener(NetworkInterfaceChangeListener listener) {
		this.listener = listener;
	}
	
	private void castModelChangedEvent() {
		if (listener != null) {
			listener.modelContentChanged();
		}
	}

}
