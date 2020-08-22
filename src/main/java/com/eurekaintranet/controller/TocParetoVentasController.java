/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T009ParamEstadoItem;
import com.eurekaintranet.entities.T011GruposItems;
import com.eurekaintranet.sessions.T009ParamEstadoItemFacade;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Named(value = "tocParetoVentasController")
@SessionScoped
public class TocParetoVentasController implements Serializable {

    @EJB
    private T009ParamEstadoItemFacade tocParametrosFacade;
    private T009ParamEstadoItem tocParametrosActual;
    private List<T009ParamEstadoItem> listaTocParametros;
    
    private T001Usuario usuarioActual;
    private int partSumada;
    
    public TocParetoVentasController() {
    }

    public T009ParamEstadoItemFacade getTocParametrosFacade() {
        return tocParametrosFacade;
    }

    public void setTocParametrosFacade(T009ParamEstadoItemFacade tocParametrosFacade) {
        this.tocParametrosFacade = tocParametrosFacade;
    }

    public T009ParamEstadoItem getTocParametrosActual() {
        return tocParametrosActual;
    }

    public void setTocParametrosActual(T009ParamEstadoItem tocParametrosActual) {
        this.tocParametrosActual = tocParametrosActual;
    }


    public List<T009ParamEstadoItem> getListaTocParametros() {
        return listaTocParametros = getTocParametrosFacade().parametrosTotalesOrdenados();
    }
    
    public List<T009ParamEstadoItem> getListaTocParametrosVigente() {
        return listaTocParametros = getTocParametrosFacade().parametrosVigentes("VIGENTE");
    }
    

    public void setListaTocParametros(List<T009ParamEstadoItem> listaTocParametros) {
        this.listaTocParametros = listaTocParametros;
    }

    public int getPartSumada() {
        return partSumada;
    }

    public void setPartSumada(int partSumada) {
        this.partSumada = partSumada;
    }

   

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
    
      //Navigation
   
    
    public void recargarLista(){
        listaTocParametros=null;
    }
     public String prepareEdit() {
        return "editarParametro";
    }
      public String prepareCreate() {
        tocParametrosActual = new T009ParamEstadoItem();
        partSumada = 0;
        return "crearParametro";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "listaParametro";
    }


    public String add() {
        try { 
            tocParametrosActual.setUsuario(usuarioActual.toString());
            tocParametrosActual.setFechaModificacion(new Date());
            tocParametrosActual.setPartSumada(BigDecimal.valueOf(partSumada).movePointLeft(2));
            tocParametrosActual.setEstado("VIGENTE");
            getTocParametrosFacade().create(tocParametrosActual);
            addSuccessMessage("Parámetros configurados", "Los parámetros fueron cargados correctamente");
            recargarLista();
            return "listaParametro";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }


    public String update() {
        try {
            tocParametrosActual.setUsuario(usuarioActual.toString());
            tocParametrosActual.setFechaModificacion(new Date());
            tocParametrosActual.setPartSumada(BigDecimal.valueOf(partSumada).movePointLeft(2));
            getTocParametrosFacade().edit(tocParametrosActual);
            addSuccessMessage("Parámetros configurados", "Los parámetros fueron cargados correctamente");
            recargarLista();
            return "listaParametro";
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
     // Converter
    public T009ParamEstadoItem getTocParetoVentas(java.lang.Integer id) {
        return tocParametrosFacade.find(id);
    }
    @FacesConverter(forClass = T009ParamEstadoItem.class)
    public static class TocParetoVentasConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TocParetoVentasController controller = (TocParetoVentasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tocParetoVentasController");
            return controller.getTocParetoVentas(getKey(value));
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
            if (object instanceof T009ParamEstadoItem) {
                T009ParamEstadoItem o = (T009ParamEstadoItem) object;
                return getStringKey(o.getIdParamEstadoItem());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T009ParamEstadoItem.class.getName());
            }
        }

    }
}
