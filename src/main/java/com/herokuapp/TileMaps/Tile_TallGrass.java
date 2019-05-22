package com.herokuapp.TileMaps;

import java.awt.Graphics;
import com.herokuapp.misc.GlobalVariables;
import com.herokuapp.player.Player;
import com.herokuapp.sprite.SpriteAnimated;

public class Tile_TallGrass extends Tile {

  SpriteAnimated spritesheet;
  int frame = 0;
  boolean steppedOn;
  int animationDelay = 10;
  boolean firstTime = true;

  public Tile_TallGrass(int x, int y, String filename, boolean hasCollision) {
    super(x, y, filename, hasCollision, true);
    spritesheet = new SpriteAnimated(x, y, 1, 2, 2, 10, filename);
    spritesheet.setScale(GlobalVariables.GAME_SCALE, GlobalVariables.GAME_SCALE);
  }

  public void steppedOn(Player player) {
    System.out.println("grass stepped on");
    steppedOn = true;

  }

  public int nextFrame() {
    if (frame == 0) {
      return 1;
    }
    if (frame == 1) {
      return 0;
    }
    return -1;
  }

  public void flipFrame() {
    if (firstTime) {
      frame = nextFrame();
      firstTime = false;
    } else {
      frame = nextFrame();
      steppedOn = false;
      firstTime = true;
    }

  }


  public void draw(Graphics g) {
    spritesheet.drawSpecificFrame(g, frame);
    if (steppedOn) {
      animationDelay--;
      if (animationDelay <= 0) {
        flipFrame();
        animationDelay = 10;
      }
    }
  }
}
