package com.example.educationalqualityproject.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "books")
public class Book {

    @Id
    private String id;
private String statusLeitura;
    private String title;
    private String author;
    private String subject;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Book() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Book(String title, String author, String subject) {
        this();
        this.title = title;
        this.author = author;
        this.subject = subject;
    }

    public String getId() {
        return id;
    }
public String getStatusLeitura() {
    return statusLeitura;
}
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        this.updatedAt = LocalDateTime.now();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public void setStatusLeitura(String statusLeitura) {
    this.statusLeitura = statusLeitura;
    this.updatedAt = LocalDateTime.now();
}
}