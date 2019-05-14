package com.herokuapp.TileMaps;

public class Tile_LevelChange extends Tile {

  public Tile_LevelChange(int x, int y, String filename, boolean hasCollision) {
    super(x, y, filename, hasCollision);
    // TODO Auto-generated constructor stub
  }


  public void steppedOn() {
    System.out.println("Change level now");

  }

}
