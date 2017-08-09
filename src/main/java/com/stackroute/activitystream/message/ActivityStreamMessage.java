package com.stackroute.activitystream.message;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins={"http://172.23.238.162:9011/activityStream/api/user","http://localhost:8888/api/message/"})
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