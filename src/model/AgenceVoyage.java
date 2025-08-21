package model;

public class AgenceVoyage {
    private long id;
    private String nom;
    private String contact;
    private String offres;

    public AgenceVoyage() {}
    public AgenceVoyage(long id, String nom, String contact, String offres) {
        this.id = id; this.nom = nom; this.contact = contact; this.offres = offres;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getOffres() { return offres; }
    public void setOffres(String offres) { this.offres = offres; }

    @Override
    public String toString() {
        return String.format("Agence[id = %d, nom = %s, contact = %s, offres = %s]", id, nom, contact, offres);
    }
}
 
