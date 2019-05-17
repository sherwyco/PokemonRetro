package com.herokuapp.server;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerThread implements Runnable {

  public final static int PORT_TCP = 56555;
  public final static int PORT_UDP = 56777;

  private AtomicBoolean ready = new AtomicBoolean(false);
  private Server server;
  private Listener listener;

  @Override
  public void run() {

    listener = new ServerListener();
    server = new Server();
    // register the class
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
    public void received(Connection connection, Object object) {
      System.out.println("Recived from client: " + connection.getID() + " object: " + object);
      connection.sendTCP(new Pong());
    }
  }
}
