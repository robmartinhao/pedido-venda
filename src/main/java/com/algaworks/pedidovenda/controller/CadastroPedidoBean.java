package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.*;
import com.algaworks.pedidovenda.repository.Clientes;
import com.algaworks.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.service.CadastroPedidoService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

    @Inject
    private CadastroPedidoService cadastroPedidoService;
    @Inject
    private Clientes clientes;
    @Inject
    private Usuarios usuarios;
    private Pedido pedido;
    private List<Usuario> vendedores;

    public CadastroPedidoBean() {
      limpar();
    }

    public void limpar() {
        this.pedido = new Pedido();
        pedido.setEnderecoEntrega(new EnderecoEntrega());
    }

    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            this.vendedores = this.usuarios.vendedores();
        }
    }

    public void salvar() {
        this.pedido = cadastroPedidoService.salvar(this.pedido);

        FacesUtil.addInfoMessage("Pedido salvo com sucesso!");
    }

    public FormaPagamento[] getFormasPagamento() {
        return FormaPagamento.values();
    }

    public List<Cliente> completarCliente(String nome) {
        return clientes.porNome(nome);
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<Usuario> getVendedores() {
        return vendedores;
    }

    public boolean isEditando() {
        return this.pedido.getId() != null;
    }
}
