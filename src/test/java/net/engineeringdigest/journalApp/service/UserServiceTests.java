package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import net.engineeringdigest.journalApp.repo.UserRepository;  
import org.springframework.boot.test.context.SpringBootTest;
import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

    


@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test   
    public void testAdd(){
        // assertEquals(4 , 2+2);
        User user = userRepository.findByUserName("Moiz");
        assertTrue(!user.getJournal_entries().isEmpty());
        
    }
    @ParameterizedTest
    @CsvSource({"1,2,3","4,5,9"})
    public void test(int a , int b , int expected){
        assertEquals(expected , a+b);
    }
}
