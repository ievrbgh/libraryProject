package ru.ievrb.libraryProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ievrb.libraryProject.dao.BookDAO;
import ru.ievrb.libraryProject.dao.PersonDAO;
import ru.ievrb.libraryProject.models.Book;
import ru.ievrb.libraryProject.models.Person;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = {"/persons", "/"})
public class PersonsController {
    private final PersonDAO personDAO;

    private final BookDAO bookDAO;

    @Autowired
    public PersonsController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
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
        List<Book> books = bookDAO.getByHolder(person.getId());
        model.addAttribute("person", person);
        model.addAttribute("books", books);
        return "persons/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.getById(id));
        return "persons/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return "persons/edit";
        }
        personDAO.update(id, person);
        return "redirect:/persons";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/persons";
    }


}
