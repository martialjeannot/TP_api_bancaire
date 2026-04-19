package com.banque.api.controller;

import com.banque.api.model.Compte;
import com.banque.api.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    @Autowired
    private CompteRepository repository;

    // Lister les comptes
    @GetMapping
    public List<Compte> getTousLesComptes() {
        return repository.findAll();
    }

    // Créer un compte
    @PostMapping("/creer")
    public Compte creerCompte(@RequestBody Compte nouveauCompte) {
        nouveauCompte.setNumeroCompte("CB-" + (int)(Math.random() * 9000));
        return repository.save(nouveauCompte);
    }

    // Transaction : Dépôt ou Retrait
    @PostMapping("/transaction")
    public String faireTransaction(@RequestParam String numero,
                                   @RequestParam double montant,
                                   @RequestParam String type) {
        Compte compte = repository.findByNumeroCompte(numero);

        if (type.equals("retrait")) {
            if (compte.getSolde() < montant) return "Erreur : Solde insuffisant";
            compte.setSolde(compte.getSolde() - montant);
        } else {
            compte.setSolde(compte.getSolde() + montant);
        }

        repository.save(compte);
        return "Transaction réussie. Nouveau solde : " + compte.getSolde();
    }
}