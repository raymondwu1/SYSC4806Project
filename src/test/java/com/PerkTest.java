package com;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class PerkTest {

    Date date = new Date(1);
    Date date1 = new Date(5);

    private Perk perk = new Perk("name", "desc", date);
    private Perk perk1 = new Perk("name1", "desc1", date1);

    @Test
    public void getId() throws Exception {
        perk.setId(23);
        assertEquals(23, perk.getId());
    }

    @Test
    public void setId() throws Exception {
        perk1.setId(53);
        assertEquals(53, perk1.getId());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("name", perk.getName());
        assertEquals("name1", perk1.getName());
    }

    @Test
    public void setName() throws Exception {
        perk.setName("asdf");
        assertEquals("asdf", perk.getName());
    }

    @Test
    public void getDescription() throws Exception {
        assertEquals("desc", perk.getDescription());
        assertEquals("desc1", perk1.getDescription());
    }

    @Test
    public void setDescription() throws Exception {
        perk.setDescription("qw");
        assertEquals("qw", perk.getDescription());
    }

    @Test
    public void getExpiryDate() throws Exception {
        Date date2 = new Date(1);
        assertEquals(date2, perk.getExpiryDate());

    }

    @Test
    public void setExpiryDate() throws Exception {
        perk.setExpiryDate(date1);
        assertEquals(date1, perk.getExpiryDate());
    }

}