package ru.ievrb.libraryProject.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ievrb.libraryProject.models.Person;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PersonDAO.class);

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //BeanPropertyRowMapper<>(Класс) - маппер, позволяющий создать объект на основе полученных данных
    public Person getById(int id){
        logger.info("Call PersonDAO getById method with argument: " + id);
        return jdbcTemplate.query("SELECT FROM person WHERE id = ?", new Object[] {id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public List<Person> getList(){
        logger.info("Call PersonDAO getList method");
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person){
        logger.info("Call PersonDAO save method with arguments: " +
                person.getFirstName()+" ,"+ person.getMiddleName()+" ,"+person.getLastName()+" ,"+person.getBirthYear());
        jdbcTemplate.update("INSERT INTO person (firstName, middleName, lastName, birthYear) VALUES (?,?,?,?)",
                person.getFirstName(), person.getMiddleName(), person.getLastName(), person.getBirthYear());
    }

    public void update(int id, Person person){
        logger.info("Call PersonDAO update method with arguments: " + id + ", " +
                person.getFirstName()+" ,"+ person.getMiddleName()+" ,"+person.getLastName()+" ,"+person.getBirthYear());
        jdbcTemplate.update("UPDATE person SET firstName = ?, middleName = ?, lastName = ?, birthYear = ? WHERE id = ?",
                person.getFirstName(), person.getMiddleName(), person.getLastName(), id);
    }

    public void delete(int id){
        logger.info("Call PersonDAO delete method with argument: "+id);
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }
}
