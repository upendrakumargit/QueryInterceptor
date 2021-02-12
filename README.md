### This wrapper driver api is designed to handle the below scenario :

1. Intercept all SQL queries at centralized place
2. Validate SQL before execution for all database
3. Quantum database support for JNDI application for individual resource
4. Support of query execution in fallback/secondary database if primary database will be down (TODO)
5. Add prefix and postfix at end of SQL before execution (TODO)
5. Hikari datasource support with metrics publish feature which can be used for real time reporting purpose (TODO)

### Available Driver Details

| Database Name | JDBC URL | Driver Name |
|:---------------|:---------------|:------------|
|Oracle|jdbc:oraclemultiplex:thin:@//HOST:PORT/SID|com.apple.gbi.gsf.multiplex.driver.OracleDriverWrapper|
|Teradata|jdbc:teradatamultiplex://HOST/charset=UTF8,DBS_PORT=PORT|com.apple.gbi.gsf.multiplex.driver.TeradataDriverWrapper|
|GBITeradata|jdbc:gbiteradatamultiplex://HOST/charset=UTF8,DBS_PORT=PORT|com.apple.gbi.gsf.multiplex.driver.GbiTeradataDriverWrapper|
|Vertica|jdbc:verticamultiplex://HOST:PORT/SID|com.apple.gbi.gsf.multiplex.driver.VerticaDriverWrapper|
|QuantumDB|jdbc:quantumdbmultiplex:@HOST:PORT|com.apple.gbi.gsf.multiplex.driver.QuantumDBDriverWrapper|
|Hana|jdbc:sapmultiplex://HOST:PORT|com.apple.gbi.gsf.multiplex.driver.HanaDriverWrapper|

### Available DataSource Details

| Name |DataSource Class Name |
|:---------------|:---------------|
|C3P0|com.apple.gbi.gsf.multiplex.datasource.pool.ComboPooledDataSourceWrapper|
|HIKARI|com.apple.gbi.gsf.multiplex.datasource.pool.HikariDataSourceWrapper|
|DBCP2|com.apple.gbi.gsf.multiplex.datasource.pool.BasicDataSourceWrapper|
|DBCP (1.4)|com.apple.gbi.gsf.multiplex.datasource.pool.legacy.BasicDataSourceWrapper|

### Available JNDI Factory Details

| Name |DataSource Class Name |
|:---------------|:---------------|
|Tomcat|com.apple.gbi.gsf.multiplex.datasource.factory.tomcat.DataSourceFactoryWrapper|
|HIKARI|com.apple.gbi.gsf.multiplex.datasource.factory.tomcat.HikariJNDIFactoryWrapper|
|DBCP2|com.apple.gbi.gsf.multiplex.datasource.factory.tomcat.BasicDataSourceFactoryWrapper|
|DBCP (1.4)|com.apple.gbi.gsf.multiplex.datasource.factory.tomcat.legacy.BasicDataSourceFactoryWrapper|

To setup datasource multiplex api following configuration needs to be done for Jetty, Tomcat and Jboss server. 

#### Jetty JNDI Setup for QuantumDB

##### Using DBCP2 BasicDataSource Connection Pool

```.xml

	<New id="JNDINAME" class="org.eclipse.jetty.plus.jndi.Resource">
     	<Arg>jdbc/JNDINAME</Arg>
     	<Arg>
			<New class="com.apple.gbi.gsf.multiplex.datasource.pool.BasicDataSourceWrapper">
			   <Set name="driverClassName">com.apple.gbi.gsf.multiplex.driver.QuantumDBDriverWrapper</Set>
			   <Set name="url">jdbc:quantumdbmultiplex:@HOST:PORT</Set>
			   <Set name="username">USER_NAME</Set>
			   <Set name="password">PASSWORD</Set>
			   <Set name="qdbHint">QDBHINT</Set>
			</New>
		</Arg>
    </New>
    
```

##### Using C3P0 ComboPooledDataSource Connection Pool

```.xml

	<New id="JNDINAME" class="org.eclipse.jetty.plus.jndi.Resource">
     	<Arg>jdbc/JNDINAME</Arg>
     	<Arg>
			<New class="com.apple.gbi.gsf.multiplex.datasource.pool.ComboPooledDataSourceWrapper">
			   <Set name="driverClass">com.apple.gbi.gsf.multiplex.driver.QuantumDBDriverWrapper</Set>
			   <Set name="jdbcUrl">jdbc:quantumdbmultiplex:@HOST:PORT</Set>
			   <Set name="user">USER_NAME</Set>
			   <Set name="password">PASSWORD</Set>
			   <Set name="qdbHint">QDBHINT</Set>
			</New>
		</Arg>
    </New>
    
```

Note : Please set the below parameter when you face any issue with connection with ComboPooledDataSourceWrapper.

```.xml

	<Set name="forceUseNamedDriverClass">true</Set>
	
```

##### Using HikariDataSource Connection Pool

```.xml

	<New id="JNDINAME" class="org.eclipse.jetty.plus.jndi.Resource">
     	<Arg>jdbc/JNDINAME</Arg>
     	<Arg>
			<New class="com.apple.gbi.gsf.multiplex.datasource.pool.HikariDataSourceWrapper">
			   <Set name="driverClassName">com.apple.gbi.gsf.multiplex.driver.QuantumDBDriverWrapper</Set>
			   <Set name="jdbcUrl">jdbc:quantumdbmultiplex:@HOST:PORT</Set>
			   <Set name="userName">USER_NAME</Set>
			   <Set name="password">PASSWORD</Set>
			   <Set name="qdbHint">QDBHINT</Set>
			</New>
		</Arg>
    </New>
    
```

### Jetty JNDI Setup for Oracle

To Setup the oracle JNDI please use the Oracle driver (as mentioned above in Driver details table). You can use any type of datasource. Please find the sample below. 

```.xml

	<New id="JNDINAME" class="org.eclipse.jetty.plus.jndi.Resource">
     	<Arg>jdbc/JNDINAME</Arg>
     	<Arg>
			<New class="com.apple.gbi.gsf.multiplex.datasource.pool.BasicDataSourceWrapper">
			   <Set name="driverClassName">com.apple.gbi.gsf.multiplex.driver.OracleDriverWrapper</Set>
			   <Set name="url">jdbc:oraclemultiplex:thin:@//HOST:PORT/SID</Set>
			   <Set name="username">USER_NAME</Set>
			   <Set name="password">PASSWORD</Set>
			</New>
		</Arg>
    </New>

	<New id="JNDINAME" class="org.eclipse.jetty.plus.jndi.Resource">
     	<Arg>jdbc/JNDINAME</Arg>
     	<Arg>
			<New class="com.apple.gbi.gsf.multiplex.datasource.pool.ComboPooledDataSourceWrapper">
			   <Set name="driverClass">com.apple.gbi.gsf.multiplex.driver.OracleDriverWrapper</Set>
			   <Set name="jdbcUrl">jdbc:oraclemultiplex:thin:@//HOST:PORT/SID</Set>
			   <Set name="user">USER_NAME</Set>
			   <Set name="password">PASSWORD</Set>
			</New>
		</Arg>
    </New>

	<New id="JNDINAME" class="org.eclipse.jetty.plus.jndi.Resource">
     	<Arg>jdbc/JNDINAME</Arg>
     	<Arg>
			<New class="com.apple.gbi.gsf.multiplex.datasource.pool.HikariDataSourceWrapper">
			   <Set name="driverClassName">com.apple.gbi.gsf.multiplex.driver.OracleDriverWrapper</Set>
			   <Set name="jdbcUrl">jdbc:oraclemultiplex:thin:@//HOST:PORT/SID</Set>
			   <Set name="userName">USER_NAME</Set>
			   <Set name="password">PASSWORD</Set>
			</New>
		</Arg>
    </New>
    
```

Note : Similar you can setup the JNDI for other driver. Please use the proper driver name and URL pattern to use datasource multiplex api.

#### Tomcat JNDI Setup for QuantumDB

##### Using TOMCAT DBCP DataSourceFactory Connection Pool

```.xml

	<Resource name="jdbc/JNDINAME" auth="Container"
			  factory="com.apple.gbi.gsf.multiplex.datasource.factory.tomcat.DataSourceFactoryWrapper" 
              type="javax.sql.DataSource" driverClassName="com.apple.gbi.gsf.multiplex.driver.QuantumDBDriverWrapper"
              url="jdbc:quantumdbmultiplex:@HOST:PORT" qdbHint="QDBHINT"
              username="USER_NAME" password="PASSWORD"/>
              
```

##### Using Legacy DBCP BasicDataSourceFactory (DBCP 1.4) Connection Pool

```.xml

	<Resource name="jdbc/JNDINAME" auth="Container"
			  factory="com.apple.gbi.gsf.multiplex.datasource.factory.tomcat.legacy.BasicDataSourceFactoryWrapper" 
              type="javax.sql.DataSource" driverClassName="com.apple.gbi.gsf.multiplex.driver.QuantumDBDriverWrapper"
              url="jdbc:quantumdbmultiplex:@HOST:PORT" qdbHint="QDBHINT"
              username="USER_NAME" password="PASSWORD"/>
              
```

##### Using DBCP2 BasicDataSourceFactory Connection Pool

```.xml

	<Resource name="jdbc/JNDINAME" auth="Container"
			  factory="com.apple.gbi.gsf.multiplex.datasource.factory.tomcat.BasicDataSourceFactoryWrapper" 
              type="javax.sql.DataSource" driverClassName="com.apple.gbi.gsf.multiplex.driver.QuantumDBDriverWrapper"
              url="jdbc:quantumdbmultiplex:@HOST:PORT" qdbHint="QDBHINT"
              username="USER_NAME" password="PASSWORD"/>
              
```

##### Using HikariJNDIFactory Connection Pool

```.xml

	<Resource name="jdbc/JNDINAME" auth="Container"
			  factory="com.apple.gbi.gsf.multiplex.datasource.factory.tomcat.HikariJNDIFactoryWrapper" 
              type="javax.sql.DataSource" driverClassName="com.apple.gbi.gsf.multiplex.driver.QuantumDBDriverWrapper"
              jdbcUrl="jdbc:quantumdbmultiplex:@HOST:PORT" qdbHint="QDBHINT"
              username="USER_NAME" password="PASSWORD"/>
              
```

For more information about Hikrai Datasource please refer the below URL.

<https://github.com/brettwooldridge/HikariCP>

<https://github.com/brettwooldridge/HikariCP/wiki/JNDI-DataSource-Factory-(Tomcat,-etc.)>

#### Tomcat JNDI Setup for Oracle

```.xml
	
	<Resource name="jdbc/JNDINAME" auth="Container"
			  factory="com.apple.gbi.gsf.multiplex.datasource.factory.tomcat.DataSourceFactoryWrapper" 
              type="javax.sql.DataSource" driverClassName="com.apple.gbi.gsf.multiplex.driver.OracleDriverWrapper"
              url="jdbc:oraclemultiplex:thin:@//HOST:PORT/SID"
              username="USER_NAME" password="PASSWORD"/>
	
	<Resource name="jdbc/JNDINAME" auth="Container"
			  factory="com.apple.gbi.gsf.multiplex.datasource.factory.tomcat.BasicDataSourceFactoryWrapper" 
              type="javax.sql.DataSource" driverClassName="com.apple.gbi.gsf.multiplex.driver.OracleDriverWrapper"
              url="jdbc:oraclemultiplex:thin:@//HOST:PORT/SID"
              username="USER_NAME" password="PASSWORD"/>
              
    <Resource name="jdbc/JNDINAME" auth="Container"
			  factory="com.apple.gbi.gsf.multiplex.datasource.factory.tomcat.legacy.BasicDataSourceFactoryWrapper" 
              type="javax.sql.DataSource" driverClassName="com.apple.gbi.gsf.multiplex.driver.OracleDriverWrapper"
              url="jdbc:oraclemultiplex:thin:@//HOST:PORT/SID"
              username="USER_NAME" password="PASSWORD"/>
              
	<Resource name="jdbc/JNDINAME" auth="Container"
			  factory="com.apple.gbi.gsf.multiplex.datasource.factory.tomcat.HikariJNDIFactoryWrapper" 
              type="javax.sql.DataSource" driverClassName="com.apple.gbi.gsf.multiplex.driver.OracleDriverWrapper"
              jdbcUrl="jdbc:oraclemultiplex:thin:@//HOST:PORT/SID"
              username="USER_NAME" password="PASSWORD"/>
              
```

Note : Similar you can setup the JNDI for other driver. Please use the proper driver name and URL pattern to use datasource multiplex api.

#### JBOSS JNDI Setup for QuantumDB

##### Using DBCP2 BasicDataSource Connection Pool

```.xml

	<subsystem xmlns="urn:jboss:domain:datasources:1.1">
            <datasources>
                <datasource jndi-name="java:jboss/datasources/JNDINAME" pool-name="JNDINAME" enabled="true" use-java-context="true">
                    <connection-url>jdbc:quantumdbmultiplex:@HOST:PORT</connection-url>
                    <driver>datasource-multiplex</driver>
                    <driver-class>com.apple.gbi.gsf.multiplex.driver.QuantumDBDriverWrapper</driver-class>
                    <datasource-class>com.apple.gbi.gsf.multiplex.datasource.pool.BasicDataSourceWrapper</datasource-class>
                    <connection-property name="qdbHint">QDBHINT</connection-property>
                    <pool>
                        <min-pool-size>MIN_POOL_SIZE</min-pool-size>
                        <max-pool-size>MAX_POOL_SIZE</max-pool-size>
                    </pool>
                    <security>
                        <user-name>USER_NAME</user-name>
                        <password>PASSWORD</password>
                    </security>
                </datasource>
                <datasource jndi-name="java:jboss/datasources/JNDINAME_1" pool-name="JNDINAME_1" enabled="true" use-java-context="true">
                    <connection-url>jdbc:oraclemultiplex:thin:@//HOST:PORT/SID</connection-url>
                    <driver>datasource-multiplex</driver>
                    <driver-class>com.apple.gbi.gsf.multiplex.driver.OracleDriverWrapper</driver-class>
                    <datasource-class>com.apple.gbi.gsf.multiplex.datasource.pool.BasicDataSourceWrapper</datasource-class>
                    <pool>
                        <min-pool-size>MIN_POOL_SIZE</min-pool-size>
                        <max-pool-size>MAX_POOL_SIZE</max-pool-size>
                    </pool>
                    <security>
                        <user-name>USER_NAME</user-name>
                        <password>PASSWORD</password>
                    </security>
                </datasource>
                <drivers>
                    <driver name="datasource-multiplex" module="com.multiplex"/>
                    .....
                </drivers>
            </datasources>
   </subsystem>
    
```

Once you will configure the above datasource in JBOSS server then it will return the "org.jboss.jca.adapters.jdbc.WrapperDataSource" Object. For more details please refer the below URL.
Similar you can setup the JNDI for other driver. Please use the proper driver name and URL pattern to use datasource multiplex api.

<https://docs.jboss.org/author/display/AS7/Admin+Guide#AdminGuide-DataSources>

<https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Application_Platform/6.4/html/Administration_and_Configuration_Guide/sect-Example_Datasources.html>

<https://developer.jboss.org/thread/203514>

<http://www.duanqu.tech/questions/4765215/how-to-configure-jndi-datasource-in-jboss-using-hikaricp>

Note : Please make sure you should register "com.multiplex" as module under JBOSS modules folder.

#### Distribution build for Jetty Server without dependency libs

mvn clean install -Pjettynodependencybuild

#### Distribution build for Jetty Server with dependency libs

mvn clean install -Pjettybuild

#### Distribution build for Tomcat Server with dependency libs

mvn clean install -Ptomcatbuild

#### Distribution build for JBOSS Server with dependency libs

mvn clean install -Pjbossbuild