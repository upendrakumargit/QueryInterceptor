package com.apple.gbi.gsf.multiplex.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import com.apple.gbi.gsf.multiplex.connection.ConnectionWrapper;

public class VerticaDriverWrapper extends AbstractDriverWrapper {

  public VerticaDriverWrapper() {
    super(new com.vertica.jdbc.Driver());
  }

  @Override
  public Connection connect(String url, Properties info) throws SQLException {
    return new ConnectionWrapper(this.getDelegate().connect(
        url.replaceAll("verticamultiplex", "vertica"), info), info);
  }

  @Override
  public boolean acceptsURL(String url) throws SQLException {
    if ((url != null) && (url.startsWith("jdbc:verticamultiplex"))) {
      return true;
    }
    return this.getDelegate().acceptsURL(url);
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return Logger.getLogger(VerticaDriverWrapper.class.getName());
  }

  static {
    try {
      DriverManager.registerDriver(new VerticaDriverWrapper());
      System.out.println("Registered VerticaDriverMultiplex successfully");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
