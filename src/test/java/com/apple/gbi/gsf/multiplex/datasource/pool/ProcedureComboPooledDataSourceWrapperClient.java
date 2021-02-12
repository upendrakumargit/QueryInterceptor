package com.apple.gbi.gsf.multiplex.datasource.pool;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.apple.gbi.gsf.multiplex.constant.ResultSetType;

public class ProcedureComboPooledDataSourceWrapperClient {

  public static void main(String[] args) throws Exception {
    final ComboPooledDataSourceWrapper pool = new ComboPooledDataSourceWrapper();
    Connection connection = null;
    CallableStatement callableStatement = null;
    String procedure = "call UDMTOOL_STG_USER.tdprc_pgh_hq_demo_select(?, ?)";
    try {
      //pool.setDriverClass("com.apple.gbi.gsf.multiplex.driver.GbiTeradataDriverWrapper");
      //pool.setJdbcUrl("jdbc:gbiteradatamultiplex://EDWDEV.corp.apple.com/charset=UTF8,DBS_PORT=1025");

       //pool.setDriverClass("com.gbi.jdbc.TDDriver");
       //pool.setJdbcUrl("jdbc:gbiteradata://EDWDEV.corp.apple.com/charset=UTF8,DBS_PORT=1025");

       //pool.setDriverClass("com.teradata.jdbc.TeraDriver");
       //pool.setJdbcUrl("jdbc:teradata://EDWDEV.corp.apple.com/charset=UTF8,DBS_PORT=1025");
       // LOG=DEBUG
       
       //pool.setDriverClass("com.apple.gbi.gsf.multiplex.driver.TeradataDriverWrapper");
       //pool.setJdbcUrl("jdbc:teradatamultiplex://EDWDEV.corp.apple.com/charset=UTF8,DBS_PORT=1025");
      
      pool.setDriverClass("com.apple.gbi.gsf.multiplex.driver.QuantumDBDriverWrapper");
      pool.setJdbcUrl("jdbc:quantumdbmultiplex:@ma-bobjt-lapp65.corp.apple.com:8301");
      pool.setUser("<#USERNAME>");
      pool.setPassword("<#PASSWORD>");
      pool.setResultSetType(ResultSetType.TYPE_SCROLL_INSENSITIVE.name());
      pool.setQdbHint("/*QDBHINT TPOD_AOSS_UDM_WEB_USER_DAO*/");
      procedure = "call GCRM_STG_USER.AOSS_ADVISORNOTES_MAPPING_S(?, ?)";
          
      //pool.setUser("<#USERNAME>");
      //pool.setPassword("<#PASSWORD>");
      
      pool.setForceUseNamedDriverClass(true);
      System.out.println("The Driver Object is of Class: " + pool.getDriverClass());
      connection = pool.getConnection();
      callableStatement = connection.prepareCall(procedure);
      //callableStatement = connection.prepareCall(procedure, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      System.out.println("The Connection Object is of Class: " + connection.getClass());

      callableStatement.setString(1, "");
      callableStatement.setString(2, "");

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
        if (null != callableStatement) {
          callableStatement.close();
        }
        if (null != connection && !connection.isClosed()) {
          connection.close();
        }
        if (null != pool) {
          pool.close();
        }
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (null != callableStatement) {
          callableStatement.close();
        }
        if (null != connection && !connection.isClosed()) {
          connection.close();
        }
        if (null != pool) {
          pool.close();
        }
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    } finally {
      try {
        if (null != callableStatement) {
          callableStatement.close();
        }
        if (null != connection && !connection.isClosed()) {
          connection.close();
        }
        if (null != pool) {
          pool.close();
        }
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    }
  }

}
