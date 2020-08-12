/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T027Periodo;
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
@Named(value = "periodoController")
@SessionScoped
public class PeriodoController implements Serializable {

    @EJB
    private T027PeriodoFacade periodoFacade;
    private T027Periodo periodoActual;
    private List<T027Periodo> listaPeriodo;
    public PeriodoController() {
    }

    public T027PeriodoFacade getPeriodoFacade() {
        return periodoFacade;
    }

    public void setPeriodoFacade(T027PeriodoFacade periodoFacade) {
        this.periodoFacade = periodoFacade;
    }

    public T027Periodo getPeriodoActual() {
        return periodoActual;
    }

    public void setPeriodoActual(T027Periodo periodoActual) {
        this.periodoActual = periodoActual;
    }

    
    public List<T027Periodo> getListaPeriodo() {
        return listaPeriodo = getPeriodoFacade().consultaPeriodo();
    }

    public void setListaPeriodo(List<T027Periodo> listaPeriodo) {
        this.listaPeriodo = listaPeriodo;
    }
    //Navegacion
     public void recargarLista(){
        listaPeriodo=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        periodoActual = new T027Periodo();
        Calendar cal= Calendar.getInstance();
        periodoActual.setAnio(cal.get(Calendar.YEAR));
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modPlaneacionKos/periodo/lista";
    }
    public String add() {
        try { 
            getPeriodoFacade().create(periodoActual);
            periodoActual.setMes(2);
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
            getPeriodoFacade().edit(periodoActual);
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
    public T027Periodo getPeriodo(java.lang.Integer id) {
        return getPeriodoFacade().find(id);
    }
    
    @FacesConverter(forClass = T027Periodo.class)
    public static class PeriodoControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PeriodoController controller = (PeriodoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "periodoController");
            return controller.getPeriodo(getKey(value));
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
            if (object instanceof T027Periodo) {
                T027Periodo o = (T027Periodo) object;
                return getStringKey(o.getIdPeriodo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T027Periodo.class.getName());
            }
        }

    }
}
