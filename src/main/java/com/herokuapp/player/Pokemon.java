package com.herokuapp.player;

public class Pokemon {
  private String name, type, current_move;
  private String[] moves;
  public int health;


  public Pokemon(String name, String[] moves, String type, int health) {

    this.name = name;
    this.type = type;
    this.moves = moves;
    setCurrent_move(moves[0]);
    this.health = health;
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
