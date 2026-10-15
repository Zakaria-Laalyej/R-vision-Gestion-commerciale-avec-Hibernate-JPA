package org.example.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dateCommande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommandeProduit> lignes = new ArrayList<>();

    public Commande() {}

    public Commande(LocalDate dateCommande, Client client) {
        this.dateCommande = dateCommande;
        this.client = client;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDate dateCommande) { this.dateCommande = dateCommande; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public List<CommandeProduit> getLignes() { return lignes; }
    public void setLignes(List<CommandeProduit> lignes) { this.lignes = lignes; }

    public void addLigne(Produit produit, int quantite, double prixUnitaire) {
        CommandeProduit cp = new CommandeProduit(this, produit, quantite, prixUnitaire);
        lignes.add(cp);
    }

    @Override
    public String toString() {
        return "Commande{id=" + id + ", date=" + dateCommande + ", client=" + (client != null ? client.getNom() : "") + "}";
    }
}
