package org.example.libraryCard.controllers;

import org.example.libraryCard.dao.BookDAO;
import org.example.libraryCard.dao.PersonDAO;
import org.example.libraryCard.models.Book;
import org.example.libraryCard.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/library")
public class LibraryController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    @Autowired
    public LibraryController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String index() {
        return "/library/library";
    }


    @GetMapping("/indexLibrary")
    public String bookIndex(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "/library/books/indexLibrary";
    }


    /*@GetMapping("/books/{id}")
    public String showBooks(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));
        return "/library/books/showBook";
    }*/

    @GetMapping("/books/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));
        Optional<Person> bookOwner = bookDAO.getBookOwner(id);
        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDAO.index());
        return "/library/books/showBook";
    }

    @GetMapping("/books/newBook")
    public String newBook(@ModelAttribute("book") Book book) {
    return "library/books/newBook";
}

    @PostMapping()
    public String createBook(@ModelAttribute("book") Book book) {
        bookDAO.save(book);
        return "redirect:/library";
    }


    @GetMapping("/books/{id}/editBook")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "/library/books/editBook";
    }

    @PostMapping("/books/{id}")
    public String update(@ModelAttribute("book")  Book book,
                         @PathVariable("id") int id) {
        bookDAO.update(id, book);
        return "redirect:/library";
    }

    @DeleteMapping("/books/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/library";
    }

    @PostMapping("/books/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.release(id);
        return "redirect:/library/books/" + id;
    }

    @PostMapping("/books/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.assign(id, person);
        return "redirect:/library/books/" + id;
    }
}
