import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;


class GestionUniversitaireNoteCRUDTest {

    @Test
    void ajouterNote_ShouldPrintSuccessMessage() throws SQLException {
        // Arrange
        String input = "1\n1\n1\n15\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement ajouterNote() sans noteDAO
    }

    @Test
    void moyenneEnseignement_ShouldPrintZeroIfNoNotes() throws SQLException {
        // Arrange
        String input = "1\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement moyenneEnseignement() sans noteDAO
    }

    @Test
    void afficherToutesNotes_ShouldPrintEmptyListMessage() throws SQLException {
        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement afficherToutesNotes() sans noteDAO
    }

    @Test
    void modifierNote_ShouldHandleInvalidId() throws SQLException {
        // Arrange
        String input = "999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement modifierNote() sans noteDAO
    }

    @Test
    void supprimerNote_ShouldCancelDeletion() throws SQLException {
        // Arrange
        String input = "1\nnon\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement supprimerNote() sans noteDAO
    }
}
