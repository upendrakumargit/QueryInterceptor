package com.apple.gbi.gsf.multiplex.datasource.pool.legacy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.apple.gbi.gsf.multiplex.connection.ConnectionWrapper;
import com.apple.gbi.gsf.multiplex.constant.MultiplexConstant;

public class BasicDataSourceWrapper extends BasicDataSource {
    
  private Properties info = new Properties(); 
  
  @Override
  protected DataSource createDataSource() throws SQLException {
    return super.createDataSource();
  }
  
  @Override
  public Connection getConnection() throws SQLException {
    return new ConnectionWrapper(super.getConnection(), this.info);
  }
  
  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return new ConnectionWrapper(super.getConnection(username, password), this.info);
  }

  public void setQdbHint(final String qdbHint) {
    this.info.putIfAbsent(MultiplexConstant.QDB_HINT, qdbHint);
  }

}
