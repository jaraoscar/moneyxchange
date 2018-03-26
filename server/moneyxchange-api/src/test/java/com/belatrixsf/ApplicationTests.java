package com.belatrixsf;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.belatrixsf.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test cases.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	private User loginUser;

    @Before
    public void setup() {
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.loginUser = new User();
    }
    
    /**
     * Test user authentication and token generation.
     * @throws Exception
     */
    @Test
    public void testAuthenticationAndTokenGeneration() throws Exception {
    	this.loginUser.setUsername("oscar.jara");
    	this.loginUser.setPassword("admin");
    	
    	String json = objectMapper.writeValueAsString(this.loginUser);
    	
        this.mockMvc.perform(post("/token/auth")
        	       .contentType(MediaType.APPLICATION_JSON)
        	       .content(json))
        	       .andExpect(status().isOk())
        	       .andExpect(jsonPath("$.token", notNullValue()));
    }
}