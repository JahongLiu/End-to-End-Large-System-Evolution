package com.ntw.oms.order;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraCqlSessionFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = { "classpath:config.properties" })
public class CartDBConfiguration {

    @Autowired
    private Environment environment;

    protected String getKeyspaceName() {
        return environment.getProperty("database.cassandra.keySpace");
    }

    @Bean
    @ConditionalOnProperty(name = "database.type", havingValue = "CQL")
    public CassandraClusterFactoryBean cartDBCluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(environment.getProperty("database.cassandra.hosts"));
        cluster.setPort(Integer.parseInt(environment.getProperty("database.cassandra.port")));
        return cluster;
    }

    @Bean
    @ConditionalOnProperty(name = "database.type", havingValue = "CQL")
    public CassandraCqlSessionFactoryBean cartCqlSession() {
        CassandraCqlSessionFactoryBean session = new CassandraCqlSessionFactoryBean();
        session.setCluster(cartDBCluster().getObject());
        session.setKeyspaceName(getKeyspaceName());
        return session;
    }

    @Bean
    @Qualifier("cartCqlTemplate")
    @ConditionalOnProperty(name = "database.type", havingValue = "CQL")
    public CqlTemplate cartCqlTemplate() throws Exception {
        return new CqlTemplate(cartCqlSession().getObject());
    }

    @Bean
    @ConditionalOnProperty(name = "database.type", havingValue = "CQL")
    public CassandraMappingContext cartDBMappingContext() {
        BasicCassandraMappingContext mappingContext =  new BasicCassandraMappingContext();
        mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cartDBCluster().getObject(),
                getKeyspaceName()));

        return mappingContext;
    }

    @Bean
    @ConditionalOnProperty(name = "database.type", havingValue = "CQL")
    public CassandraConverter cartConverter() {
        return new MappingCassandraConverter(cartDBMappingContext());
    }

    /*
     * Factory bean that creates the com.datastax.driver.core.Session instance
     */
    @Bean
    @ConditionalOnProperty(name = "database.type", havingValue = "CQL")
    public CassandraSessionFactoryBean cartDBSession() throws Exception {
        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
        session.setCluster(cartDBCluster().getObject());
        session.setKeyspaceName(getKeyspaceName());
        session.setConverter(cartConverter());
        session.setSchemaAction(SchemaAction.NONE);
        return session;
    }

    @Bean
    @Qualifier("cartCassandraOperations")
    @ConditionalOnProperty(name = "database.type", havingValue = "CQL")
    public CassandraOperations cartCassandraTemplate() throws Exception {
        return new CassandraTemplate(cartDBSession().getObject());
    }


    private String getDriverClass() {
        return "org.postgresql.Driver";
    }

    private String getPostgresUrl() {
        String host = environment.getProperty("database.postgres.host");
        String port = environment.getProperty("database.postgres.port");
        String database = environment.getProperty("database.postgres.schema");
        return "jdbc:postgresql://"+host+":"+port+"/"+database;
    }

    public String getUser() {
        return environment.getProperty("database.postgres.user.name");
    }

    public String getPassword() {
        return environment.getProperty("database.postgres.user.password");
    }

    public int getInitialPoolSize() {
        return Integer.parseInt(environment.getProperty("database.postgres.cp.size.min"));
    }

    public int getMaxPoolSize() {
        return Integer.parseInt(environment.getProperty("database.postgres.cp.size.max"));
    }

    @Bean
    @ConditionalOnProperty(name = "database.type", havingValue = "SQL")
    public DataSource postgresCartDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(getDriverClass());
        dataSource.setUrl(getPostgresUrl());
        dataSource.setUsername(getUser());
        dataSource.setPassword(getPassword());
        dataSource.setInitialSize(getInitialPoolSize());
        dataSource.setMaxTotal(getMaxPoolSize());
        return dataSource;
    }

    @Bean
    @Qualifier("cartJdbcTemplate")
    @ConditionalOnProperty(name = "database.type", havingValue = "SQL")
    public JdbcTemplate cartJdbcTemplate(DataSource postgresCartDataSource) {
        return new JdbcTemplate(postgresCartDataSource);
    }

}
