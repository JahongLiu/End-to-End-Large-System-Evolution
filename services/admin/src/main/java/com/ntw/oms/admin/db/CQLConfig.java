package com.ntw.oms.admin.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;

@Configuration
@PropertySource(value = { "classpath:config.properties" })
public class CQLConfig {

    @Autowired
    private Environment environment;

    protected String getKeyspaceName() {
        return environment.getProperty("database.cassandra.keySpace");
    }

    @Bean
    @ConditionalOnExpression("'${database.type}'.equals('CQL') or '${database.type}'.equals('ALL')")
    public CassandraCqlClusterFactoryBean cluster() {
        CassandraCqlClusterFactoryBean cluster = new CassandraCqlClusterFactoryBean();
        cluster.setContactPoints(environment.getProperty("database.cassandra.hosts"));
        cluster.setPort(Integer.parseInt(environment.getProperty("database.cassandra.port")));
        return cluster;
    }

    /*
     * Factory bean that creates the com.datastax.driver.core.Session instance
     */
    @Bean
    @ConditionalOnExpression("'${database.type}'.equals('CQL') or '${database.type}'.equals('ALL')")
    public CassandraCqlSessionFactoryBean session() {
        CassandraCqlSessionFactoryBean session = new CassandraCqlSessionFactoryBean();
        session.setCluster(cluster().getObject());
        session.setKeyspaceName(getKeyspaceName());
        return session;
    }

    @Bean
    @ConditionalOnExpression("'${database.type}'.equals('CQL') or '${database.type}'.equals('ALL')")
    public CqlTemplate cqlTemplate() {
        return new CqlTemplate(session().getObject());
    }

    @Bean
    @ConditionalOnExpression("'${database.type}'.equals('CQL') or '${database.type}'.equals('ALL')")
    public CassandraMappingContext mappingContext() {
        BasicCassandraMappingContext mappingContext =  new BasicCassandraMappingContext();
        mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster().getObject(),
                getKeyspaceName()));

        return mappingContext;
    }

    @Bean
    @ConditionalOnExpression("'${database.type}'.equals('CQL') or '${database.type}'.equals('ALL')")
    public CassandraConverter converter() {
        return new MappingCassandraConverter(mappingContext());
    }

}