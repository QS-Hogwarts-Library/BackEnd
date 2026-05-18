package com.example.educationalqualityproject.service;

import com.example.educationalqualityproject.entity.Book;
import com.example.educationalqualityproject.integration.BaseIntegrationTest;
import com.example.educationalqualityproject.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest extends BaseIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void shouldSaveBook() {
        Book book = new Book("Livro Teste", "Autor Teste", "Fantasia");
        book.setStatusLeitura("LENDO");
        book.setWizardId("wizard123");

        Book savedBook = bookService.saveBook(book);

        assertNotNull(savedBook.getId());
        assertEquals("Livro Teste", savedBook.getTitle());
    }

    @Test
    void shouldFindBooksByWizardId() {
        Book book = new Book("Harry Potter", "J.K Rowling", "Magia");
        book.setStatusLeitura("LIDO");
        book.setWizardId("wizardABC");

        bookService.saveBook(book);

        List<Book> books = bookService.getBooksByWizardId("wizardABC");

        assertFalse(books.isEmpty());
        assertEquals("Harry Potter", books.get(0).getTitle());
    }

    @Test
    void shouldDeleteBook() {
        Book book = new Book("Livro Delete", "Autor", "Teste");
        book.setWizardId("wizardDelete");

        Book savedBook = bookService.saveBook(book);
        bookService.deleteBook(savedBook.getId());

        assertTrue(bookService.getBookById(savedBook.getId()).isEmpty());
    }
}