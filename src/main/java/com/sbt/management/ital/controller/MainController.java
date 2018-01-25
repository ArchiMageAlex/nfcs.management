package com.sbt.management.ital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
 
 
@Controller
public class MainController {
    @RequestMapping("/")
    public String main(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "main";
    }
 
    @RequestMapping(value = "/persons")
    public ModelAndView persons() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("persons");
        return modelAndView;
    }
}
