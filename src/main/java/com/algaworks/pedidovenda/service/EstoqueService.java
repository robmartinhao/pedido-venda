package com.algaworks.pedidovenda.service;

import com.algaworks.pedidovenda.model.ItemPedido;
import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.repository.Pedidos;
import com.algaworks.pedidovenda.util.jpa.Transactional;

import javax.inject.Inject;
import java.io.Serializable;

public class EstoqueService implements Serializable {

    @Inject
    private Pedidos pedidos;

    @Transactional
    public void baixarItensEstoque(Pedido pedido) {
        pedido = this.pedidos.porId(pedido.getId());

        for (ItemPedido item : pedido.getItens()) {
            item.getProduto().baixarEstoque(item.getQuantidade());
        }
    }

    public void retornarItensEstoque(Pedido pedido) {
        pedido = pedidos.porId(pedido.getId());

        for (ItemPedido item: pedido.getItens()) {
            item.getProduto().adicionarEstoque(item.getQuantidade());
        }

    }
}
