package com.stackroute.activitystream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.activitystream.controller.UserRestController;
import com.stackroute.activitystream.dao.UserDAO;
import com.stackroute.activitystream.main.ActivityStreamUser;
import com.stackroute.activitystream.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ActivityStreamUser.class)
@ComponentScan(basePackages = { "com.stackroute.activitystream" })
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
public class UserRestControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private UserDAO userDAO;
	
	@InjectMocks
	private UserRestController userRestController;
	@Autowired
	WebApplicationContext context;

	@Before
	public void setup() {
		 MockitoAnnotations.initMocks(this);
	        mockMvc = MockMvcBuilders
	                .standaloneSetup(userRestController).build();

	}
	@Ignore
	@Test
	public void test_get_all_success() throws Exception {
	    List<User> users = Arrays.asList(
	            new User("Raman", "raman", "raman@gmail.com", 8765432123L, true),
	            new User("Deepak", "deepak", "deepak@gmail.com", 9765432123L, true));
	    when(userDAO.getAllUsers()).thenReturn(users);
	    mockMvc.perform(get("/api/user/getAllUsers"))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].username", is("Raman")))
	            .andExpect(jsonPath("$[0].emailId", is("raman@gmail.com")))
	            .andExpect(jsonPath("$[1].username", is("Deepak")))
	            .andExpect(jsonPath("$[1].emailId", is("deepak@gmail.com")));
	    verify(userDAO, times(1)).getAllUsers();
	    verifyNoMoreInteractions(userDAO);
	}
	@Ignore
	@Test
	public void test_create_user_success() throws Exception {
	    User user = new User("himani", "himani", "himani@gmail.com", 7765432123L, true);
	  /*  when(userService.exists(user)).thenReturn(false);
	    doNothing().when(userService).create(user);*/
	    mockMvc.perform(
	            post("/api/user/saveUser").contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isCreated())
	            .andExpect(header().string("location", containsString("/api/user/saveUser")));
	    verify(userDAO, times(1));
	    verify(userDAO, times(1)).saveUser(user);
	    verifyNoMoreInteractions(userDAO);
	}
   @Ignore
	@Test
	public void test_update_user_success() throws Exception {
	    User user =new User("himani", "himani", "himani@gmail.com", 7765432123L, true);
	    when(userDAO.getUserByEmailId(user.getEmailId())).thenReturn(user);
	    mockMvc.perform(
	            post("/api/user/updateUser", user.getEmailId())
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(user.toString()))
	            .andExpect(status().isOk());
	    verify(userDAO, times(1)).getUserByEmailId(user.getEmailId());
	    verify(userDAO, times(1)).updateUser(user);
	    verifyNoMoreInteractions(userDAO);
	}
}
