package org.spring.project1.util;

import org.spring.project1.dao.PersonDAO;
import org.spring.project1.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personDAO.find(person.getName()).isPresent())
            errors.rejectValue("name", "", "Name must be unique");

        if (person.getBirth_year() < 1900 || person.getBirth_year() > 2022)
            errors.rejectValue("birth_year", "", "Name must be between 1900 and 2022");
    }
}
