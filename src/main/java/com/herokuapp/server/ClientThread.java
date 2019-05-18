package com.herokuapp.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.herokuapp.player.DummyPlayer;

public class ClientThread implements Runnable {
  public final static String HOST = "127.0.0.1";
  public final static int TIMEOUT = 1000;

  public final static int PORT_TCP = 56555;
  public final static int PORT_UDP = 56777;

  private Client client;
  private Listener listener;

  @Override
  public void run() {
    listener = new ClientListener();
    client = new Client();
    client.getKryo().register(DummyPlayer.class);
    client.getKryo().register(Ping.class);
    client.getKryo().register(Pong.class);
    client.addListener(listener);
    client.start();
    System.out.println("finding server...");
    InetAddress adr = client.discoverHost(56777, 5000);
    if (adr == null) {
      System.out.println("No server found!");
      System.exit(-1);
    } else {
      System.out.println("found server at " + adr);
    }
    try {
      client.connect(TIMEOUT, adr.getHostAddress(), PORT_TCP, PORT_UDP);
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      System.out.println("Connected to server " + adr.getHostName());
    }

    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        client.sendTCP(new Ping());
      }
    }, 1000, 1000);
  }

  class ClientListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
      System.out.println("Server resposnse: " + object);
    }

    @Override
    public void disconnected(Connection c) {
      System.out.println("Connection to server " + c.getEndPoint() + " has been lost!");
      System.exit(-1);
    }
  }
}
