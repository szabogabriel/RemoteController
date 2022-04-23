package com.example.remotecontrollerdevice;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class RemoteControllerService {

    private DatagramSocket socket;

    private String targetHost = "192.168.1.102";
    private int targetPort = 60065;

    private float x;
    private float y;

    private LinkedBlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    public RemoteControllerService() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        init();
        new Thread(this::sendThread).start();
    }

    public void connect(String host, String port) {
        Log.d("RemoteControllerService", "Host: " + host + ", Port: " + port);
        this.targetHost = host;
        this.targetPort = Integer.parseInt(port);
    }

    public void setMovePoint(float x, float y) {
        if (isFirstData()) {
            this.x = x;
            this.y = y;
        } else {
            int dx = (int)((x - this.x) / 30);
            int dy = (int)((y - this.y) / 30);

            sendMouseMoved(dx, dy);
        }
    }

    private boolean isFirstData() {
        return x == 0 && y == 0;
    }

    public void init() {
        x = 0;
        y = 0;
    }

    private void sendMouseMoved(int dx, int dy){
        String message = "mm " + dx + " " + dy;
        send(message);
    }

    public void sendMousePressed(int mouseButton){
        String message = "mp " + mouseButton;
        send(message);
    }

    public void sendMouseReleased(int mouseButton){
        String message = "mr " + mouseButton;
        send(message);
    }

    public void sendMouseClicked(int mouseButton) {
        String message = "mc " + mouseButton;
        send(message);
    }

    public void sendMouseScrolled(int scrollTick) {
        String message = "ms " + scrollTick;
        send(message);
    }

    public void sendKeyPressed(int key) {
        String message = "kp " + key;
        send(message);
    }

    public void sendKeyReleased(int key){
        String message = "kr " + key;
        send(message);
    }

    public void sendKeyTyped(int key){
        String message = "kt " + key;
        send(message);
    }

    public void send(String message){
        messageQueue.offer(message);
    }

    private void sendThread() {
        String message = null;
        try {
            while ((message = messageQueue.poll(1000L, TimeUnit.DAYS)) != null) {
                try {
//                    Log.d("RemoteController", "Sending message " + message + " to " + targetHost + ":" + targetPort);
                    socket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, InetAddress.getByName(targetHost), targetPort));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            new Thread(this::sendThread).start();
        }
    }
}
