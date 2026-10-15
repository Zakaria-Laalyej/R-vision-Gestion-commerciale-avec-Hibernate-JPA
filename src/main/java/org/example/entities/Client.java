package org.example.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "clients")
public class Client extends User {

    @Column(length = 255)
    private String adresseLivraison;

    public Client() {}

    public Client(String nom, String email, String adresseLivraison) {
        super(nom, email);
        this.adresseLivraison = adresseLivraison;
    }

    public String getAdresseLivraison() { return adresseLivraison; }
    public void setAdresseLivraison(String adresseLivraison) { this.adresseLivraison = adresseLivraison; }

    @Override
    public String toString() {
        return "Client{id=" + getId() + ", nom='" + getNom() + "', adresse='" + adresseLivraison + "'}";
    }
}
