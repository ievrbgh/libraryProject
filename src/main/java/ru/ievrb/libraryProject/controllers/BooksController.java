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

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    public String resetHolder(){
        return null;
    }

    public String setHolder(){
        return null;
    }


    @GetMapping("/add")
    public String add(@ModelAttribute("book") Book book){
        return "books/add";
    }

    @PostMapping()
    public String save(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/add";
        }
        bookDAO.save(book);
        return "redirect:books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.getById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @GetMapping()
    public String getList(Model model){
        model.addAttribute("bookList", bookDAO.getList());
        return "books/index";
    }
    @GetMapping("/{id}")
    public String view(Model model, @PathVariable("id") int id){
        Book book = bookDAO.getById(id);
        Person person = null;

        if (book.getPersonId() != null) {
            person = personDAO.getById(book.getPersonId());
        }

        model.addAttribute("book", book);
        model.addAttribute("person", person);
        model.addAttribute("personList", personDAO.getList());
        return "books/view";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/checkHolder")
    public String checkHolder(@ModelAttribute("book") Book book){
        int id = book.getId();
        bookDAO.setHolder(book.getPersonId(), bookDAO.getById(book.getId()));
        return "redirect:/books/"+id;
    }

    @PatchMapping("/resetHolder")
    public String resetHolder(@ModelAttribute("book") Book book){
        int id = book.getId();
        bookDAO.resetHolder(id);
        return "redirect:/books/"+id;
    }
}
