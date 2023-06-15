package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.util.mail.Mailer;
import com.outjected.email.api.MailMessage;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class EnvioPedidoEmailBean implements Serializable {

    @Inject
    Mailer mailer;
    @Inject
    @PedidoEdicao
    private Pedido pedido;

    public void enviarPedido() {
        MailMessage message = mailer.novaMensagem();

        message.to(pedido.getCliente().getEmail())
                .subject("Seu pedido de venda")
                .bodyHtml("<strong>Valor total:</strong> " + this.pedido.getValorTotal())
                .send();

        FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
    }
}
