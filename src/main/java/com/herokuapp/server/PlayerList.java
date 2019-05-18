package com.herokuapp.server;

import java.util.ArrayList;
import java.util.Arrays;
import com.herokuapp.player.DummyPlayer;

public class PlayerList {
  private ArrayList<DummyPlayer> list = null;

  public ArrayList<DummyPlayer> getList() {
    return list;
  }

  public void setList(ArrayList<DummyPlayer> list) {
    this.list = new ArrayList<DummyPlayer>(list);
  }

  @Override
  public String toString() {
    if (list != null) {
      return Arrays.toString(list.toArray());
    } else {
      return "Empty";
    }
  }
}
