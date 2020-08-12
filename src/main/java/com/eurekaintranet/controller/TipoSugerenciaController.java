/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T019TipoSugerencia;
import com.eurekaintranet.sessions.T019TipoSugerenciaFacade;
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
@Named(value = "tipoSugerenciaController")
@SessionScoped
public class TipoSugerenciaController implements Serializable {

    /**
     * Creates a new instance of TipoSugerenciaController
     */
    
    @EJB
    private T019TipoSugerenciaFacade tipoSugerenciaFacade;
    private T019TipoSugerencia tipoSugerenciaActual;
    private List<T019TipoSugerencia> listaTipoSugerencia;
    
    public TipoSugerenciaController() {
    }

    public T019TipoSugerenciaFacade getTipoSugerenciaFacade() {
        return tipoSugerenciaFacade;
    }

    public void setTipoSugerenciaFacade(T019TipoSugerenciaFacade tipoSugerenciaFacade) {
        this.tipoSugerenciaFacade = tipoSugerenciaFacade;
    }

    public T019TipoSugerencia getTipoSugerenciaActual() {
        return tipoSugerenciaActual;
    }

    public void setTipoSugerenciaActual(T019TipoSugerencia tipoSugerenciaActual) {
        this.tipoSugerenciaActual = tipoSugerenciaActual;
    }

    public List<T019TipoSugerencia> getListaTipoSugerencia() {
        return listaTipoSugerencia = getTipoSugerenciaFacade().tipoSugerenciaOrdenado();
    }

    public void setListaTipoSugerencia(List<T019TipoSugerencia> listaTipoSugerencia) {
        this.listaTipoSugerencia = listaTipoSugerencia;
    }

    
    //Navigation
    public void recargarLista(){
        listaTipoSugerencia=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        tipoSugerenciaActual = new T019TipoSugerencia();
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
            getTipoSugerenciaFacade().create(tipoSugerenciaActual);
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
            getTipoSugerenciaFacade().edit(tipoSugerenciaActual);
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
   
    // Converter
    public T019TipoSugerencia getTipoSugerencia(java.lang.String id) {
        return tipoSugerenciaFacade.find(id);
    }

    @FacesConverter(forClass = T019TipoSugerencia.class)
    public static class DocumentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoSugerenciaController controller = (TipoSugerenciaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoSugerenciaController");
            return controller.getTipoSugerencia(getKey(value));
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
            if (object instanceof T019TipoSugerencia) {
                T019TipoSugerencia o = (T019TipoSugerencia) object;
                return getStringKey(o.getIdTipoSugerencia());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T019TipoSugerencia.class.getName());
            }
        }
    }
       
}
