/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T026ConfiguracionCentroTrabajo;
import com.eurekaintranet.entities.T027Periodo;
import com.eurekaintranet.sessions.T026ConfiguracionCentroTrabajoFacade;
import com.eurekaintranet.sessions.T027PeriodoFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
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
@Named(value = "configuracionGanttController")
@SessionScoped
public class ConfiguracionGanttController implements Serializable {

    @EJB
    private T026ConfiguracionCentroTrabajoFacade configuracionFacade;
    private T026ConfiguracionCentroTrabajo configuracionActual;
    private List<T026ConfiguracionCentroTrabajo> listaConfiguracion;
    public ConfiguracionGanttController() {
    }

    public T026ConfiguracionCentroTrabajoFacade getConfiguracionFacade() {
        return configuracionFacade;
    }

    public void setConfiguracionFacade(T026ConfiguracionCentroTrabajoFacade configuracionFacade) {
        this.configuracionFacade = configuracionFacade;
    }

    public T026ConfiguracionCentroTrabajo getConfiguracionActual() {
        return configuracionActual;
    }

    public void setConfiguracionActual(T026ConfiguracionCentroTrabajo configuracionActual) {
        this.configuracionActual = configuracionActual;
    }

    public List<T026ConfiguracionCentroTrabajo> getListaConfiguracion() {
        return listaConfiguracion = getConfiguracionFacade().findAll();
    }

    public void setListaConfiguracion(List<T026ConfiguracionCentroTrabajo> listaConfiguracion) {
        this.listaConfiguracion = listaConfiguracion;
    }
     //Navegacion
     public void recargarLista(){
        listaConfiguracion=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        configuracionActual = new T026ConfiguracionCentroTrabajo();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }
     public String prepareDuplicar() {
        return "crear";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modPlaneacionKos/configuracion/lista";
    }
    public String add() {
        try { 
            configuracionActual.setCargaProyectada((configuracionActual.getHoraTurno()*configuracionActual.getUndHora())*configuracionActual.getNumeroTurnos()*configuracionActual.getNumeroMaquinas());
            getConfiguracionFacade().create(configuracionActual);
            addSuccessMessage("Periodo creado correctamente", "Periodo creado correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            configuracionActual.setCargaProyectada((configuracionActual.getHoraTurno()*configuracionActual.getUndHora())*configuracionActual.getNumeroTurnos()*configuracionActual.getNumeroMaquinas());
            getConfiguracionFacade().edit(configuracionActual);
            addSuccessMessage("Periodo editado correctamente", "Periodo editado correctamente");
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
    public T026ConfiguracionCentroTrabajo getConfiguracion(java.lang.Integer id) {
        return getConfiguracionFacade().find(id);
    }
    
    @FacesConverter(forClass = T026ConfiguracionCentroTrabajo.class)
    public static class ConfiguracionGanttControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ConfiguracionGanttController controller = (ConfiguracionGanttController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "configuracionGanttController");
            return controller.getConfiguracion(getKey(value));
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
            if (object instanceof T026ConfiguracionCentroTrabajo) {
                T026ConfiguracionCentroTrabajo o = (T026ConfiguracionCentroTrabajo) object;
                return getStringKey(o.getIdConfiguracion());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T026ConfiguracionCentroTrabajo.class.getName());
            }
        }

    }
}
