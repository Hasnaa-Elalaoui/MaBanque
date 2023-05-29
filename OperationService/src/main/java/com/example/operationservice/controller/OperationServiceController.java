package com.example.operationservice.controller;

import com.example.operationservice.beans.Compte;
import com.example.operationservice.repositories.CompteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api(value="Operations")
@RestController
public class OperationServiceController {

    private CompteRepository compteRepository;

    /*
     Avant de retirer un montant, il faut vérifier que le solde du compte est suffisant
     */

    @ApiOperation(value = "Retirer montant", response = Iterable.class, tags = "retirerMontant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") }
    )
    @RequestMapping(value = "/operations/{codeCompte}/{montant}", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "fallbackMethod1")
    public void retirerMontant(String codeCompte, double montant) {

        Compte compte = compteRepository.findById(codeCompte).get();

        if(compte.getSolde()<montant) {
            throw new RuntimeException("Votre solde est insuffisant pour retirer cette somme");
        }
        compte.setSolde(compte.getSolde() - montant);
        compteRepository.save(compte);

    }
    @ApiOperation(value = "Alimenter compte", response = Iterable.class, tags = "alimenterCompte")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") }
    )
    @RequestMapping(value = "/operations/{codeCompte}/{montant}", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "fallbackMethod1")
    public void alimenterCompte(String codeCompte, double montant) {

        Compte compte = compteRepository.findById(codeCompte).get();

        compte.setSolde(compte.getSolde() + montant);
        compteRepository.save(compte);

    }

    /*
    Pour consulter le solde d'un compte, il faut d'abord le trouver dans la base de données
     */
    @ApiOperation(value = "Avoir solde", response = Iterable.class, tags = "avoirSolde")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") }
    )
    @RequestMapping(value = "/operations/{codeCompte}", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fallbackMethod1")
    public void getSoldeCompte(String codeCompte) {

        Compte compte = compteRepository.findById(codeCompte).get();

        System.out.println("Votre solde est de : " + compte.getSolde() + " euros");

    }

    /*
    Pour effectuer une transaction, il faut d'abord trouver les deux comptes dans la base de données
    Puis vérifier que le solde du compte émetteur est suffisant
    Et enfin retirer du premier compte pour alimenter le deuxième
     */
    @ApiOperation(value = "Faire une transaction", response = Iterable.class, tags = "faireTransaction")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") }
    )
    @RequestMapping(value = "/operations/{codeCompte}/{montant}", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "fallbackMethod1")
    public void setTransaction(String codeCompte1, String codeCompte2, double montant) {

        Compte compte1 = compteRepository.findById(codeCompte1).get();
        Compte compte2 = compteRepository.findById(codeCompte2).get();

        if(compte1.getSolde()<montant) {
            throw new RuntimeException("Votre solde est insuffisant pour effectuer cette transaction");
        }
        compte1.setSolde(compte1.getSolde() - montant);
        compte2.setSolde(compte2.getSolde() + montant);

        compteRepository.save(compte1);
        compteRepository.save(compte2);

        System.out.println("Transaction effectuée avec succès");

    }


}
