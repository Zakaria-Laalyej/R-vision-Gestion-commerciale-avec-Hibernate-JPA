package org.example.service;

import org.example.dao.GenericDAO;
import java.io.Serializable;
import java.util.List;

public class CrudService<T, ID extends Serializable> {
    protected final GenericDAO<T, ID> dao;

    public CrudService(Class<T> entityClass) {
        this.dao = new GenericDAO<>(entityClass);
    }

    public T getById(ID id) {
        return dao.find(id);
    }

    public List<T> getAll() {
        return dao.findAll();
    }

    public void save(T entity) {
        dao.create(entity);
    }

    public T update(T entity) {
        return dao.update(entity);
    }

    public void delete(T entity) {
        dao.delete(entity);
    }
}
