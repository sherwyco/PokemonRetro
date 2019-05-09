package com.herokuapp.TileMaps;

import java.awt.Graphics;
import com.herokuapp.sprite.SpriteSingle;

public class Tile {
  public boolean hasCollision;
  public SpriteSingle sprite;
  String filename;
  int x;
  int y;
  public boolean hasPokemon = false;

  public Tile(int x, int y, String filename, boolean hasCollision) {
    this.x = x;
    this.y = y;
    this.filename = filename;
    this.hasCollision = hasCollision;
    sprite = new SpriteSingle(x, y, 32, 32, filename);
  }

  public Tile(int x, int y, String filename, boolean hasCollision, boolean hasPokemon) {
    this.x = x;
    this.y = y;
    this.filename = filename;
    this.hasCollision = hasCollision;
    sprite = new SpriteSingle(x, y, 32, 32, filename);
    this.hasPokemon = hasPokemon;
  }

  public boolean hasCollision() {
    return hasCollision;
  }

  public void draw(Graphics g) {
    sprite.draw(g);
  }

}
