package com.algaworks.pedidovenda.repository;


import com.algaworks.pedidovenda.model.Categoria;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class Categorias implements Serializable {

    @Inject
    private EntityManager manager;

    public Categoria porId(Long id) {
        return manager.find(Categoria.class, id);
    }

    public List<Categoria> raizes() {
        return manager.createQuery("from Categoria where categoriaPai is null", Categoria.class).getResultList();
    }

    public List<Categoria> subcategoriasDe(Categoria categoriaPai) {
        return manager.createQuery("from Categoria where categoriaPai = :raiz", Categoria.class)
                .setParameter("raiz", categoriaPai)
                .getResultList();
    }
}
