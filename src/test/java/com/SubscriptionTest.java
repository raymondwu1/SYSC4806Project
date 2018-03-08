package com;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class SubscriptionTest {

    private Date date = new Date(1);
    private Perk perk = new Perk("name", "desc", date);
    private Perk perk1 = new Perk("name1", "desdec", date);
    private Perk perk2 = new Perk("name2", "descfvf", date);
    private Subscription sub = new Subscription("subscription", "90");

    @Test
    public void addPerk() throws Exception {
        sub.addPerk(perk);
        assertEquals(perk, sub.getPerks().get(0));
    }

    @Test
    public void removePerk() throws Exception {
        sub.addPerk(perk);
        sub.addPerk(perk1);
        sub.addPerk(perk2);
        sub.removePerk(perk1);
        assertEquals(2, sub.getPerks().size());
        assertEquals(false, sub.getPerks().contains(perk1));
    }

    @Test
    public void getPerks() throws Exception {
        sub.addPerk(perk);
        sub.addPerk(perk1);
        sub.addPerk(perk2);
        ArrayList<Perk> perkArrayList= new ArrayList<Perk>();
        perkArrayList.add(perk);
        perkArrayList.add(perk1);
        perkArrayList.add(perk2);
        assertEquals(perkArrayList, sub.getPerks());
    }

    @Test
    public void setPerks() throws Exception {
        ArrayList<Perk> perkArrayList= new ArrayList<Perk>();
        perkArrayList.add(perk);
        perkArrayList.add(perk1);
        perkArrayList.add(perk2);
        sub.setPerks(perkArrayList);
        assertEquals(3, sub.getPerks().size());
        assertEquals(perkArrayList, sub.getPerks());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("subscription", sub.getName());
    }

    @Test
    public void setName() throws Exception {
        sub.setName("qwe");
        assertEquals("qwe", sub.getName());
    }

    @Test
    public void getFee() throws Exception {
        assertEquals("90", sub.getFee());
    }

    @Test
    public void setFee() throws Exception {
        sub.setFee("100");
        assertEquals("100", sub.getFee());
    }

}