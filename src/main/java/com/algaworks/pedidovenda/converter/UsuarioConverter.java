package com.algaworks.pedidovenda.converter;

import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.Produtos;
import com.algaworks.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.util.cdi.CDIServiceLocator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

    //@Inject
    private Usuarios usuarios;

    public UsuarioConverter() {
        this.usuarios = CDIServiceLocator.getBean(Usuarios.class);
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Usuario retorno = null;
        if (s != null) {
            Long id = Long.valueOf(s);
            retorno = usuarios.porId(id);
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if(o != null) {
            Usuario usuario = (Usuario) o;
            return usuario.getId() == null ? null : usuario.getId().toString();
        }
        return "";
    }
}
