package org.spring.project1.services;

import org.spring.project1.models.Book;
import org.spring.project1.models.Person;
import org.spring.project1.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public List<Book> findAll(int page, int books_per_page) {
        return booksRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
    }

    public List<Book> findAll(Sort sort) {
        return booksRepository.findAll(sort);
    }

    public List<Book> findAll(int page, int books_per_page, Sort sort) {
        return booksRepository.findAll(PageRequest.of(page, books_per_page, sort)).getContent();
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public List<Book> findByNameStartsWith(String startsWith) {
        return booksRepository.findByTitleStartsWith(startsWith);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setBook_id(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assign(int bookId, Person selectedPerson) {
        Optional<Book> book = booksRepository.findById(bookId);
        if (book.isPresent()) {
            book.get().setOwner(selectedPerson);
            book.get().setLastTaken(new Date());
        }

    }

    @Transactional
    public void release(int bookId) {
        Optional<Book> book = booksRepository.findById(bookId);
        book.ifPresent(value -> value.setOwner(null));
    }



}
