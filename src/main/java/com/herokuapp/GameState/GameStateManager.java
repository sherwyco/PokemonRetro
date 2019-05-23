package com.herokuapp.GameState;

import java.awt.Graphics2D;
import java.util.ArrayList;


public class GameStateManager {

  private ArrayList<GameState> gameStates;
  private int currentState;


  public static final int MENUSTATE = 0;
  // public static final int LEVEL1STATE = 1;
  public static final int DemoMapState = 1;
  // temp
  public static final int BattleState = 2;
  public static final int MapState1 = 3;

  // constructor
  public GameStateManager() {
    gameStates = new ArrayList<GameState>();
    currentState = MENUSTATE;
    gameStates.add(new MenuState(this));
    gameStates.add(new DemoMapState(this, 50, 30));
    gameStates.add(new BattleState(this));
    gameStates.add(new MapState_CaveLevel(this, 15, 12));

  }

  public void switchToBattleState(int encounteredPokemonId) {
    // cast to battle state to use method battlestate method, since they get treated as gamestate
    // instances while in our gamestate arraylist
    ((BattleState) gameStates.get(BattleState)).startBattleState(encounteredPokemonId);
    setState(BattleState);
  }

  public void setState(int state) {
    currentState = state;
    gameStates.get(currentState).init();
  }

  public void update() {
    gameStates.get(currentState).update();
  }

  public void draw(Graphics2D g) {
    gameStates.get(currentState).draw(g);

  }

  public void keyPressed(int k) {
    gameStates.get(currentState).keyPressed(k);

  }

  public void keyReleased(int k) {
    gameStates.get(currentState).keyReleased(k);
  }
}
