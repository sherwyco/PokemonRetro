package com.herokuapp.player;

public class PokemonMoves {

  public String MoveName;
  public String MoveType;
  public int MoveAttack;
  public int MoveAccuracy;

  public PokemonMoves(String name, String type, int atk, int acc) {
    MoveName = name;
    MoveType = type;
    MoveAttack = atk;
    MoveAccuracy = acc / 100;
  }

  public String getMoveName() {
    return MoveName;
  }


  public String getMoveType() {
    return MoveType;
  }


  public int getMoveAttack() {
    return MoveAttack;
  }


  public int getMoveAccuracy() {
    return MoveAccuracy;
  }

}
