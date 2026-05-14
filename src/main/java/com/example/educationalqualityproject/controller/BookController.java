package com.example.educationalqualityproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.educationalqualityproject.entity.Book;
import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.service.BookService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String listBooks(Model model, HttpSession session) {

        Wizard loggedWizard =
                (Wizard) session.getAttribute("loggedWizard");

        if (loggedWizard == null) {
            return "redirect:/login";
        }

        List<Book> books =
                bookService.getBooksByWizardId(loggedWizard.getId());

        model.addAttribute("books", books);

        return "book/list";
    }

    @GetMapping("/new")
    public String showCreateForm(
            Model model,
            HttpSession session
    ) {

        Wizard loggedWizard =
                (Wizard) session.getAttribute("loggedWizard");

        if (loggedWizard == null) {
            return "redirect:/login";
        }

        model.addAttribute("book", new Book());

        return "book/form";
    }

    @PostMapping
    public String createBook(
            @ModelAttribute Book book,
            HttpSession session
    ) {

        Wizard loggedWizard =
                (Wizard) session.getAttribute("loggedWizard");

        if (loggedWizard == null) {
            return "redirect:/login";
        }

        book.setWizardId(loggedWizard.getId());

        bookService.saveBook(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(
            @PathVariable String id,
            Model model,
            HttpSession session
    ) {

        Wizard loggedWizard =
                (Wizard) session.getAttribute("loggedWizard");

        if (loggedWizard == null) {
            return "redirect:/login";
        }

        Book book = bookService.getBookById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid book Id:" + id
                        )
                );
        if (!book.getWizardId().equals(loggedWizard.getId())) {
         return "redirect:/books";
        }

        model.addAttribute("book", book);

        return "book/form";
    }


    @PostMapping("/{id}")
    public String updateBook(
            @PathVariable String id,
            @ModelAttribute Book book,
            HttpSession session
    ) {

        Wizard loggedWizard =
                (Wizard) session.getAttribute("loggedWizard");

        if (loggedWizard == null) {
            return "redirect:/login";
        }
        Book existingBook = bookService.getBookById(id)
        .orElseThrow(() ->
                new IllegalArgumentException("Livro não encontrado")
        );

        if (!existingBook.getWizardId().equals(loggedWizard.getId())) {
                return "redirect:/books";
        }

        book.setId(id);
    
        book.setWizardId(loggedWizard.getId());

        bookService.saveBook(book);

        return "redirect:/books";
    }


    @GetMapping("/{id}/delete")
    public String deleteBook(
            @PathVariable String id,
            HttpSession session
    ) {
        
        Wizard loggedWizard =
                (Wizard) session.getAttribute("loggedWizard");

        if (loggedWizard == null) {
            return "redirect:/login";
        }

        Book existingBook = bookService.getBookById(id)
        .orElseThrow(() ->
                new IllegalArgumentException("Livro não encontrado")
        );

        if (!existingBook.getWizardId().equals(loggedWizard.getId())) {
            return "redirect:/books";
        }

        bookService.deleteBook(id);


        return "redirect:/books";
    }
}