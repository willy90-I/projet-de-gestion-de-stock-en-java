package model;

public class AgenceVoyage {
    private int id;
    private String nom;
    private String contact;
    private String offre;

    public AgenceVoyage(int id, String nom, String contact, String offre) {
        this.id = id;
        this.nom = nom;
        this.contact = contact;
        this.offre = offre;
    }

    // getters & setters omitted for brevity
}
