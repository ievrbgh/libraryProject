package ru.ievrb.libraryProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ievrb.libraryProject.models.Book;
import ru.ievrb.libraryProject.models.Person;
import ru.ievrb.libraryProject.services.BookService;
import ru.ievrb.libraryProject.services.PersonService;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String getList(Model model,
                          @RequestParam(name = "count", required = false) Optional<Integer> count,
                          @RequestParam(name = "page", required = false) Optional<Integer> page,
                          @RequestParam(name = "sortBy", required = false) Optional <String> sortBy){

        int booksCount = count.orElse(5);
        int currentPage = page.orElse(0);
        String sortedBy = sortBy.orElse(null);

        Page<Book> booksPage = bookService.findAll(currentPage, booksCount, sortedBy);

        model.addAttribute("pagination", bookService.getPagination(booksPage));
        model.addAttribute("bookList", booksPage);
        model.addAttribute("count", booksCount);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortedBy", sortedBy == null ? "name" : sortedBy);
        return "books/index";
    }

    @GetMapping("/{id}")
    public String view(Model model, @PathVariable("id") int id){
        Book book = bookService.findById(id);
        Person person = null;

        if (book.getPersonStoredDate() != null){
            bookService.checkOverdue(book);
        }

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
    public String checkHolder(Model model, @ModelAttribute Book book){
        Person person = personService.findById(book.getPersonId());
        book = bookService.findById(book.getId());

        bookService.setHolder(book, person);
        bookService.startPersonStoredDate(book);

        return "redirect:/books/"+book.getId();
    }

    @PatchMapping("/resetHolder")
    public String resetHolder(@ModelAttribute("book") Book book){
        book = bookService.findById(book.getId());
        bookService.resetHolder(book);
        return "redirect:/books/"+book.getId();
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(name="searchValue", required = false) String searchValue){
        if(searchValue.equals("")){
            return "redirect:/books";
        }
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("bookList", bookService.search(searchValue));
        return "books/index";
    }
}
