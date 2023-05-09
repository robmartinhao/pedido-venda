package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.EnderecoEntrega;
import com.algaworks.pedidovenda.model.Pedido;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

    private Pedido pedido;

    private List<Integer> itens;

    public CadastroPedidoBean() {
        this.pedido = new Pedido();
        pedido.setEnderecoEntrega(new EnderecoEntrega());
        itens = new ArrayList<>();
        itens.add(1);
    }

    public void salvar() {
    }

    public Pedido getPedido() {
        return pedido;
    }

    public List<Integer> getItens() {
        return itens;
    }
}
