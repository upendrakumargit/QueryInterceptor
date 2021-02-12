package com.apple.gbi.gsf.multiplex.datasource.context.local;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.apple.gbi.gsf.multiplex.driver.GbiTeradataDriverWrapper;
import com.apple.gbi.gsf.multiplex.driver.HanaDriverWrapper;
import com.apple.gbi.gsf.multiplex.driver.OracleDriverWrapper;
import com.apple.gbi.gsf.multiplex.driver.QuantumDBDriverWrapper;
import com.apple.gbi.gsf.multiplex.driver.TeradataDriverWrapper;
import com.apple.gbi.gsf.multiplex.driver.VerticaDriverWrapper;

public class LocalInitialContextMain {

  public void startProcess(String sql, String datasourceName) throws SQLException, NamingException {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    try {
      final DataSource ds = (DataSource) new InitialContext().lookup("jdbc/" + datasourceName);
      con = ds.getConnection();
      // do something with connection
      System.out.println(new Date(System.currentTimeMillis()) + " Done");
      String template = sql;
      System.out.println(new Date(System.currentTimeMillis()) + " Query Text: " + template);
      con.setAutoCommit(true);
      pstmt = con.prepareStatement(template);
      System.out.println(new Date(System.currentTimeMillis()) + " Executing Statement...");
      rs = pstmt.executeQuery();
      System.out.println(new Date(System.currentTimeMillis()) + " Done");
      int i = 0;
      System.out.println(new Date(System.currentTimeMillis())
          + " Looping through ResultSet object...");
      while (rs.next()) {
        i++;
      }
      System.out.println(new Date(System.currentTimeMillis()) + " Exported " + i + " rows ");
    } catch (SQLException se) {
      System.out.println("SQL Exception:");
      while (se != null) {
        System.out.println("State  : " + se.getSQLState());
        System.out.println("Message: " + se.getMessage());
        System.out.println("Error  : " + se.getErrorCode());
        se = se.getNextException();
      }
      try {
        if (null != pstmt) {
          pstmt.close();
        }
        if (null != rs) {
          rs.close();
        }
        if (null != con) {
          con.close();
        }
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (null != pstmt) {
          pstmt.close();
        }
        if (null != rs) {
          rs.close();
        }
        if (null != con) {
          con.close();
        }
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    } finally {
      try {
        if (null != pstmt) {
          pstmt.close();
        }
        if (null != rs) {
          rs.close();
        }
        if (null != con) {
          con.close();
        }
      } catch (Exception localException) {
        localException.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws Exception {
    final LocalInitialContextMain b = new LocalInitialContextMain();
    final DriverProperty driver = DriverProperty.Hana;
    final LocalContext ctx = LocalContextFactory.createLocalContext(driver.getDriverName());
    ctx.addDataSource("jdbc/" + driver.name() + "ds1", driver.getConnectionUrl(), driver.getUserName(), driver.getPassword());
    ctx.addDataSource("jdbc/" + driver.name() + "ds2", driver.getConnectionUrl(), driver.getProperties(driver.getUserName(), driver.getPassword()));
    b.startProcess(driver.getSql(), driver.name() + "ds1");
    b.startProcess(driver.getSql(), driver.name() + "ds2");
    ctx.close();
  }

  private enum DriverProperty {
    Oracle("jdbc:oraclemultiplex:thin:@//rn2-gsfd-ldb01.rno.apple.com:1546/gsfc1d", "<#USERNAME>",
        "<#PASSWORD>", OracleDriverWrapper.class.getName(), "select sysdate from dual"), 
    GbiTeradata(
        "jdbc:gbiteradatamultiplex://EDWUAT.corp.apple.com/charset=UTF8,DBS_PORT=1025",
        "<#USERNAME>", "<#PASSWORD>", GbiTeradataDriverWrapper.class.getName(),
        "select 'test' from sys_calendar.Calendar group by 1 order by 1"), 
    Teradata(
        "jdbc:teradatamultiplex://EDWUAT.corp.apple.com/charset=UTF8,DBS_PORT=1025", "<#USERNAME>",
        "<#PASSWORD>", TeradataDriverWrapper.class.getName(), "select date"), 
    Quantum(
        "jdbc:quantumdbmultiplex:@ma-ligerst-lapp04.corp.apple.com:8301", "<#USERNAME>",
        "<#PASSWORD>",
        QuantumDBDriverWrapper.class.getName(),
        "/*QDBHINT VPOD10*/ SELECT node_name FROM v_monitor.current_session"), 
    Vertica(
        "jdbc:verticamultiplex://ma1-vegad-lvdb01.corp.apple.com:5433/GBIVEGAD", "<#USERNAME>",
        "<#PASSWORD>", VerticaDriverWrapper.class.getName(), "select sysdate from dual"),
    Hana(
        "jdbc:sapmultiplex://mr2-pn9qa-hana0a.rno.apple.com:30015", "<#USERNAME>",
        "<#PASSWORD>", HanaDriverWrapper.class.getName(), "select 'test' from dummy");

    private final String connectionUrl;
    private final String userName;
    private final String password;
    private final String driverName;
    private final String sql;

    DriverProperty(String url, String user, String password, String driverName, String sql) {
      this.connectionUrl = url;
      this.userName = user;
      this.password = password;
      this.driverName = driverName;
      this.sql = sql;
    }

    public String getConnectionUrl() {
      return this.connectionUrl;
    }

    public String getUserName() {
      return this.userName;
    }

    public String getPassword() {
      return this.password;
    }

    public String getDriverName() {
      return this.driverName;
    }

    public String getSql() {
      return this.sql;
    }

    public Properties getProperties(String username, String password) {
      final Properties properties = new Properties();
      properties.put("user", username);
      properties.put("password", password);
      return properties;
    }
  }
}
