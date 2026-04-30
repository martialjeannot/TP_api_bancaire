package com.example.api_bancaire.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroCompte;
    private String nomClient;
    private double solde;

    // --- Nouveaux champs pour tes fonctionnalités ---

    // Pour le Blocage/Déblocage
    private boolean actif = true;

    // Pour le Calcul d'intérêts (ex: 0.03 pour 3%)
    private double tauxInteret = 0.03;

    // Pour l'Historique des Transactions (Relation 1-N)
    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL)
    @JsonIgnore // Pour éviter les boucles infinies dans Swagger
    private List<Operation> historique;
}