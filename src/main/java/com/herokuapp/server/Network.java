package com.herokuapp.server;

import java.util.HashMap;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

/**
 * This class is for conveniently placing all classes common to client and server.
 */
public class Network {
  public final static int PORT_TCP = 56555;
  public final static int PORT_UDP = 56777;

  public static void register(EndPoint endPoint) {
    Kryo kryo = endPoint.getKryo();
    kryo.register(HashMap.class);
    kryo.register(PlayerList.class);
    kryo.register(PingServer.class);
    kryo.register(OnlineUsers.class);
    kryo.register(DummyPlayer.class);
  }


  public static class PlayerList {

    public HashMap<Integer, DummyPlayer> players;

  }

  public static class OnlineUsers {
    public int totalUsers;
  }

  public static class UpdateCoords {
    public int x;
    public int y;
    public movement type;
    public int clientId;

    public enum movement {
      Left, Right, Down, Up
    }

    public UpdateCoords() {

    }

    public UpdateCoords(int x, int y, movement type, int clientId) {
      this.x = x;
      this.y = y;
      this.type = type;
      this.clientId = clientId;
    }
  }

  public static class PingServer {
    public String msg;

    @Override
    public String toString() {
      return msg;
    }
  }
}
