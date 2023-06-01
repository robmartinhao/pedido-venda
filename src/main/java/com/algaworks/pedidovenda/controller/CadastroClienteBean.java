package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.TipoPessoa;
import com.algaworks.pedidovenda.repository.Clientes;
import com.algaworks.pedidovenda.service.CadastroClienteService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

    @Inject
    private CadastroClienteService cadastroClienteService;

    private Cliente cliente;

    public CadastroClienteBean() {
        this.cliente = new Cliente();
    }

    public TipoPessoa[] getTiposPessoa() {
        return TipoPessoa.values();
    }

    private void limpar() {
        cliente = new Cliente();
    }

    public void salvar() {
        this.cliente = cadastroClienteService.salvar(this.cliente);
        limpar();
        FacesUtil.addInfoMessage("Cliente salvo com sucesso!");
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isEditando() {
        return this.cliente.getId() != null;
    }

    public boolean isPessoaJuridica() {
        return this.cliente.getTipo() == TipoPessoa.JURIDICA;
    }
}
