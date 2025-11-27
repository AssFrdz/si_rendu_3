#!/bin/bash

# Script pour exécuter les tests JUnit avec Maven

echo "=== Exécution des tests JUnit 5 avec Maven ==="
echo ""

# Vérifier si Maven est installé
if ! command -v mvn &> /dev/null
then
    echo "✗ Maven n'est pas installé sur votre système."
    echo "Veuillez installer Maven : https://maven.apache.org/install.html"
    exit 1
fi

# Exécuter les tests
echo "Lancement des tests..."
mvn test

# Vérifier le résultat
if [ $? -eq 0 ]; then
    echo ""
    echo "✓ Tous les tests sont passés avec succès !"
else
    echo ""
    echo "✗ Certains tests ont échoué. Consultez les logs ci-dessus."
    exit 1
fi
