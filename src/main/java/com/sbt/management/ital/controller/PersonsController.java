package com.sbt.management.ital.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sbt.management.ital.model.Person;
import com.sbt.management.ital.repos.PersonRepository;

@Controller
@RestController
@RequestMapping("/persons")
public class PersonsController {
	@Autowired
	PersonRepository personRepository;

	// Get one Person
	@GetMapping(value="{id}")
	public ResponseEntity<Optional<Person>> person(@PathVariable(value = "id") Long personId) {
	    Optional<Person> person = personRepository.findById(personId);
	    
	    if(person == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(person);
	}

	// Get all Persons
	@GetMapping()
	public List<Person> getAllPersons(Model model) {
		return personRepository.findAll();
	}

	// New Person
	@PostMapping()
	public Person newPerson(@Validated @RequestBody Person person) {
		return personRepository.save(person);
	}
	
	// Update a Person
	@PutMapping(value="{id}")
	public ResponseEntity<Person> updateNote(@PathVariable(value = "id") Long personId, 
	                                       @Validated @RequestBody Person personDetails) {
	    Person person = personRepository.findById(personId).get();
	    
	    if(person == null) {
	        return ResponseEntity.notFound().build();
	    }
	    person.setFirstName(personDetails.getFirstName());
	    person.setLastName(personDetails.getLastName());

	    Person updatedPerson = personRepository.save(person);
	    return ResponseEntity.ok(updatedPerson);
	}
	
	// Delete a Person
	@DeleteMapping(value="{id}")
	public ResponseEntity<Person> deleteNote(@PathVariable(value = "id") Long personId) {
	    Person person = personRepository.findById(personId).get();
	    if(person == null) {
	        return ResponseEntity.notFound().build();
	    }

	    personRepository.delete(person);
	    return ResponseEntity.ok().build();
	}
}
