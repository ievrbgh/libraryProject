package ru.ievrb.libraryProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ievrb.libraryProject.dao.PersonDAO;
import ru.ievrb.libraryProject.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/persons")
public class PersonsController {
    private final PersonDAO personDAO;

    @Autowired
    public PersonsController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("personList", personDAO.getList());
        return "persons/index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("person") Person person){
        //Передается пустой объект person
        return "persons/add";
    }

    @PostMapping()
    public String save(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "persons/add";
        }
        personDAO.save(person);
        return "redirect:persons";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model){
        Person person = personDAO.getById(id);
        model.addAttribute("person", person);
        return "persons/view";
    }


}
