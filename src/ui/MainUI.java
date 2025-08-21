package ui;

import service.GestionVoyageService;
import model.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainUI {
    private GestionVoyageService service = new GestionVoyageService();
    private Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public void start() {
        System.out.println("Bienvenue au système de gestion des réservations");
        boolean loop = true;
        while (loop) {
            showMenu();
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> creerVol();
                    case "2" -> listerVols();
                    case "3" -> rechercherVols();
                    case "4" -> supprimerVol();
                    case "5" -> gererPassagers();
                    case "6" -> faireReservation();
                    case "7" -> listerReservationsUI();
                    case "8" -> annulerReservation();
                    case "9" -> rapports();
                    case "10" -> gererAgences();
                    case "0" -> { loop = false; System.out.println("Au revoir !"); }
                    default -> System.out.println("Choix invalide");
                }
            } catch (Exception e) {
                System.err.println("Erreur: " + e.getMessage());
            }
        }
    }

    private void showMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Ajouter un vol");
        System.out.println("2. Lister tous les vols");
        System.out.println("3. Rechercher vols par destination");
        System.out.println("4. Supprimer un vol");
        System.out.println("5. Gérer passagers ");
        System.out.println("6. Réserver un vol");
        System.out.println("7. Lister toutes les réservations");
        System.out.println("8. Annuler une réservation");
        System.out.println("9. Rapports / statistiques");
        System.out.println("10. Gérer agences de voyage");
        System.out.println("0. Quitter");
        System.out.print("Votre choix: ");
    }

    private void listerVols() throws SQLException {
        List<Vol> vols = service.tousLesVols();
        if (vols.isEmpty()) System.out.println("Aucun vol enregistré.");
        else vols.forEach(System.out::println);
    }

    private void rechercherVols() throws SQLException {
        System.out.print("Destination (partielle OK): ");
        String dest = sc.nextLine();
        List<Vol> vols = service.rechercherVolsParDestination(dest);
        if (vols.isEmpty()) System.out.println("Aucun vol trouvé pour: " + dest);
        else vols.forEach(System.out::println);
    }

    private void creerVol() throws SQLException {
        System.out.print("Numéro vol: "); String num = sc.nextLine();
        System.out.print("Destination: "); String dest = sc.nextLine();
        System.out.print("Date/heure départ (YYYY-MM-DD T(HH:MM)): "); LocalDateTime d = LocalDateTime.parse(sc.nextLine(), fmt);
        System.out.print("Date/heure arrivée (YYYY-MM-DD T(HH:MM)): "); LocalDateTime a = LocalDateTime.parse(sc.nextLine(), fmt);
        System.out.print("Capacité: "); int cap = Integer.parseInt(sc.nextLine());
        Vol v = service.creerVol(num, dest, d, a, cap);
        System.out.println("Vol ajouter: " + v);
    }

    private void supprimerVol() throws SQLException {
        System.out.print("Id vol à supprimer: "); long id = Long.parseLong(sc.nextLine());
        boolean ok = service.supprimerVol(id);
        System.out.println(ok ? "Vol supprimé." : "Vol introuvable.");
    }

    private void gererPassagers() throws SQLException {
        System.out.println("a. Ajouter passager\nb. Lister passagers\nc. Supprimer passager\nEntrez choix: "); String c = sc.nextLine();
        switch (c) {
            case "a" -> {
                System.out.print("Nom: "); String nom = sc.nextLine();
                System.out.print("Email: "); String em = sc.nextLine();
                System.out.print("Passport: "); String pp = sc.nextLine();
                Passager p = service.creerPassager(nom, em, pp);
                System.out.println("Passager créé: " + p);
            }
            case "b" -> {
                List<Passager> ps = service.tousLesPassagers(); ps.forEach(System.out::println);
            }
            case "c" -> {
                System.out.print("Id passager à supprimer: "); long id = Long.parseLong(sc.nextLine());
                boolean ok = service.supprimerPassager(id); System.out.println(ok ? "Passager supprimé." : "Introuvable.");
            }
            default -> System.out.println("Choix invalide");
        }
    }

    private void faireReservation() throws SQLException {
        System.out.print("Id vol: "); long vid = Long.parseLong(sc.nextLine());
        System.out.print("Id passager: "); long pid = Long.parseLong(sc.nextLine());
        try {
            Reservation r = service.reserver(vid, pid);
            System.out.println("Réservation créée: " + r);
        } catch (IllegalStateException ise) {
            System.out.println("Impossible de réserver: " + ise.getMessage());
        }
    }

    private void listerReservationsUI() throws SQLException {
    List<String> reservations = service.listerReservationsAsList();
    reservations.forEach(System.out::println);
}


    private void annulerReservation() throws SQLException {
        System.out.print("Id réservation: "); long id = Long.parseLong(sc.nextLine());
        boolean ok = service.annulerReservation(id);
        System.out.println(ok ? "Réservation annulée." : "Réservation introuvable ou erreur.");
    }

    private void rapports() throws SQLException {
        System.out.println("Top destinations (3):");
        Map<String, Long> top = service.topDestinations(3);
        top.forEach((k,v) -> System.out.println(k + " -> " + v + " réservations"));
        System.out.println("\nStats passagers par destination:");
        service.statistiquesPassagersParDestination().forEach((k,v) -> System.out.println(k + " : " + v));
    }

    private void gererAgences() throws SQLException {
        System.out.println("a. Ajouter agence\nb. Lister agences\nc. Supprimer agence\nEntrez choix: "); String c = sc.nextLine();
        switch (c) {
            case "a" -> {
                System.out.print("Nom agence: "); String nom = sc.nextLine();
                System.out.print("Contact: "); String ct = sc.nextLine();
                System.out.print("Offres: "); String of = sc.nextLine();
                System.out.println("Agence créée: " + service.creerAgence(nom, ct, of));
            }
            case "b" -> service.toutesAgences().forEach(System.out::println);
            case "c" -> {
                System.out.print("Id agence: "); long id = Long.parseLong(sc.nextLine());
                System.out.println(service.supprimerAgence(id) ? "Supprimée" : "Introuvable");
            }
            default -> System.out.println("Choix invalide");
        }
    }
}
