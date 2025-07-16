package net.engineeringdigest.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserServices;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/health-check")
    public String healthCheck() {   
        return "OK";
    }

    @PostMapping("/create-user")
   public void createUser(@RequestBody User user) {
        userServices.saveNewUser(user);
    }

}
