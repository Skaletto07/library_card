package org.example.libraryCard.dao;

import org.example.libraryCard.models.Book;
import org.example.libraryCard.models.Person;
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
        return jdbcTemplate.query("select * from book", new BookMapper());
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?",
                new BookMapper(),
                new Object[]{id}).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author, year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYearOfBook());
    }

    public void update(int id, Book book) {
        show(id);
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE book_id=?",
                book.getTitle(), book.getAuthor(), book.getYearOfBook(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", id);
    }

    public List<Book> allBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM book  JOIN person  " +
                        "ON person.person_id = book.person_id WHERE person.person_id=?",
                new BookMapper(), id);
    }

    public void addBookForPerson(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE book_id = ?", id, book.getId());
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT person.* FROM book JOIN person " +
                "ON book.person_id = person.person_id WHERE book_id = ?",
                new PersonMapper(), id).stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE book SET person_id = NULL WHERE book_id = ?", id);
    }

    public void assign(int id, Person person) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE book_id = ?", person.getId(), id);
    }
}
