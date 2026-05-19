package com.example.educationalqualityproject.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.educationalqualityproject.entity.Book;

public interface BookRepository
        extends MongoRepository<Book, String> {

    List<Book> findByWizardId(String wizardId);
}