# Projet de gestion de stock en java

## 📌 Description
C'est une application Java permettant de gérer les vols, passagers, agences et réservations.  
Il inclut des fonctionnalités complètes pour ajouter, consulter, modifier et supprimer des entités, et générer des rapports/statistiques sur les réservations.


## 🗂 Structure du projet

projet-de-gestion-de-stock-en-java
│
├── lib/ # Librairies externes
│ └── sqlite-jdbc-3.50.3.0.jar # Driver JDBC SQLite
│
├── src/
│ ├── dao/ # Classes DAO pour gérer la BDD
│ │ ├── VolDAO.java
│ │ ├── PassagerDAO.java
│ │ ├── ReservationDAO.java
│ │ └── AgenceVoyageDAO.java
│ │
│ ├── model/ # Classes métier (entités)
│ │ ├── Vol.java
│ │ ├── Passager.java
│ │ ├── Reservation.java
│ │ └── AgenceVoyage.java
│ │
│ ├── service/ # Logique métier
│ │ └── GestionVoyageService.java
│ │
│ ├── ui/ # Interface console
│ │ └── MainUI.java
│ │
│ └── Main.java # Point d’entrée
│
└── bin/ # Classes compilées

## ⚙️ Fonctionnalités

### ✈️ Gestion des Vols
- Ajouter, lister, rechercher par destination et supprimer un vol

### 👤 Gestion des Passagers
- Ajouter, lister et supprimer des passagers

### 🏢 Gestion des Agences
- Ajouter, lister et supprimer des agences

### 📑 Gestion des Réservations
- Réserver un vol pour un passager
- Annuler une réservation
- Lister toutes les réservations avec détails (vol, passager, statut)

### 📊 Rapports / Statistiques
- Top destinations par nombre de réservations confirmées
- Statistiques du nombre de passagers par destination

## 💻 Installation & Exécution

1. **Cloner le projet :
   
git clone <url-du-repo>
cd projet-gestion-voyages

2. **Ajouter SQLite JDBC dans le classpath : lib/sqlite-jdbc-3.50.3.0.jar

3. Compiler le projet : javac -cp lib/sqlite-jdbc-3.50.3.0.jar -d bin src/**/*.java

4. Lancer l’application : java -cp bin:lib/sqlite-jdbc-3.50.3.0.jar Main

🔧 Technologies

.Java 17+

.SQLite via JDBC

.Interface console


