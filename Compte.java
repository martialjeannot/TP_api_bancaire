package com.banque.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // Génère les Getters/Setters automatiquement
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroCompte;
    private String nomClient;
    private double solde;
}