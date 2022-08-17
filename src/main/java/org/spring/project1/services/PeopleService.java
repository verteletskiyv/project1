package org.spring.project1.services;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.spring.project1.models.Book;
import org.spring.project1.models.Person;
import org.spring.project1.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setPerson_id(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<Person> findByName(String name) {
        return peopleRepository.findByName(name);
    }

    public List<Book> getListOfBooks(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        Date xDaysAgo = Date.from(Instant.now().minus(Duration.ofDays(10)));

        List<Book> tmpList;
        List<Book> resList = new ArrayList<>();
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            tmpList = person.get().getBooks();
            for (Book b : tmpList) {
                if (b.getLastTaken().before(xDaysAgo))
                    b.setExpired(true);
                resList.add(b);
            }
            return resList;
        } else
            return Collections.emptyList();
    }

}
