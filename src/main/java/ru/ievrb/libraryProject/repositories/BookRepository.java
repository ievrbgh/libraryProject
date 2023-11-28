package ru.ievrb.libraryProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ievrb.libraryProject.models.Book;
import ru.ievrb.libraryProject.models.Person;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByHolder(Person person);
}
