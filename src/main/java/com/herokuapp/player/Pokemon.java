package com.herokuapp.player;

import java.awt.image.BufferedImage;

public class Pokemon {
  private String name, type, current_move, defense;
  private String[] moves;
  public int health;
  private BufferedImage image;



  public Pokemon(BufferedImage image, String name, String[] moves, String type, int health) {
    this.image = image;
    this.name = name;
    this.type = type;
    this.moves = moves;
    setCurrent_move(moves[0]);
    this.health = health;


  }


  private void setDefense(String defense) {
    this.defense = defense;
  }

  public String getDefense() {
    return defense;
  }


  public BufferedImage getImage() {
    return image;
  }



  public void setImage(BufferedImage image) {
    this.image = image;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String[] getMoves() {
    return moves;
  }

  public void setMoves(String[] moves) {
    this.moves = moves;
  }



  public int getHealth() {
    return health;
  }



  public void setHealth(int health) {
    this.health = health;
  }



  public String getCurrent_move() {
    return current_move;
  }



  public void setCurrent_move(String current_move) {
    this.current_move = current_move;
  }



}
