package com.herokuapp.misc;

import com.herokuapp.Panels.GamePanel;
import com.herokuapp.TileMaps.Tilemap;
import com.herokuapp.utils.MouseManager;

public class Handler {

  private GamePanel game;
  private Tilemap world;

  public Handler(GamePanel game) {
    this.game = game;
  }



  public MouseManager getMouseManager() {
    return game.getMouseManager();
  }

  public int getWidth() {
    return game.getWidth();
  }

  public int getHeight() {
    return game.getHeight();
  }

  public GamePanel getGame() {
    return game;
  }

  public void setGame(GamePanel game) {
    this.game = game;
  }

  public Tilemap getWorld() {
    return world;
  }

  public void setWorld(Tilemap world) {
    this.world = world;
  }

}
