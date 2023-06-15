package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.util.mail.Mailer;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;
import org.apache.velocity.tools.generic.NumberTool;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

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
                .bodyHtml(new VelocityTemplate(Objects.requireNonNull(getClass()
                        .getResourceAsStream("/emails/pedido.template")).toString()))
                .put("pedido", this.pedido)
                .put("numberTool", new NumberTool())
                .put("locale", new Locale("pt", "BR"))
                .send();

        FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
    }
}
