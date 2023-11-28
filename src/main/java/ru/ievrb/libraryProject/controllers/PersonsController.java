package ru.ievrb.libraryProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ievrb.libraryProject.models.Book;
import ru.ievrb.libraryProject.models.Person;
import ru.ievrb.libraryProject.services.BookService;
import ru.ievrb.libraryProject.services.PersonService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = {"/persons", "/"})
public class PersonsController {
    private final PersonService personService;
    private final BookService bookService;

    @Autowired
    public PersonsController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("personList", personService.findAll());
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
        personService.save(person);
        return "redirect:persons";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model){
        Person person = personService.findById(id);
        List<Book> books = bookService.getByHolder(person);
        model.addAttribute("person", person);
        model.addAttribute("books", books);
        return "persons/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.findById(id));
        return "persons/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return "persons/edit";
        }
        personService.update(id, person);
        return "redirect:/persons";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personService.delete(id);
        return "redirect:/persons";
    }


}
