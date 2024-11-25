package com.example.taskmanager.controller;

import com.example.taskmanager.entity.JournalEntry;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.service.JournalEntryService;
import com.example.taskmanager.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/journal")  //end -point for api hitting
public class JournalEntryController {

@Autowired
private JournalEntryService journalEntryService;

@Autowired
private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user=userService.findByUserName(userName);
        List<JournalEntry> all=user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName){  //POJO Class
       try{
           journalEntryService.saveEntry(myEntry,userName);
           return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
       }catch(Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId){

        Optional<JournalEntry> journalEntry= journalEntryService.findById(myId);
         if(journalEntry.isPresent()){
             return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId,@PathVariable String userName){
        journalEntryService.deleteById(myId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalById
            (@PathVariable ObjectId myId,
             @RequestBody JournalEntry newEntry,
             @PathVariable String userName)
    {
        JournalEntry old= journalEntryService.findById(myId).orElse(null);
        if(old!=null){
            old.setTitle(!newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
