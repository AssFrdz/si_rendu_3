package com.example.dao;

import com.example.model.Etudiant;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EtudiantDAOTest {

    private static Connection connection;
    private EtudiantDAO etudiantDAO;

    @BeforeAll
    static void setUpBeforeAll() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb1;DB_CLOSE_DELAY=-1", "sa", "");
        
        try (var stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE Etudiant (Etudiant_ID INT AUTO_INCREMENT PRIMARY KEY, Nom VARCHAR(25) NOT NULL, Prenom VARCHAR(25) NOT NULL, Date_Naissance DATE NOT NULL, Adresse VARCHAR(50), Ville VARCHAR(25), Code_Postal VARCHAR(9), Telephone VARCHAR(10), Fax VARCHAR(10), Email VARCHAR(100), Master_ID INT)");
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        try (var stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Etudiant");
        }
        etudiantDAO = new EtudiantDAO(connection);
    }

    @Test
    @Order(1)
    @DisplayName("Test: Sauvegarde et récupération d'un étudiant")
    void testSaveAndFindById() throws SQLException {
        Etudiant etudiant = new Etudiant();
        etudiant.setNom("Dupont");
        etudiant.setPrenom("Jean");
        etudiant.setEmail("jean.dupont@test.com");
        
        etudiantDAO.save(etudiant);
        
        assertNotEquals(0, etudiant.getEtudiantId());
        
        Etudiant found = etudiantDAO.findById(etudiant.getEtudiantId());
        assertNotNull(found);
        assertEquals("Dupont", found.getNom());
        assertEquals("Jean", found.getPrenom());
    }

    @Test
    @Order(2)
    @DisplayName("Test: Mise à jour d'un étudiant")
    void testUpdate() throws SQLException {
        Etudiant etudiant = new Etudiant();
        etudiant.setNom("Martin");
        etudiant.setPrenom("Sophie");
        etudiant.setEmail("sophie.martin@test.com");
        etudiantDAO.save(etudiant);
        
        etudiant.setNom("NouveauNom");
        etudiant.setEmail("nouveau.email@test.com");
        etudiantDAO.update(etudiant);
        
        Etudiant updated = etudiantDAO.findById(etudiant.getEtudiantId());
        assertEquals("NouveauNom", updated.getNom());
        assertEquals("nouveau.email@test.com", updated.getEmail());
    }

    @Test
    @Order(3)
    @DisplayName("Test: Suppression d'un étudiant")
    void testDelete() throws SQLException {
        Etudiant etudiant = new Etudiant();
        etudiant.setNom("Durand");
        etudiant.setPrenom("Paul");
        etudiantDAO.save(etudiant);
        int idToDelete = etudiant.getEtudiantId();
        
        etudiantDAO.delete(idToDelete);
        
        assertNull(etudiantDAO.findById(idToDelete));
    }

    @Test
    @Order(4)
    @DisplayName("Test: Récupération de tous les étudiants")
    void testFindAll() throws SQLException {
        Etudiant e1 = new Etudiant(); e1.setNom("A"); e1.setPrenom("A");
        Etudiant e2 = new Etudiant(); e2.setNom("B"); e2.setPrenom("B");
        etudiantDAO.save(e1);
        etudiantDAO.save(e2);
        
        List<Etudiant> all = etudiantDAO.findAll();
        
        assertEquals(2, all.size());
    }
    
    @AfterAll
    static void tearDownAfterAll() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
