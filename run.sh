echo "Compilation du projet..."
javac -d bin -cp "lib/sqlite-jdbc-3.50.3.0.jar" $(find src -name "*.java") || { echo "Erreur compilation"; exit 1; }

echo "Lancement de l'application..."
java -cp "bin:lib/sqlite-jdbc-3.50.3.0.jar" Main
