package org.example.libraryCard.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    private int id;
    private String title;
    private String author;
    private int yearOfBook;
    private int person_id;
}
