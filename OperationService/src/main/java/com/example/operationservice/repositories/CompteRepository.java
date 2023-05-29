package com.example.operationservice.repositories;

import com.example.operationservice.beans.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte, String> {
    ThreadLocal<Compte> findById(String codeCompte);
}