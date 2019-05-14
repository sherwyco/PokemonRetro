package com.herokuapp.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import com.herokuapp.Panels.GamePanel;
import com.herokuapp.TileMaps.Background;
import com.herokuapp.sprite.SpriteAnimated;


public class MenuState extends GameState {

  private Background bg;
  private SpriteAnimated lugia;

  private int currentChoice = 0;
  private String[] options = {"Start", "Help", "Quit", "Battle"};
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
      bg = new Background("/Backgrounds/menu_background.jpg", 1);
      bg.setVector(-.5, 0);

      lugia = new SpriteAnimated(1000, 200, 1, 10, 10,
          "src/main/resources/sprites/LugiaSpritesheet.png");


      titleColor = new Color(128, 0, 0);
      titleFont = new Font("Arial", Font.PLAIN, 100);

      font = new Font("Arial", Font.PLAIN, 50);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void init() {}

  public void update() {
    bg.update();
    lugia.update();
    lugia.moveLeft(3);
    if (lugia.getX() < -300) {
      lugia.setX(2300);
    }
  }

  public void drawCenteredString(Graphics g, String text, int y) {
    Graphics2D g2d = (Graphics2D) g;
    FontMetrics fm = g2d.getFontMetrics();
    Rectangle2D r = fm.getStringBounds(text, g2d);
    int x = (GamePanel.WIDTH - (int) r.getWidth()) / 2;
    g.drawString(text, x, y);
  }

  public void draw(Graphics2D g) {
    // draw background
    bg.draw(g);
    lugia.draw(g);
    // draw title
    g.setColor(titleColor);
    g.setFont(titleFont);
    drawCenteredString(g, "Pokemon Retro", 200);

    // draw menu options
    g.setFont(font);

    for (int i = 0; i < options.length; i++) {
      if (i == currentChoice) {
        g.setColor(Color.GREEN);
      } else {
        g.setColor(Color.RED);
      }

      // g.drawString(options[i], 145, 145 + i * 20);
      drawCenteredString(g, options[i], 600 + i * 50);
    }
  }



  private void select() {
    if (currentChoice == 0) {
      // start
      System.out.println("start the game");
      gsm.setState(GameStateManager.DemoMapState);
    }
    if (currentChoice == 1) {
      // help
    }
    if (currentChoice == 2) {
      // quit
      System.exit(0);
    }
    if (currentChoice == 3) {
      gsm.setState(GameStateManager.BattleState);


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
