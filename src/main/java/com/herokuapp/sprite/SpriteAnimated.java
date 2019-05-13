package com.herokuapp.sprite;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.herokuapp.misc.GlobalVariables;
import com.herokuapp.player.Camera;

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
  int delay;
  int currentDelay;

  // count not currently used
  public SpriteAnimated(int x, int y, int rows, int columns, int count, int delay,
      String fileName) {
    this.x = x;
    this.y = y;
    this.rows = rows;
    this.columns = columns;
    this.count = count;
    this.delay = delay;
    currentDelay = delay;

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
      currentDelay = delay;
      currentFrame++;
      if (currentFrame > rows * columns - 1) {
        currentFrame = 0;
      }
    }
    currentDelay--;

  }

  public void drawSpecificFrame(Graphics g, int frame) {
    g.drawImage(sprites[frame], x - Camera.x + GlobalVariables.screenWidth / 2,
        y - Camera.y + GlobalVariables.screenHeight / 2, (int) (width * scaleX),
        (int) (height * scaleY), null);
  }

  public void draw(Graphics g) {

    g.drawImage(sprites[currentFrame], x - Camera.x + GlobalVariables.screenWidth / 2,
        y - Camera.y + GlobalVariables.screenHeight / 2, (int) (width * scaleX),
        (int) (height * scaleY), null);

  }

  public void drawScreenSpace(Graphics g) {

    g.drawImage(sprites[currentFrame], x, y, (int) (width * scaleX), (int) (height * scaleY), null);

  }

}
