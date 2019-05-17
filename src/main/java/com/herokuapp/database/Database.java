package com.herokuapp.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.herokuapp.player.Pokemon;
import com.herokuapp.player.PokemonMoves;

public class Database {

  // get current directory
  private static String currentDir = System.getProperty("user.dir") + "/";

  // db path
  private static String url = "jdbc:sqlite:" + currentDir + "pokemon_db";

  // create table name Players
  @SuppressWarnings("unused")
  private final static String PlayerTable = "CREATE TABLE IF NOT EXISTS Players (\n"
      + " PlayerId integer PRIMARY KEY,\n" + " Username text NOT NULL UNIQUE,\n"
      + " Password text NOT NULL, \n" + " Nickname text NOT NULL UNIQUE)";

  // create player info
  @SuppressWarnings("unused")
  private final static String PlayerData = "CREATE TABLE IF NOT EXISTS PlayerData (\n"
      + "PlayerId integer, \n" + "PokeBalls integer, \n" + "GreatBalls integer, \n"
      + "UltraBalls integer, \n" + "MasterBalls integer, \n" + "Pokemons integer , \n" // amount of
                                                                                       // pokemons
      + "PRIMARY KEY (PlayerId),\n" + "FOREIGN KEY (PlayerId) REFERENCES Players (PlayerId) \n"
      + "ON DELETE CASCADE ON UPDATE NO ACTION)";


  // create table name Pokedex
  @SuppressWarnings("unused")
  private final static String PokedexTable = "CREATE TABLE IF NOT EXISTS Pokedex (\n"
      + "PokemonId integer PRIMARY KEY, \n" + "Name text NOT NULL UNIQUE, \n" // pokemon name
      + "Type text NOT NULL, \n" + "Attack integer NOT NULL, \n" + "Defense integer NOT NULL, \n"
      + "BaseHP integer NOT NULL)";

  // create table name PokemonMoves
  @SuppressWarnings("unused")
  private final static String MovesTable =
      "CREATE TABLE IF NOT EXISTS AttackMoves (" + "MoveId integer PRIMARY KEY , \n"
          + "Name text NOT NULL UNIQUE, \n" + "Type text NOT NULL, \n" // move type
          + "Damage integer NOT NULL, \n" + "Accuracy integer NOT NULL)"; // 1-100 value only. when

  // create table PlayerPokemon which holds all the players pokemons
  @SuppressWarnings("unused")
  private final static String PlayerPokemonTable =
      "CREATE TABLE IF NOT EXISTS PlayerPokemon (" + "CatchId integer PRIMARY KEY, \n"
          + "PlayerId integer, \n" + "PokemonId integer, \n" + "Level integer NOT NULL, \n"
          + "EXP integer NOT NULL, \n" + "FOREIGN KEY (PlayerId) REFERENCES Players (PlayerId) \n"
          + "ON DELETE CASCADE ON UPDATE NO ACTION,\n"
          + "FOREIGN KEY (PokemonId) REFERENCES Pokedex (PokemonId) \n"
          + "ON DELETE CASCADE ON UPDATE NO ACTION)";

  @SuppressWarnings("unused")
  private final static String PokemonMovesTable = "CREATE TABLE IF NOT EXISTS PokemonMoves ("
      + "PokemonId integer, \n" + "MoveId integer, \n" + "PRIMARY KEY (PokemonId, MoveId),\n"
      + "FOREIGN KEY (PokemonId) REFERENCES Pokedex (PokemonId) \n"
      + "ON DELETE CASCADE ON UPDATE NO ACTION,\n"
      + "FOREIGN KEY (MoveId) REFERENCES AttackMoves (MoveId) \n"
      + "ON DELETE CASCADE ON UPDATE NO ACTION)";



  public static void main(String[] args) {
    // createNewTable(PlayerTable);
    // createNewTable(PlayerData);
    // createNewTable(PokedexTable); // all pokemons will be stored here
    // createNewTable(MovesTable); // all moves will be stored here
    // createNewTable(PokemonMovesTable); // pokemons with their move will be stored here
    // createNewTable(PlayerPokemonTable); // player's pokemon will be stored here
    // // name, type, atk, def, hp
    // insertPokemon("Bulbasaur", "grass", 49, 49, 45);
    // insertPokemon("Ivysaur", "grass", 62, 63, 60);
    // insertPokemon("Venusaur", "grass", 82, 83, 80);
    // insertPokemon("Charmander", "fire", 52, 43, 39);
    // insertPokemon("Charmeleon", "fire", 64, 58, 58);
    // insertPokemon("Charizard", "fire", 84, 78, 78);
    // insertPokemon("Squirtle", "water", 48, 65, 44);
    // insertPokemon("Wartortle", "water", 63, 80, 59);
    // insertPokemon("Blastoise", "water", 83, 100, 79);
    // insertPokemon("Caterpie", "bug", 30, 35, 45);
    // insertPokemon("Metapod", "bug", 20, 55, 50);
    // insertPokemon("Butterfree", "bug", 45, 50, 60);
    // insertPokemon("Weedle", "bug", 35, 30, 40);
    // insertPokemon("Kakuna", "bug", 25, 50, 45);
    // insertPokemon("Beedrill", "bug", 90, 40, 65);
    // insertPokemon("Pidgey", "flying", 45, 40, 40);
    // insertPokemon("Pidgeotto", "flying", 60, 55, 63);
    // insertPokemon("Pidgeot", "flying", 80, 75, 83);
    // insertPokemon("Rattata", "normal", 56, 35, 30);
    // insertPokemon("Raticate", "normal", 81, 60, 50);
    // insertPokemon("Spearow", "flying", 60, 30, 40);
    // insertPokemon("Fearow", "flying", 90, 65, 65);
    // insertPokemon("Ekans", "poison", 60, 44, 35);
    // insertPokemon("Arbok", "poison", 85, 69, 60);
    // insertPokemon("Pikachu", "electric", 55, 40, 35);
    // insertPokemon("Raichu", "electric", 90, 55, 60);
    // insertPokemon("Sandshrew", "ground", 75, 85, 50);
    // insertPokemon("SandSlash", "ground", 100, 110, 75);
    // insertMove("Growl", "normal", 0, 100);
    // insertMove("Tackle", "normal", 40, 100);
    // insertMove("Vine Whip", "grass", 45, 100);
    // insertMove("Leech Seed", "grass", 0, 90);
    // insertMove("Power Whip", "grass", 120, 85);
    // insertMove("Scratch", "normal", 40, 100);
    // insertMove("Smokescreen", "normal", 0, 100);
    // insertMove("Ember", "fire", 40, 100);
    // insertMove("Heat Wave", "fire", 95, 90);
    // insertMove("Tail Whip", "normal", 0, 100);
    // insertMove("Bubble", "water", 40, 100);
    // insertMove("Water Gun", "water", 40, 100);
    // insertMove("Aqua Jet", "water", 40, 100);
    // insertMove("Hydro Pump", "water", 110, 80);
    // insertMove("String shot", "bug", 0, 95);
    // insertMove("Harden", "normal", 0, 100);
    // insertMove("Gust", "flying", 50, 100);
    // insertMove("Sleep Powder", "grass", 0, 100);
    // insertMove("Psybeam", "psychic", 65, 100);
    // insertMove("Poison Sting", "poison", 15, 100);
    // insertMove("Peck", "flying", 35, 100);
    // insertMove("Twineedle", "grass", 25, 100);
    // insertMove("Rage", "normal", 15, 85);
    // insertMove("Poison Jab", "poison", 80, 100);
    // insertMove("Quick Attack", "normal", 40, 100);
    // insertMove("Sky Attack", "flying", 140, 90);
    // insertMove("Bite", "dark", 60, 100);
    // insertMove("Fury Attack", "normal", 15, 85);
    // insertMove("Drill Peck", "normal", 80, 100);
    // insertMove("Wrap", "normal", 15, 90);
    // insertMove("Acid", "poison", 40, 100);
    // insertMove("Sucker Punch", "dark", 70, 100);
    // insertMove("Dig", "ground", 80, 100);
    // insertMove("Fury Swipes", "normal", 18, 80);
    // insertMove("Defense Curl", "normal", 0, 100);
    // insertMove("Slash", "ground", 70, 100);
    // insertMove("Thunder Shock", "electric", 40, 100);
    // insertMove("Thunder Punch", "electric", 75, 100);
    //
    // // bulbasaur
    // insertMovePokemon(1, 1);
    // insertMovePokemon(1, 2);
    // insertMovePokemon(1, 3);
    // insertMovePokemon(1, 4);
    //
    // // ivysaur
    // insertMovePokemon(2, 1);
    // insertMovePokemon(2, 2);
    // insertMovePokemon(2, 3);
    // insertMovePokemon(2, 4);
    //
    // // // venasaur
    // insertMovePokemon(3, 1);
    // insertMovePokemon(3, 3);
    // insertMovePokemon(3, 4);
    // insertMovePokemon(3, 5);
    //
    // // // charmander
    // insertMovePokemon(4, 6);
    // insertMovePokemon(4, 1);
    // insertMovePokemon(4, 7);
    // insertMovePokemon(4, 8);
    //
    // // charmeleon
    // insertMovePokemon(5, 6);
    // insertMovePokemon(5, 1);
    // insertMovePokemon(5, 7);
    // insertMovePokemon(5, 8);
    //
    // // charizard
    // insertMovePokemon(6, 1);
    // insertMovePokemon(6, 6);
    // insertMovePokemon(6, 7);
    // insertMovePokemon(6, 8);
    //
    // // squirtle
    // insertMovePokemon(7, 10);
    // insertMovePokemon(7, 11);
    // insertMovePokemon(7, 12);
    // insertMovePokemon(7, 18);
    //
    // // wartortle
    // insertMovePokemon(8, 10);
    // insertMovePokemon(8, 11);
    // insertMovePokemon(8, 12);
    // insertMovePokemon(8, 18);
    //
    // // // blastoise
    // // insertMovePokemon(9, 12);
    // // insertMovePokemon(9, 13);
    // // insertMovePokemon(9, 14);
    // // insertMovePokemon(9, 15);
    //
    // // caterpie
    // insertMovePokemon(10, 10);
    // insertMovePokemon(10, 16);
    //
    // // metapod
    // insertMovePokemon(11, 17);
    //
    // // butterfree
    // insertMovePokemon(12, 18);
    // insertMovePokemon(12, 19);
    // insertMovePokemon(12, 20);
    //
    // // weedle
    // insertMovePokemon(13, 21);
    // insertMovePokemon(13, 16);
    //
    // // kakuna
    // insertMovePokemon(14, 17);
    //
    // // // beedrill
    // insertMovePokemon(15, 22);
    // insertMovePokemon(15, 23);
    // insertMovePokemon(15, 24);
    // insertMovePokemon(15, 29);
    //
    // // pidgey
    // insertMovePokemon(16, 2);
    // insertMovePokemon(16, 22);
    // insertMovePokemon(16, 11);
    // insertMovePokemon(16, 18);
    //
    // // pidget
    // insertMovePokemon(17, 2);
    // insertMovePokemon(17, 22);
    // insertMovePokemon(17, 11);
    // insertMovePokemon(17, 18);
    //
    // // pidgeotto
    // insertMovePokemon(18, 22);
    // insertMovePokemon(18, 11);
    // insertMovePokemon(18, 18);
    // insertMovePokemon(18, 26);
    //
    // // rattata
    // insertMovePokemon(19, 10);
    // insertMovePokemon(19, 11);
    // insertMovePokemon(19, 26);
    // insertMovePokemon(19, 33);
    //
    // // raticate
    // insertMovePokemon(20, 10);
    // insertMovePokemon(20, 35);
    // insertMovePokemon(20, 26);
    //
    // // spearow
    // insertMovePokemon(21, 22);
    // insertMovePokemon(21, 29);
    // insertMovePokemon(21, 30);
    //
    // // fearow
    // insertMovePokemon(22, 22);
    // insertMovePokemon(22, 26);
    // insertMovePokemon(22, 27);
    // insertMovePokemon(22, 30);
    //
    // // ekans
    // insertMovePokemon(23, 32);
    // insertMovePokemon(23, 21);
    // insertMovePokemon(23, 31);
    //
    // // arbok
    // insertMovePokemon(24, 32);
    // insertMovePokemon(24, 21);
    // insertMovePokemon(24, 31);
    // insertMovePokemon(24, 25);
    //
    // // pikachu
    // insertMovePokemon(25, 1);
    // insertMovePokemon(25, 26);
    // insertMovePokemon(25, 38);
    //
    // // raichu
    // insertMovePokemon(26, 1);
    // insertMovePokemon(26, 26);
    // insertMovePokemon(26, 38);
    // insertMovePokemon(26, 39);
    //
    // // sandshrew
    // insertMovePokemon(27, 36);
    // insertMovePokemon(27, 6);
    // insertMovePokemon(27, 37);
    //
    // // sandslash
    // insertMovePokemon(28, 36);
    // insertMovePokemon(28, 6);
    // insertMovePokemon(28, 37);
    // insertMovePokemon(28, 34);
    // createUser("admin", "helloworld123", "adminator");
    // addCaughtPokemon(1, 1, 0, 0);
    // addCaughtPokemon(1, 4, 0, 0);
    // addCaughtPokemon(1, 4, 2, 0);
    // createUser("user1", "helloworld123", "usernator");
    // addCaughtPokemon(2, 3, 53, 53);
    // addCaughtPokemon(2, 6, 100, 0);

    // testing
    // System.out.println(getPokemonMoves(2));
    // System.out.println(getPlayerPokemon(1));
    // ArrayList<PokemonMoves> al = getPokemonMoves(2);
    // for (PokemonMoves pm : al) {
    // System.out.println(pm.getMoveName() + " " + pm.getMoveAttack());
    // }
    //
    // ArrayList<Pokemon> pl = getPlayerPokemon(1);
    // for (Pokemon p : pl) {
    // System.out.println("name: " + p.getName() + "\btype: " + p.getType() + "\n health"
    // + p.getHealth() + "\ncurr move: " + p.getMoveName(2));
    // }

    System.out.println(connectUser("user", "helloworld123"));
  }


  public static Boolean connectUser(String username, String pass) {
    String sql = String.format(
        "SELECT CASE WHEN EXISTS ( SELECT * FROM Players WHERE Username = '%s' and Password='%s')"
            + "\nTHEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END",
        username, pass);
    int result = 0;
    try {
      Connection conn = DriverManager.getConnection(url);
      PreparedStatement pstmt = conn.prepareStatement(sql);

      ResultSet rs = pstmt.executeQuery();
      result = rs.getInt(1);

      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (result == 1) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * This will add the pokemon caught to the player's inventory.
   * 
   * @param playerId
   * @param pokemonId
   * @param lvl
   * @param exp
   */
  public static void addCaughtPokemon(int playerId, int pokemonId, int lvl, int exp) {
    String sql = "INSERT INTO PlayerPokemon (PlayerId, PokemonId, Level, EXP) VALUES (?, ?, ?, ?)";

    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = DriverManager.getConnection(url);
      pstmt = conn.prepareStatement(sql);

      pstmt.setInt(1, playerId);
      pstmt.setInt(2, pokemonId);
      pstmt.setInt(3, lvl);
      pstmt.setInt(4, exp);

      pstmt.executeUpdate();
      System.out.println("Pokemon has been added to player's inventory!");

      pstmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * This function is used for getting all the moves of a pokemon encapsulated in PokemonMoves
   * class.
   * 
   * @param pokemonId Id of the pokemon.
   * @return An arraylist of PokemonMove object.
   */
  public static ArrayList<PokemonMoves> getPokemonMoves(int pokemonId) {
    ArrayList<PokemonMoves> moveList = new ArrayList<PokemonMoves>();
    String sql =
        "select a.name, a.type, a.damage, a.accuracy from AttackMoves a left join PokemonMoves pm on (a.MoveId=pm.MoveId) where pm.PokemonId="
            + pokemonId;

    try {
      Connection conn = DriverManager.getConnection(url);
      PreparedStatement pstmt = conn.prepareStatement(sql);

      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        moveList
            .add(new PokemonMoves(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
      }


      pstmt.close();
      conn.close();
    } catch (Exception e) {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
    return moveList;
  }



  /**
   * This will create Pokemon objects and store it in array list.
   * 
   * @param playerId id of the player in the database
   * @return an Array of Pokemon objects.
   */
  public static ArrayList<Pokemon> getPlayerPokemon(int playerId) {
    ArrayList<Pokemon> pokemonList = new ArrayList<Pokemon>();
    String sql =
        "select p.name, p.type, p.attack, p.defense, p.basehp, pp.level, pp.exp, p.PokemonId from PlayerPokemon PP left join Pokedex p on (pp.PokemonId=p.PokemonId) where pp.Playerid="
            + playerId;

    try {
      Connection conn = DriverManager.getConnection(url);
      PreparedStatement pstmt = conn.prepareStatement(sql);

      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        ArrayList<PokemonMoves> pm = getPokemonMoves(rs.getInt(8));

        pokemonList.add(new Pokemon(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
            rs.getInt(5), rs.getInt(6), rs.getInt(7), pm));

      }


      pstmt.close();
      conn.close();
    } catch (Exception e) {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
    return pokemonList;
  }


  public static String getUsers() {
    StringBuilder result = new StringBuilder();
    try {
      Connection conn = DriverManager.getConnection(url);
      Statement stmt = conn.createStatement();
      ResultSet rs;

      rs = stmt.executeQuery("SELECT * FROM Players");
      result.append("user id: " + rs.getString(1));
      result.append("\nusername: " + rs.getString(2));
      result.append("\npassword: " + rs.getString(3));
      result.append("\nnickname: " + rs.getString(4));

      System.out.println(result);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
    return result.toString();

  }


  public static void insertMovePokemon(int PokeId, int MoveId) {
    Connection c = null;
    Statement stmt = null;

    String sql = String.format("INSERT INTO PokemonMoves (PokemonId, MoveId) VALUES('%d', '%d')",
        PokeId, MoveId);
    System.out.println(sql);
    try {
      c = DriverManager.getConnection(url);
      c.setAutoCommit(false);
      stmt = c.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      c.commit();
      c.close();
      System.out.println("inserted Data!");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  /**
   * 
   * @param moveName Name of the move
   * @param type Type of move
   * @param damage Base damage of move
   * @param accuracy Accuracy of move. integer 1-100
   */
  public static void insertMove(String moveName, String type, int damage, int accuracy) {
    Connection c = null;
    Statement stmt = null;

    String sql = String.format(
        "INSERT INTO AttackMoves (Name, Type, Damage, Accuracy) VALUES('%s', '%s', '%d', '%d')",
        moveName, type, damage, accuracy);
    System.out.println(sql);
    try {
      c = DriverManager.getConnection(url);
      c.setAutoCommit(false);
      stmt = c.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      c.commit();
      c.close();
      System.out.println("inserted Data!");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public static void insertPokemon(String pokeName, String type, int atk, int def, int hp) {
    Connection c = null;
    Statement stmt = null;

    String sql = String.format(
        "INSERT INTO Pokedex (Name, Type, Attack, Defense, BaseHP) VALUES('%s', '%s', '%d', '%d', '%d')",
        pokeName, type, atk, def, hp);
    System.out.println(sql);
    try {
      c = DriverManager.getConnection(url);
      c.setAutoCommit(false);
      stmt = c.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      c.commit();
      c.close();
      System.out.println("inserted Data!");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  public static void createUser(String username, String password, String nickname) {
    Connection c = null;
    Statement stmt = null;
    String sql = String.format(
        "INSERT INTO PLayers (Username, Password, Nickname) VALUES ('%s', '%s', '%s')", username,
        password, nickname);

    System.out.println(sql);
    try {
      c = DriverManager.getConnection(url);
      c.setAutoCommit(false);
      stmt = c.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      c.commit();
      c.close();
      System.out.println("inserted Data");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }



  /**
   * This will create a new table on the database Pokemon_db
   * 
   * @param sql SQL query to be used for the creation of database.
   */
  public static void createNewTable(String sql) {

    try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement()) {
      // create a new table
      stmt.execute(sql);
      stmt.close();
      conn.close();
      System.out.println("table created!");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }


  /**
   * Connect to a database. If database is not found, it will create the database.
   *
   * @param fileName the database file name
   */
  public static void createNewDatabase() {
    try (Connection conn = DriverManager.getConnection(url)) {
      if (conn != null) {
        DatabaseMetaData meta = conn.getMetaData();
        System.out.println("The driver name is " + meta.getDriverName());
        System.out.println("A new database has been created.");
      }
      conn.close();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
