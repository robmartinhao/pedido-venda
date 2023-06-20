package com.algaworks.pedidovenda.repository;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.model.vo.DataValor;
import com.algaworks.pedidovenda.repository.filter.PedidoFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class Pedidos implements Serializable {

    @Inject
    private EntityManager manager;

    public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDeDias, Usuario criadoPor) {

        numeroDeDias -= 1;

        Calendar dataInicial = Calendar.getInstance();
        dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
        dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);

        Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias, dataInicial);

        String jpql = "select new com.algaworks.pedidovenda.model.vo.DataValor(date(p.dataCriacao), sum(p.valorTotal)) " +
                "from Pedido p where p.dataCriacao >= :dataInicial ";

        if (criadoPor != null) {
            jpql += "and p.vendedor = :vendedor ";
        }

        jpql += "group by date(dataCriacao)";

        TypedQuery<DataValor> query = manager.createQuery(jpql, DataValor.class);

        query.setParameter("dataInicial", dataInicial.getTime());

        if (criadoPor != null) {
            query.setParameter("vendedor", criadoPor);
        }

        List<DataValor> valoresPorData = query.getResultList();

        for (DataValor dataValor : valoresPorData) {
            resultado.put(dataValor.getData(), dataValor.getValor());
        }

        return resultado;
    }

    private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDeDias,
                                                 Calendar dataInicial) {
        dataInicial = (Calendar) dataInicial.clone();
        Map<Date, BigDecimal> mapaInicial = new TreeMap<>();

        for (int i = 0; i <= numeroDeDias; i++) {
            mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
            dataInicial.add(Calendar.DAY_OF_MONTH, 1);
        }

        return mapaInicial;
    }

    private List<Predicate> criarPredicatesParaFiltro(PedidoFilter filtro, Root<Pedido> pedidoRoot,
                                                      From<?, ?> clienteJoin, From<?,?> vendedorJoin) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();

        if (filtro.getNumeroDe() != null) {
            predicates.add(builder.greaterThanOrEqualTo(pedidoRoot.get("id"), filtro.getNumeroDe()));
        }
        if (filtro.getNumeroAte() != null) {
            predicates.add(builder.lessThanOrEqualTo(pedidoRoot.get("id"), filtro.getNumeroAte()));
        }
        if (filtro.getDataCriacaoDe() != null) {
            predicates.add(builder.greaterThanOrEqualTo(pedidoRoot.get("dataCriacao"), filtro.getDataCriacaoDe()));
        }

        if (filtro.getDataCriacaoAte() != null) {
            predicates.add(builder.lessThanOrEqualTo(pedidoRoot.get("dataCriacao"), filtro.getDataCriacaoAte()));
        }

        if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
            predicates.add(builder.like(clienteJoin.get("nome"), "%" + filtro.getNomeCliente() + "%"));
        }

        if (StringUtils.isNotBlank(filtro.getNomeVendedor())) {
            predicates.add(builder.like(vendedorJoin.get("nome"), "%" + filtro.getNomeVendedor() + "%"));
        }

        if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
            predicates.add(pedidoRoot.get("status").in(Arrays.asList(filtro.getStatuses())));
        }

        return predicates;
    }

    public List<Pedido> filtrados(PedidoFilter filtro) {
        From<?, ?> orderByFromEntity = null;

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = builder.createQuery(Pedido.class);

        Root<Pedido> pedidoRoot = criteriaQuery.from(Pedido.class);
        From<?, ?> clienteJoin = (From<?, ?>) pedidoRoot.fetch("cliente", JoinType.INNER);
        From<?, ?> vendedorJoin = (From<?, ?>) pedidoRoot.fetch("vendedor", JoinType.INNER);

        List<Predicate> predicates = criarPredicatesParaFiltro(filtro, pedidoRoot, clienteJoin, vendedorJoin);

        criteriaQuery.select(pedidoRoot);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        if (filtro.getPropriedadeOrdenacao() != null) {
            String nomePropriedadeOrdenacao = filtro.getPropriedadeOrdenacao();
            orderByFromEntity = pedidoRoot;

            if (filtro.getPropriedadeOrdenacao().contains(".")) {
                nomePropriedadeOrdenacao = nomePropriedadeOrdenacao.substring(
                        filtro.getPropriedadeOrdenacao().indexOf(".") + 1);
            }

            if (filtro.getPropriedadeOrdenacao().startsWith("cliente.")) {
                orderByFromEntity = clienteJoin;
            }

            if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
                criteriaQuery.orderBy(builder.asc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
            } else if (filtro.getPropriedadeOrdenacao() != null) {
                criteriaQuery.orderBy(builder.desc(orderByFromEntity.get(nomePropriedadeOrdenacao)));
            }
        }

        TypedQuery<Pedido> query = manager.createQuery(criteriaQuery);

        query.setFirstResult(filtro.getPrimeiroRegistro());
        query.setMaxResults(filtro.getQuantidadeRegistros());

        return query.getResultList();
    }

    public int quantidadeFiltrados(PedidoFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);

        Root<Pedido> pedidoRoot = criteriaQuery.from(Pedido.class);
        Join<Pedido, Cliente> clienteJoin = pedidoRoot.join("cliente", JoinType.INNER);
        Join<Pedido, Cliente> vendedorJoin = pedidoRoot.join("vendedor", JoinType.INNER);

        List<Predicate> predicates = criarPredicatesParaFiltro(filtro, pedidoRoot, clienteJoin, vendedorJoin);

        criteriaQuery.select(builder.count(pedidoRoot));
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Long> query = manager.createQuery(criteriaQuery);

        return query.getSingleResult().intValue();
    }

    public Pedido guardar(Pedido pedido) {
        return manager.merge(pedido);
    }

    public Pedido porId(Long id) {
        return manager.find(Pedido.class, id);
    }
}
