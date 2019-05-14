package com.herokuapp.sprite;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import com.herokuapp.misc.GlobalVariables;
import com.herokuapp.player.Camera;

public class SpriteSingle {

  int x;
  private int y;
  int width;
  int height;
  int offsetX = 0;
  int offsetY = 0;
  String fileName;
  Image img;


  public SpriteSingle(int x, int y, int width, int height, String fileName) {
    this.x = x;
    this.setY(y);
    this.width = width;
    this.height = height;
    this.fileName = fileName;
    img = Toolkit.getDefaultToolkit().getImage(fileName);
  }

  public SpriteSingle(int x, int y, int width, int height, int offsetX, int offsetY,
      String fileName) {
    this.x = x;
    this.setY(y);
    this.width = width;
    this.height = height;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.fileName = fileName;
    img = Toolkit.getDefaultToolkit().getImage(fileName);
  }

  public SpriteSingle(int x, int y, String fileName) {
    this.x = x;
    this.setY(y);
    this.fileName = fileName;
    img = Toolkit.getDefaultToolkit().getImage(fileName);
    this.width = img.getWidth(null);
    this.height = img.getHeight(null);
  }

  public void moveBy(int dx, int dy) {
    x += dx;
    setY(getY() + dy);
  }


  public void moveUp(int dist) {
    setY(getY() - dist);
  }

  public void moveDown(int dist) {
    setY(getY() + dist);
  }

  public void moveLeft(int dist) {
    x -= dist;
  }

  public void moveRight(int dist) {
    x += dist;
  }

  public void draw(Graphics g) {
    // System.out.println(fileName);
    // System.out.println(x);
    g.drawImage(img, x - Camera.x + GlobalVariables.screenWidth / 2 + offsetX,
        y - Camera.y + GlobalVariables.screenHeight / 2 + offsetY, width, height, null);
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

}
