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

/**
 *
 * @author Lccabalr
 */
@Named(value = "detalleProgramadorGanttController")
@SessionScoped
public class DetalleProgramadorGanttController implements Serializable {
    
    private String rutaOperacion;
    private Date fechaInicio;
    private Date fechaTerminacion;
    private String tipoDocumento;
    private int numeroDocumento;
    private String referencia;
    private String descItem;
    private String idExt1;
    private String descExt1;
    private String idExt2;
    private String descExt2;
    private String und;
    private BigDecimal cantPlaneada;
    private BigDecimal cantPendiente;
    private String color;
    
    public DetalleProgramadorGanttController() {
    }

    public String getRutaOperacion() {
        return rutaOperacion;
    }

    public void setRutaOperacion(String rutaOperacion) {
        this.rutaOperacion = rutaOperacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTerminacion() {
        return fechaTerminacion;
    }

    public void setFechaTerminacion(Date fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDescItem() {
        return descItem;
    }

    public void setDescItem(String descItem) {
        this.descItem = descItem;
    }

    

    public String getIdExt1() {
        return idExt1;
    }

    public void setIdExt1(String idExt1) {
        this.idExt1 = idExt1;
    }

    public String getDescExt1() {
        return descExt1;
    }

    public void setDescExt1(String descExt1) {
        this.descExt1 = descExt1;
    }

    public String getIdExt2() {
        return idExt2;
    }

    public void setIdExt2(String idExt2) {
        this.idExt2 = idExt2;
    }

    public String getDescExt2() {
        return descExt2;
    }

    public void setDescExt2(String descExt2) {
        this.descExt2 = descExt2;
    }

    public String getUnd() {
        return und;
    }

    public void setUnd(String und) {
        this.und = und;
    }

    public BigDecimal getCantPlaneada() {
        return cantPlaneada;
    }

    public void setCantPlaneada(BigDecimal cantPlaneada) {
        this.cantPlaneada = cantPlaneada;
    }

    public BigDecimal getCantPendiente() {
        return cantPendiente;
    }

    public void setCantPendiente(BigDecimal cantPendiente) {
        this.cantPendiente = cantPendiente;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
}
