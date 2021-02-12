package com.apple.gbi.gsf.multiplex.datasource.factory.tomcat;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.DataSourceFactory;

import com.apple.gbi.gsf.multiplex.constant.MultiplexConstant;
import com.apple.gbi.gsf.multiplex.datasource.DataSourceWrapper;

public class DataSourceFactoryWrapper extends DataSourceFactory {

  @Override
  public Object getObjectInstance(Object obj, Name name, Context nameCtx,
      Hashtable<?, ?> environment) throws Exception {
    System.out.println("Started initialization of DataSourceFactoryWrapper#getObjectInstance");
    final Properties info = new Properties();
    final Reference ref = (Reference) obj;
    final Enumeration<RefAddr> reqEnum = ref.getAll();
    while (reqEnum.hasMoreElements()) {
      final StringRefAddr userObj = (StringRefAddr) reqEnum.nextElement();
      final String propType = userObj.getType();
      final String propValue = (String) userObj.getContent();
      if (propType.equals(MultiplexConstant.QDB_HINT)) {
        info.putIfAbsent(MultiplexConstant.QDB_HINT, propValue);
        break;
      }
    }
    System.out.println("DataSourceFactoryWrapper initialized for resource name [" + name
        + "] and Context Name [" + nameCtx + "] with QDB HINT setup [" + info.toString() + "]");
    return new DataSourceWrapper((DataSource) super.getObjectInstance(obj, name, nameCtx,
        environment), info);
  }

}
