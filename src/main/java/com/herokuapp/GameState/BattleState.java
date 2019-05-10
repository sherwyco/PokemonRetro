package com.herokuapp.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import com.herokuapp.Panels.GamePanel;
import com.herokuapp.TileMaps.Background;
import com.herokuapp.player.Player;
import com.herokuapp.player.Pokemon;
import com.herokuapp.sprite.SpriteAnimated;

public class BattleState extends GameState {

  private Background bg;
  private SpriteAnimated lugia;

  private int currentChoice = 0, pokemon1, pokemon2;
  private String[] hostAttacks = {"WATER", "FIRE", "WIND"};
  private String[] enemyAttacks = {"ELECTRIC", "ICE", "GRASS"};
  private String[] Pokemons = {"Pikachu", "Dragonite", "Snorlax", "Charizard"};
  private int len = hostAttacks.length;
  private boolean hostTurnToAttack, enemyTurnToAttack, criticalAttack, weackAttack;
  private Color titleColor;
  private Font titleFont;
  private Font font;
  private Player host, opponent;
  private Random rand = new Random();
  private Pokemon pok1, pok2;
  private int hostHealth, enemyHealth;
  // For Battle
  private static final int DEFAULT_HEALTH = 20;

  public static final int DEFAULT_DAMAGE = 4;

  public static int damage;

  /**
   * 
   * @param gsm GameStateManager class
   */
  public BattleState(GameStateManager gsm) {

    this.gsm = gsm;

    hostTurnToAttack = true;
    criticalAttack = false;
    weackAttack = false;
    BattleState.damage = 0;
    this.enemyHealth = DEFAULT_HEALTH;
    System.out.println(enemyHealth);
    this.hostHealth = DEFAULT_HEALTH;
    // opponent.setTurnToAttack(false);

    this.pokemon1 = rand.nextInt(3);
    this.pokemon2 = rand.nextInt(3);

    addBackground();
    pok1 = new Pokemon(Pokemons[pokemon1], hostAttacks, hostAttacks[0], DEFAULT_HEALTH);
    pok2 = new Pokemon(Pokemons[pokemon2], enemyAttacks, enemyAttacks[0], DEFAULT_HEALTH);


    // pok1.setHealth(DEFAULT_HEALTH);
    // pok2.setHealth(DEFAULT_HEALTH);

  }



  public void addBackground() {

    try {
      bg = new Background("/Backgrounds/menu_background.jpg", 1);
      bg.setVector(-.5, 0);
      lugia = new SpriteAnimated(1000, 200, 1, 10, 10,
          "src/main/resources/sprites/LugiaSpritesheet.png");
      titleColor = new Color(128, 0, 0);
      titleFont = new Font("Arial", Font.PLAIN, 50);
      font = new Font("Arial", Font.PLAIN, 30);
    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  public void init() {

    System.out.println(enemyHealth);

  }


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

  public void drawAttacks(Graphics g, String text, int x, int y) {
    Graphics2D g2d = (Graphics2D) g;
    FontMetrics fm = g2d.getFontMetrics();
    Rectangle2D r = fm.getStringBounds(text, g2d);
    g.drawString(text, x, y);
  }

  public void draw(Graphics2D g) {
    // draw background
    bg.draw(g);
    lugia.draw(g);
    // draw title
    g.setColor(titleColor);
    g.setFont(titleFont);
    drawCenteredString(g, "BATTLE", 100);

    // draw menu options
    g.setFont(font);
    for (int i = 0; i < hostAttacks.length; i++) {
      // temp for host
      Rectangle host = new Rectangle();
      host.height = 100;
      host.width = 100;
      host.x = 40;
      host.y = GamePanel.HEIGHT - 500;
      g.setColor(Color.blue);
      g.drawRect(host.x, GamePanel.HEIGHT - 500, 100, 100);
      g.setColor(Color.BLUE);
      g.fill(host);
      g.setColor(Color.GREEN);
      g.drawString(Pokemons[pokemon1], host.x, host.y - 20);


      // temp for opponent
      Rectangle opponent = new Rectangle();
      opponent.height = 100;
      opponent.width = 100;
      opponent.x = GamePanel.WIDTH - 200;
      opponent.y = GamePanel.HEIGHT - 500;
      g.setColor(Color.blue);
      g.setColor(Color.RED);
      g.fill(opponent);
      g.setColor(Color.GREEN);
      g.drawString(Pokemons[pokemon2], opponent.x - 200, opponent.y - 20);

      g.drawRect(GamePanel.WIDTH - 200, GamePanel.HEIGHT - 500, 100, 100);
      g.setColor(Color.black);
      if (hostTurnToAttack) {
        pok1.setCurrent_move(hostAttacks[currentChoice]);
        if (i == currentChoice) {
          g.setColor(Color.RED);
        } else {
          g.setColor(Color.BLACK);
        }
        drawAttacks(g, hostAttacks[i], 100 + i * 200, GamePanel.HEIGHT - 300);
      }
      // Opponents turn
      else if (!hostTurnToAttack) {
        pok2.setCurrent_move(hostAttacks[currentChoice]);
        if (i == currentChoice) {
          g.setColor(Color.RED);
        } else {
          g.setColor(Color.BLACK);
        }
        drawAttacks(g, enemyAttacks[i], GamePanel.WIDTH - 700 + i * 200, GamePanel.HEIGHT - 300);
      }
    }

  }



  private void select() {
    System.out.println(enemyHealth);
    if (currentChoice == 0) {
      // start

      switchAttacker(0);

    }
    if (currentChoice == 1) {

      switchAttacker(1);
    }
    if (currentChoice == 2) {

      switchAttacker(2);
    }

  }

  private void switchAttacker(int attack) {
    // System.out.println(enemyHealth);
    if (hostTurnToAttack) {
      System.out.println("Before Attack: Pokemon 2, Current Health = " + enemyHealth);

      enemyHealth -= attackDamage(pok1.getCurrent_move(), pok2.getType());
      System.out.println(
          " Pokemon 1 Attack " + hostAttacks[currentChoice] + " Pokemon 2 health  " + enemyHealth);
      hostTurnToAttack = false;
      enemyTurnToAttack = true;
    } else if (enemyTurnToAttack) {
      System.out.print("Before Attack: Pokemon 1, Current Health = " + hostHealth);
      hostHealth -= attackDamage(pok2.getCurrent_move(), pok1.getType());
      System.out.println(
          " Pokemon 2 Attack " + enemyAttacks[currentChoice] + " Pokemon 1 Health " + hostHealth);
      // System.out.println(enemyAttacks[attack]);
      hostTurnToAttack = true;
      enemyTurnToAttack = false;
    }

  }

  public void keyPressed(int k) {
    if (k == KeyEvent.VK_ENTER) {
      select();
    }

    if ((k == KeyEvent.VK_UP) || (k == KeyEvent.VK_LEFT)) {
      currentChoice--;
      if (currentChoice < 0) {
        currentChoice = len - 1;
      }
    }

    if ((k == KeyEvent.VK_DOWN) || (k == KeyEvent.VK_RIGHT)) {
      currentChoice++;
      currentChoice %= len;

    }
  }

  public void keyReleased(int k) {

  }

  public boolean isTurnToAttack() {
    return hostTurnToAttack;
  }

  public void setTurnToAttack(boolean turnToAttack) {
    this.hostTurnToAttack = turnToAttack;
  }

  public int attackDamage(String attacker, String defender) {
    // switch statement with int data type
    damage = 0;
    attacker.toUpperCase();
    switch (attacker) {
      case "WATER":
        if (defender == "FIRE") {
          damage = DEFAULT_DAMAGE * 2;
        } else if (defender == "WATER") {
          damage = DEFAULT_DAMAGE / 2;
        } else if (defender == "ELECTRIC") {
          damage = DEFAULT_DAMAGE / 2;
        }
        break;

      case "ELECTRIC":
        if (defender == "WATER") {
          damage = DEFAULT_DAMAGE * 2;
        } else if (defender == "GROUND") {
          damage = 0;
        } else if (defender == "ELECTRIC") {
          damage = DEFAULT_DAMAGE / 2;
        }
        break;
      case "FIRE":
        if (defender == "WATER") {
          damage = DEFAULT_DAMAGE / 2;
        } else if (defender == "GROUND") {
          return 0;
        } else if (defender == "ELECTRIC") {
          damage = DEFAULT_DAMAGE / 2;
        }
        break;
      default:
        damage = DEFAULT_DAMAGE;
        break;
    }

    System.out.println(enemyHealth + ", " + hostHealth);
    return damage;
  }

}


