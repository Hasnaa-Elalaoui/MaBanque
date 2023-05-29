package com.example.assuranceservice.controller;

import com.example.assuranceservice.beans.Assurance;
import com.example.assuranceservice.beans.EAssurance;
import com.example.assuranceservice.repositories.AssuranceRepository;
import com.example.operationservice.repositories.CompteRepository;
import com.example.operationservice.beans.Compte;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

import java.util.List;

@Api(value="Assurance")
@RestController
public class AssuranceServiceController {

    AssuranceRepository assuranceRepository;
    CompteRepository compteRepository;

    @ApiOperation(value = "Get all assurances ", response = Iterable.class, tags = "getAssurances")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") }
    )
    @GetMapping("/listAssurances")
    @RequestMapping(value = "/assurances", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fallbackMethod1")
    public List<Assurance> getAllAssurances(){
        return assuranceRepository.findAll();
    }


    @ApiOperation(value = "Get assurances by compte ", response = Iterable.class, tags = "getAssurancesByCompte")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") }
    )
    @RequestMapping(value = "/assurances/{idCompte}", method = RequestMethod.GET)
    public List<Assurance> getAssurancesByCompte(String idCompte){
        return assuranceRepository.findAllByIdCompte(idCompte);
    }

    @ApiOperation(value = "Subscribe to an insurance ", response = Iterable.class, tags = "subscribeInsurance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") }
    )
    @RequestMapping(value = "/assurances/{typeAssurance}/{idCompte}", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "fallbackMethod1")
    public void souscrireAssurance(EAssurance typeAssurance, String idCompte, int montant){
        Assurance assurance = new Assurance();
        assurance.setTypeAssurance(typeAssurance);
        assurance.setMontant(montant);
        Compte compte = compteRepository.findById(idCompte).get();
        if(compte.getSolde()<assurance.getMontant()){
            throw new RuntimeException("Solde insuffisant pour souscrire à ce type d'assurance");
        }
        else {
            assurance.setIdCompte(idCompte);
            compte.setSolde(compte.getSolde() - assurance.getMontant());
        }

        assuranceRepository.save(assurance);
    }

    @ApiOperation(value = "Delete  an insurance ", response = Iterable.class, tags = "deleteInsurance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found") }
    )
    @RequestMapping(value = "/assurances/{typeAssurance}/{idCompte}", method = RequestMethod.DELETE)
    @HystrixCommand(fallbackMethod = "fallbackMethod1")
    public void resilierAssurance(int idAssurance, String idCompte){
        Assurance assurance = assuranceRepository.findById(idAssurance).get();
        Compte compte = compteRepository.findById(idCompte).get();
        compte.setSolde(compte.getSolde() + assurance.getMontant());
        assuranceRepository.delete(assurance);
    }

}
