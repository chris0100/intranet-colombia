/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T046ConfigOrdenDespacho;
import com.eurekaintranet.sessions.T046ConfigOrdenDespachoFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Lccabalr
 */
@Named(value = "configOrdenDespachoController")
@SessionScoped
public class ConfigOrdenDespachoController implements Serializable {

    @EJB
    private T046ConfigOrdenDespachoFacade configFacade;
    private T046ConfigOrdenDespacho configActual;
    private List<T046ConfigOrdenDespacho> listaConfig;
    
    private boolean campoNumerico;
    private boolean campoString;
    public ConfigOrdenDespachoController() {
    }

    public T046ConfigOrdenDespachoFacade getConfigFacade() {
        return configFacade;
    }

    public void setConfigFacade(T046ConfigOrdenDespachoFacade configFacade) {
        this.configFacade = configFacade;
    }

    public T046ConfigOrdenDespacho getConfigActual() {
        return configActual;
    }

    public void setConfigActual(T046ConfigOrdenDespacho configActual) {
        this.configActual = configActual;
    }

    public List<T046ConfigOrdenDespacho> getListaConfig() {
        return listaConfig = getConfigFacade().findAll();
    }

    public void setListaConfig(List<T046ConfigOrdenDespacho> listaConfig) {
        this.listaConfig = listaConfig;
    }

    public boolean isCampoNumerico() {
        return campoNumerico;
    }

    public void setCampoNumerico(boolean campoNumerico) {
        this.campoNumerico = campoNumerico;
    }

    public boolean isCampoString() {
        return campoString;
    }

    public void setCampoString(boolean campoString) {
        this.campoString = campoString;
    }
    
    
    //Navigation
     public void recargarLista(){
        listaConfig=null;
    }
     public String prepareEdit() {
         campoNumerico = false;
         campoString = false;
        if(configActual.getTipo().equals("Tope maximo")){
            campoNumerico=true;
        }else{
            campoString=true;
        }
        return "editarConfiguracion";
    }
      public String prepareCreate() {
        configActual = new T046ConfigOrdenDespacho();
        return "crearConfiguracion";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "listaConfiguracion";
    }
    
    public boolean validarTipoDocumento(){
        T046ConfigOrdenDespacho consulta=getConfigFacade().findByTipoDocumento(configActual.getValor());
        if(consulta != null){
            return false;
        }else{
           return true;
        }
    }
    public String addTipoDocumento() {
        try { 
            if(validarTipoDocumento()){
                configActual.setTipo("Tipo documento");
                getConfigFacade().create(configActual);
                addSuccessMessage("Configuración creada correctamente", "Configuración creada correctamente");
                recargarLista();
                return "listaConfiguracion";
            }else{
                addErrorMessage("Configuración no creada", "El numero de documento ya se encuentra registrado");
                return null;
            }
            
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            getConfigFacade().edit(configActual);
            addSuccessMessage("Configuración editada correctamente", "Configuración editada correctamente");
            recargarLista();
            return "listaConfiguracion";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String delete() {
        try {
            if(configActual.getTipo().equals("Tope maximo") || configActual.getTipo().equals("Correo envio reporte")){
                addErrorMessage("Esta configuración no se puede eliminar", "Esta configuración no se puede eliminar");
            }else{
                getConfigFacade().remove(configActual);
                addSuccessMessage("Configuración eliminada correctamente", "Configuración editada correctamente");
                recargarLista();
            }
            return "listaConfiguracion";
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
}
