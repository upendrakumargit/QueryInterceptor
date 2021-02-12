package com.apple.gbi.gsf.multiplex.driver;

import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

public abstract class AbstractDriverWrapper implements Driver {

  private final Driver delegate;

  public AbstractDriverWrapper(final Driver delegate) {
    this.delegate = delegate;
  }

  @Override
  public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
    return this.delegate.getPropertyInfo(url, info);
  }

  @Override
  public int getMajorVersion() {
    return this.delegate.getMajorVersion();
  }

  @Override
  public int getMinorVersion() {
    return this.delegate.getMinorVersion();
  }

  @Override
  public boolean jdbcCompliant() {
    return this.delegate.jdbcCompliant();
  }

  public Driver getDelegate() {
    return this.delegate;
  }

}
