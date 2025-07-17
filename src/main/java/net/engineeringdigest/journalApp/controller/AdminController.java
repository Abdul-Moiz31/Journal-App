package net.engineeringdigest.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserServices;

//this is the admin controller

@RestController
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    private UserServices userServices;


    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {    
        List<User> users = userServices.getAll();
        if(users != null && !users.isEmpty()){

            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/create-admin-user")
    public void createAdminUser(@RequestBody User user) {
        userServices.saveAdminUser(user);
    }
        

}
