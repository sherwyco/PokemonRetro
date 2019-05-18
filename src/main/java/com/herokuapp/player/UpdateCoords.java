package com.herokuapp.player;

public class UpdateCoords {
  public int x;
  public int y;
  public Boolean moveLeft = false;
  public Boolean moveRight = false;
  public Boolean moveUp = false;
  public Boolean moveDown = false;
  //
  // public enum Movement {
  // Left, Right, Up, Down
  // }

  public UpdateCoords(int x, int y) {
    this.x = x;
    this.y = y;
    // switch (type) {
    // case Left:
    // moveLeft = true;
    // break;
    // case Right:
    // moveRight = true;
    // break;
    // case Up:
    // moveUp = true;
    // case Down:
    // moveDown = true;
    // default:
    // break;
    // }
  }

  @Override
  public String toString() {
    String coords = this.x + ":" + this.y;
    return coords;
  }

}
