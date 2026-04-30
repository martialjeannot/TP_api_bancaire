package com.example.api_bancaire.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double montant;
    private String type; // "VIREMENT", "DEPOT", "RETRAIT", "INTERETS"
    private LocalDateTime dateOperation = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private Compte compte;
}