package org.example.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "commandes_produits")
public class CommandeProduit implements Serializable {

    @EmbeddedId
    private CommandeProduitPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("commandeId")
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("produitId")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @Column(nullable = false)
    private int quantite;

    @Column(nullable = false)
    private double prixUnitaire;

    public CommandeProduit() {}

    public CommandeProduit(Commande commande, Produit produit, int quantite, double prixUnitaire) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.id = new CommandeProduitPK(commande.getId(), produit.getId());
    }

    public CommandeProduitPK getId() { return id; }
    public void setId(CommandeProduitPK id) { this.id = id; }

    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    @Override
    public String toString() {
        return "CommandeProduit{produit=" + (produit != null ? produit.getNom() : "") + ", qte=" + quantite + ", pu=" + prixUnitaire + "}";
    }
}
