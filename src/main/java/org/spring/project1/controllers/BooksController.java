package org.spring.project1.controllers;

import org.spring.project1.models.Book;
import org.spring.project1.models.Person;
import org.spring.project1.services.BooksService;
import org.spring.project1.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("books_per_page") Optional<Integer> books_per_page,
                        @RequestParam(name = "sort_by_year", required = false) Optional<Boolean> isSorted) {

        if (isSorted.isPresent()) {
            if (page.isPresent() && books_per_page.isPresent()) {
                if (isSorted.get())
                    model.addAttribute("books", booksService.findAll(page.get(), books_per_page.get(), Sort.by("yearWritten")));
                else
                    model.addAttribute("books", booksService.findAll(page.get(), books_per_page.get()));
            } else {
                if (isSorted.get())
                    model.addAttribute("books", booksService.findAll(Sort.by("yearWritten")));
                else
                    model.addAttribute("books", booksService.findAll());
            }
        } else {
            if (page.isPresent() && books_per_page.isPresent())
                model.addAttribute("books", booksService.findAll(page.get(), books_per_page.get()));
            else
                model.addAttribute("books", booksService.findAll());
        }

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = booksService.findOne(id);
        model.addAttribute("book", book);
        Person bookOwner = book.getOwner();

        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", peopleService.findAll());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("selectedPerson") Person selectedPerson) {
        booksService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchPage(Model model, @RequestParam("query") Optional<String> startsWith) {
        if (startsWith.isPresent())
            model.addAttribute("foundBook", booksService.findByNameStartsWith(startsWith.get()));


        return "books/search";
    }

}

