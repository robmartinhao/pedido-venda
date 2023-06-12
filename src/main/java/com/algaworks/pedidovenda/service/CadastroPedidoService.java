package com.algaworks.pedidovenda.service;

import com.algaworks.pedidovenda.model.ItemPedido;
import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.StatusPedido;
import com.algaworks.pedidovenda.repository.Pedidos;
import com.algaworks.pedidovenda.util.jpa.Transactional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CadastroPedidoService implements Serializable {

    @Inject
    private Pedidos pedidos;

    @Transactional
    public Pedido salvar(Pedido pedido) {

        if (pedido.isNovo()) {
            pedido.setDataCriacao(new Date());
            pedido.setStatus(StatusPedido.ORCAMENTO);
        }
        List<ItemPedido> itensPedido = pedido.getItens().stream()
                .filter(itemPedido -> itemPedido.getProduto().getId() != null)
                .collect(Collectors.toList());
        pedido.setItens(itensPedido);
        pedido = this.pedidos.guardar(pedido);
        return pedido;
    }
}
