package com.herokuapp.TileMaps;

import com.herokuapp.player.Player;

public class Tile_LevelChange extends Tile {

  int levelToChangeTo;
  int xSpawnLocation; // not yet used
  int ySpawnLocation; // not yet used


  public Tile_LevelChange(int x, int y, String filename, boolean hasCollision, int xSpawnLocation,
      int ySpawnLocation, int levelStateToChangeTo) {
    super(x, y, filename, hasCollision);
    this.levelToChangeTo = levelStateToChangeTo;
    this.xSpawnLocation = xSpawnLocation;
    this.ySpawnLocation = ySpawnLocation;
  }


  public void steppedOn(Player player) {
    System.out.println("Change level now");
    player.changeLevel(levelToChangeTo);

  }

}
