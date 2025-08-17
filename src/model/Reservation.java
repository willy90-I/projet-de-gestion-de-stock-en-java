package model;

public class Reservation {
    private int id;
    private String numeroVol;
    private int passagerId;
    private String statut;        // ex: CONFIRME, ANNULE
    private String dateReservation; // yyyy-MM-dd

    public Reservation(int id, String numeroVol, int passagerId, String statut, String dateReservation) {
        this.id = id;
        this.numeroVol = numeroVol;
        this.passagerId = passagerId;
        this.statut = statut;
        this.dateReservation = dateReservation;
    }

    public Reservation(String numeroVol, int passagerId, String statut, String dateReservation) {
        this(0, numeroVol, passagerId, statut, dateReservation);
    }

    // getters & setters...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNumeroVol() { return numeroVol; }
    public void setNumeroVol(String numeroVol) { this.numeroVol = numeroVol; }
    public int getPassagerId() { return passagerId; }
    public void setPassagerId(int passagerId) { this.passagerId = passagerId; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public String getDateReservation() { return dateReservation; }
    public void setDateReservation(String dateReservation) { this.dateReservation = dateReservation; }

    @Override
    public String toString() {
        return id + " | Vol: " + numeroVol + " | PassagerID: " + passagerId + " | " + statut + " | " + dateReservation;
    }
}

