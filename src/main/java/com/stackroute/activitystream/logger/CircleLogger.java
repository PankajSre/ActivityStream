package com.stackroute.activitystream.logger;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Aspect
public class CircleLogger {

	public static final Logger logger=LoggerFactory.getLogger(CircleLogger.class);
	
	@Before("loginPointcut()")
	public void loggerBeforeCircleCreating()
	{
		logger.info("Before the method call ");
	}
	@After("loginPointcut()")
	public void loggerAfterLogin()
	{
		logger.info("After the method call ");
	}
	
	@Pointcut("execution(public * *(..))")
	public void loginPointcut()
	{
		
	}
}
