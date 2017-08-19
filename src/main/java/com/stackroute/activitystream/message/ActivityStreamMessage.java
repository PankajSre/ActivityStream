package com.stackroute.activitystream.message;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages={"com.stackroute.activitystream"})
@EntityScan(basePackages={"com.stackroute.activitystream.model"})
@EnableAspectJAutoProxy
public class ActivityStreamMessage {

	public static void main(String[] args) {
		SpringApplication.run(ActivityStreamMessage.class, args);
	}
	@Bean  
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){  
	    return hemf.getSessionFactory();  
	}  
	
	
}