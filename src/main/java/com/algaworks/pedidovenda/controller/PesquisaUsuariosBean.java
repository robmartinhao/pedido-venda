package com.algaworks.pedidovenda.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class PesquisaUsuariosBean {

    private List<Integer> usuariosFiltrados;

    public PesquisaUsuariosBean() {
        usuariosFiltrados = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            usuariosFiltrados.add(i);
        }
    }

    public List<Integer> getUsuariosFiltrados() {
        return usuariosFiltrados;
    }
}
