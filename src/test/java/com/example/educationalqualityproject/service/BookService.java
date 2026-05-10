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

    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        // Regra de negócio: evitar títulos duplicados
        if (book.getId() == null && bookRepository.existsByTitle(book.getTitle())) {
            throw new RuntimeException("Este título já existe no acervo de Hogwarts!");
        }
        return bookRepository.save(book);
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    public boolean existsByTitle(String title) {
        return bookRepository.existsByTitle(title);
    }
}