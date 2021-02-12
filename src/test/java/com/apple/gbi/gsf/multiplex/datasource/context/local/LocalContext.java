package com.apple.gbi.gsf.multiplex.datasource.context.local;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;

public class LocalContext extends InitialContext implements InitialContextFactoryBuilder,
    InitialContextFactory {

  private Map<Object, Object> dataSources;

  public LocalContext() throws NamingException {
    super();
    dataSources = new HashMap<Object, Object>();
  }

  public void addDataSource(String name, String connectionString, String username, String password) {
    this.dataSources.put(name, new LocalDataSource(connectionString, username, password));
  }
  
  public void addDataSource(String name, String connectionString, Properties info) {
    this.dataSources.put(name, new LocalDataSource(connectionString, info));
  }

  public InitialContextFactory createInitialContextFactory(Hashtable<?, ?> environment)
      throws NamingException {
    dataSources.putAll(environment);
    return this;
  }

  public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
    return this;
  }

  @Override
  public Object lookup(String name) throws NamingException {
    Object ret = dataSources.get(name);
    // super.lookup will go infinte loop if name does not exist
    return (ret != null) ? ret : super.lookup(name);
  }
  
}
