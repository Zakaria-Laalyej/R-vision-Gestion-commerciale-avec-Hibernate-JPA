package org.example.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fournisseurs")
public class Fournisseur extends User {

    @Column(length = 30)
    private String siret;

    public Fournisseur() {}

    public Fournisseur(String nom, String email, String siret) {
        super(nom, email);
        this.siret = siret;
    }

    public String getSiret() { return siret; }
    public void setSiret(String siret) { this.siret = siret; }

    @Override
    public String toString() {
        return "Fournisseur{id=" + getId() + ", nom='" + getNom() + "', siret='" + siret + "'}";
    }
}
