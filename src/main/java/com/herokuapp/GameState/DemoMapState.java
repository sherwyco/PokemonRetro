package com.herokuapp.GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import com.herokuapp.Panels.GamePanel;
import com.herokuapp.TileMaps.Tilemap;
import com.herokuapp.TileMaps.TilemapUtility;
import com.herokuapp.player.Player;
import com.herokuapp.server.ClientThread;
import com.herokuapp.server.DummyPlayer;
import com.herokuapp.server.Network.UpdateCoords;
import com.herokuapp.server.Network.UpdateCoords.movement;

public class DemoMapState extends GameState {

  // screen size 1920x1080
  // Image image = Toolkit.getDefaultToolkit().getImage("g_rt_0.gif");
  // Animation anim = new Animation("g_dn", 5, 10);
  // spawn player halfway into tile + spawn location

  Player player;
  TilemapUtility tilemapUtility = new TilemapUtility();
  Tilemap tilemap;


  boolean up_pressed = false;
  boolean dn_pressed = false;
  boolean lt_pressed = false;
  boolean rt_pressed = false;
  boolean A_pressed = false;
  boolean D_pressed = false;
  boolean S_pressed = false;
  boolean W_pressed = false;
  boolean space_pressed = false;
  boolean enter_pressed = false;
  int spawnX;
  int spawnY;

  private Thread threadClient;
  // client thread for connecting to server
  private ClientThread clientThread;
  // player's name
  public String playerName = GamePanel.name;
  public int myClientId;
  private ArrayList<DummyPlayer> dummyPlayers = new ArrayList<DummyPlayer>();
  private Thread updateThread;
  // private UpdateCoords updateDummy = new UpdateCoords();

  public DemoMapState(GameStateManager gsm, int spawnX, int spawnY) {
    clientThread = new ClientThread();
    threadClient = new Thread(clientThread);
    threadClient.setName("Client");
    threadClient.setDaemon(true);
    threadClient.start();
    this.gsm = gsm;
    this.spawnX = spawnX;
    this.spawnY = spawnY;
    init();
  }


  public void init() {
    try {
      tilemap = tilemapUtility.loadMap("src/main/resources/tilemaps/tilemap1.txt");
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    player = new Player(spawnX, spawnY, this.gsm);
    player.setLevel(tilemap);
    myClientId = clientThread.myClientId;
    System.out.println("my Id: " + myClientId);
  }


  public void keyReleased(KeyEvent e) {

  }



  @Override
  public void update() {
    dummyPlayers = new ArrayList<DummyPlayer>(clientThread.playerList);
    player.update();
    if (W_pressed) {
      player.moveUp();
      clientThread.client
          .sendUDP(new UpdateCoords(player.getX(), player.getY(), myClientId, movement.Up));
    }
    if (A_pressed) {
      player.moveLeft();
      clientThread.client
          .sendUDP(new UpdateCoords(player.getX(), player.getY(), myClientId, movement.Left));
    }
    if (S_pressed) {
      player.moveDown();
      clientThread.client
          .sendUDP(new UpdateCoords(player.getX(), player.getY(), myClientId, movement.Down));
    }
    if (D_pressed) {
      player.moveRight();
      clientThread.client
          .sendUDP(new UpdateCoords(player.getX(), player.getY(), myClientId, movement.Right));
    }
    if (enter_pressed && player.hasEncounteredPokemon()) {
      System.out.println("go to battle state now");
    }
    if (dummyPlayers.size() > 1) {

      for (DummyPlayer p : dummyPlayers) {
        // if its not my character
        if (p.myClientId != clientThread.myClientId) {
          if (clientThread.coords != null) {
            // if new coords are not for me
            System.out.println("getting new coords for client: " + clientThread.coords.clientId);
            while ((p.getX() != clientThread.coords.x) || (p.getY() != clientThread.coords.y)) {
              switch (clientThread.coords.type) {
                case Left:
                  p.moveLeft();
                  break;
                case Right:
                  p.moveRight();
                  break;
                case Up:
                  p.moveUp();
                  break;
                case Down:
                  p.moveDown();
                  break;
                default:
                  break;
              }
              p.update();
            }
            clientThread.coords = null;
          }
          p.update();
        }
      }
    }

  }


  public class playerUpdater implements Runnable {
    @Override
    public void run() {

    }
  }

  @Override
  public void draw(Graphics2D g) {
    // clear screen
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

    tilemap.draw(g);
    tilemap.drawSpritesAbove(g, player.getY());
    player.draw(g);
    tilemap.drawSpritesBelow(g, player.getY());
    player.drawHUD(g);
    // render dummy players
    if (dummyPlayers.size() > 1) {
      for (DummyPlayer p : dummyPlayers) {
        // if its not my character
        if (p.myClientId != myClientId) {
          // render
          p.draw(g);
        }
      }
    }
  }



  @Override
  public void keyPressed(int k) {
    if (k == KeyEvent.VK_UP)
      up_pressed = true;
    if (k == KeyEvent.VK_DOWN)
      dn_pressed = true;
    if (k == KeyEvent.VK_LEFT)
      lt_pressed = true;
    if (k == KeyEvent.VK_RIGHT)
      rt_pressed = true;
    if (k == KeyEvent.VK_A)
      A_pressed = true;
    if (k == KeyEvent.VK_D)
      D_pressed = true;
    if (k == KeyEvent.VK_S)
      S_pressed = true;
    if (k == KeyEvent.VK_W)
      W_pressed = true;
    if (k == KeyEvent.VK_SPACE)
      space_pressed = true;
    if (k == KeyEvent.VK_ENTER)
      enter_pressed = true;
  }



  @Override
  public void keyReleased(int k) {
    if (k == KeyEvent.VK_UP)
      up_pressed = false;
    if (k == KeyEvent.VK_DOWN)
      dn_pressed = false;
    if (k == KeyEvent.VK_LEFT)
      lt_pressed = false;
    if (k == KeyEvent.VK_RIGHT)
      rt_pressed = false;
    if (k == KeyEvent.VK_A)
      A_pressed = false;
    if (k == KeyEvent.VK_D)
      D_pressed = false;
    if (k == KeyEvent.VK_S)
      S_pressed = false;
    if (k == KeyEvent.VK_W)
      W_pressed = false;
    if (k == KeyEvent.VK_SPACE)
      space_pressed = false;
    if (k == KeyEvent.VK_ENTER)
      enter_pressed = false;
  }
}
