package com.apple.gbi.gsf.multiplex.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import com.apple.gbi.gsf.multiplex.connection.ConnectionWrapper;
import com.apple.ist.quantumdb.driver.jdbc.QuantumDBDriver;

public class QuantumDBDriverWrapper extends AbstractDriverWrapper {

  public QuantumDBDriverWrapper() {
    super(new QuantumDBDriver());
  }

  @Override
  public Connection connect(String url, Properties info) throws SQLException {
    return new ConnectionWrapper(this.getDelegate().connect(
        url.replaceAll("quantumdbmultiplex", "quantumdb"), info), info);
  }

  @Override
  public boolean acceptsURL(String url) throws SQLException {
    if ((url != null) && (url.startsWith("jdbc:quantumdbmultiplex"))) {
      return true;
    }
    return this.getDelegate().acceptsURL(url);
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return this.getDelegate().getParentLogger();
  }

  static {
    try {
      DriverManager.registerDriver(new QuantumDBDriverWrapper());
      System.out.println("Registered QuantumDBDriverMultiplex successfully");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
