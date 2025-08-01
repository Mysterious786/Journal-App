package com.example.Springbootproject.controller;

import com.example.Springbootproject.entity.JournalEntry;
import com.example.Springbootproject.entity.User;
import com.example.Springbootproject.service.JournalEntryService;
import com.example.Springbootproject.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*") // Allow frontend/Postman CORS
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserEntryService userService;

    // GET all journal entries of a user
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> journalEntries = user.getJournalEntries();

        if (journalEntries != null && !journalEntries.isEmpty()) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }

        return new ResponseEntity<>("Journal Entries not found", HttpStatus.NOT_FOUND);
    }

    // POST - create a journal entry
    @PostMapping
    public ResponseEntity<String> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            // Optionally set date here if not done in service
            // myEntry.setDate(LocalDateTime.now());
            Authentication authentication = SecurityContextHolder.createEmptyContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>("Entry created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create entry", HttpStatus.BAD_REQUEST);
        }
    }

    // GET - find entry by ID
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.createEmptyContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();
        if(!collect.isEmpty())
        {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if(journalEntry.isPresent())
            {
                return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
            }
        }



        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE - delete entry by ID
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<String> deleteEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.createEmptyContext().getAuthentication();
        String userName = authentication.getName();

           boolean removed = journalEntryService.deleteById(myId, userName);
           if(removed)
            return new ResponseEntity<>("Entry deleted successfully!", HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>("Not able to delete", HttpStatus.NOT_FOUND);

    }

    // PUT - update entry by ID
    @PutMapping("/id/{myId}")
    public ResponseEntity<String> updateEntryById(@PathVariable ObjectId myId,
                                                  @RequestBody JournalEntry newJournalEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        // Check if the journal entry belongs to the user
        boolean hasEntry = user.getJournalEntries().stream()
                .anyMatch(entry -> entry.getId().equals(myId));

        if (!hasEntry) {
            return new ResponseEntity<>("Entry not found for the user", HttpStatus.NOT_FOUND);
        }

        Optional<JournalEntry> optionalEntry = journalEntryService.findById(myId);
        if (optionalEntry.isPresent()) {
            JournalEntry existingEntry = optionalEntry.get();

            if (newJournalEntry.getTitle() != null && !newJournalEntry.getTitle().isEmpty()) {
                existingEntry.setTitle(newJournalEntry.getTitle());
            }

            if (newJournalEntry.getContent() != null && !newJournalEntry.getContent().isEmpty()) {
                existingEntry.setContent(newJournalEntry.getContent());
            }

            journalEntryService.saveEntry(existingEntry, userName);
            return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
        }

        return new ResponseEntity<>("Entry not found", HttpStatus.NOT_FOUND);
    }

}
