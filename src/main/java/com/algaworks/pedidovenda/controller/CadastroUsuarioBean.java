package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Usuario;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

    private Usuario usuario;

    public CadastroUsuarioBean() {
        this.usuario = new Usuario();
    }

    public void salvar() {
        System.out.println("Teste");
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
