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

  /**
   * 
   * @param username
   * @return This will return the nickname of the user in the database.
   */
  public String getUserNick(String username) {
    String sql = String.format("Select Nickname from Players where Username='%s'", username);
    String nickName = "Username not found!";
    try {
      Connection conn = DriverManager.getConnection(url);
      PreparedStatement pstmt = conn.prepareStatement(sql);

      ResultSet rs = pstmt.executeQuery();
      nickName = rs.getString(1);

      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return nickName;
  }

  /**
   * This method will return a boolean value that indicates if a user exists in the database.
   * 
   * @param username Username of the user in the database.
   * @param pass Password of the user.
   * @return
   */
  public Boolean checkUser(String username, String pass) {
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
  public void addCaughtPokemon(int playerId, int pokemonId, int lvl, int exp) {
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
   * 
   * @param pokemonId Id of the pokemon.
   * @return Pokemon object.
   */
  public Pokemon getPokemon(int pokemonId) {
    Pokemon pokemon = null;
    String sql = "select * from Pokedex where PokemonId=" + pokemonId;
    ArrayList<PokemonMoves> pm = null;

    try {
      Connection conn = DriverManager.getConnection(url);
      PreparedStatement pstmt = conn.prepareStatement(sql);

      ResultSet rs = pstmt.executeQuery();
      pm = getPokemonMoves(rs.getInt(1)); // get moves of pokemon based on id

      pokemon = new Pokemon(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
          rs.getInt(6), 0, 0, pm);

      pstmt.close();
      conn.close();
    } catch (Exception e) {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }

    return pokemon;
  }

  /**
   * This function is used for getting all the moves of a pokemon encapsulated in PokemonMoves
   * class.
   * 
   * @param pokemonId Id of the pokemon.
   * @return An arraylist of PokemonMove object.
   */
  public ArrayList<PokemonMoves> getPokemonMoves(int pokemonId) {
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
  public ArrayList<Pokemon> getPlayerPokemon(int playerId) {
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


  /**
   * View of Pokedex table
   * 
   * @return List of all pokemon in the database.
   */
  public String viewPokedex() {
    StringBuilder result = new StringBuilder();
    try {
      Connection conn = DriverManager.getConnection(url);
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Pokedex");

      while (rs.next()) {
        result.append("\nPokemon id: " + rs.getInt(1));
        result.append("\nPokemon name: " + rs.getString(2));
        result.append("\ntype: " + rs.getString(3));
        result.append("\nattack: " + rs.getString(4));
        result.append("\ndef: " + rs.getString(5));
        result.append("\nhp: " + rs.getString(6) + "\n");
      }

      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
    return result.toString();

  }

  public String getUsers() {
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
  public void insertMove(String moveName, String type, int damage, int accuracy) {
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

  public void insertPokemon(String pokeName, String type, int atk, int def, int hp) {
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

  public void createUser(String username, String password, String nickname) {
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
  public void createNewTable(String sql) {

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
  public void createNewDatabase() {
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
