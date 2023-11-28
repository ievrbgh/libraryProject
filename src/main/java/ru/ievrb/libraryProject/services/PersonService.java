package ru.ievrb.libraryProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ievrb.libraryProject.models.Person;
import ru.ievrb.libraryProject.repositories.PersonRepository;

import java.util.List;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person findById(int id){
        return personRepository.findById(id).orElse(null);
    }

    public void save(Person person){
        personRepository.save(person);
    }

    public void update(int id, Person person){
        person.setId(id);
        personRepository.save(person);
    }

    public void delete(int id){
        personRepository.deleteById(id);
    }
}
