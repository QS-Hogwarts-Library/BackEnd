package com.example.educationalqualityproject.repository;

import com.example.educationalqualityproject.entity.Book;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository
        extends MongoRepository<Book, String> {

    List<Book> findByWizardId(String wizardId);
}