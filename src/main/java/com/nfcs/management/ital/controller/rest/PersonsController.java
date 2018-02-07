package com.nfcs.management.ital.controller.rest;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.nfcs.management.ital.model.Person;
import com.nfcs.management.ital.repos.PersonRepository;

@Controller
@RequestMapping("/persons")
public class PersonsController extends BaseController<Person> {
	Logger LOG = Logger.getLogger(PersonsController.class.getName());
	
	@Autowired
	PersonRepository per;

	public PersonsController() {
		ber = per;
	}
	
	@PostConstruct
	public void postConstruct() {	
		ber = per;
	}
	
}
