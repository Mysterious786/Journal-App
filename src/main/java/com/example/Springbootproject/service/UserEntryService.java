package com.example.Springbootproject.service;


import com.example.Springbootproject.entity.User;
import com.example.Springbootproject.repository.UserEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserEntryService {

    @Autowired
    private UserEntryRepository userRepository;
   // private static final Logger logger = LoggerFactory.getLogger(UserEntryService.class);
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(User user)
    {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepository.save(user);
        } catch (RuntimeException e) {
            log.info("errrorrrr");
            throw new RuntimeException(e);
        }
    }
    public void saveAdmin(User user)
    {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("ADMIN", "USER"));
            userRepository.save(user);
        } catch (RuntimeException e) {
            // or create instance and use logger instead log
            log.info("Hello, error");
            throw new RuntimeException(e);

        }
    }
    public void saveUser(User user)
    {
        userRepository.save(user);
    }

    public List<User> getAll()
    {
        return userRepository.findAll();
    }
    public Optional<User> findById(ObjectId id)
    {
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id)
    {
        userRepository.deleteById(id);
    }
    public User findByUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }
}
