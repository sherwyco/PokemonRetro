package com.herokuapp.server;

import java.awt.Graphics;
import com.herokuapp.misc.GlobalVariables;
import com.herokuapp.player.Camera;
import com.herokuapp.sprite.Animation;
import com.herokuapp.sprite.Spritesheet;

public class DummyPlayer {
  private int x;
  private int y;
  private int spriteX; // visual position
  private int spriteY;
  private int width = 15;
  private int height = 22;
  private Spritesheet spritesheet;
  private boolean isMoving = false;
  private int pose = 0;
  private Animation[] anims = new Animation[4];
  private final int SPRITE_CATCH_UP_SPEED = 2;
  private int tileSize = 32;
  final int INITIAL_MOVE_DELAY = 12;
  public int myClientId;


  /**
   * 
   * @param x Starting X position.
   * @param y Starting Y position.
   * @param id Connection Id.
   */
  public DummyPlayer(int x, int y, int id) {
    spritesheet = new Spritesheet(x, y, 15, 22, 6, 4, 15,
        "src/main/resources/sprites/pokemonPlayerWalking.png");
    anims[0] = new Animation(spritesheet, 0, 4, 10);
    anims[1] = new Animation(spritesheet, 1, 4, 10);
    anims[2] = new Animation(spritesheet, 2, 4, 10);
    anims[3] = new Animation(spritesheet, 3, 4, 10);
    this.x = x * 16 * GlobalVariables.GAME_SCALE + 16;
    this.y = y * 16 * GlobalVariables.GAME_SCALE + 22;
    spriteX = this.x;
    spriteY = this.y;
    Camera.x = this.x;
    Camera.y = this.y;
    myClientId = id;
  }

  public int getX() {
    return x / 16 / GlobalVariables.GAME_SCALE + 16;
  }

  public void setX(int x) {
    this.x = x * 16 * GlobalVariables.GAME_SCALE + 16;
  }

  public int getY() {
    return y / 16 / GlobalVariables.GAME_SCALE + 22;
  }

  public void setY(int y) {
    this.y = y * 16 * GlobalVariables.GAME_SCALE + 22;
  }

  public int getTileSize() {
    return tileSize;
  }

  public void setTile(int x, int y) {
    this.x = x * 16 * GlobalVariables.GAME_SCALE + 16;
    this.spriteX = this.x;
    this.y = y * 16 * GlobalVariables.GAME_SCALE + 22;
    this.spriteY = this.y;
  }


  public void update() {
    // visual position and camera position catch up to logical position for smooth grid based
    // movement
    if (spriteX < x) {
      spriteX += SPRITE_CATCH_UP_SPEED;
    }
    if (spriteX > x) {
      spriteX -= SPRITE_CATCH_UP_SPEED;
    }
    if (spriteY < y) {
      spriteY += SPRITE_CATCH_UP_SPEED;
    }
    if (spriteY > y) {
      spriteY -= SPRITE_CATCH_UP_SPEED;
    }
    if (spriteX == x && spriteY == y) {
      isMoving = false;
    }
    // System.out.println(x + " | " + spriteX); // logical position and visual position
  }


  public void moveRight() {
    // x += speed;
    if (!isMoving) {
      pose = 3;
      x += tileSize;
      isMoving = true;
    }
  }

  public void moveLeft() {
    if (!isMoving) {
      pose = 2;
      x -= tileSize;
      isMoving = true;
    }
  }


  public void moveUp() {
    // y -= speed;
    if (!isMoving) {
      pose = 1;
      y -= tileSize;
      isMoving = true;
    }
  }

  public void moveDown() {
    // y += speed;
    if (!isMoving) {
      pose = 0;
      y += tileSize;
      isMoving = true;
    }
  }

  public boolean isMoving() {
    return isMoving;
  }

  public void setMoving(boolean isMoving) {
    this.isMoving = isMoving;
  }

  public void draw(Graphics g) {
    if (isMoving) {
      g.drawImage(anims[pose].getCurrentImage(),
          ((int) spriteX - width * GlobalVariables.GAME_SCALE / 2) - (int) Camera.x
              + GlobalVariables.screenWidth / 2,
          ((int) spriteY - height * GlobalVariables.GAME_SCALE) - (int) Camera.y
              + GlobalVariables.screenHeight / 2,
          width * GlobalVariables.GAME_SCALE, height * GlobalVariables.GAME_SCALE, null);
    } else {
      g.drawImage(anims[pose].getStillImage(),
          ((int) spriteX - width * GlobalVariables.GAME_SCALE / 2) - (int) Camera.x
              + GlobalVariables.screenWidth / 2,
          ((int) spriteY - height * GlobalVariables.GAME_SCALE) - (int) Camera.y
              + GlobalVariables.screenHeight / 2,
          width * GlobalVariables.GAME_SCALE, height * GlobalVariables.GAME_SCALE, null);
    }
  }
}
