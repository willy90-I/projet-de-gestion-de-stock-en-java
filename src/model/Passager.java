package model;

public class Passager {
    private long id;
    private String nom;
    private String email;
    private String passport;

    public Passager() {}
    public Passager(long id, String nom, String email, String passport) {
        this.id = id; this.nom = nom; this.email = email; this.passport = passport;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassport() { return passport; }
    public void setPassport(String passport) { this.passport = passport; }

    @Override
    public String toString() {
        return String.format("Passager[id = %d, nom = %s, email = %s, passport = %s]", id, nom, email, passport);
    }
}
