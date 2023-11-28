package ru.ievrb.libraryProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ievrb.libraryProject.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
