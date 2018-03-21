package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByEmail() throws Exception {
        User user = new User("test","testpass");
        user.setEmail("test@test.com");
        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findByEmail(user.getEmail());

        assertEquals(found.getEmail(), user.getEmail());
    }

    @Test
    public void findByUsername() throws Exception {
        User user = new User("test2","testpass2");
        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findByUsername(user.getUsername());

        assertEquals(found.getUsername(), user.getUsername());
    }

    @Test
    public void existsByUsername() throws Exception {
        User user = new User("test3","testpass3");
        entityManager.persist(user);
        entityManager.flush();

        assertTrue(userRepository.existsByUsername(user.getUsername()));
    }

    @Test
    public void existsByEmail() throws Exception {
        User user = new User("test4","testpass4");
        user.setEmail("test4@test.com");
        entityManager.persist(user);
        entityManager.flush();

        assertTrue(userRepository.existsByEmail(user.getEmail()));
    }

    @Test
    public void delete() throws Exception {
        User user = new User("test4","testpass4");
        entityManager.persist(user);
        entityManager.flush();
        userRepository.delete(user);
        assertFalse(userRepository.existsByUsername(user.getUsername()));
    }


}