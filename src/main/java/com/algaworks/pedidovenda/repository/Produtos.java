package com.algaworks.pedidovenda.repository;

import com.algaworks.pedidovenda.model.Categoria;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.filter.ProdutoFilter;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.util.jpa.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Produtos implements Serializable {

    @Inject
    private EntityManager manager;

    public Produto guardar(Produto produto) {
        return manager.merge(produto);
    }

    @Transactional
    public void remover(Produto produto) {
        try {
            produto =porId(produto.getId());
            manager.remove(produto);
            manager.flush();
        } catch (PersistenceException e) {
            throw  new NegocioException("Produto não pode ser excluído.");
        }
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
    public List<Produto> filtrados(ProdutoFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Produto> query = builder.createQuery(Produto.class);

        List<Predicate> predicates = new ArrayList<>();

        Root<Produto> produtoRoot = query.from(Produto.class);
        Fetch<Produto, Categoria> categoriaJoin = produtoRoot.fetch("categoria", JoinType.INNER);
        categoriaJoin.fetch("categoriaPai", JoinType.INNER);

        if (StringUtils.isNotBlank(filtro.getSku())) {
            predicates.add(builder.equal(produtoRoot.get("sku"), filtro.getSku()));
        }

        if (StringUtils.isNotBlank(filtro.getNome())) {
            predicates.add(builder.like(builder.lower(produtoRoot.get("nome")),
                    "%" + filtro.getNome().toLowerCase() + "%"));
        }

        query.select(produtoRoot);
        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(builder.asc(produtoRoot.get("nome")));

        TypedQuery<Produto> typedQuery = manager.createQuery(query);
        return typedQuery.getResultList();
    }

    public Produto porId(Long id) {
        return manager.find(Produto.class, id);
    }

    public List<Produto> porNome(String nome) {
        return manager.createQuery("from Produto where upper(nome) like :nome", Produto.class)
                .setParameter("nome", nome.toUpperCase() + "%").getResultList();
    }
}
