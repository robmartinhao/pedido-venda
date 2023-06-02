package com.algaworks.pedidovenda.repository;

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

public class Usuarios implements Serializable {

    @Inject
    private EntityManager manager;

    public Usuario guardar(Usuario usuario) {
        return manager.merge(usuario);
    }

    @Transactional
    public void remover(Usuario usuario) {
        try {
            usuario = porId(usuario.getId());
            manager.remove(usuario);
            manager.flush();
        } catch (PersistenceException e) {
            throw new NegocioException("Usuário não pode ser excluído.");
        }
    }

    public List<Usuario> filtrados(String nome) {
        Session session = manager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Usuario.class);

        if (StringUtils.isNotBlank(nome)) {
            criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
        }
        return criteria.addOrder(Order.asc("nome")).list();
    }

    public Usuario porId(Long id) {
        return manager.find(Usuario.class, id);
    }

    public Usuario porEmail(String email) {
        try {
            return manager.createQuery("from Usuario where upper(email) = :email", Usuario.class)
                    .setParameter("email", email.toUpperCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        }
    }

    public List<Usuario> vendedores() {
        return this.manager.createQuery("from Usuario", Usuario.class)
                .getResultList();
    }
}
