package org.spring.project1.dao;

import org.spring.project1.models.Book;
import org.spring.project1.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public Person show(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE person_id = ?", new PersonMapper(), id);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, birth_year) VALUES (?, ?)",
                person.getName(), person.getBirth_year());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, birth_year=? WHERE person_id=?",
                updatedPerson.getName(), updatedPerson.getBirth_year(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }

    public Optional<Person> find(String name) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE name = ?", new Object[]{name}, new PersonMapper()).stream().findAny();
    }

    public List<Book> getListOfBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id = ?", new BookMapper(), id);
    }
}
