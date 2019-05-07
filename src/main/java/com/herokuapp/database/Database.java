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
  private static String url = "jdbc:sqlite:" + currentDir + "pokemon.db";

  public static void main(String[] args) {
    fetchAllData();
  }

  /**
   * 
   * @param username
   * @throws SQLException
   */
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

      System.out.println(result);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
    return result.toString();

  }


  public static void insertData(String username, String password, String nickname) {
    Connection c = null;
    Statement stmt = null;

    String sql = "INSERT INTO PlayerData (Username, Password, Nickname) VALUES ('" + username
        + "', '" + password + "', '" + nickname + "')";
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
   * Create a new table in the test database
   *
   */
  public static void createNewTable() {
    // SQL statement for creating a new table
    String sql = "CREATE TABLE IF NOT EXISTS PlayerData (\n" + " Id integer PRIMARY KEY,\n"
        + " Username text NOT NULL UNIQUE,\n" + " Password text NOT NULL, \n"
        + " Nickname text NOT NULL UNIQUE \n" + ");";

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
