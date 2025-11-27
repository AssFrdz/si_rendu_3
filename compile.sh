#!/bin/bash

# Script de compilation pour le projet tp-jdbc-univ
# Ce script compile tous les fichiers Java du projet

echo "=== Compilation du projet tp-jdbc-univ ==="
echo ""

# Créer le dossier bin s'il n'existe pas
mkdir -p bin

# Trouver tous les fichiers .java et les compiler
echo "Recherche et compilation de tous les fichiers Java..."
find src -name "*.java" > sources.txt

# Compiler avec le classpath incluant le dossier src pour database.properties
javac -d bin -cp "src:lib/*" @sources.txt

# Vérifier si la compilation a réussi
if [ $? -eq 0 ]; then
    echo ""
    echo "✓ Compilation réussie !"
    echo "Les fichiers .class sont dans le dossier 'bin/'"
    echo ""
    echo "Pour exécuter le programme, utilisez : ./run.sh"
else
    echo ""
    echo "✗ Erreur de compilation"
    exit 1
fi

# Nettoyer le fichier temporaire
rm sources.txt
