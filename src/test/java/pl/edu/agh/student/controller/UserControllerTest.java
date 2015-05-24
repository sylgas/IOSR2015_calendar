package pl.edu.agh.student.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import pl.edu.agh.student.config.TestActiveProfileResolver;
import pl.edu.agh.student.db.model.Event;
import pl.edu.agh.student.db.repository.EventRepository;
import pl.edu.agh.student.db.repository.UserRepository;
import pl.edu.agh.student.dto.EventDto;
import pl.edu.agh.student.dto.UserDto;
import pl.edu.agh.student.service.EventService;
import pl.edu.agh.student.service.UserService;

import java.nio.charset.Charset;
import java.util.Date;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(resolver = TestActiveProfileResolver.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisDevConfig.class, SecurityConfig.class, AppConfig.class})
@WebAppConfiguration
public class UserControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    private WebClient webClient;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        userRepository.deleteAll();

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        webClient = new WebClient();
        webClient.setWebConnection(new MockMvcWebConnection(mockMvc));
    }

    @After
    public void teardown() {
        userRepository.deleteAll();
    }

    @Test
    public void createGetUserTest() throws Exception {
        // given
        UserDto userDto = new UserDto()
                .setFacebookId("ufid")
                .setFirstName("first")
                .setId("ufid")
                .setLastName("last");

        // when
        MvcResult result =
                mockMvc.perform(post("/user")
                        .content(mapper.writeValueAsString(userDto))
                        .contentType(contentType))
                        .andExpect(status().isOk())
                        .andReturn();
        UserDto resultUserDto = mapper.readValue(result.getResponse().getContentAsString(), UserDto.class);

        // then
        assertNotNull(resultUserDto);
        assertEquals(mapper.writeValueAsString(userDto), mapper.writeValueAsString(resultUserDto));
        assertEquals(mapper.writeValueAsString(userService.getUserByFacebookId("ufid")),
                mapper.writeValueAsString(resultUserDto));
    }
}
