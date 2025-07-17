package net.engineeringdigest.journalApp.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserServices;

import java.util.Collections;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

  @Autowired
  private JournalEntryService journalEntryService;

  @Autowired
  private UserServices userServices;


  // Get all journal entries for the authenticated user
  @GetMapping
  public ResponseEntity<?> getAllJournalEntriesOfUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    User user = userServices.findByUserName(userName);
    if (user == null) {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
    List<JournalEntry> all = user.getJournal_entries();
    if (all == null || all.isEmpty()) {
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }
    return new ResponseEntity<>(all, HttpStatus.OK);
  }


  // Create a new journal entry for the authenticated user
  @PostMapping
    public ResponseEntity<JournalEntry> createEntryForUser(@RequestBody JournalEntry entry) {
      try{
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        journalEntryService.saveEntry(entry, userName);
        return new ResponseEntity<>(entry, HttpStatus.CREATED);
      }  catch(Exception e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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


  @DeleteMapping("/id/{userName}/{myId}")
  public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId , @PathVariable String userName) {
    journalEntryService.deleteById(myId , userName);
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
      journalEntryService.saveEntry(old, null);
      return new ResponseEntity<>(old, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
