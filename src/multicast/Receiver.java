/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast;

import interfaces.ICallback;
import interfaces.ICallbackException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author erik0
 */
public class Receiver {
    private final MulticastSocket socket;
    private final InetAddress groupAddress;
    
    private byte[] buf = new byte[5000];
        
    private ICallback callback;
    private ICallbackException callbackException;
    
    private Thread listenerThread;
    private boolean running = true;
    
    private class Listener implements Runnable {

        @Override
        public void run() {
            while (running) {
                try {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    
                    if (!running)
                        return;
                    
                    String received = new String(
                        packet.getData(), 0, packet.getLength()
                    );

                    callback.run(received);
                } catch (Exception e) {
                    callbackException.run(e);
                }
            }
        }
        
    }

    public Receiver(String address, int port) 
            throws IOException{
        this.socket = new MulticastSocket(port);
        this.groupAddress = InetAddress.getByName(address);
        
        socket.joinGroup(groupAddress);
    }
    
    public void startListen(ICallback callback, ICallbackException callbackException) {
        this.callback = callback;
        this.callbackException = callbackException;
        
        this.listenerThread = new Thread(new Listener());
        listenerThread.start();
    }
    
    public void destroy() {
        this.running = false;
    }
}
