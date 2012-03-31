/*
 * This file is part of PortListener.
 *
 * PortListener is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PortListener is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with PortListener.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.crimsoncraft.portlistener.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import net.crimsoncraft.portlistener.PortListener;

/**
 * Listens to incoming messages.
 */
public class PortServer extends Thread {
    private final PortListener plugin;

    private String host;

    private int port;

    private ServerSocket listener;

    public PortServer(PortListener plugin, String host, int port) {
        this.plugin = plugin;
        this.host = host;
        this.port = port;
    }
    
    @Override
    public void start() {
        startListener();
        super.start();
    }

    private void startListener() {
        // Initialize the listener.
        InetSocketAddress address;
        if (host.equalsIgnoreCase("ANY") || host.equalsIgnoreCase("0.0.0.0")) {
            plugin.getLogger().log(Level.INFO, "Starting PortListener server on *:" + Integer.toString(port));
            address = new InetSocketAddress(port);
        } else {
            plugin.getLogger().log(Level.INFO, "Starting PortListener server on " + host + ":" + Integer.toString(port));
            address = new InetSocketAddress(host, port);
        }
        try {
            listener = new ServerSocket();
            listener.bind(address);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.INFO, "Could not bind to port!");
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
                (new RequestHandler(plugin, socket)).start();
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
