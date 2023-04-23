package com.algaworks.pedidovenda.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class PesquisaPedidosBean {

    private List<Integer> pedidosFiltrados;

    public PesquisaPedidosBean() {
        pedidosFiltrados = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            pedidosFiltrados.add(i);
        }
    }

    public List<Integer> getPedidosFiltrados() {
        return pedidosFiltrados;
    }
}
