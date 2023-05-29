package com.example.assuranceservice.beans;


import org.springframework.stereotype.Service;

@Service
public class Assurance {

    int idAssurance;

    private EAssurance typeAssurance;

    int montant; // montant de l'assurance
    String idCompte; // id du compte auquel est lié l'assurance

    public Assurance() {}

    public Assurance(int idAssurance, EAssurance typeAssurance, int montant, String idCompte) {
        this.idAssurance = idAssurance;
        this.typeAssurance = typeAssurance;
        this.montant = montant;
        this.idCompte = idCompte;
    }

    public int getIdAssurance() {
        return idAssurance;
    }

    public void setIdAssurance(int idAssurance) {
        this.idAssurance = idAssurance;
    }

    public EAssurance getTypeAssurance() {
        return typeAssurance;
    }

    public void setTypeAssurance(EAssurance typeAssurance) {
        this.typeAssurance = typeAssurance;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(String idCompte) {
        this.idCompte = idCompte;
    }
}