package com;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataJpaTest
public class PerkRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PerkRepository perkRepository;

    @Test
    public void findByName() throws Exception {
        Perk perk = new Perk("test discount", "get 10% off");
        entityManager.persist(perk);
        entityManager.flush();

        Perk found = perkRepository.findByName(perk.getName());

        assertEquals(found.getName(),perk.getName());
    }

    @Test
    public void existsByName() throws Exception {
        Perk perk = new Perk("test", "get 20% off");
        entityManager.persist(perk);
        entityManager.flush();

        assertTrue(perkRepository.existsByName(perk.getName()));
    }

}