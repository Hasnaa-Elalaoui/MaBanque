package com.example.assuranceservice.repositories;

import com.example.assuranceservice.beans.Assurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssuranceRepository extends JpaRepository<Assurance, String> {
    List<Assurance> findAllByIdCompte(String idCompte);

    ThreadLocal<Assurance> findById(int idAssurance);
}
