package com.algaworks.pedidovenda.repository;

import com.algaworks.pedidovenda.model.Produto;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.io.Serializable;

public class Produtos implements Serializable {

    @Inject
    private EntityManager manager;

    public Produto guardar(Produto produto) {
        EntityTransaction trx = manager.getTransaction();
        trx.begin();
        produto = manager.merge(produto);
        trx.commit();
        return produto;
    }

    public Produto porSku(String sku) {
        try {
            return manager.createQuery("from Produto where upper(sku) = :sku", Produto.class)
                    .setParameter("sku", sku.toUpperCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
