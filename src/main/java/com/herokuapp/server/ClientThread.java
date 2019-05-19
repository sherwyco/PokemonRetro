package com.herokuapp.server;

import java.io.IOException;
import java.net.InetAddress;
import javax.swing.JOptionPane;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.herokuapp.server.Network.PingServer;
import com.herokuapp.server.Network.PlayerList;

public class ClientThread implements Runnable {
  public final static String HOST = "127.0.0.1";
  public final static int TIMEOUT = 1000;

  public Client client;
  private Listener listener;

  @Override
  public void run() {
    Log.set(Log.LEVEL_DEBUG);
    Log.set(Log.LEVEL_TRACE);
    Log.set(Log.LEVEL_ERROR);


    listener = new ClientListener();
    client = new Client();
    Network.register(client);
    client.addListener(listener);
    client.start();
    System.out.println("finding server...");
    // scan udp port servers
    InetAddress adr = client.discoverHost(Network.PORT_UDP, 5000);
    if (adr == null) {
      System.out.println("No server found!");
      System.exit(-1);
    } else {
      System.out.println("found server at " + adr);
    }
    try {
      client.connect(TIMEOUT, adr.getHostAddress(), Network.PORT_TCP, Network.PORT_UDP);
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      System.out.println("Connected to server " + adr.getHostName());
    }
  }

}



class ClientListener extends Listener {
  @Override
  public void received(Connection c, Object obj) {
    if (obj instanceof PingServer) {
      System.out.println("Server: " + obj);
    }
    if (obj instanceof PlayerList) {
      System.out.println("hashmap received from server: " + obj);
    }
  }

  @Override
  public void disconnected(Connection c) {
    System.out.println("Connection to server " + c.getID() + " has been lost!");

    JOptionPane.showMessageDialog(null, "Server is down!", "Server error",
        JOptionPane.ERROR_MESSAGE);
  }

}

