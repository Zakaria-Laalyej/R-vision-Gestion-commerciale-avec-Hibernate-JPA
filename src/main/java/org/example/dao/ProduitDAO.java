package org.example.dao;

import org.example.entities.Produit;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProduitDAO extends GenericDAO<Produit, Long> {

    public ProduitDAO() {
        super(Produit.class);
    }

    public List<Produit> findByNamedQueryPriceAbove(double minPrix) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        try {
            return em.createNamedQuery("Produit.findByPriceGreaterThan", Produit.class)
                     .setParameter("minPrix", minPrix)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Produit> findWithCriteria(Double minPrix, int page, int pageSize) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Produit> cq = cb.createQuery(Produit.class);
            Root<Produit> root = cq.from(Produit.class);

            if (minPrix != null) {
                cq.where(cb.gt(root.get("prix"), minPrix));
            }
            cq.orderBy(cb.asc(root.get("nom")));

            TypedQuery<Produit> query = em.createQuery(cq);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
