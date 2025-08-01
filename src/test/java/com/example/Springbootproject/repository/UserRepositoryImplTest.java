package com.example.Springbootproject.repository;

import com.example.Springbootproject.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Disabled
@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    public void testCriteria()
    {
       Assertions.assertNotNull(userRepositoryImpl.getUserForSA());


    }
}
