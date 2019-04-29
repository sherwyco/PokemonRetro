package com.herokuapp.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDatabase {

  public static void main(String[] args) {
    String currentDir = System.getProperty("user.dir") + "/";
    System.out.println(currentDir);
  }

  /**
   * Connect to a sample database
   *
   * @param fileName the database file name
   */
  public static void createNewDatabase(String fileName) {
    String currentDir = System.getProperty("user.dir") + "/";

    String url = "jdbc:sqlite:" + currentDir + fileName;

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
