package com.algaworks.pedidovenda.service;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.util.jpa.Transactional;

import javax.inject.Inject;
import java.io.Serializable;

public class CadastroUsuarioService implements Serializable {

    @Inject
    private Usuarios usuarios;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        Usuario usuarioExistente = usuarios.porEmail(usuario.getEmail());
        if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
            throw new NegocioException("Já existe um usuário com o e-mail informado.");
        }
        return usuarios.guardar(usuario);
    }
}
