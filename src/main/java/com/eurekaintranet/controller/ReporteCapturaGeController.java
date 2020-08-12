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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Named(value = "reporteCapturaGeController")
@SessionScoped
public class ReporteCapturaGeController implements Serializable {

    private int idCaptura ;
    private Date fechaCaptura ;
    private int idTurno ;
    private String operario ;
    private String nombreHomologado ;
    private String descEquipo ;
    private String descProceso ;
    private Date horaInicio ;
    private Date horaFinal ;
    private String op ;
    private String idItem ;
    private String descripcionItem ;
    private String ext1Detalle ;
    private String ext2Detalle ;
    private String ruta ;
    private String centroTrabajo ;
    private int puntasNominal ;
    private int puntasUnoee ;
    private int puntasProduccion ;
    private int pp ;
    private BigDecimal mtrsProducidos ;
    private BigDecimal peso ;
    private BigDecimal factorConversion ;
    private BigDecimal claseb ;
    private BigDecimal productoRechazado ;
    private Date horaInicioParada ;
    private Date horaFinParada ;
    private BigDecimal tiempoDiferencia ;
    private String codigoParada ;
    private String descParada ;
    private String letra ;
    private String descEsquema ;
    private String descUnidadNegocio ;
    private String descPlanta ;
    private String descLineaProduccion ;
    private BigDecimal cantProgramada ;
    private BigDecimal cantEntregada ;

    private Date fecha1;
    private Date fecha2;
    
    private List<ReporteCapturaGeController> listaReporte;
    private ReporteCapturaGeController reporteActual;
    
    public ReporteCapturaGeController() {
    }

    public int getIdCaptura() {
        return idCaptura;
    }

    public void setIdCaptura(int idCaptura) {
        this.idCaptura = idCaptura;
    }

    public Date getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public String getOperario() {
        return operario;
    }

    public void setOperario(String operario) {
        this.operario = operario;
    }

    public String getNombreHomologado() {
        return nombreHomologado;
    }

    public void setNombreHomologado(String nombreHomologado) {
        this.nombreHomologado = nombreHomologado;
    }

    public String getDescEquipo() {
        return descEquipo;
    }

    public void setDescEquipo(String descEquipo) {
        this.descEquipo = descEquipo;
    }

    public String getDescProceso() {
        return descProceso;
    }

    public void setDescProceso(String descProceso) {
        this.descProceso = descProceso;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getDescripcionItem() {
        return descripcionItem;
    }

    public void setDescripcionItem(String descripcionItem) {
        this.descripcionItem = descripcionItem;
    }

    public String getExt1Detalle() {
        return ext1Detalle;
    }

    public void setExt1Detalle(String ext1Detalle) {
        this.ext1Detalle = ext1Detalle;
    }

    public String getExt2Detalle() {
        return ext2Detalle;
    }

    public void setExt2Detalle(String ext2Detalle) {
        this.ext2Detalle = ext2Detalle;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getCentroTrabajo() {
        return centroTrabajo;
    }

    public void setCentroTrabajo(String centroTrabajo) {
        this.centroTrabajo = centroTrabajo;
    }

    public int getPuntasNominal() {
        return puntasNominal;
    }

    public void setPuntasNominal(int puntasNominal) {
        this.puntasNominal = puntasNominal;
    }

    public int getPuntasUnoee() {
        return puntasUnoee;
    }

    public void setPuntasUnoee(int puntasUnoee) {
        this.puntasUnoee = puntasUnoee;
    }

    public int getPuntasProduccion() {
        return puntasProduccion;
    }

    public void setPuntasProduccion(int puntasProduccion) {
        this.puntasProduccion = puntasProduccion;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public BigDecimal getMtrsProducidos() {
        return mtrsProducidos;
    }

    public void setMtrsProducidos(BigDecimal mtrsProducidos) {
        this.mtrsProducidos = mtrsProducidos;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getFactorConversion() {
        return factorConversion;
    }

    public void setFactorConversion(BigDecimal factorConversion) {
        this.factorConversion = factorConversion;
    }

    public BigDecimal getClaseb() {
        return claseb;
    }

    public void setClaseb(BigDecimal claseb) {
        this.claseb = claseb;
    }

    public BigDecimal getProductoRechazado() {
        return productoRechazado;
    }

    public void setProductoRechazado(BigDecimal productoRechazado) {
        this.productoRechazado = productoRechazado;
    }

    public Date getHoraInicioParada() {
        return horaInicioParada;
    }

    public void setHoraInicioParada(Date horaInicioParada) {
        this.horaInicioParada = horaInicioParada;
    }

    public Date getHoraFinParada() {
        return horaFinParada;
    }

    public void setHoraFinParada(Date horaFinParada) {
        this.horaFinParada = horaFinParada;
    }

    public BigDecimal getTiempoDiferencia() {
        return tiempoDiferencia;
    }

    public void setTiempoDiferencia(BigDecimal tiempoDiferencia) {
        this.tiempoDiferencia = tiempoDiferencia;
    }

    public String getCodigoParada() {
        return codigoParada;
    }

    public void setCodigoParada(String codigoParada) {
        this.codigoParada = codigoParada;
    }

    public String getDescParada() {
        return descParada;
    }

    public void setDescParada(String descParada) {
        this.descParada = descParada;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getDescEsquema() {
        return descEsquema;
    }

    public void setDescEsquema(String descEsquema) {
        this.descEsquema = descEsquema;
    }

    public String getDescUnidadNegocio() {
        return descUnidadNegocio;
    }

    public void setDescUnidadNegocio(String descUnidadNegocio) {
        this.descUnidadNegocio = descUnidadNegocio;
    }

    public String getDescPlanta() {
        return descPlanta;
    }

    public void setDescPlanta(String descPlanta) {
        this.descPlanta = descPlanta;
    }

    public String getDescLineaProduccion() {
        return descLineaProduccion;
    }

    public void setDescLineaProduccion(String descLineaProduccion) {
        this.descLineaProduccion = descLineaProduccion;
    }

    public BigDecimal getCantProgramada() {
        return cantProgramada;
    }

    public void setCantProgramada(BigDecimal cantProgramada) {
        this.cantProgramada = cantProgramada;
    }

    public BigDecimal getCantEntregada() {
        return cantEntregada;
    }

    public void setCantEntregada(BigDecimal cantEntregada) {
        this.cantEntregada = cantEntregada;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }

    public List<ReporteCapturaGeController> getListaReporte() {
        return listaReporte;
    }

    public void setListaReporte(List<ReporteCapturaGeController> listaReporte) {
        this.listaReporte = listaReporte;
    }

    public ReporteCapturaGeController getReporteActual() {
        return reporteActual;
    }

    public void setReporteActual(ReporteCapturaGeController reporteActual) {
        this.reporteActual = reporteActual;
    }
    
    
    public String prepareReporte() throws SQLException{
        return "listaDetalle";
    }
    public void consultarReporteGe()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");  
        String fecha1Convert = dateFormat1.format(getFecha1()); 
        DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");  
        String fecha2Convert = dateFormat2.format(getFecha2()); 
        String query = "exec [sp_obtener_captura_parada_ge] '"+fecha1Convert+"','"+fecha2Convert+"'";
        listaReporte = new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                reporteActual = new ReporteCapturaGeController();
                reporteActual.setIdCaptura(rs.getInt("id_captura"));
                reporteActual.setFechaCaptura(rs.getDate("fecha_captura"));
                reporteActual.setIdTurno(rs.getInt("id_turno"));
                reporteActual.setOperario(rs.getString("operario"));
                reporteActual.setNombreHomologado(rs.getString("nombre_homologado"));
                reporteActual.setDescEquipo(rs.getString("desc_equipo"));
                reporteActual.setDescProceso(rs.getString("desc_proceso"));
                reporteActual.setHoraInicio(rs.getTimestamp("hora_inicio"));
                reporteActual.setHoraFinal(rs.getTimestamp("hora_final"));
                reporteActual.setOp(rs.getString("op"));
                reporteActual.setIdItem(rs.getString("id_item"));
                reporteActual.setDescripcionItem(rs.getString("descripcion_item"));
                reporteActual.setExt1Detalle(rs.getString("ext1_detalle"));
                reporteActual.setExt2Detalle(rs.getString("ext2_detalle"));
                reporteActual.setRuta(rs.getString("ruta"));
                reporteActual.setCentroTrabajo(rs.getString("centro_trabajo"));
                
                reporteActual.setPuntasNominal(rs.getInt("puntas_nominal"));
                reporteActual.setPuntasUnoee(rs.getInt("puntas_unoee"));
                reporteActual.setPuntasProduccion(rs.getInt("puntas_produccion"));
                reporteActual.setPp(rs.getInt("pp"));
                
                reporteActual.setMtrsProducidos(rs.getBigDecimal("mtrs_producidos"));
                reporteActual.setPeso(rs.getBigDecimal("peso"));
                reporteActual.setFactorConversion(rs.getBigDecimal("factor_conversion"));
                reporteActual.setClaseb(rs.getBigDecimal("claseb"));
                reporteActual.setProductoRechazado(rs.getBigDecimal("producto_rechazado"));
                
                reporteActual.setHoraInicioParada(rs.getTimestamp("hora_inicio_parada"));
                reporteActual.setHoraFinParada(rs.getTimestamp("hora_fin_parada"));
                
                reporteActual.setTiempoDiferencia(rs.getBigDecimal("tiempo_diferencia"));
                
                reporteActual.setCodigoParada(rs.getString("codigo_parada"));
                reporteActual.setDescParada(rs.getString("desc_parada"));
                reporteActual.setLetra(rs.getString("letra"));
                reporteActual.setDescEsquema(rs.getString("desc_esquema"));
                reporteActual.setDescUnidadNegocio(rs.getString("desc_unidad_negocio"));
                reporteActual.setDescPlanta(rs.getString("desc_planta"));
                reporteActual.setDescLineaProduccion(rs.getString("desc_linea_produccion"));
            
                reporteActual.setCantProgramada(rs.getBigDecimal("cant_programada"));
                reporteActual.setCantEntregada(rs.getBigDecimal("cant_entregada"));
                
                listaReporte.add(reporteActual);
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
}
