package com.herokuapp.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

  // get current directory
  private static String currentDir = System.getProperty("user.dir") + "/";

  // db path
  private static String url = "jdbc:sqlite:" + currentDir + "pokemon_db";

  // create table name Players
  private final static String PlayerTable = "CREATE TABLE IF NOT EXISTS Players (\n"
      + " PlayerId integer PRIMARY KEY,\n" + " Username text NOT NULL UNIQUE,\n"
      + " Password text NOT NULL, \n" + " Nickname text NOT NULL UNIQUE)";

  // create player info
  private final static String PlayerData = "CREATE TABLE IF NOT EXISTS PlayerData (\n"
      + "PlayerId integer, \n" + "PokeBalls integer, \n" + "GreatBalls integer, \n"
      + "UltraBalls integer, \n" + "MasterBalls integer, \n" + "Pokemons integer , \n" // amount of
                                                                                       // pokemons
      + "PRIMARY KEY (PlayerId),\n" + "FOREIGN KEY (PlayerId) REFERENCES Players (PlayerId) \n"
      + "ON DELETE CASCADE ON UPDATE NO ACTION)";


  // create table name Pokedex
  private final static String PokedexTable = "CREATE TABLE IF NOT EXISTS Pokedex (\n"
      + "PokemonId integer PRIMARY KEY, \n" + "Name text NOT NULL UNIQUE, \n" // pokemon name
      + "Type text NOT NULL, \n" + "BaseHP integer NOT NULL)";

  // create table name PokemonMoves
  private final static String MovesTable =
      "CREATE TABLE IF NOT EXISTS AttackMoves (" + "MoveId integer PRIMARY KEY , \n"
          + "Name text NOT NULL UNIQUE, \n" + "Type text NOT NULL, \n" // move type
          + "Damage integer NOT NULL, \n" + "Accuracy integer NOT NULL)"; // 1-100 value only. when
                                                                          // attacking accuracy/100
                                                                          // for the chance


  // create table PlayerPokemon which holds all the players pokemons
  private final static String PlayerPokemonTable = "CREATE TABLE IF NOT EXISTS PlayerPokemon ("
      + "PlayerId integer, \n" + "PokemonId integer, \n" + "Level integer NOT NULL, \n"
      + "EXP integer NOT NULL, \n" + "PRIMARY KEY (PlayerId, PokemonId),\n"
      + "FOREIGN KEY (PlayerId) REFERENCES Players (PlayerId) \n"
      + "ON DELETE CASCADE ON UPDATE NO ACTION,\n"
      + "FOREIGN KEY (PokemonId) REFERENCES Pokedex (PokemonId) \n"
      + "ON DELETE CASCADE ON UPDATE NO ACTION)";

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
    // insertPokemon("Bulbasaur", "grass", 45);
    // insertPokemon("Ivysaur", "grass", 60);
    // insertPokemon("Venusaur", "grass", 80);
    // insertPokemon("Charmander", "fire", 39);
    // insertPokemon("Charmeleon", "fire", 58);
    // insertPokemon("Charizard", "fire", 78);
    // insertPokemon("Squirtle", "water", 44);
    // insertPokemon("Wartortle", "water", 59);
    // insertPokemon("Blastoise", "water", 79);
    // insertPokemon("Caterpie", "bug", 45);
    // insertPokemon("Metapod", "bug", 50);
    // insertPokemon("Butterfree", "bug", 60);
    // insertPokemon("Weedle", "bug", 40);
    // insertPokemon("Kakuna", "bug", 45);
    // insertPokemon("Beedrill", "bug", 65);
    // insertPokemon("Pidgey", "flying", 40);
    // insertPokemon("Pidgeotto", "flying", 63);
    // insertPokemon("Pidgeot", "flying", 83);
    // insertPokemon("Rattata", "normal", 30);
    // insertPokemon("Raticate", "normal", 50);
    // insertPokemon("Spearow", "flying", 40);
    // insertPokemon("Fearow", "flying", 65);
    // insertPokemon("Ekans", "poison", 35);
    // insertPokemon("Arbok", "poison", 60);
    // insertPokemon("Pikachu", "electric", 35);
    // insertPokemon("Raichu", "electric", 60);
    // insertPokemon("Sandshrew", "ground", 50);
    // insertPokemon("SandSlash", "ground", 75);
    // insertMove("Growl", "normal", 0, 100);
    // insertMove("Tacke", "normal", 40, 100);
    // insertMove("Vine Whip", "grass", 45, 100);



  }


  public static String fetchAllData() {
    StringBuilder result = new StringBuilder();
    try {
      Connection conn = DriverManager.getConnection(url);
      Statement stmt = conn.createStatement();
      ResultSet rs;

      rs = stmt.executeQuery("SELECT * FROM PlayerData");
      result.append("user id: " + rs.getString(1));
      result.append("\nusername: " + rs.getString(2));
      result.append("\npassword: " + rs.getString(3));
      result.append("\nickname: " + rs.getString(4));

      System.out.println(result);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
    return result.toString();

  }

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

  public static void insertPokemon(String pokeName, String type, int hp) {
    Connection c = null;
    Statement stmt = null;

    String sql = String.format("INSERT INTO Pokedex (Name, Type, BaseHP) VALUES('%s', '%s', '%d')",
        pokeName, type, hp);
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
