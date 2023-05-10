package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Cliente;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

    private Cliente cliente;

    public CadastroClienteBean() {
        this.cliente = new Cliente();
    }

    public void salvar() {
        System.out.println("Teste");
    }

    public Cliente getCliente() {
        return cliente;
    }
}
