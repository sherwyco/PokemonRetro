package com.herokuapp.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

  private ArrayList<Integer> playerId = new ArrayList<Integer>();
  private HashMap<Integer, DummyPlayer> dummyMap = new HashMap<Integer, DummyPlayer>();

  @Override
  public void run() {

    listener = new ServerListener();
    server = new Server();
    // register the class
    server.getKryo().register(UpdateCoords.class);
    server.getKryo().register(DummyPlayer.class);
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
    public void received(Connection c, Object o) {
      System.out.println(o);
      c.sendUDP(new Pong());
      if (o instanceof Ping) {
        System.out.println("its a ping!!");
      }
      // int id = c.getID();
      // if (object instanceof DummyPlayer) {
      // // add this to the map of connected players
      // DummyPlayer new_player = (DummyPlayer) object;
      // dummyMap.put(id, new_player);
      // } else if (object instanceof UpdateCoords) {
      // UpdateCoords newCoords = (UpdateCoords) object;
      // DummyPlayer dummy = dummyMap.get(id);
      // dummy.setX(newCoords.x);
      // dummy.setY(newCoords.y);
      // dummyMap.replace(id, dummy);
      // }
      //
      //
      // ArrayList<DummyPlayer> playersToSend = new ArrayList<DummyPlayer>();
      // for (int i = playerId.get(c.getID() + 1); i < playerId.size(); i++) {
      // playersToSend.add(dummyMap.get(c.getID()));
      // }
      // c.sendUDP(playersToSend);
    }

    @Override
    public void connected(Connection c) {
      System.out.println("client " + c.getID() + " has connected");
    }

    @Override
    public void disconnected(Connection c) {
      System.out.println("client " + c.getID() + " has disconnected!");
      // remove connectedPlayers to map and arraylist
    }
  }
}
