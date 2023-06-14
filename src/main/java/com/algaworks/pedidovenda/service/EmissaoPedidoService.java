package com.algaworks.pedidovenda.service;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.StatusPedido;
import com.algaworks.pedidovenda.repository.Pedidos;
import com.algaworks.pedidovenda.util.jpa.Transactional;

import javax.inject.Inject;
import java.io.Serializable;

public class EmissaoPedidoService implements Serializable {

    @Inject
    private CadastroPedidoService cadastroPedidoService;
    @Inject
    private Pedidos pedidos;
    @Inject
    private EstoqueService estoqueService;

    @Transactional
    public Pedido emitir(Pedido pedido) {
        pedido = this.cadastroPedidoService.salvar(pedido);

        if (pedido.isNaoEmissivel()) {
            throw new NegocioException("Pedido não pode ser emitido com status " +
                    pedido.getStatus().getDescricao() + ".");
        }
        this.estoqueService.baixarItensEstoque(pedido);

        pedido.setStatus(StatusPedido.EMITIDO);

        pedido = this.pedidos.guardar(pedido);

        return pedido;
    }
}
