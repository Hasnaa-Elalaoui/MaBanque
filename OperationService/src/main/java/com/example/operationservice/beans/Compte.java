package com.example.operationservice.beans;

import org.springframework.data.annotation.Id;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class Compte implements Serializable {

    @Id
    private String codeCompte;
    private double solde;
    @ManyToOne
    @JoinColumn(name="CODE_CLIENT")
    private Client client;
    @OneToMany(mappedBy="compte")
    private Collection<Operation> operations;

    public Compte() {

    }

    public Compte(String codeCompte, double solde, Client client) {
        this.codeCompte = codeCompte;
        this.solde = solde;
        this.client = client;
    }


    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Client getClient() {
        return client;
    }

    public void setCleint(Client client) {
        this.client = client;
    }

    public Collection<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Collection<Operation> operations) {
        this.operations = operations;
    }


}
