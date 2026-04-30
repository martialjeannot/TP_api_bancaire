package com.example.api_bancaire.repository;

import com.example.api_bancaire.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {

    // Recherche exacte par numéro de compte
    Compte findByNumeroCompte(String numeroCompte);

    // NOUVEAU : Recherche par nom (insensible à la casse et recherche partielle)
    // Utile pour la fonctionnalité "Recherche Avancée et Filtrage"
    List<Compte> findByNomClientContainingIgnoreCase(String nomClient);
}