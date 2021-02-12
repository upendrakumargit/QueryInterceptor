package com.apple.gbi.gsf.multiplex.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class TeradataConnectionWrapper extends ConnectionWrapper {

  public TeradataConnectionWrapper(Connection delegate) {
    super(delegate);
  }
  
  public TeradataConnectionWrapper(Connection delegate, final Properties info) {
    super(delegate, info);
  }

  @Override
  public boolean isValid(int timeout) throws SQLException {
    // Support of validate connection check for Teradata
    // Below feature supported in Teradata higher version of JDBC driver
    /*
      final CurrentThreadIO localCurrentThreadIO = TeraDriver.getCurrentThreadDefaultConnection();
      final TDSession localTDSession = (TDSession) localCurrentThreadIO.getConnection();
      return localTDSession.isValid(timeout);
     */
    // Alternative we can write our own implementation logic for validate connection
    // https://stackoverflow.com/questions/11350336/how-to-implement-isvalid-connection-for-using-with-oracle-driver-classes12
    return true;
  }

}
