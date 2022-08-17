package org.spring.project1.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;
    @NotEmpty(message = "Please specify your name")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "Please stick to a valid name format - John Doe")
    @Size(min = 1, max = 100, message = "Name should be between 1 and 100 characters")
    @Column(name = "name")
    private String name;

    @Max(value = 2022, message = "You haven't been born yet!")
    @Min(value = 1900, message = "You can't be this old!")
    @Column(name = "birth_year")
    private int birth_year;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person() {}

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Person(String name, int birth_year) {
        this.name = name;
        this.birth_year = birth_year;
    }


    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }
}
