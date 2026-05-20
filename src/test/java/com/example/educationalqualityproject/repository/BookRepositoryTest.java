package com.example.educationalqualityproject.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.educationalqualityproject.entity.Book;
import com.example.educationalqualityproject.integration.BaseIntegrationTest;

class BookRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldSaveBook() {

        Book book = new Book();

        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setSubject("Fantasia");

        Book savedBook =
                bookRepository.save(book);

        assertNotNull(savedBook.getId());
    }

    @Test
    void shouldFindBookById() {

        Book book = new Book();

        book.setTitle("Hobbit");
        book.setAuthor("Tolkien");
        book.setSubject("Fantasia");

        Book savedBook =
                bookRepository.save(book);

        var foundBook =
                bookRepository.findById(
                        savedBook.getId()
                );

        assertTrue(foundBook.isPresent());
    }

    @Test
    void shouldFindBooksByWizardId() {

        Book book = new Book();

        book.setTitle("Livro Teste");
        book.setAuthor("Autor");
        book.setSubject("Magia");
        book.setWizardId("wizard123");

        bookRepository.save(book);

        List<Book> books =
                bookRepository.findByWizardId(
                        "wizard123"
                );

        assertFalse(books.isEmpty());
    }
}