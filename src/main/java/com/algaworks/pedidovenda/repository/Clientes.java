package com.algaworks.pedidovenda.repository;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Usuario;
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
import java.io.Serializable;
import java.util.List;

public class Clientes implements Serializable {

    @Inject
    private EntityManager manager;

    public Cliente guardar(Cliente cliente) {
        return manager.merge(cliente);
    }

    @Transactional
    public void remover(Cliente cliente) {
        try {
            cliente = porId(cliente.getId());
            manager.remove(cliente);
            manager.flush();
        } catch (PersistenceException e) {
            throw new NegocioException("Cliente não pode ser excluído.");
        }
    }

    public List<Cliente> filtrados(String documentoReceitaFederal, String nome) {
        Session session = manager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Cliente.class);

        if (StringUtils.isNotBlank(nome)) {
            criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(documentoReceitaFederal)) {
            criteria.add(Restrictions.ilike("documentoReceitaFederal", documentoReceitaFederal, MatchMode.ANYWHERE));
        }
        return criteria.addOrder(Order.asc("nome")).list();
    }

    public Cliente porId(Long id) {
        return manager.find(Cliente.class, id);
    }

    public Cliente porEmail(String email) {
        try {
            return manager.createQuery("from Cliente where upper(email) = :email", Cliente.class)
                    .setParameter("email", email.toUpperCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        }
    }

}
