package com.example.Springbootproject;

import com.example.Springbootproject.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {
    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail()
    {
        emailService.sendEmail("saqlainzarjisansari@gmail.com","Email send properly","Hey Saqlain How are you!");
    }


}
