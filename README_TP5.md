# TP 5 - Tests Unitaires et Tests d'Intégration avec JUnit 5

Ce document complète le README principal avec les instructions spécifiques au TP 5.

## 📋 Objectif du TP 5

Ajouter une stratégie de tests automatisés au projet JDBC existant en utilisant **JUnit 5** pour tester les services métiers (DAO) avec des tests unitaires et d'intégration.

## 🏗️ Structure du Projet Maven

Le projet a été transformé en projet Maven avec la structure suivante :

```
tp-jdbc-univ/
├── pom.xml                       # Configuration Maven avec JUnit 5
├── src/
│   ├── main/
│   │   ├── java/                 # Code de production
│   │   │   └── com/example/
│   │   │       ├── dao/          # Data Access Objects
│   │   │       ├── model/        # Entités (POJO)
│   │   │       ├── service/      # Services métier
│   │   │       ├── jdbc/         # Configuration JDBC
│   │   │       └── Main.java
│   │   └── resources/
│   │       └── database.properties
│   └── test/
│       └── java/                 # Code de test
│           └── com/example/dao/
│               ├── EtudiantDAOTest.java
│               ├── MasterDAOTest.java
│               ├── SalleDAOTest.java
│               ├── NoteDAOTest.java
│               ├── InscriptionDAOTest.java
│               └── EnseignementDAOTest.java
├── test.sh                       # Script pour exécuter les tests
├── compile.sh                    # Script de compilation (TP 4)
├── run.sh                        # Script d'exécution (TP 4)
└── README.md                     # Documentation principale
```

## 🧪 Classes de Test Créées

Six classes de test ont été créées pour couvrir les principaux DAO :

1. **`EtudiantDAOTest`** : Tests CRUD pour la gestion des étudiants
2. **`MasterDAOTest`** : Tests CRUD pour la gestion des masters
3. **`SalleDAOTest`** : Tests CRUD pour la gestion des salles (clé composite)
4. **`NoteDAOTest`** : Tests CRUD pour la gestion des notes
5. **`InscriptionDAOTest`** : Tests CRUD pour la gestion des inscriptions
6. **`EnseignementDAOTest`** : Tests CRUD pour la gestion des enseignements (clé composite)

### Types de Tests

Chaque classe de test contient :

- **Tests unitaires** : Vérification des opérations CRUD de base (save, findById, update, delete, findAll)
- **Tests d'intégration** : Utilisation d'une base de données **H2 en mémoire** pour simuler un environnement réaliste sans dépendre de MySQL

### Annotations JUnit 5 Utilisées

- `@Test` : Marque une méthode comme test
- `@BeforeAll` : Exécuté une fois avant tous les tests (setup de la base H2)
- `@BeforeEach` : Exécuté avant chaque test (nettoyage des données)
- `@AfterAll` : Exécuté après tous les tests (fermeture de la connexion)
- `@DisplayName` : Nom descriptif du test
- `@TestMethodOrder` et `@Order` : Ordre d'exécution des tests

## 🚀 Exécution des Tests

### Méthode 1 : Avec le script `test.sh` (Mac/Linux)

```bash
cd tp-jdbc-univ
./test.sh
```

### Méthode 2 : Avec Maven directement

```bash
cd tp-jdbc-univ
mvn test
```

### Méthode 3 : Avec VS Code

1. Ouvrez le projet dans VS Code
2. Assurez-vous que l'extension **"Extension Pack for Java"** est installée
3. VS Code détectera automatiquement le `pom.xml` et importera le projet Maven
4. Ouvrez la vue **"Testing"** (icône de tube à essai dans la barre latérale)
5. Cliquez sur **"Run All Tests"** pour exécuter tous les tests
6. Vous pouvez aussi cliquer sur l'icône "Run Test" à côté de chaque méthode de test

### Méthode 4 : Exécuter un seul test

Dans VS Code, ouvrez une classe de test et cliquez sur l'icône "Run Test" à côté d'une méthode annotée `@Test`.

## 📊 Résultats Attendus

Tous les tests devraient passer au vert (✓) :

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.example.dao.EtudiantDAOTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.example.dao.MasterDAOTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.example.dao.SalleDAOTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.example.dao.NoteDAOTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.example.dao.InscriptionDAOTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.example.dao.EnseignementDAOTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 24, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] BUILD SUCCESS
```

## 🔧 Configuration Maven (pom.xml)

Le fichier `pom.xml` contient :

- **JUnit 5 (Jupiter)** version 5.10.0 pour les tests
- **MySQL Connector/J** version 8.0.33 pour la connexion à MySQL
- **H2 Database** version 2.2.224 pour les tests en mémoire
- **Maven Surefire Plugin** pour exécuter les tests

## 🛠️ Dépannage

### Erreur : "Maven not found"

Installez Maven sur votre système :
- **Mac** : `brew install maven`
- **Linux** : `sudo apt install maven`
- **Windows** : Téléchargez depuis https://maven.apache.org/download.cgi

### Erreur : "Tests not detected in VS Code"

1. Rechargez la fenêtre VS Code (Cmd+Shift+P → "Reload Window")
2. Vérifiez que l'extension "Extension Pack for Java" est installée
3. Supprimez le dossier `.vscode` et laissez VS Code le recréer

### Erreur de compilation

Exécutez `mvn clean compile` pour nettoyer et recompiler le projet.

## 📚 Concepts Clés du TP 5

- **Tests unitaires** : Vérification isolée d'une unité de code (méthode, classe)
- **Tests d'intégration** : Vérification du fonctionnement avec d'autres composants (base de données)
- **Base H2 en mémoire** : Base de données légère pour les tests, ne nécessite pas MySQL
- **Assertions JUnit** : `assertEquals`, `assertNotNull`, `assertNull`, `assertThrows`
- **Cycle de vie des tests** : `@BeforeAll`, `@BeforeEach`, `@AfterEach`, `@AfterAll`

## 📝 Livrable du TP 5

Le projet contient :

✅ Un fichier `pom.xml` configuré avec JUnit 5  
✅ Le code de production du TP 4 dans `src/main/java`  
✅ Six classes de test dans `src/test/java/com/example/dao`  
✅ Des tests unitaires et d'intégration couvrant les cas normaux et d'erreur  
✅ Un script `test.sh` pour exécuter les tests facilement  

## 🎯 Prochaines Étapes

- Ajouter des tests pour les classes de service (`EtudiantService`, `InscriptionService`)
- Augmenter la couverture de code avec des tests supplémentaires
- Intégrer les tests dans un pipeline CI/CD (GitHub Actions, GitLab CI)

---

**Auteur** : Généré automatiquement  
**Date** : 2025
