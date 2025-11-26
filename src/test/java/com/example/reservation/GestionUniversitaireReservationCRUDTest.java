import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import main.java.com.example.GestionUniversitaire;


class GestionUniversitaireReservationCRUDTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        // Réinitialise le Scanner et la sortie standard avant chaque test
        GestionUniversitaire.scanner = new java.util.Scanner(System.in);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        // Réinitialise le Scanner et la sortie standard après chaque test
        GestionUniversitaire.scanner = new java.util.Scanner(System.in);
        System.setOut(System.out);
    }

   

   
}
