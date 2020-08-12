/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T005Proceso;
import com.eurekaintranet.sessions.T005ProcesoFacade;
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
@Named(value = "procesoController")
@SessionScoped
public class ProcesoController implements Serializable {

    @EJB
    private T005ProcesoFacade procesoFacade;
    private T005Proceso ProcesoActual;
    private List<T005Proceso> listaProceso;
    
    public ProcesoController() {
    }

    public T005ProcesoFacade getProcesoFacade() {
        return procesoFacade;
    }

    public void setProcesoFacade(T005ProcesoFacade procesoFacade) {
        this.procesoFacade = procesoFacade;
    }

    public T005Proceso getProcesoActual() {
        return ProcesoActual;
    }

    public void setProcesoActual(T005Proceso ProcesoActual) {
        this.ProcesoActual = ProcesoActual;
    }

    public List<T005Proceso> getListaProceso() {
        return listaProceso = getProcesoFacade().findAll();
    }
    public List<String> getListaProcesoString() {
        return getProcesoFacade().procesosString();
    }
    public void setListaProceso(List<T005Proceso> listaProceso) {
        this.listaProceso = listaProceso;
    }
    //Navigation
     public void recargarLista(){
        listaProceso=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        ProcesoActual = new T005Proceso();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGestorDocumental/proceso/lista";
    }
    public String add() {
        try { 
            getProcesoFacade().create(ProcesoActual);
            addSuccessMessage("Proceso creado correctamente", "Proceso creado correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            getProcesoFacade().edit(ProcesoActual);
            addSuccessMessage("Proceso editado correctamente", "Proceso editado correctamente");
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
    public T005Proceso getProcesos(java.lang.String id) {
        return getProcesoFacade().find(id);
    }
    
    @FacesConverter(forClass = T005Proceso.class)
    public static class procesoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProcesoController controller = (ProcesoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "procesoController");
            return controller.getProcesos(getKey(value));
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
            if (object instanceof T005Proceso) {
                T005Proceso o = (T005Proceso) object;
                return getStringKey(o.getIdProceso());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T005Proceso.class.getName());
            }
        }

    }
}
