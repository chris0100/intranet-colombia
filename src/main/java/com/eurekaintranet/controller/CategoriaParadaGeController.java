/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T038CategoriaParadaGe;
import com.eurekaintranet.sessions.T038CategoriaParadaGeFacade;
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
 * @author luis carlos cabal
 */
@Named(value = "categoriaParadaGeController")
@SessionScoped
public class CategoriaParadaGeController implements Serializable {

    @EJB
    private T038CategoriaParadaGeFacade categoriaGeFacade;
    private T038CategoriaParadaGe categoriaGeActual;
    private List<T038CategoriaParadaGe> listaCategoriaGe;
    
    public CategoriaParadaGeController() {
    }

    public T038CategoriaParadaGeFacade getCategoriaGeFacade() {
        return categoriaGeFacade;
    }

    public void setCategoriaGeFacade(T038CategoriaParadaGeFacade categoriaGeFacade) {
        this.categoriaGeFacade = categoriaGeFacade;
    }

    public T038CategoriaParadaGe getCategoriaGeActual() {
        return categoriaGeActual;
    }

    public void setCategoriaGeActual(T038CategoriaParadaGe categoriaGeActual) {
        this.categoriaGeActual = categoriaGeActual;
    }

    public List<T038CategoriaParadaGe> getListaCategoriaGe() {
        return listaCategoriaGe = getCategoriaGeFacade().findAll();
    }

    public void setListaCategoriaGe(List<T038CategoriaParadaGe> listaCategoriaGe) {
        this.listaCategoriaGe = listaCategoriaGe;
    }
    
     //Navigation
     public void recargarLista(){
        listaCategoriaGe=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        categoriaGeActual = new T038CategoriaParadaGe();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGeTelares/categoria/lista";
    }
    public String add() {
        try { 
            getCategoriaGeFacade().create(categoriaGeActual);
            addSuccessMessage("Categoria creada correctamente", "Categoria creada correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            getCategoriaGeFacade().edit(categoriaGeActual);
            addSuccessMessage("Categoria editada correctamente", "Categoria editada correctamente");
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
    public T038CategoriaParadaGe getCategoriaGe(java.lang.Integer id) {
        return getCategoriaGeFacade().find(id);
    }
     @FacesConverter(forClass = T038CategoriaParadaGe.class)
    public static class CategoriaParadaGeControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            
            CategoriaParadaGeController controller = (CategoriaParadaGeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "categoriaParadaGeController");
            return controller.getCategoriaGe(getKey(value));
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
            if (object instanceof T038CategoriaParadaGe) {
                T038CategoriaParadaGe o = (T038CategoriaParadaGe) object;
                return getStringKey(o.getIdCategoria());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T038CategoriaParadaGe.class.getName());
            }
        }
    }
}
