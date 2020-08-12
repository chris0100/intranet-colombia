/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T035ParadasGe;
import com.eurekaintranet.entities.T039EquipoGe;
import com.eurekaintranet.sessions.T039EquipoGeFacade;
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

//EQUPO - PAIS
@Named(value = "equipoGeController")
@SessionScoped
public class EquipoGeController implements Serializable {

    @EJB
    private T039EquipoGeFacade equipoGeFacade;
    private T039EquipoGe equipoGeActual;
    private List<T039EquipoGe> listaEquipoGe;
    
    public EquipoGeController() {
    }

    public T039EquipoGeFacade getEquipoGeFacade() {
        return equipoGeFacade;
    }

    public void setEquipoGeFacade(T039EquipoGeFacade equipoGeFacade) {
        this.equipoGeFacade = equipoGeFacade;
    }

    public T039EquipoGe getEquipoGeActual() {
        return equipoGeActual;
    }

    public void setEquipoGeActual(T039EquipoGe equipoGeActual) {
        this.equipoGeActual = equipoGeActual;
    }

    public List<T039EquipoGe> getListaEquipoGe() {
        return listaEquipoGe = getEquipoGeFacade().findAll();
    }

    public void setListaEquipoGe(List<T039EquipoGe> listaEquipoGe) {
        this.listaEquipoGe = listaEquipoGe;
    }

   
     //Navigation
     public void recargarLista(){
        listaEquipoGe=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        equipoGeActual = new T039EquipoGe();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGeTelares/equipo/lista";
    }
    public String add() {
        try { 
            getEquipoGeFacade().create(equipoGeActual);
            addSuccessMessage("Equipo creado correctamente", "Equipo creado correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            getEquipoGeFacade().edit(equipoGeActual);
            addSuccessMessage("Equipo editado correctamente", "Equipo editado correctamente");
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
    public T039EquipoGe getEquipoGe(java.lang.Integer id) {
        return getEquipoGeFacade().find(id);
    }
    
    @FacesConverter(forClass = T039EquipoGe.class)
    public static class EquipoGeControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            
            EquipoGeController controller = (EquipoGeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "equipoGeController");
            return controller.getEquipoGe(getKey(value));
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
            if (object instanceof T039EquipoGe) {
                T039EquipoGe o = (T039EquipoGe) object;
                return getStringKey(o.getIdEquipo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T039EquipoGe.class.getName());
            }
        }
    }
}

