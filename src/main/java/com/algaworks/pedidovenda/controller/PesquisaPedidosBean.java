package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.StatusPedido;
import com.algaworks.pedidovenda.repository.Pedidos;
import com.algaworks.pedidovenda.repository.filter.PedidoFilter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class PesquisaPedidosBean implements Serializable {

    @Inject
    Pedidos pedidos;
    private PedidoFilter filtro;
    private LazyDataModel<Pedido> model;

    public PesquisaPedidosBean() {
        filtro = new PedidoFilter();
        model = new LazyDataModel<>() {
            @Override
            public List<Pedido> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                     Map<String, Object> filters) {

                filtro.setPrimeiroRegistro(first);
                filtro.setQuantidadeRegistros(pageSize);
                filtro.setPropriedadeOrdenacao(sortField);
                filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));

                setRowCount(pedidos.quantidadeFiltrados(filtro));

                return pedidos.filtrados(filtro);
            }

        };
    }

    public PedidoFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(PedidoFilter filtro) {
        this.filtro = filtro;
    }

    public StatusPedido[] getStatuses() {
        return StatusPedido.values();
    }

    public LazyDataModel<Pedido> getModel() {
        return model;
    }
}
