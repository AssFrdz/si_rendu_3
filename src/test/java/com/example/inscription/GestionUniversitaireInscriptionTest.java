import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import main.java.com.example.GestionUniversitaire;
import main.java.com.example.dao.EnseignementDAO;
import main.java.com.example.dao.EtudiantDAO;
import main.java.com.example.dao.InscriptionDAO;
import main.java.com.example.model.Enseignant;
import main.java.com.example.model.Enseignement;
import main.java.com.example.model.Etudiant;
import main.java.com.example.model.Inscription;
import main.java.com.example.model.Master;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GestionUniversitaireInscriptionTest {

    private GestionUniversitaire gestion;
    private EtudiantDAO etudiantDAO;
    private EnseignementDAO enseignementDAO;
    private InscriptionDAO inscriptionDAO;

    private int idEtudiant;
    private int idEnseignement;
    private int idInscription;

    @BeforeAll
    public void setup() throws Exception {
    gestion = new GestionUniversitaire();
    etudiantDAO = new EtudiantDAO();
    enseignementDAO = new EnseignementDAO();
    inscriptionDAO = new InscriptionDAO();

    Random rnd = new Random();
    idEtudiant = rnd.nextInt(1000) + 100;
    idEnseignement = rnd.nextInt(1000) + 100;
    idInscription = rnd.nextInt(1000) + 100;

    // Création étudiant
    Etudiant e = new Etudiant(idEtudiant, "TestNom", "TestPrenom");
    e.setDateNaiss(LocalDate.now());
    etudiantDAO.save(e);

    // Création Enseignant fictif
    Enseignant ens = new Enseignant(1, "ProfNom", "ProfPrenom", "Grade", "000", "111", "email@test.com");
    // normalement tu devrais le sauvegarder via DAO Enseignant si FK existe

    // Création Master fictif
    Master m = new Master(1, "MasterTest");

    // Création Enseignement simple pour le test
    Enseignement enseignement = new Enseignement(idEnseignement);
    enseignement.setMaster(m);
    enseignement.setIntitule("intitule");
    enseignement.setObligatoire("OUI");
    enseignement.setEnseignant(ens);
    enseignement.setMaster(m);
    enseignement.setIntitule("Intitule Test");
    
    enseignementDAO.save(enseignement);
}


    @BeforeEach
    public void resetInput() {
        String input = idInscription + "\n" + idEtudiant + "\n" + idEnseignement + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    public void testAjouterInscription() throws SQLException {
        Inscription insc = new Inscription(idInscription,
                etudiantDAO.findById(idEtudiant),
                enseignementDAO.findById(idEnseignement));

        inscriptionDAO.save(insc);

        Inscription found = inscriptionDAO.findById(idInscription);
        assertNotNull(found, "Inscription devrait être ajoutée");
        assertEquals(idEtudiant, found.getEtudiant().getId());
    }

    @Test
    public void testAfficherToutesInscriptions() throws SQLException {
        List<Inscription> list = inscriptionDAO.findAll();
        assertFalse(list.isEmpty(), "Il devrait y avoir au moins une inscription");
    }

    @Test
    public void testRechercherInscriptionParNom() throws SQLException {
        List<Inscription> list = inscriptionDAO.findByName("TestNom");
        assertFalse(list.isEmpty(), "La recherche par nom devrait retourner des résultats");
        assertEquals(idEtudiant, list.get(0).getEtudiant().getId());
    }

    @Test
    public void testModifierInscription() throws SQLException {
        Inscription insc = inscriptionDAO.findById(idInscription);
        assertNotNull(insc);

        insc.getEtudiant().setNom("NouveauNom");
        inscriptionDAO.update(insc);

        Inscription modif = inscriptionDAO.findById(idInscription);
        assertEquals("NouveauNom", modif.getEtudiant().getNom());
    }

    @Test
    public void testSupprimerInscription() throws SQLException {
        inscriptionDAO.delete(idInscription);
        Inscription deleted = inscriptionDAO.findById(idInscription);
        assertNull(deleted, "Inscription devrait être supprimée");
    }

    @AfterAll
    public void cleanup() throws SQLException {
        inscriptionDAO.findAll().forEach(i -> {
            try { inscriptionDAO.delete(i.getId()); } catch (SQLException e) {}
        });
        enseignementDAO.delete(idEnseignement);
        etudiantDAO.delete(idEtudiant);
    }
}
