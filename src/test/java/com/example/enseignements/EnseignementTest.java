import main.java.com.example.GestionUniversitaire;
import main.java.com.example.dao.EnseignementDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class EnseignementTest {

    @BeforeEach
    void setup() throws Exception {
        GestionUniversitaire.enseignementDAO = new EnseignementDAO();
    }


    // -------------------------------------------------------
    // 1) AJOUTER ENSEIGNEMENT (Should Work)
    // -------------------------------------------------------
    @Test
    void ajouterEnseignementShouldWork() {

        int idRandom = (int)(Math.random() * 900000 + 100000); // ID UNIQUE

        String input =
            idRandom + "\n" +
            "Cours Test\n" +
            "Description test\n" +
            "OUI\n" +
            "1\n" +
            "DOE\n" +
            "John\n" +
            "1\n" +
            "MASTER TEST\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        GestionUniversitaire.scanner = new Scanner(System.in);

        assertDoesNotThrow(() -> GestionUniversitaire.ajouterEnseignement());

        System.setIn(System.in);
    }


    // -------------------------------------------------------
    // 2) AJOUTER ENSEIGNEMENT (Should NOT Work – FK Invalides)
    // -------------------------------------------------------
    @Test
    void ajouterEnseignementShouldNotWork_InvalidFK() {

        String input =
            "999999\n" +
            "Test\n" +
            "Desc\n" +
            "OUI\n" +
            "9999\n" +
            "AAA\n" +
            "BBB\n" +
            "9999\n" +
            "MASTER\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        GestionUniversitaire.scanner = new Scanner(System.in);

        assertThrows(SQLException.class, () -> GestionUniversitaire.ajouterEnseignement());

        System.setIn(System.in);
    }


    // -------------------------------------------------------
    // 3) RECHERCHE PAR INTITULÉ (Should Work)
    // -------------------------------------------------------
    @Test
    void rechercherCoursParIntituleShouldWork() throws Exception {

        String input = "Math\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        GestionUniversitaire.scanner = new Scanner(System.in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        assertDoesNotThrow(() -> GestionUniversitaire.rechercherCoursParIntitule());

        assertTrue(out.toString().contains("Cours trouves :")
                || out.toString().contains("Aucun cours trouve"));

        System.setOut(System.out);
        System.setIn(System.in);
    }


    // -------------------------------------------------------
    // 4) RECHERCHE PAR INTITULÉ (Should NOT Work)
    // -------------------------------------------------------
    @Test
    void rechercherCoursParIntituleShouldNotWork() throws Exception {

        String input = "INTITULEQUIEXISTEPASDU TOUT\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        GestionUniversitaire.scanner = new Scanner(System.in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        assertDoesNotThrow(() -> GestionUniversitaire.rechercherCoursParIntitule());

        assertTrue(out.toString().contains("Aucun cours trouve"));

        System.setOut(System.out);
        System.setIn(System.in);
    }


    // -------------------------------------------------------
    // 5) AFFICHER TOUS LES COURS (Should Work)
    // -------------------------------------------------------
    @Test
    void afficherCoursShouldWork() throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        assertDoesNotThrow(() -> GestionUniversitaire.afficherCours());

        assertTrue(out.toString().contains("Cours trouves")
                || out.toString().contains("Aucun cours enregistres"));

        System.setOut(System.out);
    }


    // -------------------------------------------------------
    // 6) MODIFIER ENSEIGNEMENT (Should Work)
    // -------------------------------------------------------
    @Test
    void modifierEnseignementShouldWork() throws Exception {

        String input =
            "1\n" +    // ID existant
            "\n\n\n\n\n"; // pas de modifications

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        GestionUniversitaire.scanner = new Scanner(System.in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        assertDoesNotThrow(() -> GestionUniversitaire.modifierEnseignement());

        assertTrue(out.toString().contains("Enseignement modifie"));

        System.setOut(System.out);
        System.setIn(System.in);
    }


    // -------------------------------------------------------
    // 7) MODIFIER ENSEIGNEMENT (Should NOT Work – ID absent)
    // -------------------------------------------------------
    @Test
    void modifierEnseignementShouldNotWork() throws Exception {

        String input = "999999\n"; // ID inexistant
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        GestionUniversitaire.scanner = new Scanner(System.in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        assertDoesNotThrow(() -> GestionUniversitaire.modifierEnseignement());

        assertTrue(out.toString().contains("introuvable"));

        System.setOut(System.out);
        System.setIn(System.in);
    }


    // -------------------------------------------------------
    // 8) SUPPRIMER ENSEIGNEMENT (Should Work – ID Aléatoire)
    // -------------------------------------------------------
    @Test
    void supprimerEnseignementShouldWork() throws Exception {

        int idRandom = (int)(Math.random() * 900000 + 100000);

        // 1) INSERTION du cours qui SERA supprimé
        String insertInput =
            idRandom + "\nTest\nDesc\nOUI\n1\nDoe\nJohn\n1\nMaster\n";

        System.setIn(new ByteArrayInputStream(insertInput.getBytes()));
        GestionUniversitaire.scanner = new Scanner(System.in);

        GestionUniversitaire.ajouterEnseignement();


        // 2) SUPPRESSION du même cours
        String deleteInput =
            idRandom + "\noui\n";

        System.setIn(new ByteArrayInputStream(deleteInput.getBytes()));
        GestionUniversitaire.scanner = new Scanner(System.in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        assertDoesNotThrow(() -> GestionUniversitaire.supprimerEnseignement());

        assertTrue(out.toString().contains("supprime"));

        System.setOut(System.out);
        System.setIn(System.in);
    }


    // -------------------------------------------------------
    // 9) SUPPRIMER ENSEIGNEMENT (Should NOT Work)
    // -------------------------------------------------------
    @Test
    void supprimerEnseignementShouldNotWork() throws Exception {

        String input = "999999\nyes\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        GestionUniversitaire.scanner = new Scanner(System.in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        assertDoesNotThrow(() -> GestionUniversitaire.supprimerEnseignement());

        assertTrue(out.toString().contains("introuvable"));

        System.setOut(System.out);
        System.setIn(System.in);
    }
}
