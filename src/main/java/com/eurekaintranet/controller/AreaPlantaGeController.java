/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T031AreaPlantaGe;
import com.eurekaintranet.sessions.T031AreaPlantaGeFacade;
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
@Named(value = "areaPlantaGeController")
@SessionScoped
public class AreaPlantaGeController implements Serializable {

    @EJB
    private T031AreaPlantaGeFacade areaPlantaGeFacade;
    private T031AreaPlantaGe areaPlantaGeActual;
    private List<T031AreaPlantaGe> listaAreaPlantaGe;
    public AreaPlantaGeController() {
    }

    public T031AreaPlantaGeFacade getAreaPlantaGeFacade() {
        return areaPlantaGeFacade;
    }

    public void setAreaPlantaGeFacade(T031AreaPlantaGeFacade areaPlantaGeFacade) {
        this.areaPlantaGeFacade = areaPlantaGeFacade;
    }

    public T031AreaPlantaGe getAreaPlantaGeActual() {
        return areaPlantaGeActual;
    }

    public void setAreaPlantaGeActual(T031AreaPlantaGe areaPlantaGeActual) {
        this.areaPlantaGeActual = areaPlantaGeActual;
    }

    public List<T031AreaPlantaGe> getListaAreaPlantaGe() {
        return listaAreaPlantaGe = getAreaPlantaGeFacade().findAll();
    }

    public void setListaAreaPlantaGe(List<T031AreaPlantaGe> listaAreaPlantaGe) {
        this.listaAreaPlantaGe = listaAreaPlantaGe;
    }
     //Navigation
     public void recargarLista(){
        listaAreaPlantaGe=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        areaPlantaGeActual = new T031AreaPlantaGe();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGeTelares/area/lista";
    }
    public String add() {
        try { 
            getAreaPlantaGeFacade().create(areaPlantaGeActual);
            addSuccessMessage("Area creada correctamente", "Area creada correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            getAreaPlantaGeFacade().edit(areaPlantaGeActual);
            addSuccessMessage("Area editada correctamente", "Area editada correctamente");
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
    public T031AreaPlantaGe getAreaPlantaGe(java.lang.Integer id) {
        return getAreaPlantaGeFacade().find(id);
    }
    
    @FacesConverter(forClass = T031AreaPlantaGe.class)
    public static class AreaPlantaGeControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AreaPlantaGeController controller = (AreaPlantaGeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "areaPlantaGeController");
            return controller.getAreaPlantaGe(getKey(value));
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
            if (object instanceof T031AreaPlantaGe) {
                T031AreaPlantaGe o = (T031AreaPlantaGe) object;
                return getStringKey(o.getIdArea());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T031AreaPlantaGe.class.getName());
            }
        }
    }
}
