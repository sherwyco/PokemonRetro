package com.herokuapp.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import com.herokuapp.TileMaps.Background;


/**
 * 
 * @author sherwinwyco
 *
 */
public class MenuState extends GameState {

  private Background bg;

  private int currentChoice = 0;
  private String[] options = {"Start", "Help", "Quit"};
  private int len = options.length;
  private Color titleColor;
  private Font titleFont;
  private Font font;

  /**
   * 
   * @param gsm GameStateManager class
   */
  public MenuState(GameStateManager gsm) {
    this.gsm = gsm;

    try {
      bg = new Background("/Backgrounds/menu_background.jpg", 100);
      bg.setVector(-0.1, 0);

      titleColor = new Color(128, 0, 0);
      titleFont = new Font("Arial", Font.PLAIN, 100);

      font = new Font("Arial", Font.PLAIN, 25);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void init() {}

  public void update() {
    bg.update();
  }

  public void draw(Graphics2D g) {
    // draw background
    bg.draw(g);


    // draw title
    g.setColor(titleColor);
    g.setFont(titleFont);
    g.drawString("Pokemon Retro", 145, 50);

    // draw menu options
    g.setFont(font);
    for (int i = 0; i < options.length; i++) {
      if (i == currentChoice) {
        g.setColor(Color.GREEN);
      } else {
        g.setColor(Color.RED);
      }

      g.drawString(options[i], 145, 145 + i * 20);
    }
  }

  private void select() {
    if (currentChoice == 0) {
      // start
      System.out.println("start the game");
    }
    if (currentChoice == 1) {
      // help
    }
    if (currentChoice == 2) {
      // quit
      System.exit(0);
    }
  }

  public void keyPressed(int k) {
    if (k == KeyEvent.VK_ENTER) {
      select();
    }

    if (k == KeyEvent.VK_UP) {
      currentChoice--;
      if (currentChoice < 0) {
        currentChoice = len - 1;
      }
    }

    if (k == KeyEvent.VK_DOWN) {
      currentChoice++;
      currentChoice %= len;

    }
  }

  public void keyReleased(int k) {

  }

}
