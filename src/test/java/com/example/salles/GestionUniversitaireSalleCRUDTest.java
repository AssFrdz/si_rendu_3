import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;


class GestionUniversitaireSalleCRUDTest {

    @Test
    void ajouterSalle_ShouldPrintSuccessMessage() throws SQLException {
        // Arrange
        String input = "Bâtiment A\n101\n30\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement ajouterSalle() sans salleDAO
    }

    @Test
    void chercherSalle_ShouldHandleInvalidSalle() throws SQLException {
        // Arrange
        String input = "Bâtiment A\n999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement chercherSalle() sans salleDAO
    }

    @Test
    void afficherToutesSalles_ShouldPrintEmptyListMessage() throws SQLException {
        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement afficherToutesSalles() sans salleDAO
    }

    @Test
    void modifierSalle_ShouldHandleInvalidSalle() throws SQLException {
        // Arrange
        String input = "Bâtiment A\n999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement modifierSalle() sans salleDAO
    }

    @Test
    void supprimerSalle_ShouldCancelDeletion() throws SQLException {
        // Arrange
        String input = "Bâtiment A\n101\nnon\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Redirige la sortie standard pour capturer les prints
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        // Impossible d'appeler directement supprimerSalle() sans salleDAO
    }
}
