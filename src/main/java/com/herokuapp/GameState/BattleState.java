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
import com.herokuapp.utils.ClickListener;
import com.herokuapp.utils.ImageManager;
import com.herokuapp.utils.UIImageButton;
import com.herokuapp.utils.UIManager;

public class BattleState extends GameState {

  private Background bg;
  private SpriteAnimated lugia;

  // string[] pokemonPlayers = database.getPlayerPokemon();
  // String currpokemon = pokemonPlayers[0];
  // String[] currPokemonAttack = database.getAttacks(pokemonPlayer[0]);
  //
  // for(int i = 0; i<currPokemonAttack.length; i++) {
  //
  // }

  private Database databse;
  private ArrayList<Pokemon> PokemonArr, PokemonArr2;
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
  private ImageManager imageUtil;
  private Pokemon userPokemons[];
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

    // opponent.setTurnToAttack(false);

    this.pokemon1 = rand.nextInt(3);
    this.pokemon2 = rand.nextInt(3);
    // System.out.println(Player.sendPokemons());

    addBackground();
    users = databse.getUsers();
    PokemonArr = databse.getPlayerPokemon(1);
    PokemonArr2 = databse.getPlayerPokemon(2);
    int index = rand.nextInt(PokemonArr.size() - 1);
    pok1 = PokemonArr.get(index);
    index = rand.nextInt(PokemonArr2.size() - 1);
    pok2 = PokemonArr2.get(index);
    System.out.println(users);
    System.out.println("User 1 Pokemons");
    DisplayPlayerPokemons(PokemonArr);
    System.out.println("User 2 Pokemons");
    DisplayPlayerPokemons(PokemonArr2);
    this.enemyHealth = pok2.getHealth();

    this.hostHealth = pok1.getHealth();

    System.out.println("User 1 Curr Pokemon");
    System.out.println(pok1.getName());
    System.out.println("User 2  curr Pokemon");
    System.out.println(pok2.getName());

    buttons = new UIManager(handler);
    buttons.addObject(new UIImageButton((GlobalVariables.screenWidth / 2) - 120,
        GlobalVariables.screenHeight - 570, 200, 100, "Button1", new ClickListener() {
          @Override
          public void onClick() {
            System.exit(0);
          }
        }));

    ImageManager imageUtil = new ImageManager();

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
      pokHealth = pokemons.get(index).getHealth();
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

    buttons.tick();

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

  public void drawAttackTurn(Graphics g) {
    g.setColor(Color.YELLOW);
    g.drawString(pok1.getName(), 50, GamePanel.HEIGHT - 600);
    g.drawImage(imageManager.getPokemonImage("charmander"), 100, GamePanel.HEIGHT - 650, 300, 300,
        null);
    drawHealthBar(g, (int) pok1.getHealth(), 100, GamePanel.HEIGHT - 1000);

    g.drawImage(imageManager.getPokemonImage("jolteon"), GamePanel.WIDTH - 500, 150, 300, 300,
        null);
    drawHealthBar(g, (int) pok2.getHealth(), GamePanel.WIDTH - 500, GamePanel.HEIGHT - 1000);

    g.drawString(Pokemons[pokemon2], GamePanel.WIDTH - 400, GamePanel.HEIGHT - 900);

    for (int i = 0; i < hostAttacks.length; i++) {
      if (hostTurnToAttack) {
        g.setColor(Color.BLACK);

        if (i == currentChoice) {
          g.setColor(Color.RED);
        } else {
          g.setColor(Color.BLUE);
        }

        drawAttacks(g, hostAttacks[i], 100 + i * 200, GamePanel.HEIGHT - 300);

      }
      // Opponents turn
      else if (!hostTurnToAttack) {
        if (i == currentChoice) {
          g.setColor(Color.RED);
        } else {
          g.setColor(Color.BLACK);
        }
        drawAttacks(g, enemyAttacks[i], 1100 + i * 300, GamePanel.HEIGHT - 350);

      }

    }

  }

  private void switchAttacker(int attack) {
    // System.out.println(enemyHealth);
    if (hostTurnToAttack) {
      System.out.println("Before Attack: Pokemon 2, Current Health = " + pok2.getHealth());
      int health = pok2.getHealth() - attackDamage(pok1.getCurrent_move(), pok2.getType());
      pok2.setHealth(health);
      System.out.println(" Pokemon 1 Attack " + hostAttacks[currentChoice] + " Pokemon 2 health  "
          + pok2.getHealth());
      hostTurnToAttack = false;
      enemyTurnToAttack = true;
    } else if (enemyTurnToAttack) {
      System.out.print("Before Attack: Pokemon 1, Current Health = " + hostHealth);
      pok1.setHealth(pok1.getHealth() - attackDamage(pok2.getCurrent_move(), pok1.getType()));
      System.out.println(" Pokemon 2 Attack " + enemyAttacks[currentChoice] + " Pokemon 1 Health "
          + pok1.getHealth());
      // System.out.println(enemyAttacks[attack]);
      hostTurnToAttack = true;
      enemyTurnToAttack = false;
    }
    currentChoice = 0;

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
    attacker.toUpperCase();
    switch (attacker) {
      case "WATER":
        if (defender == "FIRE") {
          base_damage = DEFAULT_DAMAGE * 2;
        } else if (defender == "WATER") {
          base_damage = DEFAULT_DAMAGE / 2;
        } else if (defender == "ELECTRIC") {
          base_damage = DEFAULT_DAMAGE / 2;
        }
        break;

      case "ELECTRIC":
        if (defender == "WATER") {
          base_damage = DEFAULT_DAMAGE * 2;
        } else if (defender == "GROUND") {
          base_damage = 0;
        } else if (defender == "ELECTRIC") {
          base_damage = DEFAULT_DAMAGE / 2;
        }
        break;
      case "FIRE":
        if (defender == "WATER") {
          base_damage = DEFAULT_DAMAGE / 2;
        } else if (defender == "GROUND") {
          return 0;
        } else if (defender == "ELECTRIC") {
          base_damage = DEFAULT_DAMAGE / 2;
        }
        break;
      default:
        base_damage = DEFAULT_DAMAGE;
        break;
    }

    System.out.println(enemyHealth + ", " + hostHealth);
    return base_damage;
  }

  public void drawHealthBar(Graphics g, int health, int x, int y) {

    g.setColor(Color.GRAY.darker());
    g.fillRect(x, y, enemyHealth * 10, 40);
    g.setColor(Color.GREEN.brighter());
    g.fillRect(x, y, health * 10, 40);

  }

  public void gameOverScreen() {
    if (pok1.getHealth() <= 0 || pok2.getHealth() <= 0)
      gsm.setState(GameStateManager.DemoMapState);

  }



}


