package com.algaworks.pedidovenda.repository;


import com.algaworks.pedidovenda.model.Categoria;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class Categorias implements Serializable {

    @Inject
    private EntityManager manager;

    public List<Categoria> raizes() {
        return manager.createQuery("from Categoria", Categoria.class).getResultList();
    }
}
