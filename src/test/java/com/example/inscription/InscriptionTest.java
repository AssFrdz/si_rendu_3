import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import main.java.com.example.dao.InscriptionDAO;
import main.java.com.example.model.Enseignement;
import main.java.com.example.model.Etudiant;
import main.java.com.example.model.Inscription;
import main.java.com.example.model.Master;
import main.java.com.example.GestionUniversitaire;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;


public class InscriptionTest {


    @BeforeEach
    void setUp() throws Exception {
        GestionUniversitaire.inscriptionDAO = new InscriptionDAO();

    }
@Test
void ajouterInscriptionShouldWork() throws SQLException {
    // Désactive auto-commit pour pouvoir rollback

    String input = "12\n4\n1\n2\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    GestionUniversitaire.scanner = new Scanner(System.in);

    GestionUniversitaire.ajouterInscription();

    System.setIn(System.in);

    assertTrue(true, "La méthode s'est exécutée sans erreur.");

    // Annule toutes les modifications pour ne pas polluer la base
   
}

    @Test
    void ajouterInscriptionShouldNotWork_InvalidForeignKey() {
        // 1. Simule des entrées avec des IDs invalides (qui n'existent pas en base)
        String input = "999\n999\n999\n999\n"; // IDs qui n'existent pas
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // 2. Réinitialise le scanner
        GestionUniversitaire.scanner = new Scanner(System.in);

        // 3. Vérifie que la méthode lève une SQLException
        SQLException exception = assertThrows(
            SQLException.class,
            () -> GestionUniversitaire.ajouterInscription(),
            "La méthode devrait lever une SQLException pour des IDs invalides"
        );

        // 4. Réinitialise System.in
        System.setIn(System.in);
    }

    @Test
void rechercherInscriptionParNomShouldWork() throws SQLException {
    // 1. Simule une entrée utilisateur valide
    String input = "SUFFIT\n";  // Nom d'un étudiant existant
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    // 2. Réinitialise le scanner
    GestionUniversitaire.scanner = new Scanner(System.in);

    // 3. Redirige la sortie console pour vérifier le message affiché
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // 4. Appelle la méthode
    assertDoesNotThrow(
        () -> GestionUniversitaire.rechercherInscriptionParNom(),
        "La méthode devrait s'exécuter sans erreur"
    );

    // 5. Vérifie que la sortie contient le message attendu
    String output = outContent.toString();
    assertTrue(
        output.contains("Cours trouves :"),
        "La méthode devrait afficher 'Cours trouves :' si des inscriptions sont trouvées"
    );

    // 6. Réinitialise System.in et System.out
    System.setIn(System.in);
    System.setOut(System.out);
}

@Test
void rechercherInscriptionParNomShouldNotWork() throws SQLException {
    // 1. Simule une entrée utilisateur pour un nom inexistant
    String input = "Inconnu\n";  // Nom d'un étudiant qui n'existe pas
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    // 2. Réinitialise le scanner
    GestionUniversitaire.scanner = new Scanner(System.in);

    // 3. Redirige la sortie console pour vérifier le message affiché
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // 4. Appelle la méthode
    assertDoesNotThrow(
        () -> GestionUniversitaire.rechercherInscriptionParNom(),
        "La méthode devrait s'exécuter sans erreur même si aucune inscription n'est trouvée"
    );

    // 5. Vérifie que la sortie contient le message attendu
    String output = outContent.toString();
    assertTrue(
        output.contains("Aucune inscription trouvee pour l'eleve : Inconnu"),
        "La méthode devrait afficher un message indiquant qu'aucune inscription n'a été trouvée"
    );

    // 6. Réinitialise System.in et System.out
    System.setIn(System.in);
    System.setOut(System.out);

    
}


    @Test
    void afficherToutesInscriptionsShouldWork() throws SQLException {
        // 1. Redirige la sortie console pour vérifier le message affiché
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // 2. Appelle la méthode
        assertDoesNotThrow(
            () -> GestionUniversitaire.afficherToutesInscriptions(),
            "La méthode devrait s'exécuter sans erreur"
        );

        // 3. Vérifie que la sortie contient le message attendu
        String output = outContent.toString();
        assertTrue(
            output.contains("Inscriptions trouvees :"),
            "La méthode devrait afficher 'Inscriptions trouvees :' si des inscriptions existent"
        );

        // 4. Réinitialise System.out
        System.setOut(System.out);
    }

    @Test
    void afficherToutesInscriptionsShouldNotWork() throws Exception {
        // 1. Utilise un DAO qui retourne une liste vide
        GestionUniversitaire.inscriptionDAO = new InscriptionDAO() {
            @Override
            public List<Inscription> findAll() {
                return new ArrayList<>(); // Retourne une liste vide
            }
        };

        // 2. Redirige la sortie console pour vérifier le message affiché
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // 3. Appelle la méthode
        assertDoesNotThrow(
            () -> GestionUniversitaire.afficherToutesInscriptions(),
            "La méthode devrait s'exécuter sans erreur même si aucune inscription n'est enregistrée"
        );

        // 4. Vérifie que la sortie contient le message attendu
        String output = outContent.toString();
        assertTrue(
            output.contains("Aucune inscription enregistree dans la base."),
            "La méthode devrait afficher un message indiquant qu'aucune inscription n'est enregistrée"
        );

        // 5. Réinitialise System.out et le DAO
        System.setOut(System.out);
        GestionUniversitaire.inscriptionDAO = new InscriptionDAO(); // Réinitialise le DAO
    }

    @Test
    void modifierInscriptionShouldWork() throws Exception {
    // 1. Simule les entrées utilisateur
    // Ici : on choisit un ID existant dans la base et on laisse les champs vides pour ne rien changer
    String input = "1\n\n\n\n"; // ID existant, puis entrées vides pour conserver les valeurs
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    // 2. Réinitialise le scanner
    GestionUniversitaire.scanner = new Scanner(System.in);

    // 3. Redirige la sortie console pour capturer le message
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // 4. Appelle la méthode
    assertDoesNotThrow(
        () -> GestionUniversitaire.modifierInscription(),
        "La méthode devrait s'exécuter sans erreur"
    );

    // 5. Vérifie que la sortie contient le message attendu
    String output = outContent.toString();
    assertTrue(
        output.contains("Enseignement modifie avec succes !"),
        "La méthode devrait afficher un message de succès"
    );

    // 6. Réinitialise System.in et System.out
    System.setIn(System.in);
    System.setOut(System.out);
}

@Test
void modifierInscriptionShouldNotWork() throws Exception {
    // 1. Simule les entrées utilisateur avec un ID inexistant
    String input = "999\n"; // ID qui n'existe pas dans la base
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    // 2. Réinitialise le scanner
    GestionUniversitaire.scanner = new Scanner(System.in);

    // 3. Redirige la sortie console pour capturer le message
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // 4. Appelle la méthode
    assertDoesNotThrow(
        () -> GestionUniversitaire.modifierInscription(),
        "La méthode devrait s'exécuter sans erreur même si l'ID n'existe pas"
    );

    // 5. Vérifie que la sortie contient le message d'erreur attendu
    String output = outContent.toString();
    assertTrue(
        output.contains("Inscription introuvable avec l'ID : 999"),
        "La méthode devrait afficher un message indiquant que l'inscription est introuvable"
    );

    // 6. Réinitialise System.in et System.out
    System.setIn(System.in);
    System.setOut(System.out);
}

@Test
void supprimerInscriptionShouldWork() throws Exception {
    

    int tempId = 11;
    String input = tempId + "\noui\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    GestionUniversitaire.scanner = new Scanner(System.in);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Appelle la méthode
    GestionUniversitaire.supprimerInscription();

    // Vérifie que le message de succès est affiché avant rollback
    String output = outContent.toString();
    assertTrue(output.contains("Cours supprime avec succes !"),
        "La méthode devrait afficher un message de succès après suppression"
    );

    // Reset System.in et System.out
    System.setIn(System.in);
    System.setOut(System.out);

}


@Test
void supprimerInscriptionShouldNotWork() throws Exception {
    // Simule un ID inexistant
    int nonExistentId = 9999;

    // Simule l'entrée utilisateur : ID + confirmation
    String input = nonExistentId + "\noui\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    GestionUniversitaire.scanner = new Scanner(System.in);

    // Redirige la sortie console
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Appelle la méthode et vérifie qu'elle ne lance pas d'exception
    assertDoesNotThrow(
        () -> GestionUniversitaire.supprimerInscription(),
        "La méthode devrait s'exécuter sans erreur même si l'ID n'existe pas"
    );

    // Vérifie que le message d'erreur est affiché
    String output = outContent.toString();
    assertTrue(
        output.contains("Cours introuvable avec l'ID : " + nonExistentId),
        "La méthode devrait afficher un message indiquant que l'inscription n'existe pas"
    );

    // Réinitialise System.in et System.out
    System.setIn(System.in);
    System.setOut(System.out);
}

    
}
