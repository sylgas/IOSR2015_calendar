package pl.edu.agh.student.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebConnection;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.edu.agh.student.config.AppConfig;
import pl.edu.agh.student.config.RedisDevConfig;
import pl.edu.agh.student.config.SecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

@ActiveProfiles(resolver = TestActiveProfileResolver.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisDevConfig.class, SecurityConfig.class, AppConfig.class})
@WebAppConfiguration
public class AuthorizationControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private WebClient webClient;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        webClient = new WebClient();
        webClient.setWebConnection(new MockMvcWebConnection(mockMvc));
    }

    @Test
    public void testUnauthorizedAuthorizationCheck() throws Exception {
        MvcResult result =
                mockMvc.perform(get("/authorization/user"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(0, result.getResponse().getContentLength());
    }

}
