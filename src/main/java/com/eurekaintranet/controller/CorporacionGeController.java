/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T040CorporacionGe;
import com.eurekaintranet.sessions.T040CorporacionGeFacade;
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
@Named(value = "corporacionGeController")
@SessionScoped
public class CorporacionGeController implements Serializable {

    @EJB
    private T040CorporacionGeFacade corporacionFacade;
    private T040CorporacionGe corporacionActual;
    private List<T040CorporacionGe> listaCorporacion;
    public CorporacionGeController() {
    }

    public T040CorporacionGeFacade getCorporacionFacade() {
        return corporacionFacade;
    }

    public void setCorporacionFacade(T040CorporacionGeFacade corporacionFacade) {
        this.corporacionFacade = corporacionFacade;
    }

    public T040CorporacionGe getCorporacionActual() {
        return corporacionActual;
    }

    public void setCorporacionActual(T040CorporacionGe corporacionActual) {
        this.corporacionActual = corporacionActual;
    }

    public List<T040CorporacionGe> getListaCorporacion() {
        return listaCorporacion  = getCorporacionFacade().findAll();
    }

    public void setListaCorporacion(List<T040CorporacionGe> listaCorporacion) {
        this.listaCorporacion = listaCorporacion;
    }
     //Navigation
     public void recargarLista(){
        listaCorporacion=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        corporacionActual = new T040CorporacionGe();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGeTelares/corporacion/lista";
    }
    public String add() {
        try { 
            getCorporacionFacade().create(corporacionActual);
            addSuccessMessage("Corporación creada correctamente", "Corporación creada correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            getCorporacionFacade().edit(corporacionActual);
            addSuccessMessage("Corporación editada correctamente", "Corporación editada correctamente");
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
    //converter
   public T040CorporacionGe getCorporacionGe(java.lang.Integer id) {
        return getCorporacionFacade().find(id);
    }
    
    @FacesConverter(forClass = T040CorporacionGe.class)
    public static class CorporacionGeControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CorporacionGeController controller = (CorporacionGeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "corporacionGeController");
            return controller.getCorporacionGe(getKey(value));
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
            if (object instanceof T040CorporacionGe) {
                T040CorporacionGe o = (T040CorporacionGe) object;
                return getStringKey(o.getIdCorporacion());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T040CorporacionGe.class.getName());
            }
        }
    }
}
