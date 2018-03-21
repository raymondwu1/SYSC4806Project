package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.hasProperty;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserValidator userValidator;

    @Test
    public void homeShouldReturnHome() throws Exception{
        this.mvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    @Test
    public void registrationPageShouldHaveModel() throws Exception{
        this.mvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userForm"));
    }

    @Test
    public void successfulRegistrationTest() throws Exception{
        this.mvc.perform(post("/registration")
                .param("username","test")
                .param("password","testPass")
                .param("confirmPassword", "testPass"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"));
    }

    @Test
    public void failedRegistrationTest() throws Exception{
        this.mvc.perform(post("/registration")
                .param("username","test")
                .param("password","testPass")
                .param("confirmPassword", "wrongPass"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }


    @Test
    public void loginShouldHaveModel() throws Exception{

        this.mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userForm"));
    }

    @Test
    public void testSuccessfulLogin() throws Exception{

        User user = new User("test", "testpass");
        given(userService.existsByUsername("test")).willReturn(true);
        given(userService.findByUsername("test")).willReturn(user);


        this.mvc.perform(post("/login")
                .param("username", "test")
                .param("password", "testpass"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"));
    }

    @Test
    public void testFailedLogin() throws Exception{

        this.mvc.perform(post("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}