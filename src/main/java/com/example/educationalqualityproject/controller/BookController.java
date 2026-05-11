package com.example.educationalqualityproject.controller;


import com.example.educationalqualityproject.entity.Book;
import com.example.educationalqualityproject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/form";
    }

    @PostMapping
    public String createBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "book/form";
    }

    @PostMapping("/{id}")
    public String updateBook(@PathVariable String id, @ModelAttribute Book book) {
        book.setId(id);
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/delete")
    public String deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}