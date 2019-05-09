package com.herokuapp.player;

import java.awt.Graphics;
import com.herokuapp.TileMaps.Tilemap;
import com.herokuapp.misc.GlobalVariables;
import com.herokuapp.sprite.Animation;
import com.herokuapp.sprite.Spritesheet;

public class Player {

  int x; // actual position
  int y;
  int spriteX; // visual position
  int spriteY;
  int width = 15;
  int height = 22;
  int speed = 3;
  int tileSize = 32;
  int moveDelay = 0;
  final int INITIAL_MOVE_DELAY = 12;
  final int SPRITE_CATCH_UP_SPEED = 2;
  Tilemap level;



  // turn to attack
  private boolean turnToAttack = false;

  Spritesheet spritesheet;
  Animation down;
  Animation up;
  Animation left;
  boolean isMoving = false;
  int pose = 0;
  Animation[] anims = new Animation[4];

  public Player(int x, int y) {
    spritesheet = new Spritesheet(x, y, 15, 22, 6, 4, 15,
        "src/main/resources/sprites/pokemonPlayerWalking.png");
    anims[0] = new Animation(spritesheet, 0, 4, 10);
    anims[1] = new Animation(spritesheet, 1, 4, 10);
    anims[2] = new Animation(spritesheet, 2, 4, 10);
    anims[3] = new Animation(spritesheet, 3, 4, 10);
    this.x = x;
    this.y = y;
    spriteX = x;
    spriteY = y;
    Camera.x = x;
    Camera.y = y;
    turnToAttack = false;
  }

  public boolean isTurnToAttack() {
    return turnToAttack;
  }

  public void setTurnToAttack(boolean turnToAttack) {
    this.turnToAttack = turnToAttack;
  }

  public void moveUp() {
    // y -= speed;
    if (!isMoving) {
      pose = 1;
      if (!level.tiles[xTile()][yTile() - 1].hasCollision) {
        y -= tileSize;
        isMoving = true;
      }
    }
  }

  public void moveDown() {
    // y += speed;
    if (!isMoving) {
      System.out.println(xTile() + "|" + yTile());
      pose = 0;
      if (!level.tiles[xTile()][yTile() + 1].hasCollision) {
        y += tileSize;
        isMoving = true;
      }
    }
  }

  public void moveLeft() {
    // x -= speed;
    if (!isMoving) {
      pose = 2;
      if (!level.tiles[xTile() - 1][yTile()].hasCollision) {
        x -= tileSize;
        isMoving = true;
      }
    }
  }

  public void moveRight() {
    // x += speed;
    if (!isMoving) {
      pose = 3;
      if (!level.tiles[xTile() + 1][yTile()].hasCollision) {
        x += tileSize;
        isMoving = true;
      }
    }
  }

  public void setLevel(Tilemap level) {
    this.level = level;
  }

  public int xTile() {
    return (x - 16) / 32;
  }

  public int yTile() {
    return (y - 22) / 32;
  }

  public void draw(Graphics g) {
    // spritesheet.draw(g);
    if (spriteX < x) {
      spriteX += SPRITE_CATCH_UP_SPEED;
      Camera.x += SPRITE_CATCH_UP_SPEED;
    }
    if (spriteX > x) {
      spriteX -= SPRITE_CATCH_UP_SPEED;
      Camera.x -= SPRITE_CATCH_UP_SPEED;
    }
    if (spriteY < y) {
      spriteY += SPRITE_CATCH_UP_SPEED;
      Camera.y += SPRITE_CATCH_UP_SPEED;
    }
    if (spriteY > y) {
      spriteY -= SPRITE_CATCH_UP_SPEED;
      Camera.y -= SPRITE_CATCH_UP_SPEED;
    }

    // linear interpolation does not work right unless sprite coordinates are doubles/floats
    // spriteX = (spriteX * (1.0 - .05) + (this.x * .05));
    // spriteY = (spriteY * (1.0 - .05) + (this.y * .05));
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

    if (spriteX == x && spriteY == y) {
      isMoving = false;
    }
    // System.out.println(x + " | " + spriteX); logical position and visual position
    moveDelay--;
  }



}
