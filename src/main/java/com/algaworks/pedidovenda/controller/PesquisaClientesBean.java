package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.Clientes;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PesquisaClientesBean implements Serializable {

    @Inject
    private Clientes clientes;
    private Cliente clienteSelecionado;
    private List<Cliente> clientesFiltrados;
    private String nome;
    private String documentoReceitaFederal;

    public PesquisaClientesBean() {
        this.nome = "";
        this.documentoReceitaFederal = "";
    }

    public void pesquisar() {
        clientesFiltrados = clientes.filtrados(this.documentoReceitaFederal, this.nome);
    }

    public void excluir() {
        clientes.remover(clienteSelecionado);
        clientesFiltrados.remove(clienteSelecionado);

        FacesUtil.addInfoMessage("Cliente " + clienteSelecionado.getNome() + " excluído com sucesso.");
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public List<Cliente> getClientesFiltrados() {
        return clientesFiltrados;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumentoReceitaFederal() {
        return documentoReceitaFederal;
    }

    public void setDocumentoReceitaFederal(String documentoReceitaFederal) {
        this.documentoReceitaFederal = documentoReceitaFederal;
    }
}

