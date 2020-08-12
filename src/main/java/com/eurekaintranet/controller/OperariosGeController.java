/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T030OperariosGe;
import com.eurekaintranet.sessions.T030OperariosGeFacade;
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
@Named(value = "operariosGeController")
@SessionScoped
public class OperariosGeController implements Serializable {

    @EJB
    private T030OperariosGeFacade operariosGeFacade;
    private T030OperariosGe operariosGeActual;
    private List<T030OperariosGe> listaOperariosGe;
    
    private T001Usuario usuarioActual;
    private String registroLog ;
    public OperariosGeController() {
    }
    //get and set

    public T030OperariosGeFacade getOperariosGeFacade() {
        return operariosGeFacade;
    }

    public void setOperariosGeFacade(T030OperariosGeFacade operariosGeFacade) {
        this.operariosGeFacade = operariosGeFacade;
    }

    public T030OperariosGe getOperariosGeActual() {
        return operariosGeActual;
    }

    public void setOperariosGeActual(T030OperariosGe operariosGeActual) {
        this.operariosGeActual = operariosGeActual;
    }

    public List<T030OperariosGe> getListaOperariosGe() {
        return listaOperariosGe = getOperariosGeFacade().findAll();
    }
    public List<T030OperariosGe> getListaOperariosGeEstadoActivo() {
        return listaOperariosGe = getOperariosGeFacade().consultarEstadoOperario(true);
    }
        
    public void setListaOperariosGe(List<T030OperariosGe> listaOperariosGe) {
        this.listaOperariosGe = listaOperariosGe;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
     //Navigation
     public void recargarLista(){
        listaOperariosGe=null;
        registroLog = "";
    }
     public String prepareEdit() {
        if(operariosGeActual.getIdEquipo()!=null){
           registroLog = operariosGeActual.getIdEquipo().getDescripcion(); 
        }
        return "editar";
    }
    public String prepareCreate() {
        operariosGeActual = new T030OperariosGe();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGeTelares/operarios/lista";
    }
    public String add() {
        try { 
            operariosGeActual.setEstado(true);
            operariosGeActual.setUsuarioCreacion(usuarioActual.getUsername());
            operariosGeActual.setFechaCreacion(new Date());
            getOperariosGeFacade().create(operariosGeActual);
            addSuccessMessage("Operario creado correctamente", "Operario creado correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            if(operariosGeActual.getIdEquipo()!=null && !registroLog.equals(operariosGeActual.getIdEquipo().getDescripcion())){
                operariosGeActual.setEquipoLog(registroLog);
                operariosGeActual.setUsuarioActualizacion(usuarioActual.getUsername());
                operariosGeActual.setFechaActualizacion(new Date());
            }
            getOperariosGeFacade().edit(operariosGeActual);
            addSuccessMessage("Operario editado correctamente", "Operario editado correctamente");
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
    public T030OperariosGe getOperario(java.lang.Integer id) {
        return getOperariosGeFacade().find(id);
    }
    
    @FacesConverter(forClass = T030OperariosGe.class)
    public static class OperariosGeControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OperariosGeController controller = (OperariosGeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "operariosGeController");
            return controller.getOperario(getKey(value));
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
            if (object instanceof T030OperariosGe) {
                T030OperariosGe o = (T030OperariosGe) object;
                return getStringKey(o.getIdOperario());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T030OperariosGe.class.getName());
            }
        }
    }
}
