package com.example.educationalqualityproject.repository;

import com.example.educationalqualityproject.entity.Wizard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface WizardRepository extends MongoRepository<Wizard, String> {
    
    boolean existsByEmail(String email);
    
    boolean existsByMagicRegistration(String magicRegistration);
}