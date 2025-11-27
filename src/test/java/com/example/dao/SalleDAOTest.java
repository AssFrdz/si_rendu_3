package com.example.dao;

import com.example.model.Salle;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SalleDAOTest {

    private static Connection connection;
    private SalleDAO salleDAO;

    @BeforeAll
    static void setUpBeforeAll() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb2;DB_CLOSE_DELAY=-1", "sa", "");
        
        try (var stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE Salle (Batiment VARCHAR(1) NOT NULL, Numero_Salle VARCHAR(10) NOT NULL, Capacite INT, PRIMARY KEY (Batiment, Numero_Salle))");
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        try (var stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Salle");
        }
        salleDAO = new SalleDAO(connection);
    }

    @Test
    @Order(1)
    @DisplayName("Test: Sauvegarde et récupération d'une Salle")
    void testSaveAndFindById() throws SQLException {
        Salle salle = new Salle();
        salle.setBatiment("A");
        salle.setNumeroSalle("101");
        salle.setCapacite(50);
        
        salleDAO.save(salle);
        
        Salle found = salleDAO.findById("A", "101");
        assertNotNull(found);
        assertEquals(50, found.getCapacite());
    }

    @Test
    @Order(2)
    @DisplayName("Test: Mise à jour d'une Salle")
    void testUpdate() throws SQLException {
        Salle salle = new Salle();
        salle.setBatiment("B");
        salle.setNumeroSalle("202");
        salle.setCapacite(30);
        salleDAO.save(salle);
        
        salle.setCapacite(60);
        salleDAO.update(salle);
        
        Salle updated = salleDAO.findById("B", "202");
        assertEquals(60, updated.getCapacite());
    }

    @Test
    @Order(3)
    @DisplayName("Test: Suppression d'une Salle")
    void testDelete() throws SQLException {
        Salle salle = new Salle();
        salle.setBatiment("C");
        salle.setNumeroSalle("303");
        salle.setCapacite(20);
        salleDAO.save(salle);
        
        salleDAO.delete("C", "303");
        
        assertNull(salleDAO.findById("C", "303"));
    }

    @Test
    @Order(4)
    @DisplayName("Test: Récupération de toutes les Salles")
    void testFindAll() throws SQLException {
        Salle s1 = new Salle(); s1.setBatiment("D"); s1.setNumeroSalle("401"); s1.setCapacite(10);
        Salle s2 = new Salle(); s2.setBatiment("D"); s2.setNumeroSalle("402"); s2.setCapacite(15);
        salleDAO.save(s1);
        salleDAO.save(s2);
        
        List<Salle> all = salleDAO.findAll();
        
        assertEquals(2, all.size());
    }
    
    @AfterAll
    static void tearDownAfterAll() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
