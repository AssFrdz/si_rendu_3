package com.example.service;

import com.example.dao.EtudiantDAO;
import com.example.model.Etudiant;

import java.sql.SQLException;
import java.util.List;

public class EtudiantService {

    private final EtudiantDAO etudiantDAO;

    public EtudiantService() throws Exception {
        this.etudiantDAO = new EtudiantDAO();
    }

    /**
     * Enregistre un nouvel étudiant après validation simple.
     * @param etudiant L'étudiant à enregistrer.
     * @return L'étudiant enregistré avec son ID généré.
     * @throws IllegalArgumentException si les données de l'étudiant sont invalides.
     * @throws SQLException en cas d'erreur de base de données.
     */
    public Etudiant enregistrerEtudiant(Etudiant etudiant) throws IllegalArgumentException, SQLException {
        if (etudiant.getNom() == null || etudiant.getNom().trim().isEmpty() ||
            etudiant.getPrenom() == null || etudiant.getPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom et le prénom de l'étudiant sont obligatoires.");
        }
        
        etudiantDAO.save(etudiant);
        return etudiant;
    }
// The comment `/**
//  * Récupère tous les étudiants.
//  */` is a documentation comment for the method `listerTousLesEtudiants()` in the `EtudiantService`
// class. This comment serves as documentation for developers who will be using this method. It
// describes what the method does, which is to retrieve all students.

    /**
     * Récupère tous les étudiants.
     * @return La liste de tous les étudiants.
     * @throws SQLException en cas d'erreur de base de données.
     */
    public List<Etudiant> listerTousLesEtudiants() throws SQLException {
        return etudiantDAO.findAll();
    }

    /**
     * Ferme la connexion DAO.
     * @throws SQLException
     */
    public void close() throws SQLException {
        etudiantDAO.close();
    }
}
