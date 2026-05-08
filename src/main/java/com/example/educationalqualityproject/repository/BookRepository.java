package com.example.educationalqualityproject.repository;

import com.example.educationalqualityproject.entity.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    
    boolean existsByAuthor(String author);

    boolean existsByTitle(String title);
}