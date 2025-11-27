package com.example.dao;

import com.example.model.Note;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NoteDAOTest {

    private static Connection connection;
    private NoteDAO noteDAO;
    private static int inscriptionId;

    @BeforeAll
    static void setUpBeforeAll() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb3;DB_CLOSE_DELAY=-1", "sa", "");
        
        try (var stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE Inscription (Inscription_ID INT AUTO_INCREMENT PRIMARY KEY, Etudiant_ID INT, Enseignement_ID INT, Master_ID INT)");
            stmt.execute("INSERT INTO Inscription (Etudiant_ID, Enseignement_ID, Master_ID) VALUES (1, 1, 1)");
            try (ResultSet rs = stmt.executeQuery("SELECT Inscription_ID FROM Inscription WHERE Etudiant_ID = 1")) {
                if (rs.next()) inscriptionId = rs.getInt(1);
            }
            
            stmt.execute("CREATE TABLE Note (Note_ID INT AUTO_INCREMENT PRIMARY KEY, Inscription_ID INT NOT NULL, Note FLOAT, FOREIGN KEY (Inscription_ID) REFERENCES Inscription(Inscription_ID))");
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        try (var stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Note");
        }
        noteDAO = new NoteDAO(connection);
    }

    @Test
    @Order(1)
    @DisplayName("Test: Sauvegarde et récupération d'une Note")
    void testSaveAndFindById() throws SQLException {
        Note note = new Note();
        note.setInscriptionId(inscriptionId);
        note.setNote(15.5f);
        
        noteDAO.save(note);
        
        assertNotEquals(0, note.getNoteId());
        
        Note found = noteDAO.findById(note.getNoteId());
        assertNotNull(found);
        assertEquals(15.5f, found.getNote(), 0.001);
    }

    @Test
    @Order(2)
    @DisplayName("Test: Mise à jour d'une Note")
    void testUpdate() throws SQLException {
        Note note = new Note();
        note.setInscriptionId(inscriptionId);
        note.setNote(10.0f);
        noteDAO.save(note);
        
        note.setNote(18.75f);
        noteDAO.update(note);
        
        Note updated = noteDAO.findById(note.getNoteId());
        assertEquals(18.75f, updated.getNote(), 0.001);
    }

    @Test
    @Order(3)
    @DisplayName("Test: Suppression d'une Note")
    void testDelete() throws SQLException {
        Note note = new Note();
        note.setInscriptionId(inscriptionId);
        note.setNote(12.0f);
        noteDAO.save(note);
        int idToDelete = note.getNoteId();
        
        noteDAO.delete(idToDelete);
        
        assertNull(noteDAO.findById(idToDelete));
    }

    @Test
    @Order(4)
    @DisplayName("Test: Récupération de toutes les Notes")
    void testFindAll() throws SQLException {
        Note n1 = new Note(); n1.setInscriptionId(inscriptionId); n1.setNote(14.0f);
        Note n2 = new Note(); n2.setInscriptionId(inscriptionId); n2.setNote(16.0f);
        noteDAO.save(n1);
        noteDAO.save(n2);
        
        List<Note> all = noteDAO.findAll();
        
        assertEquals(2, all.size());
    }
    
    @AfterAll
    static void tearDownAfterAll() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
