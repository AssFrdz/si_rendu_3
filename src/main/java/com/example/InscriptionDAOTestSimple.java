import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.com.example.dao.EnseignementDAO;
import main.java.com.example.dao.EtudiantDAO;
import main.java.com.example.dao.InscriptionDAO;
import main.java.com.example.model.Enseignement;
import main.java.com.example.model.Etudiant;
import main.java.com.example.model.Inscription;

public class InscriptionDAOTestSimple {

    private static EtudiantDAO etudiantDAO;
    private static EnseignementDAO enseignementDAO;
    private static InscriptionDAO inscriptionDAO;

    private static final int ID_ETUDIANT = 1;
    private static final int ID_ENSEIGNEMENT = 1;
    private static final int ID_INSCRIPTION = 1;

    @BeforeAll
    public static void setup() throws SQLException {
        etudiantDAO = new EtudiantDAO();
        enseignementDAO = new EnseignementDAO();
        inscriptionDAO = new InscriptionDAO();

        // Création d'un étudiant simple
        Etudiant e = new Etudiant(ID_ETUDIANT, "NomTest", "PrenomTest");
        e.setDateNaiss(LocalDate.now());
        etudiantDAO.save(e);

        // Création d'un enseignement simple
        Enseignement ens = new Enseignement(ID_ENSEIGNEMENT);
        ens.setIntitule("TestCours");
        enseignementDAO.save(ens);

        // Création d'une inscription simple
        Inscription insc = new Inscription(ID_INSCRIPTION, e, ens);
        inscriptionDAO.save(insc);
    }

    @Test
    public void testAjouterInscription() throws SQLException {
        Etudiant e = etudiantDAO.findById(ID_ETUDIANT);
        Enseignement ens = enseignementDAO.findById(ID_ENSEIGNEMENT);

        Inscription insc = new Inscription(2, e, ens);
        inscriptionDAO.save(insc);

        Inscription found = inscriptionDAO.findById(2);
        assertNotNull(found);
        assertEquals(ID_ETUDIANT, found.getEtudiant().getId());
    }

    @Test
    public void testRechercherInscriptionParNom() throws SQLException {
        List<Inscription> list = inscriptionDAO.findByName("NomTest");
        assertFalse(list.isEmpty());
        assertEquals(ID_ETUDIANT, list.get(0).getEtudiant().getId());
    }

    @Test
    public void testModifierInscription() throws SQLException {
        Inscription insc = inscriptionDAO.findById(ID_INSCRIPTION);
        assertNotNull(insc);
        insc.getEtudiant().setNom("NouveauNom");
        inscriptionDAO.update(insc);

        Inscription updated = inscriptionDAO.findById(ID_INSCRIPTION);
        assertEquals("NouveauNom", updated.getEtudiant().getNom());
    }

    @Test
    public void testSupprimerInscription() throws SQLException {
        inscriptionDAO.delete(ID_INSCRIPTION);
        Inscription deleted = inscriptionDAO.findById(ID_INSCRIPTION);
        assertNull(deleted);
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        // Nettoyage simple
        inscriptionDAO.findAll().forEach(i -> {
            try { inscriptionDAO.delete(i.getId()); } catch (SQLException ex) {}
        });
        enseignementDAO.delete(ID_ENSEIGNEMENT);
        etudiantDAO.delete(ID_ETUDIANT);
    }
}
