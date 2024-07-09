package com.coffeeorderproject.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;


import com.coffeeorderproject.mapper.BoardMapper;
import com.coffeeorderproject.mapper.UserMapper;
import com.coffeeorderproject.service.AccountService;
import com.coffeeorderproject.service.AccountServiceImpl;
import com.coffeeorderproject.service.BoardService;
import com.coffeeorderproject.service.BoardServiceImpl;

@Configuration
@MapperScan(basePackages = { "com.coffeeorderproject.mapper" })  // == <mybatis:scan base-package="com.coffeeorderproject.mapper"/>
@EnableTransactionManagement // <tx:annotation-driven 과 같은 역할
public class RootConfiguration {

	// application.properties 의 정보를 읽어서 저장하는 객체
	@Autowired
	Environment env;

	@Bean
	// Environment 객체에 저장된 속성 중에서 spring.datasource.hikari로 시작하는 속성을 적용
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	HikariConfig hikariConfig() {
		return new HikariConfig();
	}

	@Bean
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource(hikariConfig());
		return dataSource;
	}
	
//	@Bean
//	public DataSource dataSource() {
//		HikariDataSource dataSource = new HikariDataSource();
//		dataSource.setJdbcUrl(env.getProperty("spring.datasource.hikari.jdbc-url"));
//		dataSource.setDriverClassName(env.getProperty("spring.datasource.hikari.driver-class-name"));
//		dataSource.setUsername(env.getProperty("spring.datasource.hikari.username"));
//		dataSource.setPassword(env.getProperty("spring.datasource.hikari.password"));
//		return dataSource;
//	}
	
	@Bean SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		return factoryBean.getObject();
	}
	
	@Bean AccountService accountService(UserMapper userMapper) throws Exception {
		AccountServiceImpl accountService = new AccountServiceImpl();
		accountService.setUserMapper(userMapper);
		return accountService;
	}
	
	@Bean BoardService boardService(BoardMapper boardMapper) throws Exception {
		BoardServiceImpl boardService = new BoardServiceImpl();
		boardService.setBoardMapper(boardMapper);
//		boardService.setTransactionTemplate(transactionTemplate());
		return boardService;
	}
	
	@Bean PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}
	
	@Bean TransactionTemplate transactionTemplate() {
		TransactionTemplate transactionTemplate = new TransactionTemplate();
		transactionTemplate.setTransactionManager(transactionManager());
		return transactionTemplate;
	}
	
}







