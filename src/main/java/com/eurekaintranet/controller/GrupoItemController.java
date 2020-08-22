/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T009ParamEstadoItem;
import com.eurekaintranet.entities.T010EstadoItem;
import com.eurekaintranet.entities.T011GruposItems;
import com.eurekaintranet.sessions.T009ParamEstadoItemFacade;
import com.eurekaintranet.sessions.T010EstadoItemFacade;
import com.eurekaintranet.sessions.T011GruposItemsFacade;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.context.RequestContext;

/**
 * @author Lccabalr
 */
@Named(value = "grupoItemController")
@SessionScoped
public class GrupoItemController implements Serializable {

    @EJB
    private T011GruposItemsFacade grupoItemFacade;
    private T011GruposItems grupoItemActual;
    private List<T011GruposItems> listaGrupoItem;
    @EJB
    private T009ParamEstadoItemFacade tocParametrosFacade;
    private List<T009ParamEstadoItem> listaTocParametros = null;
    @EJB
    private T010EstadoItemFacade estadoItemFacade;
    private List<T010EstadoItem> listaEstadoItem = null;

    private T001Usuario usuarioActual;
    private String plan50;

    public GrupoItemController() {
    }

    public T011GruposItemsFacade getGrupoItemFacade() {
        return grupoItemFacade;
    }

    public void setGrupoItemFacade(T011GruposItemsFacade grupoItemFacade) {
        this.grupoItemFacade = grupoItemFacade;
    }

    public T011GruposItems getGrupoItemActual() {
        return grupoItemActual;
    }

    public void setGrupoItemActual(T011GruposItems grupoItemActual) {
        this.grupoItemActual = grupoItemActual;
    }

    public List<T011GruposItems> getListaGrupoItem() {
        return listaGrupoItem = getGrupoItemFacade().findAll();
    }

    public void setListaGrupoItem(List<T011GruposItems> listaGrupoItem) {
        this.listaGrupoItem = listaGrupoItem;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }


    public T009ParamEstadoItemFacade getTocParametrosFacade() {
        return tocParametrosFacade;
    }

    public void setTocParametrosFacade(T009ParamEstadoItemFacade tocParametrosFacade) {
        this.tocParametrosFacade = tocParametrosFacade;
    }

    public List<T009ParamEstadoItem> getListaTocParametros() {
        return listaTocParametros;
    }

    public void setListaTocParametros(List<T009ParamEstadoItem> listaTocParametros) {
        this.listaTocParametros = listaTocParametros;
    }

    public T010EstadoItemFacade getEstadoItemFacade() {
        return estadoItemFacade;
    }

    public void setEstadoItemFacade(T010EstadoItemFacade estadoItemFacade) {
        this.estadoItemFacade = estadoItemFacade;
    }

    public List<T010EstadoItem> getListaEstadoItem() {
        return listaEstadoItem;
    }

    public void setListaEstadoItem(List<T010EstadoItem> listaEstadoItem) {
        this.listaEstadoItem = listaEstadoItem;
    }


    public List<T010EstadoItem> getListaEstadoItemComercial() {
        if (listaEstadoItem == null) {
            return listaEstadoItem = new ArrayList<>();
        } else {
            return listaEstadoItem = getEstadoItemFacade().estadoItemPlan50Comercial(plan50);
        }
    }

    public String getPlan50() {
        return plan50;
    }

    public void setPlan50(String plan50) {
        this.plan50 = plan50;
    }

    //Navigation

    public void consultarTocParametros() {
        listaTocParametros = getTocParametrosFacade().parametrosSelect(grupoItemActual.getIdParamEstadoItem().getIdParamEstadoItem(), "VIGENTE");
    }

    public void recargarLista() {
        plan50 = "";
        listaGrupoItem = null;
    }

    public String prepareEdit() {
        return "editar";
    }

    public String prepareCreate() {
        grupoItemActual = new T011GruposItems();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGestorItems/planes/listaPlan50";
    }

    public String add() {
        try {
            getGrupoItemFacade().create(grupoItemActual);
            addSuccessMessage("Tipo de archivo creado correctamente", "Tipo de archivo creado correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }

    public String update() {
        try {
            System.out.println(grupoItemActual.getDescripcion() + " : " + grupoItemActual.getIdParamEstadoItem() + " : " + grupoItemActual.getGrupoPendiente());
            getGrupoItemFacade().edit(grupoItemActual);
            addSuccessMessage("Tipo de archivo editado correctamente", "Tipo de archivo editado correctamente");
            recargarLista();
            return "listaPlan50";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }


    public String updateEnviarDatosComercial() {

        try {
            if (grupoItemActual.getIdParamEstadoItem() == null) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Items no enviados", "Los items del grupo " + grupoItemActual.getDescripcion() + " no fueron enviados debido a que no tienen parámetros asignados");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return "listaPlan50";
            }

            else {
                getGrupoItemFacade().envioDatosSp(grupoItemActual.getDescripcion(),
                        usuarioActual.toString(),
                        grupoItemActual.getIdParamEstadoItem().getFechaInicial(),
                        grupoItemActual.getIdParamEstadoItem().getFechaFinal(),
                        grupoItemActual.getIdParamEstadoItem().getCantidadClientes(),
                        grupoItemActual.getIdParamEstadoItem().getRotacion(),
                        grupoItemActual.getIdParamEstadoItem().getPartSumada(),
                        grupoItemActual.getIdParamEstadoItem().getDiasHabiles());
                plan50 = grupoItemActual.getDescripcion();
                grupoItemActual.setGrupoPendiente((short) 1);
                //guarda en BD
                grupoItemFacade.edit(grupoItemActual);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Items enviados", "Los items del grupo " + grupoItemActual.getDescripcion() + " fueron enviados al proceso comercial para su respectiva evaluación de estado 1");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return "listaItemsEnviados";
            }
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
    public T011GruposItems getGruposItems(java.lang.Integer id) {
        return grupoItemFacade.find(id);
    }

    @FacesConverter(forClass = T011GruposItems.class)
    public static class TipoGrupoItemConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GrupoItemController controller = (GrupoItemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "grupoItemController");
            return controller.getGruposItems(getKey(value));
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
            if (object instanceof T011GruposItems) {
                T011GruposItems o = (T011GruposItems) object;
                return getStringKey(o.getIdGrupo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T011GruposItems.class.getName());
            }
        }
    }

    public void doSomething() {
        try {
            // simulate a long running request
            Thread.sleep(100000);
        } catch (Exception e) {
            // ignore
        }
    }
}
