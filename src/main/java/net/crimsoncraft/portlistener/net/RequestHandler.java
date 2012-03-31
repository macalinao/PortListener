/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crimsoncraft.portlistener.net;

import com.crimsonrpg.pvpranker.CrimsonPvP;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;

/**
 *
 * @author simplyianm
 */
public class RequestHandler extends Thread {
    private Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Read the request and handle it.
            handleRequest(socket, reader.readLine());

            // Finally close the socket.
            socket.close();
        } catch (IOException ignored) {
        } catch (Exception ex) {
            CrimsonPvP.log(Level.SEVERE, "Uh oh! Something unexpected happened.", ex);
        }
    }

    private void handleRequest(Socket socket, String message) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        if (parseRequest(message)) {
            out.writeBytes("GOOD");
        } else {
            out.writeBytes("BAD");
        }
    }

    private boolean parseRequest(String request) {
        String[] parts = request.split(";");
        if (parts.length < 3) {
            return false;
        }

        String id = parts[0];
        String name = parts[1];
        String pass = parts[2];

        if (!pass.equals("234nu89cuc2u")) {
            return false;
        }

        if (!id.equals("1")) {
            return false;
        }

        CrimsonPvP.getInstance().getVoteHandler().addVoter(name);

        return true;
    }

}
