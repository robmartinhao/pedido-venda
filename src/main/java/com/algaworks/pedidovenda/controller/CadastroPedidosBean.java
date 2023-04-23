package com.algaworks.pedidovenda.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class CadastroPedidosBean {

    private List<Integer> itens;

    public CadastroPedidosBean() {
        itens = new ArrayList<>();
        itens.add(1);
    }

    public List<Integer> getItens() {
        return itens;
    }
}
