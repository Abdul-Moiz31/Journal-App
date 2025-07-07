package net.engineeringdigest.journalApp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import net.engineeringdigest.journalApp.service.UserServices;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import net.engineeringdigest.journalApp.service.JournalEntryService;

import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

  @Autowired
  private JournalEntryService journalEntryService;

  @Autowired
  private UserServices userServices;


  // Get all journal entries for a specific user
  @GetMapping("/user/{userName}") // localhost:1533/journal/user/{userName}
  public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
    User user = userServices.findByUserName(userName);
    if (user != null && user.getJournal_entries() != null) {
      return new ResponseEntity<>(user.getJournal_entries(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }


  // Create a new journal entry for a specific user
  @PostMapping("/user/{userName}")
  public ResponseEntity<?> createEntry(@PathVariable String userName, @RequestBody JournalEntry entry) {
    try {
      User user = userServices.findByUserName(userName);
      if (user == null) {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
      }
      entry.setDate(LocalDateTime.now());
      user.getJournal_entries().add(entry);
      userServices.save(user);
      return new ResponseEntity<>(entry, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @GetMapping("/id/{myId}")
  public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
    Optional<JournalEntry> entry = journalEntryService.findById(myId);
    if (entry.isPresent()) {
      return new ResponseEntity<>(entry.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }


  @DeleteMapping("/id/{myId}")
  public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
    journalEntryService.deleteById(myId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


  @PutMapping("/id/{myId}")
  public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
    JournalEntry old = journalEntryService.findById(myId).orElse(null);
    if (old != null) {
      if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
        old.setTitle(newEntry.getTitle());
      }
      if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
        old.setContent(newEntry.getContent());
      }
      journalEntryService.saveEntry(old);
      return new ResponseEntity<>(old, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
