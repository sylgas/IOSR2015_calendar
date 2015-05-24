package pl.edu.agh.student.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.google.gson.*;
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
import pl.edu.agh.student.db.model.Event;
import pl.edu.agh.student.dto.EventDto;
import pl.edu.agh.student.dto.UserDto;
import pl.edu.agh.student.service.EventService;

import java.nio.charset.Charset;
import java.util.Date;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(resolver = TestActiveProfileResolver.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisDevConfig.class, SecurityConfig.class, AppConfig.class})
@WebAppConfiguration
public class EventControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private EventService eventService;

    private MockMvc mockMvc;

    private WebClient webClient;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private Gson gsonSerializer;
    private Gson gsonDeserializer;

    @Before
    public void setup() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>)
                (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));
        gsonDeserializer = builder.create();

        gsonSerializer = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();


        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        webClient = new WebClient();
        webClient.setWebConnection(new MockMvcWebConnection(mockMvc));
    }

    @Test
    public void createGetEventTest() throws Exception {
        // given
        UserDto userDto = new UserDto();
        userDto.setFacebookId("ufid");
        userDto.setFirstName("first");
        userDto.setId("ufid");
        userDto.setLastName("last");
        EventDto eventDto = new EventDto();
        eventDto.setColor("col");
        eventDto.setDescription("desc");
        eventDto.setEndDate(new Date());
        eventDto.setId("id");
        eventDto.setLocation(new Event.Location());
        eventDto.setName("name");
        eventDto.setOwner(userDto);
        eventDto.setStartDate(new Date());

        // when
        MvcResult result =
                mockMvc.perform(post("/event")
                        .content(gsonSerializer.toJson(eventDto))
                        .contentType(contentType))
                        .andExpect(status().isOk())
                        .andReturn();
        EventDto resultEventDto = gsonDeserializer.fromJson(result.getResponse().getContentAsString(), EventDto.class);


        // then
        assertNotNull(resultEventDto);
        assertEquals(eventDto.getColor(), eventDto.getColor());
        assertEquals(eventDto.getDescription(), eventDto.getDescription());
        assertEquals(eventDto.getFacebookId(), eventDto.getFacebookId());
        assertEquals(eventDto.getName(), eventDto.getName());
        assertEquals(eventDto.getLocation(), eventDto.getLocation());
        assertEquals(eventDto.getStartDate(), eventDto.getStartDate());
        assertEquals(eventDto.getEndDate(), eventDto.getEndDate());
        assertNotEquals(0, eventService.getAll().size());
    }
}
