package org.example.libraryCard.models;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {

    private int id;
    private int book_id;
    private String fio;
    private int yearBirthday;


}
