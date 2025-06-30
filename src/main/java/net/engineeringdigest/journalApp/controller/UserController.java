
package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    // Define methods for user operations like create, read, update, delete
    // Example: Create a new user
    // @PostMapping
    // public ResponseEntity<?> createUser(@RequestBody User user) {
    //     userServices.saveUser(user);
    
   @GetMapping
    public List<User> getAllUsers() {
        return userServices.getAllUsers();
    }

    // Example: Get user by ID
    // @GetMapping("/{id}")
    // public ResponseEntity<?> getUserById(@PathVariable ObjectId id) {
    //     Optional<User> user = userServices.findById(id);
    //

    @PostMapping 
    public void createUser(@RequestBody User user) {
        userServices.saveUser(user);
    }


    @PutMapping
    public void updateUser(@RequestBody User user) {
        userServices.saveUser(user);
    }
}
