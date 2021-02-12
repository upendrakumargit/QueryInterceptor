package com.apple.gbi.gsf.multiplex.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import com.apple.gbi.gsf.multiplex.connection.TeradataConnectionWrapper;
import com.gbi.jdbc.TDDriver;

public class GbiTeradataDriverWrapper extends AbstractDriverWrapper {

  public GbiTeradataDriverWrapper() {
    super(new TDDriver());
  }

  @Override
  public Connection connect(String url, Properties info) throws SQLException {
    return new TeradataConnectionWrapper(this.getDelegate().connect(
        url.replaceAll("gbiteradatamultiplex", "gbiteradata"), info), info);
  }

  @Override
  public boolean acceptsURL(String url) throws SQLException {
    if ((url != null) && (url.startsWith("jdbc:gbiteradatamultiplex"))) {
      return true;
    }
    return this.getDelegate().acceptsURL(url);
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return Logger.getLogger(GbiTeradataDriverWrapper.class.getName());
  }

  static {
    try {
      DriverManager.registerDriver(new GbiTeradataDriverWrapper());
      System.out.println("Registered GbiTeradataDriverMultiplex successfully");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
