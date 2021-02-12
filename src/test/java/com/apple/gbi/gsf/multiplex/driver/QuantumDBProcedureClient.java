package com.apple.gbi.gsf.multiplex.driver;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import com.apple.gbi.gsf.multiplex.constant.MultiplexConstant;
import com.apple.gbi.gsf.multiplex.constant.ResultSetType;

public class QuantumDBProcedureClient {
  
  public static void main(String[] args) {
    CallableStatement callableStatement = null;
    Connection myConn = null;
    String connString = "jdbc:quantumdbmultiplex:@ma-bobjt-lapp65.corp.apple.com:8301";
    final Properties properties = new Properties();
    try {
      System.out.println(new Date(System.currentTimeMillis()) + " Getting connection...");
      Class.forName("com.apple.gbi.gsf.multiplex.driver.QuantumDBDriverWrapper");
      properties.put("user", "<#USERNAME>");
      properties.put("password", "<#PASSWORD>");
      properties.put(MultiplexConstant.RESULTSET_TYPE, ResultSetType.TYPE_SCROLL_INSENSITIVE.name());
      properties.put(MultiplexConstant.QDB_HINT, "/*QDBHINT TPOD_AOSS_UDM_WEB_USER_DAO*/");
      myConn = DriverManager.getConnection(connString, properties);
      System.out.println(new Date(System.currentTimeMillis()) + " Done");
      String template = "call GCRM_STG_USER.AOSS_ADVISORNOTES_MAPPING_S(?, ?)";
      System.out.println(new Date(System.currentTimeMillis()) + " Query Text: " + template);
      myConn.setAutoCommit(true);
      System.out.println(new Date(System.currentTimeMillis()) + " Executing Statement...");
      callableStatement = myConn.prepareCall(template);
      callableStatement.setString(1, "");
      callableStatement.setString(2, "");
      System.out.println(new Date(System.currentTimeMillis()) + " Done");
      boolean rsReturned = callableStatement.execute();
      int counter = 0;
      if (rsReturned) {
        do {
          counter++;
          final ResultSet rs = callableStatement.getResultSet();
          System.out.println("Printing Result Set # " + counter);
          while (rs.next()) {
            for (int colNum = 1; colNum <= rs.getMetaData().getColumnCount(); colNum++) {
              System.out.println(" " + rs.getMetaData().getColumnName(colNum) + " : "
                  + rs.getString(colNum));
            }
          }
        } while (!((callableStatement.getMoreResults() == false) && (callableStatement
            .getUpdateCount() == -1)));
      }
    } catch (SQLException se) {
      System.out.println("SQL Exception:");
      while (se != null) {
        System.out.println("State  : " + se.getSQLState());
        System.out.println("Message: " + se.getMessage());
        System.out.println("Error  : " + se.getErrorCode());
        se = se.getNextException();
      }
      try {
        callableStatement.close();
        myConn.close();
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
      try {
        callableStatement.close();
        myConn.close();
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    } finally {
      try {
        callableStatement.close();
        myConn.close();
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    }
  }
}
