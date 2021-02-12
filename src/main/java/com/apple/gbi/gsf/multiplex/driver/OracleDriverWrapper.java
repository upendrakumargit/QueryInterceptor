package com.apple.gbi.gsf.multiplex.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import oracle.jdbc.driver.OracleDriver;

import com.apple.gbi.gsf.multiplex.connection.ConnectionWrapper;

public class OracleDriverWrapper extends AbstractDriverWrapper {

  public OracleDriverWrapper() {
    super(new OracleDriver());
  }

  @Override
  public Connection connect(String url, Properties info) throws SQLException {
    return new ConnectionWrapper(this.getDelegate().connect(
        url.replaceAll("oraclemultiplex", "oracle"), info), info);
  }

  @Override
  public boolean acceptsURL(String url) throws SQLException {
    if ((url != null) && (url.startsWith("jdbc:oraclemultiplex:thin"))) {
      return true;
    }
    return this.getDelegate().acceptsURL(url);
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return Logger.getLogger(OracleDriverWrapper.class.getName());
  }

  static {
    try {
      DriverManager.registerDriver(new OracleDriverWrapper());
      System.out.println("Registered OracleDriverMultiplex successfully");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
