package com.apple.gbi.gsf.multiplex.datasource.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.apple.gbi.gsf.multiplex.connection.ConnectionWrapper;
import com.apple.gbi.gsf.multiplex.constant.MultiplexConstant;
import com.zaxxer.hikari.HikariDataSource;

public class HikariDataSourceWrapper extends HikariDataSource {
  
  private Properties info = new Properties(); 
  
  @Override
  public Connection getConnection() throws SQLException {
    return new ConnectionWrapper(super.getConnection(), this.info);
  }
  
  @SuppressWarnings("deprecation")
  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return new ConnectionWrapper(super.getConnection(username, password), this.info);
  }
  
  public void setQdbHint(final String qdbHint) {
    this.info.putIfAbsent(MultiplexConstant.QDB_HINT, qdbHint);
  }
}
