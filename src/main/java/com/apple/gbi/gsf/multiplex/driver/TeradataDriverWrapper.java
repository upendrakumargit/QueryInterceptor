package com.apple.gbi.gsf.multiplex.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import com.apple.gbi.gsf.multiplex.connection.TeradataConnectionWrapper;
import com.teradata.jdbc.TeraDriver;

public class TeradataDriverWrapper extends AbstractDriverWrapper {

  public TeradataDriverWrapper() {
    super(new TeraDriver());
  }

  @Override
  public Connection connect(String url, Properties info) throws SQLException {
    return new TeradataConnectionWrapper(this.getDelegate().connect(
        url.replaceAll("teradatamultiplex", "teradata"), info), info);
  }

  @Override
  public boolean acceptsURL(String url) throws SQLException {
    if ((url != null) && (url.startsWith("jdbc:teradatamultiplex"))) {
      return true;
    }
    return this.getDelegate().acceptsURL(url);
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return Logger.getLogger(TeradataDriverWrapper.class.getName());
  }

  static {
    try {
      DriverManager.registerDriver(new TeradataDriverWrapper());
      System.out.println("Registered TeradataDriverMultiplex successfully");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
