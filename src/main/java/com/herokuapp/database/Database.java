package com.herokuapp.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

  // get current directory
  private static String currentDir = System.getProperty("user.dir") + "/";

  // db path
  private static String url = "jdbc:sqlite:" + currentDir + "test.db";

  public static void main(String[] args) {
    connect();
  }

  /**
   * Connect to a sample database
   */
  public static void connect() {
    Connection conn = null;
    try {
      // create a connection to the database
      conn = DriverManager.getConnection(url);

      System.out.println("Connection to SQLite has been established.");

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
    }
  }


  /**
   * create a sample database
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
