package remote.gui.network;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class NetworkUtil {
	
	private NetworkUtil() {
		
	}
	
	private static Enumeration<NetworkInterface> getNetworkInterfaces() throws SocketException {
		return NetworkInterface.getNetworkInterfaces();
	}
	
	public static List<String> getNetworkInterfaceNames() {
		List<String> ret = new ArrayList<>();
		try {
			Enumeration<NetworkInterface> nets = getNetworkInterfaces();
			while (nets.hasMoreElements()) {
				NetworkInterface ifc = nets.nextElement();
				ret.add(ifc.getName());
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		ret.sort(String.CASE_INSENSITIVE_ORDER);
		return ret;
	}
	
	public static NetworkInterface getNetworkInterfaceByDisplayName(String name) {
		try {
			Enumeration<NetworkInterface> nets = getNetworkInterfaces();
			while (nets.hasMoreElements()) {
				NetworkInterface tmp = nets.nextElement();
				if (name.equals(tmp.getDisplayName())) {
					return tmp;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
