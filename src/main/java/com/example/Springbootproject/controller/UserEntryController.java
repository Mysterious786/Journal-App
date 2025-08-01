package com.example.Springbootproject.controller;

import com.example.Springbootproject.api.response.WeatherResponse;
import com.example.Springbootproject.entity.User;
import com.example.Springbootproject.repository.UserEntryRepository;
import com.example.Springbootproject.service.UserEntryService;
import com.example.Springbootproject.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserEntryService userService;

    @Autowired
    private UserEntryRepository userEntryRepository;
    @Autowired
    private WeatherService weatherService;

    // GET all users
    @GetMapping
    public List<User> getAllUser() {
        return userService.getAll();
    }

    // POST create user
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving user", HttpStatus.BAD_REQUEST);
        }
    }

    // PUT update user by username
    // it is authenticated now...

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        // whenever user hits user name and password for authentication it will come here automaticallyy...
        //Security context holder stores the data of authenticated user


        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();


        User userInDB = userService.findByUserName(userName);
        if (userInDB != null) {
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            userService.saveNewUser(userInDB);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        // whenever user hits user name and password for authentication it will come here automaticallyy...
        //Security context holder stores the data of authenticated user


        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        userEntryRepository.deleteByUserName(authentication.getName());



            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);



    }

    @GetMapping("/greetings")
    public ResponseEntity<String> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greetings = "";  // ✅ Default value

        if (weatherResponse != null && weatherResponse.getCurrent() != null) {
            greetings = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }

        return new ResponseEntity<>("Hi " + authentication.getName() + greetings, HttpStatus.OK);
    }

}
