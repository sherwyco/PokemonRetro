package com.herokuapp.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import com.herokuapp.GameState.GameStateManager;
import com.herokuapp.HUD.Notification;
import com.herokuapp.TileMaps.Tilemap;
import com.herokuapp.database.Database;
import com.herokuapp.misc.GlobalVariables;
import com.herokuapp.sprite.Animation;
import com.herokuapp.sprite.Spritesheet;

public class Player {
  public final String username = "admin";
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
  GameStateManager gsm;
  // private String[] hostAttacks = {"WATER", "FIRE", "WIND"};
  // private String[] enemyAttacks = {"ELECTRIC", "ICE", "GRASS"};
  // private String[] Pokemons = {"Pikachu", "Dragonite", "Snorlax", "Charizard"};


  // private Pokemon[] pokemons =
  // {new Pokemon(getPokemonImage("jolteon"), "Jolteon", hostAttacks, "Water", 20),
  // new Pokemon(getPokemonImage("jolteon"), "Picachu", hostAttacks, "Electric", 20),
  // new Pokemon(getPokemonImage("charmander"), "charmander", hostAttacks, "Fire", 20)};
  //


  // turn to attack
  private boolean turnToAttack = false;
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
  int encounteredPokemonId = -1;
  Database db = new Database();

  public Player(int x, int y, GameStateManager gsm) {
    this.gsm = gsm;
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

    turnToAttack = false;



  }

  public boolean isTurnToAttack() {
    return turnToAttack;
  }

  public void setTurnToAttack(boolean turnToAttack) {
    this.turnToAttack = turnToAttack;

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
      int chance = random.nextInt(4);
      System.out.println(chance);
      if (chance <= 1) {
        encounteredPokemonId = random.nextInt(27) + 1;
        System.out.println(encounteredPokemonId);
        // Pokemon encounteredPokemon = db.getPokemon(encounteredPokemonId);
        notificationTest = new Notification(db.getPokemon(encounteredPokemonId).getName());

        hasEncounteredPokemon = true;
      }
    }
  }

  public int getEncounteredPokemonId() {
    return encounteredPokemonId;
  }

  // public Pokemon[] sendPokemons() {
  // return pokemons;
  //
  // }

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

  public void changeLevel(int levelState) {
    gsm.setState(levelState);
  }

  public void moveUp() {
    // y -= speed;
    if (!isMoving) {
      pose = 1;
      if (!level.tiles[xTile()][yTile() - 1].hasCollision) {
        y -= tileSize;
        isMoving = true;
        level.tiles[xTile()][yTile()].steppedOn(this);
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
        level.tiles[xTile()][yTile()].steppedOn(this);
        findPokemon();
      }
    }
  }

  public BufferedImage getPokemonImage(String pokemon) {
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File("src/main/resources/Pokemon/" + pokemon + ".png"));
    } catch (IOException e) {

      e.printStackTrace();
    }
    return img;
  }

  public void moveLeft() {

    if (!isMoving) {
      pose = 2;
      if (!level.tiles[xTile() - 1][yTile()].hasCollision) {
        x -= tileSize;
        isMoving = true;
        level.tiles[xTile()][yTile()].steppedOn(this);
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
        level.tiles[xTile()][yTile()].steppedOn(this);
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

