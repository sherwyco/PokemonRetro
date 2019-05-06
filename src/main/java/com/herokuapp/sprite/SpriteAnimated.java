package com.herokuapp.sprite;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteAnimated {
  int x;
  int y;
  int width;
  int height;
  double scaleX = 1;
  double scaleY = 1;
  int rows;
  int columns;
  int count;
  int currentFrame = 0;
  BufferedImage spritesheet;
  BufferedImage[] sprites;
  int delay = 10;
  int currentDelay = delay;

  // count not currently used
  public SpriteAnimated(int x, int y, int rows, int columns, int count, String fileName) {
    this.x = x;
    this.y = y;
    this.rows = rows;
    this.columns = columns;
    this.count = count;

    try {
      spritesheet = ImageIO.read(new File(fileName));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      System.out.println(fileName + " not found");
      e.printStackTrace();
    }

    this.width = spritesheet.getWidth() / columns;
    this.height = spritesheet.getHeight() / rows;

    sprites = new BufferedImage[rows * columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        sprites[(i * columns) + j] = spritesheet.getSubimage(j * width, i * height, width, height);
      }
    }

  }

  public void setScale(int scaleX, int scaleY) {
    this.scaleX = scaleX;
    this.scaleY = scaleY;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public void moveUp(int dist) {
    y -= dist;
  }

  public void moveDown(int dist) {
    y += dist;
  }

  public void moveLeft(int dist) {
    x -= dist;
  }

  public void moveRight(int dist) {
    x += dist;
  }

  public void update() {
    if (currentDelay <= 0) {
      currentDelay = 7;
      // if (currentFrame == 5) {
      // delay = 50;
      // }
      currentFrame++;
      if (currentFrame > rows * columns - 1) {
        currentFrame = 0;
      }
    }
    currentDelay--;

  }

  public void draw(Graphics g) {

    g.drawImage(sprites[currentFrame], x, y, (int) (width * scaleX), (int) (height * scaleY), null);

  }

}
