package com;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        @Bean
        public UserService userService(){
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp(){
        User user = new User("test", "testpass");
        user.setEmail("test@test.com");

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
    }

    @Test
    public void findByEmail() throws Exception {
        String email = "test@test.com";

        User found = userService.findByEmail(email);
        assertEquals(found.getEmail(),email);
    }

    @Test
    public void findByUsername() throws Exception {
        String username = "test";

        User found = userService.findByUsername(username);

        assertEquals(found.getUsername(), username);
    }

    @Test
    public void existsByUsername() throws Exception {
        assertTrue(userService.existsByEmail("test@test.com"));
    }

    @Test
    public void existsByEmail() throws Exception {
        assertTrue(userService.existsByUsername("test"));
    }

}