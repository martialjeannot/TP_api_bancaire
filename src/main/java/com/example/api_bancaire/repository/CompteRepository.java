package com.example.api_bancaire.repository; // 1. Toujours en premier

// 2. Les imports ensuite
import com.example.api_bancaire.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 3. La déclaration de l'interface enfin
@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {

    // Cette méthode magique génère la requête SQL toute seule
    Compte findByNumeroCompte(String numeroCompte);
}