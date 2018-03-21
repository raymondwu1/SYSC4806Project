package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByEmail(String email){ return userRepository.findByEmail(email);}

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username){return userRepository.existsByUsername(username);}

    public boolean existsByEmail(String email){return userRepository.existsByEmail(email);}


    public void delete(User user) {userRepository.delete(user);}

    }