package com.example;

import com.example.dao.EtudiantDAO;
import com.example.model.Etudiant;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Interactive menu application for managing students and rooms
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static EtudiantDAO etudiantDAO;

    public static void main(String[] args) {
        try {
            // Initialize DAOs
            etudiantDAO = new EtudiantDAO();

            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║   Bienvenue dans le Système de Gestion Universitaire  ║");
            System.out.println("╚════════════════════════════════════════════════════════╝\n");

            boolean continuer = true;

            while (continuer) {
                afficherMenuPrincipal();
                int choix = lireEntier("Votre choix : ");

                switch (choix) {
                    case 1:
                        menuEtudiants();
                        break;
                    case 0:
                        continuer = false;
                        System.out.println("\n Au revoir !");
                        break;
                    default:
                        System.out.println("Choix invalide. Veuillez réessayer.\n");
                }
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation : " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (etudiantDAO != null) etudiantDAO.close();
                scanner.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Display main menu
     */
    private static void afficherMenuPrincipal() {
        System.out.println("\n╔══════════════ MENU PRINCIPAL ═══════════════╗");
        System.out.println("║  1. Gestion des Étudiants                  ║");
        System.out.println("║  0. Quitter                                ║");
        System.out.println("╚═════════════════════════════════════════════╝");
    }

    /**
     * Student management menu
     */
    private static void menuEtudiants() {
        boolean retour = false;

        while (!retour) {
            System.out.println("\n╔══════════ GESTION DES ÉTUDIANTS ═══════════╗");
            System.out.println("║  1. Ajouter un étudiant                    ║");
            System.out.println("║  2. Rechercher un étudiant par nom         ║");
            System.out.println("║  3. Afficher tous les étudiants            ║");
            System.out.println("║  4. Modifier un étudiant                   ║");
            System.out.println("║  5. Supprimer un étudiant                  ║");
            System.out.println("║  0. Retour au menu principal               ║");
            System.out.println("╚═════════════════════════════════════════════╝");

            int choix = lireEntier("Votre choix : ");

            try {
                switch (choix) {
                    case 1:
                        ajouterEtudiant();
                        break;
                    case 2:
                        rechercherEtudiantParNom();
                        break;
                    case 3:
                        afficherTousEtudiants();
                        break;
                    case 4:
                        modifierEtudiant();
                        break;
                    case 5:
                        supprimerEtudiant();
                        break;
                    case 0:
                        retour = true;
                        break;
                    default:
                        System.out.println("Choix invalide.\n");
                }
            } catch (SQLException e) {
                System.err.println("Erreur SQL : " + e.getMessage());
            }
        }
    }

    /**
     * Add a new student
     */
    private static void ajouterEtudiant() throws SQLException {
        System.out.println("\n--- Ajouter un Étudiant ---");

        String nom = lireChaine("Nom : ");
        String prenom = lireChaine("Prénom : ");
        String email = lireChaine("Email : ");

        Etudiant etudiant = new Etudiant();
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        etudiant.setEmail(email);

        etudiantDAO.save(etudiant);
        System.out.println("Étudiant ajouté avec succès ! ID : " + etudiant.getEtudiantId());
    }

    /**
     * Search student by name
     */
    private static void rechercherEtudiantParNom() throws SQLException {
        System.out.println("\n--- Rechercher un Étudiant ---");

        String nom = lireChaine("Nom à rechercher : ");
        Etudiant etudiant = etudiantDAO.findByNom(nom);

        if (etudiant != null) {
            System.out.println("\nÉtudiant trouvé :");
            afficherEtudiant(etudiant);
        } else {
            System.out.println(" Aucun étudiant trouvé avec le nom : " + nom);
        }
    }

    /**
     * Display all students
     */
    private static void afficherTousEtudiants() throws SQLException {
        System.out.println("\n--- Liste de tous les Étudiants ---");

        List<Etudiant> etudiants = etudiantDAO.findAll();

        if (etudiants.isEmpty()) {
            System.out.println("Aucun étudiant dans la base de données.");
        } else {
            System.out.println("Nombre total d'étudiants : " + etudiants.size() + "\n");
            System.out.println("┌──────┬─────────────────┬─────────────────┬──────────────────────────┐");
            System.out.println("│  ID  │      Nom        │     Prénom      │          Email           │");
            System.out.println("├──────┼─────────────────┼─────────────────┼──────────────────────────┤");
            for (Etudiant e : etudiants) {
                System.out.printf("│ %-4d │ %-15s │ %-15s │ %-24s │%n",
                        e.getEtudiantId(),
                        e.getNom(),
                        e.getPrenom(),
                        e.getEmail());
            }
            System.out.println("└──────┴─────────────────┴─────────────────┴──────────────────────────┘");
        }
    }

    /**
     * Update student information
     */
    private static void modifierEtudiant() throws SQLException {
        System.out.println("\n--- Modifier un Étudiant ---");

        int id = lireEntier("ID de l'étudiant à modifier : ");
        Etudiant etudiant = etudiantDAO.findById(id);

        if (etudiant == null) {
            System.out.println("Étudiant introuvable avec l'ID : " + id);
            return;
        }

        System.out.println("\nÉtudiant actuel :");
        afficherEtudiant(etudiant);

        System.out.println("\nNouvelles informations (Entrée pour conserver) :");

        String nom = lireChaine("Nouveau nom [" + etudiant.getNom() + "] : ");
        if (!nom.isEmpty()) etudiant.setNom(nom);

        String prenom = lireChaine("Nouveau prénom [" + etudiant.getPrenom() + "] : ");
        if (!prenom.isEmpty()) etudiant.setPrenom(prenom);

        String email = lireChaine("Nouveau email [" + etudiant.getEmail() + "] : ");
        if (!email.isEmpty()) etudiant.setEmail(email);

        etudiantDAO.update(etudiant);
        System.out.println("Étudiant modifié avec succès !");
    }

    /**
     * Delete a student
     */
    private static void supprimerEtudiant() throws SQLException {
        System.out.println("\n--- Supprimer un Étudiant ---");

        int id = lireEntier("ID de l'étudiant à supprimer : ");
        Etudiant etudiant = etudiantDAO.findById(id);

        if (etudiant == null) {
            System.out.println("Étudiant introuvable avec l'ID : " + id);
            return;
        }

        afficherEtudiant(etudiant);

        String confirmation = lireChaine("\nConfirmer la suppression ? (oui/non) : ");
        if (confirmation.equalsIgnoreCase("oui") || confirmation.equalsIgnoreCase("o")) {
            etudiantDAO.delete(id);
            System.out.println("Étudiant supprimé avec succès !");
        } else {
            System.out.println("Suppression annulée.");
        }
    }

    // Lit un entier avec boucle de validation (ne rend que lorsqu'un entier valide est saisi)
	private static int lireEntier(String prompt) {
	    while (true) {
	        System.out.print(prompt);
	        String line = scanner.nextLine().trim();
	        try {
	            return Integer.parseInt(line);
	        } catch (NumberFormatException ex) {
	            System.out.println("Veuillez saisir un entier valide.");
	        }
	    }
	}

	// Lit une chaîne (retourne éventuellement une chaîne vide si l'utilisateur fait Entrée)
	// Utile pour "Entrée pour conserver" dans la modification.
	private static String lireChaine(String prompt) {
	    System.out.print(prompt);
	    String line = scanner.nextLine();
	    return line == null ? "" : line.trim();
	}

	// Affiche un étudiant sur plusieurs lignes (tolère les champs null)
	private static void afficherEtudiant(Etudiant e) {
	    if (e == null) {
	        System.out.println("(Aucun étudiant)");
	        return;
	    }
	    String id     = String.valueOf(e.getEtudiantId());
	    String nom    = e.getNom()    == null ? "" : e.getNom();
	    String prenom = e.getPrenom() == null ? "" : e.getPrenom();
	    String email  = e.getEmail()  == null ? "" : e.getEmail();

	    System.out.println("┌───────────────────────────────────────────────┐");
	    System.out.printf ("│ ID     : %-35s │%n", id);
	    System.out.printf ("│ Nom    : %-35s │%n", nom);
	    System.out.printf ("│ Prénom : %-35s │%n", prenom);
	    System.out.printf ("│ Email  : %-35s │%n", email);
	    System.out.println("└───────────────────────────────────────────────┘");
	}

}
