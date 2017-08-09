/*package com.stackroute.activitystream;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.activitystream.dao.UserDAO;
import com.stackroute.activitystream.main.ActivityStreamUser;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT,classes=ActivityStreamUser.class)
@EnableAspectJAutoProxy
public class UserTest {

	@Autowired
	private  UserDAO userDAO;

  
	@Test
	public void testValidateUser()
	{
		User validatedUser=userDAO.validateUser("krishna@gmail.com", "krishna");
		assertNotNull(validatedUser);
	}

}*/