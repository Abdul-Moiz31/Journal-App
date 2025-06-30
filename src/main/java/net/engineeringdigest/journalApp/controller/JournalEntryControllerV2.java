package net.engineeringdigest.journalApp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import java.util.List;
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

  @GetMapping
  public ResponseEntity<?> getAllEntries() { // localhost:8080/journal Get
    List<JournalEntry> entries = journalEntryService.getAllEntries();
    if (entries != null) {
      return new ResponseEntity<>(entries, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping // localhost:8080/ Post
  public ResponseEntity<?> createEntry(@RequestBody JournalEntry entry) {
    try {
      entry.setDate(LocalDateTime.now());
      journalEntryService.saveEntry(entry);
      return new ResponseEntity<>(entry, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("id/{myId}")
  public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
    Optional<JournalEntry> entry = journalEntryService.findById(myId);
    if (entry.isPresent()) {
      return new ResponseEntity<>(entry.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("id/{myId}")
  public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
    journalEntryService.deleteById(myId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("id/{myId}")
  public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
    JournalEntry old = journalEntryService.findById(myId).orElse(null);
    if (old != null) {
      if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) { // if newEntry.gettitle() is not null and not
                                                                           // empty then update the title
        old.setTitle(newEntry.getTitle());
      }
      if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) { // if newEntry.getCOntent() is not null
                                                                               // and not empty then update the content
        old.setContent(newEntry.getContent());
      }
      journalEntryService.saveEntry(old);
      return new ResponseEntity<>(old, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
