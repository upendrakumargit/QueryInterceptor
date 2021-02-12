package com.apple.gbi.gsf.multiplex.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.apple.gbi.gsf.multiplex.connection.ConnectionWrapper;

public class DataSourceWrapper extends AbstractDataSourceWrapper {

  private Properties info;
  
  public DataSourceWrapper(DataSource delegate) {
    super(delegate);
  }
  
  public DataSourceWrapper(final DataSource delagate, final Properties info) {
    this(delagate);
    this.info = info;
  }

  @Override
  public Connection getConnection() throws SQLException {
    return new ConnectionWrapper(this.getDelegate().getConnection(), this.info);
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return new ConnectionWrapper(this.getDelegate().getConnection(username, password), this.info);
  }

}
