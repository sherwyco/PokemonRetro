package com.herokuapp.player;

import java.awt.Graphics;
import java.util.Random;
import com.herokuapp.HUD.Notification;
import com.herokuapp.TileMaps.Tilemap;
import com.herokuapp.misc.GlobalVariables;
import com.herokuapp.sprite.Animation;
import com.herokuapp.sprite.Spritesheet;

public class Player {
  int x; // actual position
  private int y;
  int spriteX; // visual position
  int spriteY;
  int width = 15;
  int height = 22;
  int speed = 3;
  int tileSize = 32;
  final int INITIAL_MOVE_DELAY = 12;
  final int SPRITE_CATCH_UP_SPEED = 2;
  Tilemap level;

  Spritesheet spritesheet;
  Animation down;
  Animation up;
  Animation left;
  boolean isMoving = false;
  int pose = 0;
  Animation[] anims = new Animation[4];

  // placeholder system to spawn pokemon
  Notification notificationTest = null;
  Random random = new Random();
  boolean hasEncounteredPokemon = false;

  public Player(int x, int y) {
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
  }

  public void findPokemon() {
    hasEncounteredPokemon = false;
    if (level.tiles[xTile()][yTile()].hasPokemon) {
      int hi = random.nextInt(4);
      System.out.println(hi);
      if (hi <= 1) {
        notificationTest = new Notification();
        hasEncounteredPokemon = true;
      }
    }
  }

  public void setTile(int x, int y) {
    this.x = x * 16 * GlobalVariables.GAME_SCALE + 16;
    this.spriteX = this.x;
    Camera.x = this.x;
    this.y = y * 16 * GlobalVariables.GAME_SCALE + 22;
    this.spriteY = this.y;
    Camera.y = this.y;
  }

  public boolean hasEncounteredPokemon() {
    return hasEncounteredPokemon;
  }

  public void moveUp() {
    // y -= speed;
    if (!isMoving) {
      pose = 1;
      if (!level.tiles[xTile()][yTile() - 1].hasCollision) {
        y -= tileSize;
        isMoving = true;
        findPokemon();
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
        findPokemon();
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
        findPokemon();
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
        findPokemon();
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

  public void update() {
    // visual position and camera position catch up to logical position for smooth grid based
    // movement
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
    if (spriteX == x && spriteY == y) {
      isMoving = false;
    }
    // System.out.println(x + " | " + spriteX); logical position and visual position
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

  public void drawHUD(Graphics g) {
    if (notificationTest != null && hasEncounteredPokemon) {
      notificationTest.draw(g);
    }
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

}

