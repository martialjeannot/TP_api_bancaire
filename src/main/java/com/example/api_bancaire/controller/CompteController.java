package com.example.api_bancaire.controller;

// GARDER UNIQUEMENT CES DEUX-LÀ
import com.example.api_bancaire.model.Compte;
import com.example.api_bancaire.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired; // Ajoute celle-ci si @Autowired est rouge
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    @Autowired
    private CompteRepository repository;

    @GetMapping
    public List<Compte> getTousLesComptes() {
        return repository.findAll();
    }

    @PostMapping("/creer")
    public Compte creerCompte(@RequestBody Compte nouveauCompte) {
        nouveauCompte.setNumeroCompte("CB-" + (int)(Math.random() * 9000));
        return repository.save(nouveauCompte);
    }

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