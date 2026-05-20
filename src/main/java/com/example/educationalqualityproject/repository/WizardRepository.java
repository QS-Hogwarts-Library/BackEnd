package com.example.educationalqualityproject.repository;

import com.example.educationalqualityproject.entity.Wizard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WizardRepository extends MongoRepository<Wizard, String> {
    
    boolean existsByEmail(String email);
    Optional<Wizard> findByEmailAndMagicRegistration(String email, String magicRegistration);
    
    Optional<Wizard> findByEmail(String email);
}