/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T035ParadasGe;
import com.eurekaintranet.sessions.T035ParadasGeFacade;
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
 * @author Luis Carlos Cabal
 */
@Named(value = "paradasGeController")
@SessionScoped
public class ParadasGeController implements Serializable {

    /**
     * Creates a new instance of ParadasGeController
     */
    @EJB
    private T035ParadasGeFacade paradasGeFacade;
    private T035ParadasGe paradasGeActual;
    private List<T035ParadasGe> listaParadasGe;
    
    private T001Usuario usuarioActual; 
    
    public ParadasGeController() {
    }
    
    public T035ParadasGeFacade getParadasGeFacade() {
        return paradasGeFacade;
    }

    public void setParadasGeFacade(T035ParadasGeFacade paradasGeFacade) {
        this.paradasGeFacade = paradasGeFacade;
    }

    public T035ParadasGe getParadasGeActual() {
        return paradasGeActual;
    }

    public void setParadasGeActual(T035ParadasGe paradasGeActual) {
        this.paradasGeActual = paradasGeActual;
    }

    public List<T035ParadasGe> getListaParadasGe() {
        return listaParadasGe = getParadasGeFacade().findAll();
    }
    public List<T035ParadasGe> getListaParadasGeEstadoActivo() {
        return listaParadasGe = getParadasGeFacade().consultarEstadoParada(true);
    }
    public void setListaParadasGe(List<T035ParadasGe> listaParadasGe) {
        this.listaParadasGe = listaParadasGe;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
    
    //Navigation
     public void recargarLista(){
        listaParadasGe=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        int consecutivo = 1;
        for(T035ParadasGe item: getParadasGeFacade().findAll()){
            consecutivo ++;
        }
        paradasGeActual = new T035ParadasGe();
        paradasGeActual.setCodigo(String.format("%03d", consecutivo));
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGeTelares/paradas/lista";
    }
    public String add() {
        try { 
            paradasGeActual.setEstado(true);
            paradasGeActual.setUsuarioCreacion(usuarioActual.getUsername());
            paradasGeActual.setFechaCreacion(new Date());
            paradasGeActual.setCodigo(paradasGeActual.getIdEsquema().getLetra().toUpperCase()+paradasGeActual.getCodigo());
            getParadasGeFacade().create(paradasGeActual);
            addSuccessMessage("Parada creada correctamente", "Parada creada correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            paradasGeActual.setUsuarioActualizacion(usuarioActual.getUsername());
            paradasGeActual.setFechaActualizacion(new Date());
            getParadasGeFacade().edit(paradasGeActual);
            addSuccessMessage("Parada editada correctamente", "Parada editada correctamente");
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
    public T035ParadasGe getParadasGe(java.lang.Integer id) {
        return getParadasGeFacade().find(id);
    }
    
    @FacesConverter(forClass = T035ParadasGe.class)
    public static class ParadasGeControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ParadasGeController controller = (ParadasGeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "paradasGeController");
            return controller.getParadasGe(getKey(value));
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
            if (object instanceof T035ParadasGe) {
                T035ParadasGe o = (T035ParadasGe) object;
                return getStringKey(o.getIdParada());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T035ParadasGe.class.getName());
            }
        }
    }
}
