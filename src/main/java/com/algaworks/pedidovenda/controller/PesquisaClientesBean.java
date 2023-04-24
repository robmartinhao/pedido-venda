package com.algaworks.pedidovenda.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class PesquisaClientesBean {

    private List<Integer> clientesFiltrados;

    public PesquisaClientesBean() {
        clientesFiltrados = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            clientesFiltrados.add(i);
        }
    }

    public List<Integer> getClientesFiltrados() {
        return clientesFiltrados;
    }
}
