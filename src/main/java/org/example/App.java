package org.example;

import org.example.dao.GenericDAO;
import org.example.dao.ProduitDAO;
import org.example.entities.*;
import org.example.service.CrudService;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class App {

    public static void main(String[] args) {
        System.out.println("#####################################################################");
        System.out.println("#  RÉVISION : GESTION COMMERCIALE AVEC HIBERNATE 5.6 & JPA 2.2      #");
        System.out.println("#####################################################################\n");

        CrudService<Categorie, Long> catService = new CrudService<>(Categorie.class);
        ProduitDAO produitDao = new ProduitDAO();
        CrudService<Client, Long> clientService = new CrudService<>(Client.class);
        CrudService<Fournisseur, Long> fourService = new CrudService<>(Fournisseur.class);
        CrudService<Commande, Long> comService = new CrudService<>(Commande.class);

        // 1. Insertion Catégorie
        Categorie cat1 = new Categorie("Informatique");
        Categorie cat2 = new Categorie("Bureautique");
        catService.save(cat1);
        catService.save(cat2);
        System.out.println("1. Catégories insérées : " + cat1.getNom() + ", " + cat2.getNom());

        // 2. Insertion Produits
        Produit p1 = new Produit("PC Portable Mac", 14500.0, 15, cat1);
        Produit p2 = new Produit("Souris Sans Fil Logitech", 250.0, 50, cat1);
        Produit p3 = new Produit("Écran 20 pouces 4K", 3200.0, 8, cat1);
        Produit p4 = new Produit("Chaise Ergonomique", 1800.0, 20, cat2);
        produitDao.create(p1);
        produitDao.create(p2);
        produitDao.create(p3);
        produitDao.create(p4);
        System.out.println("2. Produits créés avec succès !");

        // 3. Héritage JOINED : User, Client, Fournisseur
        Client client1 = new Client("Zakaria Laalyej", "zakaria@example.com", "Marrakech, Maroc");
        Fournisseur four1 = new Fournisseur("Tech Distri SARL", "contact@techdistri.ma", "0657554245");
        clientService.save(client1);
        fourService.save(four1);
        System.out.println("3. Héritage JOINED validé : Client " + client1.getNom() + " & Fournisseur " + four1.getNom());

        // 4. Commande avec clé composite CommandeProduit
        EntityManager em = GenericDAO.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Client clientManaged = em.find(Client.class, client1.getId());
        Produit p1Managed = em.find(Produit.class, p1.getId());
        Produit p2Managed = em.find(Produit.class, p2.getId());

        Commande commande = new Commande(LocalDate.now(), clientManaged);
        em.persist(commande);

        CommandeProduit cp1 = new CommandeProduit(commande, p1Managed, 1, p1Managed.getPrix());
        CommandeProduit cp2 = new CommandeProduit(commande, p2Managed, 2, p2Managed.getPrix());
        em.persist(cp1);
        em.persist(cp2);
        em.getTransaction().commit();
        em.close();
        System.out.println("4. Commande avec entité associative CommandeProduit créée !");

        // 5. Requête Nommée : prix > 1000 DH
        System.out.println("\n5. Test Requête Nommée (Prix > 1000 DH) :");
        List<Produit> chers = produitDao.findByNamedQueryPriceAbove(1000.0);
        chers.forEach(p -> System.out.println("   * " + p.getNom() + " -> " + p.getPrix() + " DH"));

        // 6. Criteria API avec pagination (Page 1, taille 2)
        System.out.println("\n6. Test Criteria API avec pagination (Page 1, taille 2, minPrix=100) :");
        List<Produit> pageProduits = produitDao.findWithCriteria(100.0, 1, 2);
        pageProduits.forEach(p -> System.out.println("   * " + p.getNom() + " -> " + p.getPrix() + " DH"));

        System.out.println("\nApplication de Révision terminée avec succès !");
    }
}
