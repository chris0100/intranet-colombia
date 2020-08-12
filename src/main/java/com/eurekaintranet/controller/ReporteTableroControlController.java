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
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lccabalr
 */
@Named(value = "reporteTableroControlController")
@SessionScoped
public class ReporteTableroControlController implements Serializable {

    private String f_item;
    private String f_desc_item;
    private String f_desc_ext_detalle_1_normal;
    private String f_desc_ext_detalle_2;
    private BigDecimal f_cant_existencia_1;
    private BigDecimal f_costo_prom_uni;
    private BigDecimal f_costo_prom_tot;
    private BigDecimal f_existencia_dias;
    private BigDecimal f_cons_prom_diario;
    private BigDecimal f_nivel_minimo;
    private BigDecimal f_nivel_maximo;
    private BigDecimal f_nivel_pedido;
    private String f_bodega;
    private int f_rowid_item_ext;  
    private List<ReporteTableroControlController> listaReporte;
    private ReporteTableroControlController reporteActual;
    private String color;
    private String f_tipo_inv;
    
    public ReporteTableroControlController() {
    }

    public String getF_item() {
        return f_item;
    }

    public void setF_item(String f_item) {
        this.f_item = f_item;
    }

    public String getF_desc_item() {
        return f_desc_item;
    }

    public void setF_desc_item(String f_desc_item) {
        this.f_desc_item = f_desc_item;
    }

    public String getF_desc_ext_detalle_1_normal() {
        return f_desc_ext_detalle_1_normal;
    }

    public void setF_desc_ext_detalle_1_normal(String f_desc_ext_detalle_1_normal) {
        this.f_desc_ext_detalle_1_normal = f_desc_ext_detalle_1_normal;
    }

    public String getF_desc_ext_detalle_2() {
        return f_desc_ext_detalle_2;
    }

    public void setF_desc_ext_detalle_2(String f_desc_ext_detalle_2) {
        this.f_desc_ext_detalle_2 = f_desc_ext_detalle_2;
    }

    public BigDecimal getF_cant_existencia_1() {
        return f_cant_existencia_1;
    }

    public void setF_cant_existencia_1(BigDecimal f_cant_existencia_1) {
        this.f_cant_existencia_1 = f_cant_existencia_1;
    }

    public BigDecimal getF_costo_prom_uni() {
        return f_costo_prom_uni;
    }

    public void setF_costo_prom_uni(BigDecimal f_costo_prom_uni) {
        this.f_costo_prom_uni = f_costo_prom_uni;
    }

    public BigDecimal getF_costo_prom_tot() {
        return f_costo_prom_tot;
    }

    public void setF_costo_prom_tot(BigDecimal f_costo_prom_tot) {
        this.f_costo_prom_tot = f_costo_prom_tot;
    }

    public BigDecimal getF_existencia_dias() {
        return f_existencia_dias;
    }

    public void setF_existencia_dias(BigDecimal f_existencia_dias) {
        this.f_existencia_dias = f_existencia_dias;
    }

    public BigDecimal getF_cons_prom_diario() {
        return f_cons_prom_diario;
    }

    public void setF_cons_prom_diario(BigDecimal f_cons_prom_diario) {
        this.f_cons_prom_diario = f_cons_prom_diario;
    }

    public BigDecimal getF_nivel_minimo() {
        return f_nivel_minimo;
    }

    public void setF_nivel_minimo(BigDecimal f_nivel_minimo) {
        this.f_nivel_minimo = f_nivel_minimo;
    }

    public BigDecimal getF_nivel_maximo() {
        return f_nivel_maximo;
    }

    public void setF_nivel_maximo(BigDecimal f_nivel_maximo) {
        this.f_nivel_maximo = f_nivel_maximo;
    }

    public BigDecimal getF_nivel_pedido() {
        return f_nivel_pedido;
    }

    public void setF_nivel_pedido(BigDecimal f_nivel_pedido) {
        this.f_nivel_pedido = f_nivel_pedido;
    }

    public String getF_bodega() {
        return f_bodega;
    }

    public void setF_bodega(String f_bodega) {
        this.f_bodega = f_bodega;
    }

    public int getF_rowid_item_ext() {
        return f_rowid_item_ext;
    }

    public void setF_rowid_item_ext(int f_rowid_item_ext) {
        this.f_rowid_item_ext = f_rowid_item_ext;
    }

    public String getF_tipo_inv() {
        return f_tipo_inv;
    }

    public void setF_tipo_inv(String f_tipo_inv) {
        this.f_tipo_inv = f_tipo_inv;
    }

       
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    
    public List<ReporteTableroControlController> getListaReporte() {
        return listaReporte;
    }

    public void setListaReporte(List<ReporteTableroControlController> listaReporte) {
        this.listaReporte = listaReporte;
    }

    public ReporteTableroControlController getReporteActual() {
        return reporteActual;
    }

    public void setReporteActual(ReporteTableroControlController reporteActual) {
        this.reporteActual = reporteActual;
    }
    public void agregarBodegaConsultarReporteTableroControl(String inventario, int idBodega) throws SQLException{
        String connectionUrl ="jdbc:sqlserver://kosconnect.siesacloud.com:20446;databaseName=UnoEE_KosColombia_Real;user=kos;password=Kos$12$%";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "exec sp_cons_inv_exitencias 1,'"+inventario+"','   ','   ',"+idBodega+",0,0,1,NULL,1,0,NULL,10017,'REPORTE_TABLEROCONTROKOS_EUREKA',4300,'COP',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL";
        
       
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                reporteActual = new ReporteTableroControlController();
                reporteActual.setF_desc_ext_detalle_1_normal(rs.getString("f_desc_ext_detalle_1_normal"));
                
                reporteActual.setF_item(rs.getString("f_item"));
                reporteActual.setF_desc_item(rs.getString("f_desc_item"));
                reporteActual.setF_desc_ext_detalle_2(rs.getString("f_desc_ext_detalle_2"));
                reporteActual.setF_cant_existencia_1(rs.getBigDecimal("f_cant_existencia_1"));
                reporteActual.setF_costo_prom_uni(rs.getBigDecimal("f_costo_prom_uni"));
                reporteActual.setF_costo_prom_tot(rs.getBigDecimal("f_costo_prom_tot"));
                reporteActual.setF_existencia_dias(rs.getBigDecimal("f_existencia_dias"));
                reporteActual.setF_cons_prom_diario(rs.getBigDecimal("f_cons_prom_diario").multiply(new BigDecimal(30)));
                reporteActual.setF_nivel_minimo(rs.getBigDecimal("f_nivel_minimo"));
                reporteActual.setF_nivel_maximo(rs.getBigDecimal("f_nivel_maximo"));
                reporteActual.setF_nivel_pedido(rs.getBigDecimal("f_nivel_pedido"));
                reporteActual.setF_bodega(rs.getString("f_bodega"));
                reporteActual.setF_rowid_item_ext(rs.getInt("f_rowid_item_ext"));
                if(reporteActual.getF_cant_existencia_1().compareTo(reporteActual.getF_nivel_minimo()) < 0){
                    reporteActual.setColor("ROJO");
                }else if (reporteActual.getF_cant_existencia_1().compareTo(reporteActual.getF_nivel_minimo()) > 0
                        && reporteActual.getF_cant_existencia_1().compareTo(reporteActual.getF_nivel_maximo()) < 0){
                    reporteActual.setColor("NARANJA");
                }else if (reporteActual.getF_cant_existencia_1().compareTo(reporteActual.getF_nivel_maximo()) > 0){
                    reporteActual.setColor("NEGRO");
                }
                
                if((reporteActual.getF_desc_ext_detalle_1_normal().contains("BLANCO") && inventario.equals("IN1430K.ex")) || 
                (reporteActual.getF_desc_ext_detalle_1_normal().contains("GENERICO") && inventario.equals("IN1430K.ex"))){
                    listaReporte.add(reporteActual);
                }else if (!inventario.equals("IN1430K.ex")){
                        listaReporte.add(reporteActual);
                }  
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
       
    public void consultarReporteTableroControl(String inventario, int idBodega) throws SQLException{
        String connectionUrl ="jdbc:sqlserver://kosconnect.siesacloud.com:20446;databaseName=UnoEE_KosColombia_Real;user=kos;password=Kos$12$%";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "exec sp_cons_inv_exitencias 1,'"+inventario+"','   ','   ',"+idBodega+",0,0,1,NULL,1,0,NULL,10017,'REPORTE_TABLEROCONTROKOS_EUREKA',4300,'COP',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL";
        
        listaReporte = new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                reporteActual = new ReporteTableroControlController();
                reporteActual.setF_desc_ext_detalle_1_normal(rs.getString("f_desc_ext_detalle_1_normal"));
                reporteActual.setF_item(rs.getString("f_item"));
                reporteActual.setF_desc_item(rs.getString("f_desc_item"));
                reporteActual.setF_desc_ext_detalle_2(rs.getString("f_desc_ext_detalle_2"));
                reporteActual.setF_cant_existencia_1(rs.getBigDecimal("f_cant_existencia_1"));
                reporteActual.setF_costo_prom_uni(rs.getBigDecimal("f_costo_prom_uni"));
                reporteActual.setF_costo_prom_tot(rs.getBigDecimal("f_costo_prom_tot"));
                reporteActual.setF_existencia_dias(rs.getBigDecimal("f_existencia_dias"));
                reporteActual.setF_cons_prom_diario(rs.getBigDecimal("f_cons_prom_diario").multiply(new BigDecimal(30)));
                reporteActual.setF_nivel_minimo(rs.getBigDecimal("f_nivel_minimo"));
                reporteActual.setF_nivel_maximo(rs.getBigDecimal("f_nivel_maximo"));
                reporteActual.setF_nivel_pedido(rs.getBigDecimal("f_nivel_pedido"));
                reporteActual.setF_bodega(rs.getString("f_bodega"));
                reporteActual.setF_rowid_item_ext(rs.getInt("f_rowid_item_ext"));
                if(reporteActual.getF_cant_existencia_1().compareTo(reporteActual.getF_nivel_minimo()) < 0){
                    reporteActual.setColor("ROJO");
                }else if (reporteActual.getF_cant_existencia_1().compareTo(reporteActual.getF_nivel_minimo()) > 0
                        && reporteActual.getF_cant_existencia_1().compareTo(reporteActual.getF_nivel_maximo()) < 0){
                    reporteActual.setColor("NARANJA");
                }else if (reporteActual.getF_cant_existencia_1().compareTo(reporteActual.getF_nivel_maximo()) > 0){
                    reporteActual.setColor("NEGRO");
                }
                
                if((reporteActual.getF_desc_ext_detalle_1_normal().contains("BLANCO") && inventario.equals("IN1430K.ex")) || 
                    (reporteActual.getF_desc_ext_detalle_1_normal().contains("GENERICO") && inventario.equals("IN1430K.ex"))){
                        listaReporte.add(reporteActual);
                }else if (!inventario.equals("IN1430K.ex")){
                        listaReporte.add(reporteActual);
                }                
            }
            if(inventario.equals("IN1460K")){
                agregarBodegaConsultarReporteTableroControl(inventario, 14);
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    
    
    //Reporte de programacion kos
  
    public List<ReporteTableroControlController> getListaR1() throws SQLException {
        consultarReporteTableroControl("IN1430K.ex", 2);
        return listaReporte;
    }
    public List<ReporteTableroControlController> getListaR2() throws SQLException {
        consultarReporteTableroControl("IN1405K", 1);
        return listaReporte;
    }
     public List<ReporteTableroControlController> getListaR3() throws SQLException {
        consultarReporteTableroControl("IN1460K", 3);
        return listaReporte;
    }
}
