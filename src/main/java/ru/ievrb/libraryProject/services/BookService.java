package ru.ievrb.libraryProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ievrb.libraryProject.models.Book;
import ru.ievrb.libraryProject.models.Person;
import ru.ievrb.libraryProject.repositories.BookRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public void setHolder(Book book, Person person) {
        book.setHolder(person);
        this.save(book);
    }

    public void resetHolder(Book book) {
        book.setHolder(null);
        this.save(book);
    }

    public List<Book> getByHolder(Person person) {
        List<Book> books = bookRepository.findByHolder(person);
        books.stream().forEach(e->this.checkOverdue(e));
        return books;
    }

    public Page<Book> findAll(int page, int count, String sort){

        return sort == null ? bookRepository.findAll(PageRequest.of(page, count)) :
                                bookRepository.findAll(PageRequest.of(page, count, Sort.by(sort))) ;
    }

    public List<Integer> getPagination(Page bookPage){
        if(bookPage.getTotalPages() > 1) {
            return IntStream.rangeClosed(0, bookPage.getTotalPages()-1).boxed().collect(Collectors.toList());
        }
        return null;
    }

    public void startPersonStoredDate(Book book) {
        book.setPersonStoredDate(new Date());
        this.save(book);
    }

    public void checkOverdue(Book book){
        Date personStored = book.getPersonStoredDate();
        Date now = new Date();
        long elapsedms = now.getTime() - personStored.getTime();

        book.setOverdue(TimeUnit.DAYS.convert(elapsedms, TimeUnit.MILLISECONDS) > 10);

    }

    public List<Book> search(String value){
        return bookRepository.findWithPartOfName(value);
    }
}
