package com.example.educationalqualityproject.service;

import com.example.educationalqualityproject.entity.Book;
import com.example.educationalqualityproject.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByWizardId(String wizardId) {

        return bookRepository.findByWizardId(wizardId);
    }

    public Optional<Book> getBookById(String id) {

        return bookRepository.findById(id);
    }

    // SALVAR
    public Book saveBook(Book book) {

        return bookRepository.save(book);
    }

    // DELETAR
    public void deleteBook(String id) {

        bookRepository.deleteById(id);
    }
}