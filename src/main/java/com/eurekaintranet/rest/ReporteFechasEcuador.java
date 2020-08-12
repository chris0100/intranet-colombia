/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.rest;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Lccabalr
 */
@Path("reporteFechasEcuador")
public class ReporteFechasEcuador {

    private String nroPedidoUnoee;
    private String notas;
    private String nroPedidoMba;
    private String codBarraItem;
    private String fechaEntrega;
    private BigDecimal cantPendiente;
    private ReporteFechasEcuador reporteActual;
    private List<ReporteFechasEcuador> listaReporteFechasEcuador;
    
    public ReporteFechasEcuador() {
    }

    public String getNroPedidoUnoee() {
        return nroPedidoUnoee;
    }

    public void setNroPedidoUnoee(String nroPedidoUnoee) {
        this.nroPedidoUnoee = nroPedidoUnoee;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getNroPedidoMba() {
        return nroPedidoMba;
    }

    public void setNroPedidoMba(String nroPedidoMba) {
        this.nroPedidoMba = nroPedidoMba;
    }

    public String getCodBarraItem() {
        return codBarraItem;
    }

    public void setCodBarraItem(String codBarraItem) {
        this.codBarraItem = codBarraItem;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

  

    public BigDecimal getCantPendiente() {
        return cantPendiente;
    }

    public void setCantPendiente(BigDecimal cantPendiente) {
        this.cantPendiente = cantPendiente;
    }

    public ReporteFechasEcuador getReporteActual() {
        return reporteActual;
    }

    public void setReporteActual(ReporteFechasEcuador reporteActual) {
        this.reporteActual = reporteActual;
    }

    public List<ReporteFechasEcuador> getListaReporteFechasEcuador() {
        return listaReporteFechasEcuador;
    }

    public void setListaReporteFechasEcuador(List<ReporteFechasEcuador> listaReporteFechasEcuador) {
        this.listaReporteFechasEcuador = listaReporteFechasEcuador;
    }
    
    
    public void obtenerReportePedidosFechas()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        Calendar anioActual = Calendar.getInstance();
        String anioString = String.valueOf(anioActual.get(Calendar.YEAR));
        String query = "exec sp_pv_cons_pedido_item 1,'001','   ','          ','   ','   ',0,0,'20200401','20200415',1,'"+anioString+"0101','"+anioString+"1231',6,1,10097,'EUREKA_REPORTEFECHASECUADOR','          ',0,4300,120,0,1,NULL,NULL,0,0,0,0";
        listaReporteFechasEcuador = new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                reporteActual= new ReporteFechasEcuador();
                reporteActual.setNroPedidoUnoee(rs.getString("f_nrodocto"));
                reporteActual.setCodBarraItem("26"+rs.getString("f_codigo_barras_principal"));
                String pattern = "MM/dd/yyyy";
                DateFormat df = new SimpleDateFormat(pattern);
                Date fecha = Calendar.getInstance().getTime();     
                String fechaString = df.format(rs.getDate("f_fecha_entrega"));
                reporteActual.setFechaEntrega(fechaString);
                reporteActual.setNotas(rs.getString("f_notas_movto").trim());
                reporteActual.setCantPendiente(rs.getBigDecimal("f_cant_pendiente_base"));
                String[] obtenerPedido = reporteActual.getNotas().split("/");
                int totalCadena = 0;
                for (int i = 0; i < obtenerPedido.length; i++) {
                    totalCadena++;
                }
                try {
                    if(obtenerPedido[totalCadena-1].length()<30){
                        reporteActual.setNroPedidoMba(obtenerPedido[totalCadena-1].trim()+"-EK001-PE");
                    }else{
                        reporteActual.setNroPedidoMba("Error");
                    }
                } catch (Exception e) {
                    reporteActual.setNroPedidoMba("");
                    
                }
                listaReporteFechasEcuador.add(reporteActual);         
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReporteFechasEcuador> find() throws SQLException {
        obtenerReportePedidosFechas();
        return listaReporteFechasEcuador;
    }
}
