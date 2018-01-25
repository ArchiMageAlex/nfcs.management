package com.sbt.management.ital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sbt.management.ital.model.Person;
import com.sbt.management.ital.repos.PersonRepository;

@Controller
public class MainController {
	@Autowired
	PersonRepository personRepository;

	@RequestMapping("/")
	public String main(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "main";
	}

	@RequestMapping("/person/{id}")
	public String product(@PathVariable Long id, Model model) {
		model.addAttribute("product", personRepository.findById(id));
		return "product";
	}

	@RequestMapping(value = "/persons", method = RequestMethod.GET)
	public String productsList(Model model) {
		model.addAttribute("persons", personRepository.findAll());
		return "persons";
	}

	@RequestMapping(value = "/saveperson", method = RequestMethod.POST)
	@ResponseBody
	public String savePerson(@RequestBody Person person) {
		personRepository.save(person);
		return person.getId().toString();
	}

}
