#!/bin/bash

# Script d'exécution pour le projet tp-jdbc-univ
# Ce script exécute le programme principal (Main.java)

echo "=== Exécution du projet tp-jdbc-univ ==="
echo ""

# Vérifier si le dossier bin existe
if [ ! -d "bin" ]; then
    echo "✗ Le dossier 'bin' n'existe pas."
    echo "Veuillez d'abord compiler le projet avec : ./compile.sh"
    exit 1
fi

# Vérifier si la classe Main existe
if [ ! -f "bin/com/example/Main.class" ]; then
    echo "✗ La classe Main n'a pas été compilée."
    echo "Veuillez d'abord compiler le projet avec : ./compile.sh"
    exit 1
fi

# Exécuter le programme avec le classpath incluant bin, src (pour database.properties) et lib
echo "Lancement du programme..."
echo ""
java -cp "bin:src:lib/*" com.example.Main
