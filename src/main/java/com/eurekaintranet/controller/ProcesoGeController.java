/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T043LineaProduccionGe;
import com.eurekaintranet.entities.T044ProcesoGe;
import com.eurekaintranet.sessions.T043LineaProduccionGeFacade;
import com.eurekaintranet.sessions.T044ProcesoGeFacade;
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
@Named(value = "procesoGeController")
@SessionScoped
public class ProcesoGeController implements Serializable {

    @EJB
    private T044ProcesoGeFacade procesoFacade;
    private T044ProcesoGe procesoActual;
    private List<T044ProcesoGe> listaProceso;
    public ProcesoGeController() {
    }

    public T044ProcesoGeFacade getProcesoFacade() {
        return procesoFacade;
    }

    public void setProcesoFacade(T044ProcesoGeFacade procesoFacade) {
        this.procesoFacade = procesoFacade;
    }

    public T044ProcesoGe getProcesoActual() {
        return procesoActual;
    }

    public void setProcesoActual(T044ProcesoGe procesoActual) {
        this.procesoActual = procesoActual;
    }

    public List<T044ProcesoGe> getListaProceso() {
        return listaProceso = getProcesoFacade().findAll();
    }

    public void setListaProceso(List<T044ProcesoGe> listaProceso) {
        this.listaProceso = listaProceso;
    }

  
     //Navigation
    public void recargarLista(){
        listaProceso=null;
    }
     public String prepareEdit() {
        return "editar";
    }
    public String prepareCreate() {
        procesoActual = new T044ProcesoGe();
        return "crear";
    }
    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGeTelares/proceso/lista";
    }
    public String add() {
        try { 
            getProcesoFacade().create(procesoActual);
            addSuccessMessage("Proceso creado correctamente", "Proceso creado correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            getProcesoFacade().edit(procesoActual);
            addSuccessMessage("Proceso editado correctamente", "Proceso editado correctamente");
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
    public T044ProcesoGe getProcesoGe(java.lang.Integer id) {
        return getProcesoFacade().find(id);
    }
    
    @FacesConverter(forClass = T044ProcesoGe.class)
    public static class ProcesoGeControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProcesoGeController controller = (ProcesoGeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "procesoGeController");
            return controller.getProcesoGe(getKey(value));
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
            if (object instanceof T044ProcesoGe) {
                T044ProcesoGe o = (T044ProcesoGe) object;
                return getStringKey(o.getIdProceso());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T044ProcesoGe.class.getName());
            }
        }
    }
}
