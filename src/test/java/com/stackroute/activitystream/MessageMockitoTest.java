/*package com.stackroute.activitystream;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.stackroute.activitystream.message.ActivityStreamMessage;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT,classes=ActivityStreamMessage.class)
@ComponentScan(basePackages={"com.stackroute.activitystream"})
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
public class MessageMockitoTest {
   
	private MockMvc mockMvc;
	@Autowired
	WebApplicationContext context;
	
		@Before
		public void setup() {
	        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		}
		@Test
		public void verifyAllToDoList() throws Exception {
			mockMvc.perform(get("/api/message/messageById/1")).andExpect(status().isOk());
		}
		@Test
		public void getMyMessage() throws Exception
		{
			 mockMvc.perform(get("/api/message/messageById/1"))
             .andExpect(status().isOk())
             .andExpect(content().string("{\"errorCode\":\"null\",\"errorMessage\":\"null\",\"messageId\":1,\"circleName\":\"general\",\"messageSize\":234}"));
		}

	
}
*/