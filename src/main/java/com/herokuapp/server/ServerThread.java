package com.herokuapp.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.herokuapp.player.DummyPlayer;

public class ServerThread implements Runnable {

  public final static int PORT_TCP = 56555;
  public final static int PORT_UDP = 56777;

  private AtomicBoolean ready = new AtomicBoolean(false);
  private Server server;
  private Listener listener;

  private HashMap<Integer, DummyPlayer> map = new HashMap<Integer, DummyPlayer>();

  @Override
  public void run() {
    listener = new ServerListener();
    server = new Server();
    // register the class
    server.getKryo().register(PlayerList.class);
    server.getKryo().register(DummyPlayer.class);
    server.getKryo().register(UpdateCoords.class);
    server.getKryo().register(Ping.class);
    server.getKryo().register(Pong.class);
    server.addListener(listener);
    System.out.println("Server started!");
    server.start();
    try {
      server.bind(PORT_TCP, PORT_UDP);
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
      System.out.println(obj);
      if (obj instanceof UpdateCoords) {
        UpdateCoords coords = (UpdateCoords) obj;
        map.replace(c.getID(), new DummyPlayer(coords.getX(), coords.getY()));
      }
      PlayerList pl = new PlayerList();
      // ArrayList<DummyPlayer> al = new ArrayList<DummyPlayer>();
      // // for loop
      for (Entry<Integer, DummyPlayer> entry : map.entrySet()) {
        if (c.getID() != entry.getKey()) {
          System.out.println(entry.getKey() + " /" + entry.getValue());
        }
      }
      // pl.setList(al);
      c.sendUDP(pl);
    }

    @Override
    public void connected(Connection c) {
      System.out.println("client " + c.getID() + " has connected");
      map.put(c.getID(), new DummyPlayer(50, 30));
      System.out.println(map.get(c.getID()));
    }

    @Override
    public void disconnected(Connection c) {
      System.out.println("client " + c.getID() + " has disconnected!");
      map.remove(c.getID());
    }
  }
}
