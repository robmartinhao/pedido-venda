package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PesquisaUsuariosBean implements Serializable {

    @Inject
    private Usuarios usuarios;
    private Usuario usuarioSelecionado;
    private String nome;
    private List<Usuario> usuariosFiltrados;

    public PesquisaUsuariosBean() {
        this.nome = "";
    }

    public void pesquisar() {
        usuariosFiltrados = usuarios.filtrados(this.nome);
    }

    public void excluir() {
        usuarios.remover(usuarioSelecionado);
        usuariosFiltrados.remove(usuarioSelecionado);

        FacesUtil.addInfoMessage("Usuário " + usuarioSelecionado.getNome() + " excluído com sucesso.");
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public List<Usuario> getUsuariosFiltrados() {
        return usuariosFiltrados;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
