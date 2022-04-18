package remote;

public class RuntimeParams {
	
	private int port = 60065;
	private int mouseMoveSleep = 1;
	private int mouseClickTimeout = 10; // mouse click timeout in milliseconds
	private int buttonPressTimeout = 10; // keyboard button press timeout in milliseconds
	private boolean echo = true;
	
	public RuntimeParams(String[] args) {
		try {
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
				case "-h" : printHelp(); System.exit(0); break;
				case "--port" : port = Integer.parseInt(args[++i]); break;
				case "-p" : port = Integer.parseInt(args[++i]); break;
				case "--mouse-move-sleep" : mouseMoveSleep = Integer.parseInt(args[++i]); break;
				case "-mms" : mouseMoveSleep = Integer.parseInt(args[++i]); break;
				case "--mouse-click-timeout" : mouseClickTimeout = Integer.parseInt(args[++i]); break;
				case "-mct" : mouseClickTimeout = Integer.parseInt(args[++i]); break;
				case "--button-press-timeout" : buttonPressTimeout = Integer.parseInt(args[++i]); break;
				case "-bpt" : buttonPressTimeout = Integer.parseInt(args[++i]); break;
				case "--echo" : echo = Boolean.parseBoolean(args[++i]); break;
				case "-e" : echo = Boolean.parseBoolean(args[++i]); break;
				}
			}
		} catch (Exception e) {
			printHelp();
		}
	}
	
	private void printHelp() {
		System.out.println("Invalid arguments. Valid arguments are the following.");
		System.out.println("\t\t[--port|-p]                     Set port.");
		System.out.println("\t\t[--mouse-move-sleep|-mms]       Set sleep time for the mouse movement (in millies).");
		System.out.println("\t\t[--mouse-click-timeout|-mct]    Set timeout between clicks (in millies).");
		System.out.println("\t\t[--button-press-timeout|-bpt]   Set timeout between button presses (in millies).");
		System.out.println("\t\t[--echo|-e]                     Set echo for answer.");
	}

	public int getPort() {
		return port;
	}

	public int getMouseMoveSleep() {
		return mouseMoveSleep;
	}

	public int getMouseClickTimeout() {
		return mouseClickTimeout;
	}

	public int getButtonPressTimeout() {
		return buttonPressTimeout;
	}

	public boolean isEcho() {
		return echo;
	}
	
}
