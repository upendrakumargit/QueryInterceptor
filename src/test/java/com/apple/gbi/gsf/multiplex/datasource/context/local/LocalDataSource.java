package com.apple.gbi.gsf.multiplex.datasource.context.local;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class LocalDataSource implements DataSource, Serializable {
  
  private static final long serialVersionUID = 1L;
  private String connectionString;
  private String username;
  private String password;
  private Properties info;

  public LocalDataSource(String connectionString, String username, String password) {
    this.connectionString = connectionString;
    this.username = username;
    this.password = password;
  }
  
  public LocalDataSource(String connectionString, Properties info) {
    this.connectionString = connectionString;
    this.info = info;
  }

  @Override
  public Connection getConnection() throws SQLException {
    if(null == this.info) {
      return DriverManager.getConnection(connectionString, username, password);
    }
    return DriverManager.getConnection(connectionString, this.info);
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return DriverManager.getConnection(connectionString, username, password);
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return null;
  }
  
  @Override
  public int getLoginTimeout() throws SQLException {
    return 0;
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {}

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {}

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return null;
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return null;
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return false;
  }
}
