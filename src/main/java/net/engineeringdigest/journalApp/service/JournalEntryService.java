package net.engineeringdigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.repo.JournalEntryrepo;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import java.util.List;
import org.bson.types.ObjectId;
import java.util.Optional;


    @Component
    public class JournalEntryService {

    @Autowired
    private JournalEntryrepo journalEntryrepo;

    public void saveEntry(JournalEntry entry){
        journalEntryrepo.save(entry);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryrepo.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryrepo.findById(id); 
    }

    public void deleteById(ObjectId id){
        journalEntryrepo.deleteById(id);
    }
}