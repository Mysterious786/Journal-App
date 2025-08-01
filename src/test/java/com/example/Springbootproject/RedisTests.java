package com.example.Springbootproject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@Disabled
@SpringBootTest
public class RedisTests {

    // serializer and deserializer should be same inorder to store our keys in redis server else it is not going
    // to be stored

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    void testSendMail() {
        redisTemplate.opsForValue().set("email", "saqlainzarjisansari@gmail.com");
        String email = redisTemplate.opsForValue().get("email").toString();
        System.out.println(email); // Should print your email if Redis is configured correctly
    }
}
