package com.example.kafkaproducer1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.example.kafkaproducer1.UserEventProducer;
import com.example.kafkaproducer1.controller.UserController;
import com.example.kafkaproducer1.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
//@SpringBootTest
//@ExtendWith(SpringExtension.class)

public class UserEventControllerUnitTest {
	
	
	    @Autowired
	    MockMvc mockMvc;

	    ObjectMapper objectMapper = new ObjectMapper();

	    @MockBean
	    UserEventProducer userEventProducer;
	    
	    String exampleUserJson = "{\"name\":\"sai\",\"dept\":\"cse\",\"dept\":25000}";
	    
	    
	    @Test
	    void publishUserEvent() throws Exception {
	    	
	    	User user=objectMapper.readValue(exampleUserJson, User.class);
	    	 doNothing().when(userEventProducer).sendEvent(user);
	    	 
	    	 
	    	 mockMvc.perform( MockMvcRequestBuilders
	    		      .post("/publish")
	    		      .content(exampleUserJson)
	    		     .contentType(MediaType.APPLICATION_JSON)
	    		     .accept(MediaType.APPLICATION_JSON))
	    	          .andExpect(status().isCreated());
	    	
	    	
	    }

}
