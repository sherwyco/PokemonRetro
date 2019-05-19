package com.herokuapp.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.herokuapp.server.Network.OnlineUsers;
import com.herokuapp.server.Network.PingServer;
import com.herokuapp.server.Network.PlayerList;

public class ServerThread implements Runnable {

  public final static int PORT_TCP = 56555;
  public final static int PORT_UDP = 56777;

  private AtomicBoolean ready = new AtomicBoolean(false);
  private Server server;
  private Listener listener;
  HashMap<Integer, DummyPlayer> map = new HashMap<Integer, DummyPlayer>();
  OnlineUsers ol = new OnlineUsers();

  @Override
  public void run() {
    Log.set(Log.LEVEL_DEBUG);
    Log.set(Log.LEVEL_TRACE);
    Log.set(Log.LEVEL_ERROR);
    listener = new ServerListener();
    server = new Server();
    // register the class
    Network.register(server);
    server.addListener(listener);
    System.out.println("Server started!");
    server.start();
    try {
      server.bind(Network.PORT_TCP, Network.PORT_UDP);
      ready.set(true);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public boolean isReady() {
    return ready.get();
  }

  class ServerListener extends Listener {
    @Override
    public void received(Connection c, Object obj) {
      if (obj instanceof PingServer) {
        System.out.println("Client: " + obj);
        PingServer test = new PingServer();
        test.msg = "Pong";
        // send pong back to sender of ping
        c.sendTCP(test);
        return;
      }
    }

    @Override
    public void connected(Connection c) {
      System.out.println("client " + c.getID() + " has connected");
      ol.totalUsers++; // increment by 1
      System.out.println("total users connected: " + ol.totalUsers);
      map.put(c.getID(), new DummyPlayer(50, 30));
      System.out.println(map.get(c.getID()));
      // send an updated map to all except the newly connected client;
      server.sendToAllUDP(new PlayerList(map));
    }

    @Override
    public void disconnected(Connection c) {
      System.out.println("client " + c.getID() + " has disconnected!");
      map.remove(c.getID());
      ol.totalUsers--;// reduce by one
    }
  }
}
