package com.example.creditservice.repositories;
import com.example.creditservice.beans.Credit;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, String> {

    public Credit findById(int idCredit);

    public List<Credit> findAll();

    List<Credit> findAllByIdCompte(int idCompte);
}
