package com.stackroute.activitystream.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.activitystream.dao.UserCircleDAO;
import com.stackroute.activitystream.main.ActivityStreamUserCircle;
import com.stackroute.activitystream.model.UserCircle;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT,classes=ActivityStreamUserCircle.class)
@EnableAspectJAutoProxy
public class UserCircleJunitTest {
	
	@Autowired
	private UserCircleDAO userCircleDAO;
	@Autowired
	private UserCircle userCircle;
    @Ignore
	@Test
	public void addToUserCircle()
	{
		userCircle.setCircleName("general");
		userCircle.setDateOfJoining(new Date());
		userCircle.setStatus(true);
		userCircle.setSubscriberId("advik@gmail.com");
		
		assertEquals(true, userCircleDAO.addCircle(userCircle));
	}
	@Ignore
	@Test
	public void testToGetAllCircles()
	{
		List<UserCircle> circles=userCircleDAO.getAllCircles();
		assertNotNull(circles);
		assertEquals(2, circles.size());		
	}
	
    @Ignore
	@Test
	public void testToDeleteUserCircle()
	{
		userCircle=userCircleDAO.getCircleByName("general");
		assertEquals(true, userCircleDAO.deleteCircle(userCircle));
	}
	
 @Ignore
  @Test
  public void testToUpdateUserCircle()
  {
	 userCircle=userCircleDAO.getCircleByName("general");
	  userCircle.setStatus(false);
	  assertEquals(true, userCircleDAO.updateCircle(userCircle));
	  
	  
  }

	

}
