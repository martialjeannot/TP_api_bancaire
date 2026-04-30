package com.example.api_bancaire.repository;


import com.example.api_bancaire.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    // Cette méthode permettra de récupérer l'historique d'un compte spécifique
    List<Operation> findByCompteId(Long compteId);
}