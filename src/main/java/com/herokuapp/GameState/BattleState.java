package com.herokuapp.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import com.herokuapp.Panels.GamePanel;
import com.herokuapp.TileMaps.Background;
import com.herokuapp.database.Database;
import com.herokuapp.misc.GlobalVariables;
import com.herokuapp.misc.Handler;
import com.herokuapp.player.Player;
import com.herokuapp.player.Pokemon;
import com.herokuapp.player.PokemonMoves;
import com.herokuapp.sprite.SpriteAnimated;
import com.herokuapp.utils.ImageManager;
import com.herokuapp.utils.UIManager;

public class BattleState extends GameState {

  private Background bg;
  private SpriteAnimated lugia;
  private Database databse;
  private ArrayList<Pokemon> user1Poks, user2Poks;
  String users;


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
  BufferedImage hostpokemon;
  private UIManager buttons;
  private Handler handler;
  private ImageManager imageManager;

  // For Battle
  private static final int DEFAULT_HEALTH = 20;

  public static final int DEFAULT_DAMAGE = 4;

  public static int base_damage;

  /**
   * 
   * @param gsm GameStateManager class
   */
  public BattleState(GameStateManager gsm) {

    this.gsm = gsm;
    databse = new Database();
    this.imageManager = new ImageManager();

    hostTurnToAttack = true;
    criticalAttack = false;
    weackAttack = false;
    BattleState.base_damage = 0;

    addBackground();
    users = databse.getUsers();
    pupulatePokemons();

    System.out.println("Pokemon 1 Damage = " + pok1.getAttack() + " health = "
        + pok1.getCurrent_health() + " defense = " + pok1.getDefense());
    System.out.println("Pokemon 2 Damage = " + pok2.getAttack() + " health = "
        + pok2.getCurrent_health() + " defense = " + pok2.getDefense());



  }

  private void pupulatePokemons() {
    user1Poks = databse.getPlayerPokemon(1);
    user2Poks = databse.getPlayerPokemon(2);
    int index = rand.nextInt(user1Poks.size() - 1);
    pok1 = user1Poks.get(index);
    index = rand.nextInt(user2Poks.size() - 1);
    pok2 = user2Poks.get(index);
    System.out.println(users);
    System.out.println("User 1 Pokemons");
    DisplayPlayerPokemons(user1Poks);
    System.out.println("User 2 Pokemons");
    DisplayPlayerPokemons(user2Poks);
    this.enemyHealth = pok2.getCurrent_health();

    this.hostHealth = pok1.getCurrent_health();

    System.out.println("User 1 Curr Pokemon");
    System.out.println(pok1.getName());
    System.out.println("User 2  curr Pokemon");
    System.out.println(pok2.getName());
  }

  public void DisplayPlayerPokemons(ArrayList<Pokemon> pks) {

    for (int i = 0; i < pks.size(); i++) {

      System.out.println(pks.get(i).getName());

    }
  }



  public Pokemon[] generatePokemons(ArrayList<Pokemon> pokemons) {
    Pokemon[] arr = new Pokemon[pokemons.size()];
    ArrayList<PokemonMoves> moves = new ArrayList<PokemonMoves>();
    String pokId;
    String pokName;
    int defense, level, xp;
    String pokType;
    int pokAttack;
    String pokCurrMove;
    int pokHealth = 0;
    pokemons.size();
    for (int index = 0; index < pokemons.size(); index++) {
      System.out.println(pokemons.get(index));
      // pokId = pokemons.get(index).getCurrent_move();
      pokName = pokemons.get(index).getName();
      pokType = pokemons.get(index).getType();
      pokAttack = pokemons.get(index).getAttack();
      pokCurrMove = pokemons.get(index).getCurrent_move();
      pokHealth = pokemons.get(index).getCurrent_health();
      level = pokemons.get(index).getLevel();
      xp = pokemons.get(index).getExp();
      defense = pokemons.get(index).getDefense();
      System.out.println(pokName);
      System.out.println(pokType);
      System.out.println(pokCurrMove);
      System.out.println(pokHealth);
      System.out.println(pokAttack);
      System.out.println(pokemons.get(index).getDefense());
      System.out.println(pokemons.get(index).getMoveName(index));

      arr[index] = new Pokemon(pokName, pokType, pokAttack, defense, pokHealth, level, xp, moves);

    }
    return arr;

  }

  public void addBackground() {

    try {
      bg = new Background("/Backgrounds/menu_background.jpg", 1);
      bg.setVector(-.5, 0);
      lugia = new SpriteAnimated(1000, 200, 1, 10, 10, 7,
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
    gameOverScreen();
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
    // FontMetrics fm = g2d.getFontMetrics();
    // Rectangle2D r = fm.getStringBounds(text, g2d);
    g.drawString(text, x, y);
  }



  public void draw(Graphics2D g) {
    // draw background
    g.clearRect(0, 0, GlobalVariables.screenWidth, GlobalVariables.screenHeight);
    bg.draw(g);
    lugia.drawScreenSpace(g);
    // draw title
    g.setColor(titleColor);
    g.setFont(titleFont);
    drawCenteredString(g, "BATTLE", 100);
    drawAttackTurn(g);
  }


  private void select() {

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

  public void drawAttackTurn(Graphics g) {
    // drawing pokemon one with name and image
    g.setColor(Color.YELLOW);
    g.drawString(pok1.getName(), 50, GamePanel.HEIGHT - 600);
    g.drawImage(imageManager.getPokemonImage(pok1.getName()), 100, GamePanel.HEIGHT - 650, 300, 300,
        null);
    drawHealthBar(g, pok1, 100, GamePanel.HEIGHT - 1000, hostHealth);

    // drawing pokemon 2 with name
    g.drawImage(imageManager.getPokemonImage(pok2.getName()), GamePanel.WIDTH - 500, 150, 300, 300,
        null);
    drawHealthBar(g, pok2, GamePanel.WIDTH - 650, GamePanel.HEIGHT - 1000, enemyHealth);

    g.drawString(pok2.getName(), GamePanel.WIDTH - 400, GamePanel.HEIGHT - 900);

    for (int i = 0; i < 3; i++) {
      if (hostTurnToAttack) {
        g.setColor(Color.BLACK);

        if (i == currentChoice) {
          g.setColor(Color.RED);
        } else {
          g.setColor(Color.BLUE);
        }

        drawAttacks(g, pok1.getMoveName(i), 100 + i * 200, GamePanel.HEIGHT - 300);

      }
      // Opponents turn
      else if (!hostTurnToAttack) {
        if (i == currentChoice) {
          g.setColor(Color.RED);
        } else {
          g.setColor(Color.BLACK);
        }
        drawAttacks(g, pok2.getMoveName(i), 1000 + i * 300, GamePanel.HEIGHT - 350);
      }
    }
  }

  private void switchAttacker(int attack) {
    // System.out.println(enemyHealth);
    if (hostTurnToAttack) {
      System.out.println("Before Attack: Pokemon 2, Current Health = " + pok2.getCurrent_health());
      pok2.setCurrent_health(pok2.getCurrent_health() - attackDamage(pok1, pok2));
      System.out.println(" Pokemon 1 Attack " + pok1.getCurrent_move() + " Pokemon 2 health  "
          + pok2.getCurrent_health());
      hostTurnToAttack = false;
      enemyTurnToAttack = true;
    } else if (enemyTurnToAttack) {
      System.out.print("Before Attack: Pokemon 1, Current Health = " + hostHealth);
      pok1.setCurrent_health(pok1.getCurrent_health() - attackDamage(pok2, pok1));
      System.out.println(" Pokemon 2 Attack " + pok2.getCurrent_move() + " Pokemon 1 Health "
          + pok1.getCurrent_health());
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

  public int attackDamage(Pokemon Pattacker, Pokemon Pdefender) {
    // switch statement with int data type
    base_damage = 0;
    String attacker = Pattacker.getType().toUpperCase();

    String defender = Pdefender.getType().toUpperCase();

    attacker.toUpperCase();
    switch (attacker) {
      case "WATER":
        if (defender == "FIRE") {
          base_damage = ((Pattacker.getAttack() / 2) - Pdefender.getDefense()) * 2;
        } else if (defender == "WATER") {
          base_damage = ((Pattacker.getAttack() / 2) - Pdefender.getDefense()) / 2;
        } else if (defender == "ELECTRIC") {
          base_damage = ((Pattacker.getAttack() / 2) - Pdefender.getDefense()) / 2;
        }
        break;

      case "ELECTRIC":
        if (defender == "WATER") {
          base_damage = ((Pattacker.getAttack() / 2) - Pdefender.getDefense()) * 2;
        } else if (defender == "GROUND") {
          base_damage = ((Pattacker.getAttack() / 2) - Pdefender.getDefense()) / 4;
        } else if (defender == "ELECTRIC") {
          base_damage = ((Pattacker.getAttack() / 2) - Pdefender.getDefense()) / 4;
        }
        break;
      case "FIRE":
        if (defender == "WATER") {
          base_damage = ((Pattacker.getAttack() / 2) - Pdefender.getDefense()) / 2;
        } else if (defender == "GROUND") {

          base_damage = (Pattacker.getAttack() / 2 - Pdefender.getDefense()) / 4;
        } else if (defender == "ELECTRIC") {
          base_damage = ((Pattacker.getAttack() / 2) - Pdefender.getDefense()) / 2;
        }
        break;
      default:

        base_damage = Pattacker.getAttack() / 2;
        break;
    }

    if (base_damage <= 0)
      return 15;
    return base_damage;
  }

  public void drawHealthBar(Graphics g, Pokemon pok, int x, int y, int maxHealth) {

    int base_h = pok.getHealth();
    g.setColor(Color.GRAY.darker());
    g.fillRect(x, y, 300, 40);
    g.setColor(Color.RED.brighter());
    g.fillRect(x, y, (int) (((double) pok.getCurrent_health() / (double) base_h) * 300), 40);

  }

  public void gameOverScreen() {
    if (pok1.getCurrent_health() <= 0 || pok2.getCurrent_health() <= 0)
      gsm.setState(GameStateManager.DemoMapState);

    // if we win we can update pokemons xp-->level
    // also we can add the loser pokemon to our players pokemons

  }

}


