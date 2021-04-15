package com.applause.controllers;

import com.applause.config.jsonViews.BugView;
import com.applause.exceptions.BugNotFoundException;
import com.applause.model.Bug;
import com.applause.repositories.BugRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class BugController {

    @Autowired
    BugRepository bugRepository;

    @GetMapping("bugs")
    @JsonView(BugView.class)
    public ResponseEntity<Bug> getAllBugs() {

        return new ResponseEntity(bugRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("bug/{id}")
    @JsonView(BugView.class)
    public ResponseEntity<Bug> getById(@PathVariable("id") Long id) throws BugNotFoundException {

        return new ResponseEntity(bugRepository.findById(id).orElseThrow(() -> new BugNotFoundException(id)), HttpStatus.OK);
    }


}
