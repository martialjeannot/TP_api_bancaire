package com.example.api_bancaire.controller;

import com.example.api_bancaire.model.Compte;
import com.example.api_bancaire.model.Operation;
import com.example.api_bancaire.repository.CompteRepository;
import com.example.api_bancaire.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/comptes")
@CrossOrigin(origins = "*") // Utile si tu veux connecter un front-end plus tard
public class CompteController {

    @Autowired
    private CompteRepository repository;

    @Autowired
    private OperationRepository operationRepository;

    // 1. LISTER TOUS LES COMPTES
    @GetMapping
    public List<Compte> getTousLesComptes() {
        return repository.findAll();
    }

    // 2. RECHERCHE AVANCÉE ET FILTRAGE
    @GetMapping("/recherche")
    public List<Compte> rechercher(@RequestParam String nom) {
        return repository.findByNomClientContainingIgnoreCase(nom);
    }

    // 3. STATISTIQUES GLOBALES
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        List<Compte> comptes = repository.findAll();
        double total = comptes.stream().mapToDouble(Compte::getSolde).sum();

        Map<String, Object> stats = new HashMap<>();
        stats.put("banque_nom", "Ma Super Banque API");
        stats.put("total_depots", total);
        stats.put("nombre_clients", comptes.size());
        return stats;
    }

    // 4. CRÉATION AVEC NUMÉRO UNIQUE
    @PostMapping("/creer")
    public Compte creerCompte(@RequestBody Compte nouveauCompte) {
        nouveauCompte.setNumeroCompte("CB-" + System.currentTimeMillis() % 10000);
        nouveauCompte.setActif(true); // Compte actif par défaut
        return repository.save(nouveauCompte);
    }

    // 5. TRANSACTION AVEC HISTORIQUE (Dépôt / Retrait / Virement)
    @PostMapping("/transaction")
    public String faireTransaction(@RequestParam String numero,
                                   @RequestParam double montant,
                                   @RequestParam String type) {
        Compte compte = repository.findByNumeroCompte(numero);

        if (compte == null) return "Erreur : Compte introuvable";
        if (!compte.isActif()) return "Erreur : Ce compte est bloqué !";

        if (type.equalsIgnoreCase("retrait")) {
            if (compte.getSolde() < montant) return "Erreur : Solde insuffisant";
            compte.setSolde(compte.getSolde() - montant);
        } else {
            compte.setSolde(compte.getSolde() + montant);
        }

        // --- Sauvegarde de l'Historique ---
        Operation op = new Operation();
        op.setMontant(montant);
        op.setType(type.toUpperCase());
        op.setCompte(compte);
        operationRepository.save(op);

        repository.save(compte);
        return "Opération " + type + " réussie. Nouveau solde : " + compte.getSolde();
    }

    // 6. CALCUL D'INTÉRÊTS
    @PutMapping("/{id}/interets")
    public String appliquerInterets(@PathVariable Long id) {
        return repository.findById(id).map(compte -> {
            double interets = compte.getSolde() * compte.getTauxInteret();
            compte.setSolde(compte.getSolde() + interets);

            Operation op = new Operation();
            op.setMontant(interets);
            op.setType("INTERETS");
            op.setCompte(compte);
            operationRepository.save(op);

            repository.save(compte);
            return "Intérêts appliqués : +" + interets;
        }).orElse("Compte non trouvé");
    }

    // 7. BLOCAGE / DÉBLOCAGE
    @PatchMapping("/{id}/statut")
    public Compte changerStatut(@PathVariable Long id, @RequestParam boolean actif) {
        Compte compte = repository.findById(id).get();
        compte.setActif(actif);
        return repository.save(compte);
    }
}