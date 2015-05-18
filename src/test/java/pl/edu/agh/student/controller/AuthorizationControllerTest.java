package pl.edu.agh.student.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.edu.agh.student.dto.UserDto;
import pl.edu.agh.student.service.UserService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthorizationControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthorizationController authorizationController;

    private org.springframework.test.web.servlet.MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authorizationController).build();
    }

    @Test
    public void testUnauthorized() throws Exception {
        when(userService.getUserByHttpServletRequest(any())).thenReturn(null);

        mockMvc.perform(get("/authorization/user"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthorized() throws Exception {
        UserDto userDto = mock(UserDto.class);

        when(userService.getUserByHttpServletRequest(any())).thenReturn(userDto);

        mockMvc.perform(get("/authorization/user"))
                .andExpect(status().isOk());
    }

}
