package com.example.Springbootproject;

import com.example.Springbootproject.repository.UserEntryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled  // ✅ This disables the whole test class
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserEntryRepository userEntryRepository;

    @Test
    public void testFindByUserName() {
        assertNotNull(userEntryRepository.findByUserName("saqlain123"));
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,2,4",
            "3,1,4"
    })

    public void textCheck(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }

    @Test
    public void testCheckAdd() {
        assertEquals(4, 2 + 2);
    }
}
