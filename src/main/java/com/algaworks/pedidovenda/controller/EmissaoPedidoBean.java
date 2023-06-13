package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.service.EmissaoPedidoService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class EmissaoPedidoBean implements Serializable {

    @Inject
    private EmissaoPedidoService emissaoPedidoService;
    @Inject
    @PedidoEdicao
    private Pedido pedido;
    @Inject
    private Event<PedidoAlteradoEvent> pedidoAlteradoEventEvent;

    public void emitirPedido() {
        pedido.removerItemVazio();
        try {
            this.pedido = this.emissaoPedidoService.emitir(this.pedido);
            this.pedidoAlteradoEventEvent.fire(new PedidoAlteradoEvent(pedido));

            FacesUtil.addInfoMessage("Pedido emitido com sucesso!");
        } finally {
            this.pedido.adicionaItemVazio();
        }
    }
}
