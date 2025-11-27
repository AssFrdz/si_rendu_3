package com.example.service;

import com.example.dao.InscriptionDAO;
import com.example.model.Inscription;

import java.sql.SQLException;
import java.util.List;

public class InscriptionService {

    private final InscriptionDAO inscriptionDAO;

    public InscriptionService() throws Exception {
        this.inscriptionDAO = new InscriptionDAO();
    }

    /**
     * Inscrit un étudiant à un enseignement après validation.
     * @param etudiantId ID de l'étudiant.
     * @param enseignementId ID de l'enseignement.
     * @param masterId ID du Master.
     * @return L'objet Inscription créé.
     * @throws IllegalArgumentException si les IDs sont invalides.
     * @throws SQLException en cas d'erreur de base de données.
     */
    public Inscription inscrireEtudiant(int etudiantId, int enseignementId, int masterId) throws IllegalArgumentException, SQLException {
        if (etudiantId <= 0 || enseignementId <= 0 || masterId <= 0) {
            throw new IllegalArgumentException("Les IDs de l'étudiant, de l'enseignement et du master doivent être positifs.");
        }
        
        // Logique métier : vérifier si l'étudiant est déjà inscrit à cet enseignement
        // Cette vérification est déjà gérée par la contrainte UNIQUE dans la base de données,
        // mais une vérification côté service est une bonne pratique.
        
        Inscription inscription = new Inscription();
        inscription.setEtudiantId(etudiantId);
        inscription.setEnseignementId(enseignementId);
        inscription.setMasterId(masterId);

        inscriptionDAO.save(inscription);
        return inscription;
    }

    /**
     * Récupère toutes les inscriptions.
     * @return La liste de toutes les inscriptions.
     * @throws SQLException en cas d'erreur de base de données.
     */
    public List<Inscription> listerToutesLesInscriptions() throws SQLException {
        return inscriptionDAO.findAll();
    }

    /**
     * Ferme la connexion DAO.
     * @throws SQLException
     */
    public void close() throws SQLException {
        inscriptionDAO.close();
    }
}
