/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T032TurnosGe;
import com.eurekaintranet.sessions.T032TurnosGeFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
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
@Named(value = "turnosGeController")
@SessionScoped
public class TurnosGeController implements Serializable {

    @EJB
    private T032TurnosGeFacade turnosGeFacade;
    private T032TurnosGe turnosGeActual;
    private List<T032TurnosGe> listaTurnosGe;
    
    private T001Usuario usuarioActual; 
    
    public TurnosGeController() {
    }

    public T032TurnosGeFacade getTurnosGeFacade() {
        return turnosGeFacade;
    }

    public void setTurnosGeFacade(T032TurnosGeFacade turnosGeFacade) {
        this.turnosGeFacade = turnosGeFacade;
    }

    public T032TurnosGe getTurnosGeActual() {
        return turnosGeActual;
    }

    public void setTurnosGeActual(T032TurnosGe turnosGeActual) {
        this.turnosGeActual = turnosGeActual;
    }

    public List<T032TurnosGe> getListaTurnosGe() {
        return listaTurnosGe = getTurnosGeFacade().findAll();
    }
    public List<T032TurnosGe> getListaTurnosGeEstadoActivo() {
        return listaTurnosGe = getTurnosGeFacade().consultarEstadoTurno(true);
    }
    public void setListaTurnosGe(List<T032TurnosGe> listaTurnosGe) {
        this.listaTurnosGe = listaTurnosGe;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
    //Navigation
     public void recargarLista(){
        listaTurnosGe=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        turnosGeActual = new T032TurnosGe();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGeTelares/turnos/lista";
    }
    public String add() {
        try { 
            turnosGeActual.setEstado(true);
            turnosGeActual.setUsuarioCreacion(usuarioActual.getUsername());
            turnosGeActual.setFechaCreacion(new Date());
            getTurnosGeFacade().create(turnosGeActual);
            addSuccessMessage("Turno creado correctamente", "Turno creado correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            turnosGeActual.setUsuarioActualizacion(usuarioActual.getUsername());
            turnosGeActual.setFechaActualizacion(new Date());
            getTurnosGeFacade().edit(turnosGeActual);
            addSuccessMessage("Turno editado correctamente", "turno editado correctamente");
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
    public T032TurnosGe getTurnosGe(java.lang.Integer id) {
        return getTurnosGeFacade().find(id);
    }
    
    @FacesConverter(forClass = T032TurnosGe.class)
    public static class TurnosGeControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TurnosGeController controller = (TurnosGeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "turnosGeController");
            return controller.getTurnosGe(getKey(value));
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
            if (object instanceof T032TurnosGe) {
                T032TurnosGe o = (T032TurnosGe) object;
                return getStringKey(o.getIdTurno());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T032TurnosGe.class.getName());
            }
        }
    }
}
