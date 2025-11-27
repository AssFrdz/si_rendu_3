-- Departement (AUTO_INCREMENT id → omit it)
INSERT INTO Departement (Nom_Departement) VALUES
  ('MIDO'),
  ('LSO'),
  ('MSO');

-- Enseignant (omit Enseignant_ID)
INSERT INTO Enseignant (Departement_ID, Nom, Prenom, Grade, Telephone, Fax, Email) VALUES
  ((SELECT Departement_ID FROM Departement WHERE Nom_Departement='MIDO'),'MANOUVRIER','Maude','MCF','4185','4091','manouvrier@lamsade.dauphine.fr'),
  ((SELECT Departement_ID FROM Departement WHERE Nom_Departement='MIDO'),'OZTURK','Meltem','MCF',NULL,'4091','ozturk@lamsade.dauphine.fr'),
  ((SELECT Departement_ID FROM Departement WHERE Nom_Departement='MIDO'),'MURAT','Cécile','MCF',NULL,'4091','murat@lamsade.dauphine.fr'),
  ((SELECT Departement_ID FROM Departement WHERE Nom_Departement='MIDO'),'DEBECE','Gilles','Moniteur',NULL,NULL,NULL);

-- Master (omit Master_ID)
INSERT INTO Master (Nom_Master, Responsable_ID, Departement_ID) VALUES
  ('ID',        (SELECT Enseignant_ID FROM Enseignant WHERE Nom='OZTURK'),     (SELECT Departement_ID FROM Departement WHERE Nom_Departement='MIDO')),
  ('MIAGE-IF',  (SELECT Enseignant_ID FROM Enseignant WHERE Nom='MANOUVRIER'), (SELECT Departement_ID FROM Departement WHERE Nom_Departement='MIDO')),
  ('MIAGE-SITN',(SELECT Enseignant_ID FROM Enseignant WHERE Nom='MURAT'),      (SELECT Departement_ID FROM Departement WHERE Nom_Departement='MIDO'));

-- Etudiant (omit Etudiant_ID)  — use YYYY-MM-DD dates
INSERT INTO Etudiant (Nom, Prenom, Date_Naissance, Adresse, Ville, Code_Postal, Telephone, Fax, Email, Master_ID) VALUES
  ('DEBECE','Aude','1979-08-15','45, Avenue des abeilles','PARIS','75012',NULL,NULL,NULL,(SELECT Master_ID FROM Master WHERE Nom_Master='MIAGE-IF')),
  ('SUFFIT','Sam','1985-09-01','145, Boulevard Raspail','PARIS','75014',NULL,NULL,NULL,(SELECT Master_ID FROM Master WHERE Nom_Master='MIAGE-SITN'));

-- Enseignement (no auto-inc; keep explicit ids as integers)
INSERT INTO Enseignement (Enseignement_ID, Master_ID, Intitule, Description, Obligatoire, Enseignant_ID) VALUES
  (1,(SELECT Master_ID FROM Master WHERE Nom_Master='MIAGE-IF'),'C++/C#/Python','C++ (18h)+ Python (6h) + C#(3h)','NON',(SELECT Enseignant_ID FROM Enseignant WHERE Nom='MANOUVRIER')),
  (2,(SELECT Master_ID FROM Master WHERE Nom_Master='MIAGE-IF'),'Persistance ORM/HIBERNATE','TP JDBC en Java et projet Hibernate + Examen','NON',(SELECT Enseignant_ID FROM Enseignant WHERE Nom='MANOUVRIER'));

-- Preference (omit Preference_ID)
INSERT INTO Preference (Etudiant_ID, Enseignement_ID, Master_ID, Preference) VALUES
  ((SELECT Etudiant_ID FROM Etudiant WHERE Nom='SUFFIT'), 1, (SELECT Master_ID FROM Master WHERE Nom_Master='MIAGE-IF'), 4),
  ((SELECT Etudiant_ID FROM Etudiant WHERE Nom='SUFFIT'), 2, (SELECT Master_ID FROM Master WHERE Nom_Master='MIAGE-IF'), 0);

-- Inscription (omit Inscription_ID)
INSERT INTO Inscription (Etudiant_ID, Enseignement_ID, Master_ID) VALUES
  ((SELECT Etudiant_ID FROM Etudiant WHERE Nom='SUFFIT'), 1, (SELECT Master_ID FROM Master WHERE Nom_Master='MIAGE-IF'));

-- Salle (Capacite is INT → no quotes)
INSERT INTO Salle (Batiment, Numero_Salle, Capacite) VALUES
  ('B','020',15),
  ('B','022',15);

-- Reservation (omit Reservation_ID; use proper date/time and INT for Nombre_Heures)
INSERT INTO Reservation
  (Batiment, Numero_Salle, Enseignement_ID, Master_ID, Enseignant_ID, Date_Resa, Heure_Debut, Heure_Fin, Nombre_Heures)
VALUES
  ('B','022',
   (SELECT Enseignement_ID FROM Enseignement WHERE Intitule LIKE '%C++%'),
   (SELECT Master_ID       FROM Enseignement WHERE Intitule LIKE '%C++%'),
   (SELECT Enseignant_ID   FROM Enseignant   WHERE Nom='MANOUVRIER'),
   '2012-01-15','08:30:00','11:45:00',3),
  ('B','020',
   (SELECT Enseignement_ID FROM Enseignement WHERE Intitule LIKE '%HIBERNATE%'),
   (SELECT Master_ID       FROM Enseignement WHERE Intitule LIKE '%HIBERNATE%'),
   (SELECT Enseignant_ID   FROM Enseignant   WHERE Nom='MANOUVRIER'),
   '2011-11-17','13:45:00','17:00:00',3);

-- Candidature (booleans are fine as TRUE/FALSE)
INSERT INTO Candidature (Etudiant_ID, Master_ID, Preference, Confirmee) VALUES
  ((SELECT Etudiant_ID FROM Etudiant WHERE Nom='SUFFIT'),
   (SELECT Master_ID   FROM Master   WHERE Nom_Master='MIAGE-IF'),
   4, TRUE);

-- Seminaire (omit Seminaire_ID)
INSERT INTO Seminaire (Master_ID, Entreprise, Confirmee) VALUES
  ((SELECT Master_ID FROM Master WHERE Nom_Master='MIAGE-IF'), 'SG CIB', TRUE);

-- Presence (use the row we just inserted; LIMIT 1 guards against duplicates)
INSERT INTO Presence (Seminaire_ID, Etudiant_ID) VALUES
  ((SELECT Seminaire_ID FROM Seminaire WHERE Master_ID=(SELECT Master_ID FROM Master WHERE Nom_Master='MIAGE-IF') AND Entreprise='SG CIB' LIMIT 1),
   (SELECT Etudiant_ID  FROM Etudiant  WHERE Nom='SUFFIT'));

-- Stage (omit Stage_ID)
INSERT INTO Stage (Etudiant_ID, Descriptif, Entreprise) VALUES
  ((SELECT Etudiant_ID FROM Etudiant WHERE Nom='SUFFIT'),'Stage de développement C++','SG CIB');

-- Affectation (omit Affectation_ID)
INSERT INTO Affectation (Batiment, Numero_Salle, Master_ID) VALUES
  ('B','022', (SELECT Master_ID FROM Master WHERE Nom_Master='MIAGE-IF'));

-- Biblio (omit Biblio_ID)
INSERT INTO Biblio (Enseignement_ID, Master_ID, ISBN_Livre) VALUES
  ((SELECT Enseignement_ID FROM Enseignement WHERE Intitule LIKE '%C++%'),
   (SELECT Master_ID       FROM Enseignement WHERE Intitule LIKE '%C++%'),
   '2 345 678 456');  -- kept as VARCHAR
