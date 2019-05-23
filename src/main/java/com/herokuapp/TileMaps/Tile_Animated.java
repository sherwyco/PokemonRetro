package com.herokuapp.TileMaps;

import java.awt.Graphics;
import com.herokuapp.misc.GlobalVariables;
import com.herokuapp.sprite.SpriteAnimated;

public class Tile_Animated extends Tile {

  // too laggy
  SpriteAnimated spritesheet;

  public Tile_Animated(int x, int y, String filename, boolean hasCollision) {
    super(x, y, filename, hasCollision, true);
    spritesheet = new SpriteAnimated(x, y, 1, 3, 3, 10, filename);
    spritesheet.setScale(GlobalVariables.GAME_SCALE, GlobalVariables.GAME_SCALE);
  }

  public void update() {
    spritesheet.update();
  }

  public void draw(Graphics g, int positionX, int positionY) {
    spritesheet.drawAtPosition(g, positionX, positionY);
  }

}
