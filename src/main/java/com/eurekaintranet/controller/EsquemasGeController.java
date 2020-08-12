/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T034EsquemasGe;
import com.eurekaintranet.sessions.T034EsquemasGeFacade;
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
@Named(value = "esquemasGeController")
@SessionScoped
public class EsquemasGeController implements Serializable {

    @EJB
    private T034EsquemasGeFacade esquemasGeFacade;
    private T034EsquemasGe esquemasGeActual;
    private List<T034EsquemasGe> listaEsquemasGe;
    public EsquemasGeController() {
    }

    public T034EsquemasGeFacade getEsquemasGeFacade() {
        return esquemasGeFacade;
    }

    public void setEsquemasGeFacade(T034EsquemasGeFacade esquemasGeFacade) {
        this.esquemasGeFacade = esquemasGeFacade;
    }

    public T034EsquemasGe getEsquemasGeActual() {
        return esquemasGeActual;
    }

    public void setEsquemasGeActual(T034EsquemasGe esquemasGeActual) {
        this.esquemasGeActual = esquemasGeActual;
    }

    public List<T034EsquemasGe> getListaEsquemasGe() {
        return listaEsquemasGe = getEsquemasGeFacade().findAll();
    }

    public void setListaEsquemasGe(List<T034EsquemasGe> listaEsquemasGe) {
        this.listaEsquemasGe = listaEsquemasGe;
    }
    //Navigation
     public void recargarLista(){
        listaEsquemasGe=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        esquemasGeActual = new T034EsquemasGe();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGeTelares/esquemas/lista";
    }
    public String add() {
        try { 
            getEsquemasGeFacade().create(esquemasGeActual);
            addSuccessMessage("Esquema creado correctamente", "Esquema creado correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            getEsquemasGeFacade().edit(esquemasGeActual);
            addSuccessMessage("Esquema editado correctamente", "Esquema editado correctamente");
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
    public T034EsquemasGe getEsquemasGe(java.lang.Integer id) {
        return getEsquemasGeFacade().find(id);
    }
    
    @FacesConverter(forClass = T034EsquemasGe.class)
    public static class EsquemasGeControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EsquemasGeController controller = (EsquemasGeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "esquemasGeController");
            return controller.getEsquemasGe(getKey(value));
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
            if (object instanceof T034EsquemasGe) {
                T034EsquemasGe o = (T034EsquemasGe) object;
                return getStringKey(o.getIdEsquema());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T034EsquemasGe.class.getName());
            }
        }
    }
    
}
