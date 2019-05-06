package com.herokuapp.TileMaps;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Tilemap {
  public Tile[][] tiles;
  private LinkedList sprites;
  int width;
  int height;

  public Tilemap(int width, int height) {
    tiles = new Tile[width][height];
    sprites = new LinkedList();
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

  // public Iterator getSprites() {
  // return sprites.iterator();
  // }


  public void draw(Graphics g) {
    g.setColor(Color.BLACK);
    g.clearRect(0, 0, 5000, 50000);
    g.fillRect(0, 0, 5000, 5000);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (tiles[x][y] != null) {
          tiles[x][y].draw(g);
        }
      }
    }
  }



}
