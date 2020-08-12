/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T005Proceso;
import com.eurekaintranet.entities.T006TipoDocumento;
import com.eurekaintranet.sessions.T006TipoDocumentoFacade;
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
 * @author junio
 */
@Named(value = "tipoDocumentoController")
@SessionScoped
public class TipoDocumentoController implements Serializable {

    @EJB
    private T006TipoDocumentoFacade tipoDocumentoFacade;
    private T006TipoDocumento tipoDocumentoActual;
    private List<T006TipoDocumento> listaTipoDocumento;
    public TipoDocumentoController() {
    }

    public T006TipoDocumentoFacade getTipoDocumentoFacade() {
        return tipoDocumentoFacade;
    }

    public void setTipoDocumentoFacade(T006TipoDocumentoFacade tipoDocumentoFacade) {
        this.tipoDocumentoFacade = tipoDocumentoFacade;
    }

    public T006TipoDocumento getTipoDocumentoActual() {
        return tipoDocumentoActual;
    }

    public void setTipoDocumentoActual(T006TipoDocumento tipoDocumentoActual) {
        this.tipoDocumentoActual = tipoDocumentoActual;
    }

    public List<T006TipoDocumento> getListaTipoDocumento() {
        return listaTipoDocumento = getTipoDocumentoFacade().findAll();
    }

    public void setListaTipoDocumento(List<T006TipoDocumento> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }
    
      //Navigation
     public void recargarLista(){
        listaTipoDocumento=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        tipoDocumentoActual = new T006TipoDocumento();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGestorDocumental/tipoArchivo/lista";
    }
    public String add() {
        try { 
            getTipoDocumentoFacade().create(tipoDocumentoActual);
            addSuccessMessage("Tipo de archivo creado correctamente", "Tipo de archivo creado correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            getTipoDocumentoFacade().edit(tipoDocumentoActual);
            addSuccessMessage("Tipo de archivo editado correctamente", "Tipo de archivo editado correctamente");
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
    public T006TipoDocumento getTipoDocumento(java.lang.String id) {
        return getTipoDocumentoFacade().find(id);
    }
    @FacesConverter(forClass = T006TipoDocumento.class)
    public static class T006TipoDocumentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoDocumentoController controller = (TipoDocumentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoDocumentoController");
            return controller.getTipoDocumento(getKey(value));
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
            if (object instanceof T006TipoDocumento) {
                T006TipoDocumento o = (T006TipoDocumento) object;
                return getStringKey(o.getIdTipoDocumento());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T006TipoDocumento.class.getName());
            }
        }

    }
    
    
}
