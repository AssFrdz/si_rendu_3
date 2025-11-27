package com.example.dao;

import com.example.model.Inscription;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InscriptionDAOTest {

    private static Connection connection;
    private InscriptionDAO inscriptionDAO;

    @BeforeAll
    static void setUpBeforeAll() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb4;DB_CLOSE_DELAY=-1", "sa", "");
        
        try (var stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE Master (Master_ID INT PRIMARY KEY, Nom_Master VARCHAR(25) NOT NULL)");
            stmt.execute("INSERT INTO Master (Master_ID, Nom_Master) VALUES (1, 'MIAGE')");
            
            stmt.execute("CREATE TABLE Etudiant (Etudiant_ID INT PRIMARY KEY, Nom VARCHAR(25) NOT NULL, Prenom VARCHAR(25) NOT NULL, Date_Naissance DATE NOT NULL, Email VARCHAR(100), Master_ID INT, FOREIGN KEY (Master_ID) REFERENCES Master(Master_ID))");
            stmt.execute("INSERT INTO Etudiant (Etudiant_ID, Nom, Prenom, Date_Naissance) VALUES (1, 'Test', 'Etudiant', '2000-01-01')");
            
            stmt.execute("CREATE TABLE Enseignement (Enseignement_ID INT, Master_ID INT, Intitule VARCHAR(60) NOT NULL, Description VARCHAR(1000), Obligatoire VARCHAR(3), Enseignant_ID INT, PRIMARY KEY (Enseignement_ID, Master_ID), FOREIGN KEY (Master_ID) REFERENCES Master(Master_ID))");
            stmt.execute("INSERT INTO Enseignement (Enseignement_ID, Master_ID, Intitule, Obligatoire) VALUES (1, 1, 'Java JDBC', 'OUI')");
            
            stmt.execute("CREATE TABLE Inscription (Inscription_ID INT AUTO_INCREMENT PRIMARY KEY, Etudiant_ID INT NOT NULL, Enseignement_ID INT NOT NULL, Master_ID INT NOT NULL, FOREIGN KEY (Etudiant_ID) REFERENCES Etudiant(Etudiant_ID), FOREIGN KEY (Enseignement_ID, Master_ID) REFERENCES Enseignement(Enseignement_ID, Master_ID))");
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        try (var stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Inscription");
        }
        inscriptionDAO = new InscriptionDAO(connection);
    }

    @Test
    @Order(1)
    @DisplayName("Test: Sauvegarde et récupération d'une Inscription")
    void testSaveAndFindById() throws SQLException {
        Inscription inscription = new Inscription();
        inscription.setEtudiantId(1);
        inscription.setEnseignementId(1);
        inscription.setMasterId(1);
        
        inscriptionDAO.save(inscription);
        
        assertNotEquals(0, inscription.getInscriptionId());
        
        Inscription found = inscriptionDAO.findById(inscription.getInscriptionId());
        assertNotNull(found);
        assertEquals(1, found.getEtudiantId());
    }

    @Test
    @Order(2)
    @DisplayName("Test: Mise à jour d'une Inscription")
    void testUpdate() throws SQLException {
        Inscription inscription = new Inscription();
        inscription.setEtudiantId(1);
        inscription.setEnseignementId(1);
        inscription.setMasterId(1);
        inscriptionDAO.save(inscription);
        
        inscription.setEtudiantId(1);
        inscriptionDAO.update(inscription);
        
        Inscription updated = inscriptionDAO.findById(inscription.getInscriptionId());
        assertEquals(1, updated.getEtudiantId());
    }

    @Test
    @Order(3)
    @DisplayName("Test: Suppression d'une Inscription")
    void testDelete() throws SQLException {
        Inscription inscription = new Inscription();
        inscription.setEtudiantId(1);
        inscription.setEnseignementId(1);
        inscription.setMasterId(1);
        inscriptionDAO.save(inscription);
        int idToDelete = inscription.getInscriptionId();
        
        inscriptionDAO.delete(idToDelete);
        
        assertNull(inscriptionDAO.findById(idToDelete));
    }

    @Test
    @Order(4)
    @DisplayName("Test: Récupération de toutes les Inscriptions")
    void testFindAll() throws SQLException {
        Inscription i1 = new Inscription(); i1.setEtudiantId(1); i1.setEnseignementId(1); i1.setMasterId(1);
        Inscription i2 = new Inscription(); i2.setEtudiantId(1); i2.setEnseignementId(1); i2.setMasterId(1);
        inscriptionDAO.save(i1);
        inscriptionDAO.save(i2);
        
        List<Inscription> all = inscriptionDAO.findAll();
        
        assertEquals(2, all.size());
    }
    
    @AfterAll
    static void tearDownAfterAll() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
