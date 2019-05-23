package com.herokuapp.TileMaps;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import com.herokuapp.sprite.SpriteSingle;

public class Tilemap {
  public Tile[][] tiles;
  private ArrayList<SpriteSingle> sprites;
  int width;
  int height;

  public Tilemap(int width, int height) {
    tiles = new Tile[width][height];
    sprites = new ArrayList<SpriteSingle>();
    this.width = width;
    this.height = height;
  }

  public int getWidth() {
    return tiles.length;
  }

  public int getHeight() {
    return tiles[0].length;
  }

  public Tile getTile(int x, int y) {
    if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
      return null;
    }
    return tiles[x][y];
  }

  public void setTile(int x, int y, Tile tile) {
    tiles[x][y] = tile;
  }

  public void addSprite(SpriteSingle sprite) {
    sprites.add(sprite);
  }


  public void draw(Graphics g) {
    // ((Tile_Animated) tiles[1][1]).update(); // for animated water, uncomment, change tile to
    // tile_animated in tilemap utility class, and image filename to water_animation, too laggy for
    // now
    g.setColor(Color.BLACK);
    g.clearRect(0, 0, 5000, 50000);
    g.fillRect(0, 0, 5000, 5000);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (tiles[x][y] != null) {
          tiles[x][y].draw(g, x * 32, y * 32);
        }
      }
    }
  }

  public void drawSpritesBelow(Graphics g, int playerHeight) {
    for (int i = 0; i < sprites.size(); i++) {
      if (playerHeight <= sprites.get(i).getY()) {
        sprites.get(i).draw(g);
      }
    }
  }

  public void drawSpritesAbove(Graphics g, int playerHeight) {
    for (int i = 0; i < sprites.size(); i++) {
      if (playerHeight > sprites.get(i).getY()) {
        sprites.get(i).draw(g);
      }
    }
  }



}

