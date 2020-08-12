/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T010EstadoItem;
import com.eurekaintranet.sessions.T010EstadoItemFacade;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lccabalr
 */
@Named(value = "reporteProgramacionController")
@SessionScoped
public class ReporteProgramacionController implements Serializable {

    private String f_nrodocto;
    private String f_referencia;
    private String f_ext_detalle_1;
    private String f_ext_detalle_2;
    private String f_desc_ext_detalle_2_normal;
    private String f_desc_item;
    private BigDecimal f_cant_pedida_base;
    private BigDecimal f_cant_existencia;
    private BigDecimal f_cant_existencia_ins;
    private BigDecimal f_cant_comprometida_bodega;
    private BigDecimal f_cant_disponible;
    private BigDecimal f_cant_req_bodega;
    private BigDecimal f_cant_oc_op_inv_ins;
    private Date f_fecha_entrega;
    private String f_notas_docto;
    private String f_ops;
    private String f_notas_movto;
    private String f_estado_movto;
    private String f_cliente_desp_razon_soc;
    private String f_desc_ciudad;
    private BigDecimal f_divisor_margen_prom;
    private int f_rowid_movto;
    private BigDecimal f_divisor_margen_est;
    private int f_rowid_pv_docto;
    private BigDecimal f_utilidad_prom_f;
    private int f_id_clase_docto;
    private int f_numero;
    private int f_rowid;
    private String f_01_050;
    private String f_02_005;
    private String f_02_002;
    private String f_20_00000014_M121;
    
    
    
    
    private ReporteProgramacionController reporteActual;
    private BigDecimal c1_existencia_real;
    private BigDecimal c2_pendiente_programar;
    private Date c3_fecha_final_op;
    private String  c4_fecha_final_op_calculada;
    private Date fechaFinalOp;
    private Date fechaParametro1;
    private Date fechaParametro2;
    private String[] estado;
    private ArrayList<String> grupo = new ArrayList<>();
    private String tipoAgrupamiento;
    private boolean filtrarExistenciaRealNegativa = true;
    private boolean obtenerFechaTerminacionOP;
    
    private ReporteProgramacionDetalleController reporteDetalleActual;
    private List<ReporteProgramacionDetalleController> listaReporteDetalle;
  
    private List<ReporteProgramacionController> listaReporte;
    
    
    @EJB
    private T010EstadoItemFacade estadoItemFacade;
    
    
    public ReporteProgramacionController() {
    }

    public String getF_nrodocto() {
        return f_nrodocto;
    }

    public void setF_nrodocto(String f_nrodocto) {
        this.f_nrodocto = f_nrodocto;
    }

    public String getF_referencia() {
        return f_referencia;
    }

    public void setF_referencia(String f_referencia) {
        this.f_referencia = f_referencia;
    }

    public String getF_desc_item() {
        return f_desc_item;
    }

    public void setF_desc_item(String f_desc_item) {
        this.f_desc_item = f_desc_item;
    }

    public String getF_ext_detalle_1() {
        return f_ext_detalle_1;
    }

    public void setF_ext_detalle_1(String f_ext_detalle_1) {
        this.f_ext_detalle_1 = f_ext_detalle_1;
    }

    public String getF_ext_detalle_2() {
        return f_ext_detalle_2;
    }

    public void setF_ext_detalle_2(String f_ext_detalle_2) {
        this.f_ext_detalle_2 = f_ext_detalle_2;
    }

    public String getF_desc_ext_detalle_2_normal() {
        return f_desc_ext_detalle_2_normal;
    }

    public void setF_desc_ext_detalle_2_normal(String f_desc_ext_detalle_2_normal) {
        this.f_desc_ext_detalle_2_normal = f_desc_ext_detalle_2_normal;
    }

    public BigDecimal getF_cant_existencia() {
        return f_cant_existencia;
    }

    public void setF_cant_existencia(BigDecimal f_cant_existencia) {
        this.f_cant_existencia = f_cant_existencia;
    }

    public BigDecimal getF_cant_existencia_ins() {
        return f_cant_existencia_ins;
    }

    public void setF_cant_existencia_ins(BigDecimal f_cant_existencia_ins) {
        this.f_cant_existencia_ins = f_cant_existencia_ins;
    }

    
    
    public BigDecimal getF_cant_pedida_base() {
        return f_cant_pedida_base;
    }

    public void setF_cant_pedida_base(BigDecimal f_cant_pedida_base) {
        this.f_cant_pedida_base = f_cant_pedida_base;
    }

    public BigDecimal getF_cant_comprometida_bodega() {
        return f_cant_comprometida_bodega;
    }

    public void setF_cant_comprometida_bodega(BigDecimal f_cant_comprometida_bodega) {
        this.f_cant_comprometida_bodega = f_cant_comprometida_bodega;
    }

    
    public BigDecimal getF_cant_disponible() {
        return f_cant_disponible;
    }

    public void setF_cant_disponible(BigDecimal f_cant_disponible) {
        this.f_cant_disponible = f_cant_disponible;
    }

    public BigDecimal getF_cant_req_bodega() {
        return f_cant_req_bodega;
    }

    public void setF_cant_req_bodega(BigDecimal f_cant_req_bodega) {
        this.f_cant_req_bodega = f_cant_req_bodega;
    }

    public BigDecimal getF_cant_oc_op_inv_ins() {
        return f_cant_oc_op_inv_ins;
    }

    public void setF_cant_oc_op_inv_ins(BigDecimal f_cant_oc_op_inv_ins) {
        this.f_cant_oc_op_inv_ins = f_cant_oc_op_inv_ins;
    }

    public String getF_cliente_desp_razon_soc() {
        return f_cliente_desp_razon_soc;
    }

    public void setF_cliente_desp_razon_soc(String f_cliente_desp_razon_soc) {
        this.f_cliente_desp_razon_soc = f_cliente_desp_razon_soc;
    }

    public String getF_ops() {
        return f_ops;
    }

    public void setF_ops(String f_ops) {
        this.f_ops = f_ops;
    }

    public Date getF_fecha_entrega() {
        return f_fecha_entrega;
    }

    public void setF_fecha_entrega(Date f_fecha_entrega) {
        this.f_fecha_entrega = f_fecha_entrega;
    }

    public String getF_notas_docto() {
        return f_notas_docto;
    }

    public void setF_notas_docto(String f_notas_docto) {
        this.f_notas_docto = f_notas_docto;
    }
    public String getF_notas_movto() {
        return f_notas_movto;
    }

    public void setF_notas_movto(String f_notas_movto) {
        this.f_notas_movto = f_notas_movto;
    }
    
    public String getF_estado_movto() {
        return f_estado_movto;
    }

    public void setF_estado_movto(String f_estado_movto) {
        this.f_estado_movto = f_estado_movto;
    }

    public String getF_desc_ciudad() {
        return f_desc_ciudad;
    }

    public void setF_desc_ciudad(String f_desc_ciudad) {
        this.f_desc_ciudad = f_desc_ciudad;
    }

   

    public BigDecimal getF_divisor_margen_prom() {
        return f_divisor_margen_prom;
    }

    public void setF_divisor_margen_prom(BigDecimal f_divisor_margen_prom) {
        this.f_divisor_margen_prom = f_divisor_margen_prom;
    }

    public int getF_rowid_movto() {
        return f_rowid_movto;
    }

    public void setF_rowid_movto(int f_rowid_movto) {
        this.f_rowid_movto = f_rowid_movto;
    }

    public BigDecimal getF_divisor_margen_est() {
        return f_divisor_margen_est;
    }

    public void setF_divisor_margen_est(BigDecimal f_divisor_margen_est) {
        this.f_divisor_margen_est = f_divisor_margen_est;
    }

    public int getF_rowid_pv_docto() {
        return f_rowid_pv_docto;
    }

    public void setF_rowid_pv_docto(int f_rowid_pv_docto) {
        this.f_rowid_pv_docto = f_rowid_pv_docto;
    }

    public int getF_rowid() {
        return f_rowid;
    }

    public void setF_rowid(int f_rowid) {
        this.f_rowid = f_rowid;
    }

    public BigDecimal getF_utilidad_prom_f() {
        return f_utilidad_prom_f;
    }

    public void setF_utilidad_prom_f(BigDecimal f_utilidad_prom_f) {
        this.f_utilidad_prom_f = f_utilidad_prom_f;
    }

    public int getF_id_clase_docto() {
        return f_id_clase_docto;
    }

    public void setF_id_clase_docto(int f_id_clase_docto) {
        this.f_id_clase_docto = f_id_clase_docto;
    }

    public int getF_numero() {
        return f_numero;
    }

    public void setF_numero(int f_numero) {
        this.f_numero = f_numero;
    }

    public String getF_01_050() {
        return f_01_050;
    }

    public void setF_01_050(String f_01_050) {
        this.f_01_050 = f_01_050;
    }

    public String getF_02_005() {
        return f_02_005;
    }

    public void setF_02_005(String f_02_005) {
        this.f_02_005 = f_02_005;
    }

   

    public String getF_02_002() {
        return f_02_002;
    }

    public void setF_02_002(String f_02_002) {
        this.f_02_002 = f_02_002;
    }

    
    
    public String getF_20_00000014_M121() {
        return f_20_00000014_M121;
    }

    public void setF_20_00000014_M121(String f_20_00000014_M121) {
        this.f_20_00000014_M121 = f_20_00000014_M121;
    }

    public List<ReporteProgramacionController> getListaReporte() {
        return listaReporte;
    }

    public void setListaReporte(List<ReporteProgramacionController> listaReporte) {
        this.listaReporte = listaReporte;
    }

    public ReporteProgramacionController getReporteActual() {
        return reporteActual;
    }

    public void setReporteActual(ReporteProgramacionController reporteActual) {
        this.reporteActual = reporteActual;
    }

    public BigDecimal getC1_existencia_real() {
        return c1_existencia_real;
    }

    public void setC1_existencia_real(BigDecimal c1_existencia_real) {
        this.c1_existencia_real = c1_existencia_real;
    }

    public BigDecimal getC2_pendiente_programar() {
        return c2_pendiente_programar;
    }

    public void setC2_pendiente_programar(BigDecimal c2_pendiente_programar) {
        this.c2_pendiente_programar = c2_pendiente_programar;
    }

    public Date getC3_fecha_final_op() {
        return c3_fecha_final_op;
    }

    public void setC3_fecha_final_op(Date c3_fecha_final_op) {
        this.c3_fecha_final_op = c3_fecha_final_op;
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

    public String[] getEstado() {
        return estado;
    }

    public void setEstado(String[] estado) {
        this.estado = estado;
    }

    public String getTipoAgrupamiento() {
        return tipoAgrupamiento;
    }

    public void setTipoAgrupamiento(String tipoAgrupamiento) {
        this.tipoAgrupamiento = tipoAgrupamiento;
    }

    public boolean isAgrupamiento() {
        if(tipoAgrupamiento.equals("p")){
           return true;  
        }else{
           return false;
        }
    }

    public ArrayList<String> getGrupo() {
        return grupo;
    }

    public void setGrupo(ArrayList<String> grupo) {
        this.grupo = grupo;
    }

    public boolean isFiltrarExistenciaRealNegativa() {
        return filtrarExistenciaRealNegativa;
    }

    public void setFiltrarExistenciaRealNegativa(boolean filtrarExistenciaRealNegativa) {
        this.filtrarExistenciaRealNegativa = filtrarExistenciaRealNegativa;
    }

    public Date getFechaFinalOp() {
        return fechaFinalOp;
    }

    public void setFechaFinalOp(Date fechaFinalOp) {
        this.fechaFinalOp = fechaFinalOp;
    }

    public ReporteProgramacionDetalleController getReporteDetalleActual() {
        return reporteDetalleActual;
    }

    public void setReporteDetalleActual(ReporteProgramacionDetalleController reporteDetalleActual) {
        this.reporteDetalleActual = reporteDetalleActual;
    }

    public List<ReporteProgramacionDetalleController> getListaReporteDetalle() {
        return listaReporteDetalle;
    }

    public void setListaReporteDetalle(List<ReporteProgramacionDetalleController> listaReporteDetalle) {
        this.listaReporteDetalle = listaReporteDetalle;
    }

    public T010EstadoItemFacade getEstadoItemFacade() {
        return estadoItemFacade;
    }

    public void setEstadoItemFacade(T010EstadoItemFacade estadoItemFacade) {
        this.estadoItemFacade = estadoItemFacade;
    }

    public boolean isObtenerFechaTerminacionOP() {
        return obtenerFechaTerminacionOP;
    }

    public void setObtenerFechaTerminacionOP(boolean obtenerFechaTerminacionOP) {
        this.obtenerFechaTerminacionOP = obtenerFechaTerminacionOP;
    }

    public String getC4_fecha_final_op_calculada() {
        return c4_fecha_final_op_calculada;
    }

    public void setC4_fecha_final_op_calculada(String c4_fecha_final_op_calculada) {
        this.c4_fecha_final_op_calculada = c4_fecha_final_op_calculada;
    }
    
    
    
    //navegacion
    public void recargarLista(){
        obtenerFechaTerminacionOP = false;
        filtrarExistenciaRealNegativa = true;
        tipoAgrupamiento = "p";        
        estado = null;
        fechaParametro1 = null;
        fechaParametro2 = null;
        listaReporte = new ArrayList<>();
    }
    public String prepareListProgramacion() throws SQLException{
        recargarLista();
        return  "/administrador/modProgramacion/reporteProgramacion";
    }
    public String prepareListVentas() throws SQLException{
        recargarLista();
        return  "/administrador/modProgramacion/reporteVentas";
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
    //Obtener reporte Unoee
    public String recargarConsultarItemsPedidosUnoee()
        throws SQLException, ParseException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");  
        String fecha1 = dateFormat1.format(fechaParametro1); 
        DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");  
        String fecha2 = dateFormat2.format(fechaParametro2); 
        listaReporte = new ArrayList<>();
        grupo = new ArrayList<>();
        for (int i = 0; i < estado.length; i++){
            String query = "exec sp_pv_cons_pedido_item 1,'001','   ','          ','   ','   ',0,1,'"+fecha1+"','"+fecha2+"',0,'20190501','20190529',"+estado[i]+",1,10097,'EUREKA_REPORTEPLANEACION','          ',0,4300,0,0,1,NULL,NULL,0,0,0";
            try {
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    rs.setFetchSize(20000);
                    boolean grupoEncontrado = false;
                    reporteActual = new ReporteProgramacionController();
                    reporteActual.setF_nrodocto(rs.getString("f_nrodocto")); //Pedido
                    reporteActual.setF_referencia(rs.getString("f_referencia")); //Referencia
                    reporteActual.setF_ext_detalle_1(rs.getString("f_ext_detalle_1")); //Tamaño
                    reporteActual.setF_ext_detalle_2(rs.getString("f_ext_detalle_2")); //Color
                    reporteActual.setF_desc_ext_detalle_2_normal(rs.getString("f_desc_ext_detalle_2_normal")); //Desc. color
                    reporteActual.setF_desc_item(rs.getString("f_desc_item")); //Item
                    reporteActual.setF_cant_pedida_base(rs.getBigDecimal("f_cant_pedida_base")); //Cant. pedido
                    reporteActual.setF_cant_existencia(rs.getBigDecimal("f_cant_existencia")); //Existencia
                    reporteActual.setF_cant_existencia_ins(rs.getBigDecimal("f_cant_existencia_ins")); //Existencia (ins)
                    reporteActual.setF_cant_comprometida_bodega(rs.getBigDecimal("f_cant_comprometida_bodega")); //Comprometido
                    reporteActual.setF_cant_disponible(rs.getBigDecimal("f_cant_disponible"));// Disponible
                    reporteActual.setF_cant_req_bodega(rs.getBigDecimal("f_cant_req_bodega")); //Requerido
                    reporteActual.setF_cant_oc_op_inv_ins(rs.getBigDecimal("f_cant_oc_op_inv_ins")); // Lanzado
                    reporteActual.setF_fecha_entrega(rs.getDate("f_fecha_entrega")); //Fecha entrega
                    reporteActual.setF_notas_movto(rs.getString("f_notas_movto")); //Notas item
                    reporteActual.setF_notas_docto(rs.getString("f_notas_docto")); //Notas documento
                    reporteActual.setF_estado_movto(rs.getString("f_estado_movto")); //Estado movimiento
                    reporteActual.setF_cliente_desp_razon_soc(rs.getString("f_cliente_desp_razon_soc")); //Cliente
                    reporteActual.setF_desc_ciudad(rs.getString("f_desc_ciudad")); //Ciudad
                    //reporteActual.setF_divisor_margen_prom(rs.getBigDecimal("f_divisor_margen_prom"));//
                    reporteActual.setF_rowid_movto(rs.getInt("f_rowid_movto"));
                    //reporteActual.setF_divisor_margen_est(rs.getBigDecimal("f_divisor_margen_est"));
                    reporteActual.setF_rowid_pv_docto(rs.getInt("f_rowid_pv_docto"));
                    //reporteActual.setF_utilidad_prom_f(rs.getBigDecimal("f_utilidad_prom_f"));
                    //reporteActual.setF_id_clase_docto(rs.getInt("f_id_clase_docto"));
                    //reporteActual.setF_numero(rs.getInt("f_numero"));
                    //reporteActual.setF_rowid(rs.getInt("f_rowid"));
                    reporteActual.setF_01_050(rs.getString("f_01_050"));//Grupo
                    reporteActual.setF_02_005(rs.getString("f_02_005"));//Asesor 
                    reporteActual.setF_02_002(rs.getString("f_02_002"));//Segmetos
                    
                    if(rs.getString("f_ops").equals("")){
                        reporteActual.setF_ops("NO TIENE");
                    }else{
                        reporteActual.setF_ops(rs.getString("f_ops"));
                    }
                     //Nro. op
                    if(reporteActual.getF_01_050()==null){
                        reporteActual.setF_01_050("");
                    }
                    try {
                        reporteActual.setC1_existencia_real(reporteActual.getF_cant_existencia().add(reporteActual.getF_cant_oc_op_inv_ins()).subtract(reporteActual.getF_cant_comprometida_bodega()).subtract(reporteActual.getF_cant_req_bodega()));
                        reporteActual.setC2_pendiente_programar(reporteActual.getF_cant_existencia().subtract(reporteActual.getF_cant_req_bodega()).subtract(reporteActual.getF_cant_comprometida_bodega()));
                    } catch (Exception e) {
                        reporteActual.setC1_existencia_real(new BigDecimal(0));
                    } 
                    if(filtrarExistenciaRealNegativa && reporteActual.getC1_existencia_real().compareTo(BigDecimal.ZERO) < 0){
                        reporteActual.setF_20_00000014_M121(rs.getString("f_20_00000014_M121"));//Estado item
                        try {
                            for(String item: grupo ){
                                if(item.equals(reporteActual.getF_01_050())){
                                    grupoEncontrado = true;
                                    break;
                                }
                            }
                            if(!grupoEncontrado){
                                grupo.add(reporteActual.getF_01_050());
                            }
                        } catch (Exception e) {
                        }
                        listaReporte.add(reporteActual);
                    }else if(!filtrarExistenciaRealNegativa) {
                        reporteActual.setF_20_00000014_M121(rs.getString("f_20_00000014_M121"));//Estado item
                        try {
                            for(String item: grupo ){
                                if(item.equals(reporteActual.getF_01_050())){
                                    grupoEncontrado = true;
                                    break;
                                }
                            }
                            if(!grupoEncontrado){
                                grupo.add(reporteActual.getF_01_050());
                            }
                        } catch (Exception e) {
                        }
                        listaReporte.add(reporteActual);
                    }
                }
                //Consulta fecha terminación op
                if(obtenerFechaTerminacionOP){
                    obtenerFechaTerminacionOPFacade();
                }
            } catch (SQLException e ) {
                System.out.println("error "+e);
            } finally {
                if (stmt != null) { stmt.close(); }
            }
        }
        return "reporteProgramacion";
    }
    public String recargarConsultarItemsPedidosUnoeeVentas()
        throws SQLException, ParseException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");  
        String fecha1 = dateFormat1.format(fechaParametro1); 
        DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");  
        String fecha2 = dateFormat2.format(fechaParametro2); 
        listaReporte = new ArrayList<>();
        grupo = new ArrayList<>();
        for (int i = 0; i < estado.length; i++){
            String query = "exec sp_pv_cons_pedido_item 1,'001','   ','          ','   ','   ',0,1,'"+fecha1+"','"+fecha2+"',0,'20190501','20190529',"+estado[i]+",1,10097,'EUREKA_REPORTEPROGRAMACIONVENTAS','          ',0,4300,0,0,1,NULL,NULL,0,0,0";
            try {
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    rs.setFetchSize(20000);
                    reporteActual = new ReporteProgramacionController();
                    reporteActual.setF_nrodocto(rs.getString("f_nrodocto")); //Pedido
                    reporteActual.setF_referencia(rs.getString("f_referencia")); //Referencia
                    reporteActual.setF_ext_detalle_1(rs.getString("f_ext_detalle_1")); //Tamaño
                    reporteActual.setF_ext_detalle_2(rs.getString("f_ext_detalle_2")); //Color
                    reporteActual.setF_desc_ext_detalle_2_normal(rs.getString("f_desc_ext_detalle_2_normal")); //Desc. color
                    reporteActual.setF_desc_item(rs.getString("f_desc_item")); //Item
                    reporteActual.setF_cliente_desp_razon_soc(rs.getString("f_cliente_desp_razon_soc")); //Cliente
                    reporteActual.setF_cant_pedida_base(rs.getBigDecimal("f_cant_pedida_base")); //Cant. pedido
                    reporteActual.setF_cant_existencia(rs.getBigDecimal("f_cant_existencia")); //Existencia
                    reporteActual.setF_cant_existencia_ins(rs.getBigDecimal("f_cant_existencia_ins")); //Existencia (ins)
                    reporteActual.setF_cant_comprometida_bodega(rs.getBigDecimal("f_cant_comprometida_bodega")); //Comprometido
                    reporteActual.setF_cant_disponible(rs.getBigDecimal("f_cant_disponible"));// Disponible
                    reporteActual.setF_cant_req_bodega(rs.getBigDecimal("f_cant_req_bodega")); //Requerido
                    reporteActual.setF_cant_oc_op_inv_ins(rs.getBigDecimal("f_cant_oc_op_inv_ins")); // Lanzado
                    reporteActual.setF_fecha_entrega(rs.getDate("f_fecha_entrega")); //Fecha entrega
                    reporteActual.setF_notas_docto(rs.getString("f_notas_docto")); //Notas documento
                    reporteActual.setF_estado_movto(rs.getString("f_estado_movto")); //Estado movimiento
                    reporteActual.setF_rowid_movto(rs.getInt("f_rowid_movto"));
                    reporteActual.setF_rowid_pv_docto(rs.getInt("f_rowid_pv_docto"));
                    reporteActual.setF_20_00000014_M121(rs.getString("f_20_00000014_M121"));//Estado item
                    if(rs.getString("f_ops").equals("")){
                        reporteActual.setF_ops("NO TIENE");
                    }else{
                        reporteActual.setF_ops(rs.getString("f_ops"));
                    }
                    try {
                        reporteActual.setC1_existencia_real(reporteActual.getF_cant_existencia().add(reporteActual.getF_cant_oc_op_inv_ins()).subtract(reporteActual.getF_cant_comprometida_bodega()).subtract(reporteActual.getF_cant_req_bodega()));
                        reporteActual.setC2_pendiente_programar(reporteActual.getF_cant_existencia().subtract(reporteActual.getF_cant_req_bodega()).subtract(reporteActual.getF_cant_comprometida_bodega()));
                    } catch (Exception e) {
                        reporteActual.setC1_existencia_real(new BigDecimal(0));
                    } 
                    listaReporte.add(reporteActual);
                }
                
            } catch (SQLException e ) {
                System.out.println("error "+e);
            } finally {
                if (stmt != null) { stmt.close(); }
            }
        }
        //Consulta fecha terminación op
        obtenerFechaTerminacionOPFacade();
        Collections.sort(listaReporte, (ReporteProgramacionController p1, ReporteProgramacionController p2) -> (p2.getF_nrodocto()).compareTo(p1.getF_nrodocto())
        );
        return "reporteVentas";
    }
    
    //Fecha fional op relacionada
    //Obtener reporte Unoee
    public void cargarDialogo() throws SQLException{
        obtenerFechaTerminacionOp();
        obtenerDetalleItem();
    }
    public void obtenerFechaTerminacionOp() throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "SELECT TOP 1 f851_fecha_terminacion FROM [UnoEE].[dbo].[t851_mf_op_docto_item] t851 inner join t850_mf_op_docto t850 on t850.f850_rowid = t851.f851_rowid_op_docto  inner join v121 on v121.v121_rowid_item_ext = t851.f851_rowid_item_ext_padre inner join [UnoEE].[dbo].t430_cm_pv_docto t430 on  t850.f850_rowid_pv_docto = t430.f430_rowid where V121.v121_referencia = '"+reporteActual.getF_referencia()+"' AND v121.v121_id_ext1_detalle = '"+reporteActual.getF_ext_detalle_1()+"' and  v121.v121_id_ext2_detalle =  '"+reporteActual.getF_ext_detalle_2()+"'  AND t430.f430_rowid = "+reporteActual.getF_rowid_pv_docto()+" and t851.f851_ind_estado = 1 ORDER BY f851_fecha_terminacion DESC";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
               reporteActual.setC3_fecha_final_op(rs.getDate("f851_fecha_terminacion"));
              break;
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void obtenerDetalleItem()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "SELECT t850.f850_id_tipo_docto+'-'+RIGHT('00000000' + Ltrim(Rtrim(CONVERT(varchar(8), t850.f850_consec_docto))),8) nro_documento,  t851.f851_ind_estado estado, t850.f850_referencia_1 referencia_1, f851_fecha_terminacion fecha_terminacion, t851.f851_cant_planeada_1 cant_planeada FROM [UnoEE].[dbo].[t851_mf_op_docto_item] t851 inner join [UnoEE].[dbo].t850_mf_op_docto t850 on t850.f850_rowid = t851.f851_rowid_op_docto inner join [UnoEE].[dbo].v121 on v121.v121_rowid_item_ext =  t851.f851_rowid_item_ext_padre where V121.v121_referencia = '"+reporteActual.getF_referencia()+"' AND v121.v121_id_ext1_detalle = '"+reporteActual.getF_ext_detalle_1()+"' and  v121.v121_id_ext2_detalle =  '"+reporteActual.getF_ext_detalle_2()+"' and t851.f851_ind_estado = 1 ORDER BY f851_fecha_terminacion DESC";
        listaReporteDetalle = new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                reporteDetalleActual = new ReporteProgramacionDetalleController();
                reporteDetalleActual.setNroDocumentoDetalle(rs.getString("nro_documento"));
                reporteDetalleActual.setReferencia1Detalle(rs.getString("referencia_1"));
                reporteDetalleActual.setFechaTerminacionDetalle(rs.getDate("fecha_terminacion"));
                reporteDetalleActual.setCantPlaneadaDetalle(rs.getBigDecimal("cant_planeada"));
                listaReporteDetalle.add(reporteDetalleActual);         
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public String obtenerFechaMayorTerminacionOp(String referencia, String ext1, String ext2)
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "SELECT TOP 1 f851_fecha_terminacion fecha_terminacion FROM [UnoEE].[dbo].[t851_mf_op_docto_item] t851 inner join [UnoEE].[dbo].t850_mf_op_docto t850 on t850.f850_rowid = t851.f851_rowid_op_docto inner join [UnoEE].[dbo].v121 on v121.v121_rowid_item_ext =  t851.f851_rowid_item_ext_padre where V121.v121_referencia = '"+referencia+"' AND v121.v121_id_ext1_detalle = '"+ext1+"' and  v121.v121_id_ext2_detalle =  '"+ext2+"' and t851.f851_ind_estado = 1 ORDER BY f851_fecha_terminacion DESC";
        String fechaTerminacion ="";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fechaTerminacion= String.valueOf(rs.getDate("fecha_terminacion")); 
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return fechaTerminacion;
    }
    public void obtenerFechaTerminacionOPFacade(){
        try {
            for(ReporteProgramacionController item: listaReporte){
                //Se utiliza el facade T010EstadoItem
                if(!item.getF_ops().equals("NO TIENE")){
                    item.setC4_fecha_final_op_calculada(getEstadoItemFacade().fechaTerminacionOP(item.getF_referencia(), item.getF_ext_detalle_1(), item.getF_ext_detalle_2(), item.getF_rowid_pv_docto()));
                 }else if(item.getF_ops().equals("NO TIENE") && item.getC1_existencia_real().compareTo(new BigDecimal(0))>=0){
                    item.setC4_fecha_final_op_calculada(obtenerFechaMayorTerminacionOp(item.getF_referencia(),item.getF_ext_detalle_1(),item.getF_ext_detalle_2()));
                    if(item.getC4_fecha_final_op_calculada().equals("")){
                        item.setC4_fecha_final_op_calculada("ASIGNAR O VALIDAR IMPORTACIÓN");
                    }
                }else if(item.getF_ops().equals("NO TIENE") && item.getC1_existencia_real().compareTo(new BigDecimal(0))<0){
                    item.setC4_fecha_final_op_calculada("PENDIENTE POR PROGRAMAR");
                }
            }
        } catch (Exception e) {
           System.out.println("error "+e); 
        }
    }
    
    
    
}
