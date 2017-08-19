package com.stackroute.activitystream.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.activitystream.controller.CircleRestController;
import com.stackroute.activitystream.dao.CircleDAO;
import com.stackroute.activitystream.main.ActivityStreamUserCircle;
import com.stackroute.activitystream.model.Circle;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ActivityStreamUserCircle.class)
public class CircleMockitoTest {


	private MockMvc mockMvc;

	@Mock
	private CircleDAO circleDAO;
    @Autowired
	@InjectMocks
	private CircleRestController circleRestController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(circleRestController).build();

	}

	@Test
	public void test_get_all_circles_success() throws Exception {
		List<Circle> circles = Arrays.asList(new Circle("niit", "pankaj@gmail.com", true, new Date()),
				new Circle("inventors", "india@gmail.com", true, new Date()));
		when(circleDAO.getAllCircles()).thenReturn(circles);
		mockMvc.perform(get("/api/circles/get-all-circles")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].circleName", is("niit")))
				.andExpect(jsonPath("$[0].ownerEmailId", is("pankaj@gmail.com")))
				.andExpect(jsonPath("$[1].circleName", is("inventors")))
				.andExpect(jsonPath("$[1].ownerEmailId", is("india@gmail.com")));
		verify(circleDAO, times(1)).getAllCircles();
		verifyNoMoreInteractions(circleDAO);
	}
    @Ignore
	@Test
	public void test_create_circle_success() throws Exception {
		Circle circle = new Circle("niit", "pankaj@gmail.com", true, new Date());
		when(circleDAO.isCircleExists("niit")).thenReturn(false);
		mockMvc.perform(post("/api/circles/create-circle").contentType("application/json")
				.content(asJsonString(circle))).andExpect(status().isCreated())
				.andExpect(header().string("location", containsString("http://localhost:8888/activityStream/api/circles/create-circle")));
		verify(circleDAO, times(1)).isCircleExists("niit");
		verify(circleDAO, times(1)).addCircle(circle);
		verifyNoMoreInteractions(circleDAO);
	}

	private static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
