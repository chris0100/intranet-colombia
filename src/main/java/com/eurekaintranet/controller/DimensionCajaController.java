/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T023DimensionCaja;
import com.eurekaintranet.sessions.T023DimensionCajaFacade;
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
@Named(value = "dimensionCajaController")
@SessionScoped
public class DimensionCajaController implements Serializable {

    @EJB
    private T023DimensionCajaFacade dimensionCajaFacade;
    private T023DimensionCaja dimensionCajaActual;
    private List<T023DimensionCaja> listaDimensionCaja;
    
    private T001Usuario usuarioActual;
       
    public DimensionCajaController() {
    }

    public T023DimensionCajaFacade getDimensionCajaFacade() {
        return dimensionCajaFacade;
    }

    public void setDimensionCajaFacade(T023DimensionCajaFacade dimensionCajaFacade) {
        this.dimensionCajaFacade = dimensionCajaFacade;
    }

    public T023DimensionCaja getDimensionCajaActual() {
        return dimensionCajaActual;
    }

    public void setDimensionCajaActual(T023DimensionCaja dimensionCajaActual) {
        this.dimensionCajaActual = dimensionCajaActual;
    }

    public List<T023DimensionCaja> getListaDimensionCaja() {
        return listaDimensionCaja = getDimensionCajaFacade().findAll();
    }

    public void setListaDimensionCaja(List<T023DimensionCaja> listaDimensionCaja) {
        this.listaDimensionCaja = listaDimensionCaja;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
   
     //Navigation
     public void recargarLista(){
        listaDimensionCaja=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        dimensionCajaActual = new T023DimensionCaja();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modListaEmpaque/dimensionCaja/lista";
    }
    public String add() {
        try { 
            obtenerDescripcionCaja();
            dimensionCajaActual.setResponsable(usuarioActual.toString());
            dimensionCajaActual.setFechaCreacion(new Date());
            getDimensionCajaFacade().create(dimensionCajaActual);
            addSuccessMessage("Dimensiones de la caja creada correctamente", "Dimensiones de la caja creada correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            obtenerDescripcionCaja();
            dimensionCajaActual.setResponsable(usuarioActual.toString());
            dimensionCajaActual.setFechaModificacion(new Date());
            getDimensionCajaFacade().edit(dimensionCajaActual);
            addSuccessMessage("Dimensiones de la caja editada correctamente", "Dimensiones de la caja editada correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    private void obtenerDescripcionCaja(){
        String alto = String.valueOf(dimensionCajaActual.getAlto());
        String largo = String.valueOf(dimensionCajaActual.getLargo());
        String ancho = String.valueOf(dimensionCajaActual.getAncho());
        dimensionCajaActual.setDescripciones(alto+"*"+largo+"*"+ancho);
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
    public T023DimensionCaja getDimension(java.lang.Integer id) {
        return getDimensionCajaFacade().find(id);
    }
    
    @FacesConverter(forClass = T023DimensionCaja.class)
    public static class DimensionControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DimensionCajaController controller = (DimensionCajaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "dimensionCajaController");
            return controller.getDimension(getKey(value));
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
            if (object instanceof T023DimensionCaja) {
                T023DimensionCaja o = (T023DimensionCaja) object;
                return getStringKey(o.getIdDimension());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T023DimensionCaja.class.getName());
            }
        }

    }
}
