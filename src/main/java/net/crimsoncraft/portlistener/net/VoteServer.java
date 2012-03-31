/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crimsoncraft.portlistener.net;

import com.crimsonrpg.pvpranker.CrimsonPvP;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simplyianm
 */
public class VoteServer extends Thread {
    private String host;

    private int port;

    private ServerSocket listener;

    public VoteServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startListener() {
        // Initialize the listener.
        InetSocketAddress address;
        if (host.equalsIgnoreCase("ANY") || host.equalsIgnoreCase("0.0.0.0")) {
            CrimsonPvP.log("Starting Vote server on *:" + Integer.toString(port));
            address = new InetSocketAddress(port);
        } else {
            CrimsonPvP.log("Starting Vote server on " + host + ":" + Integer.toString(port));
            address = new InetSocketAddress(host, port);
        }
        try {
            listener = new ServerSocket();
            listener.bind(address);
        } catch (IOException ex) {
            CrimsonPvP.log("Could not bind to port!");
        }
    }
    
    public void close() {
        try {
            listener.close();
        } catch (IOException ex) {
        }
    }

    @Override
    public void run() {
        try {
            while (!listener.isClosed()) {
                // Wait for and accept all incoming connections.
                Socket socket = getListener().accept();

                // Create a new thread to handle the request.
                (new RequestHandler(socket)).start();
            }
        } catch (IOException ignored) {
        }
    }

    public String getHost() {
        return host;
    }

    public ServerSocket getListener() {
        return listener;
    }

    public int getPort() {
        return port;
    }

}
