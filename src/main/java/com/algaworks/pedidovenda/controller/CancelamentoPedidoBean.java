package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.service.CancelamentoPedidoService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class CancelamentoPedidoBean implements Serializable {

    @Inject
    private CancelamentoPedidoService cancelamentoPedidoService;
    @Inject
    private Event<PedidoAlteradoEvent> pedidoAlteradoEventEvent;
    @Inject
    @PedidoEdicao
    private Pedido pedido;
    public void cancelarPedido() {
        this.pedido = this.cancelamentoPedidoService.cancelar(this.pedido);
        this.pedidoAlteradoEventEvent.fire(new PedidoAlteradoEvent(this.pedido));

        FacesUtil.addInfoMessage("Pedido cancelado com sucesso!");
    }
    
}
