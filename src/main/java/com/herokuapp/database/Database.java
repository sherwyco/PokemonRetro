package com.herokuapp.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

  // get current directory
  private static String currentDir = System.getProperty("user.dir") + "/";

  // db path
  private static String url = "jdbc:sqlite:" + currentDir + "pokemon.db";

  public static void main(String[] args) {
    createNewDatabase();
    createNewTable();
  }

  /**
   * Create a new table in the test database
   *
   */
  public static void createNewTable() {
    // SQL statement for creating a new table
    String sql = "CREATE TABLE IF NOT EXISTS Player (\n" + " Id integer PRIMARY KEY,\n"
        + " Username text NOT NULL,\n" + " Password text NOT NULL, \n"
        + " nickname text NOT NULL \n" + ");";

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

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
