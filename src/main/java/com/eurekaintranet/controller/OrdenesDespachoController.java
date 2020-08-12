/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T045ImpresionOrdenDespacho;
import com.eurekaintranet.entities.T046ConfigOrdenDespacho;
import com.eurekaintranet.sessions.T045ImpresionOrdenDespachoFacade;
import com.eurekaintranet.sessions.T046ConfigOrdenDespachoFacade;
import com.lowagie.text.DocumentException;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.xhtmlrenderer.pdf.ITextRenderer;

import static com.eurekaintranet.controller.util.OrderPedidoItem.*;

/**
 *
 * @author LUIS CARLOS CABAL
 */
@Named(value = "ordenesDespachoController")
@SessionScoped
public class OrdenesDespachoController implements Serializable {

    public BigDecimal getTotal_cant_comprom_base() {
        return total_cant_comprom_base;
    }

    public void setTotal_cant_comprom_base(BigDecimal total_cant_comprom_base) {
        this.total_cant_comprom_base = total_cant_comprom_base;
    }
    
    private String f_cliente_desp_razon_soc;
    private String f_desc_item;
    private String f_estado_docto;
    private String f_item;
    private String f_desc_ext_detalle_1;
    private String f_desc_ext_detalle_2;
    private String f_fecha_entrega;
    private String f_nrodocto;
    private int f_rowid_movto_431;
    private String f_estado_movto;
    private String f_bodega;
    private BigDecimal f_cant_pedida_base;
    private BigDecimal f_cant_comp_parc_base;
    private BigDecimal f_cant_comprom_base;
    private String f_fecha_creacion_docto;
    private String f_um_base;
    private BigDecimal f_cant_pendiente_base;
    private BigDecimal f_cant_existencia;
    private BigDecimal f_cant_oc_op_ins;
    private BigDecimal f_valor_neto_pendiente;
    private String f_ops;
    private String f_desc_ciudad;
    private String f_direccion1;
    private String f_punto_envio;
    private String f_desc_punto_envio;
    private String f_notas_docto;
    private String f_orden_compra;
    private Date f_fecha_factura;
    private String f_codigo_barras_principal;
    private int f_dia_maximo_factura;
    private BigDecimal f_valor_neto_docto;
    private String f_backorder;
    private String f_ext_detalle_1;
    private String f_ext_detalle_2;
    private String f_referencia;
    private Date f_fecha_modificacion_movto;
    private BigDecimal f_divisor_margen_prom;
    private BigDecimal f_divisor_margen_est;
    private int f_rowid_pv_docto;
    private BigDecimal f_utilidad_prom_f;
    private int f_rowid_movto;
    private int f_id_clase_docto;
    private int f_numero;
    private int f_rowid;
    private String f_01_010;
    private String f_01_100;
    private String f_01_050;
    private String f_02_002;
    private String f_02_003;
    private String f_02_005;
    private String f_20_00000014_M121;
    
    private String evaluacionItem;
    private String estadoImpresion;
    private String generarImpresion; 
 
    private String razonSocial;
    private OrdenesDespachoController reporteActual;
    
    private Date fechaParametro1;
    private Date fechaParametro2;
    private String[] estado;
    
    private List<OrdenesDespachoController> listaReporte;
    private List<OrdenesDespachoController> listaReporteImpresion;
    private BigDecimal contarValoNeto;
    
    private Date fechaImpresion;
    private int consImpresion;
    
    private List<String> listaImpresionCliente;
    private String clienteImprimir;
    
    @EJB
    private T045ImpresionOrdenDespachoFacade impresionFacade;
    private T045ImpresionOrdenDespacho impresionActual;
    private List<T045ImpresionOrdenDespacho> listaImpresion;
    
    private String estadoImpresionHabilitar;
    private String opcionImprimirCliente;
    private String opcionImprimirCondicion;
    
    private T001Usuario usuarioActual;
    
    @EJB
    private T046ConfigOrdenDespachoFacade configFacade;
    private T046ConfigOrdenDespacho configActual;
    private List<T046ConfigOrdenDespacho> listaConfig;
    
    private int contItemComp;
    private int contItemCompParcial;
    
    private BigDecimal total_cant_comprom_base;
    private BigDecimal total_valor_neto_pente;
    
    private String opcionFechaReporte;
    private static String[] columns = {"CLIENTE CUMPLE CONDICIÓN","LANZABLE","GENERA IMPRESIÓN","CLIENTE","ITEM","DESC. ÍTEM","DESC. TAMAÑO","DESC. COLOR","FECHA ENTREGA","PEDIDO","ESTADO MOVIMIENTO","ESTADO DOCUMENTO","CANT. PEDIDO","CANT. COMPROMETIDA","CANT. COMPROMETIDA PARCIAL","VALOR NETO PENDIENTE","VALOR NETO DOCTO.","ESTADO ITEM","BODEGA","FAMILIA","FECHA CREACIÓN","U.M.","CANT. PENDIENTE","CANT. EXISTENCIA","CANT. OC/OP (INS)","NRO. OP","PUNTO ENVIO","DESC. PUNTO DE ENVIO","CIUDAD","DIRECCIÓN","NOTAS DOCUMENTO","SEGMENTO","OC","FECHA FACTURA","SUBGRUPO","SERVICIO AL CLIENTE","CÓDIGO BARRA","DÍA MAX. FACTURACIÓN","BACKORDER","GRUPO","TAMAÑO","COLOR","REFERENCIA","FECHA MODIFICACIÓN MOVTO.","ESTADO","ORDEN INTERNO"};
    private EmailController emailController;
    
    public OrdenesDespachoController() {
    }

    public String getF_cliente_desp_razon_soc() {
        return f_cliente_desp_razon_soc;
    }

    public void setF_cliente_desp_razon_soc(String f_cliente_desp_razon_soc) {
        this.f_cliente_desp_razon_soc = f_cliente_desp_razon_soc;
    }

    public String getF_desc_item() {
        return f_desc_item;
    }

    public void setF_desc_item(String f_desc_item) {
        this.f_desc_item = f_desc_item;
    }

    public String getF_estado_docto() {
        return f_estado_docto;
    }

    public void setF_estado_docto(String f_estado_docto) {
        this.f_estado_docto = f_estado_docto;
    }

    public String getF_item() {
        return f_item;
    }

    public void setF_item(String f_item) {
        this.f_item = f_item;
    }

    public String getF_desc_ext_detalle_1() {
        return f_desc_ext_detalle_1;
    }

    public void setF_desc_ext_detalle_1(String f_desc_ext_detalle_1) {
        this.f_desc_ext_detalle_1 = f_desc_ext_detalle_1;
    }

    public String getF_desc_ext_detalle_2() {
        return f_desc_ext_detalle_2;
    }

    public void setF_desc_ext_detalle_2(String f_desc_ext_detalle_2) {
        this.f_desc_ext_detalle_2 = f_desc_ext_detalle_2;
    }

    public String getF_fecha_entrega() {
        return f_fecha_entrega;
    }

    public void setF_fecha_entrega(String f_fecha_entrega) {
        this.f_fecha_entrega = f_fecha_entrega;
    }

    public String getF_nrodocto() {
        return f_nrodocto;
    }

    public void setF_nrodocto(String f_nrodocto) {
        this.f_nrodocto = f_nrodocto;
    }

    public int getF_rowid_movto_431() {
        return f_rowid_movto_431;
    }

    public void setF_rowid_movto_431(int f_rowid_movto_431) {
        this.f_rowid_movto_431 = f_rowid_movto_431;
    }

    public String getF_estado_movto() {
        return f_estado_movto;
    }

    public void setF_estado_movto(String f_estado_movto) {
        this.f_estado_movto = f_estado_movto;
    }

    public String getF_bodega() {
        return f_bodega;
    }

    public void setF_bodega(String f_bodega) {
        this.f_bodega = f_bodega;
    }

    public BigDecimal getF_cant_pedida_base() {
        return f_cant_pedida_base;
    }

    public void setF_cant_pedida_base(BigDecimal f_cant_pedida_base) {
        this.f_cant_pedida_base = f_cant_pedida_base;
    }

    public BigDecimal getF_cant_comp_parc_base() {
        return f_cant_comp_parc_base;
    }

    public void setF_cant_comp_parc_base(BigDecimal f_cant_comp_parc_base) {
        this.f_cant_comp_parc_base = f_cant_comp_parc_base;
    }

    public String getF_fecha_creacion_docto() {
        return f_fecha_creacion_docto;
    }

    public void setF_fecha_creacion_docto(String f_fecha_creacion_docto) {
        this.f_fecha_creacion_docto = f_fecha_creacion_docto;
    }

    public String getF_um_base() {
        return f_um_base;
    }

    public void setF_um_base(String f_um_base) {
        this.f_um_base = f_um_base;
    }

    public BigDecimal getF_cant_pendiente_base() {
        return f_cant_pendiente_base;
    }

    public void setF_cant_pendiente_base(BigDecimal f_cant_pendiente_base) {
        this.f_cant_pendiente_base = f_cant_pendiente_base;
    }

    public BigDecimal getF_cant_existencia() {
        return f_cant_existencia;
    }

    public void setF_cant_existencia(BigDecimal f_cant_existencia) {
        this.f_cant_existencia = f_cant_existencia;
    }

    public BigDecimal getF_cant_oc_op_ins() {
        return f_cant_oc_op_ins;
    }

    public void setF_cant_oc_op_ins(BigDecimal f_cant_oc_op_ins) {
        this.f_cant_oc_op_ins = f_cant_oc_op_ins;
    }

    public String getF_ops() {
        return f_ops;
    }

    public void setF_ops(String f_ops) {
        this.f_ops = f_ops;
    }

    public String getF_desc_ciudad() {
        return f_desc_ciudad;
    }

    public void setF_desc_ciudad(String f_desc_ciudad) {
        this.f_desc_ciudad = f_desc_ciudad;
    }

    public String getF_notas_docto() {
        return f_notas_docto;
    }

    public void setF_notas_docto(String f_notas_docto) {
        this.f_notas_docto = f_notas_docto;
    }

    public String getF_orden_compra() {
        return f_orden_compra;
    }

    public void setF_orden_compra(String f_orden_compra) {
        this.f_orden_compra = f_orden_compra;
    }

    public Date getF_fecha_factura() {
        return f_fecha_factura;
    }

    public void setF_fecha_factura(Date f_fecha_factura) {
        this.f_fecha_factura = f_fecha_factura;
    }

    public String getF_codigo_barras_principal() {
        return f_codigo_barras_principal;
    }

    public void setF_codigo_barras_principal(String f_codigo_barras_principal) {
        this.f_codigo_barras_principal = f_codigo_barras_principal;
    }

    public int getF_dia_maximo_factura() {
        return f_dia_maximo_factura;
    }

    public void setF_dia_maximo_factura(int f_dia_maximo_factura) {
        this.f_dia_maximo_factura = f_dia_maximo_factura;
    }

    public BigDecimal getF_valor_neto_docto() {
        return f_valor_neto_docto;
    }

    public void setF_valor_neto_docto(BigDecimal f_valor_neto_docto) {
        this.f_valor_neto_docto = f_valor_neto_docto;
    }

    public String getF_backorder() {
        return f_backorder;
    }

    public void setF_backorder(String f_backorder) {
        this.f_backorder = f_backorder;
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

    public String getF_referencia() {
        return f_referencia;
    }

    public void setF_referencia(String f_referencia) {
        this.f_referencia = f_referencia;
    }

    public Date getF_fecha_modificacion_movto() {
        return f_fecha_modificacion_movto;
    }

    public void setF_fecha_modificacion_movto(Date f_fecha_modificacion_movto) {
        this.f_fecha_modificacion_movto = f_fecha_modificacion_movto;
    }

    public BigDecimal getF_divisor_margen_prom() {
        return f_divisor_margen_prom;
    }

    public void setF_divisor_margen_prom(BigDecimal f_divisor_margen_prom) {
        this.f_divisor_margen_prom = f_divisor_margen_prom;
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

    public BigDecimal getF_utilidad_prom_f() {
        return f_utilidad_prom_f;
    }

    public void setF_utilidad_prom_f(BigDecimal f_utilidad_prom_f) {
        this.f_utilidad_prom_f = f_utilidad_prom_f;
    }

    public int getF_rowid_movto() {
        return f_rowid_movto;
    }

    public void setF_rowid_movto(int f_rowid_movto) {
        this.f_rowid_movto = f_rowid_movto;
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

    public int getF_rowid() {
        return f_rowid;
    }

    public void setF_rowid(int f_rowid) {
        this.f_rowid = f_rowid;
    }

    public String getF_01_010() {
        return f_01_010;
    }

    public void setF_01_010(String f_01_010) {
        this.f_01_010 = f_01_010;
    }

    public String getF_01_100() {
        return f_01_100;
    }

    public void setF_01_100(String f_01_100) {
        this.f_01_100 = f_01_100;
    }

    public String getF_01_050() {
        return f_01_050;
    }

    public void setF_01_050(String f_01_050) {
        this.f_01_050 = f_01_050;
    }

    public String getF_02_002() {
        return f_02_002;
    }

    public void setF_02_002(String f_02_002) {
        this.f_02_002 = f_02_002;
    }

    public String getF_02_003() {
        return f_02_003;
    }

    public void setF_02_003(String f_02_003) {
        this.f_02_003 = f_02_003;
    }

    public String getF_02_005() {
        return f_02_005;
    }

    public void setF_02_005(String f_02_005) {
        this.f_02_005 = f_02_005;
    }

    public String getF_20_00000014_M121() {
        return f_20_00000014_M121;
    }

    public void setF_20_00000014_M121(String f_20_00000014_M121) {
        this.f_20_00000014_M121 = f_20_00000014_M121;
    }

    public OrdenesDespachoController getReporteActual() {
        return reporteActual;
    }

    public void setReporteActual(OrdenesDespachoController reporteActual) {
        this.reporteActual = reporteActual;
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

    public List<OrdenesDespachoController> getListaReporte() {
        return listaReporte;
    }

    public void setListaReporte(List<OrdenesDespachoController> listaReporte) {
        this.listaReporte = listaReporte;
    }

    public String getEvaluacionItem() {
        return evaluacionItem;
    }

    public void setEvaluacionItem(String evaluacionItem) {
        this.evaluacionItem = evaluacionItem;
    }

    public String getEstadoImpresion() {
        return estadoImpresion;
    }

    public void setEstadoImpresion(String estadoImpresion) {
        this.estadoImpresion = estadoImpresion;
    }

    public String getGenerarImpresion() {
        return generarImpresion;
    }

    public void setGenerarImpresion(String generarImpresion) {
        this.generarImpresion = generarImpresion;
    }

    public BigDecimal getF_valor_neto_pendiente() {
        return f_valor_neto_pendiente;
    }

    public void setF_valor_neto_pendiente(BigDecimal f_valor_neto_pendiente) {
        this.f_valor_neto_pendiente = f_valor_neto_pendiente;
    }

    public BigDecimal getF_cant_comprom_base() {
        return f_cant_comprom_base;
    }

    public void setF_cant_comprom_base(BigDecimal f_cant_comprom_base) {
        this.f_cant_comprom_base = f_cant_comprom_base;
    }

    public List<OrdenesDespachoController> getListaReporteImpresion() {
        return listaReporteImpresion;
    }

    public void setListaReporteImpresion(List<OrdenesDespachoController> listaReporteImpresion) {
        this.listaReporteImpresion = listaReporteImpresion;
    }

    public String getF_direccion1() {
        return f_direccion1;
    }

    public void setF_direccion1(String f_direccion1) {
        this.f_direccion1 = f_direccion1;
    }

    public String getF_punto_envio() {
        return f_punto_envio;
    }

    public void setF_punto_envio(String f_punto_envio) {
        this.f_punto_envio = f_punto_envio;
    }

    public String getF_desc_punto_envio() {
        return f_desc_punto_envio;
    }

    public void setF_desc_punto_envio(String f_desc_punto_envio) {
        this.f_desc_punto_envio = f_desc_punto_envio;
    }

    public Date getFechaImpresion() {
        return fechaImpresion;
    }

    public void setFechaImpresion(Date fechaImpresion) {
        this.fechaImpresion = fechaImpresion;
    }

    public int getConsImpresion() {
        return consImpresion;
    }

    public void setConsImpresion(int consImpresion) {
        this.consImpresion = consImpresion;
    }

    public T045ImpresionOrdenDespachoFacade getImpresionFacade() {
        return impresionFacade;
    }

    public void setImpresionFacade(T045ImpresionOrdenDespachoFacade impresionFacade) {
        this.impresionFacade = impresionFacade;
    }

    public T045ImpresionOrdenDespacho getImpresionActual() {
        return impresionActual;
    }

    public void setImpresionActual(T045ImpresionOrdenDespacho impresionActual) {
        this.impresionActual = impresionActual;
    }

    public List<T045ImpresionOrdenDespacho> getListaImpresion() {
        return listaImpresion;
    }

    public void setListaImpresion(List<T045ImpresionOrdenDespacho> listaImpresion) {
        this.listaImpresion = listaImpresion;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public List<String> getListaImpresionCliente() {
        return listaImpresionCliente;
    }

    public void setListaImpresionCliente(List<String> listaImpresionCliente) {
        this.listaImpresionCliente = listaImpresionCliente;
    }

    public String getClienteImprimir() {
        return clienteImprimir;
    }

    public void setClienteImprimir(String clienteImprimir) {
        this.clienteImprimir = clienteImprimir;
    }

    public String getOpcionImprimirCliente() {
        return opcionImprimirCliente;
    }

    public void setOpcionImprimirCliente(String opcionImprimirCliente) {
        this.opcionImprimirCliente = opcionImprimirCliente;
    }

    public String getOpcionImprimirCondicion() {
        return opcionImprimirCondicion;
    }

    public void setOpcionImprimirCondicion(String opcionImprimirCondicion) {
        this.opcionImprimirCondicion = opcionImprimirCondicion;
    }

    

    public String getEstadoImpresionHabilitar() {
        return estadoImpresionHabilitar;
    }

    public void setEstadoImpresionHabilitar(String estadoImpresionHabilitar) {
        this.estadoImpresionHabilitar = estadoImpresionHabilitar;
    }

    public T046ConfigOrdenDespachoFacade getConfigFacade() {
        return configFacade;
    }

    public void setConfigFacade(T046ConfigOrdenDespachoFacade configFacade) {
        this.configFacade = configFacade;
    }

    public T046ConfigOrdenDespacho getConfigActual() {
        return configActual;
    }

    public void setConfigActual(T046ConfigOrdenDespacho configActual) {
        this.configActual = configActual;
    }

    public List<T046ConfigOrdenDespacho> getListaConfig() {
        return listaConfig;
    }

    public void setListaConfig(List<T046ConfigOrdenDespacho> listaConfig) {
        this.listaConfig = listaConfig;
    }

    public int getContItemComp() {
        return contItemComp;
    }

    public void setContItemComp(int contItemComp) {
        this.contItemComp = contItemComp;
    }

    public int getContItemCompParcial() {
        return contItemCompParcial;
    }

    public void setContItemCompParcial(int contItemCompParcial) {
        this.contItemCompParcial = contItemCompParcial;
    }

    public BigDecimal getTotal_valor_neto_pente() {
        return total_valor_neto_pente;
    }

    public void setTotal_valor_neto_pente(BigDecimal total_valor_neto_pente) {
        this.total_valor_neto_pente = total_valor_neto_pente;
    }

    public String getOpcionFechaReporte() {
        return opcionFechaReporte;
    }

    public void setOpcionFechaReporte(String opcionFechaReporte) {
        this.opcionFechaReporte = opcionFechaReporte;
    }
    
    //navegacion
    public void recargarLista(){   
        opcionFechaReporte = "1";
        estado = null;
        fechaParametro1 = null;
        fechaParametro2 = null;
        listaReporte = new ArrayList<>();
        contItemComp = 0;
        contItemCompParcial = 0;
    }
    public String prepareList() throws SQLException{
        recargarLista();
        return  "/administrador/modOrdenesDespacho/reporte";
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
    public void opcionFechaReporteGenerar() throws SQLException, ParseException{
        switch(opcionFechaReporte){
            case "1":
                recargarConsultarItemsPedidosUnoee();
                //ordenarListaReporte();
                break;
            case "2":
                recargarConsultarItemsPedidosUnoee31Dic();
                //ordenarListaReporte();
                break;
            case "0":
                recargarConsultarItemsPedidosUnoee31Dic();
                recargarConsultarItemsPedidosUnoee();
                //ordenarListaReporte();
                break;
            default:
                break;
        }
    }

    //PARTE NUEVA PARA PROBAR
    //Metodo para ordenar lista de reporte
    public void ordenarListaReporte(){
        //Se raliza ordenamiento de lista
        listaReporte.sort(ascending(getComparator(ITEM_SORT, DESC_EXT1_SORT, DESC_EXT2_SORT)));
    }
    
    public String prepareReporteConsultar() throws SQLException, ParseException{
        listaReporte = new ArrayList<>();
        configActual = new T046ConfigOrdenDespacho();
        configActual = getConfigFacade().findByTope("Tope maximo");
        listaConfig = getConfigFacade().consultarDocumentos("Tipo documento");
        contItemComp = 0;
        contItemCompParcial = 0;
        opcionFechaReporteGenerar();
        return "reporte";
    }
    //Obtener reporte Unoee
    public void recargarConsultarItemsPedidosUnoee()
        throws SQLException, ParseException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");  
        String fecha1 = dateFormat1.format(fechaParametro1); 
        DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");  
        String fecha2 = dateFormat2.format(fechaParametro2); 
        for (T046ConfigOrdenDespacho tipoDoc: listaConfig){
            for (int i = 0; i < estado.length; i++){
                String query = "exec sp_pv_cons_pedido_item 1,'001','"+tipoDoc.getValor()+"','          ','   ','001',0,0,'20190529','20190529',1,'"+fecha1+"','"+fecha2+"',"+estado[i]+",1,10097,'EUREKA_ORDENESDESPACHO_CEDI','          ',0,4300,0,0,1,NULL,NULL,0,0,0";
                try {
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        rs.setFetchSize(20000);
                        reporteActual = new OrdenesDespachoController();
                        reporteActual.setF_cliente_desp_razon_soc(rs.getString("f_cliente_desp_razon_soc")); //Cliente
                        reporteActual.setF_desc_item(rs.getString("f_desc_item")); //Item
                            reporteActual.setF_estado_docto(rs.getString("f_estado_docto")); //Estado documento
                            reporteActual.setF_item(rs.getString("f_item")); //ID ITEM
                            reporteActual.setF_desc_ext_detalle_1(rs.getString("f_desc_ext_detalle_1"));
                            reporteActual.setF_desc_ext_detalle_2(rs.getString("f_desc_ext_detalle_2"));
                            reporteActual.setF_fecha_entrega(rs.getString("f_fecha_entrega").substring(0,10)); //Fecha entrega
                            reporteActual.setF_nrodocto(rs.getString("f_nrodocto")); //Pedido
                            reporteActual.setF_rowid_movto_431(rs.getInt("f_rowid_movto_431"));
                            reporteActual.setF_estado_movto(rs.getString("f_estado_movto")); //Estado movimiento
                            reporteActual.setF_bodega(rs.getString("f_bodega"));//bodega
                            reporteActual.setF_cant_pedida_base(rs.getBigDecimal("f_cant_pedida_base")); //Cant. pedido
                            reporteActual.setF_cant_comp_parc_base(rs.getBigDecimal("f_cant_comp_parc_base")); //Cant. comprometida parcial
                            reporteActual.setF_cant_comprom_base(rs.getBigDecimal("f_cant_comprom_base")); //Cant. comprometida 
                            reporteActual.setF_fecha_creacion_docto(rs.getString("f_fecha_creacion_docto").substring(0,10));//fecha creacion docto
                            reporteActual.setF_um_base(rs.getString("f_um_base"));//unidad medida              
                            reporteActual.setF_cant_pendiente_base(rs.getBigDecimal("f_cant_pendiente_base")); //cant_pendiente
                            reporteActual.setF_cant_existencia(rs.getBigDecimal("f_cant_existencia")); //Existencia
                            reporteActual.setF_cant_oc_op_ins(rs.getBigDecimal("f_cant_oc_op_ins"));
                            if(rs.getString("f_ops").equals("")){
                                reporteActual.setF_ops("NO TIENE");
                            }else{
                                reporteActual.setF_ops(rs.getString("f_ops"));
                            }
                            reporteActual.setF_desc_ciudad(rs.getString("f_desc_ciudad")); //Ciudad
                            reporteActual.setF_direccion1(rs.getString("f_direccion1")); //Ciudad
                            reporteActual.setF_desc_punto_envio(rs.getString("f_desc_punto_envio")); //desc. punto envio
                            reporteActual.setF_punto_envio(rs.getString("f_punto_envio")+" - "+rs.getString("f_cliente_desp_razon_soc")); //punto envio
                            reporteActual.setF_notas_docto(rs.getString("f_notas_docto")); //Notas documento
                            reporteActual.setF_orden_compra(rs.getString("f_orden_compra"));//orden compra
                            reporteActual.setF_fecha_factura(rs.getDate("f_fecha_factura"));//fecha factura
                            reporteActual.setF_codigo_barras_principal(rs.getString("f_codigo_barras_principal"));//Codigo barras
                            reporteActual.setF_dia_maximo_factura(rs.getInt("f_dia_maximo_factura"));//dia maximo factura
                            reporteActual.setF_valor_neto_docto(rs.getBigDecimal("f_valor_neto_docto"));//valor neto documento
                            reporteActual.setF_backorder(rs.getString("f_backorder"));
                            reporteActual.setF_ext_detalle_1(rs.getString("f_ext_detalle_1")); //Tamaño
                            reporteActual.setF_ext_detalle_2(rs.getString("f_ext_detalle_2")); //Color
                            reporteActual.setF_referencia(rs.getString("f_referencia")); //Referencia
                            reporteActual.setF_fecha_modificacion_movto(rs.getDate("f_fecha_modificacion_movto"));//Fecha modificacion movimiento
                            reporteActual.setF_rowid_pv_docto(rs.getInt("f_rowid_pv_docto"));
                            reporteActual.setF_divisor_margen_est(rs.getBigDecimal("f_divisor_margen_est"));
                            //reporteActual.setF_divisor_margen_prom(rs.getBigDecimal("f_divisor_margen_prom"));
                            //reporteActual.setF_utilidad_prom_f(rs.getBigDecimal("f_utilidad_prom_f"));
                            //reporteActual.setF_rowid_movto(rs.getInt("f_rowid_movto_"));
                            //reporteActual.setF_id_clase_docto(rs.getInt("f_id_clase_docto"));
                            //reporteActual.setF_numero(rs.getInt("f_numero"));
                            //reporteActual.setF_rowid(rs.getInt("f_rowid"));
                            reporteActual.setF_01_010(rs.getString("f_01_010"));//Familia
                            reporteActual.setF_01_050(rs.getString("f_01_050"));//Grupo
                            reporteActual.setF_02_005(rs.getString("f_02_005"));//Asesor 
                            reporteActual.setF_02_002(rs.getString("f_02_002"));//Segmetos
                            reporteActual.setF_02_003(rs.getString("f_02_003"));//Subgrupo 
                            if(rs.getString("f_20_00000014_M121").length()>8){
                                reporteActual.setF_20_00000014_M121(rs.getString("f_20_00000014_M121").substring(0,8));//ESTADO os 

                            }

                            reporteActual.setGenerarImpresion("NO");
                            try {
                                reporteActual.setF_valor_neto_pendiente((((reporteActual.getF_valor_neto_docto().divide(reporteActual.getF_cant_pedida_base(),2, RoundingMode.HALF_UP)).multiply(reporteActual.getF_cant_comp_parc_base())).add(((((reporteActual.getF_valor_neto_docto()).divide(reporteActual.getF_cant_pedida_base(),2, RoundingMode.HALF_UP)).multiply(reporteActual.getF_cant_comprom_base()))))).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                            } catch (Exception e) {
                                reporteActual.setF_valor_neto_pendiente(new BigDecimal(0));
                            }
                            contarValoNeto = new BigDecimal(0);
                            evaluarItem();
                            if(!reporteActual.getF_cliente_desp_razon_soc().equals("CORPORACION EKAZIPPER DEL ECUADOR S A")){
                                listaReporte.add(reporteActual); 
                            }
                    }
                } catch (SQLException e ) {
                    System.out.println("error "+e);
                } finally {
                    if (stmt != null) { stmt.close(); }
                }
            }
        }
        evaluarBloqueoImpresion();
        evaluarImpresionCliente();
    }
    
    //Obtener reporte Unoee
    public void recargarConsultarItemsPedidosUnoee31Dic()
        throws SQLException, ParseException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        Calendar cal= Calendar.getInstance();
        String fechaAnioFin= String.valueOf(cal.get(Calendar.YEAR))+"1231";
        for (T046ConfigOrdenDespacho tipoDoc: listaConfig){
            for (int i = 0; i < estado.length; i++){
                String query = "exec sp_pv_cons_pedido_item 1,'001','"+tipoDoc.getValor()+"','          ','   ','001',0,0,'20190529','20190529',1,'"+fechaAnioFin+"','"+fechaAnioFin+"',"+estado[i]+",1,10097,'EUREKA_ORDENESDESPACHO_CEDI','          ',0,4300,0,0,1,NULL,NULL,0,0,0";
                try {
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        rs.setFetchSize(20000);
                        reporteActual = new OrdenesDespachoController();
                        reporteActual.setF_cliente_desp_razon_soc(rs.getString("f_cliente_desp_razon_soc")); //Cliente
                        reporteActual.setF_desc_item(rs.getString("f_desc_item")); //Item
                            reporteActual.setF_estado_docto(rs.getString("f_estado_docto")); //Estado documento
                            reporteActual.setF_item(rs.getString("f_item")); //ID ITEM
                            reporteActual.setF_desc_ext_detalle_1(rs.getString("f_desc_ext_detalle_1"));
                            reporteActual.setF_desc_ext_detalle_2(rs.getString("f_desc_ext_detalle_2"));
                            reporteActual.setF_fecha_entrega(rs.getString("f_fecha_entrega").substring(0,10)); //Fecha entrega
                            reporteActual.setF_nrodocto(rs.getString("f_nrodocto")); //Pedido
                            reporteActual.setF_rowid_movto_431(rs.getInt("f_rowid_movto_431"));
                            reporteActual.setF_estado_movto(rs.getString("f_estado_movto")); //Estado movimiento
                            reporteActual.setF_bodega(rs.getString("f_bodega"));//bodega
                            reporteActual.setF_cant_pedida_base(rs.getBigDecimal("f_cant_pedida_base")); //Cant. pedido
                            reporteActual.setF_cant_comp_parc_base(rs.getBigDecimal("f_cant_comp_parc_base")); //Cant. comprometida parcial
                            reporteActual.setF_cant_comprom_base(rs.getBigDecimal("f_cant_comprom_base")); //Cant. comprometida 
                            reporteActual.setF_fecha_creacion_docto(rs.getString("f_fecha_creacion_docto").substring(0,10));//fecha creacion docto
                            reporteActual.setF_um_base(rs.getString("f_um_base"));//unidad medida              
                            reporteActual.setF_cant_pendiente_base(rs.getBigDecimal("f_cant_pendiente_base")); //cant_pendiente
                            reporteActual.setF_cant_existencia(rs.getBigDecimal("f_cant_existencia")); //Existencia
                            reporteActual.setF_cant_oc_op_ins(rs.getBigDecimal("f_cant_oc_op_ins"));
                            if(rs.getString("f_ops").equals("")){
                                reporteActual.setF_ops("NO TIENE");
                            }else{
                                reporteActual.setF_ops(rs.getString("f_ops"));
                            }
                            reporteActual.setF_desc_ciudad(rs.getString("f_desc_ciudad")); //Ciudad
                            reporteActual.setF_direccion1(rs.getString("f_direccion1")); //Ciudad
                            reporteActual.setF_desc_punto_envio(rs.getString("f_desc_punto_envio")); //desc. punto envio
                            reporteActual.setF_punto_envio(rs.getString("f_punto_envio")+" - "+rs.getString("f_cliente_desp_razon_soc")); //punto envio
                            reporteActual.setF_notas_docto(rs.getString("f_notas_docto")); //Notas documento
                            reporteActual.setF_orden_compra(rs.getString("f_orden_compra"));//orden compra
                            reporteActual.setF_fecha_factura(rs.getDate("f_fecha_factura"));//fecha factura
                            reporteActual.setF_codigo_barras_principal(rs.getString("f_codigo_barras_principal"));//Codigo barras
                            reporteActual.setF_dia_maximo_factura(rs.getInt("f_dia_maximo_factura"));//dia maximo factura
                            reporteActual.setF_valor_neto_docto(rs.getBigDecimal("f_valor_neto_docto"));//valor neto documento
                            reporteActual.setF_backorder(rs.getString("f_backorder"));
                            reporteActual.setF_ext_detalle_1(rs.getString("f_ext_detalle_1")); //Tamaño
                            reporteActual.setF_ext_detalle_2(rs.getString("f_ext_detalle_2")); //Color
                            reporteActual.setF_referencia(rs.getString("f_referencia")); //Referencia
                            reporteActual.setF_fecha_modificacion_movto(rs.getDate("f_fecha_modificacion_movto"));//Fecha modificacion movimiento
                            reporteActual.setF_rowid_pv_docto(rs.getInt("f_rowid_pv_docto"));
                            reporteActual.setF_divisor_margen_est(rs.getBigDecimal("f_divisor_margen_est"));
                            //reporteActual.setF_divisor_margen_prom(rs.getBigDecimal("f_divisor_margen_prom"));
                            //reporteActual.setF_utilidad_prom_f(rs.getBigDecimal("f_utilidad_prom_f"));
                            //reporteActual.setF_rowid_movto(rs.getInt("f_rowid_movto_"));
                            //reporteActual.setF_id_clase_docto(rs.getInt("f_id_clase_docto"));
                            //reporteActual.setF_numero(rs.getInt("f_numero"));
                            //reporteActual.setF_rowid(rs.getInt("f_rowid"));
                            reporteActual.setF_01_010(rs.getString("f_01_010"));//Familia
                            reporteActual.setF_01_050(rs.getString("f_01_050"));//Grupo
                            reporteActual.setF_02_005(rs.getString("f_02_005"));//Asesor 
                            reporteActual.setF_02_002(rs.getString("f_02_002"));//Segmetos
                            reporteActual.setF_02_003(rs.getString("f_02_003"));//Subgrupo 
                            if(rs.getString("f_20_00000014_M121").length()>8){
                                reporteActual.setF_20_00000014_M121(rs.getString("f_20_00000014_M121").substring(0,8));//ESTADO os 

                            }

                            reporteActual.setGenerarImpresion("NO");
                            try {
                                reporteActual.setF_valor_neto_pendiente((((reporteActual.getF_valor_neto_docto().divide(reporteActual.getF_cant_pedida_base(),2, RoundingMode.HALF_UP)).multiply(reporteActual.getF_cant_comp_parc_base())).add(((((reporteActual.getF_valor_neto_docto()).divide(reporteActual.getF_cant_pedida_base(),2, RoundingMode.HALF_UP)).multiply(reporteActual.getF_cant_comprom_base()))))).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                            } catch (Exception e) {
                                reporteActual.setF_valor_neto_pendiente(new BigDecimal(0));
                            }
                            contarValoNeto = new BigDecimal(0);
                            evaluarItem();
                            if(!reporteActual.getF_cliente_desp_razon_soc().equals("CORPORACION EKAZIPPER DEL ECUADOR S A")){
                                listaReporte.add(reporteActual); 
                            }
                    }
                } catch (SQLException e ) {
                    System.out.println("error "+e);
                } finally {
                    if (stmt != null) { stmt.close(); }
                }
            }
        }
        evaluarBloqueoImpresion();
        evaluarImpresionCliente();
    }
    
    
    public void evaluarItem(){
        if(reporteActual.getF_estado_movto().equals("Comprometido") && !reporteActual.getF_backorder().contains("pedido completo")){
            reporteActual.setEvaluacionItem("SI");
            contItemComp++;
        }else if(reporteActual.getF_estado_movto().equals("Comprometido parcial") && !reporteActual.getF_backorder().equals("%pedido completo%")){
            reporteActual.setEvaluacionItem("PARCIAL");
            contItemCompParcial++;
        }else{
            reporteActual.setEvaluacionItem("NO");
        }
    } 
    public void evaluarBloqueoImpresion(){
        List<OrdenesDespachoController> lista = listaReporte;
        String estado;
        contarValoNeto = new BigDecimal(0);
        for(OrdenesDespachoController item: listaReporte){
            estado= "NO";
            for(OrdenesDespachoController item2: lista){
                if(item.getF_cliente_desp_razon_soc().equals(item2.getF_cliente_desp_razon_soc()) && (item.getEvaluacionItem().equals("NO")) && item.getF_desc_ciudad().equals(item2.getF_desc_ciudad())){
                    contarValoNeto = contarValoNeto.add(item2.getF_valor_neto_docto());
                    if(contarValoNeto.compareTo(new BigDecimal(configActual.getNumerico()))<=0){
                        estado = "BLOQUEADO";
                    }else{
                        estado = "NO"; 
                    }
                }
            }/*
            if((item.getF_desc_ciudad().contains("MEDELLIN") || item.getF_desc_ciudad().contains("BOGOTA")) && (item.getEvaluacionItem().equals("SI") || item.getEvaluacionItem().equals("PARCIAL"))){
                estado = "SI";
            }   */ 
            item.setEstadoImpresion(estado);
            contarValoNeto = new BigDecimal(0);
        }
    }
    public void evaluarImpresionCliente() throws SQLException, ParseException{
        List<OrdenesDespachoController> lista = listaReporte;
        String estado;
        contarValoNeto = new BigDecimal(0);
        for(OrdenesDespachoController item: listaReporte){
            for(OrdenesDespachoController item2: lista){
                if(item.getF_cliente_desp_razon_soc().equals(item2.getF_cliente_desp_razon_soc()) && (item.getEvaluacionItem().equals("SI") || item.getEvaluacionItem().equals("PARCIAL")) && item.getF_desc_ciudad().equals(item2.getF_desc_ciudad()) && !item.getEstadoImpresion().equals("BLOQUEADO")){
                    contarValoNeto = contarValoNeto.add(item2.getF_valor_neto_pendiente());
                    if(contarValoNeto.compareTo(new BigDecimal(configActual.getNumerico()))>0){
                        item.setEstadoImpresion("SI");
                        break;
                    }
                }
            }/*
            if((item.getF_desc_ciudad().contains("MEDELLIN") || item.getF_desc_ciudad().contains("BOGOTA")) && (item.getEvaluacionItem().equals("SI") || item.getEvaluacionItem().equals("PARCIAL"))){
                estado = "SI";
            }   */ 
            
            if((obtenerImpresion(item.getF_rowid_movto_431())==null)){
                if(item.getEstadoImpresion().equals("SI")){
                    if(item.getEvaluacionItem().equals("PARCIAL")){
                        item.setGenerarImpresion("PARCIAL");
                    }else{
                        item.setGenerarImpresion("SI");
                    }
                }
            }else if((obtenerImpresion(item.getF_rowid_movto_431()).getEstado().equals("PARCIAL"))){
                if(item.getEstadoImpresion().equals("SI")){
                    if(obtenerImpresion(item.getF_rowid_movto_431()).getCantComp().compareTo(item.getF_cant_comprom_base())<0){
                       if(item.getEvaluacionItem().equals("PARCIAL")){
                            item.setGenerarImpresion("PARCIAL");
                        }else{
                            item.setGenerarImpresion("IMPRESO");
                        } 
                    }else{
                        item.setGenerarImpresion("IMPRESO");
                    }
                }
            }else{
                if(item.getEstadoImpresion().equals("SI")){
                    if((obtenerImpresion(item.getF_rowid_movto_431()).getCantComp().compareTo(item.getF_cant_comprom_base())<0)){
                        if(item.getEvaluacionItem().equals("SI")){
                            item.setGenerarImpresion("SI");
                        }else{
                            item.setGenerarImpresion("IMPRESO");
                        }
                    }else{
                        item.setGenerarImpresion("IMPRESO");
                    }
                }
            }
            contarValoNeto = new BigDecimal(0);
        }
    } 
    //GENERAR IMPRESIÓN
    public String prepareImpresionCompleta() throws PrinterException, IOException, SQLException, ParseException, Exception{
        listaReporteImpresion = new ArrayList<>();
        reporteActual = new OrdenesDespachoController();
        consImpresion = 1;
        fechaImpresion = new Date();
        for(String item: quitarDuplicados() ){
            total_cant_comprom_base = new BigDecimal(0);
            total_valor_neto_pente = new BigDecimal(0);
            for(OrdenesDespachoController item2: listaReporte){
                if(item.equals(item2.getF_punto_envio()+item2.getF_nrodocto()) && (item2.getGenerarImpresion().equals("SI") || item2.getGenerarImpresion().equals("PARCIAL")) && item2.getEstadoImpresion().equals("SI")){
                    total_cant_comprom_base = item2.getF_cant_comprom_base().add(total_cant_comprom_base);
                    total_valor_neto_pente = item2.getF_valor_neto_pendiente().add(total_valor_neto_pente);
                    listaReporteImpresion.add(item2);
                    reporteActual = item2;
                    crearHistorialImpresion(item2.getF_rowid_movto_431(),item2.getF_cant_comprom_base(),item2.getGenerarImpresion());
                    }
            }


            //PARTE NUEVA
            //Se raliza ordenamiento de lista
            listaReporteImpresion.sort(ascending(getComparator(ITEM_SORT, DESC_EXT1_SORT, DESC_EXT2_SORT)));


            if(!listaReporteImpresion.isEmpty()){
                //System.out.println("PRUEBA CLIENTE "+item+ "  cont "+consImpresion+" pedido "+reporteActual.getF_nrodocto());
                createPDF();
                consImpresion ++;
                listaReporteImpresion = new ArrayList<>();
                reporteActual = new OrdenesDespachoController(); 
                imprimir(); 
            }
        }
        clearAllFilters();
        prepareReporteConsultar();
        return "/administrador/modOrdenesDespacho/reporte";
    }
    
    public String prepareImpresionCliente() throws PrinterException, IOException, SQLException, ParseException, Exception{
        listaReporteImpresion = new ArrayList<>();
        reporteActual = new OrdenesDespachoController();
        consImpresion = 1;
        fechaImpresion = new Date();
        for(String item: quitarDuplicados() ){
            total_cant_comprom_base = new BigDecimal(0);
            total_valor_neto_pente = new BigDecimal(0);
            if(item.contains(clienteImprimir)){
                for(OrdenesDespachoController item2: listaReporte){
                    if(item.equals(item2.getF_punto_envio()+item2.getF_nrodocto()) && (item2.getGenerarImpresion().equals("SI") || item2.getGenerarImpresion().equals("PARCIAL"))){
                        total_cant_comprom_base = item2.getF_cant_comprom_base().add(total_cant_comprom_base);
                        total_valor_neto_pente = item2.getF_valor_neto_pendiente().add(total_valor_neto_pente);
                        listaReporteImpresion.add(item2);
                        reporteActual = item2;
                        crearHistorialImpresion(item2.getF_rowid_movto_431(),item2.getF_cant_comprom_base(),item2.getGenerarImpresion());
                        //System.out.println("   PRUEBA ITEM "+item2.getF_item()+" - "+item2.getF_desc_ext_detalle_1()+" - "+item2.getF_desc_ext_detalle_2()+" - "+item2.getF_valor_neto_pendiente()+" - "+item2.getEstadoImpresion());
                    }
                }

                //PARTE NUEVA
                //Se raliza ordenamiento de lista
                listaReporteImpresion.sort(ascending(getComparator(ITEM_SORT, DESC_EXT1_SORT, DESC_EXT2_SORT)));


                if(!listaReporteImpresion.isEmpty()){
                    //System.out.println("PRUEBA CLIENTE "+item+ "  cont "+consImpresion);
                    createPDF();
                    consImpresion ++;
                    listaReporteImpresion = new ArrayList<>();
                    imprimir(); 
                }
            } 
        }
        clearAllFilters();
        prepareReporteConsultar();
        return "/administrador/modOrdenesDespacho/reporte";
    }
    public void generarListaClienteImprimir(){
        listaImpresionCliente = new ArrayList<>();
        for(OrdenesDespachoController item: listaReporte){
            listaImpresionCliente.add(item.getF_punto_envio());
        }
        List<String> lista = new ArrayList<>();
        listaImpresionCliente = new ArrayList<>(new HashSet<>(listaImpresionCliente));
    }
     public List<String> quitarDuplicadosPuntoEnvio(){
        List<String> listaCliente = new ArrayList<>();
        for(OrdenesDespachoController item: listaReporte){
            listaCliente.add(item.getF_punto_envio());
        }
        List<String> lista = new ArrayList<>();
        lista = new ArrayList<>(new HashSet<>(listaCliente));
        return lista;
    }
    public List<String> quitarDuplicados(){
        List<String> listaCliente = new ArrayList<>();
        Collections.sort(listaReporte, (OrdenesDespachoController p1, OrdenesDespachoController p2) -> (p2.getF_cliente_desp_razon_soc()).compareTo(p1.getF_cliente_desp_razon_soc())
        );
        for(OrdenesDespachoController item: listaReporte){
            listaCliente.add(item.getF_punto_envio()+item.getF_nrodocto());
        }
        List<String> lista = new ArrayList<>();
        lista = new ArrayList<>(new HashSet<>(listaCliente));
        Collections.sort(lista);
        return lista;
    }
    public List<String> quitarDuplicadosPuntoEnvioHabilitado(){
        List<String> listaCliente = new ArrayList<>();
        for(OrdenesDespachoController item: listaReporte){
            if(item.getGenerarImpresion().equals("SI")){
                listaCliente.add(item.getF_punto_envio());
            }
        }
        List<String> lista = new ArrayList<>();
        lista = new ArrayList<>(new HashSet<>(listaCliente));
        return lista;
    }
    public List<String> quitarDuplicadosPedidoHabilitado(){
        List<String> pedido = new ArrayList<>();
        for(OrdenesDespachoController item: listaReporte){
            if(item.getGenerarImpresion().equals("SI")){
                pedido.add(item.getF_nrodocto());
            }
        }
        List<String> lista = new ArrayList<>();
        lista = new ArrayList<>(new HashSet<>(pedido));
        return lista;
    }
    public void createPDF(){
        try {
            FacesContext facesContext= FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpSession session= (HttpSession) externalContext.getSession(true);
            String url ="http" + "://" + "192.168.160.154" + ":" + "8080" + "/intranet"+"/faces/administrador/modOrdenesDespacho/formato.xhtml;jsessionid=" + session.getId()+"?pdf=true";
            OutputStream out = new FileOutputStream(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.pdf"));
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);
            renderer.layout();
            renderer.createPDF(out);            
            out.close();
        } catch (DocumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void imprimir() throws PrinterException, IOException, Exception {
        PDDocument document = PDDocument.load(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.pdf")));
        //PrintService myPrintService = findPrintService("Impresión ordenes de despacho");
        //job.setPrintService(myPrintService);
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        job.print();
    }


    private static PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService;
            }
        }
        return null;
    }
    public T045ImpresionOrdenDespacho obtenerImpresion2(int idMoviento){
           return getImpresionFacade().consultaUltimaImpresion(idMoviento);
    }
    //obtener historial de impresion
    public T045ImpresionOrdenDespacho obtenerImpresion(int idMovimiento)
    throws SQLException, ParseException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        String query ="SELECT TOP 1 id_impresion, cant_comp ,cant_comp_parcial ,estado ,id_movimiento FROM t045_impresion_orden_despacho WHERE id_movimiento = "+idMovimiento+" ORDER BY id_impresion ASC";
        Statement stmt = null;
        int valor = 0;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                valor = 1;
                impresionActual= new T045ImpresionOrdenDespacho();  
                impresionActual.setIdImpresion(rs.getInt("id_impresion"));
                impresionActual.setCantComp(rs.getBigDecimal("cant_comp"));
                impresionActual.setCantCompParcial(rs.getBigDecimal("cant_comp_parcial"));
                impresionActual.setEstado(rs.getString("estado"));
                impresionActual.setIdMovimiento(rs.getInt("id_movimiento"));
                System.out.println("prueba "+impresionActual);
                break;
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        
        if(valor==0){
            impresionActual=null;
            System.out.println("prueba sin "+impresionActual);
        }
        return impresionActual;
    }
    
    public void crearHistorialImpresion(int idMovimiento, BigDecimal cantComp, String estado ){
        impresionActual = new T045ImpresionOrdenDespacho();
        impresionActual.setIdMovimiento(idMovimiento);
        impresionActual.setCantComp(cantComp);
        impresionActual.setEstado(estado);
        impresionActual.setFechaImpresion(new Date());
        impresionActual.setUsuario(usuarioActual.getUsername());
        impresionFacade.create(impresionActual);
    }
    //cargar detalle
    public void llenarHistorialImpresion(SelectEvent event){
        estadoImpresionHabilitar = reporteActual.getGenerarImpresion();
        opcionImprimirCliente = "i";
        opcionImprimirCondicion = reporteActual.getEstadoImpresion();
        listaImpresion = getImpresionFacade().consultaHistorialImpresion(reporteActual.getF_rowid_movto_431());
    }
    //Cambiar estado impresion
    public void cambiarEstadoImpresion(){
        if(opcionImprimirCondicion.equals("t")){
            if(opcionImprimirCliente.equals("i")){
                reporteActual.setGenerarImpresion(estadoImpresionHabilitar);
            }else if(opcionImprimirCliente.equals("c")){
                for(OrdenesDespachoController item: listaReporte){
                    if(item.getF_punto_envio().equals(reporteActual.getF_punto_envio())){
                        item.setGenerarImpresion(estadoImpresionHabilitar);
                    }
                }
            }else if(opcionImprimirCliente.equals("t")){
                for(OrdenesDespachoController item: listaReporte){
                    item.setGenerarImpresion(estadoImpresionHabilitar);
                }
            }  
        }else{
            if(opcionImprimirCliente.equals("i") && reporteActual.getEstadoImpresion().equals(opcionImprimirCondicion)){
                reporteActual.setGenerarImpresion(estadoImpresionHabilitar);
            }else if(opcionImprimirCliente.equals("c")){
                for(OrdenesDespachoController item: listaReporte){
                    if(item.getF_punto_envio().equals(reporteActual.getF_punto_envio()) && reporteActual.getEstadoImpresion().equals(opcionImprimirCondicion)){
                        item.setGenerarImpresion(estadoImpresionHabilitar);
                    }
                }
            }else if(opcionImprimirCliente.equals("t")){
                for(OrdenesDespachoController item: listaReporte){
                    if(reporteActual.getEstadoImpresion().equals(opcionImprimirCondicion)){
                        item.setGenerarImpresion(estadoImpresionHabilitar);
                    }
                }
            }
        }
    }
    
    public String obtenerAnioActual(){
        Calendar cal= Calendar.getInstance();
        String fechaAnioFin= String.valueOf(cal.get(Calendar.YEAR))+"1231";
        return fechaAnioFin;
    }
    
    public void clearAllFilters() {
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form-reporte:reporteItemTable");
    }
    
    public void enviarXlsOrdenesDespacho(){
        try {
            recargarLista();
            listaReporte = new ArrayList<>();
            obtenerTopeMaximoFoto();
            obtenerTipoDocumentoFoto();
            contItemComp = 0;
            contItemCompParcial = 0;
            recargarConsultarItemsPedidosUnoeeFoto();
            escribirXlsInvDisponible(new File("text1.xlsx").getAbsolutePath());
            sendMailReporte();
        } catch (SQLException ex) {
            Logger.getLogger(OrdenesDespachoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(OrdenesDespachoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrdenesDespachoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(OrdenesDespachoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int obtenerTopeMaximoFoto()
        throws SQLException, ParseException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        String query ="SELECT id_configuracion, numerico FROM t046_config_orden_despacho WHERE tipo = 'Tope maximo'";
        Statement stmt = null;
        int valor =0 ;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                configActual = new T046ConfigOrdenDespacho();
                configActual.setIdConfiguracion(rs.getInt("id_configuracion"));
                configActual.setNumerico(rs.getInt("numerico"));
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return configActual.getNumerico();
    }
    public String obtenerCorreoEnvioFoto()
        throws SQLException, ParseException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        String query ="SELECT id_configuracion, valor FROM t046_config_orden_despacho WHERE tipo = 'Correo envio reporte'";
        Statement stmt = null;
        String correo ="";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                correo = rs.getString("valor");
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return correo;
    }
     
     public void obtenerTipoDocumentoFoto()
        throws SQLException, ParseException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        String query ="SELECT id_configuracion, valor FROM t046_config_orden_despacho WHERE tipo = 'Tipo documento'";
        Statement stmt = null;
        listaConfig = new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                T046ConfigOrdenDespacho config= new T046ConfigOrdenDespacho();
                config.setIdConfiguracion(rs.getInt("id_configuracion"));
                config.setValor(rs.getString("valor"));
                listaConfig.add(config);
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void recargarConsultarItemsPedidosUnoeeFoto()
        throws SQLException, ParseException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");  
        String fecha2 = dateFormat2.format(new Date()); 
        String[] estado={"8", "12"};
        for (T046ConfigOrdenDespacho tipoDoc: listaConfig){
            for (int i = 0; i < estado.length; i++){
                String query = "exec sp_pv_cons_pedido_item 1,'001','"+tipoDoc.getValor()+"','          ','   ','001',0,0,'20190529','20190529',1,'20110101','"+fecha2+"',"+estado[i]+",1,10097,'EUREKA_ORDENESDESPACHO_CEDI','          ',0,4300,0,0,1,NULL,NULL,0,0,0";
                try {
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        rs.setFetchSize(20000);
                        reporteActual = new OrdenesDespachoController();
                        reporteActual.setF_cliente_desp_razon_soc(rs.getString("f_cliente_desp_razon_soc")); //Cliente
                        reporteActual.setF_desc_item(rs.getString("f_desc_item")); //Item
                            reporteActual.setF_estado_docto(rs.getString("f_estado_docto")); //Estado documento
                            reporteActual.setF_item(rs.getString("f_item")); //ID ITEM
                            reporteActual.setF_desc_ext_detalle_1(rs.getString("f_desc_ext_detalle_1"));
                            reporteActual.setF_desc_ext_detalle_2(rs.getString("f_desc_ext_detalle_2"));
                            reporteActual.setF_fecha_entrega(rs.getString("f_fecha_entrega").substring(0,10)); //Fecha entrega
                            reporteActual.setF_nrodocto(rs.getString("f_nrodocto")); //Pedido
                            reporteActual.setF_rowid_movto_431(rs.getInt("f_rowid_movto_431"));
                            reporteActual.setF_estado_movto(rs.getString("f_estado_movto")); //Estado movimiento
                            reporteActual.setF_bodega(rs.getString("f_bodega"));//bodega
                            reporteActual.setF_cant_pedida_base(rs.getBigDecimal("f_cant_pedida_base")); //Cant. pedido
                            reporteActual.setF_cant_comp_parc_base(rs.getBigDecimal("f_cant_comp_parc_base")); //Cant. comprometida parcial
                            reporteActual.setF_cant_comprom_base(rs.getBigDecimal("f_cant_comprom_base")); //Cant. comprometida 
                            reporteActual.setF_fecha_creacion_docto(rs.getString("f_fecha_creacion_docto").substring(0,10));//fecha creacion docto
                            reporteActual.setF_um_base(rs.getString("f_um_base"));//unidad medida              
                            reporteActual.setF_cant_pendiente_base(rs.getBigDecimal("f_cant_pendiente_base")); //cant_pendiente
                            reporteActual.setF_cant_existencia(rs.getBigDecimal("f_cant_existencia")); //Existencia
                            reporteActual.setF_cant_oc_op_ins(rs.getBigDecimal("f_cant_oc_op_ins"));
                            if(rs.getString("f_ops").equals("")){
                                reporteActual.setF_ops("NO TIENE");
                            }else{
                                reporteActual.setF_ops(rs.getString("f_ops"));
                            }
                            reporteActual.setF_desc_ciudad(rs.getString("f_desc_ciudad")); //Ciudad
                            reporteActual.setF_direccion1(rs.getString("f_direccion1")); //Ciudad
                            reporteActual.setF_desc_punto_envio(rs.getString("f_desc_punto_envio")); //desc. punto envio
                            reporteActual.setF_punto_envio(rs.getString("f_punto_envio")+" - "+rs.getString("f_cliente_desp_razon_soc")); //punto envio
                            reporteActual.setF_notas_docto(rs.getString("f_notas_docto")); //Notas documento
                            reporteActual.setF_orden_compra(rs.getString("f_orden_compra"));//orden compra
                            reporteActual.setF_fecha_factura(rs.getDate("f_fecha_factura"));//fecha factura
                            reporteActual.setF_codigo_barras_principal(rs.getString("f_codigo_barras_principal"));//Codigo barras
                            reporteActual.setF_dia_maximo_factura(rs.getInt("f_dia_maximo_factura"));//dia maximo factura
                            reporteActual.setF_valor_neto_docto(rs.getBigDecimal("f_valor_neto_docto"));//valor neto documento
                            reporteActual.setF_backorder(rs.getString("f_backorder"));
                            reporteActual.setF_ext_detalle_1(rs.getString("f_ext_detalle_1")); //Tamaño
                            reporteActual.setF_ext_detalle_2(rs.getString("f_ext_detalle_2")); //Color
                            reporteActual.setF_referencia(rs.getString("f_referencia")); //Referencia
                            reporteActual.setF_fecha_modificacion_movto(rs.getDate("f_fecha_modificacion_movto"));//Fecha modificacion movimiento
                            reporteActual.setF_rowid_pv_docto(rs.getInt("f_rowid_pv_docto"));
                            reporteActual.setF_divisor_margen_est(rs.getBigDecimal("f_divisor_margen_est"));
                            //reporteActual.setF_divisor_margen_prom(rs.getBigDecimal("f_divisor_margen_prom"));
                            //reporteActual.setF_utilidad_prom_f(rs.getBigDecimal("f_utilidad_prom_f"));
                            //reporteActual.setF_rowid_movto(rs.getInt("f_rowid_movto_"));
                            //reporteActual.setF_id_clase_docto(rs.getInt("f_id_clase_docto"));
                            //reporteActual.setF_numero(rs.getInt("f_numero"));
                            //reporteActual.setF_rowid(rs.getInt("f_rowid"));
                            reporteActual.setF_01_010(rs.getString("f_01_010"));//Familia
                            reporteActual.setF_01_050(rs.getString("f_01_050"));//Grupo
                            reporteActual.setF_02_005(rs.getString("f_02_005"));//Asesor 
                            reporteActual.setF_02_002(rs.getString("f_02_002"));//Segmetos
                            reporteActual.setF_02_003(rs.getString("f_02_003"));//Subgrupo 
                            if(rs.getString("f_20_00000014_M121").length()>8){
                                reporteActual.setF_20_00000014_M121(rs.getString("f_20_00000014_M121").substring(0,8));//ESTADO os 

                            }

                            reporteActual.setGenerarImpresion("NO");
                            try {
                                reporteActual.setF_valor_neto_pendiente((((reporteActual.getF_valor_neto_docto().divide(reporteActual.getF_cant_pedida_base(),2, RoundingMode.HALF_UP)).multiply(reporteActual.getF_cant_comp_parc_base())).add(((((reporteActual.getF_valor_neto_docto()).divide(reporteActual.getF_cant_pedida_base(),2, RoundingMode.HALF_UP)).multiply(reporteActual.getF_cant_comprom_base()))))).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                            } catch (Exception e) {
                                reporteActual.setF_valor_neto_pendiente(new BigDecimal(0));
                            }
                            contarValoNeto = new BigDecimal(0);
                            evaluarItem();
                            if(!reporteActual.getF_cliente_desp_razon_soc().equals("CORPORACION EKAZIPPER DEL ECUADOR S A")){
                                listaReporte.add(reporteActual); 
                            }
                    }
                } catch (SQLException e ) {
                    System.out.println("error "+e);
                } finally {
                    if (stmt != null) { stmt.close(); }
                }
            }
        }
        evaluarBloqueoImpresion();
        evaluarImpresionCliente();
    }
    public void sendMailReporte() throws FileNotFoundException{
        try {
            emailController = new EmailController();
            String asunto = "Reporte de ordenes de despacho CEDI.";
            String cuerpo = "Se adjunta el reporte de ordenes de despacho con los siguientes datos:" +
                            "\n" +
                            "\n" +
                            "Total registros:" + listaReporte.size()+"\n" +
                            "Total clientes/Punto envio: "+ quitarDuplicadosPuntoEnvio().size()+"\n" +
                            "Total clientes habilitados impresión: " +quitarDuplicadosPuntoEnvioHabilitado().size() +" \n" +
                            "Total pedidos habilitados impresion: " +quitarDuplicadosPedidoHabilitado().size() + "\n" +
                            "Total ítems comprometidos: " +contItemComp +" \n" +
                            "Total ítems comprometidos parcial: " +contItemCompParcial+" "+
                            "\n" +
                            "\n" +
                            "Nota: Este es un mensaje automático, por favor no responder." +
                            "\n" +
                            "\n" +
                            "Departamento de TI"+
                            "\n" +
                            "\n";
            String adjunto = new File("text1.xlsx").getAbsolutePath();
            String nombreAdjunto = "reporte.xlsx";
            emailController.sendMailAdjunto(obtenerCorreoEnvioFoto(), asunto, cuerpo, adjunto, nombreAdjunto);
        } catch (Exception e) {
        }
            
          
     }
    public void escribirXlsInvDisponible(String nombre) throws FileNotFoundException, IOException,Exception{
        try {
            String rutaArchivoExcel = nombre;
            FileInputStream inputStream = new FileInputStream(new File(rutaArchivoExcel));
            Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
            CreationHelper createHelper = workbook.getCreationHelper();
            // Create a Sheet
            Sheet sheet = workbook.createSheet("Items");
            // Create a Font for styling header cells
            Font headerFont = workbook.createFont();
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLUE.getIndex());
            headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

            // Create a CellStyle with the font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setAlignment((short) 2);

            CellStyle alinearPrecio = workbook.createCellStyle();
            alinearPrecio.setAlignment((short) 3);
            
            CellStyle cellStyleDate = workbook.createCellStyle();

            cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
            // Create a Row
            Row titulo = sheet.createRow(0);
            Cell cellTitulo = titulo.createCell(0);
            cellTitulo.setCellValue("Eka Corporación S.A - FOTO ORDENES DE DESPACHOS");
            cellTitulo.setCellStyle(headerCellStyle);
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
            Row headerRow = sheet.createRow(1);

            // Create cells
            for(int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }
            // Create Other rows and cells with employees data
            int rowNum = 2;
            for(OrdenesDespachoController item: listaReporte) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(item.getEstadoImpresion());
                row.createCell(1).setCellValue(item.getEvaluacionItem());
                row.createCell(2).setCellValue(item.getGenerarImpresion());
                row.createCell(3).setCellValue(item.getF_cliente_desp_razon_soc());
                row.createCell(4).setCellValue(item.getF_item());
                row.createCell(5).setCellValue(item.getF_desc_item());
                row.createCell(6).setCellValue(item.getF_desc_ext_detalle_1());
                row.createCell(7).setCellValue(item.getF_desc_ext_detalle_2());
                row.createCell(8).setCellValue(item.getF_fecha_entrega());
                row.createCell(9).setCellValue(item.getF_nrodocto());
                row.createCell(10).setCellValue(item.getF_estado_movto());
                row.createCell(11).setCellValue(item.getF_estado_docto());
                try {
                    row.createCell(12).setCellValue(item.getF_cant_pedida_base().doubleValue());
                }catch (Exception e) {
                    row.createCell(12).setCellValue(0);
                }
                try {
                    row.createCell(13).setCellValue(item.getF_cant_comprom_base().doubleValue());
                }catch (Exception e) {
                    row.createCell(13).setCellValue(0);
                }
                try {
                    row.createCell(14).setCellValue(item.getF_cant_comp_parc_base().doubleValue());
                }catch (Exception e) {
                    row.createCell(14).setCellValue(0);
                }
                try {
                    row.createCell(15).setCellValue(item.getF_valor_neto_pendiente().doubleValue());
                }catch (Exception e) {
                    row.createCell(15).setCellValue(0);
                }
                try {
                    row.createCell(16).setCellValue(item.getF_valor_neto_docto().doubleValue());
                }catch (Exception e) {
                    row.createCell(16).setCellValue(0);
                }
                row.createCell(17).setCellValue(item.getF_20_00000014_M121());
                row.createCell(18).setCellValue(item.getF_bodega());
                row.createCell(19).setCellValue(item.getF_01_010());
                row.createCell(20).setCellValue(item.getF_fecha_creacion_docto());
                row.createCell(21).setCellValue(item.getF_um_base());
                try {
                    row.createCell(22).setCellValue(item.getF_cant_pendiente_base().doubleValue());
                } catch (Exception e) {
                    row.createCell(22).setCellValue(0);
                }
                try {
                    row.createCell(23).setCellValue(item.getF_cant_existencia().doubleValue());
                } catch (Exception e) {
                    row.createCell(23).setCellValue(0);
                }
                try {
                    row.createCell(24).setCellValue(item.getF_cant_oc_op_ins().doubleValue());
                } catch (Exception e) {
                    row.createCell(24).setCellValue(0);
                }
                row.createCell(25).setCellValue(item.getF_ops());
                row.createCell(26).setCellValue(item.getF_punto_envio());
                row.createCell(27).setCellValue(item.getF_desc_punto_envio());
                row.createCell(28).setCellValue(item.getF_desc_ciudad());
                row.createCell(29).setCellValue(item.getF_direccion1());
                row.createCell(30).setCellValue(item.getF_notas_docto());
                row.createCell(31).setCellValue(item.getF_02_002());
                row.createCell(32).setCellValue(item.getF_orden_compra());
                if(item.getF_fecha_factura() == null){
                    row.createCell(33).setCellValue("");
                }else{
                    DateFormat fechaFacturaFormat = new SimpleDateFormat("yyyy-MM-dd");  
                    String fecha1 = fechaFacturaFormat.format(item.getF_fecha_factura()); 
                    row.createCell(33).setCellValue(fecha1);
                }
                row.createCell(34).setCellValue(item.getF_02_003());
                row.createCell(35).setCellValue(item.getF_02_005());
                row.createCell(36).setCellValue(item.getF_codigo_barras_principal());
                row.createCell(37).setCellValue(item.getF_dia_maximo_factura());
                row.createCell(38).setCellValue(item.getF_backorder());
                row.createCell(39).setCellValue(item.getF_01_050());
                row.createCell(40).setCellValue(item.getF_ext_detalle_1());
                row.createCell(41).setCellValue(item.getF_ext_detalle_2());
                row.createCell(42).setCellValue(item.getF_referencia());
                if(item.getF_fecha_modificacion_movto() == null){
                    row.createCell(43).setCellValue("");
                }else{
                    DateFormat fechaFacturaFormat = new SimpleDateFormat("yyyy-MM-dd");  
                    String fecha2 = fechaFacturaFormat.format(item.getF_fecha_modificacion_movto()); 
                    row.createCell(43).setCellValue(fecha2);
                }
                row.createCell(44).setCellValue(item.getF_20_00000014_M121());
                row.createCell(45).setCellValue(item.getF_rowid_movto_431());
            }
            // Resize all columns to fit the content size
            for(int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            try (FileOutputStream fileOut = new FileOutputStream(rutaArchivoExcel)) {
                workbook.write(fileOut);
            }
        }  catch (FileNotFoundException ex) {
            System.out.println("error "+ex);
        } 
    }
}
