/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author erik0
 */
public class Sender {
    private final DatagramSocket socket;
    private final InetAddress groupAddress;
    private final int port;

    public Sender(String address, int port) 
            throws UnknownHostException, SocketException{
        this.socket = new DatagramSocket();
        this.groupAddress = InetAddress.getByName(address);
        this.port = port;
    }
    
    public void send(String message) throws IOException{
        byte[] buf = message.getBytes();
        
        DatagramPacket packet = new DatagramPacket(buf, buf.length, 
                groupAddress, port);
        socket.send(packet);
    }
    
    public void destroy() {
        socket.close();
    }
    
}
