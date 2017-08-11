package com.stackroute.activitystream.main;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(scanBasePackages={"com.stackroute.activitystream"})
@EntityScan(basePackages={"com.stackroute.activitystream.model"})
public class ActivityStreamUserCircle {

	public static void main(String[] args) {
		SpringApplication.run(ActivityStreamUserCircle.class, args);
	}
	@Bean  
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){  
	    return hemf.getSessionFactory();  
	}  
	
	
}