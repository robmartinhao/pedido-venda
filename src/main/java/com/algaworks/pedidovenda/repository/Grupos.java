package com.algaworks.pedidovenda.repository;


import com.algaworks.pedidovenda.model.Grupo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class Grupos implements Serializable {

    @Inject
    private EntityManager manager;

    public Grupo porId(Long id) {
        return manager.find(Grupo.class, id);
    }

    public List<Grupo> todos() {
        return manager.createQuery("from Grupo", Grupo.class).getResultList();
    }
}
