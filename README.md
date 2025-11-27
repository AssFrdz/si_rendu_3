# TP JDBC - Projet Universitaire

Projet Java complet avec JDBC pour la gestion d'une base de données universitaire.

## 📁 Structure du Projet

```
tp-jdbc-univ/
├── src/                          # Code source Java
│   ├── database.properties       # Configuration de la base de données
│   └── com/example/
│       ├── jdbc/                 # Classes de connexion JDBC
│       │   ├── ConfigConnection.java
│       │   └── TestJDBC.java
│       ├── model/                # Classes POJO (entités)
│       │   ├── Etudiant.java
│       │   ├── Master.java
│       │   ├── Enseignement.java
│       │   ├── Inscription.java
│       │   ├── Note.java
│       │   ├── Salle.java
│       │   └── Reservation.java
│       ├── dao/                  # Data Access Objects
│       │   ├── EtudiantDAO.java
│       │   ├── MasterDAO.java
│       │   ├── EnseignementDAO.java
│       │   ├── InscriptionDAO.java
│       │   ├── NoteDAO.java
│       │   ├── SalleDAO.java
│       │   └── ReservationDAO.java
│       ├── service/              # Logique métier
│       │   ├── EtudiantService.java
│       │   └── InscriptionService.java
│       └── Main.java             # Programme principal (menu interactif)
├── lib/                          # Bibliothèques externes
│   └── mysql-connector-j-8.0.33.jar
├── td4_uniiv_data.sql            # Script de création de la base de données
├── Script_Insertion_BDExemple.sql # Script d'insertion des données de test
├── compile.sh                    # Script de compilation
├── run.sh                        # Script d'exécution du programme principal
├── run-testjdbc.sh               # Script d'exécution de TestJDBC
└── README.md                     # Ce fichier

```

## 🚀 Installation et Configuration

### 1. Prérequis

- **Java JDK 8 ou supérieur** installé sur votre machine
- **MySQL Server** installé et en cours d'exécution
- **Terminal** (sur Mac/Linux) ou **Git Bash** (sur Windows)

### 2. Configuration de la Base de Données

#### Étape 1 : Créer la base de données

Connectez-vous à MySQL et créez la base de données :

```bash
mysql -u root -p
```

Puis dans MySQL :

```sql
CREATE DATABASE testtp;
EXIT;
```

#### Étape 2 : Exécuter les scripts SQL

Depuis le terminal, dans le dossier du projet :

```bash
# Créer les tables
mysql -u root -p testtp < td4_uniiv_data.sql

# Insérer les données de test
mysql -u root -p testtp < Script_Insertion_BDExemple.sql
```

#### Étape 3 : Configurer la connexion

Éditez le fichier `src/database.properties` avec vos identifiants MySQL :

```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/testtp
utilisateur=root
mdp=VOTRE_MOT_DE_PASSE
```

**Remplacez `VOTRE_MOT_DE_PASSE`** par votre mot de passe MySQL.

## 🔨 Compilation et Exécution

### Sur Mac/Linux

#### Compilation

Depuis le dossier du projet, exécutez :

```bash
./compile.sh
```

Ce script va :
- Créer un dossier `bin/` pour les fichiers compilés
- Compiler tous les fichiers `.java` du projet
- Inclure le connecteur MySQL dans le classpath

#### Exécution du programme principal (Main)

```bash
./run.sh
```

Cela lance le menu interactif pour gérer les étudiants.

#### Exécution de TestJDBC

```bash
./run-testjdbc.sh
```

Cela lance le programme de test JDBC qui effectue diverses opérations sur la base de données.

### Sur Windows

Utilisez **Git Bash** ou **WSL** pour exécuter les scripts `.sh`.

Ou compilez et exécutez manuellement :

```bash
# Compilation
mkdir bin
javac -d bin -cp "src;lib/*" src/com/example/**/*.java src/com/example/*.java

# Exécution de Main
java -cp "bin;src;lib/*" com.example.Main

# Exécution de TestJDBC
java -cp "bin;src;lib/*" com.example.jdbc.TestJDBC
```

**Note :** Sur Windows, utilisez `;` au lieu de `:` pour séparer les chemins dans le classpath.

## 📝 Utilisation

### Programme Principal (Main)

Le programme principal offre un menu interactif pour :

1. Ajouter un étudiant
2. Rechercher un étudiant par nom
3. Afficher tous les étudiants
4. Modifier un étudiant
5. Supprimer un étudiant
6. Quitter

### TestJDBC

Le programme `TestJDBC` effectue automatiquement :
- Des mises à jour de la base de données
- Des requêtes préparées
- Des tests de transactions

## 🛠️ Dépannage

### Erreur : "database.properties not found"

- Vérifiez que le fichier `src/database.properties` existe
- Vérifiez que vous exécutez les scripts depuis la racine du projet

### Erreur : "Access denied for user"

- Vérifiez vos identifiants MySQL dans `src/database.properties`
- Vérifiez que MySQL est en cours d'exécution

### Erreur : "Communications link failure"

- Vérifiez que MySQL est démarré
- Vérifiez l'URL de connexion dans `database.properties`

### Erreur de compilation

- Vérifiez que Java JDK est installé : `java -version` et `javac -version`
- Vérifiez que vous êtes dans le bon dossier (racine du projet)

## 📚 Architecture du Projet

Le projet suit une architecture en couches :

1. **Couche Modèle (model/)** : Classes POJO représentant les entités de la base de données
2. **Couche DAO (dao/)** : Objets d'accès aux données, gèrent les opérations CRUD
3. **Couche Service (service/)** : Logique métier et validation
4. **Couche JDBC (jdbc/)** : Configuration de connexion et tests



