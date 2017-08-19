package com.stackroute.activitystream;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.hamcrest.core.Is.is;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.activitystream.controller.UserMessageRestController;
import com.stackroute.activitystream.dao.MessageDAO;
import com.stackroute.activitystream.dao.UserMessageDAO;
import com.stackroute.activitystream.message.ActivityStreamMessage;
import com.stackroute.activitystream.model.Message;
import com.stackroute.activitystream.model.UserMessage;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT,classes=ActivityStreamMessage.class)
@ComponentScan(basePackages={"com.stackroute.activitystream"})
@AutoConfigureMockMvc
public class MessageMockitoTest {
   
	private MockMvc mockMvc;

    @Mock
    private MessageDAO messageDAO;
    
    @Mock
    private UserMessageDAO userMessageDAO;

    @InjectMocks
    private UserMessageRestController userMessageRestController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userMessageRestController)
                .build();
    }

    // =========================================== Get All Messages ==========================================
 
    @Test
    public void test_get_all_messages_success() throws Exception {
        List<Message> messages = Arrays.asList(
               new Message(1, "This is Good", "ravi@gmail.com", new Date(), 230, 10000, "Text", "jagan@gmail.com", "general"),
               new Message(2, "Hello dear", "krishna@gmail.com", new Date(), 521, 10000, "Text", "ravi@gmail.com", "general")
        		);

        when(messageDAO.getAllMessages("ram@gmail.com")).thenReturn(messages);

        mockMvc.perform(post("/api/message/getAllMessages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].messageSize", is(230)))
                .andExpect(jsonPath("$[0].messageText", is("This is Good")))
                .andExpect(jsonPath("$[1].messageSize", is(521)))
                .andExpect(jsonPath("$[1].messageText", is("Hello dear")));

        verify(messageDAO, times(1)).getAllMessages("ram@gmail.com");
        verifyNoMoreInteractions(messageDAO);
    }

    // =========================================== Get Message By ID =========================================
   @Ignore
    @Test
    public void test_get_by_messageId_success() throws Exception {
        UserMessage message =  new UserMessage(1, "This is Good", "ravi@gmail.com", new Date(), 230, 10000, "Text", "jagan@gmail.com", "general");

        when(userMessageDAO.getMessageByMessageId(message.getMessageId())).thenReturn(message);

        mockMvc.perform(get("/api/message/messageById/{messageId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.messageId", is(1)))
                .andExpect(jsonPath("$.messageText", is("This is Good")));

        verify(userMessageDAO, times(1)).getMessageByMessageId(1);
        verifyNoMoreInteractions(userMessageDAO);
    }
    @Ignore
    @Test
    public void test_get_by_messageId_fail_404_not_found() throws Exception {

        when(userMessageDAO.getMessageByMessageId(1)).thenReturn(null);

        mockMvc.perform(get("/api/message/messageById/{messageId}", 1))
                .andExpect(status().isNotFound());

        verify(userMessageDAO, times(1)).getMessageByMessageId(1);
        verifyNoMoreInteractions(userMessageDAO);
    }

    // =========================================== Create New Message ========================================
    @Ignore
    @Test
    public void test_create_message_success() throws Exception {
       Message message= new Message(1, "This is Good", "ravi@gmail.com", new Date(), 230, 10000, "Text", "jagan@gmail.com", "general");

        mockMvc.perform(
                post("/api/message/sendMessage")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(asJsonString(message)))
                .andExpect(status().isOk())
                .andExpect(header().string("location", containsString("http://localhost:8888/activityStream/api/message/sendMessage")));
    }
   @Ignore
    @Test
    public void test_create_message_fail_409_conflict() throws Exception {
    	 Message message= new Message(1, "This is Good", "ravi@gmail.com", new Date(), 230, 10000, "Text", "jagan@gmail.com", "general");
       mockMvc.perform(
                post("api/message/sendMessage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(message)))
                .andExpect(status().isNotFound());

        verifyNoMoreInteractions(messageDAO);
    }
   
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
}
