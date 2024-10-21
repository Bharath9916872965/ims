package com.vts.ims.cfg;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.vts.*",
        entityManagerFactoryRef = "EntityManagerMySql",
        transactionManagerRef = "transactionManagerMySql" )

@EnableTransactionManagement
public class JpaConfigurationMySql
{
	
	  @Autowired
	    private Environment env;
	 
	 
	 
	 @Primary
	    @Bean
	    public DataSource mysqlDataSource() {
	  
	        HikariDataSource dataSource
	          = new HikariDataSource();
	        dataSource.setDriverClassName(
	          env.getProperty("app.datasource.mysql.driver-class-name"));
	        dataSource.setJdbcUrl(env.getProperty("app.datasource.mysql.url"));
	        dataSource.setUsername(env.getProperty("app.datasource.mysql.username"));
	        dataSource.setPassword(env.getProperty("app.datasource.mysql.password"));
	 
	        return dataSource;
	    }
	 
	 
	 
	 
	 
	 @Primary
	    @Bean("EntityManagerMySql")
	    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
	        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	        factoryBean.setDataSource(mysqlDataSource());
	        factoryBean.setPackagesToScan(new String[] { "com.vts.*" });
	        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
	        factoryBean.setJpaProperties(jpaProperties());
	        return factoryBean;
	    }
	 
	    /*
	     * Provider specific adapter.
	     */
	 @Primary
	    @Bean
	    public JpaVendorAdapter jpaVendorAdapter() {
	        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
	        return hibernateJpaVendorAdapter;
	    }
	 
	    /*
	     * Here you can specify any provider specific properties.
	     */
	    private Properties jpaProperties() {
	        Properties properties = new Properties();
	        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
	        properties.put("hibernate.hbm2ddl.auto", "update");
	        properties.put("hibernate.show_sql", "true");
	        return properties;
	    }
	    @Primary
	    @Bean
	    @Autowired
	    public PlatformTransactionManager transactionManagerMySql(EntityManagerFactory emf) {
	        JpaTransactionManager txManager = new JpaTransactionManager();
	        txManager.setEntityManagerFactory(emf);
	        return txManager;
	    }
}