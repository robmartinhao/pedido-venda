package com.algaworks.pedidovenda.converter;

import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.Produtos;
import org.apache.commons.lang3.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {

    @Inject
    private Produtos produtos;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Produto retorno = null;
        if (StringUtils.isNotEmpty(s)) {
            Long id = Long.valueOf(s);
            retorno = produtos.porId(id);
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if(o != null) {
            Produto produto = (Produto) o;
            return produto.getId() == null ? null : produto.getId().toString();
        }
        return "";
    }
}
