package com.algaworks.pedidovenda.controller;

import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.Grupos;
import com.algaworks.pedidovenda.service.CadastroUsuarioService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

    @Inject
    private CadastroUsuarioService cadastroUsuarioService;
    private Usuario usuario;
    @Inject
    private Grupos grupos;

    private Grupo grupoSelecionado = new Grupo();

    private List<Grupo> grupoList = new ArrayList<>();

    public void inicializar() {
        if (this.usuario == null) {
            limpar();
        }
        this.grupoList = grupos.todos();
    }

    public CadastroUsuarioBean() {
        limpar();
    }

    private void limpar() {
        this.usuario = new Usuario();
        this.grupoList = new ArrayList<>();
        this.grupoSelecionado = new Grupo();
    }

    public void salvar() {
        this.usuario = cadastroUsuarioService.salvar(this.usuario);
        limpar();
        FacesUtil.addInfoMessage("Usuário salvo com sucesso!");
    }

    public void adicionarGrupo() {
        if (!this.usuario.getGrupos().contains(this.grupoSelecionado)) {
            this.usuario.getGrupos().add(this.grupoSelecionado);
        }
    }

    public void removerGrupo() {
        this.usuario.getGrupos().remove(this.grupoSelecionado);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Grupo getGrupoSelecionado() {
        return grupoSelecionado;
    }

    public void setGrupoSelecionado(Grupo grupoSelecionado) {
        this.grupoSelecionado = grupoSelecionado;
    }

    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    public boolean isEditando() {
        return this.usuario.getId() != null;
    }
}
