const express = require('express');
const sequelize = require('./config/database');
const compteController = require('./controllers/compteController');

const app = express();
app.use(express.json());
app.use(express.static('public')); // Permet de rendre accessible notre dossier frontend

// Association des routes aux nouvelles fonctionnalités requises
app.post('/api/comptes/creer', compteController.creerCompte);
app.post('/api/comptes/connexion', compteController.connexionCompte);
app.post('/api/comptes/transaction', compteController.faireTransaction);
app.post('/api/comptes/transfert', compteController.transfererArgent);
app.delete('/api/comptes/:id/supprimer', compteController.supprimerCompteAdmin);
app.get('/api/comptes', compteController.getTousLesComptes);

// Exportation de l'application (indispensable pour les tests d'intégration futurs)
module.exports = app;

// Démarrage du serveur uniquement si on ne teste pas
if (process.env.NODE_ENV !== 'test') {
    const PORT = process.env.PORT || 8080;
    
    // Synchronisation simple et sécurisée sans { alter: true }
    sequelize.sync().then(() => {
        console.log('Base de données synchronisée.');
        app.listen(PORT, () => console.log(`Serveur actif sur le port ${PORT}`));
    }).catch(err => console.error('Erreur DB:', err));
}