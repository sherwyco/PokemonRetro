package com.herokuapp.server;

public class UpdateCoords {

  private int x;
  private int y;

  private Boolean moveLeft = false;
  private Boolean moveRight = false;
  private Boolean moveUp = false;
  private Boolean moveDown = false;


  @Override
  public String toString() {

    return this.x + ":" + this.y;
  }

  public Boolean getMoveLeft() {
    return moveLeft;
  }

  public void setMoveLeft(Boolean moveLeft) {
    this.moveLeft = moveLeft;
  }

  public Boolean getMoveRight() {
    return moveRight;
  }

  public void setMoveRight(Boolean moveRight) {
    this.moveRight = moveRight;
  }

  public Boolean getMoveUp() {
    return moveUp;
  }

  public void setMoveUp(Boolean moveUp) {
    this.moveUp = moveUp;
  }

  public Boolean getMoveDown() {
    return moveDown;
  }

  public void setMoveDown(Boolean moveDown) {
    this.moveDown = moveDown;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

}
