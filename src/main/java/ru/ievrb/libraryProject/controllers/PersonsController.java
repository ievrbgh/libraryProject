package ru.ievrb.libraryProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ievrb.libraryProject.dao.PersonDAO;

@Controller
@RequestMapping("/persons")
public class PersonsController {
    private final PersonDAO personDAO;

    @Autowired
    public PersonsController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(){
        return "persons/index";
    }
}
