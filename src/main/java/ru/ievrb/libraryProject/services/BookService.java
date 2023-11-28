package ru.ievrb.libraryProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ievrb.libraryProject.models.Book;
import ru.ievrb.libraryProject.models.Person;
import ru.ievrb.libraryProject.repositories.BookRepository;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findById(int id){
        return bookRepository.findById(id).orElse(null);
    }

    public void save(Book book){
        bookRepository.save(book);
    }

    public void update(int id, Book book){
        book.setId(id);
        bookRepository.save(book);
    }

    public void delete(int id){
        bookRepository.deleteById(id);
    }

    public void setHolder(Person person, Book book) {
        book.setHolder(person);
        this.save(book);
    }

    public void resetHolder(Book book) {
        book.setHolder(null);
        this.save(book);
    }

    public List<Book> getByHolder(Person person) {
        return bookRepository.findByHolder(person);
    }

    public Page<Book> findAll(int page, int count){
        return bookRepository.findAll(PageRequest.of(page, count));
    }
}
