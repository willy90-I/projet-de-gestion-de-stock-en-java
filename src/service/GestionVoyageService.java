package service;

import dao.*;
import model.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class GestionVoyageService {
    private VolDAO volDAO = new VolDAO();
    private PassagerDAO passagerDAO = new PassagerDAO();
    private ReservationDAO reservationDAO = new ReservationDAO();
    private AgenceVoyageDAO agenceDAO = new AgenceVoyageDAO();

    // ---------------- Vols ----------------
    public Vol creerVol(String numero, String dest, LocalDateTime depart, LocalDateTime arrivee, int capacite) throws SQLException {
        Vol v = new Vol(0, numero, dest, depart, arrivee, capacite);
        return volDAO.create(v);
    }

    public List<Vol> rechercherVolsParDestination(String dest) throws SQLException {
        return volDAO.searchByDestination(dest);
    }

    public List<Vol> tousLesVols() throws SQLException {
        return volDAO.findAll();
    }

    public boolean supprimerVol(long id) throws SQLException {
        return volDAO.delete(id);
    }

    // ---------------- Passagers ----------------
    public Passager creerPassager(String nom, String email, String passport) throws SQLException {
        return passagerDAO.create(new Passager(0, nom, email, passport));
    }

    public List<Passager> tousLesPassagers() throws SQLException {
        return passagerDAO.findAll();
    }

    public boolean supprimerPassager(long id) throws SQLException {
        return passagerDAO.delete(id);
    }

    // ---------------- Agences ----------------
    public AgenceVoyage creerAgence(String nom, String contact, String offres) throws SQLException {
        return agenceDAO.create(new AgenceVoyage(0, nom, contact, offres));
    }

    public List<AgenceVoyage> toutesAgences() throws SQLException {
        return agenceDAO.findAll();
    }

    public boolean supprimerAgence(long id) throws SQLException {
        return agenceDAO.delete(id);
    }

    // ---------------- Reservations ----------------
    public Reservation reserver(long volId, long passagerId) throws SQLException, IllegalStateException {
        Vol vol = volDAO.findById(volId);
        if (vol == null) throw new IllegalArgumentException("Vol introuvable");
        if (vol.placesDisponibles() <= 0) throw new IllegalStateException("Plus de places disponibles");
        Reservation r = new Reservation(0, volId, passagerId, Reservation.Statut.CONFIRMEE);
        r = reservationDAO.create(r);
        volDAO.updatePlacesReservees(volId, vol.getPlacesReservees() + 1);
        return r;
    }

    public boolean annulerReservation(long reservationId) throws SQLException {
        Reservation r = reservationDAO.findById(reservationId);
        if (r == null) return false;
        boolean ok = reservationDAO.updateStatus(reservationId, Reservation.Statut.ANNULEE);
        if (ok) {
            Vol vol = volDAO.findById(r.getVolId());
            if (vol != null) volDAO.updatePlacesReservees(vol.getId(), Math.max(0, vol.getPlacesReservees() - 1));
        }
        return ok;
    }

    public List<Reservation> toutesReservations() throws SQLException {
        return reservationDAO.findAll();
    }

    public List<String> listerReservationsAsList() throws SQLException {
        List<Reservation> reservations = reservationDAO.findAll();
        List<String> result = new ArrayList<>();

        if (reservations.isEmpty()) {
            result.add("Aucune réservation trouvée.");
            return result;
        }

        for (Reservation r : reservations) {
            Vol vol = volDAO.findById(r.getVolId());
            Passager passager = passagerDAO.findById(r.getPassagerId());

            StringBuilder sb = new StringBuilder();
            sb.append("Réservation ID: ").append(r.getId()).append("\n");
            sb.append("  Vol: ").append(vol != null ? vol.getNumero() + " -> " + vol.getDestination() : "Vol introuvable").append("\n");
            sb.append("  Passager: ").append(passager != null ? passager.getNom() + " (" + passager.getEmail() + ")" : "Passager introuvable").append("\n");
            sb.append("  Statut: ").append(r.getStatut()).append("\n");
            sb.append("------------------------------");

            result.add(sb.toString());
        }

        return result;
    }

    // ---------------- Rapports simples ----------------
    public Map<String, Long> topDestinations(int topN) throws SQLException {
        List<Reservation> res = reservationDAO.findAll();
        Map<Long, Long> countsByVol = res.stream()
                .filter(r -> r.getStatut() == Reservation.Statut.CONFIRMEE)
                .collect(Collectors.groupingBy(Reservation::getVolId, Collectors.counting()));

        Map<String, Long> destCounts = new HashMap<>();
        for (Map.Entry<Long, Long> e : countsByVol.entrySet()) {
            Vol v = volDAO.findById(e.getKey());
            if (v != null) destCounts.merge(v.getDestination(), e.getValue(), Long::sum);
        }

        return destCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(topN)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    public Map<String, Long> statistiquesPassagersParDestination() throws SQLException {
        List<Reservation> res = reservationDAO.findAll();
        Map<String, Long> map = new HashMap<>();

        for (Reservation r : res) {
            if (r.getStatut() != Reservation.Statut.CONFIRMEE) continue;
            Vol v = volDAO.findById(r.getVolId());
            if (v != null) map.merge(v.getDestination(), 1L, Long::sum);
        }

        return map;
    }
}
