package com.example.api_bancaire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// On ajoute l'instruction pour dire à Spring d'aller voir aussi dans com.banque.api
@SpringBootApplication(scanBasePackages = {"com.example.api_bancaire", "com.banque.api"})
public class ApiBancaireApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiBancaireApplication.class, args);
    }
}