package ru.ievrb.libraryProject.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ievrb.libraryProject.models.Book;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PersonDAO.class);

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getByHolder(int id) {
        logger.info("Call BookDAO getByHolder method with argument: " + id);
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getById(int id){
        logger.info("Call BookDAO getById method with argument: " + id);
        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public List<Book> getList(){
        logger.info("Call BookDAO getList method");
        return jdbcTemplate.query("SELECT * FROM Book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book){
        logger.info("Call BookDAO save method with arguments: " +
                book.getPersonId()+" ,"+ book.getName()+" ,"+book.getAuthor()+" ,"+book.getYear());

        jdbcTemplate.update("INSERT INTO Book ( name, author, year) VALUES (?,?,?)",
                 book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book book){
        logger.info("Call BookDAO update method with arguments: " + id + ", " +
                book.getPersonId()+" ,"+ book.getName()+" ,"+book.getAuthor()+" ,"+book.getYear());
        jdbcTemplate.update("UPDATE Book SET  name = ?, author = ?, year = ? WHERE id = ?",
                 book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id){
        logger.info("Call BookDAO delete method with argument: "+id);
        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }

    public void setHolder(int id, Book book){
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?", id, book.getId());
    }

    public void resetHolder(int id){
        jdbcTemplate.update("UPDATE Book SET person_id = null WHERE id = ?", id);
    }
}
