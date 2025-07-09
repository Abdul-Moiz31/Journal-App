package net.engineeringdigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import net.engineeringdigest.journalApp.repo.UserRepository;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import java.util.List;

import org.bson.types.ObjectId;
import java.util.Optional;


@Component
@Data
public class UserServices {
    
    @Autowired
    private UserRepository userRepository;

    // If you want to add a journal entry to a user, use this method:
    public void addJournalEntryToUser(JournalEntry entry, String userName) {
        User user = userRepository.findByUserName(userName);
        if (user != null) {
            entry.setDate(java.time.LocalDateTime.now());
            user.getJournal_entries().add(entry);
            userRepository.save(user);
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }


    public void addUser(User user) {
        userRepository.save(user);
    }


    public void save(User user) {
        userRepository.save(user);
    }
    
}




