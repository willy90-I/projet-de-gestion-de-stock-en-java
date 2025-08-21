package model;

import java.time.LocalDateTime;

public class Vol {
    private long id;
    private String numero;
    private String destination;
    private LocalDateTime depart;
    private LocalDateTime arrivee;
    private int capacite;
    private int placesReservees;

    public Vol() {}

    public Vol(long id, String numero, String destination, LocalDateTime depart, LocalDateTime arrivee, int capacite) {
        this.id = id;
        this.numero = numero;
        this.destination = destination;
        this.depart = depart;
        this.arrivee = arrivee;
        this.capacite = capacite;
        this.placesReservees = 0;
    }

    // getters/setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public LocalDateTime getDepart() { return depart; }
    public void setDepart(LocalDateTime depart) { this.depart = depart; }
    public LocalDateTime getArrivee() { return arrivee; }
    public void setArrivee(LocalDateTime arrivee) { this.arrivee = arrivee; }
    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }
    public int getPlacesReservees() { return placesReservees; }
    public void setPlacesReservees(int placesReservees) { this.placesReservees = placesReservees; }

    public int placesDisponibles() {
        return capacite - placesReservees;
    }

    @Override
    public String toString() {
        return String.format("Vol[id = %d, num = %s, dest = %s, depart = %s, arrivee = %s, cap = %d, reserv = %d]",
                id, numero, destination, depart, arrivee, capacite, placesReservees);
    }
}


