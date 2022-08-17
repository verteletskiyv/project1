package org.spring.project1.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;
    @NotEmpty(message = "Title can not be empty")
    @Size(min = 1, max = 150, message = "Title should be between 1 and 150 characters")
    @Column(name = "title")
    private String title;
    @NotEmpty(message = "Author's name can not be empty")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "Please stick to a valid name format - John Doe")
    @Size(min = 1, max = 150, message = "Name should be between 1 and 150 characters")
    @Column(name = "author")
    private String author;

    @Max(value = 2022, message = "Only add books that are already written")
    @Column(name = "year_written")
    private int yearWritten;

    @Column(name = "last_taken")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTaken;

    @Transient
    private Boolean isExpired;

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        this.isExpired = expired;
    }

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    public Book() {
    }

    public Book(String title, String author, int yearWritten) {
        this.title = title;
        this.author = author;
        this.yearWritten = yearWritten;
    }

    public Date getLastTaken() {
        return lastTaken;
    }

    public void setLastTaken(Date lastTaken) {
        this.lastTaken = lastTaken;
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

    public int getYearWritten() {
        return yearWritten;
    }

    public void setYearWritten(int year_written) {
        this.yearWritten = year_written;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
