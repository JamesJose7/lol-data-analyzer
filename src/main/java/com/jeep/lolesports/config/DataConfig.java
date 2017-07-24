package com.jeep.lolesports.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

/**
 * Created by jeeps on 3/3/2017.
 */
@Configuration
@PropertySource("app.properties")
public class DataConfig {
    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        Resource config = new ClassPathResource("hibernate.cfg.xml");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setConfigLocation(config);
        sessionFactory.setPackagesToScan(env.getProperty("lol.entity.package"));
        sessionFactory.setDataSource(dataSource());
        return sessionFactory;
    }

    @Bean
    @Profile("dev")
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();

        //Driver class name
        ds.setDriverClassName(env.getProperty("lol.db.driver"));
        //Set the URL
        ds.setUrl(env.getProperty("lol.db.url"));
        //Set credentials
        ds.setUsername(env.getProperty("lol.db.username"));
        ds.setPassword(env.getProperty("lol.db.password"));

        return ds;
    }

    @Bean(name = "dataSource")
    @Profile("prod")
    public DataSource prodDataSource() {
        BasicDataSource ds = new BasicDataSource();

        ds.setDriverClassName(env.getProperty("lol.db.prod.driver"));
        ds.setUrl(env.getProperty("lol.db.prod.url"));
        ds.setUsername(env.getProperty("lol.db.prod.username"));
        ds.setPassword(env.getProperty("lol.db.prod.password"));

        return ds;
    }
}
