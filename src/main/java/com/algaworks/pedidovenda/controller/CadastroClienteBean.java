package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Endereco;
import com.algaworks.pedidovenda.model.TipoPessoa;
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
    private Endereco endereco;
    private Endereco enderecoSelecionado;

    public CadastroClienteBean() {
        this.cliente = new Cliente();
        this.endereco = new Endereco();
    }

    public void inicializar() {
        if (this.cliente == null) {
            limpar();
        }
    }

    public TipoPessoa[] getTiposPessoa() {
        return TipoPessoa.values();
    }

    private void limpar() {
        cliente = new Cliente();
        limparEndereco();
    }

    public void limparEndereco() {
        endereco = new Endereco();
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Endereco getEnderecoSelecionado() {
        return enderecoSelecionado;
    }

    public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
        this.enderecoSelecionado = enderecoSelecionado;
    }

    public boolean isEditando() {
        return this.cliente.getId() != null;
    }

    public boolean isPessoaJuridica() {
        return this.cliente.getTipo() == TipoPessoa.JURIDICA;
    }

    public void adicionarEndereco() {
        if (this.endereco.getCliente() == null) {
            this.endereco.setCliente(this.cliente);
            this.cliente.getEnderecos().add(this.endereco);
        }
    }

    public void incluirEndereco() {
        if (this.endereco.getCliente() == null) {
            this.endereco.setCliente(this.cliente);
            this.cliente.getEnderecos().add(this.endereco);
        }
    }

    public void removerEndereco() {
        this.cliente.getEnderecos().remove(this.enderecoSelecionado);
    }
}
