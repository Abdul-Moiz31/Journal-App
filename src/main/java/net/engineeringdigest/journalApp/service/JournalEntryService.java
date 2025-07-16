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
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userServices.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryrepo.save(journalEntry);
            user.getJournal_entries().add(saved);
            userServices.saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry.", e);
        }
    }
    public void saveEntry(JournalEntry journalEntry){
       journalEntryrepo.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryrepo.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryrepo.findById(id);
    }

    public void deleteById(ObjectId id , String userName) {
        User user = userServices.findByUserName(userName);
        user.getJournal_entries().removeIf(entry -> entry.getId().equals(id));
        userServices.saveUser(user);
        journalEntryrepo.deleteById(id);
    }

    public List<JournalEntry> findByUserName(String userName) {
        User user = userServices.findByUserName(userName);
        return user.getJournal_entries();
    }
        }   