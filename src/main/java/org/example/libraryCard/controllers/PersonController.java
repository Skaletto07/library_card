package org.example.libraryCard.controllers;

import org.example.libraryCard.dao.BookDAO;
import org.example.libraryCard.dao.PersonDAO;
import org.example.libraryCard.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/person")
public class PersonController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    @Autowired
    public PersonController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping("/indexPerson")
    public String personIndex(Model model) {
        model.addAttribute("people", personDAO.index());
        return "person/people/indexPerson";
    }

    @GetMapping("/people/{id}")
    public String showPeople(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("books", bookDAO.allBooks(id));
        return "/person/people/showPerson";
    }

    @GetMapping("/people/newPerson")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "person/people/newPerson";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") Person person) {
        personDAO.save(person);
        return "redirect:/library/";
    }
    @GetMapping("/people/{id}/editPerson")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "/person/people/editPerson";
    }

    @PostMapping("/people/{id}")
    public String update(@ModelAttribute("person")  Person person,
                         @PathVariable("id") int id) {
        personDAO.update(id, person);
        return "redirect:/library";
    }

    @DeleteMapping("/people/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/library";
    }

}
