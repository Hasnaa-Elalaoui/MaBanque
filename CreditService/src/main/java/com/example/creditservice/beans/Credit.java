package com.example.creditservice.beans;

import java.util.Date;

public class Credit {
    private int id;
    private String idCompte;

    private Date dateDebut;
    private int creditDuree; //durée du crédit en mois
    private int montantPaye; //montant payé
    private int creditTotal; //montant total à payer

    public Credit(int id, String idCompte, int creditDuree, int montantPaye, int creditTotal) {
        this.id = id;
        this.idCompte = idCompte;
        this.creditDuree = creditDuree;
        this.montantPaye = montantPaye;
        this.creditTotal = creditTotal;
        this.dateDebut = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreditDuree() {
        return creditDuree;
    }

    public void setCreditDuree(int creditDuree) {
        this.creditDuree = creditDuree;
    }

    public int getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(int montantPaye) {
        this.montantPaye = montantPaye;
    }

    public int getCreditTotal() {
        return creditTotal;
    }

    public void setCreditTotal(int creditTotal) {
        this.creditTotal = creditTotal;
    }

    public String toString() {
        return "Credit : {id=" + id + "; creditDuree=" + creditDuree + "; montantPaye=" + montantPaye + "; creditTotal=" + creditTotal +"; montant restant à payer="+ getMontantRestant() +"; sur "+getDureeRestante()+ " mois.}";
    }

    public int getMontantRestant() {
        return creditTotal-montantPaye;
    }

    public int getDureeRestante() {
        Date dateFin = dateDebut;
        dateFin.setMonth(dateFin.getMonth()+creditDuree);
        return dateFin.getMonth()-new Date().getMonth();
    }

}
