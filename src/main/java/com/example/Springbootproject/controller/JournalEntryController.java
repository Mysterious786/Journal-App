package com.example.Springbootproject.controller;


import com.example.Springbootproject.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap();

    @GetMapping
    public List<JournalEntry> getAll()
    {
        return new ArrayList<>(journalEntries.values());

    }
    @PostMapping
    public String createEntry(@RequestBody JournalEntry myEntry)
    {
        //journalEntries.put(myEntry.getId(),myEntry);
        return "Added successfully";
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable long myId)
    {
        return journalEntries.get(myId);
    }

    @DeleteMapping("id/{myId}")
    public String deleteEntryById(@PathVariable long myId)
    {
        journalEntries.remove(myId);
        return "Entry deleted Successfully!";
    }

    @PutMapping("id/{myId}")
    public String updateEntryById(@PathVariable long myId, @RequestBody JournalEntry newJournalEntry)
    {
        journalEntries.put(myId,newJournalEntry);
        return "Entry updated Successfully!";
    }

}
