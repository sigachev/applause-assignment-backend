package com.applause.controllers;

import com.applause.config.jsonViews.TesterView;
import com.applause.exceptions.TesterNotFoundException;
import com.applause.repositories.TesterRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TesterController {

        @Autowired
        private TesterRepository testerRepository;

        @GetMapping("/testers")
        @JsonView(TesterView.class)
        public ResponseEntity getAllDevices() {
                return new ResponseEntity(testerRepository.findAll(), HttpStatus.OK);
        }

        @GetMapping("/tester/{id}")
        @JsonView(TesterView.class)
        public ResponseEntity getById(@PathVariable("id") Long id) throws TesterNotFoundException {


                return new ResponseEntity(testerRepository.findById(id).orElseThrow(
                        () -> new TesterNotFoundException(id)), HttpStatus.OK
                );

        }

        @GetMapping("/countries")
        public ResponseEntity getAllCountries() {

                SortedSet<String> countries = new TreeSet();
                testerRepository.findAll().forEach(t -> countries.add(t.getCountry()));

                return new ResponseEntity(countries, HttpStatus.OK);
        }


}
