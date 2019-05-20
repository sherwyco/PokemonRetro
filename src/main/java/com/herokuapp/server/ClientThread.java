package com.herokuapp.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JOptionPane;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.herokuapp.server.Network.ConnectionId;
import com.herokuapp.server.Network.PingServer;
import com.herokuapp.server.Network.PlayerCoords;
import com.herokuapp.server.Network.UpdateCoords;

public class ClientThread implements Runnable {
  public final static String HOST = "127.0.0.1";
  public final static int TIMEOUT = 1000;

  public Client client;
  private Listener listener;
  public HashMap<Integer, PlayerCoords> map = null;
  public ArrayList<DummyPlayer> playerList = null;
  public int myClientId = 0;
  public UpdateCoords coords = null;

  @Override
  public void run() {
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

  class ClientListener extends Listener {
    @SuppressWarnings("unchecked")
    @Override
    public void received(Connection c, Object obj) {
      if (obj instanceof PingServer) {
        System.out.println("Server: " + obj);
        return;
      }
      if (obj instanceof ConnectionId) {
        ConnectionId connId = (ConnectionId) obj;
        myClientId = connId.clientId; // get my id from server
      }
      if (obj instanceof HashMap) {
        playerList = new ArrayList<DummyPlayer>(); // initialize a new arraylist
        System.out.println("Its a hashmap!");
        map = new HashMap<Integer, PlayerCoords>();
        map.putAll((Map<? extends Integer, ? extends PlayerCoords>) obj);
        for (Entry<Integer, PlayerCoords> entry : map.entrySet()) {
          int x = entry.getValue().x;
          int y = entry.getValue().y;
          int id = entry.getValue().clientId;
          System.out.println(String.format("x: %d, y: %d id: %d", x, y, id));
          playerList.add(new DummyPlayer(x, y, id));
          System.out.println(Arrays.toString(playerList.toArray()));
        }
        return;
      }
      if (obj instanceof UpdateCoords) {
        UpdateCoords newCoords = (UpdateCoords) obj;
        if (newCoords.clientId != myClientId) {
          // update the map with new coords
          System.out.println("got new coordinates for client: " + newCoords.clientId);
          map.replace(newCoords.clientId,
              new PlayerCoords(newCoords.x, newCoords.y, newCoords.clientId));
          coords = (UpdateCoords) newCoords;
        }
        return;
      }
    }

    @Override
    public void disconnected(Connection c) {
      System.out.println("Connection to server " + c.getID() + " has been lost!");

      JOptionPane.showMessageDialog(null, "Server is down!", "Server error",
          JOptionPane.ERROR_MESSAGE);
    }
  }
}


