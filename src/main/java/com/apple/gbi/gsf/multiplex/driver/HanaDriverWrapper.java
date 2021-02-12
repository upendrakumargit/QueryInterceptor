package com.apple.gbi.gsf.multiplex.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import com.apple.gbi.gsf.multiplex.connection.ConnectionWrapper;

public class HanaDriverWrapper extends AbstractDriverWrapper {

  public HanaDriverWrapper() {
    super(new com.sap.db.jdbc.Driver());
  }

  @Override
  public Connection connect(String url, Properties info) throws SQLException {
    return new ConnectionWrapper(this.getDelegate().connect(
        url.replaceAll("sapmultiplex", "sap"), info), info);
  }

  @Override
  public boolean acceptsURL(String url) throws SQLException {
    if ((url != null) && (url.startsWith("jdbc:sapmultiplex"))) {
      return true;
    }
    return this.getDelegate().acceptsURL(url);
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return Logger.getLogger(HanaDriverWrapper.class.getName());
  }

  static {
    try {
      DriverManager.registerDriver(new HanaDriverWrapper());
      System.out.println("Registered HanaDriverMultiplex successfully");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
