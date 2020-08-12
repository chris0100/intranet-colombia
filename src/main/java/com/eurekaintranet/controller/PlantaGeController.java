/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T042PlantaGe;
import com.eurekaintranet.sessions.T042PlantaGeFacade;
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
@Named(value = "plantaGeController")
@SessionScoped
public class PlantaGeController implements Serializable {

    @EJB
    private T042PlantaGeFacade plantaFacade;
    private T042PlantaGe plantaActual;
    private List<T042PlantaGe> listaPlanta;
    public PlantaGeController() {
    }

    public T042PlantaGeFacade getPlantaFacade() {
        return plantaFacade;
    }

    public void setPlantaFacade(T042PlantaGeFacade plantaFacade) {
        this.plantaFacade = plantaFacade;
    }

    public T042PlantaGe getPlantaActual() {
        return plantaActual;
    }

    public void setPlantaActual(T042PlantaGe plantaActual) {
        this.plantaActual = plantaActual;
    }

    public List<T042PlantaGe> getListaPlanta() {
        return listaPlanta  = getPlantaFacade().findAll();
    }

    public void setListaPlanta(List<T042PlantaGe> listaPlanta) {
        this.listaPlanta = listaPlanta;
    }
    //Navigation
    public void recargarLista(){
        listaPlanta=null;
    }
     public String prepareEdit() {
        return "editar";
    }
    public String prepareCreate() {
        plantaActual = new T042PlantaGe();
        return "crear";
    }
    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGeTelares/planta/lista";
    }
    public String add() {
        try { 
            getPlantaFacade().create(plantaActual);
            addSuccessMessage("Planta creada correctamente", "Planta creada correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            getPlantaFacade().edit(plantaActual);
            addSuccessMessage("Planta editada correctamente", "Planta editada correctamente");
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
    public T042PlantaGe getPlantaGe(java.lang.Integer id) {
        return getPlantaFacade().find(id);
    }
    
    @FacesConverter(forClass = T042PlantaGe.class)
    public static class UnidadNegocioGeControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PlantaGeController controller = (PlantaGeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "plantaGeController");
            return controller.getPlantaGe(getKey(value));
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
            if (object instanceof T042PlantaGe) {
                T042PlantaGe o = (T042PlantaGe) object;
                return getStringKey(o.getIdPlanta());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T042PlantaGe.class.getName());
            }
        }
    }
}
