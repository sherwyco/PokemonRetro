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
    kryo.register(ConnectionId.class);
    kryo.register(OnlineUsers.class);
    kryo.register(PlayerCoords.class);
    kryo.register(Enum.class);
    kryo.register(UpdateCoords.class);
    kryo.register(HashMap.class);
    kryo.register(PingServer.class);
  }


  public static class ConnectionId {
    public int clientId;

    public ConnectionId() {}

    public ConnectionId(int id) {
      clientId = id;
    }

  }

  public static class OnlineUsers {
    public int totalUsers;
  }

  public static class PlayerCoords {
    public int x;
    public int y;
    public int clientId;

    public PlayerCoords() {

    }

    public PlayerCoords(int x, int y, int clientId) {
      this.x = x;
      this.y = y;
      this.clientId = clientId;
    }

  }
  public static class UpdateCoords {
    public int x;
    public int y;
    public int clientId;
    public movement type;

    public enum movement {
      Left, Right, Down, Up
    }

    public UpdateCoords() {

    }

    public UpdateCoords(int x, int y, int clientId, movement type) {
      this.x = x;
      this.y = y;
      this.clientId = clientId;
      this.type = type;
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
