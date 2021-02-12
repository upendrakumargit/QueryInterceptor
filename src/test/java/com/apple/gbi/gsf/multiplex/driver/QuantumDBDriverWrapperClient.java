package com.apple.gbi.gsf.multiplex.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class QuantumDBDriverWrapperClient {
  
  public static void main(String[] args) {
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    Connection myConn = null;
    String connString = "jdbc:quantumdbmultiplex:@ma-ligerst-lapp04.corp.apple.com:8301";
    try {
      System.out.println(new Date(System.currentTimeMillis()) + " Getting connection...");
      Class.forName("com.apple.gbi.gsf.multiplex.driver.QuantumDBDriverWrapper");
      myConn = DriverManager.getConnection(connString, "<#USERNAME>", "<#PASSWORD>");
      System.out.println(new Date(System.currentTimeMillis()) + " Done");
      String template = "/*QDBHINT VPOD10*/ SELECT node_name FROM v_monitor.current_session";
      System.out.println(new Date(System.currentTimeMillis()) + " Query Text: " + template);
      myConn.setAutoCommit(true);
      pstmt = myConn.prepareStatement(template);
      System.out.println(new Date(System.currentTimeMillis()) + " Executing Statement...");
      rs = pstmt.executeQuery();
      System.out.println(new Date(System.currentTimeMillis()) + " Done");
      int i = 0;
      System.out.println(new Date(System.currentTimeMillis()) + " Looping through ResultSet object...");
      while (rs.next()) {
        i++;
      }
      System.out.println(new Date(System.currentTimeMillis()) + " Exported " + i + " rows ");
    } catch (SQLException se) {
      System.out.println("SQL Exception:");
      while (se != null) {
        System.out.println("State  : " + se.getSQLState());
        System.out.println("Message: " + se.getMessage());
        System.out.println("Error  : " + se.getErrorCode());
        se = se.getNextException();
      }
      try {
        pstmt.close();
        rs.close();
        myConn.close();
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
      try {
        pstmt.close();
        rs.close();
        myConn.close();
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    } finally {
      try {
        pstmt.close();
        rs.close();
        myConn.close();
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    }
  }
}
