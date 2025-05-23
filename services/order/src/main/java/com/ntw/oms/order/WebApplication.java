package com.ntw.oms.order;

import com.ntw.common.config.*;
import com.ntw.common.security.CORSFilter;
import com.ntw.oms.cart.dao.CartDaoFactory;
import com.ntw.oms.order.dao.OrderDaoFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.servlet.ServletContextListener;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.ntw.oms.order", "com.ntw.oms.cart"})
@PropertySource(value = { "classpath:config.properties" })
@EnableAutoConfiguration(exclude={CassandraDataAutoConfiguration.class})
public class WebApplication extends SpringBootServletInitializer {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebApplication.class);
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory(
            @Value("${server.port:8080}") int port,
            @Value("${server.threadPool.threads.minimum:1}") int minThreadCount,
            @Value("${server.threadPool.threads.maximum:10}") int maxThreadCount,
            @Value("${server.threadPool.threads.idleTime:60000}") int idleThreadTimeout
    ) {
        JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
        factory.setPort(port);
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMinThreads(minThreadCount);
        threadPool.setMaxThreads(maxThreadCount);
        threadPool.setIdleTimeout(idleThreadTimeout);
        factory.setThreadPool(threadPool);
        return factory;
    }

    @Bean
    public FilterRegistrationBean loggingFilterBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new LoggingFilter());
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public FilterRegistrationBean corsFilterBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new CORSFilter());
        bean.setOrder(2);
        return bean;
    }

    @Bean
    public EnvConfig envConfig(Environment environment) {
        // Added this bean to view env vars on console/log
        EnvConfig envConfigBean = new EnvConfig();
        envConfigBean.setEnvironment(environment);
        return envConfigBean;
    }

    @Bean
    public ServletListenerRegistrationBean<ServletContextListener>
    appConfigListenerRegistration() {
        AppConfigListener appConfigListener = new AppConfigListener();
        ServletListenerRegistrationBean<ServletContextListener> bean =
                new ServletListenerRegistrationBean<>();
        bean.setListener(appConfigListener);
        return bean;
    }

    @Bean("cartDaoFactory")
    public FactoryBean serviceLocatorFactoryBean1() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(CartDaoFactory.class);
        return factoryBean;
    }

    @Bean("orderDaoFactory")
    public FactoryBean serviceLocatorFactoryBean2() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(OrderDaoFactory.class);
        return factoryBean;
    }

}
