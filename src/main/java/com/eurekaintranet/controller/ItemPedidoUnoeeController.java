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

/**
 *
 * @author Lccabalr
 */
@Named(value = "itemPedidoUnoeeController")
@SessionScoped
public class ItemPedidoUnoeeController implements Serializable {

    private String nrodocto;
    private String estado;
    private int item;
    private String ext1;
    private String ext2;
    private String unidadMedida;
    private BigDecimal cantPedida;
    private BigDecimal cantPendiente;
    private BigDecimal cantComprometida;
    public ItemPedidoUnoeeController() {
    }

    public String getNrodocto() {
        return nrodocto;
    }

    public void setNrodocto(String nrodocto) {
        this.nrodocto = nrodocto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
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
    
    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getCantPedida() {
        return cantPedida;
    }

    public void setCantPedida(BigDecimal cantPedida) {
        this.cantPedida = cantPedida;
    }

    public BigDecimal getCantPendiente() {
        return cantPendiente;
    }

    public void setCantPendiente(BigDecimal cantPendiente) {
        this.cantPendiente = cantPendiente;
    }

    public BigDecimal getCantComprometida() {
        return cantComprometida;
    }

    public void setCantComprometida(BigDecimal cantComprometida) {
        this.cantComprometida = cantComprometida;
    }
    @Override
    public String toString() {
        return item+" - "+ext1+" - "+ext2;
    }
   
}
