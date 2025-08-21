package model;

public class Reservation {
    public enum Statut { CONFIRMEE, ANNULEE }

    private long id;
    private long volId;
    private long passagerId;
    private Statut statut;

    public Reservation() {}
    public Reservation(long id, long volId, long passagerId, Statut statut) {
        this.id = id; this.volId = volId; this.passagerId = passagerId; this.statut = statut;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getVolId() { return volId; }
    public void setVolId(long volId) { this.volId = volId; }
    public long getPassagerId() { return passagerId; }
    public void setPassagerId(long passagerId) { this.passagerId = passagerId; }
    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }

    @Override
    public String toString() {
        return String.format("Reservation[id = %d, volId = %d, passagerId = %d, statut = %s]", id, volId, passagerId, statut);
    }
}
