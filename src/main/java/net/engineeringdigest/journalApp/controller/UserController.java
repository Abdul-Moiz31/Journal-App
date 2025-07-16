
package net.engineeringdigest.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserServices;
import org.bson.types.ObjectId;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;
    

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userServices.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userServices.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userServices.deleteById(new ObjectId(authentication.getName()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // @GetMapping
    // public ResponseEntity<?> greeting() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
    //     String greeting = "";
    //     if (weatherResponse != null) {
    //         greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
    //     }
    //     return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
    // }

}
