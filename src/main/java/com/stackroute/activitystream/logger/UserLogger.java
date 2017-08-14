package com.stackroute.activitystream.logger;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserLogger {

	public static final Logger logger=LoggerFactory.getLogger(UserLogger.class);
	
	@Before("loginPointcut()")
	public void loggerBeforeLogin()
	{
		logger.warn("You are entering the User detials ");
	}
	@After("loginPointcut()")
	public void loggerAfterLogin()
	{
		logger.warn("You have Successfully loggedIn ");
	}
	
	@Pointcut("execution(public * getAllUsers())")
	public void loginPointcut()
	{
		
	}
	
	//this code is minimal. Please implement all logging through aspects.
	
}
