package com.example.dao;

import com.example.model.Master;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MasterDAOTest {

    private static Connection connection;
    private MasterDAO masterDAO;

    @BeforeAll
    static void setUpBeforeAll() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb5;DB_CLOSE_DELAY=-1", "sa", "");
        
        try (var stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE Master (Master_ID INT PRIMARY KEY, Nom_Master VARCHAR(25) NOT NULL, Responsable_ID INT, Departement_ID INT)");
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        try (var stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Master");
        }
        masterDAO = new MasterDAO(connection);
    }

    @Test
    @Order(1)
    @DisplayName("Test: Sauvegarde et récupération d'un Master")
    void testSaveAndFindById() throws SQLException {
        Master master = new Master();
        master.setMasterId(1);
        master.setNomMaster("MIAGE");
        
        masterDAO.save(master);
        
        Master found = masterDAO.findById(1);
        assertNotNull(found);
        assertEquals("MIAGE", found.getNomMaster());
    }

    @Test
    @Order(2)
    @DisplayName("Test: Mise à jour d'un Master")
    void testUpdate() throws SQLException {
        Master master = new Master();
        master.setMasterId(2);
        master.setNomMaster("INFORMATIQUE");
        masterDAO.save(master);
        
        master.setNomMaster("INFO");
        masterDAO.update(master);
        
        Master updated = masterDAO.findById(2);
        assertEquals("INFO", updated.getNomMaster());
    }

    @Test
    @Order(3)
    @DisplayName("Test: Suppression d'un Master")
    void testDelete() throws SQLException {
        Master master = new Master();
        master.setMasterId(3);
        master.setNomMaster("GESTION");
        masterDAO.save(master);
        
        masterDAO.delete(3);
        
        assertNull(masterDAO.findById(3));
    }

    @Test
    @Order(4)
    @DisplayName("Test: Récupération de tous les Masters")
    void testFindAll() throws SQLException {
        Master m1 = new Master(); m1.setMasterId(4); m1.setNomMaster("DROIT");
        Master m2 = new Master(); m2.setMasterId(5); m2.setNomMaster("ECONOMIE");
        masterDAO.save(m1);
        masterDAO.save(m2);
        
        List<Master> all = masterDAO.findAll();
        
        assertEquals(2, all.size());
    }
    
    @AfterAll
    static void tearDownAfterAll() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
