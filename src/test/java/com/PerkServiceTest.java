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
public class PerkServiceTest {

    @TestConfiguration
    static class PerkServiceTestContextConfiguration{
        @Bean
        public PerkService perkService(){
            return new PerkService();
        }
    }

    @Autowired
    private PerkService perkService;

    @MockBean
    private PerkRepository perkRepository;

    @Before
    public void setUp() throws Exception {
        Perk perk = new Perk("testPerk", "test desc");
        Mockito.when(perkRepository.findByName(perk.getName())).thenReturn(perk);
        Mockito.when(perkRepository.existsByName(perk.getName())).thenReturn(true);
    }

    @Test
    public void findByName() throws Exception {
        Perk found = perkService.findByName("testPerk");
        assertEquals(found.getName(), "testPerk");
    }

    @Test
    public void existsByName() throws Exception {
        assertTrue(perkService.existsByName("testPerk"));
    }

}