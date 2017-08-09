/*package com.stackroute.activitystream;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT,classes=ActivityStreamUser.class)
@ComponentScan(basePackages={"com.stackroute.activitystream"})
public class UserMocketoTest {

	@TestConfiguration
	static class UserDAOImplTestConfiguration
	{
		@Bean
		public UserDAO userDAO()
		{
			return new UserDAOImpl();
		}
	}
	@Autowired
	@MockBean
	UserDAO userDAO;
	
	@Before
	public void init()
	{
		User user = new User();
		user.setEmailId("rahul@gmail.com");
		Mockito.when(userDAO.getUserByEmailId(user.getEmailId()))
	      .thenReturn(user);
	}
	
	@Test
	public void whenValidName_thenEmployeeShouldBeFound()
	{
		String emailId = "rahul@gmail.com";
	    User foundUser = userDAO.getUserByEmailId(emailId);
	    userDAO.saveUser(foundUser);
	     assertEquals(foundUser.getEmailId(),emailId);
	      
	}
}*/