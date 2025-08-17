package model;

public class Passager {
    private int id;
    private String nom;
    private String email;
    private String numeroPasseport;

    public Passager(int id, String nom, String email, String numeroPasseport) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.numeroPasseport = numeroPasseport;
    }

    public Passager(String nom, String email, String numeroPasseport) {
        this(0, nom, email, numeroPasseport);
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNumeroPasseport() { return numeroPasseport; }
    public void setNumeroPasseport(String numeroPasseport) { this.numeroPasseport = numeroPasseport; }

    @Override
    public String toString() {
        return id + " | " + nom + " | " + email + " | Passport: " + numeroPasseport;
    }
}
