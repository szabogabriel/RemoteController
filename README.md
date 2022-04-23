# RemoteController
This is a simple remote controller for your PC (laptop, notebook etc.). It uses a thin Java agent which opens a UDP socket and listens for any command. Currently there is only a Java Swing client created.

## Motivation
I was setting up my HTPC, but was not satisfied with the wireless keyboard/touchpad I was using. The dongle had a great lag, and the signal was weak. I was looking for a solution, but could not find anything simple and "user friendly". Since I couldn't be bothered reading the different manuals (like for anyRemote), I falled back to a simpler solution: write one of my own (a.k.a. how hard could it be).

## Disclaimer

This is a hobby project, more like a PoC. It is **very** unsecure and should be used with caution. Any future development is heavily questionable, it really depends, whether I find good use for this project.

## Usage - Server
The server needs access to the network. It will create a `DatagramSocket` to listen for any incomming information. The start and configuration of the server is very straight forward.

```
Invalid arguments. Valid arguments are the following.
		[--port|-p]                     Set port.
		[--mouse-move-sleep|-mms]       Set sleep time for the mouse movement (in millies).
		[--mouse-click-timeout|-mct]    Set timeout between clicks (in millies).
		[--button-press-timeout|-bpt]   Set timeout between button presses (in millies).
		[--echo|-e]                     Set echo for answer.
```

All the configuration parameters have a default value. So providing nothing will create a server on `localhost:60065`, with timeout values of 10ms and echo enabled.

## Usage - Client
Start the client as a Java Swing application. In the configuration, you must enter the following information: `[client-address];[server-address]:[common-port]`. After entering this data you can connect to the server. "Dragging" on the canvas area will move the mouse on the remote site. Entering text into the bottom text field will send the keys entered (including command and backspace). Selecting the checkbox will enable "drag" mode, instead of just moving the mouse.

## Tested

The server application was tested on Windows 8.1 and Linux Ubuntu 20.03LTS. It worked without any issues.

## Hint

If you want to increase fluidity, try setting the `--mouse-move-sleep` attribute to zero.
