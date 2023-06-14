package com.algaworks.pedidovenda.service;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.StatusPedido;
import com.algaworks.pedidovenda.repository.Pedidos;
import com.algaworks.pedidovenda.util.jpa.Transactional;

import javax.inject.Inject;
import java.io.Serializable;

public class CancelamentoPedidoService implements Serializable {

    @Inject
    private Pedidos pedidos;
    @Inject
    private EstoqueService estoqueService;
    @Transactional
    public Pedido cancelar(Pedido pedido) {
        pedido = this.pedidos.porId(pedido.getId());

        if(pedido.isNaoCancelavel()) {
            throw new NegocioException("Pedido não pode ser cancelado no status "
                + pedido.getStatus().getDescricao() + ".");
        }
        if (pedido.isEmitido()) {
            this.estoqueService.retornarItensEstoque(pedido);
        }
        pedido.setStatus(StatusPedido.CANCELADO);
        pedido = this.pedidos.guardar(pedido);
        return pedido;
    }
}
