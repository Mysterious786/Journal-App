package com.example.Springbootproject.controller;

import com.example.Springbootproject.cache.AppCache;
import com.example.Springbootproject.entity.User;
import com.example.Springbootproject.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    // get all user's list from a database...
    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers()
    {
        List<User> all = userEntryService.getAll();
        if(all!=null && !all.isEmpty())
        {
            return new ResponseEntity<>(all, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    // create one use manually
    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdmin(@RequestBody User user)
    {
        userEntryService.saveAdmin(user);
        return new ResponseEntity<>("Admin and User created successfully",HttpStatus.CREATED);

    }

    @GetMapping("/clear-app-cache")
    public void clearAppCache()
    {
        appCache.init();

    }


}
