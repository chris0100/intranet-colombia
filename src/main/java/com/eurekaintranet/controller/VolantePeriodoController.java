/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T016PeriodoVolanteNomina;
import com.eurekaintranet.sessions.T016PeriodoVolanteNominaFacade;
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
import javax.faces.validator.ValidatorException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Lccabalr
 */
@Named(value = "volantePeriodoController")
@SessionScoped
public class VolantePeriodoController implements Serializable {

    @EJB
    private T016PeriodoVolanteNominaFacade periodoVolanteFacade;
    private T016PeriodoVolanteNomina periodoVolanteActual;
    private List<T016PeriodoVolanteNomina> listaPeriodoVolante;
    
    private T001Usuario usuarioActual;
    
    
    public VolantePeriodoController() {
    }

    public T016PeriodoVolanteNominaFacade getPeriodoVolanteFacade() {
        return periodoVolanteFacade;
    }

    public void setPeriodoVolanteFacade(T016PeriodoVolanteNominaFacade periodoVolanteFacade) {
        this.periodoVolanteFacade = periodoVolanteFacade;
    }

    public T016PeriodoVolanteNomina getPeriodoVolanteActual() {
        return periodoVolanteActual;
    }

    public void setPeriodoVolanteActual(T016PeriodoVolanteNomina periodoVolanteActual) {
        this.periodoVolanteActual = periodoVolanteActual;
    }

    public List<T016PeriodoVolanteNomina> getListaPeriodoVolante() {
        return listaPeriodoVolante = getPeriodoVolanteFacade().consultaListaPeriodo();
    }

    public void setListaPeriodoVolante(List<T016PeriodoVolanteNomina> listaPeriodoVolante) {
        this.listaPeriodoVolante = listaPeriodoVolante;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
    
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

    
    //navigation
    public String prepareListPeriodo(){
        return "/administrador/modVolantesPago/periodo/listaPeriodo";
    }
    public void prepareCreatePeriodoDialog(){
        periodoVolanteActual = new T016PeriodoVolanteNomina();
    }
     public String prepareCreatePeriodo(){
        periodoVolanteActual = new T016PeriodoVolanteNomina();
        return "/administrador/modVolantesPago/periodo/crearPeriodo";
    }

    public void recargarLista(){
        listaPeriodoVolante=null;
    }
    public String add() {
        try { 
            T016PeriodoVolanteNomina consulta=getPeriodoVolanteFacade().findByPeriodoDuplicado(periodoVolanteActual.getAnio(),periodoVolanteActual.getMes(),periodoVolanteActual.getDia());
            if(consulta != null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo duplicado", "Error en la creación del periodo");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return null;
            }else{
                getPeriodoVolanteActual().setUsuarioCreacion(usuarioActual.getUsername());
                getPeriodoVolanteActual().setFechaCreacion(new Date());
                getPeriodoVolanteFacade().create(periodoVolanteActual);
                addSuccessMessage("Periodo creado correctamente", "periodo creado correctamente");
                recargarLista();
                return "listaPeriodo";
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public void addDialog() {
        try { 
            T016PeriodoVolanteNomina consulta=getPeriodoVolanteFacade().findByPeriodoDuplicado(periodoVolanteActual.getAnio(),periodoVolanteActual.getMes(),periodoVolanteActual.getDia());
            if(consulta != null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo duplicado", "Error en la creación del periodo");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo duplicado", "Error en la creación del periodo"));   
            }else{
                getPeriodoVolanteActual().setUsuarioCreacion(usuarioActual.getUsername());
                getPeriodoVolanteActual().setFechaCreacion(new Date());
                getPeriodoVolanteFacade().create(periodoVolanteActual);
                addSuccessMessage("Periodo creado correctamente", "periodo creado correctamente");
                recargarLista();
            }
        } catch (Exception e) {
            System.out.println("Error");
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
    }


    // Converter
    public T016PeriodoVolanteNomina getPeriodoVolanteNomina(java.lang.Integer id) {
        return getPeriodoVolanteFacade().find(id);
    }

    @FacesConverter(forClass = T016PeriodoVolanteNomina.class)
    public static class VolantePeriodoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VolantePeriodoController controller = (VolantePeriodoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "volantePeriodoController");
            return controller.getPeriodoVolanteNomina(getKey(value));
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
            if (object instanceof T016PeriodoVolanteNomina) {
                T016PeriodoVolanteNomina o = (T016PeriodoVolanteNomina) object;
                return getStringKey(o.getIdPeriodo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T016PeriodoVolanteNomina.class.getName());
            }
        }
    }
}
