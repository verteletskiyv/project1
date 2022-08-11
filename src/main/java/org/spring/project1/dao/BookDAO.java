package org.spring.project1.dao;

import org.spring.project1.models.Book;
import org.spring.project1.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
    }

    public Book show(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Book WHERE book_id = ?", new BookMapper(), id);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title, author, year_written) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear_written());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET title = ?, author = ?, year_written = ? WHERE book_id=?",
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear_written(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    public void assign(int bookId, Person selectedPerson) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE book_id = ?", selectedPerson.getPerson_id(), bookId);
    }

    public void release(int bookId) {
        jdbcTemplate.update("UPDATE book SET person_id = null WHERE book_id = ?", bookId);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT person.* FROM person JOIN book ON person.person_id = book.person_id WHERE book.book_id = ?",
                new PersonMapper(), id).stream().findAny();
    }
}
