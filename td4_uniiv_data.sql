-- MySQL 8.0 schema -----------------------------------------------------------
-- Safe teardown (drop children first); adjust order as needed
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS Biblio;
DROP TABLE IF EXISTS Affectation;
DROP TABLE IF EXISTS Stage;
DROP TABLE IF EXISTS Presence;
DROP TABLE IF EXISTS Seminaire;
DROP TABLE IF EXISTS Candidature;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Salle;
DROP TABLE IF EXISTS Inscription;
DROP TABLE IF EXISTS Preference;
DROP TABLE IF EXISTS Enseignement;
DROP TABLE IF EXISTS Etudiant;
DROP TABLE IF EXISTS Master;
DROP TABLE IF EXISTS Enseignant;
DROP TABLE IF EXISTS Departement;

SET FOREIGN_KEY_CHECKS = 1;

-- Create all tables ----------------------------------------------------------
CREATE TABLE `Departement` (
  `Departement_ID` INT NOT NULL AUTO_INCREMENT,
  `Nom_Departement` VARCHAR(25) NOT NULL,
  CONSTRAINT `PK_Departement` PRIMARY KEY (`Departement_ID`),
  CONSTRAINT `UN_Nom_Departement` UNIQUE (`Nom_Departement`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Enseignant` (
  `Enseignant_ID` INT NOT NULL AUTO_INCREMENT,
  `Departement_ID` INT NOT NULL,
  `Nom` VARCHAR(25) NOT NULL,
  `Prenom` VARCHAR(25) NOT NULL,
  `Grade` ENUM('Vacataire','Moniteur','ATER','MCF','PROF') NULL,
  `Telephone` VARCHAR(10) DEFAULT NULL,
  `Fax` VARCHAR(10) DEFAULT NULL,
  `Email` VARCHAR(100) DEFAULT NULL,
  CONSTRAINT `PK_Enseignant` PRIMARY KEY (`Enseignant_ID`),
  CONSTRAINT `FK_Enseignant_Departement_ID`
    FOREIGN KEY (`Departement_ID`) REFERENCES `Departement`(`Departement_ID`)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Master` (
  `Master_ID` INT NOT NULL AUTO_INCREMENT,
  `Nom_Master` VARCHAR(25) NOT NULL,
  `Responsable_ID` INT NOT NULL,
  `Departement_ID` INT NOT NULL,
  CONSTRAINT `PK_Master` PRIMARY KEY (`Master_ID`),
  CONSTRAINT `UN_Nom_Master` UNIQUE (`Nom_Master`),
  CONSTRAINT `FK_Master_Responsable_ID`
    FOREIGN KEY (`Responsable_ID`) REFERENCES `Enseignant`(`Enseignant_ID`)
    ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT `FK_Master_Departement_ID`
    FOREIGN KEY (`Departement_ID`) REFERENCES `Departement`(`Departement_ID`)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Etudiant` (
  `Etudiant_ID` INT NOT NULL AUTO_INCREMENT,
  `Nom` VARCHAR(25) NOT NULL,
  `Prenom` VARCHAR(25) NOT NULL,
  `Date_Naissance` DATE NOT NULL,
  `Adresse` VARCHAR(50) DEFAULT NULL,
  `Ville` VARCHAR(25) DEFAULT NULL,
  `Code_Postal` VARCHAR(9) DEFAULT NULL,
  `Telephone` VARCHAR(10) DEFAULT NULL,
  `Fax` VARCHAR(10) DEFAULT NULL,
  `Email` VARCHAR(100) DEFAULT NULL,
  `Master_ID` INT DEFAULT NULL,
  CONSTRAINT `PK_Etudiant` PRIMARY KEY (`Etudiant_ID`),
  CONSTRAINT `FK_Etudiant_Master_ID`
    FOREIGN KEY (`Master_ID`) REFERENCES `Master`(`Master_ID`)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Enseignement` (
  `Enseignement_ID` INT NOT NULL,
  `Master_ID` INT NOT NULL,
  `Intitule` VARCHAR(60) NOT NULL,
  `Description` VARCHAR(1000) DEFAULT NULL,
  `Obligatoire` ENUM('OUI','NON') NOT NULL,
  `Enseignant_ID` INT NOT NULL,
  CONSTRAINT `PK_Enseignement` PRIMARY KEY (`Enseignement_ID`, `Master_ID`),
  CONSTRAINT `FK_Enseignement_Master_ID`
    FOREIGN KEY (`Master_ID`) REFERENCES `Master`(`Master_ID`)
    ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT `FK_Enseignement_Enseignant`
    FOREIGN KEY (`Enseignant_ID`) REFERENCES `Enseignant`(`Enseignant_ID`)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Preference` (
  `Preference_ID` INT NOT NULL AUTO_INCREMENT,
  `Etudiant_ID` INT NOT NULL,
  `Enseignement_ID` INT NOT NULL,
  `Master_ID` INT NOT NULL,
  `Preference` INT DEFAULT 0,
  CONSTRAINT `PK_Preference` PRIMARY KEY (`Preference_ID`),
  CONSTRAINT `FK_Preference_Etudiant`
    FOREIGN KEY (`Etudiant_ID`) REFERENCES `Etudiant`(`Etudiant_ID`)
    ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT `FK_Preference_Enseignement`
    FOREIGN KEY (`Enseignement_ID`, `Master_ID`)
    REFERENCES `Enseignement`(`Enseignement_ID`, `Master_ID`)
    ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT `UN_Preference` UNIQUE (`Etudiant_ID`,`Enseignement_ID`,`Master_ID`),
  CONSTRAINT `CK_Preference_Preference` CHECK (`Preference` IN (0,1,2,3,4))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Inscription` (
  `Inscription_ID` INT NOT NULL AUTO_INCREMENT,
  `Etudiant_ID` INT NOT NULL,
  `Enseignement_ID` INT NOT NULL,
  `Master_ID` INT NOT NULL,
  CONSTRAINT `PK_Inscription` PRIMARY KEY (`Inscription_ID`),
  CONSTRAINT `FK_Inscription_Etudiant`
    FOREIGN KEY (`Etudiant_ID`) REFERENCES `Etudiant`(`Etudiant_ID`)
    ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT `FK_Inscription_Enseignement`
    FOREIGN KEY (`Enseignement_ID`, `Master_ID`)
    REFERENCES `Enseignement`(`Enseignement_ID`, `Master_ID`)
    ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT `UN_Inscription` UNIQUE (`Etudiant_ID`,`Enseignement_ID`,`Master_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Note` (
  `Note_ID` INT NOT NULL AUTO_INCREMENT,
  `Inscription_ID` INT NOT NULL,
  `Note` FLOAT,
  CONSTRAINT `PK_Notes` PRIMARY KEY (`Note_ID`),
  CONSTRAINT `FK_Notes_Inscription`
    FOREIGN KEY (`Inscription_ID`)
    REFERENCES `Inscription` (`Inscription_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Salle` (
  `Batiment` VARCHAR(1) NOT NULL,
  `Numero_Salle` VARCHAR(10) NOT NULL,
  `Capacite` INT CHECK (`Capacite` > 1),
  CONSTRAINT `PK_Salle` PRIMARY KEY (`Batiment`, `Numero_Salle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Reservation` (
  `Reservation_ID` INT NOT NULL AUTO_INCREMENT,
  `Batiment` VARCHAR(1) NOT NULL,
  `Numero_Salle` VARCHAR(10) NOT NULL,
  `Enseignement_ID` INT NOT NULL,
  `Master_ID` INT NOT NULL,
  `Enseignant_ID` INT NOT NULL,
  `Date_Resa` DATE NOT NULL DEFAULT (CURRENT_DATE),
  `Heure_Debut` TIME NOT NULL DEFAULT (CURRENT_TIME),
  `Heure_Fin` TIME NOT NULL DEFAULT '23:00:00',
  `Nombre_Heures` INT NOT NULL,
  CONSTRAINT `PK_Reservation` PRIMARY KEY (`Reservation_ID`),
  CONSTRAINT `FK_Reservation_Salle`
    FOREIGN KEY (`Batiment`,`Numero_Salle`)
    REFERENCES `Salle`(`Batiment`,`Numero_Salle`)
    ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT `FK_Reservation_Enseignement`
    FOREIGN KEY (`Enseignement_ID`,`Master_ID`)
    REFERENCES `Enseignement`(`Enseignement_ID`,`Master_ID`)
    ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT `FK_Reservation_Enseignant`
    FOREIGN KEY (`Enseignant_ID`) REFERENCES `Enseignant`(`Enseignant_ID`)
    ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT `CK_Reservation_Nombre_Heures` CHECK (`Nombre_Heures` >= 1),
  CONSTRAINT `CK_Reservation_HeureDebFin` CHECK (`Heure_Debut` < `Heure_Fin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Candidature` (
  `Etudiant_ID` INT NOT NULL,
  `Master_ID` INT NOT NULL,
  `Preference` INT NOT NULL,
  `Confirmee` BOOLEAN DEFAULT FALSE,
  CONSTRAINT `PK_Candidature` PRIMARY KEY (`Etudiant_ID`,`Master_ID`),
  CONSTRAINT `FK_Candidature_Etudiant`
    FOREIGN KEY (`Etudiant_ID`) REFERENCES `Etudiant`(`Etudiant_ID`),
  CONSTRAINT `FK_Candidature_Master`
    FOREIGN KEY (`Master_ID`) REFERENCES `Master`(`Master_ID`),
  CONSTRAINT `CK_Candidature_Preference` CHECK (`Preference` IN (0,1,2,3,4)),
  CONSTRAINT `UN_Candidature` UNIQUE (`Etudiant_ID`,`Master_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Seminaire` (
  `Seminaire_ID` INT NOT NULL AUTO_INCREMENT,
  `Master_ID` INT NOT NULL,
  `Entreprise` VARCHAR(100) NOT NULL,
  `Confirmee` BOOLEAN DEFAULT FALSE,
  CONSTRAINT `PK_Seminaire` PRIMARY KEY (`Seminaire_ID`),
  CONSTRAINT `FK_Seminaire_Master`
    FOREIGN KEY (`Master_ID`) REFERENCES `Master`(`Master_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Presence` (
  `Seminaire_ID` INT NOT NULL,
  `Etudiant_ID` INT NOT NULL,
  CONSTRAINT `PK_Presence` PRIMARY KEY (`Seminaire_ID`,`Etudiant_ID`),
  CONSTRAINT `FK_Presence_Seminaire`
    FOREIGN KEY (`Seminaire_ID`) REFERENCES `Seminaire`(`Seminaire_ID`),
  CONSTRAINT `FK_Presence_Etudiant`
    FOREIGN KEY (`Etudiant_ID`) REFERENCES `Etudiant`(`Etudiant_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Stage` (
  `Stage_ID` INT NOT NULL AUTO_INCREMENT,
  `Etudiant_ID` INT NOT NULL,
  `Descriptif` VARCHAR(100) NOT NULL,
  `Entreprise` VARCHAR(100) NOT NULL,
  CONSTRAINT `PK_Stage` PRIMARY KEY (`Stage_ID`, `Etudiant_ID`),
  CONSTRAINT `FK_Stage_Etudiant`
    FOREIGN KEY (`Etudiant_ID`) REFERENCES `Etudiant`(`Etudiant_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Affectation` (
  `Affectation_ID` INT NOT NULL AUTO_INCREMENT,
  `Batiment` VARCHAR(1) NOT NULL,
  `Numero_Salle` VARCHAR(100) NOT NULL,
  `Master_ID` INT NOT NULL,
  CONSTRAINT `PK_Affectation` PRIMARY KEY (`Affectation_ID`),
  CONSTRAINT `FK_Affectation_Salle`
    FOREIGN KEY (`Batiment`,`Numero_Salle`)
    REFERENCES `Salle`(`Batiment`,`Numero_Salle`),
  CONSTRAINT `FK_Affectation_Master`
    FOREIGN KEY (`Master_ID`) REFERENCES `Master`(`Master_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Biblio` (
  `Biblio_ID` INT NOT NULL AUTO_INCREMENT,
  `Enseignement_ID` INT NOT NULL,
  `Master_ID` INT NOT NULL,
  `ISBN_Livre` VARCHAR(20) NOT NULL,
  CONSTRAINT `PK_Biblio` PRIMARY KEY (`Biblio_ID`),
  CONSTRAINT `FK_Biblio_Enseignement`
    FOREIGN KEY (`Enseignement_ID`,`Master_ID`)
    REFERENCES `Enseignement`(`Enseignement_ID`,`Master_ID`),
  CONSTRAINT `FK_Biblio_Master`
    FOREIGN KEY (`Master_ID`) REFERENCES `Master`(`Master_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
