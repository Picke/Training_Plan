package com.picke.web;

import com.picke.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    public void testWelcomePage() throws Exception {
        this.mockMvc.perform(get("/user/home")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.model().attribute("title", "Spring MVC")).
                andExpect(MockMvcResultMatchers.model().attribute("message", "This is welcome page!")).
                andExpect(MockMvcResultMatchers.view().name("home_page"));
    }

    @Test
    public void testInfoPage() throws Exception {
        this.mockMvc.perform(get("/user/info")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.model().attribute("name", "user")).
                andExpect(MockMvcResultMatchers.model().attribute("surname", "user")).
                andExpect(MockMvcResultMatchers.view().name("info_page"));
    }
}
