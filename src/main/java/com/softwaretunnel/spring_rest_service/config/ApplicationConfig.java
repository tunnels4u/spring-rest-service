package com.softwaretunnel.spring_rest_service.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseConfigurer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(basePackages = "com.softwaretunnel.spring_rest_service.repository")
@EnableTransactionManagement
class ApplicationConfig {

	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	@Value("${spring.datasource.driverClassName}")
	private String driverClass;

	@Value("${spring.datasource.username}")
	private String userName;

	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.hikari.maximum-pool-size}")
	private Integer maxPoolSize;

	@Value("${spring.datasource.hikari.minimum-idle}")
	private Integer minIdle;

	@Value("${spring.datasource.hikari.idle-timeout}")
	private Integer idleTimeout;

	@Value("${spring.datasource.hikari.pool-name}")
	private String poolName;
	
	@Value("${spring.datasource.hikari.max-lifetime}")
	private Integer maxLifeTime;

	@Value("${spring.datasource.hikari.connection-timeout}")
	private Integer connectionTimeout;

//	@Bean
//	public DataSource dataSource() {
//
//		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//		EmbeddedDatabaseConfigurer embeddedDatabaseConfigurer = new EmbeddedDatabaseConfigurer() {
//
//			@Override
//			public void shutdown(DataSource dataSource, String databaseName) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void configureConnectionProperties(ConnectionProperties properties, String databaseName) {
//				properties.setUrl(datasourceUrl);
//				properties.setUsername(userName);
//				properties.setPassword(password);
//				try {
//					properties.setDriverClass((Class<? extends org.h2.Driver>) Class.forName(driverClass));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		};
//		builder.setDatabaseConfigurer(embeddedDatabaseConfigurer);
//		return builder.build();
//	}

	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(datasourceUrl); // File-based H2
		hikariConfig.setDriverClassName(driverClass);
		hikariConfig.setUsername(userName);
		hikariConfig.setPassword(password);

		// scalability of db
		hikariConfig.setMaximumPoolSize(maxPoolSize);
        hikariConfig.setMinimumIdle(minIdle);
        hikariConfig.setIdleTimeout(idleTimeout);
        hikariConfig.setMaxLifetime(maxLifeTime);
        hikariConfig.setConnectionTimeout(connectionTimeout);
        hikariConfig.setPoolName(poolName);
		
		return new HikariDataSource(hikariConfig);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.softwaretunnel.spring_rest_service.persistance.entity");
		factory.setDataSource(dataSource());
		return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}
}
