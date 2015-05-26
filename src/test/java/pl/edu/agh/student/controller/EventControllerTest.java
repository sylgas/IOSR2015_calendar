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
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.security.SocialAuthenticationToken;
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
                .setOwner(userDto1.getId())
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
                .setOwner(userDto1.getId())
                .setStartDate(new Date());
        InvitedDto invitedDto = new InvitedDto().setUser(userDto1);
        EventDto eventDto2 = new EventDto()
                .setColor("col")
                .setDescription("desc")
                .setEndDate(new Date())
                .setId("id2")
                .setLocation(new Event.Location())
                .setName("name2")
                .setOwner(userDto2.getId())
                .setInvited(Arrays.asList(invitedDto))
                .setStartDate(new Date());

        SocialUser usr1 = mock(SocialUser.class);
        when(usr1.getUserId()).thenReturn("ufid1");
        Facebook api1 = null;
        Connection conn1 = mock(Connection.class);
        when(conn1.getApi()).thenReturn(api1);
        SocialAuthenticationToken auth1 = mock(SocialAuthenticationToken.class);
        when(auth1.getPrincipal()).thenReturn(usr1);
        when(auth1.getConnection()).thenReturn(conn1);
        SecurityContextImpl ctx1 = mock(SecurityContextImpl.class);
        when(ctx1.getAuthentication()).thenReturn(auth1);
        HttpSession session1 = mock(HttpSession.class);
        when(session1.getAttribute("SPRING_SECURITY_CONTEXT")).thenReturn(ctx1);
        HttpServletRequest req1 = mock(HttpServletRequest.class);
        when(req1.getSession()).thenReturn(session1);

        SocialUser usr2 = mock(SocialUser.class);
        when(usr2.getUserId()).thenReturn("ufid2");
        Authentication auth2 = mock(Authentication.class);
        when(auth2.getPrincipal()).thenReturn(usr2);
        SecurityContextImpl ctx2 = mock(SecurityContextImpl.class);
        when(ctx2.getAuthentication()).thenReturn(auth2);
        HttpSession session2 = mock(HttpSession.class);
        when(session2.getAttribute("SPRING_SECURITY_CONTEXT")).thenReturn(ctx2);
        HttpServletRequest req2 = mock(HttpServletRequest.class);
        when(req2.getSession()).thenReturn(session2);

        eventService.save(req1, eventDto1);
        eventService.save(req2, eventDto2);

        //when
        List<EventDto> ownerList = eventService.getAllOwnedByCurrentUser(req1);

        // then
        assertEquals(1, ownerList.size());
        assertEquals(ownerList.get(0).getId(), eventDto1.getId());

        // when
        List<EventDto> invitedList = eventService.getAllThatInvitedCurrentUser(req1);

        // then
        assertEquals(1, invitedList.size());
        assertEquals(invitedList.get(0).getId(), eventDto2.getId());

        //when
        List<EventDto> allList = eventService.getAllByCurrentUser(req1);

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
                .setOwner(userDto1.getId())
                .setStartDate(new Date());

        SocialUser usr1 = mock(SocialUser.class);
        when(usr1.getUserId()).thenReturn("ufid1");
        Authentication auth1 = mock(Authentication.class);
        when(auth1.getPrincipal()).thenReturn(usr1);
        SecurityContextImpl ctx1 = mock(SecurityContextImpl.class);
        when(ctx1.getAuthentication()).thenReturn(auth1);
        HttpSession session1 = mock(HttpSession.class);
        when(session1.getAttribute("SPRING_SECURITY_CONTEXT")).thenReturn(ctx1);
        HttpServletRequest req1 = mock(HttpServletRequest.class);
        when(req1.getSession()).thenReturn(session1);

        eventService.save(req1, eventDto1);
        assertNotNull(eventService.get("id1"));

        // when
        mockMvc.perform(delete("/event/id1"))
                .andExpect(status().isOk());

        // then
        assertNull(eventService.get("id1"));
    }
}
