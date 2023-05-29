package com.example.creditservice.controller;

import com.example.creditservice.beans.Credit;
import com.example.creditservice.repositories.CreditRepository;
import com.example.operationservice.beans.Compte;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value="Credit")
@RestController
public class CreditserviceController {

    private CreditRepository creditRepository;

    /*
    On vérifie que le client a assez d'argent pour demander le crédit (35% du montant total du crédit)
    Ensuite on ajoute le montant du crédit au solde du compte s'il remplit les conditions
     */

    @ApiOperation(value = "Demander un credit", response = Iterable.class, tags = "demanderCredit")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") }
    )
    @RequestMapping(value = "/credits/{idCompte}", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "fallbackMethod1")
    public void demanderCredit(int montant, Compte compte) {
        Credit credit = new Credit((int) Math.random(),compte.getCodeCompte(),24,0, montant);
        if(compte.getSolde()<(0.35*montant)){
            System.out.println("Vous n'avez pas assez d'argent pour demander ce crédit");
        }
        else{
            compte.setSolde(compte.getSolde()+montant);
            creditRepository.save(credit);
            System.out.println("Votre demande de crédit a été acceptée");
        }


    }

    @ApiOperation(value = "Liste des credits", response = Iterable.class, tags = "listeCredits")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") }
    )
    @RequestMapping(value = "/credits", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fallbackMethod1")
    public List<Credit> getAllCredits() {
        return creditRepository.findAll();
    }


    @ApiOperation(value = "Liste des credits d'un compte", response = Iterable.class, tags = "listeCreditsByCompte")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") }
    )
    @RequestMapping(value = "/credits/{idCompte}", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fallbackMethod1")
    public List<Credit> getAllCompteCredits(int idCompte) {
        List<Credit> compteCredits = new ArrayList<>();
        for (Credit credit : creditRepository.findAll()) {
            compteCredits = creditRepository.findAllByIdCompte(idCompte);
        }
        for (Credit credit: compteCredits ) {
            System.out.println(credit.toString());
        }
        return creditRepository.findAllByIdCompte(idCompte);
    }

}
