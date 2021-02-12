package com.apple.gbi.gsf.multiplex.datasource.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BasicDataSourceWrapperClient {

  public static void main(String[] args) throws Exception {
    final BasicDataSourceWrapper pool = new BasicDataSourceWrapper();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      pool.setDriverClassName("com.apple.gbi.gsf.multiplex.driver.GbiTeradataDriverWrapper");
      pool.setUrl("jdbc:gbiteradatamultiplex://EDWUAT.corp.apple.com/charset=UTF8,DBS_PORT=1025");
      pool.setUsername("<#USERNAME>");
      pool.setPassword("<#PASSWORD>");
      pool.setQdbHint("/*QDBHINT VPOD11*/");
      connection = pool.getConnection();
      preparedStatement = connection.prepareStatement("select 'test' as name from sys_calendar.Calendar group by 1 order by 1");
      System.out.println("The Connection Object is of Class: " + connection.getClass());
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
          System.out.println(resultSet.getString(1));
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
        if(null != preparedStatement) {
          preparedStatement.close();
        }
        if(null != resultSet) {
          resultSet.close();
        }
        if(null != connection) {
          connection.close();
        }
        if(null != pool) {
          pool.close();
        }
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
      try {
        if(null != preparedStatement) {
          preparedStatement.close();
        }
        if(null != resultSet) {
          resultSet.close();
        }
        if(null != connection) {
          connection.close();
        }
        if(null != pool) {
          pool.close();
        }
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    } finally {
      try {
        if(null != preparedStatement) {
          preparedStatement.close();
        }
        if(null != resultSet) {
          resultSet.close();
        }
        if(null != connection) {
          connection.close();
        }
        if(null != pool) {
          pool.close();
        }
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    }
  }

}
