package com.example.dao;

import com.example.model.Enseignement;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EnseignementDAOTest {

    private static Connection connection;
    private EnseignementDAO enseignementDAO;

    @BeforeAll
    static void setUpBeforeAll() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb6;DB_CLOSE_DELAY=-1", "sa", "");
        
        try (var stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE Master (Master_ID INT PRIMARY KEY, Nom_Master VARCHAR(25) NOT NULL)");
            stmt.execute("INSERT INTO Master (Master_ID, Nom_Master) VALUES (1, 'MIAGE')");
            
            stmt.execute("CREATE TABLE Enseignement (Enseignement_ID INT, Master_ID INT, Intitule VARCHAR(60) NOT NULL, Description VARCHAR(1000), Obligatoire VARCHAR(3), Enseignant_ID INT, PRIMARY KEY (Enseignement_ID, Master_ID), FOREIGN KEY (Master_ID) REFERENCES Master(Master_ID))");
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        try (var stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Enseignement");
        }
        enseignementDAO = new EnseignementDAO(connection);
    }

    @Test
    @Order(1)
    @DisplayName("Test: Sauvegarde et récupération d'un Enseignement")
    void testSaveAndFindById() throws SQLException {
        Enseignement ens = new Enseignement();
        ens.setEnseignementId(1);
        ens.setMasterId(1);
        ens.setIntitule("Java JDBC");
        ens.setObligatoire("OUI");
        
        enseignementDAO.save(ens);
        
        Enseignement found = enseignementDAO.findById(1, 1);
        assertNotNull(found);
        assertEquals("Java JDBC", found.getIntitule());
    }

    @Test
    @Order(2)
    @DisplayName("Test: Mise à jour d'un Enseignement")
    void testUpdate() throws SQLException {
        Enseignement ens = new Enseignement();
        ens.setEnseignementId(2);
        ens.setMasterId(1);
        ens.setIntitule("Bases de données");
        ens.setObligatoire("OUI");
        enseignementDAO.save(ens);
        
        ens.setIntitule("SQL Avancé");
        enseignementDAO.update(ens);
        
        Enseignement updated = enseignementDAO.findById(2, 1);
        assertEquals("SQL Avancé", updated.getIntitule());
    }

    @Test
    @Order(3)
    @DisplayName("Test: Suppression d'un Enseignement")
    void testDelete() throws SQLException {
        Enseignement ens = new Enseignement();
        ens.setEnseignementId(3);
        ens.setMasterId(1);
        ens.setIntitule("UML");
        enseignementDAO.save(ens);
        
        enseignementDAO.delete(3, 1);
        
        assertNull(enseignementDAO.findById(3, 1));
    }

    @Test
    @Order(4)
    @DisplayName("Test: Récupération de tous les Enseignements")
    void testFindAll() throws SQLException {
        Enseignement e1 = new Enseignement(); e1.setEnseignementId(4); e1.setMasterId(1); e1.setIntitule("Test1");
        Enseignement e2 = new Enseignement(); e2.setEnseignementId(5); e2.setMasterId(1); e2.setIntitule("Test2");
        enseignementDAO.save(e1);
        enseignementDAO.save(e2);
        
        List<Enseignement> all = enseignementDAO.findAll();
        
        assertEquals(2, all.size());
    }
    
    @AfterAll
    static void tearDownAfterAll() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
