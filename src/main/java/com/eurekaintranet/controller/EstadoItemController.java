/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T010EstadoItem;
import com.eurekaintranet.entities.T011GruposItems;
import com.eurekaintranet.sessions.T010EstadoItemFacade;
import com.eurekaintranet.sessions.T011GruposItemsFacade;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 * @author Lccabalr
 */
@Named(value = "estadoItemController")
@SessionScoped
public class EstadoItemController implements Serializable {

    @EJB
    private T010EstadoItemFacade estadoItemFacade;
    private T010EstadoItem estadoItemActual;
    private List<T010EstadoItem> listaEstadoItem = null;
    private List<T010EstadoItem> listaEstadoItemFiltro = null;
    @EJB
    private T011GruposItemsFacade grupoItemFacade;
    private T011GruposItems grupoItemActual;
    private List<T011GruposItems> listaGrupoItem;

    private T001Usuario usuarioActual;
    private String plan50;
    private String estadoItemMasivo;
    private String evaluado;

    private String filtroReferencia;
    private String filtroExt1;
    private String filtroExt2;
    private String filtroEstado;

    private int seguridadMasivo;
    private int tiempoReposicionMasivo;
    private double tasaProduccionDiaMasivo;
    private double tamanoLoteMasivo;


    //PARTE NUEVA
    //******************************************
    //Valida que el noi sea automatico(false) o manual(true)
    private boolean estadoCasillaNoi = false;

    public boolean isEstadoCasillaNoi() {
        return estadoCasillaNoi;
    }

    public void setEstadoCasillaNoi(boolean estadoCasillaNoi) {
        this.estadoCasillaNoi = estadoCasillaNoi;
    }
    //******************************************


    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public String getEvaluado() {
        return evaluado;
    }

    public void setEvaluado(String evaluado) {
        this.evaluado = evaluado;
    }


    public EstadoItemController() {
    }

    public T010EstadoItemFacade getEstadoItemFacade() {
        return estadoItemFacade;
    }


    public void setEstadoItemFacade(T010EstadoItemFacade estadoItemFacade) {
        this.estadoItemFacade = estadoItemFacade;
    }

    public T010EstadoItem getEstadoItemActual() {
        return estadoItemActual;
    }


    public void setEstadoItemActual(T010EstadoItem estadoItemActual) {
        this.estadoItemActual = estadoItemActual;
    }

    public List<T010EstadoItem> getListaEstadoItem() {
        return listaEstadoItem = getEstadoItemFacade().findAll();
    }

    public List<T010EstadoItem> getListaEstadoItemPlan50ComercialEstadoCambio() {
        if (listaEstadoItem == null) {
            return listaEstadoItem = new ArrayList<>();
        } else {
            return listaEstadoItem = getEstadoItemFacade().estadoItemPlan50ComercialEstadoCambio(grupoItemActual.getDescripcion());
        }
    }


    public List<T010EstadoItem> getListaEstadoItemPlan50Comercial() {
        if (listaEstadoItem == null) {
            return listaEstadoItem = new ArrayList<>();
        } else {
            return listaEstadoItem = getEstadoItemFacade().estadoItemPlan50Comercial(grupoItemActual.getDescripcion());
        }
    }


    public List<T010EstadoItem> getListaEstadoItemPlan50ComercialEstadoItem() {
        if (listaEstadoItem == null) {
            return listaEstadoItem = new ArrayList<>();
        } else {
            return listaEstadoItem = getEstadoItemFacade().estadoItemPlan50ComercialEstadoItem(grupoItemActual.getDescripcion(), "Estado 1");
        }
    }


    public List<T010EstadoItem> getListaEstadoItemPlan50Planeacion() {
        if (listaEstadoItem == null) {
            return listaEstadoItem = new ArrayList<>();
        } else {
            return listaEstadoItem = getEstadoItemFacade().estadoItemPlan50Planeacion(grupoItemActual.getDescripcion());
        }
    }


    public List<T010EstadoItem> getListaEstadoItemPlan50PlaneacionEstadoItem() {
        if (listaEstadoItem == null) {
            return listaEstadoItem = new ArrayList<>();
        } else {
            return listaEstadoItem = getEstadoItemFacade().estadoItemPlan50PlaneacionEstadoItem(grupoItemActual.getDescripcion(), "Estado 1");
        }
    }


    public void setListaEstadoItem(List<T010EstadoItem> listaEstadoItem) {
        this.listaEstadoItem = listaEstadoItem;
    }


    public List<T010EstadoItem> getListaEstadoItemPlan50Unoee() {
        if (listaEstadoItem == null) {
            return listaEstadoItem = new ArrayList<>();
        } else {
            return listaEstadoItem = getEstadoItemFacade().estadoItemPlan50Unoee(grupoItemActual.getDescripcion());
        }
    }


    public List<T010EstadoItem> getListaEstadoItemPlan50UnoeeEstado2() {
        if (listaEstadoItem == null) {
            return listaEstadoItem = new ArrayList<>();
        } else {
            return listaEstadoItem = getEstadoItemFacade().estadoItemPlan50UnoeeEstado2(grupoItemActual.getDescripcion());
        }
    }


    public String getPlan50() {
        return plan50;
    }

    public void setPlan50(String plan50) {
        this.plan50 = plan50;
    }

    public String getEstadoItemMasivo() {
        return estadoItemMasivo;
    }

    public void setEstadoItemMasivo(String estadoItemMasivo) {
        this.estadoItemMasivo = estadoItemMasivo;
    }

    public String getFiltroReferencia() {
        return filtroReferencia;
    }

    public void setFiltroReferencia(String filtroReferencia) {
        this.filtroReferencia = filtroReferencia;
    }

    public String getFiltroExt1() {
        return filtroExt1;
    }

    public void setFiltroExt1(String filtroExt1) {
        this.filtroExt1 = filtroExt1;
    }

    public String getFiltroExt2() {
        return filtroExt2;
    }

    public void setFiltroExt2(String filtroExt2) {
        this.filtroExt2 = filtroExt2;
    }

    public String getFiltroEstado() {
        return filtroEstado;
    }

    public void setFiltroEstado(String filtroEstado) {
        this.filtroEstado = filtroEstado;
    }

    public int getSeguridadMasivo() {
        return seguridadMasivo;
    }

    public void setSeguridadMasivo(int seguridadMasivo) {
        this.seguridadMasivo = seguridadMasivo;
    }

    public int getTiempoReposicionMasivo() {
        return tiempoReposicionMasivo;
    }

    public void setTiempoReposicionMasivo(int tiempoReposicionMasivo) {
        this.tiempoReposicionMasivo = tiempoReposicionMasivo;
    }

    public List<T010EstadoItem> getListaEstadoItemFiltro() {
        return listaEstadoItemFiltro;
    }

    public void setListaEstadoItemFiltro(List<T010EstadoItem> listaEstadoItemFiltro) {
        this.listaEstadoItemFiltro = listaEstadoItemFiltro;
    }

    public double getTasaProduccionDiaMasivo() {
        return tasaProduccionDiaMasivo;
    }

    public void setTasaProduccionDiaMasivo(double tasaProduccionDiaMasivo) {
        this.tasaProduccionDiaMasivo = tasaProduccionDiaMasivo;
    }

    public double getTamanoLoteMasivo() {
        return tamanoLoteMasivo;
    }

    public void setTamanoLoteMasivo(double tamanoLoteMasivo) {
        this.tamanoLoteMasivo = tamanoLoteMasivo;
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

    public List<T011GruposItems> getListaGrupoItemComercial() {
        return listaGrupoItem = getGrupoItemFacade().grupoPendienteComercial();
    }

    public List<T011GruposItems> getListaGrupoItemPlaneacion() {
        listaGrupoItem = getGrupoItemFacade().grupoPendientePlaneacion();
        System.out.println(listaGrupoItem.size() + " tamaño listaGrupoItem()");
        return listaGrupoItem;
    }

    public void setListaGrupoItem(List<T011GruposItems> listaGrupoItem) {
        this.listaGrupoItem = listaGrupoItem;
    }

    //Navigation
    public void consultar() {
        //System.out.println(grupoItemActual.getDescripcion() + "Esta es la descripcion del item actual");
        listaEstadoItem = getEstadoItemFacade().estadoItemPlan50Comercial(grupoItemActual.getDescripcion());
        listaEstadoItemFiltro = getEstadoItemFacade().estadoItemPlan50Comercial(grupoItemActual.getDescripcion());
    }


    public void recargarLista() {
        seguridadMasivo = 0;
        tiempoReposicionMasivo = 0;
        tamanoLoteMasivo = 0;
        tasaProduccionDiaMasivo = 0;
        evaluado = null;
        filtroReferencia = "";
        filtroExt1 = "";
        filtroExt2 = "";
        filtroEstado = "";
        grupoItemActual = new T011GruposItems();
        listaEstadoItem = null;
    }

    public void prepareEdit() {
        estadoItemActual = new T010EstadoItem();
    }


    public String prepareCreate() {
        estadoItemActual = new T010EstadoItem();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareListLogUnoEE() {
        recargarLista();
        return "/administrador/modGestorItems/unoee/lista";
    }

    public String prepareListLogUnoEEEstado2() {
        recargarLista();
        return "/administrador/modGestorItems/unoee/listaEstado2";
    }

    public String prepareListComercial() {
        recargarLista();
        return "/administrador/modGestorItems/comercial/lista";
    }

    public String prepareListPlaneacion() {
        recargarLista();
        return "/administrador/modGestorItems/planeacion/lista";
    }


    public String add() {
        try {
            getEstadoItemFacade().create(estadoItemActual);
            addSuccessMessage("Tipo de archivo creado correctamente", "Tipo de archivo creado correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }


    //COMERCIAL
    public String prepareleListItemsEstadoCambio() {
        recargarLista();
        return "listaItemsEstadoModificado";
    }


    public String updateComercial() {
        try {
            if (getEvaluado().equals("NO EVALUADO")) {
                estadoItemActual.setEvaluado((short) 0);
            } else if (getEvaluado().equals("PENDIENTE")) {
                estadoItemActual.setEvaluado((short) 1);
            } else {
                estadoItemActual.setEvaluado((short) 2);
            }
            evaluado = null;
            estadoItemActual.setUsuarioActualizacion(usuarioActual.getNombre());
            estadoItemActual.setFechaActualizacion(new Date());
            estadoItemActual.setEstadoItemCambio((short) 1);
            getEstadoItemFacade().edit(estadoItemActual);
            addSuccessMessage("Item actualizado", "Item actualizado");
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
        return null;
    }

    //clic en guardar masivo
    public String updateMasivoComercial() {
        try {

            for (T010EstadoItem item : getListaEstadoItemPlan50Comercial()) {
                if (filtroReferencia.equals(item.getReferencia())
                        && filtroExt1.equals(item.getExtension1())
                        && filtroEstado.equals(item.getEstadoItem())) {
                    item.setEstadoItem(estadoItemMasivo);
                    if (estadoItemMasivo.equals("ESTADO 1")) {
                        item.setEvaluado((short) 2);
                    } else {
                        item.setEvaluado((short) 1);
                    }
                    item.setEstadoItemCambio((short) 1);
                    item.setUsuarioActualizacion(usuarioActual.getNombre());
                    item.setFechaActualizacion(new Date());
                    getEstadoItemFacade().edit(item);
                } else if
                (filtroReferencia.equals("")
                                && filtroExt1.equals(item.getExtension1())
                                && filtroEstado.equals(item.getEstadoItem())) {
                    item.setEstadoItem(estadoItemMasivo);
                    if (estadoItemMasivo.equals("ESTADO 1")) {
                        item.setEvaluado((short) 2);
                    } else {
                        item.setEvaluado((short) 1);
                    }
                    item.setEstadoItemCambio((short) 1);
                    item.setUsuarioActualizacion(usuarioActual.getNombre());
                    item.setFechaActualizacion(new Date());
                    getEstadoItemFacade().edit(item);
                } else if
                (filtroReferencia.equals("")
                                && filtroExt1.equals("")
                                && filtroEstado.equals(item.getEstadoItem())) {
                    item.setEstadoItem(estadoItemMasivo);
                    if (estadoItemMasivo.equals("ESTADO 1")) {
                        item.setEvaluado((short) 2);
                    } else {
                        item.setEvaluado((short) 1);
                    }
                    item.setEstadoItemCambio((short) 1);
                    item.setUsuarioActualizacion(usuarioActual.getNombre());
                    item.setFechaActualizacion(new Date());
                    getEstadoItemFacade().edit(item);
                } else if
                (filtroReferencia.equals("")
                                && filtroExt1.equals("")
                                && filtroEstado.equals(" ")) {
                    item.setEstadoItem(estadoItemMasivo);
                    if (estadoItemMasivo.equals("ESTADO 1")) {
                        item.setEvaluado((short) 2);
                    } else {
                        item.setEvaluado((short) 1);
                    }
                    item.setEstadoItemCambio((short) 1);
                    item.setUsuarioActualizacion(usuarioActual.getNombre());
                    item.setFechaActualizacion(new Date());
                    getEstadoItemFacade().edit(item);
                } else if
                (filtroReferencia.equals(item.getReferencia())
                                && filtroExt1.equals("")
                                && filtroEstado.equals(item.getEstadoItem())) {
                    item.setEstadoItem(estadoItemMasivo);
                    if (estadoItemMasivo.equals("ESTADO 1")) {
                        item.setEvaluado((short) 2);
                    } else {
                        item.setEvaluado((short) 1);
                    }
                    item.setEstadoItemCambio((short) 1);
                    item.setUsuarioActualizacion(usuarioActual.getNombre());
                    item.setFechaActualizacion(new Date());
                    getEstadoItemFacade().edit(item);
                } else if
                (filtroReferencia.equals("")
                                && filtroExt1.equals(item.getExtension1())
                                && filtroEstado.equals(" ")) {
                    item.setEstadoItem(estadoItemMasivo);
                    if (estadoItemMasivo.equals("ESTADO 1")) {
                        item.setEvaluado((short) 2);
                    } else {
                        item.setEvaluado((short) 1);
                    }
                    item.setEstadoItemCambio((short) 1);
                    item.setUsuarioActualizacion(usuarioActual.getNombre());
                    item.setFechaActualizacion(new Date());
                    getEstadoItemFacade().edit(item);
                } else if
                (filtroReferencia.equals(item.getReferencia())
                                && filtroExt1.equals("")
                                && filtroEstado.equals(" ")) {
                    item.setEstadoItem(estadoItemMasivo);
                    if (estadoItemMasivo.equals("ESTADO 1")) {
                        item.setEvaluado((short) 2);
                    } else {
                        item.setEvaluado((short) 1);
                    }
                    item.setEstadoItemCambio((short) 1);
                    item.setUsuarioActualizacion(usuarioActual.getNombre());
                    item.setFechaActualizacion(new Date());
                    getEstadoItemFacade().edit(item);
                } else if
                (filtroReferencia.equals(item.getReferencia())
                                && filtroExt1.equals(item.getExtension1())
                                && filtroEstado.equals(" ")) {
                    item.setEstadoItem(estadoItemMasivo);
                    if (estadoItemMasivo.equals("ESTADO 1")) {
                        item.setEvaluado((short) 2);
                    } else {
                        item.setEvaluado((short) 1);
                    }
                    item.setEstadoItemCambio((short) 1);
                    item.setUsuarioActualizacion(usuarioActual.getNombre());
                    item.setFechaActualizacion(new Date());
                    getEstadoItemFacade().edit(item);
                }
            }
            addSuccessMessage("Items actualizados", "Items actualizados");
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
        return "lista";
    }


    public String updateTerminarEvaluacionComercial() {
        int contador = 0;
        try {

            for (T010EstadoItem item : getListaEstadoItemPlan50Comercial()) {
                if (item.getEstadoItem().equals("ESTADO 1")) {
                    item.setEstado((short) 2);
                    item.setEvaluado((short) 0);
                    item.setUsuarioActualizacion(usuarioActual.getNombre());
                    item.setFechaActualizacion(new Date());
                    getEstadoItemFacade().edit(item);
                    contador++;
                } else {
                    item.setEstado((short) 0);
                    item.setUsuarioActualizacion(usuarioActual.getNombre());
                    item.setFechaActualizacion(new Date());
                    getEstadoItemFacade().edit(item);
                }
            }

            grupoItemActual.setGrupoPendiente((short) 2);
            grupoItemFacade.edit(grupoItemActual);

        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evaluación terminada", "Se enviaron " + contador + " items");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return "lista";
    }


    public String eliminarEvaluacionComercial() {
        try {

            for (T010EstadoItem item : getListaEstadoItemPlan50Comercial()) {
                item.setEstado((short) 0);
                item.setUsuarioActualizacion(usuarioActual.getNombre());
                item.setFechaActualizacion(new Date());
                getEstadoItemFacade().edit(item);
            }
            addSuccessMessage("Items actualizados", "Items actualizados");
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
        return null;
    }

    //PLANEACION
    public String updatePlaneacion() {
        try {
            double porcentaje = estadoItemActual.getSeguridad() / 100.0;
            if (getEvaluado().equals("NO EVALUADO")) {
                estadoItemActual.setEvaluado((short) 0);
            } else if (getEvaluado().equals("PENDIENTE")) {
                estadoItemActual.setEvaluado((short) 1);
            } else {
                estadoItemActual.setEvaluado((short) 2);
            }
            estadoItemActual.setUsuarioActualizacion(usuarioActual.getNombre());
            estadoItemActual.setFechaActualizacion(new Date());

            //PARTE NUEVA
            //Si el noi es falso se hace automatico el calculo del NOI
            if (!estadoCasillaNoi) {
                estadoItemActual.setNoi(((estadoItemActual.getMaxCantidadComprada() * estadoItemActual.getTiempoReposicion()) * (porcentaje)) + (estadoItemActual.getMaxCantidadComprada() * estadoItemActual.getTiempoReposicion()));
            }

            //estadoItemActual.setNoi(((estadoItemActual.getMaxCantidadComprada() * estadoItemActual.getTiempoReposicion()) * (porcentaje)) + (estadoItemActual.getMaxCantidadComprada() * estadoItemActual.getTiempoReposicion()));

            getEstadoItemFacade().edit(estadoItemActual);
            addSuccessMessage("Item actualizado", "Item actualizado");
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
        return null;
    }


    public String updateMasivoPlaneacion() {
        try {
            for (T010EstadoItem item : getListaEstadoItemPlan50Planeacion()) {
                if (filtroReferencia.equals(item.getReferencia())
                        && filtroExt2.equals(item.getExtension2())) {
                    item.setSeguridad(seguridadMasivo);
                    item.setTiempoReposicion(tiempoReposicionMasivo);
                    item.setTamanoLote(tamanoLoteMasivo);
                    item.setTasaProduccionDia(tasaProduccionDiaMasivo);
                    item.setEvaluado((short) 2);
                    item.setUsuarioActualizacion(usuarioActual.getNombre());
                    item.setFechaActualizacion(new Date());
                    double porcentaje = item.getSeguridad() / 100.0;
                    item.setNoi(((item.getMaxCantidadComprada() * item.getTiempoReposicion()) * (porcentaje)) + (item.getMaxCantidadComprada() * item.getTiempoReposicion()));
                    getEstadoItemFacade().edit(item);
                } else if
                (filtroReferencia.equals("")
                                && filtroExt2.equals(item.getExtension2())) {
                    item.setSeguridad(seguridadMasivo);
                    item.setTiempoReposicion(tiempoReposicionMasivo);
                    item.setTamanoLote(tamanoLoteMasivo);
                    item.setTasaProduccionDia(tasaProduccionDiaMasivo);
                    item.setEvaluado((short) 2);
                    item.setUsuarioActualizacion(usuarioActual.getNombre());
                    item.setFechaActualizacion(new Date());
                    double porcentaje = item.getSeguridad() / 100.0;
                    item.setNoi(((item.getMaxCantidadComprada() * item.getTiempoReposicion()) * (porcentaje)) + (item.getMaxCantidadComprada() * item.getTiempoReposicion()));
                    getEstadoItemFacade().edit(item);
                } else if
                (filtroReferencia.equals("")
                                && filtroExt2.equals("")) {
                    item.setSeguridad(seguridadMasivo);
                    item.setTiempoReposicion(tiempoReposicionMasivo);
                    item.setTamanoLote(tamanoLoteMasivo);
                    item.setTasaProduccionDia(tasaProduccionDiaMasivo);
                    item.setEvaluado((short) 2);
                    item.setUsuarioActualizacion(usuarioActual.getNombre());
                    item.setFechaActualizacion(new Date());
                    double porcentaje = item.getSeguridad() / 100.0;
                    item.setNoi((((item.getMaxCantidadComprada() * item.getTiempoReposicion()) * (porcentaje)) + (item.getMaxCantidadComprada() * item.getTiempoReposicion())));
                    getEstadoItemFacade().edit(item);
                } else if
                (filtroReferencia.equals(item.getReferencia())
                                && filtroExt2.equals("")) {
                    item.setSeguridad(seguridadMasivo);
                    item.setTiempoReposicion(tiempoReposicionMasivo);
                    item.setTamanoLote(tamanoLoteMasivo);
                    item.setTasaProduccionDia(tasaProduccionDiaMasivo);
                    item.setEvaluado((short) 2);
                    item.setUsuarioActualizacion(usuarioActual.getNombre());
                    item.setFechaActualizacion(new Date());
                    double porcentaje = item.getSeguridad() / 100.0;
                    item.setNoi(((item.getMaxCantidadComprada() * item.getTiempoReposicion()) * (porcentaje)) + (item.getMaxCantidadComprada() * item.getTiempoReposicion()));
                    getEstadoItemFacade().edit(item);
                }
            }
            addSuccessMessage("Items actualizados", "Items actualizados");
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
        return "lista";
    }


    //Metodo que se activa cuando se da clic en terminar evaluacion
    public String updateTerminarEvaluacionPlaneacion() {
        try {
            int cont = 0;

            //lista que se carga de acuerdo a la descripcion del  grupo item seleccionado previamente
            //donde a traves de consultar() se obtiene la listaEstadoItem con el plan50 y el estado1, donde si esta vacia
            //se retorna una lista vacia, sino esta vacia hace la consulta para itemplaneacion50 con estado2 y descripcion
            for (T010EstadoItem item : getListaEstadoItemPlan50Planeacion()) {
                if (item.getNoi() == null) {
                    cont++;
                    break;
                }
            }

            //Si se obtuvo algun NOI vacio,
            if (cont > 0) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Datos no enviados", "no se enviaron datos debido a que la evaluación no ha terminado");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return "lista";
            }


            // Si no se obtienen NOIS vacios
            else {

                //Llama al procedimiento envia la descripcion del grupoItem y el usuario que actualmente esta conectado
                getEstadoItemFacade().estadoItemSp(grupoItemActual.getDescripcion(), usuarioActual.getUsername());
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evaluación terminada", "Se enviaron  items");
                RequestContext.getCurrentInstance().showMessageInDialog(message);

                //Cambia el estado del grupo pendiente y lo convierte en cero
                grupoItemActual.setGrupoPendiente((short) 0);
                grupoItemFacade.edit(grupoItemActual);
                return "/administrador/modGestorItems/unoee/lista";
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return "lista";
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
}
