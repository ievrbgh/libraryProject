package ru.ievrb.libraryProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ievrb.libraryProject.models.Book;
import ru.ievrb.libraryProject.models.Person;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByHolder(Person person);
    @Query("SELECT b FROM Book b WHERE b.name LIKE CONCAT('%',:searchValue,'%')")
    List<Book> findWithPartOfName(@Param("searchValue") String searchValue);

}
