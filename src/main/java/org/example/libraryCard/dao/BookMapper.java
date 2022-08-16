package org.example.libraryCard.dao;

import org.example.libraryCard.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setYearOfBook(rs.getInt("year"));
        book.setId(rs.getInt("book_id"));
        book.setAuthor(rs.getString("author"));
        book.setTitle(rs.getString("title"));
        book.setPerson_id(rs.getInt("person_id"));
        return book;
    }
}
