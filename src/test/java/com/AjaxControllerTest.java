package com;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
* Test the AjaxController class.
*
* */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AjaxControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private PerkService perkService;
    @Autowired
    MockMvc mvc;


    private User user;
    private Perk perk;
    private Subscription sub, sub2;

    private String username = "Test";
    private String pass = "pass";
    private String namePerk = "half off test";
    private String description = "This is a test";
    private String nameSub = "testVisa";
    private String nameSub2 = "passVisa";
    private String fee = "once a month";
    private java.util.Date expiryDate =  new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();

    /* Setup objects. */
    @Before
    public void init()
    {
        user = new User(username,pass);
        perk = new Perk(namePerk, description);
        perk.setExpiryDate(expiryDate);
        sub = new Subscription(nameSub,fee);
        sub2 = new Subscription(nameSub2,fee);
    }

    /* Clear objects and remove user from repository. */
    @After
    public void tearDown()
    {
        sub.getPerks().clear();
        user.getSubscriptions().clear();
        User temp = userService.findByUsername(user.getUsername());
        if(temp != null) {
            userService.delete(temp);
        }
    }

    /* Test GetTable method */
    @Test
    public void testGetTable() throws Exception {
        /* Add perk and subscription to user. */
        sub.addPerk(perk);
        user.addSubscription(sub);
        userService.save(user);
        /* Make call. */
        MvcResult result = mvc.perform(get("/GetTable").param("userName", user.getUsername())).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        /* Verify the table returns the sub and perk name. */
        assert(content.contains(sub.getName()));
        assert(content.contains(perk.getCode()));
        assert(content.contains(new SimpleDateFormat("yyyy-MM-dd").format(expiryDate)));
    }

    @Test
    public void testHomeTableInitial() throws Exception{
        /* Add perk and subscription to user. */
        sub2.addPerk(perk);
        user.addSubscription(sub2);
        userService.save(user);
        /* Make call. */
        MvcResult result = mvc.perform(get("/GeneralPopulation")).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        /* Verify the table returns the sub and perk name. */
        assert(content.contains(sub2.getName()));
        assert(content.contains(perk.getCode()));
        assert(content.contains(new SimpleDateFormat("yyyy-MM-dd").format(expiryDate)));
        assert (!content.contains("class=\"upvotebutton"));
        assert (!content.contains("class=\"downvotebutton"));


    }

    /* Test AddSubscription method */
    @Test
    public void testAddSubscription() throws Exception {
        userService.save(user);
        /* Make call. */
        mvc.perform(post("/AddSubscription").param("userName", user.getUsername()).contentType(MediaType.APPLICATION_JSON_UTF8).content(asJsonString(sub))).andExpect(status().isOk());
        /* Verify subscription was added in User. */
        List<Subscription> temp = userService.findByUsername(user.getUsername()).getSubscriptions();
        assert(temp.size() == 1);
        assert(temp.get(0).getName().equals(sub.getName()));
    }

    /* Test AddPerk method */
    @Test
    public void testAddPerk() throws Exception {
        /* Add subscription. */
        user.addSubscription(sub);
        userService.save(user);
        /* Make call. */
        mvc.perform(post("/AddPerk").param("userName", user.getUsername()).param("subName", sub.getName()).contentType(MediaType.APPLICATION_JSON_UTF8).content(asJsonString(perk))).andExpect(status().isOk());
        List<Subscription> tempSubs = userService.findByUsername(user.getUsername()).getSubscriptions();
        /* Verify subscription is in the UserService. */
        assert(tempSubs.size() == 1);
        assert(tempSubs.get(0).getName().equals(sub.getName()));

        /* Verify perk was added. */
        List<Perk> tempPerks = tempSubs.get(0).getPerks();
        assert(tempPerks.size() == 1);
        assert(tempPerks.get(0).getCode().equals(perk.getCode()));
    }

    /* Test AddPerk method */
    @Test
    public void testGetSubs() throws Exception {
        sub.addPerk(perk);
        user.addSubscription(sub);
        userService.save(user);
        MvcResult result = mvc.perform(get("/GetSubs").param("userName", user.getUsername())).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        /* Verify the table returns the sub and perk name. */
        System.out.print(content);
        assert(content.contains(sub.getName()));
    }

    /* Test DeleteSub method */
    @Test
    public void testDeleteSub() throws Exception {
        user.addSubscription(sub);
        userService.save(user);
        MvcResult result = mvc.perform(delete("/DeleteSub").param("userName", user.getUsername()).param("subName", sub.getName())).andExpect(status().isOk()).andReturn();
        User temp = userService.findByUsername(user.getUsername());
        /* Verify the table returns the sub and perk name. */
        assert(!temp.getSubscriptions().contains(sub));
    }

    /* Helper method that converts an object to json. */
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}