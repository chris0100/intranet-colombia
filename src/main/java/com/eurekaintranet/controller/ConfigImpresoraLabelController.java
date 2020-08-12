/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T0231ConfigImpresoraLabel;
import com.eurekaintranet.sessions.T0231ConfigImpresoraLabelFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Lccabalr
 */
@Named(value = "configImpresoraLabelController")
@SessionScoped
public class ConfigImpresoraLabelController implements Serializable {

    @EJB
    private T0231ConfigImpresoraLabelFacade configImpresoraFacade;
    private T0231ConfigImpresoraLabel configImpresoraActual;
    private List<T0231ConfigImpresoraLabel> listaConfigImpresora;
    private List<String> listaImpresorasCreadas;
    
    public ConfigImpresoraLabelController() {
    }

    public T0231ConfigImpresoraLabelFacade getConfigImpresoraFacade() {
        return configImpresoraFacade;
    }

    public void setConfigImpresoraFacade(T0231ConfigImpresoraLabelFacade configImpresoraFacade) {
        this.configImpresoraFacade = configImpresoraFacade;
    }

    public T0231ConfigImpresoraLabel getConfigImpresoraActual() {
        return configImpresoraActual;
    }

    public void setConfigImpresoraActual(T0231ConfigImpresoraLabel configImpresoraActual) {
        this.configImpresoraActual = configImpresoraActual;
    }

    public List<T0231ConfigImpresoraLabel> getListaConfigImpresora() {
        return listaConfigImpresora = getConfigImpresoraFacade().findAll();
    }

    public void setListaConfigImpresora(List<T0231ConfigImpresoraLabel> listaConfigImpresora) {
        this.listaConfigImpresora = listaConfigImpresora;
    }

    public List<String> getListaImpresorasCreadas() {
        return listaImpresorasCreadas;
    }

    public void setListaImpresorasCreadas(List<String> listaImpresorasCreadas) {
        this.listaImpresorasCreadas = listaImpresorasCreadas;
    }
    
    //Navigation
    public void recargarLista(){
        listaConfigImpresora=null;
    }
    public String prepareEdit() {
        obtenerNombreImpresoras();
        return "editar";
    }
    public String prepareCreate() {
        configImpresoraActual = new T0231ConfigImpresoraLabel();
        obtenerNombreImpresoras();
        return "crear";
    }
    public String prepareView() {
        return "ver";
    }
    public String prepareList() {
        recargarLista();
        return "/administrador/modListaEmpaque/configImpresion/lista";
    }
    public String add() {
        try { 
            validarImpresoraEquipoAsignado();
            getConfigImpresoraFacade().create(configImpresoraActual);
            addSuccessMessage("Asignación de impresora creada", "Asignación de impresora creada correctamente");
            recargarLista();
            return "lista"; 
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al crear asignación de impresora","Es posible que ya exista este equipo en la asignación de impresoras");
            RequestContext.getCurrentInstance().showMessageInDialog(message); 
            return null;
        }
    }
    public String update() {
        try {
            getConfigImpresoraFacade().edit(configImpresoraActual);
            addSuccessMessage("Asignación de impresora actualizada", "Asignación de impresora actualizada correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String delete() {
        try {
            getConfigImpresoraFacade().remove(configImpresoraActual);
            addSuccessMessage("Asignación de impresora borrada", "Asignación de impresora borrada correctamente");
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
    
    public void obtenerNombreImpresoras() {
        listaImpresorasCreadas = new ArrayList<>();
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            listaImpresorasCreadas.add(printService.getName());
        }
    }
    
    public boolean validarImpresoraEquipoAsignado(){
        T0231ConfigImpresoraLabel configImpresora=getConfigImpresoraFacade().findByEquipoAsignado(configImpresoraActual.getNombreEquipo());
        if(configImpresora != null){
            return true;
        }else{
            return false;
        }
    }
}
