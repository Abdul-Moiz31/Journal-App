package net.engineeringdigest.journalApp.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repo.JournalEntryrepo;


    @Component
    public class JournalEntryService {

    @Autowired
    private JournalEntryrepo journalEntryrepo;

    @Autowired
    private UserServices userServices;


    @Transactional
    public void saveEntry(JournalEntry entry , String userName){
        User user = userServices.findByUserName(userName);
        entry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryrepo.save(entry);
        user.getJournal_entries().add(saved);
        // If you want to add a journal entry to a user, use this method:
        // userServices.addJournalEntryToUser(saved, userName);
        // If you want to save the user after adding the entry:
        // userServices.addUser(user);
        // Alternatively, you can use the save method to update the user:
        // userServices.save(user);
        user.setUserName(null);
       userServices.save(user);
    }
    public void saveEntry(JournalEntry journalEntry){
       journalEntryrepo.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryrepo.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryrepo.findById(id); 
    }

    public void deleteById(ObjectId id , String userName) {
        User user = userServices.findByUserName(userName);
        user.getJournal_entries().removeIf(entry -> entry.getId().equals(id));
        userServices.save(user);
        journalEntryrepo.deleteById(id);
    }
}