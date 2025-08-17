package model;

public class Vol {
    private String numeroVol;
    private String destination;
    private String dateDepart;   // yyyy-MM-dd
    private String heureDepart;  // HH:mm
    private String heureArrivee; // HH:mm
    private int capacite;

    public Vol(String numeroVol, String destination, String dateDepart,
               String heureDepart, String heureArrivee, int capacite) {
        this.numeroVol = numeroVol;
        this.destination = destination;
        this.dateDepart = dateDepart;
        this.heureDepart = heureDepart;
        this.heureArrivee = heureArrivee;
        this.capacite = capacite;
    }

    // getters & setters
    public String getNumeroVol() { return numeroVol; }
    public void setNumeroVol(String numeroVol) { this.numeroVol = numeroVol; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getDateDepart() { return dateDepart; }
    public void setDateDepart(String dateDepart) { this.dateDepart = dateDepart; }
    public String getHeureDepart() { return heureDepart; }
    public void setHeureDepart(String heureDepart) { this.heureDepart = heureDepart; }
    public String getHeureArrivee() { return heureArrivee; }
    public void setHeureArrivee(String heureArrivee) { this.heureArrivee = heureArrivee; }
    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }

    @Override
    public String toString() {
        return numeroVol + " -> " + destination + " | " + dateDepart + " " + heureDepart +
               " - " + heureArrivee + " | Capacité: " + capacite;
    }
}
