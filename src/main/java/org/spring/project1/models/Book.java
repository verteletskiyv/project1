package org.spring.project1.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Book {
    private int book_id;
    @NotEmpty(message = "Title can not be empty")
    @Size(min = 1, max = 150, message = "Title should be between 1 and 150 characters")
    private String title;
    @NotEmpty(message = "Author's name can not be empty")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "Please stick to a valid name format - John Doe")
    @Size(min = 1, max = 150, message = "Name should be between 1 and 150 characters")
    private String author;

    @Max(value = 2022, message = "Only add books that are already written")
    private int year_written;
    private int person_id;

    public Book() {
    }

    public Book(String title, String author, int year_written, int person_id) {
        this.title = title;
        this.author = author;
        this.year_written = year_written;
        this.person_id = person_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear_written() {
        return year_written;
    }

    public void setYear_written(int year_written) {
        this.year_written = year_written;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }
}
