/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T043LineaProduccionGe;
import com.eurekaintranet.sessions.T043LineaProduccionGeFacade;
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
@Named(value = "lineaProduccionGeController")
@SessionScoped
public class LineaProduccionGeController implements Serializable {

    @EJB
    private T043LineaProduccionGeFacade lineaProduccionFacade;
    private T043LineaProduccionGe lineaProduccionActual;
    private List<T043LineaProduccionGe> listaLineaProduccion;
    public LineaProduccionGeController() {
    }

    public T043LineaProduccionGeFacade getLineaProduccionFacade() {
        return lineaProduccionFacade;
    }

    public void setLineaProduccionFacade(T043LineaProduccionGeFacade lineaProduccionFacade) {
        this.lineaProduccionFacade = lineaProduccionFacade;
    }

    public T043LineaProduccionGe getLineaProduccionActual() {
        return lineaProduccionActual;
    }

    public void setLineaProduccionActual(T043LineaProduccionGe lineaProduccionActual) {
        this.lineaProduccionActual = lineaProduccionActual;
    }

    public List<T043LineaProduccionGe> getListaLineaProduccion() {
        return listaLineaProduccion  = getLineaProduccionFacade().findAll();
    }

    public void setListaLineaProduccion(List<T043LineaProduccionGe> listaLineaProduccion) {
        this.listaLineaProduccion = listaLineaProduccion;
    }
     //Navigation
    public void recargarLista(){
        listaLineaProduccion=null;
    }
     public String prepareEdit() {
        return "editar";
    }
    public String prepareCreate() {
        lineaProduccionActual = new T043LineaProduccionGe();
        return "crear";
    }
    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGeTelares/lineaProduccion/lista";
    }
    public String add() {
        try { 
            getLineaProduccionFacade().create(lineaProduccionActual);
            addSuccessMessage("Linea de producción creada correctamente", "Linea de producción creada correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            getLineaProduccionFacade().edit(lineaProduccionActual);
            addSuccessMessage("Linea de producción editada correctamente", "Linea de producción editada correctamente");
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
    
    public T043LineaProduccionGe getLineaProduccionGe(java.lang.Integer id) {
        return getLineaProduccionFacade().find(id);
    }
    
    @FacesConverter(forClass = T043LineaProduccionGe.class)
    public static class LineaProduccionGeControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LineaProduccionGeController controller = (LineaProduccionGeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "lineaProduccionGeController");
            return controller.getLineaProduccionGe(getKey(value));
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
            if (object instanceof T043LineaProduccionGe) {
                T043LineaProduccionGe o = (T043LineaProduccionGe) object;
                return getStringKey(o.getIdLineaProduccion());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T043LineaProduccionGe.class.getName());
            }
        }
    }    
}
