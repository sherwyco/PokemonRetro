package com.herokuapp.player;

import java.util.ArrayList;

public class Pokemon {
  private String name, type, current_move;
  private ArrayList<PokemonMoves> moves;
  private int attack, defense, health, level, exp;
  private int current_health;

  /**
   * 
   * @param name Name of Pokemon.
   * @param moves String array of Pokemon Moves
   * @param type Type of Pokemon.
   * @param atk Base Attack of Pokemon.
   * @param def Base Defense of Pokemon.
   * @param health Base HP of Pokemon.
   * @param level Level of Player's Pokemon
   * @param exp Exp of Player's Pokemon
   * @param moves ArrayList containing PokemonMoves class.
   */
  public Pokemon(String name, String type, int atk, int def, int health, int level, int exp,
      ArrayList<PokemonMoves> moves) {
    this.name = name;
    this.type = type;
    this.attack = atk;
    this.defense = def;
    this.health = health;
    this.level = level;
    this.exp = exp;
    this.moves = moves;
    this.current_move = moves.get(0).getMoveName(); // very first attack of pokemon
    this.current_health = health;

  }

  public ArrayList<PokemonMoves> getMoves() {
    return moves;
  }

  public int getAttack() {
    return attack;
  }



  public void setAttack(int attack) {
    this.attack = attack;
  }



  public int getDefense() {
    return defense;
  }



  public void setDefense(int defense) {
    this.defense = defense;
  }



  public int getLevel() {
    return level;
  }



  public void setLevel(int level) {
    this.level = level;
  }



  public int getExp() {
    return exp;
  }



  public void setExp(int exp) {
    this.exp = exp;
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


  public int getHealth() {
    return health;
  }


  public void setHealth(int health) {
    this.health = health;
  }


  public String getCurrent_move() {
    return current_move;
  }


  public void setCurrentMove(String current_move) {
    this.current_move = current_move;
  }


  public String getMoveName(int index) {
    return moves.get(index).getMoveName();
  }

  public int getCurrent_health() {
    return current_health;
  }

  public void setCurrent_health(int current_health) {
    this.current_health = current_health;
  }

}
