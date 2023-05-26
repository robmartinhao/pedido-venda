package com.algaworks.pedidovenda.converter;

import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.Grupos;
import com.algaworks.pedidovenda.repository.Usuarios;
import com.algaworks.pedidovenda.util.cdi.CDIServiceLocator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Grupo.class)
public class GrupoConverter implements Converter {

    //@Inject
    private Grupos grupos;

    public GrupoConverter() {
        this.grupos = CDIServiceLocator.getBean(Grupos.class);
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Grupo retorno = null;
        if (s != null) {
            Long id = Long.valueOf(s);
            retorno = grupos.porId(id);
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if(o != null) {
            Grupo grupo = (Grupo) o;
            return grupo.getId() == null ? null : grupo.getId().toString();
        }
        return "";
    }
}
