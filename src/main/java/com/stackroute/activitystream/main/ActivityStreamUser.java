package com.stackroute.activitystream.main;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication(scanBasePackages={"com.stackroute.activitystream"})
//redundant annotation
@EntityScan(basePackages={"com.stackroute.activitystream.model"})
public class ActivityStreamUser {
 
	private static final Logger logger = LoggerFactory.getLogger(ActivityStreamUser.class);
	public static void main(String[] args) {
		logger.debug("User Service Application starting...");
		SpringApplication.run(ActivityStreamUser.class, args);
	}
	
	//create an app context for the beans. Main class should remain clean
	@Bean(destroyMethod="")  
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){  
		logger.debug("Hibernate Session factory initialized");
	    return hemf.getSessionFactory();  
	}  
	
	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
	
}
