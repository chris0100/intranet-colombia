/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T041UnidadNegocioGe;
import com.eurekaintranet.sessions.T041UnidadNegocioGeFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author LUIS CARLOS CABAL
 */
@Named(value = "unidadNegocioGeController")
@SessionScoped
public class UnidadNegocioGeController implements Serializable {

    @EJB
    private T041UnidadNegocioGeFacade unidadNegocioFacade;
    private T041UnidadNegocioGe unidadNegocioActual;
    private List<T041UnidadNegocioGe> listaUnidadNegocio;
    
    public UnidadNegocioGeController() {
    }

    public T041UnidadNegocioGeFacade getUnidadNegocioFacade() {
        return unidadNegocioFacade;
    }

    public void setUnidadNegocioFacade(T041UnidadNegocioGeFacade unidadNegocioFacade) {
        this.unidadNegocioFacade = unidadNegocioFacade;
    }

    public T041UnidadNegocioGe getUnidadNegocioActual() {
        return unidadNegocioActual;
    }

    public void setUnidadNegocioActual(T041UnidadNegocioGe unidadNegocioActual) {
        this.unidadNegocioActual = unidadNegocioActual;
    }

    public List<T041UnidadNegocioGe> getListaUnidadNegocio() {
        return listaUnidadNegocio = getUnidadNegocioFacade().findAll();
    }

    public void setListaUnidadNegocio(List<T041UnidadNegocioGe> listaUnidadNegocio) {
        this.listaUnidadNegocio = listaUnidadNegocio;
    }

    
     //Navigation
     public void recargarLista(){
        listaUnidadNegocio=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        unidadNegocioActual = new T041UnidadNegocioGe();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGeTelares/unidadNegocio/lista";
    }
    public String add() {
        try { 
            getUnidadNegocioFacade().create(unidadNegocioActual);
            addSuccessMessage("Unidad negocio creada correctamente", "Unidad negocio creada correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            getUnidadNegocioFacade().edit(unidadNegocioActual);
            addSuccessMessage("Unidad negocio editada correctamente", "Unidad negocio editada correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    // Messages
    private void addErrorMessage(String title, String msg) {
        FacesMessage facesMsg
                = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    private void addSuccessMessage(String title, String msg) {
        FacesMessage facesMsg
                = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }
    public T041UnidadNegocioGe getUnidadNegocioGe(java.lang.Integer id) {
        return getUnidadNegocioFacade().find(id);
    }
    
    @FacesConverter(forClass = T041UnidadNegocioGe.class)
    public static class UnidadNegocioGeControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UnidadNegocioGeController controller = (UnidadNegocioGeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "unidadNegocioGeController");
            return controller.getUnidadNegocioGe(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof T041UnidadNegocioGe) {
                T041UnidadNegocioGe o = (T041UnidadNegocioGe) object;
                return getStringKey(o.getIdUnidadNegocio());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T041UnidadNegocioGe.class.getName());
            }
        }
    }
    
}
