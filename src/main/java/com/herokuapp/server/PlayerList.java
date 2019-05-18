package com.herokuapp.server;

import java.util.ArrayList;
import com.herokuapp.player.DummyPlayer;

public class PlayerList {
  private ArrayList<DummyPlayer> list = null;

  public ArrayList<DummyPlayer> getList() {
    return list;
  }

  public void setList(ArrayList<DummyPlayer> list) {
    this.list = (ArrayList<DummyPlayer>) list;
  }
}
