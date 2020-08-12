/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.controller.util.JsfUtil;
import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T013CreacionItem;
import com.eurekaintranet.sessions.EmailSessionBean;
import com.eurekaintranet.sessions.T013CreacionItemFacade;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.persistence.Cacheable;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Lccabalr
 */

@Named(value = "creacionItemController")
@SessionScoped
public class CreacionItemController implements Serializable{
    
    @EJB
    private T013CreacionItemFacade creacionItemFacade;
    private T013CreacionItem creacionItemActual;
    private List<T013CreacionItem> listaCreacionItem;
    
    @EJB
    EmailSessionBean emailBean;
    
    private Date fechaParametro1;
    private Date fechaParametro2;
    
    private String estadoItem;
    
    private T001Usuario usuarioActual;
    
    private List<String> listaExt1;
    private List<String> listaExt2;
    private List<String> listaItemPatron;
    private List<String> listaTipoInventario;
    private List<String> listaPrecio;
    private List<String> listaUndInventario;
    private List<String> listaPosicionArancelaria;
    
    private String ext1;
    private String ext2;
    private String itemPatron;
    
    private String tipoExt1;
    private String tipoExt2;
    private String tipoItemPatron;
    private String tipoItemPatronConca;
    private String idListaPrecio;
    private BigDecimal precio;
    private String moneda;
    
    private boolean indVenta;
    private boolean indManufactura;
    private boolean indCompra;
    
    private boolean aplicarTodo;
    private LoginController loginController = new LoginController();
    
    @PostConstruct
        public void init() {
            listaCreacionItem = new ArrayList<>();
            listaCreacionItem = getCreacionItemFacade().recargarListaItemsCreados();
        }
    
    public CreacionItemController() {
    }

    public T013CreacionItemFacade getCreacionItemFacade() {
        return creacionItemFacade;
    }

    public void setCreacionItemFacade(T013CreacionItemFacade creacionItemFacade) {
        this.creacionItemFacade = creacionItemFacade;
    }

    public T013CreacionItem getCreacionItemActual() {
        return creacionItemActual;
    }

    public void setCreacionItemActual(T013CreacionItem creacionItemActual) {
        this.creacionItemActual = creacionItemActual;
    }
    public List<String> getListaExt2() {
        return listaExt2;
    }
    public List<String> getListaExt1() {
        return listaExt1;
    }
    public List<T013CreacionItem> getListaCreacionItemSolicitudTodos() {
        return listaCreacionItem = getCreacionItemFacade().estadoCreacionItemTodos((short)0);
    }
    public List<T013CreacionItem> getListaCreacionItemRespuestaTodos() {
        return listaCreacionItem = getCreacionItemFacade().estadoCreacionItemRespuestaTodos((short)1);
    }
    public List<T013CreacionItem> getListaCreacionItemRechazosTodos() {
        return listaCreacionItem = getCreacionItemFacade().estadoCreacionItemTodos((short)2);
    }
    
    
    public List<T013CreacionItem> getListaCreacionItemSolicitud() {
        return listaCreacionItem = getCreacionItemFacade().estadoCreacionItem((short)0, (short)0);
    }
    public List<T013CreacionItem> getListaCreacionItemSolicitudItemPatron() {
        return listaCreacionItem = getCreacionItemFacade().estadoCreacionItem((short)0, (short)1);
    }
    
    public List<T013CreacionItem> getListaCreacionItemSinEnvio() {
        return listaCreacionItem = getCreacionItemFacade().estadoCreacionItem((short)1,(short)0);
    }
     public List<T013CreacionItem> getListaCreacionItemSinEnvioItemPatron() {
        return listaCreacionItem = getCreacionItemFacade().estadoCreacionItem((short)1,(short)1);
    }
     
    public List<T013CreacionItem> getListaCreacionItemRespuesta() {
        return listaCreacionItem = getCreacionItemFacade().estadoCreacionItemRespuesta((short)1, (short)0);
    }
    public List<T013CreacionItem> getListaCreacionItemRespuestaItemPatron() {
        return listaCreacionItem = getCreacionItemFacade().estadoCreacionItemRespuesta((short)1, (short)1);
    }
    
    public List<T013CreacionItem> getListaCreacionItemRechazos() {
        return listaCreacionItem = getCreacionItemFacade().estadoCreacionItem((short)2, (short)0);
    }
    public List<T013CreacionItem> getListaCreacionItemRechazosItemPatron() {
        return listaCreacionItem = getCreacionItemFacade().estadoCreacionItem((short)2, (short)1);
    }
    
    public List<T013CreacionItem> getListaCreacionItemCreado() {
        return listaCreacionItem = getCreacionItemFacade().recargarListaItemsCreados();
    }
    public List<T013CreacionItem> getListaReporteTiempo(){
        listaCreacionItem = new ArrayList<>();
        return listaCreacionItem = getCreacionItemFacade().consultaReporteTiempo(getFechaParametro1(), getFechaParametro2());
    }
    public void setListaCreacionItem(List<T013CreacionItem> listaCreacionItem) {
        this.listaCreacionItem = listaCreacionItem;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public String getEstadoItem() {
        return estadoItem;
    }

    public void setEstadoItem(String estadoItem) {
        this.estadoItem = estadoItem;
    }

    public List<String> getListaItemPatron() {
        return listaItemPatron;
    }

    public void setListaItemPatron(List<String> listaItemPatron) {
        this.listaItemPatron = listaItemPatron;
    }

    public String getItemPatron() {
        return itemPatron;
    }

    public void setItemPatron(String itemPatron) {
        this.itemPatron = itemPatron;
    }

    
    
    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public Date getFechaParametro1() {
        return fechaParametro1;
    }

    public void setFechaParametro1(Date fechaParametro1) {
        this.fechaParametro1 = fechaParametro1;
    }

    public Date getFechaParametro2() {
        return fechaParametro2;
    }

    public void setFechaParametro2(Date fechaParametro2) {
        this.fechaParametro2 = fechaParametro2;
    }

    public String getTipoExt1() {
        return tipoExt1;
    }

    public void setTipoExt1(String tipoExt1) {
        this.tipoExt1 = tipoExt1;
    }

    public String getTipoExt2() {
        return tipoExt2;
    }

    public void setTipoExt2(String tipoExt2) {
        this.tipoExt2 = tipoExt2;
    }

    public List<String> getListaTipoInventario() {
        return listaTipoInventario;
    }

    public void setListaTipoInventario(List<String> listaTipoInventario) {
        this.listaTipoInventario = listaTipoInventario;
    }

    public String getTipoItemPatronConca() {
        return tipoItemPatronConca;
    }
    public void setTipoItemPatronConca(String tipoItemPatronConca) {
        this.tipoItemPatronConca = tipoItemPatronConca;
    }

    public List<String> getListaPrecio() {
        return listaPrecio;
    }

    public void setListaPrecio(List<String> listaPrecio) {
        this.listaPrecio = listaPrecio;
    }

    public String getIdListaPrecio() {
        return idListaPrecio;
    }

    public void setIdListaPrecio(String idListaPrecio) {
        this.idListaPrecio = idListaPrecio;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public boolean isAplicarTodo() {
        return aplicarTodo;
    }

    public void setAplicarTodo(boolean aplicarTodo) {
        this.aplicarTodo = aplicarTodo;
    }

    public List<String> getListaUndInventario() {
        return listaUndInventario;
    }

    public void setListaUndInventario(List<String> listaUndInventario) {
        this.listaUndInventario = listaUndInventario;
    }

    public boolean isIndVenta() {
        return indVenta;
    }

    public void setIndVenta(boolean indVenta) {
        this.indVenta = indVenta;
    }

    public boolean isIndManufactura() {
        return indManufactura;
    }

    public void setIndManufactura(boolean indManufactura) {
        this.indManufactura = indManufactura;
    }

    public boolean isIndCompra() {
        return indCompra;
    }

    public void setIndCompra(boolean indCompra) {
        this.indCompra = indCompra;
    }

    public List<String> getListaPosicionArancelaria() {
        return listaPosicionArancelaria = getCreacionItemFacade().consultaPosArancelaria();
    }

    public void setListaPosicionArancelaria(List<String> listaPosicionArancelaria) {
        this.listaPosicionArancelaria = listaPosicionArancelaria;
    }

  


    //Render forms
    public boolean isNacional(){
        return moneda.equals("")? false : moneda.equals("COP");
    }
    
    //Navigation
    
    public void extraerMoneda() {
        moneda = getIdListaPrecio().substring(4,7);
        creacionItemActual.setPrecio(null);
        
    }
    
    public void consultarTipoExt(){
        tipoExt1 = itemPatron.substring(63, 65);
        tipoExt2 = itemPatron.substring(65, 67);
        listaExt1 = getCreacionItemFacade().consultaExt1(tipoExt1);
        listaExt2 = getCreacionItemFacade().consultaExt2(tipoExt2);
    }
    public void recargarLista(){
        listaCreacionItem=null;
        ext1 = "";
        ext2 = "";
        itemPatron = "";
        estadoItem = "";
        idListaPrecio = "";
        moneda = "";
        aplicarTodo = false;
    }
     public String prepareEdit() {
        return "editar";
    }
    public String  prepareCreateSolicitudItemNuevo() {
        indCompra = false;
        indVenta = false;
        indManufactura = false;
        idListaPrecio = "001-COP-NACIONAL";
        moneda = "COP";
        listaPrecio = getCreacionItemFacade().consultaListaPrecio();
        listaItemPatron = getCreacionItemFacade().consultaListaItemPatron();
        listaUndInventario = getCreacionItemFacade().consultaListaUndInventario();
        creacionItemActual = new T013CreacionItem();
        creacionItemActual.setUnidadInventario("UND - UNIDAD");
        creacionItemActual.setTipoItem("Inventario");
        return "crearItemNuevo";
    }
    public String prepareCreateSolicitudItemExistente() {
        itemPatron = "";
        ext1 = "";
        ext2 = "";
        tipoExt1 = "";
        tipoExt1 = "";
        tipoItemPatron = ""; 
        idListaPrecio = "001-COP-NACIONAL";
        moneda = "COP";
        listaExt1 = getCreacionItemFacade().consultaExt1(tipoExt1);
        listaExt2 = getCreacionItemFacade().consultaExt2(tipoExt2);
        listaPrecio = getCreacionItemFacade().consultaListaPrecio();
        listaItemPatron = getCreacionItemFacade().consultaListaItemPatron();
        creacionItemActual = new T013CreacionItem();
        return "crearItemExistente";
    }
    public String prepareDuplicarSolicitud() {
        if(creacionItemActual.getItemNuevo()== (short)0){
            tipoItemPatron = "";
            listaExt1 = getCreacionItemFacade().consultaExt1(tipoExt1);
            listaExt2 = getCreacionItemFacade().consultaExt2(tipoExt2);
            listaItemPatron = getCreacionItemFacade().consultaListaItemPatron();        
            listaTipoInventario = getCreacionItemFacade().consultaListaTipoInventario();
            itemPatron = creacionItemActual.getDescripcionCompletaItem();
            tipoExt1 = itemPatron.substring(63, 65);
            tipoExt2 = itemPatron.substring(65, 67);
            listaPrecio = getCreacionItemFacade().consultaListaPrecio();
            ext1 = creacionItemActual.getDescripcionExt1();
            ext2 = creacionItemActual.getDescripcionExt2();
            idListaPrecio = creacionItemActual.getDescripcionListaPrecio();
           return "crearItemExistente"; 
        }else{
            listaUndInventario = getCreacionItemFacade().consultaListaUndInventario();
            listaPrecio = getCreacionItemFacade().consultaListaPrecio();
            idListaPrecio = creacionItemActual.getDescripcionListaPrecio();
           return "crearItemNuevo";        
        }       
    }
    public String prepareEditSolicitud() {
        
        if(creacionItemActual.getItemNuevo()== (short)0){
            tipoItemPatron = "";
            listaExt1 = getCreacionItemFacade().consultaExt1(tipoExt1);
            listaExt2 = getCreacionItemFacade().consultaExt2(tipoExt2);
            listaItemPatron = getCreacionItemFacade().consultaListaItemPatron();
            listaTipoInventario = getCreacionItemFacade().consultaListaTipoInventario();
            itemPatron = creacionItemActual.getDescripcionCompletaItem();
            tipoExt1 = itemPatron.substring(63, 65);
            tipoExt2 = itemPatron.substring(65, 67);
            listaPrecio = getCreacionItemFacade().consultaListaPrecio();
            ext1 = creacionItemActual.getDescripcionExt1();
            ext2 = creacionItemActual.getDescripcionExt2();
            idListaPrecio = creacionItemActual.getDescripcionListaPrecio();
            moneda = creacionItemActual.getMoneda();
           return "editarItemExistente"; 
        }else{
            listaUndInventario = getCreacionItemFacade().consultaListaUndInventario();
            listaPrecio = getCreacionItemFacade().consultaListaPrecio();
            idListaPrecio = creacionItemActual.getDescripcionListaPrecio();
            moneda = creacionItemActual.getMoneda();
           return "editarItemNuevo";        
        }       
    }
    
    public List<String> completeExt1(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : listaExt1) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }
    public List<String> completeExt2(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : listaExt2) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }
    public List<String> completeItemPatron(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : listaItemPatron) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }
    
    
    public String prepareView() {
        return "ver";
    }
    public String prepareViewUser() {
        return "/usuario/modGestorContenido/verContenido";
    }   
    
    public String prepareListSolcitud() {
        recargarLista();
        return "/administrador/modGestorCreacionItems/solicitud/lista";
    }
    public String prepareListSolcitudItemPatron() {
        recargarLista();
        return "/administrador/modGestorCreacionItems/solicitud/listaItemPatronNuevo";
    }
    public String prepareListResponder() {
        recargarLista();
        return "/administrador/modGestorCreacionItems/respuesta/lista";
    }
    
    public String prepareListResponderItemNuevo() {
        recargarLista();
        return "/administrador/modGestorCreacionItems/respuesta/listaItemPatronNuevo";
    }
    public String prepareListRechazadas() {
        recargarLista();
        return "/administrador/modGestorCreacionItems/rechazos/lista";
    }
    public String prepareListRechazadasNuevo() {
        recargarLista();
        return "/administrador/modGestorCreacionItems/rechazos/listaItemPatronNuevo";
    }
    public String prepareListCreadas() {
        recargarLista();
        listaCreacionItem = getCreacionItemFacade().recargarListaItemsCreados();
        return "/administrador/modGestorCreacionItems/creado/lista";
    }
    public String addSolicitudItemNuevo() {
        try { 
            getCreacionItemActual().setUnoeeReactivado((short)0);
            getCreacionItemActual().setUnoeeListasMateriales((short)0);
            getCreacionItemActual().setUnoeeParametrosPlaneacion((short)0);
            getCreacionItemActual().setUnoeePrecios((short)0);
            getCreacionItemActual().setUnoeeCodigoBarra((short)0);
            getCreacionItemActual().setUnoeePortafolio((short)0);
            getCreacionItemActual().setIdListaPrecio(getIdListaPrecio().substring(0,3));
            getCreacionItemActual().setDescripcionListaPrecio(getIdListaPrecio());
            getCreacionItemActual().setMoneda(getIdListaPrecio().substring(4,7));
            getCreacionItemActual().setUsuarioSolicitud(usuarioActual.getUsername());
            getCreacionItemActual().setFechaSolicitud(new Date());
            getCreacionItemActual().setEstado((short)0);
            getCreacionItemActual().setItemNuevo((short)1);
            getCreacionItemFacade().create(creacionItemActual);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud creada correctamente", "Solicitud creada correctamente");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            recargarLista();
            return "listaItemPatronNuevo";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String addSolicitudItemExistente() {
        try {              
            String partirDescripcion ="";
            String partirCodigoItem = getItemPatron().substring(0,7);
            if(getItemPatron().length()>=50){
                 partirDescripcion = getItemPatron().substring(10,50).trim();
            }else{
                partirDescripcion = getItemPatron().substring(10,getItemPatron().length()).trim();
            }
            getCreacionItemActual().setCodigoItem(Integer.parseInt(partirCodigoItem));
            getCreacionItemActual().setDescripcion(partirDescripcion);
            getCreacionItemActual().setIdExt1Detalle(getExt1().substring(0,4));
            getCreacionItemActual().setDescripcionExt1(getExt1());
            getCreacionItemActual().setIdExt2Detalle(getExt2().substring(0,5));
            getCreacionItemActual().setDescripcionExt2(getExt2());
            if(!getCreacionItemFacade().consultaListaItemsRepetidosUnoEE(getCreacionItemActual().getCodigoItem(), getCreacionItemActual().getIdExt1Detalle(), getCreacionItemActual().getIdExt2Detalle()).isEmpty()){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Item repetido", "El item ya se encuentra creado en el UnoEE");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return null;
            }
            else if (!getCreacionItemFacade().consultaListaItemsRepetidosEureka(getCreacionItemActual().getCodigoItem(), getCreacionItemActual().getIdExt1Detalle(), getCreacionItemActual().getIdExt2Detalle(), (short) 1).isEmpty()){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Item repetido", "Ya existe una solicitud del mismo item");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return null;
            }else{
                if(!getCreacionItemFacade().consultaListaItemsInactivosUnoEE(getCreacionItemActual().getCodigoItem(), getCreacionItemActual().getIdExt1Detalle(), getCreacionItemActual().getIdExt2Detalle()).isEmpty()){
                    getCreacionItemActual().setUnoeeReactivado((short)1);
                }else{
                    getCreacionItemActual().setUnoeeReactivado((short)0);
                }
                getCreacionItemActual().setIdListaPrecio(getIdListaPrecio().substring(0,3));
                getCreacionItemActual().setDescripcionListaPrecio(getIdListaPrecio());
                getCreacionItemActual().setMoneda(getIdListaPrecio().substring(4,7));
                getCreacionItemActual().setDescripcionCompletaItem(getItemPatron());
                getCreacionItemActual().setUsuarioSolicitud(usuarioActual.getUsername());
                getCreacionItemActual().setFechaSolicitud(new Date());
                getCreacionItemActual().setEstado((short)0);
                getCreacionItemActual().setItemNuevo((short)0);
                getCreacionItemActual().setUnoeeListasMateriales((short)0);
                getCreacionItemActual().setUnoeeParametrosPlaneacion((short)0);
                getCreacionItemActual().setUnoeePrecios((short)0);
                getCreacionItemActual().setUnoeeCodigoBarra((short)0);
                getCreacionItemActual().setUnoeePortafolio((short)0);
                getCreacionItemActual().setIndCompra(false);
                getCreacionItemActual().setIndVenta(false);
                getCreacionItemActual().setIndManufactura(false);
                getCreacionItemFacade().create(creacionItemActual);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud creada correctamente", "Solicitud creada correctamente");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                //sendMailSolicitud();
                recargarLista();
                return "lista";
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String addRespuesta() {
        try {
            if(estadoItem.equals("rechazado")&&creacionItemActual.getDescripcionRespuesta().equals("")){
                addErrorMessage("La solicitud no editada correctamente", "La solicitud debe tiener descripción de rechazo");
                recargarLista();
                return null;
            }else{
                if(isAplicarTodo()==true){
                    for (T013CreacionItem item : getListaCreacionItemRespuesta()) {
                        item.setUsuarioCreacion(usuarioActual.getUsername());
                        item.setFechaCreacion(new Date());
                        if(estadoItem.equals("aprobado")){
                           item.setEstado((short)1);
                           item.setUnoeePrecios((short) 1);
                        }else if(estadoItem.equals("rechazado")){
                           item.setEstado((short)2); 
                           item.setDescripcionRespuesta(creacionItemActual.getDescripcionRespuesta());
                        }else if(estadoItem.equals("sin revisar")){
                           item.setEstado((short)0); 
                        }
                        getCreacionItemFacade().edit(item);
                    }
                    addSuccessMessage("La solicitud fue editada correctamente", "La solicitud fue editada correctamente para todos los items de esta lista");
                    recargarLista();
                    return null;
                }else{
                    creacionItemActual.setUsuarioCreacion(usuarioActual.getUsername());
                    creacionItemActual.setFechaCreacion(new Date());
                    if(estadoItem.equals("aprobado")){
                        creacionItemActual.setEstado((short)1);
                        creacionItemActual.setUnoeePrecios((short) 1);
                    }else if(estadoItem.equals("rechazado")){
                       creacionItemActual.setEstado((short)2); 
                    }
                    else if(estadoItem.equals("sin revisar")){
                       creacionItemActual.setEstado((short)0); 
                    }  
                    else if(estadoItem.equals("completado")){
                       creacionItemActual.setEstado((short)3); 
                    }  
                    getCreacionItemFacade().edit(creacionItemActual);
                    addSuccessMessage("La solicitud fue editada correctamente", "La solicitud fue editada correctamente");
                    recargarLista();
                    return null;
                }    
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String updateSolicitudRechazada() {
        try {
            if(creacionItemActual.getItemNuevo()== (short)0){
                getCreacionItemActual().setDescripcionListaPrecio(getIdListaPrecio());
                getCreacionItemActual().setIdListaPrecio(getIdListaPrecio().substring(0,3));
                getCreacionItemActual().setMoneda(getIdListaPrecio().substring(4,7));
                String partirCodigoItem = getItemPatron().substring(0,7);
                String partirDescripcion = getItemPatron().substring(10,50);

                getCreacionItemActual().setCodigoItem(Integer.parseInt(partirCodigoItem));
                getCreacionItemActual().setDescripcion(partirDescripcion);
                getCreacionItemActual().setIdExt1Detalle(getExt1().substring(0,4));
                getCreacionItemActual().setDescripcionExt1(getExt1());
                getCreacionItemActual().setIdExt2Detalle(getExt2().substring(0,5));
                getCreacionItemActual().setDescripcionExt2(getExt2());
                getCreacionItemActual().setUsuarioSolicitud(usuarioActual.getUsername());
                getCreacionItemActual().setEstado((short)0);
                getCreacionItemActual().setFechaSolicitud(new Date());
                getCreacionItemFacade().edit(creacionItemActual);
                addSuccessMessage("La solicitud fue editada correctamente", "La solicitud fue editada correctamente");
                recargarLista();
                return "lista";
            }else{
                getCreacionItemActual().setEstado((short)0);
                getCreacionItemActual().setUsuarioSolicitud(usuarioActual.getUsername());
                getCreacionItemActual().setFechaSolicitud(new Date());
                getCreacionItemFacade().edit(creacionItemActual);
                addSuccessMessage("La solicitud fue editada correctamente", "La solicitud fue editada correctamente");
                return "listaItemPatronNuevo"; 
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String updateSolicitud() {
        try {
            if(creacionItemActual.getItemNuevo()== (short)0){
               getCreacionItemActual().setDescripcionListaPrecio(getIdListaPrecio());
               getCreacionItemActual().setIdListaPrecio(getIdListaPrecio().substring(0,3));
               getCreacionItemActual().setMoneda(getIdListaPrecio().substring(4,7));
               String partirCodigoItem = getItemPatron().substring(0,7);
               String partirDescripcion = getItemPatron().substring(10,50);

               getCreacionItemActual().setCodigoItem(Integer.parseInt(partirCodigoItem));
               getCreacionItemActual().setDescripcion(partirDescripcion);
               getCreacionItemActual().setIdExt1Detalle(getExt1().substring(0,4));
               getCreacionItemActual().setDescripcionExt1(getExt1());
               getCreacionItemActual().setIdExt2Detalle(getExt2().substring(0,5));
               getCreacionItemActual().setDescripcionExt2(getExt2());
               getCreacionItemActual().setUsuarioSolicitud(usuarioActual.getUsername());
               getCreacionItemActual().setFechaSolicitud(new Date());
               getCreacionItemFacade().edit(creacionItemActual);
               addSuccessMessage("La solicitud fue editada correctamente", "La solicitud fue editada correctamente");
               recargarLista();
               return "lista";   
            }else{
                getCreacionItemActual().setUsuarioSolicitud(usuarioActual.getUsername());
                getCreacionItemActual().setFechaSolicitud(new Date());
                getCreacionItemFacade().edit(creacionItemActual);
                addSuccessMessage("La solicitud fue editada correctamente", "La solicitud fue editada correctamente");
                return "listaItemPatronNuevo"; 
            }
            
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String deleteSolicitud() {
        try {   
            getCreacionItemFacade().remove(creacionItemActual);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contenido eliminado correctamente", "El contenido fue eliminado con exito");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    public String prepareViewReporteTiempo(){
        return "/administrador/modGestorCreacionItems/creado/reporteCreados";
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
    //Email send
    private void sendMailSolicitud() {
        //String codigo ="";
        //if(creacionItemActual.getCodigoItem()==null){
        //    codigo="Item nuevo";
        //}
        String subject = "NUEVA SOLICITUD DE CREACIÓN DE ITEMS"/*+usuarioActual.getNombre()*/ ;
        StringBuilder mensaje = new StringBuilder();
        //mensaje.append("SOLICITUD CREADA POR:");
        //mensaje.append(usuarioActual.getNombre());
        mensaje.append("\nCOD. ITEM: ");
        mensaje.append(creacionItemActual.getCodigoItem());
        //mensaje.append("\nDESCRIPCIÓN DEL ITEM: ");
        /*mensaje.append(creacionItemActual.getDescripcion());
        mensaje.append("\nEXTENSIÓN 1: ");
        mensaje.append(creacionItemActual.getIdExt1Detalle());
        mensaje.append("\nEXTENSIÓN 2: ");
        mensaje.append(creacionItemActual.getIdExt2Detalle());
        mensaje.append("\nDESCRIPCIÓN EXTENSIÓN 1: ");
        mensaje.append(creacionItemActual.getDescripcionExt2());
        mensaje.append("\nGRUPO - PLAN 50: ");
        mensaje.append(creacionItemActual.getGrupo());
        mensaje.append("\nFECHA DE SOLICITUD: ");
        mensaje.append(creacionItemActual.getFechaCreacion().toLocaleString());*/
        sendMail("luisc@ekazipper.com", subject, mensaje.toString());  
    }
    private void sendMail(String to, String subject, String body) {
        try {
            emailBean.sendMail(to, subject, body);
            JsfUtil.addSuccessMessage("Mensaje Enviado Exitosamente");
        } catch (NamingException | MessagingException ex) {
            JsfUtil.addErrorMessage("Error sending message " + ex.getClass().getName());
        }
    }
     
    public String ejecutarSissItemExt() throws InterruptedException {
        //EJECUTAR PAQUETE SSIS DESDE JAVA - SOLO SE PUEDE SI ESTA LA BD LOCAL
        /*try {
            Process executionProcess = Runtime.getRuntime().exec("dtexec /f \"\\\\192.168.160.153\\Users\\lccabalr\\Desktop\\CREACION_ITEMS\\CREACION_ITEMS\\Eka_SSIS\\CREACION_ITEMS.dtsx\"");
            BufferedReader output = new BufferedReader(new InputStreamReader(new DataInputStream(executionProcess.getInputStream())));
            String readStr;
            while ((readStr = output.readLine()) != null) {
                System.out.println(readStr);
            }
            output.close();
            executionProcess.waitFor();
            if (executionProcess.exitValue() == 0) {
                System.out.println("SSIS execution succeeded\n");
            } else {
                System.out.println("SSIS execution failed\n");
            }
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }*/
        try {
            if(getCreacionItemFacade().estadoCreacionItem((short)1,(short)0 ).isEmpty()){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datos no procesados", "Los datos no fueron ejecutados correctamente, debido a que la lista se encuentra vacía");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return "lista";
            }else{
                getCreacionItemFacade().spEjecutarSsisItemExt();
                Thread.sleep(25000);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos procesados", "Los datos fueron ejecutados correctamente, verificar si pasan a estado terminado o rechazado");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                recargarLista();
                return "lista";
            }
            
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la ejecución", "Los datos no fueron ejecutados correctamente");
             return "lista";
        }
    }  
    public void recargarListaCreacionItems(){
        listaCreacionItem = new ArrayList<>();
        listaCreacionItem = getCreacionItemFacade().recargarListaItemsCreados();
    }
     public String ejecutarSpActualizarItemsCreados() throws InterruptedException {
        try {
            getCreacionItemFacade().spEjecutarActualizarItemsCreados();
            //Thread.sleep(18000);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos procesados", "Los datos fueron ejecutados correctamente, verificar si pasan a estado terminado o rechazado");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            recargarLista();
            recargarListaCreacionItems();
            return "lista";
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la ejecución", "Los datos no fueron ejecutados correctamente");
             return "lista";
        }
    }   
    public String ejecutarSpActualizarItemsCreadosPortafolio() throws InterruptedException {
        try {
            getCreacionItemFacade().spEjecutarActualizarItemsCreadosPortafolio();
            //Thread.sleep(18000);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos procesados", "Los datos fueron sincronizados correctamente");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            recargarLista();
            recargarListaCreacionItems();
            return "reporteCreados";
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la ejecución", "Los datos no fueron ejecutados correctamente");
             return "reporteCreados";
        }
    }
    
    //Crear item - pedidos web
    public void procesarItemPedidoWeb() throws IOException, SQLException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            itemPatron = "";
            ext1 = "";
            ext2 = "";
            consultarItemPedido(request.getParameter("id"));
            idListaPrecio = "001-COP-NACIONAL";
            moneda = "COP";
            listaPrecio = getCreacionItemFacade().consultaListaPrecio();
            creacionItemActual = new T013CreacionItem();
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/faces/administrador/modGestorCreacionItems/solicitud/crearItemExistentePedidosWeb.xhtml");
        } catch (Exception e) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml");
        }
    }
    public void consultarItemPedido(String idItem) throws SQLException{
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_ClientesEka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "SELECT id_item, descripcion_ext1, descripcion_ext2, descripcion_completa_item FROM t004_item_pedido WHERE id_item ="+idItem;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                itemPatron = rs.getString("descripcion_completa_item");
                ext1 = rs.getString("descripcion_ext1");
                ext2 = rs.getString("descripcion_ext2");
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    
}
