/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;


import com.eurekaintranet.entities.T008TipoContenido;
import com.eurekaintranet.sessions.T008TipoContenidoFacade;
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
 * @author Lccabalr
 */
@Named(value = "tipoContenidoController")
@SessionScoped
public class TipoContenidoController implements Serializable {

    @EJB
    private T008TipoContenidoFacade tipoContenidoFacade;
    private T008TipoContenido tipoContenidoActual;
    private List<T008TipoContenido> listaTipoContenido;
    
    public TipoContenidoController() {
    }

    public T008TipoContenidoFacade getTipoContenidoFacade() {
        return tipoContenidoFacade;
    }

    public void setTipoContenidoFacade(T008TipoContenidoFacade tipoContenidoFacade) {
        this.tipoContenidoFacade = tipoContenidoFacade;
    }

    public T008TipoContenido getTipoContenidoActual() {
        return tipoContenidoActual;
    }

    public void setTipoContenidoActual(T008TipoContenido tipoContenidoActual) {
        this.tipoContenidoActual = tipoContenidoActual;
    }

    public List<T008TipoContenido> getListaTipoContenido() {
        return listaTipoContenido = getTipoContenidoFacade().findAll();
    }

    public void setListaTipoContenido(List<T008TipoContenido> listaTipoContenido) {
        this.listaTipoContenido = listaTipoContenido;
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
    public T008TipoContenido getTipoContenido(java.lang.String id) {
        return getTipoContenidoFacade().find(id);
    } 
    @FacesConverter(forClass = T008TipoContenido.class)
    public static class T008TipoContenidoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoContenidoController controller = (TipoContenidoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoContenidoController");
            return controller.getTipoContenido(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof T008TipoContenido) {
                T008TipoContenido o = (T008TipoContenido) object;
                return getStringKey(o.getIdTipo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T008TipoContenido.class.getName());
            }
        }

    }
}
