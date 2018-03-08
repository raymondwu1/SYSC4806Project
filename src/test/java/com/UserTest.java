package com;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserTest {

    private User user = new User("name", "pass");
    private Subscription sub = new Subscription("subscription", "90");

    @Test
    public void addSubscription() throws Exception {
        user.addSubscription(sub);
        assertEquals(sub, user.getSubscriptions().get(0));
    }

    @Test
    public void removeSubscription() throws Exception {
        user.addSubscription(sub);
        user.removeSubscription(sub);
        assertEquals(0, user.getSubscriptions().size());
        assertEquals(false, sub.getPerks().contains(sub));
    }

    @Test
    public void getId() throws Exception {
        user.setId(0);
        assertEquals(0, user.getId());
    }

    @Test
    public void setId() throws Exception {
        user.setId(544);
        assertEquals(544, user.getId());
    }

    @Test
    public void getEmail() throws Exception {
        user.setEmail("a@cmail.com");
        assertEquals("a@cmail.com", user.getEmail());
    }

    @Test
    public void setEmail() throws Exception {
        user.setEmail("abc@cmail.com");
        assertEquals("abc@cmail.com", user.getEmail());
    }

    @Test
    public void getUsername() throws Exception {
        assertEquals("name", user.getUsername());
    }

    @Test
    public void setUsername() throws Exception {
        user.setUsername("zxc");
        assertEquals("zxc", user.getUsername());
    }

    @Test
    public void getPassword() throws Exception {
        assertEquals("pass", user.getPassword());
    }

    @Test
    public void setPassword() throws Exception {
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void getConfirmPassword() throws Exception {
        user.setConfirmPassword("password1234");
        assertEquals("password1234", user.getConfirmPassword());
    }

    @Test
    public void setConfirmPassword() throws Exception {
        user.setConfirmPassword("password1234");
        assertEquals("password1234", user.getConfirmPassword());
    }

    @Test
    public void getSubscriptions() throws Exception {
        user.addSubscription(sub);
        ArrayList<Subscription> subList = new ArrayList<Subscription>();
        subList.add(sub);
        assertEquals(subList, user.getSubscriptions());
    }

    @Test
    public void setSubscriptions() throws Exception {
    }

}