package main.java.com.example;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import main.java.com.example.dao.EnseignementDAO;
import main.java.com.example.dao.EtudiantDAO;
import main.java.com.example.dao.InscriptionDAO;
import main.java.com.example.dao.NoteDAO;
import main.java.com.example.dao.ReservationDAO;
import main.java.com.example.dao.SalleDAO;
import main.java.com.example.model.Enseignant;
import main.java.com.example.model.Enseignement;
import main.java.com.example.model.Etudiant;
import main.java.com.example.model.Inscription;
import main.java.com.example.model.Master;
import main.java.com.example.model.Note;
import main.java.com.example.model.Reservation;
import main.java.com.example.model.Salle;

public class GestionUniversitaire {
    public static Scanner scanner = new Scanner(System.in);
    public static EtudiantDAO etudiantDAO;
    public static EnseignementDAO enseignementDAO;
    public static InscriptionDAO inscriptionDAO;
    public static NoteDAO noteDAO;
    public static SalleDAO salleDAO;
    public static ReservationDAO reservationDAO;

    public static void main(String[] args) {
        try {
            // Initialize DAOs
            etudiantDAO = new EtudiantDAO();
            enseignementDAO = new EnseignementDAO();
            inscriptionDAO = new InscriptionDAO();
            salleDAO = new SalleDAO();
            noteDAO = new NoteDAO();
            reservationDAO = new ReservationDAO();
            System.out.println("+-----------------------------------------------+");
            System.out.println("|   Bienvenue dans le Systeme de Gestion       |");
            System.out.println("|   Universitaire                              |");
            System.out.println("+-----------------------------------------------+");

            boolean continuer = true;
            while (continuer) {
                afficherMenuPrincipal();
                int choix = lireEntier("Votre choix : ");
                switch (choix) {
                    case 1:
                        menuEtudiants();
                        break;
                    case 2:
                        menuEnseignement();
                        break;
                    case 3:
                        menuInscription();
                        break;
                    case 4:
                        menuNotes();
                        break;
                    case 5:
                        menuSalle();
                        break;
                    case 6:
                        menuReservations();
                        break;
                    case 0:
                        continuer = false;
                        System.out.println("\n Au revoir !");
                        break;
                    default:
                        System.out.println("Choix invalide. Veuillez reessayer.\n");
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
    public static void afficherMenuPrincipal() {
        System.out.println("\n+-----------------------------------------------+");
        System.out.println("|                 MENU PRINCIPAL                |");
        System.out.println("+-----------------------------------------------+");
        System.out.println("|  1. Gestion des Etudiants                    |");
        System.out.println("|  2. Gestion des Cours                        |");
        System.out.println("|  3. Gestion des Inscriptions                 |");
        System.out.println("|  4. Gestion des Notes                        |");
        System.out.println("|  5. Gestion des Salles                        |");
        System.out.println("|  6. Gestion des REservations                  |");
        System.out.println("+-----------------------------------------------+");
    }

    /**
     * Student management menu
     */

     public static void menuReservations() {
    boolean retour = false;
    while (!retour) {
        System.out.println("\n+-----------------------------------------------+");
        System.out.println("|            GESTION DES RÉSERVATIONS           |");
        System.out.println("+-----------------------------------------------+");
        System.out.println("|  1. Ajouter une réservation                  |");
        System.out.println("|  2. Rechercher une réservation par ID        |");
        System.out.println("|  3. Afficher toutes les réservations         |");
        System.out.println("|  4. Modifier une réservation                |");
        System.out.println("|  5. Supprimer une réservation               |");
        System.out.println("|  0. Retour au menu principal               |");
        System.out.println("+-----------------------------------------------+");
        int choix = lireEntier("Votre choix : ");
        try {
            switch (choix) {
                case 1:
                    ajouterReservation();
                    break;
                case 2:
                    rechercherReservationParId();
                    break;
                case 3:
                    afficherToutesReservations();
                    break;
                case 4:
                    modifierReservation();
                    break;
                case 5:
                    supprimerReservation();
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

 public static void menuSalle(){
         boolean retour = false;
        while (!retour) {
            System.out.println("\n+-----------------------------------------------+");
            System.out.println("|            GESTION DES SALLES          |");
            System.out.println("+-----------------------------------------------+");
            System.out.println("|  1. Ajouter une salle                        |");
            System.out.println("|  2. Chercher une salle                        |");
            System.out.println("|  3. Afficher les salles                       |");
            System.out.println("|  4. Modifier une salle                      |");
            System.out.println("|  5. Supprimer une salle                       |");
            System.out.println("|  0. Retour                      |");
            System.out.println("+-----------------------------------------------+");

            int choix = lireEntier("Votre choix : ");
            try {
                switch (choix) {
                    case 1:
                        ajouterSalle();
                        break;
                    case 2:
                        chercherSalle();
                        break;
                    case 3:
                        afficherToutesSalles();
                        break;
                    case 4:
                        modifierSalle();
                        break;
                    case 5:
                        supprimerSalle();
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

    public static void menuEnseignement(){
         boolean retour = false;
        while (!retour) {
            System.out.println("\n+-----------------------------------------------+");
            System.out.println("|            GESTION DES ENSEIGNEMENTS          |");
            System.out.println("+-----------------------------------------------+");
            System.out.println("|  1. Ajouter un cours                        |");
            System.out.println("|  2. Rechercher un cours par son intitulé    |");
            System.out.println("|  3. Afficher tous les cours                 |");
            System.out.println("|  4. Modifier un cours                       |");
            System.out.println("|  5. Supprimer un cours                      |");
            System.out.println("|  0. Retour au menu principal               |");
            System.out.println("+-----------------------------------------------+");

            int choix = lireEntier("Votre choix : ");
            try {
                switch (choix) {
                    case 1:
                        ajouterEnseignement();
                        break;
                    case 2:
                        rechercherCoursParIntitule();
                        break;
                    case 3:
                        afficherCours();
                        break;
                    case 4:
                        modifierEnseignement();
                        break;
                    case 5:
                        supprimerEnseignement();
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

    public static void menuInscription(){
         boolean retour = false;
        while (!retour) {
            System.out.println("\n+-----------------------------------------------+");
            System.out.println("|            GESTION DES INSCRIPTIONS          |");
            System.out.println("+-----------------------------------------------+");
            System.out.println("|  1. Inscrire un etudiant                    |");
            System.out.println("|  2. Rechercher une inscription par nom      |");
            System.out.println("|  3. Afficher toutes les inscriptions        |");
            System.out.println("|  4. Modifier une inscription                |");
            System.out.println("|  5. Supprimer une inscription               |");
            System.out.println("|  0. Retour au menu principal               |");
            System.out.println("+-----------------------------------------------+");

            int choix = lireEntier("Votre choix : ");
            try {
                switch (choix) {
                    case 1:
                        ajouterInscription();
                        break;
                    case 2:
                        rechercherInscriptionParNom();
                        break;
                    case 3:
                        afficherToutesInscriptions();
                        break;
                    case 4:
                        modifierInscription();
                        break;
                    case 5:
                        supprimerInscription();
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

    public static void menuNotes(){
         boolean retour = false;
        while (!retour) {
            System.out.println("\n+-----------------------------------------------+");
            System.out.println("|            GESTION DES NOTES                  |");
            System.out.println("+-----------------------------------------------+");
            System.out.println("|  1. Saisir une note                         |");
            System.out.println("|  2. Moyenne d'un etudiant dans un cours                   |");
            System.out.println("|  3. Toutes les notes                        |");
            System.out.println("|  4. Modifier une note                       |");
            System.out.println("|  5. Supprimer une note                      |");
            System.out.println("|  0. Retour au menu principal               |");
            System.out.println("+-----------------------------------------------+");

            int choix = lireEntier("Votre choix : ");
            try {
                switch (choix) {
                    case 1:
                        ajouterNote();
                        break;
                    case 2:
                        moyenneEnseignement();
                        break;
                    case 3:
                        afficherToutesNotes();
                        break;
                    case 4:
                        modifierNote();
                        break;
                    case 5:
                        supprimerNote();
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

    public static void menuEtudiants() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n+-----------------------------------------------+");
            System.out.println("|            GESTION DES ETUDIANTS              |");
            System.out.println("+-----------------------------------------------+");
            System.out.println("|  1. Ajouter un etudiant                     |");
            System.out.println("|  2. Rechercher un etudiant par nom          |");
            System.out.println("|  3. Afficher tous les etudiants             |");
            System.out.println("|  4. Modifier un etudiant                    |");
            System.out.println("|  5. Supprimer un etudiant                   |");
            System.out.println("|  0. Retour au menu principal               |");
            System.out.println("+-----------------------------------------------+");

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
    public static void ajouterEtudiant() throws SQLException {
        System.out.println("\n--- Ajouter un Etudiant ---");
        String nom = lireChaine("Nom : ");
        String prenom = lireChaine("Prenom : ");
        String email = lireChaine("Email : ");
        Etudiant etudiant = new Etudiant();
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        etudiant.setEmail(email);
        etudiantDAO.save(etudiant);
        System.out.println("Etudiant ajoute avec succes ! ID : " + etudiant.getId());
    }

    /**
     * Search student by name
     */
    public static void rechercherEtudiantParNom() throws SQLException {
        System.out.println("\n--- Rechercher un Etudiant ---");
        String nom = lireChaine("Nom a rechercher : ");
        List<Etudiant> etudiants = etudiantDAO.findByNom(nom);
        if (etudiants.isEmpty()) {
            System.out.println(" Aucun etudiant trouve avec le nom : " + nom);
        } else {
            System.out.println("\nEtudiants trouves :");
            afficherListeEtudiants(etudiants);
        }
    }

    /**
     * Display all students
     */
    public static void afficherTousEtudiants() throws SQLException {
        System.out.println("\n--- Liste de tous les Etudiants ---");
        List<Etudiant> etudiants = etudiantDAO.findAll();
        if (etudiants.isEmpty()) {
            System.out.println("Aucun etudiant dans la base de donnees.");
        } else {
            System.out.println("Nombre total d'etudiants : " + etudiants.size() + "\n");
            System.out.println("+------+-----------------+-----------------+--------------------------+");
            System.out.println("|  ID  |      Nom        |     Prenom      |          Email           |");
            System.out.println("+------+-----------------+-----------------+--------------------------+");
            for (Etudiant e : etudiants) {
                System.out.printf("| %-4d | %-15s | %-15s | %-24s |%n",
                        e.getId(),
                        e.getNom(),
                        e.getPrenom(),
                        e.getEmail());
            }
            System.out.println("+------+-----------------+-----------------+--------------------------+");
        }
    }

    /**
     * Update student information
     */
    public static void modifierEtudiant() throws SQLException {
        System.out.println("\n--- Modifier un Etudiant ---");
        int id = lireEntier("ID de l'etudiant a modifier : ");
        Etudiant etudiant = etudiantDAO.findById(id);
        if (etudiant == null) {
            System.out.println("Etudiant introuvable avec l'ID : " + id);
            return;
        }
        System.out.println("\nEtudiant actuel :");
        afficherEtudiant(etudiant);
        System.out.println("\nNouvelles informations (Entree pour conserver) :");
        String nom = lireChaine("Nouveau nom [" + etudiant.getNom() + "] : ");
        if (!nom.isEmpty()) etudiant.setNom(nom);
        String prenom = lireChaine("Nouveau prenom [" + etudiant.getPrenom() + "] : ");
        if (!prenom.isEmpty()) etudiant.setPrenom(prenom);
        String email = lireChaine("Nouveau email [" + etudiant.getEmail() + "] : ");
        if (!email.isEmpty()) etudiant.setEmail(email);
        etudiantDAO.update(etudiant);
        System.out.println("Etudiant modifie avec succes !");
    }

    /**
     * Delete a student
     */
    public static void supprimerEtudiant() throws SQLException {
        System.out.println("\n--- Supprimer un Etudiant ---");
        int id = lireEntier("ID de l'etudiant a supprimer : ");
        Etudiant etudiant = etudiantDAO.findById(id);
        if (etudiant == null) {
            System.out.println("Etudiant introuvable avec l'ID : " + id);
            return;
        }
        afficherEtudiant(etudiant);
        String confirmation = lireChaine("\nConfirmer la suppression ? (oui/non) : ");
        if (confirmation.equalsIgnoreCase("oui") || confirmation.equalsIgnoreCase("o")) {
            etudiantDAO.delete(id);
            System.out.println("Etudiant supprime avec succes !");
        } else {
            System.out.println("Suppression annulee.");
        }
    }

    // Lit un entier avec boucle de validation (ne rend que lorsqu'un entier valide est saisi)
    public static int lireEntier(String prompt) {
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

    // Lit une chaine (retourne eventuellement une chaine vide si l'utilisateur fait Entree)
    public static String lireChaine(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine();
        return line == null ? "" : line.trim();
    }

    // Affiche un etudiant sur plusieurs lignes (tolere les champs null)
    public static void afficherEtudiant(Etudiant e) {
        if (e == null) {
            System.out.println("(Aucun etudiant)");
            return;
        }
        String id     = String.valueOf(e.getId());
        String nom    = e.getNom()    == null ? "" : e.getNom();
        String prenom = e.getPrenom() == null ? "" : e.getPrenom();
        String email  = e.getEmail()  == null ? "" : e.getEmail();
        System.out.println("+-----------------------------------------------+");
        System.out.printf ("| ID     : %-35s |%n", id);
        System.out.printf ("| Nom    : %-35s |%n", nom);
        System.out.printf ("| Prenom : %-35s |%n", prenom);
        System.out.printf ("| Email  : %-35s |%n", email);
        System.out.println("+-----------------------------------------------+");
    }

    // Affiche une liste d'etudiants
    public static void afficherListeEtudiants(List<Etudiant> etudiants) {
        if (etudiants.isEmpty()) {
            System.out.println("Aucun etudiant trouve.");
            return;
        }
        System.out.println("+------+-----------------+-----------------+--------------------------+");
        System.out.println("|  ID  |      Nom        |     Prenom      |          Email           |");
        System.out.println("+------+-----------------+-----------------+--------------------------+");
        for (Etudiant e : etudiants) {
            System.out.printf("| %-4d | %-15s | %-15s | %-24s |%n",
                    e.getId(),
                    e.getNom(),
                    e.getPrenom(),
                    e.getEmail());
        }
        System.out.println("+------+-----------------+-----------------+--------------------------+");
    }

    /*Afficher une liste d'enseignements */

    public static void afficherEnseignement(Enseignement e) {
        if (e == null) {
            System.out.println("(Aucun enseignement)");
            return;
        }
        String id = String.valueOf(e.getId());
        String intitule = e.getIntitule() == null ? "" : e.getIntitule();
        String descr = e.getDescription() == null ? "" : e.getDescription();
        String obligatoire = e.isObligatoire() == null ? "" : e.isObligatoire();
        String enseignant = e.getEnseignant().getNom() == null ? "" : e.getEnseignant().getNom();
        String master = e.getMaster().getNom() == null ? "" : e.getMaster().getNom();
        System.out.println("+-----------------------------------------------+");
        System.out.printf ("| ID     : %-35s |%n", id);
        System.out.printf ("| Intitule    : %-35s |%n", intitule);
        System.out.printf ("| Description    : %-35s |%n", descr);
        System.out.printf ("| Obligatoire    : %-35s |%n", obligatoire);
        System.out.printf ("| Enseignant    : %-35s |%n", enseignant);
        System.out.printf ("| Master    : %-35s |%n", master);
        System.out.println("+-----------------------------------------------+");
    }



   public static void afficherListeEnseignements(List<Enseignement> enseignements) {
        if (enseignements.isEmpty()) {
            System.out.println("Aucun cours trouve.");
            return;
        }
        System.out.println("+------+------------------------------------+------------------------------------+----------------------+-------------------+-----------------+");
        System.out.println("|  ID  |            Intitule                |           Description             |      Obligatoire     |    Enseignant      |    Master       |");
        System.out.println("+------+------------------------------------+------------------------------------+----------------------+-------------------+-----------------+");
        for (Enseignement e : enseignements) {
            System.out.printf(
                 "| %-4d | %-34s | %-34s | %-20s | %-17s | %-15s |%n",
                    e.getId(),
                    e.getIntitule(),
                    e.getDescription(),
                    e.isObligatoire(),
                    e.getEnseignant().getNom() + " " +  e.getEnseignant().getPrenom(),
                    e.getMaster().getNom()
                );
        }
        System.out.println("+------+-----------------+-----------------+----------------------+-------------------+-----------------+");
    }

    public static void afficherListeInscriptions(List<Inscription> inscriptions) {
    if (inscriptions.isEmpty()) {
        System.out.println("Aucune inscription trouvée.");
        return;
    }

    // En-tête du tableau
    System.out.println("+------+-----------------------+------------------------------------+-----------------+");
    System.out.println("|  ID  |       Étudiant        |            Intitulé                |    Master       |");
    System.out.println("+------+-----------------------+------------------------------------+-----------------+");

    // Boucle pour afficher chaque inscription
    for (Inscription i : inscriptions) {
        System.out.printf(
            "| %-4d | %-21s | %-34s | %-15s |%n",
            i.getId(), // ID de l'inscription
            i.getEtudiant().getNom() + " " + i.getEtudiant().getPrenom(), // Nom et prénom de l'étudiant
            i.getEnseignement().getIntitule(), // Intitulé du cours
            i.getEnseignement().getMaster().getNom() // Nom du master
        );
    }

    // Ligne de séparation finale
    System.out.println("+------+-----------------------+------------------------------------+-----------------+");
}
   
public static void afficherListeNotes(List<Note> notes) {
    if (notes == null || notes.isEmpty()) {
        System.out.println("Aucune note trouvée.");
        return;
    }

    // En-tête du tableau
    System.out.println("+------+-----------------------+------------------------------------+--------+");
    System.out.println("|  ID  |       Étudiant        |          Enseignement             |  Note  |");
    System.out.println("+------+-----------------------+------------------------------------+--------+");

    // Boucle pour afficher chaque note
    for (Note note : notes) {
        // Récupération des objets liés
        Etudiant etudiant = note.getEtudiant();
        Enseignement enseignement = note.getEnseignement();

        // Affichage formaté
        System.out.printf(
            "| %-4d | %-21s | %-34s | %-6.2f |%n",
            note.getId(), // ID de la note
            etudiant.getNom() + " " + etudiant.getPrenom(), // Nom et prénom de l'étudiant
            enseignement.getIntitule(), // Nom de l'enseignement
            note.getNote() // Valeur de la note
        );
    }

    // Ligne de fermeture du tableau
    System.out.println("+------+-----------------------+------------------------------------+--------+");
}

public static void afficherNote(Note note) {
    if (note == null) {
        System.out.println("(Aucune note)");
        return;
    }

    // Récupération des informations avec gestion des valeurs null
    String idNote = String.valueOf(note.getId());

    String etudiant = (note.getEtudiant() == null ||
                       note.getEtudiant().getNom() == null ||
                       note.getEtudiant().getPrenom() == null)
                      ? "" : note.getEtudiant().getNom() + " " + note.getEtudiant().getPrenom();

    String enseignement = (note.getEnseignement() == null ||
                           note.getEnseignement().getIntitule() == null)
                          ? "" : note.getEnseignement().getIntitule();

    String valeur = (note.getNote() == 0.0)
                   ? "" : String.format("%.2f", note.getNote());

    // Affichage formaté
    System.out.println("+-----------------------------------------------+");
    System.out.printf("| ID note         : %-35s |%n", idNote);
    System.out.printf("| Étudiant        : %-35s |%n", etudiant);
    System.out.printf("| Enseignement    : %-35s |%n", enseignement);
    System.out.printf("| Valeur          : %-35s |%n", valeur);
    System.out.println("+-----------------------------------------------+");
}


public static void afficherInscription(Inscription i) {
    if (i == null) {
        System.out.println("(Aucune inscription)");
        return;
    }

    // Récupération des informations avec gestion des valeurs null
    String id = String.valueOf(i.getId());
    String etudiant = (i.getEtudiant() == null || i.getEtudiant().getNom() == null || i.getEtudiant().getPrenom() == null)
                      ? "" : i.getEtudiant().getNom() + " " + i.getEtudiant().getPrenom();
    String intitule = (i.getEnseignement() == null || i.getEnseignement().getIntitule() == null)
                      ? "" : i.getEnseignement().getIntitule();
    String master = (i.getEnseignement() == null || i.getEnseignement().getMaster() == null || i.getEnseignement().getMaster().getNom() == null)
                    ? "" : i.getEnseignement().getMaster().getNom();

    // Affichage formaté
    System.out.println("+-----------------------------------------------+");
    System.out.printf("| ID inscription : %-35s |%n", id);
    System.out.printf("| Étudiant        : %-35s |%n", etudiant);
    System.out.printf("| Intitulé cours  : %-35s |%n", intitule);
    System.out.printf("| Master          : %-35s |%n", master);
    System.out.println("+-----------------------------------------------+");
}

public static void afficherListeSalles(List<Salle> salles) {
    if (salles == null || salles.isEmpty()) {
        System.out.println("Aucune salle trouvée.");
        return;
    }

    System.out.println("+------+-----------------+-----------------+");
    System.out.println("| Bâtiment | Numéro Salle | Capacité       |");
    System.out.println("+------+-----------------+-----------------+");

    for (Salle salle : salles) {
        System.out.printf("| %-6s | %-13s | %-15d |%n",
                salle.getBatiment(),
                salle.getNumSalle(),
                salle.getCapacite());
    }

    System.out.println("+------+-----------------+-----------------+");
}
public static void afficherSalle(Salle salle) {
    if (salle == null) {
        System.out.println("(Aucune salle)");
        return;
    }

    String batiment = salle.getBatiment() == null ? "" : salle.getBatiment();
    String numSalle = salle.getNumSalle() == null ? "" : salle.getNumSalle();
    String capacite = String.valueOf(salle.getCapacite());

    System.out.println("+-----------------------------------------------+");
    System.out.printf("| Bâtiment      : %-35s |%n", batiment);
    System.out.printf("| Numéro salle  : %-35s |%n", numSalle);
    System.out.printf("| Capacité      : %-35s |%n", capacite);
    System.out.println("+-----------------------------------------------+");
}


    
    /* Enseignement : ajouter un cours */

     public static void ajouterEnseignement() throws SQLException {
        System.out.println("\n--- Ajouter un Enseignement ---");
        Integer id = lireEntier("Id de l'enseignement : ");
        String intitule = lireChaine("Intitule : ");
        String descr = lireChaine("Description : ");
        String obligatoire = lireChaine("Obligatoire ? (OUI-NON) : ");
        Integer enseignantId = lireEntier("Id de l'enseignant : ");
        String enseignantNom = lireChaine("Nom : ");
        String enseignantPrenom = lireChaine("Prenom : ");
        Integer masterId = lireEntier("Id du Master : ");
        String nomMaster = lireChaine("Nom du Master : ");

        Enseignement enseignement = new Enseignement(id,intitule,descr,obligatoire,new Enseignant(enseignantId,enseignantNom,enseignantPrenom),new Master(masterId,nomMaster));
        
        enseignementDAO.save(enseignement);
        System.out.println("Enseignement ajoute avec succes ! ID : " + enseignement.getId());
    }

    public static void rechercherCoursParIntitule() throws SQLException {
        System.out.println("\n--- Rechercher un Enseignement ---");
        String intitule = lireChaine("Cours a rechercher : ");
        List<Enseignement> enseignements = enseignementDAO.findByIntitule(intitule);
        if (enseignements.isEmpty()) {
            System.out.println(" Aucun cours trouve avec l'intitule : " + intitule);
        } else {
            System.out.println("\nCours trouves :");
            afficherListeEnseignements(enseignements);
        }
    }

    public static void afficherCours() throws SQLException {
        System.out.println("\n--- Tout les cours : ---");
        List<Enseignement> enseignements = enseignementDAO.findAll();
        if (enseignements.isEmpty()) {
            System.out.println(" Aucun cours enregistres dans la base.");
        } else {
            System.out.println("\nCours trouves :");
            afficherListeEnseignements(enseignements);
        }
    }
    
     public static void modifierEnseignement() throws SQLException {
        System.out.println("\n--- Modifier un Enseignement ---");
        int id = lireEntier("ID du cours a modifier : ");
        Enseignement enseignement = enseignementDAO.findById(id);
        if (enseignement == null) {
            System.out.println("Cours introuvable avec l'ID : " + id);
            return;
        }
        System.out.println("\nEnseignement actuel :");
        afficherEnseignement(enseignement);
        System.out.println("\nNouvelles informations (Entree pour conserver) :");
        String intitule =  lireChaine("Nouveau intitule [" + enseignement.getIntitule() + "] : ");
        if(!intitule.isEmpty()) enseignement.setIntitule(intitule);
        String descr =  lireChaine("Nouvelle description [" + enseignement.getDescription() + "] : ");
        if(!descr.isEmpty()) enseignement.setDescription(descr);
        String obligatoire =  lireChaine("Obligatoire [" + enseignement.isObligatoire() + "] : ");
        if(!obligatoire.isEmpty()) enseignement.setObligatoire(obligatoire);
        String enseignant =  lireChaine("Id enseignant [" + enseignement.getEnseignant().getId() + "] : ");
        if(!enseignant.isEmpty()) enseignement.getEnseignant().setId(Integer.parseInt(enseignant));
        String master =  lireChaine("Id master [" + enseignement.getMaster().getId() + "] : ");
        if(!master.isEmpty()) enseignement.getMaster().setId(Integer.parseInt(master));
    
        enseignementDAO.update(enseignement);
        System.out.println("Enseignement modifie avec succes !");
    }

     public static void supprimerEnseignement() throws SQLException {
        System.out.println("\n--- Supprimer un Enseignement ---");
        int id = lireEntier("ID du cours a supprimer : ");
        Enseignement e = enseignementDAO.findById(id);
        if (e == null) {
            System.out.println("Cours introuvable avec l'ID : " + id);
            return;
        }
        afficherEnseignement(e);
        String confirmation = lireChaine("\nConfirmer la suppression ? (oui/non) : ");
        if (confirmation.equalsIgnoreCase("oui") || confirmation.equalsIgnoreCase("o")) {
            enseignementDAO.delete(id);
            System.out.println("Cours supprime avec succes !");
        } else {
            System.out.println("Suppression annulee.");
        }
    }

    /* Inscriptions -> méthodes */

    public static void ajouterInscription() throws SQLException {
        System.out.println("\n--- Ajouter une inscription ---");
        Integer id = lireEntier("Id de l'inscription : ");
        Integer etudiant = lireEntier("Id de l'etudiant : ");
        Integer enseignement = lireEntier("Id de l'enseignement  : ");
        Integer master = lireEntier("Id du master  : ");

        Inscription inscr = new Inscription(id, new Etudiant(etudiant,"",""), new Enseignement(enseignement), new Master(master,""));

        inscriptionDAO.save(inscr);
        System.out.println("Inscription ajoutee avec succes ! ID : " + inscr.getId());
    }

    public static void rechercherInscriptionParNom() throws SQLException {
        System.out.println("\n--- Rechercher une Inscription ---");
        String nom = lireChaine("Nom de l'etudiant : ");
        List<Inscription> inscriptions = inscriptionDAO.findByName(nom);
        if (inscriptions.isEmpty()) {
            System.out.println(" Aucune inscription trouvee pour l'eleve : " + nom);
        } else {
            System.out.println("\nCours trouves :");
            afficherListeInscriptions(inscriptions);
        }
    }

    public static void afficherToutesInscriptions() throws SQLException {
        System.out.println("\n--- Toutes les inscriptions : ---");
        List<Inscription> inscriptions = inscriptionDAO.findAll();
        if (inscriptions.isEmpty()) {
            System.out.println(" Aucune inscription enregistree dans la base.");
        } else {
            System.out.println("\nInscriptions trouvees :");
            afficherListeInscriptions(inscriptions);
        }
    }

    public static void modifierInscription() throws SQLException {
        System.out.println("\n--- Modifier une inscription ---");
        int id = lireEntier("ID de l'inscription a modifier : ");
        Inscription inscription = inscriptionDAO.findById(id);
        if (inscription == null) {
            System.out.println("Inscription introuvable avec l'ID : " + id);
            return;
        }
        System.out.println("\nInscription actuelle :");
        afficherInscription(inscription);
        System.out.println("\nNouvelles informations (Entree pour conserver) :");
        String etudiant =  lireChaine("Id etudiant [" + inscription.getEtudiant().getId() + "] : ");
        if(!etudiant.isEmpty()) inscription.getEtudiant().setId(Integer.parseInt(etudiant));
        String enseignement =  lireChaine("Id enseignement [" + inscription.getEnseignement().getId() + "] : ");
        if(!enseignement.isEmpty()) inscription.getEnseignement().setId(Integer.parseInt(enseignement));
        String master =  lireChaine("Id master [" + inscription.getEnseignement().getMaster().getId() + "] : ");
        if(!master.isEmpty()) inscription.getEnseignement().getMaster().setId(Integer.parseInt(enseignement));
        

        inscriptionDAO.update(inscription);
        System.out.println("Enseignement modifie avec succes !");
    }

    public static void supprimerInscription() throws SQLException {
        System.out.println("\n--- Supprimer une Inscription ---");
        int id = lireEntier("ID de l'inscription a supprimer : ");
        Inscription e = inscriptionDAO.findById(id);
        if (e == null) {
            System.out.println("Cours introuvable avec l'ID : " + id);
            return;
        }
        afficherInscription(e);
        String confirmation = lireChaine("\nConfirmer la suppression ? (oui/non) : ");
        if (confirmation.equalsIgnoreCase("oui") || confirmation.equalsIgnoreCase("o")) {
            inscriptionDAO.delete(id);
            System.out.println("Cours supprime avec succes !");
        } else {
            System.out.println("Suppression annulee.");
        }
    }

    /* ajouter notes */

    public static void ajouterNote() throws SQLException{
        System.out.println("\n--- Ajouter une note ---");
        Integer id = lireEntier("Id de la note : ");
        Integer etudiant = lireEntier("Id de l'etudiant : ");
        Integer enseignement = lireEntier("Id de l'enseignement  : ");
        
        Integer note = lireEntier("Valeur de la note  : ");

        Note n = new Note(id,new Etudiant(etudiant,"",""),new Enseignement(enseignement), note);

        noteDAO.save(n);
        System.out.println("Note ajoutee avec succes ! ID : " + n.getId());
    

    }

    public static void moyenneEnseignement() throws SQLException {
        System.out.println("\n--- Moyenne d'un etudiant dans un cours ---");
        Integer idEtudiant = lireEntier("Id de l'etudiant : ");
        Integer idEnseignement = lireEntier("Id de l'enseignement : ");
        double moyenne = noteDAO.moyenneEnseignement(idEtudiant, idEnseignement);
        if (moyenne==0) {
            System.out.println(" Aucune moyenne pour ce cours ou bien 0 de moyenne ");
        } else {
            System.out.println("\nMoyenne pour ce cours : " + moyenne);
        }
    }

    public static void afficherToutesNotes() throws SQLException {
        System.out.println("\n--- Toutes les notes : ---");
        List<Note> notes = noteDAO.findAll();
        if (notes.isEmpty()) {
            System.out.println(" Aucune note enregistree dans la base.");
        } else {
            System.out.println("\nNotes :");
            afficherListeNotes(notes);
        }
    }

    public static void modifierNote() throws SQLException {
        System.out.println("\n--- Modifier une note ---");
        int id = lireEntier("ID de la note a modifier : ");
        Note note = noteDAO.findById(id);
        if (note == null) {
            System.out.println("Note introuvable avec l'ID : " + id);
            return;
        }
        System.out.println("\nInscription actuelle :");
        afficherNote(note);
        System.out.println("\nNouvelles informations (Entree pour conserver) :");
        String etudiant =  lireChaine("Id etudiant [" + note.getEtudiant().getId() + "] : ");
        if(!etudiant.isEmpty()) note.getEtudiant().setId(Integer.parseInt(etudiant));
        String enseignement =  lireChaine("Id enseignement [" + note.getEnseignement().getId() + "] : ");
        if(!enseignement.isEmpty()) note.getEnseignement().setId(Integer.parseInt(enseignement));
        String n =  lireChaine("Valeur note [" + note.getNote() + "] : ");
        if(!n.isEmpty()) note.setNote(Integer.parseInt(enseignement));
        

        noteDAO.update(note);
        System.out.println("Note modifiee avec succes !");
    }

     public static void supprimerNote() throws SQLException {
        System.out.println("\n--- Supprimer une Note ---");
        int id = lireEntier("ID de la note a supprimer : ");
        Note n = noteDAO.findById(id);
        if (n == null) {
            System.out.println("Cours introuvable avec l'ID : " + id);
            return;
        }
        afficherNote(n);
        String confirmation = lireChaine("\nConfirmer la suppression ? (oui/non) : ");
        if (confirmation.equalsIgnoreCase("oui") || confirmation.equalsIgnoreCase("o")) {
            noteDAO.delete(id);
            System.out.println("Note supprimee avec succes !");
        } else {
            System.out.println("Suppression annulee.");
        }
    }

    /* Salle DAO */

    public static void ajouterSalle() throws SQLException{
        System.out.println("\n--- Ajouter une salle ---");
        String bat = lireChaine("Batiment : ");
        String numSalle = lireChaine("Numero de salle : ");
        Integer capacite = lireEntier("Capacite : ");

        Salle s = new Salle(bat,numSalle,capacite);

        salleDAO.save(s);
        System.out.println("Salle ajoutee avec succes ! ID : " + s.getNumSalle());
    

    }

    public static void chercherSalle() throws SQLException {
        System.out.println("\n--- Salle ---");
        String numSalle = lireChaine("Numero de la salle : ");
        String numBat = lireChaine("Batiment : ");
        Salle s = salleDAO.findById(numBat, numSalle);
        if (s==null) {
            System.out.println(" Aucune salle trouvee ");
        } else {
            System.out.println("\nInfo salle :  " );
            afficherSalle(s);
        }
    }



    public static void afficherToutesSalles() throws SQLException {
        System.out.println("\n--- Toutes les salles : ---");
        List<Salle> salles = salleDAO.findAll();
        if (salles.isEmpty()) {
            System.out.println(" Aucune salle enregistree dans la base.");
        } else {
            System.out.println("\nSalles :");
            afficherListeSalles(salles);
        }
    }

    public static void modifierSalle() throws SQLException {
        System.out.println("\n--- Modifier une Salle ---");
        String bat = lireChaine("Num bat : ");
        String salle = lireChaine("Num salle : ");
        Salle s = salleDAO.findById(bat,salle);
        if (s == null) {
            System.out.println("SALLE introuvable ");
            return;
        }
        System.out.println("\nSalle actuelle :");
        afficherSalle(s);
        System.out.println("\nNouvelles informations (Entree pour conserver) :");
        bat =  lireChaine("Bat [" + s.getBatiment()+ "] : ");
        if(!bat.isEmpty()) s.setBatiment(bat);
        salle =  lireChaine("Salle [" + s.getNumSalle()+ "] : ");
        if(!salle.isEmpty()) s.setNumSalle(salle);
        String capacite =  lireChaine("CApacite [" + s.getCapacite() + "] : ");
        if(!capacite.isEmpty()) s.setCapacite((Integer.parseInt(capacite)));
    

        salleDAO.update(s);
        System.out.println("Salle modifiee avec succes !");
    }

     public static void supprimerSalle() throws SQLException {
        System.out.println("\n--- Supprimer une salle ---");
        String numSalle = lireChaine("Numero de la salle : ");
        String numBat = lireChaine("Batiment : ");
        Salle s = salleDAO.findById(numBat, numSalle);
        
        if (s == null) {
            System.out.println("salle introuvable ");
            return;
        }
        afficherSalle(s);
        String confirmation = lireChaine("\nConfirmer la suppression ? (oui/non) : ");
        if (confirmation.equalsIgnoreCase("oui") || confirmation.equalsIgnoreCase("o")) {
            salleDAO.delete(numBat,numSalle);
            System.out.println("Salle supprimee avec succes !");
        } else {
            System.out.println("Suppression annulee.");
        }
    }

    



    
    

public static void ajouterReservation() throws SQLException {
    System.out.println("\n--- Ajouter une Réservation ---");
    String batiment = lireChaine("Bâtiment : ");
    String numSalle = lireChaine("Numéro de salle : ");
    int enseignementId = lireEntier("ID de l'enseignement : ");
    int enseignantId = lireEntier("ID de l'enseignant : ");
    int masterId = lireEntier("ID du master : ");
    LocalDateTime dateResa = lireDateHeure("Date de réservation (format : yyyy-MM-dd HH:mm) : ");
    int nbHeures = lireEntier("Nombre d'heures : ");

    LocalDateTime dateFin = dateResa.plusHours(nbHeures);

    Salle salle = new Salle(batiment, numSalle, 0);
    Enseignant enseignant = new Enseignant(enseignantId, "", "", "", "", "", "");
    Enseignement enseignement = new Enseignement(enseignementId, "", "", "", enseignant, new Master(masterId, ""));

    Reservation reservation = new Reservation();
    reservation.setSalle(salle);
    reservation.setEnseignement(enseignement);
    reservation.setDebut(dateResa);
    reservation.setFin(dateFin);
    reservation.setNbHeures(nbHeures);

    reservationDAO.save(reservation);
    System.out.println("Réservation ajoutée avec succès ! ID : " + reservation.getId());
}
public static void rechercherReservationParId() throws SQLException {
    System.out.println("\n--- Rechercher une Réservation ---");
    int id = lireEntier("ID de la réservation à rechercher : ");
    Reservation reservation = reservationDAO.findById(id);
    if (reservation == null) {
        System.out.println("Aucune réservation trouvée avec l'ID : " + id);
    } else {
        System.out.println("\nRéservation trouvée :");
        afficherReservation(reservation);
    }
}
public static void afficherToutesReservations() throws SQLException {
    System.out.println("\n--- Liste de toutes les Réservations ---");
    List<Reservation> reservations = reservationDAO.findAll();
    if (reservations.isEmpty()) {
        System.out.println("Aucune réservation dans la base de données.");
    } else {
        System.out.println("Nombre total de réservations : " + reservations.size() + "\n");
        afficherListeReservations(reservations);
    }
}
public static void modifierReservation() throws SQLException {
    System.out.println("\n--- Modifier une Réservation ---");
    int id = lireEntier("ID de la réservation à modifier : ");
    Reservation reservation = reservationDAO.findById(id);
    if (reservation == null) {
        System.out.println("Réservation introuvable avec l'ID : " + id);
        return;
    }
    System.out.println("\nRéservation actuelle :");
    afficherReservation(reservation);
    System.out.println("\nNouvelles informations (Entree pour conserver) :");

    String batiment = lireChaine("Nouveau bâtiment [" + reservation.getSalle().getBatiment() + "] : ");
    if (!batiment.isEmpty()) reservation.getSalle().setBatiment(batiment);

    String numSalle = lireChaine("Nouveau numéro de salle [" + reservation.getSalle().getNumSalle() + "] : ");
    if (!numSalle.isEmpty()) reservation.getSalle().setNumSalle(numSalle);

    String enseignementIdStr = lireChaine("Nouvel ID de l'enseignement [" + reservation.getEnseignement().getId() + "] : ");
    if (!enseignementIdStr.isEmpty()) reservation.getEnseignement().setId(Integer.parseInt(enseignementIdStr));

    String enseignantIdStr = lireChaine("Nouvel ID de l'enseignant [" + reservation.getEnseignement().getEnseignant().getId() + "] : ");
    if (!enseignantIdStr.isEmpty()) reservation.getEnseignement().getEnseignant().setId(Integer.parseInt(enseignantIdStr));

    String dateResaStr = lireChaine("Nouvelle date de réservation [" + reservation.getDebut().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "] (format : yyyy-MM-dd HH:mm) : ");
    if (!dateResaStr.isEmpty()) {
        LocalDateTime newDateResa = LocalDateTime.parse(dateResaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        reservation.setDebut(newDateResa);
        reservation.setFin(newDateResa.plusHours(reservation.getNbHeures()));
    }

    String nbHeuresStr = lireChaine("Nouveau nombre d'heures [" + reservation.getNbHeures() + "] : ");
    if (!nbHeuresStr.isEmpty()) {
        int newNbHeures = Integer.parseInt(nbHeuresStr);
        reservation.setNbHeures(newNbHeures);
        reservation.setFin(reservation.getDebut().plusHours(newNbHeures));
    }

    reservationDAO.update(reservation);
    System.out.println("Réservation modifiée avec succès !");
}

public static void supprimerReservation() throws SQLException {
    System.out.println("\n--- Supprimer une Réservation ---");
    int id = lireEntier("ID de la réservation à supprimer : ");
    Reservation reservation = reservationDAO.findById(id);
    if (reservation == null) {
        System.out.println("Réservation introuvable avec l'ID : " + id);
        return;
    }
    afficherReservation(reservation);
    String confirmation = lireChaine("\nConfirmer la suppression ? (oui/non) : ");
    if (confirmation.equalsIgnoreCase("oui") || confirmation.equalsIgnoreCase("o")) {
        reservationDAO.delete(id);
        System.out.println("Réservation supprimée avec succès !");
    } else {
        System.out.println("Suppression annulée.");
    }
}
public static LocalDateTime lireDateHeure(String prompt) {
    while (true) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        try {
            return LocalDateTime.parse(line, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception ex) {
            System.out.println("Format invalide. Veuillez utiliser le format : yyyy-MM-dd HH:mm.");
        }
    }
}
public static void afficherReservation(Reservation reservation) {
    if (reservation == null) {
        System.out.println("(Aucune réservation)");
        return;
    }

    String id = String.valueOf(reservation.getId());
    String batiment = reservation.getSalle().getBatiment() == null ? "" : reservation.getSalle().getBatiment();
    String numSalle = reservation.getSalle().getNumSalle() == null ? "" : reservation.getSalle().getNumSalle();
    String enseignement = reservation.getEnseignement().getIntitule() == null ? "" : reservation.getEnseignement().getIntitule();
    String enseignant = reservation.getEnseignement().getEnseignant() == null ? "" :
                        reservation.getEnseignement().getEnseignant().getNom() + " " +
                        reservation.getEnseignement().getEnseignant().getPrenom();
    String dateDebut = reservation.getDebut() == null ? "" : reservation.getDebut().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    String dateFin = reservation.getFin() == null ? "" : reservation.getFin().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    String nbHeures = String.valueOf(reservation.getNbHeures());

    System.out.println("+-----------------------------------------------+");
    System.out.printf("| ID Réservation   : %-30s |%n", id);
    System.out.printf("| Bâtiment          : %-30s |%n", batiment);
    System.out.printf("| Numéro Salle      : %-30s |%n", numSalle);
    System.out.printf("| Enseignement      : %-30s |%n", enseignement);
    System.out.printf("| Enseignant        : %-30s |%n", enseignant);
    System.out.printf("| Date Début        : %-30s |%n", dateDebut);
    System.out.printf("| Date Fin          : %-30s |%n", dateFin);
    System.out.printf("| Nombre Heures     : %-30s |%n", nbHeures);
    System.out.println("+-----------------------------------------------+");
}

public static void afficherListeReservations(List<Reservation> reservations) {
    if (reservations.isEmpty()) {
        System.out.println("Aucune réservation trouvée.");
        return;
    }

    System.out.println("+------+-----------------+-----------------+------------------------------------+----------------------------+----------------------------+-----------------+----------------------------+");
    System.out.println("|  ID  |    Bâtiment     |   Numéro Salle  |            Enseignement             |        Enseignant          |        Date Début         |          Date Fin         | Nombre Heures |");
    System.out.println("+------+-----------------+-----------------+------------------------------------+----------------------------+----------------------------+----------------------------+-----------------+");

    for (Reservation reservation : reservations) {
        String enseignant = reservation.getEnseignement().getEnseignant() == null ? "" :
                            reservation.getEnseignement().getEnseignant().getNom() + " " +
                            reservation.getEnseignement().getEnseignant().getPrenom();

        System.out.printf("| %-4d | %-15s | %-15s | %-34s | %-26s | %-26s | %-26s | %-15d |%n",
                reservation.getId(),
                reservation.getSalle().getBatiment(),
                reservation.getSalle().getNumSalle(),
                reservation.getEnseignement().getIntitule(),
                enseignant,
                reservation.getDebut().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                reservation.getFin().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                reservation.getNbHeures());
    }

    System.out.println("+------+-----------------+-----------------+------------------------------------+----------------------------+----------------------------+----------------------------+-----------------+");
}



}
