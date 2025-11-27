#!/bin/bash

# Script d'exécution pour TestJDBC
# Ce script exécute le programme de test JDBC (TestJDBC.java)

echo "=== Exécution de TestJDBC ==="
echo ""

# Vérifier si le dossier bin existe
if [ ! -d "bin" ]; then
    echo "✗ Le dossier 'bin' n'existe pas."
    echo "Veuillez d'abord compiler le projet avec : ./compile.sh"
    exit 1
fi

# Vérifier si la classe TestJDBC existe
if [ ! -f "bin/com/example/jdbc/TestJDBC.class" ]; then
    echo "✗ La classe TestJDBC n'a pas été compilée."
    echo "Veuillez d'abord compiler le projet avec : ./compile.sh"
    exit 1
fi

# Exécuter le programme avec le classpath incluant bin, src (pour database.properties) et lib
echo "Lancement de TestJDBC..."
echo ""
java -cp "bin:src:lib/*" com.example.jdbc.TestJDBC
