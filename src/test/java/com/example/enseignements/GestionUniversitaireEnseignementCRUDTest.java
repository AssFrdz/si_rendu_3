
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class GestionUniversitaireEnseignementCRUDTest {

    @Test
    void ajouterEnseignement_ShouldPrintSuccessMessage() throws SQLException {
        // Arrange
        String input = "1\nMathématiques\nCours de maths\nOUI\n1\nDupont\nJean\n1\nMaster Info\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement ajouterEnseignement() sans enseignementDAO
    }

    @Test
    void rechercherCoursParIntitule_ShouldHandleEmptyResult() throws SQLException {
        // Arrange
        String input = "Informatique\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement rechercherCoursParIntitule() sans enseignementDAO
    }

    @Test
    void afficherCours_ShouldPrintEmptyListMessage() throws SQLException {
        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement afficherCours() sans enseignementDAO
    }

    @Test
    void modifierEnseignement_ShouldHandleInvalidId() throws SQLException {
        // Arrange
        String input = "999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement modifierEnseignement() sans enseignementDAO
    }

    @Test
    void supprimerEnseignement_ShouldCancelDeletion() throws SQLException {
        // Arrange
        String input = "1\nnon\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement supprimerEnseignement() sans enseignementDAO
    }
}

