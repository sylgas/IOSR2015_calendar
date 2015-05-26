package pl.edu.agh.student.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.social.security.SocialUser;
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
import pl.edu.agh.student.dto.InvitedDto;
import pl.edu.agh.student.dto.UserDto;
import pl.edu.agh.student.service.EventService;
import pl.edu.agh.student.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Autowired
    private EventRepository eventRepository;

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

    private UserDto userDto1;
    private UserDto userDto2;

    @Before
    public void setup() {
        eventRepository.deleteAll();
        userRepository.deleteAll();

        userDto1 = new UserDto()
                .setFacebookId("ufid1")
                .setFirstName("first1")
                .setId("ufid1")
                .setLastName("last1");
        userService.save(userDto1);
        userDto2 = new UserDto()
                .setFacebookId("ufid2")
                .setFirstName("first2")
                .setId("ufid2")
                .setLastName("last2");
        userService.save(userDto2);

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        webClient = new WebClient();
        webClient.setWebConnection(new MockMvcWebConnection(mockMvc));
    }

    @After
    public void teardown() {
        eventRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createGetEventTest() throws Exception {
        // given
        EventDto eventDto = new EventDto()
                .setColor("col")
                .setDescription("desc")
                .setEndDate(new Date())
                .setId("id")
                .setLocation(new Event.Location())
                .setName("name")
                .setOwner(userDto.getId())
                .setStartDate(new Date());

        // when
        MvcResult result =
                mockMvc.perform(post("/event")
                        .content(mapper.writeValueAsString(eventDto))
                        .contentType(contentType))
                        .andExpect(status().isOk())
                        .andReturn();
        EventDto resultEventDto = mapper.readValue(result.getResponse().getContentAsString(), EventDto.class);

        // then
        assertNotNull(resultEventDto);
        assertEquals(mapper.writeValueAsString(eventDto), mapper.writeValueAsString(resultEventDto));
        assertEquals(mapper.writeValueAsString(eventService.get("id")), mapper.writeValueAsString(resultEventDto));
    }

    @Test
    public void getByInvited() throws Exception {
        // given
        EventDto eventDto1 = new EventDto()
                .setColor("col")
                .setDescription("desc")
                .setEndDate(new Date())
                .setId("id1")
                .setLocation(new Event.Location())
                .setName("name1")
                .setOwner(userDto1)
                .setStartDate(new Date());
        InvitedDto invitedDto = new InvitedDto().setUser(userDto1);
        EventDto eventDto2 = new EventDto()
                .setColor("col")
                .setDescription("desc")
                .setEndDate(new Date())
                .setId("id2")
                .setLocation(new Event.Location())
                .setName("name2")
                .setOwner(userDto2)
                .setInvited(Arrays.asList(invitedDto))
                .setStartDate(new Date());
        eventService.save(eventDto1);
        eventService.save(eventDto2);

        SocialUser usr = mock(SocialUser.class);
        when(usr.getUserId()).thenReturn("ufid1");

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(usr);

        SecurityContextImpl ctx = mock(SecurityContextImpl.class);
        when(ctx.getAuthentication()).thenReturn(auth);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("SPRING_SECURITY_CONTEXT")).thenReturn(ctx);

        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getSession()).thenReturn(session);

        //when
        List<EventDto> ownerList = eventService.getAllOwnedByCurrentUser(req);

        // then
        assertEquals(1, ownerList.size());
        assertEquals(ownerList.get(0).getId(), eventDto1.getId());

        // when
        List<EventDto> invitedList = eventService.getAllThatInvitedCurrentUser(req);

        // then
        assertEquals(1, invitedList.size());
        assertEquals(invitedList.get(0).getId(), eventDto2.getId());

        //when
        List<EventDto> allList = eventService.getAllByCurrentUser(req);

        // then
        assertEquals(2, allList.size());
        assertEquals(allList.get(0).getId(), eventDto1.getId());
        assertEquals(allList.get(1).getId(), eventDto2.getId());
    }

    @Test
    public void deleteEvent() throws Exception {
        // given
        EventDto eventDto1 = new EventDto()
                .setColor("col")
                .setDescription("desc")
                .setEndDate(new Date())
                .setId("id1")
                .setLocation(new Event.Location())
                .setName("name1")
                .setOwner(userDto1)
                .setStartDate(new Date());
        eventService.save(eventDto1);
        assertNotNull(eventService.getById("id1"));

        // when
        mockMvc.perform(delete("/event/id1"))
                .andExpect(status().isOk());

        // then
        assertNull(eventService.getById("id1"));
    }
}
