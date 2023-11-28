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

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BooksController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
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
        bookService.save(book);
        return "redirect:books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookService.update(id, book);
        return "redirect:/books";
    }

    @GetMapping()
    public String getList(Model model){
        model.addAttribute("bookList", bookService.findAll());
        return "books/index";
    }
    @GetMapping("/{id}")
    public String view(Model model, @PathVariable("id") int id){
        Book book = bookService.findById(id);
        Person person = null;

        if (book.getHolder() != null) {
            person = book.getHolder();
        }

        model.addAttribute("book", book);
        model.addAttribute("person", person);
        model.addAttribute("personList", personService.findAll());
        return "books/view";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/checkHolder")
    public String checkHolder(@ModelAttribute("book") Book book){
        bookService.save(book);
        return "redirect:/books/"+book.getId();
    }

    @PatchMapping("/resetHolder")
    public String resetHolder(@ModelAttribute("book") Book book){
        bookService.resetHolder(book);
        return "redirect:/books/"+book.getId();
    }
}
