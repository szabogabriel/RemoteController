# RemoteController
This is a simple remote controller for PCs (laptop, notebook etc.). It uses a thin Java agent which opens a UDP socket and listens for any command. Currently Java Swing and Android clients are available.

## Motivation
After setting up my HTPC, I was not satisfied with the wireless keyboard/touchpad I was using. The dongle had a noticable lag, and the signal was weak. I was looking for a solution, but could not find anything simple and "user friendly". Since I couldn't be bothered reading the different manuals (like for anyRemote), I falled back to a simpler solution: write one of my own (a.k.a. how hard could it be?).

## Disclaimer

This is a hobby project, more like a PoC. It is **very** unsecure and should be used with caution. Future development from my side depends on whether or not I find good use for this project.

## Manual

### Agent
The agent needs access to the network. It will create a `DatagramSocket` to listen for any incomming data. The start and configuration of the server is very straight forward.

```
Invalid arguments. Valid arguments are the following.
		[--port|-p]                     Set port.
		[--mouse-move-sleep|-mms]       Set sleep time for the mouse movement (in millies).
		[--mouse-click-timeout|-mct]    Set timeout between clicks (in millies).
		[--button-press-timeout|-bpt]   Set timeout between button presses (in millies).
		[--echo|-e]                     Set echo for answer.
```
The application is packed into a single jar file. The following command will start an agent on port 54321 with no mouse sleept.

`java -cp remoteController.jar controller.Main --port 54321 --mouse-move-sleep 0`

All the configuration parameters have a default value. Providing no parameters will create a server on `localhost:60065`, with timeout values of 10ms and echo enabled.

The agent seems to be having issues controlling system-related windows on Windows 8.1. A good example seems to be the Task Manager from windows, which I was not able to control via the agent.

### Swing Client
Start the client as a Java Swing application. In the configuration, you must enter the following information: `[server-address]:[common-port]`. After entering this data you can connect to the server. "Dragging" on the canvas area will move the mouse on the remote site. Entering text into the bottom text field will send the keys entered (including command and backspace). Selecting the checkbox will enable "drag" mode, instead of just moving the mouse.

From a development point of view, the control messages are sent from the event dispatcher thread. The application itself is simple and stores no data at all. It consists solely from a GUI part and an agent client part (not counting the main class).

### Android Client
The Android client is currently not available from Play store. If you want to use it, you have to install it from the IDE. The application is in its PoC stage. Currently it offers one main activity and a settings activity. The settings only consists of the target host and port settings. These are persisted in the application, but becase of a current bug, you have to restart the application after changing them.

The messages are sent asynchronously from a background worker thread. There is also a rather huge lag on the machine. Also, recognizing input data is currently set to a simplified state. A more elaborate method is desperately needed here.

## Tested

The server application was tested on Windows 8.1 and Linux Ubuntu 20.03LTS. It worked without any issues.

## Hint

If you want to increase fluidity, try setting the `--mouse-move-sleep` attribute to zero.
