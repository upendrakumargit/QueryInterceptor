package com.apple.gbi.gsf.multiplex.datasource.pool;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.sql.ConnectionPoolDataSource;

import com.apple.gbi.gsf.multiplex.connection.ConnectionWrapper;
import com.apple.gbi.gsf.multiplex.constant.MultiplexConstant;
import com.mchange.v2.c3p0.AbstractComboPooledDataSource;

public class ComboPooledDataSourceWrapper extends AbstractComboPooledDataSource implements
    Serializable, Referenceable {

  // serialization stuff -- set up bound/constrained property event handlers on deserialization
  private static final long serialVersionUID = 1;
  private static final short VERSION = 0x0002;

  private Properties info = new Properties();
  
  private String qdbHint;
  
  private String resultSetType;
  
  private String resultSetConcurrency;

  public ComboPooledDataSourceWrapper() {
    super();
  }

  public ComboPooledDataSourceWrapper(boolean autoregister) {
    super(autoregister);
  }

  public ComboPooledDataSourceWrapper(String configName) {
    super(configName);
  }

  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.writeShort(VERSION);
  }

  private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
    short version = ois.readShort();
    switch (version) {
      case VERSION:
        // ok
        break;
      default:
        throw new IOException("Unsupported Serialized Version: " + version);
    }
  }

  @Override
  public Reference getReference() throws NamingException {
    return super.getReference();
  }

  @Override
  public synchronized ConnectionPoolDataSource getConnectionPoolDataSource() {
    return super.getConnectionPoolDataSource();
  }

  @Override
  public Connection getConnection() throws SQLException {
    return new ConnectionWrapper(super.getConnection(), this.info);
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return new ConnectionWrapper(super.getConnection(username, password), this.info);
  }

  public void setQdbHint(String qdbHint) {
    this.info.putIfAbsent(MultiplexConstant.QDB_HINT, qdbHint);
  }
  
  public String getQdbHint() {
    return qdbHint;
  }

  public void setResultSetType(String resultSetType) {
    this.info.putIfAbsent(MultiplexConstant.RESULTSET_TYPE, resultSetType);
  }

  public String getResultSetType() {
    return resultSetType;
  }

  public void setResultSetConcurrency(String resultSetConcurrency) {
    this.info.putIfAbsent(MultiplexConstant.RESULTSET_CONCURRENCY, resultSetConcurrency);
  }
  
  public String getResultSetConcurrency() {
    return resultSetConcurrency;
  }

}
