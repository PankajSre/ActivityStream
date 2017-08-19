package com.stackroute.activitystream.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.activitystream.controller.UserCircleRestController;
import com.stackroute.activitystream.dao.UserCircleDAO;
import com.stackroute.activitystream.main.ActivityStreamUserCircle;
import com.stackroute.activitystream.model.UserCircle;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ActivityStreamUserCircle.class)
@ComponentScan(basePackages = { "com.stackroute.activitystream" })
@AutoConfigureMockMvc
public class UserCircleMockMVC {

	private MockMvc mockMvc;

	@Mock
	private UserCircleDAO userCircleDAO;

	@InjectMocks
	private UserCircleRestController userCircleRestController;

	@Autowired
	WebApplicationContext context;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userCircleRestController).build();

	}

	@Ignore
	@Test
	public void test_get_all_success() throws Exception {
		List<UserCircle> userCircles = Arrays.asList(new UserCircle(1, "DevOps", "ram@yahoo.com", true, new Date()),
				new UserCircle(1, "MeanStack", "india@yahoo.com", true, new Date()));
		when(userCircleDAO.getAllCircles()).thenReturn(userCircles);
		mockMvc.perform(get("/api/circle/getAllCircles")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].circleName", is("DevOps")))
				.andExpect(jsonPath("$[0].subscriberId", is("ram@yahoo.com")))
				.andExpect(jsonPath("$[1].circleName", is("MeanStack")))
				.andExpect(jsonPath("$[1].subscriberId", is("india@yahoo.com")));
		verify(userCircleDAO, times(1)).getAllCircles();
		verifyNoMoreInteractions(userCircleDAO);
	}

	@Ignore
	@Test
	public void test_create_circle_success() throws Exception {
		UserCircle userCircle = new UserCircle(1, "DevOps", "ram@yahoo.com", true, new Date());
		mockMvc.perform(post("/api/circle/create-user-circle").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(userCircle))).andExpect(status().isCreated())
				.andExpect(header().string("location",
						containsString("http://localhost:8888/activityStream/api/circle/create-user-circle")));
	}
//---------------------------------Get Circle By Name-------------------------------

	public void test_get_by_circleName_success() throws Exception {
		UserCircle userCircle = new UserCircle(1, "DevOps", "ram@yahoo.com", true, new Date());

		when(userCircleDAO.getCircleByName("DevOps")).thenReturn(userCircle);

		mockMvc.perform(get("/api/circle/circle-by-name/" + "DevOps", "DevOps")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.circleName", is("DevOps")))
				.andExpect(jsonPath("$.subscriberId", is("ram@yahoo.com")));

		verify(userCircleDAO, times(1)).getCircleByName("DevOps");
		verifyNoMoreInteractions(userCircleDAO);
	}

	@Test
	public void test_get_by_circleName_fail_404_not_found() throws Exception {

		when(userCircleDAO.getCircleByName("general1")).thenReturn(null);

		mockMvc.perform(get("/api/circle/circle-by-name/general1")).andExpect(status().isOk());

		verify(userCircleDAO, times(1)).getCircleByName("general1");
		verifyNoMoreInteractions(userCircleDAO);
	}
//---------------------------------Update Circle----------------------------------------------
   
  
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
