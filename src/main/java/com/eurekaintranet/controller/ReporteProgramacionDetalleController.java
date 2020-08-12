/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lccabalr
 */
@Named(value = "reporteProgramacionDetalleController")
@SessionScoped
public class ReporteProgramacionDetalleController implements Serializable {

    //detalle
    private String nroDocumentoDetalle;
    private String referencia1Detalle;
    private Date fechaTerminacionDetalle;
    private BigDecimal cantPlaneadaDetalle;
    
    private List<ReporteProgramacionController> listaReporte;
    private List<ReporteProgramacionController> listaReporteDetalle;
    public ReporteProgramacionDetalleController() {
    }

    public String getNroDocumentoDetalle() {
        return nroDocumentoDetalle;
    }

    public void setNroDocumentoDetalle(String nroDocumentoDetalle) {
        this.nroDocumentoDetalle = nroDocumentoDetalle;
    }

    public String getReferencia1Detalle() {
        return referencia1Detalle;
    }

    public void setReferencia1Detalle(String referencia1Detalle) {
        this.referencia1Detalle = referencia1Detalle;
    }

    public Date getFechaTerminacionDetalle() {
        return fechaTerminacionDetalle;
    }

    public void setFechaTerminacionDetalle(Date fechaTerminacionDetalle) {
        this.fechaTerminacionDetalle = fechaTerminacionDetalle;
    }

    public BigDecimal getCantPlaneadaDetalle() {
        return cantPlaneadaDetalle;
    }

    public void setCantPlaneadaDetalle(BigDecimal cantPlaneadaDetalle) {
        this.cantPlaneadaDetalle = cantPlaneadaDetalle;
    }

    public List<ReporteProgramacionController> getListaReporte() {
        return listaReporte;
    }

    public void setListaReporte(List<ReporteProgramacionController> listaReporte) {
        this.listaReporte = listaReporte;
    }

    public List<ReporteProgramacionController> getListaReporteDetalle() {
        return listaReporteDetalle;
    }

    public void setListaReporteDetalle(List<ReporteProgramacionController> listaReporteDetalle) {
        this.listaReporteDetalle = listaReporteDetalle;
    }
    
}
