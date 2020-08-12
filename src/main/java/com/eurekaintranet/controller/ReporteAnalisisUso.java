/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T004Documento;
import com.eurekaintranet.entities.T006TipoDocumento;
import com.eurekaintranet.entities.T007Contenido;
import com.eurekaintranet.entities.T010EstadoItem;
import com.eurekaintranet.entities.T012Lecturas;
import com.eurekaintranet.entities.T013CreacionItem;
import com.eurekaintranet.entities.T015VolanteNomina;
import com.eurekaintranet.entities.T018BuzonSugerencia;
import com.eurekaintranet.sessions.T004DocumentoFacade;
import com.eurekaintranet.sessions.T007ContenidoFacade;
import com.eurekaintranet.sessions.T010EstadoItemFacade;
import com.eurekaintranet.sessions.T012LecturasFacade;
import com.eurekaintranet.sessions.T013CreacionItemFacade;
import com.eurekaintranet.sessions.T015VolanteNominaFacade;
import com.eurekaintranet.sessions.T018BuzonSugerenciaFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
import javax.ejb.EJB;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.chart.MeterGaugeChartModel;

/**
 *
 * @author Lccabalr
 */
@Named(value = "reporteAnalisisUso")
@SessionScoped
public class ReporteAnalisisUso implements Serializable {
    @EJB 
    private T004DocumentoFacade documentosFacade;
    private T004Documento documentosActual;
    private List<T004Documento> listaDocumentos;
    
    @EJB 
    private T007ContenidoFacade contenidoFacade;
    private T007Contenido contenidoActual;
    private List<T007Contenido> listaContenido;
    
    
    @EJB 
    private T010EstadoItemFacade estadoItemFacade;
    private T010EstadoItem estadoItemActual;
    private List<T010EstadoItem> listaEstadoItem;
    
    @EJB 
    private T012LecturasFacade lecturaFacade;
    private T012Lecturas lecturaActual;
    private List<T012Lecturas> listaLecturaActual;
    
    @EJB 
    private T013CreacionItemFacade creacionItemFacade;
    private T013CreacionItem creacionItemActual;
    private List<T013CreacionItem> listaCreacionItem;
    
    @EJB 
    private T018BuzonSugerenciaFacade buzonSugerenciaFacade;
    private T018BuzonSugerencia buzonSugerenciaActual;
    private List<T018BuzonSugerencia> listaBuzonSugerencia;
    
    @EJB 
    private T015VolanteNominaFacade volanteNominaFacade;
    private T015VolanteNomina volanteNominaActual;
    private List<T015VolanteNomina> listaVolanteNomina;
    
    private int pedidosUnoEE = 0;
    private int pedidosPortalWeb = 0;
    
    private int facturaUnoEE = 0;
    private int facturaNotificacionDespacho = 0;
    
    //Gestor documental   
    private BarChartModel barModel1 = new BarChartModel();
    private LineChartModel dateModel1 = new LineChartModel();
    
    
    //Gestor contendio  
    private MeterGaugeChartModel meterGaugeModel2;
    private LineChartModel dateModel2 = new LineChartModel();
    
    //Toc estado item  
    private MeterGaugeChartModel meterGaugeModel3;
    private LineChartModel dateModel3 = new LineChartModel();
    
    //Lecturas kos
    private MeterGaugeChartModel meterGaugeModel4;
    private LineChartModel dateModel4 = new LineChartModel();
    
    //Creacion de items
    private MeterGaugeChartModel meterGaugeModel5;
    private LineChartModel dateModel5 = new LineChartModel();
    
    //Buzon de sugerencias
    private MeterGaugeChartModel meterGaugeModel6;
    private LineChartModel dateModel6 = new LineChartModel();
    
    //Comprobantes de pago
    private MeterGaugeChartModel meterGaugeModel7;
    private LineChartModel dateModel7 = new LineChartModel();
    
    //Pedidos web
    private MeterGaugeChartModel meterGaugeModel8;
    private LineChartModel dateModel8 = new LineChartModel();
    
    //Notificación de despacho
    private MeterGaugeChartModel meterGaugeModel9;
    private LineChartModel dateModel9 = new LineChartModel();
    
    public ReporteAnalisisUso() {
    }

    public T004DocumentoFacade getDocumentosFacade() {
        return documentosFacade;
    }

    public void setDocumentosFacade(T004DocumentoFacade documentosFacade) {
        this.documentosFacade = documentosFacade;
    }

    public T004Documento getDocumentosActual() {
        return documentosActual;
    }

    public void setDocumentosActual(T004Documento documentosActual) {
        this.documentosActual = documentosActual;
    }

    public List<T004Documento> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<T004Documento> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public BarChartModel getBarModel1() {
        return barModel1;
    }

    public void setBarModel1(BarChartModel barModel1) {
        this.barModel1 = barModel1;
    }

    public LineChartModel getDateModel() {
        return dateModel1;
    }

    public void setDateModel(LineChartModel dateModel1) {
        this.dateModel1 = dateModel1;
    }

    public LineChartModel getDateModel1() {
        return dateModel1;
    }

    public void setDateModel1(LineChartModel dateModel1) {
        this.dateModel1 = dateModel1;
    }

    public LineChartModel getDateModel2() {
        return dateModel2;
    }

    public void setDateModel2(LineChartModel dateModel2) {
        this.dateModel2 = dateModel2;
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
        return listaEstadoItem;
    }

    public void setListaEstadoItem(List<T010EstadoItem> listaEstadoItem) {
        this.listaEstadoItem = listaEstadoItem;
    }

    public MeterGaugeChartModel getMeterGaugeModel3() {
        return meterGaugeModel3;
    }

    public void setMeterGaugeModel3(MeterGaugeChartModel meterGaugeModel3) {
        this.meterGaugeModel3 = meterGaugeModel3;
    }

    public LineChartModel getDateModel3() {
        return dateModel3;
    }

    public void setDateModel3(LineChartModel dateModel3) {
        this.dateModel3 = dateModel3;
    }

    public T012LecturasFacade getLecturaFacade() {
        return lecturaFacade;
    }

    public void setLecturaFacade(T012LecturasFacade lecturaFacade) {
        this.lecturaFacade = lecturaFacade;
    }

    public T012Lecturas getLecturaActual() {
        return lecturaActual;
    }

    public void setLecturaActual(T012Lecturas lecturaActual) {
        this.lecturaActual = lecturaActual;
    }

    public List<T012Lecturas> getListaLecturaActual() {
        return listaLecturaActual;
    }

    public void setListaLecturaActual(List<T012Lecturas> listaLecturaActual) {
        this.listaLecturaActual = listaLecturaActual;
    }

    public MeterGaugeChartModel getMeterGaugeModel4() {
        return meterGaugeModel4;
    }

    public void setMeterGaugeModel4(MeterGaugeChartModel meterGaugeModel4) {
        this.meterGaugeModel4 = meterGaugeModel4;
    }

    public LineChartModel getDateModel4() {
        return dateModel4;
    }

    public void setDateModel4(LineChartModel dateModel4) {
        this.dateModel4 = dateModel4;
    }

    
    
    public T007ContenidoFacade getContenidoFacade() {
        return contenidoFacade;
    }

    public void setContenidoFacade(T007ContenidoFacade contenidoFacade) {
        this.contenidoFacade = contenidoFacade;
    }

    public T007Contenido getContenidoActual() {
        return contenidoActual;
    }

    public void setContenidoActual(T007Contenido contenidoActual) {
        this.contenidoActual = contenidoActual;
    }

    public List<T007Contenido> getListaContenido() {
        return listaContenido;
    }

    public void setListaContenido(List<T007Contenido> listaContenido) {
        this.listaContenido = listaContenido;
    }

    public MeterGaugeChartModel getMeterGaugeModel2() {
        return meterGaugeModel2;
    }

    public void setMeterGaugeModel2(MeterGaugeChartModel meterGaugeModel2) {
        this.meterGaugeModel2 = meterGaugeModel2;
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

    public List<T013CreacionItem> getListaCreacionItem() {
        return listaCreacionItem;
    }

    public void setListaCreacionItem(List<T013CreacionItem> listaCreacionItem) {
        this.listaCreacionItem = listaCreacionItem;
    }

    public MeterGaugeChartModel getMeterGaugeModel5() {
        return meterGaugeModel5;
    }

    public void setMeterGaugeModel5(MeterGaugeChartModel meterGaugeModel5) {
        this.meterGaugeModel5 = meterGaugeModel5;
    }

    public LineChartModel getDateModel5() {
        return dateModel5;
    }

    public void setDateModel5(LineChartModel dateModel5) {
        this.dateModel5 = dateModel5;
    }

    public T018BuzonSugerenciaFacade getBuzonSugerenciaFacade() {
        return buzonSugerenciaFacade;
    }

    public void setBuzonSugerenciaFacade(T018BuzonSugerenciaFacade buzonSugerenciaFacade) {
        this.buzonSugerenciaFacade = buzonSugerenciaFacade;
    }

    public T018BuzonSugerencia getBuzonSugerenciaActual() {
        return buzonSugerenciaActual;
    }

    public void setBuzonSugerenciaActual(T018BuzonSugerencia buzonSugerenciaActual) {
        this.buzonSugerenciaActual = buzonSugerenciaActual;
    }

    public List<T018BuzonSugerencia> getListaBuzonSugerencia() {
        return listaBuzonSugerencia;
    }

    public void setListaBuzonSugerencia(List<T018BuzonSugerencia> listaBuzonSugerencia) {
        this.listaBuzonSugerencia = listaBuzonSugerencia;
    }

    public MeterGaugeChartModel getMeterGaugeModel6() {
        return meterGaugeModel6;
    }

    public void setMeterGaugeModel6(MeterGaugeChartModel meterGaugeModel6) {
        this.meterGaugeModel6 = meterGaugeModel6;
    }

    public LineChartModel getDateModel6() {
        return dateModel6;
    }

    public void setDateModel6(LineChartModel dateModel6) {
        this.dateModel6 = dateModel6;
    }

    public T015VolanteNominaFacade getVolanteNominaFacade() {
        return volanteNominaFacade;
    }

    public void setVolanteNominaFacade(T015VolanteNominaFacade volanteNominaFacade) {
        this.volanteNominaFacade = volanteNominaFacade;
    }

    public T015VolanteNomina getVolanteNominaActual() {
        return volanteNominaActual;
    }

    public void setVolanteNominaActual(T015VolanteNomina volanteNominaActual) {
        this.volanteNominaActual = volanteNominaActual;
    }

    public List<T015VolanteNomina> getListaVolanteNomina() {
        return listaVolanteNomina;
    }

    public void setListaVolanteNomina(List<T015VolanteNomina> listaVolanteNomina) {
        this.listaVolanteNomina = listaVolanteNomina;
    }

    public MeterGaugeChartModel getMeterGaugeModel7() {
        return meterGaugeModel7;
    }

    public void setMeterGaugeModel7(MeterGaugeChartModel meterGaugeModel7) {
        this.meterGaugeModel7 = meterGaugeModel7;
    }

    public LineChartModel getDateModel7() {
        return dateModel7;
    }

    public void setDateModel7(LineChartModel dateModel7) {
        this.dateModel7 = dateModel7;
    }

    public int getPedidosUnoEE() {
        return pedidosUnoEE;
    }

    public void setPedidosUnoEE(int pedidosUnoEE) {
        this.pedidosUnoEE = pedidosUnoEE;
    }

    public int getPedidosPortalWeb() {
        return pedidosPortalWeb;
    }

    public void setPedidosPortalWeb(int pedidosPortalWeb) {
        this.pedidosPortalWeb = pedidosPortalWeb;
    }

    public MeterGaugeChartModel getMeterGaugeModel8() {
        return meterGaugeModel8;
    }

    public void setMeterGaugeModel8(MeterGaugeChartModel meterGaugeModel8) {
        this.meterGaugeModel8 = meterGaugeModel8;
    }

    public LineChartModel getDateModel8() {
        return dateModel8;
    }

    public void setDateModel8(LineChartModel dateModel8) {
        this.dateModel8 = dateModel8;
    }

    public MeterGaugeChartModel getMeterGaugeModel9() {
        return meterGaugeModel9;
    }

    public void setMeterGaugeModel9(MeterGaugeChartModel meterGaugeModel9) {
        this.meterGaugeModel9 = meterGaugeModel9;
    }

    public LineChartModel getDateModel9() {
        return dateModel9;
    }

    public void setDateModel9(LineChartModel dateModel9) {
        this.dateModel9 = dateModel9;
    }

    public int getFacturaUnoEE() {
        return facturaUnoEE;
    }

    public void setFacturaUnoEE(int facturaUnoEE) {
        this.facturaUnoEE = facturaUnoEE;
    }

    public int getFacturaNotificacionDespacho() {
        return facturaNotificacionDespacho;
    }

    public void setFacturaNotificacionDespacho(int facturaNotificacionDespacho) {
        this.facturaNotificacionDespacho = facturaNotificacionDespacho;
    }
    
    
    public String prepareAnalisisUso() throws SQLException{
        createDateModel1();
        createBarModel1();
        createDateModel2();
        createMeterGaugeModels2();
        createMeterGaugeModels3();
        createMeterGaugeModels4();
        createMeterGaugeModels5();
        createDateModel6();
        createMeterGaugeModels7();
        consultarPedidosUnoee();
        consultarPedidosPortalWeb();
        createMeterGaugeModels8();
        consultarFacturasUnoEE();
        consultarNotificacionDespacho();
        createMeterGaugeModels9();
        listaDocumentos = getDocumentosFacade().consultaTopDocumento();
        return "/usuario/modReportes/indicadoresUsoServicios/reporte";
    }
    
    // DOCUMENTOS
    private void createDateModel1() {
        dateModel1 = new LineChartModel();
        int cont = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String fecha1 = "";
        String fecha2 = "";
        LineChartSeries uno = new LineChartSeries();
        uno.setLabel("Documentos");
        for (T004Documento items : getDocumentosFacade().documentosTipoDocumentoUser(new T006TipoDocumento("FTO"))) { 
            fecha1 = dateFormat1.format(items.getFecha());
            for (T004Documento items2 : getDocumentosFacade().documentosTipoDocumentoUser(new T006TipoDocumento("FTO"))) { 
                fecha2 = dateFormat2.format(items2.getFecha());
                if(fecha1.equals(fecha2)){
                    cont++;
                } 
            }
            uno.set(fecha1, cont);
            cont=0;
        }
        cont = 0;
        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        fecha1 = "";
        fecha2 = "";
        LineChartSeries dos = new LineChartSeries();
        dos.setLabel("Formatos");
        for (T004Documento items : getDocumentosFacade().documentosTipoFormatoUser(new T006TipoDocumento("FTO"))) { 
            fecha1 = dateFormat1.format(items.getFecha());
            for (T004Documento items2 : getDocumentosFacade().documentosTipoFormatoUser(new T006TipoDocumento("FTO"))) { 
                fecha2= dateFormat2.format(items2.getFecha());
                if(fecha1.equals(fecha2)){
                    cont++;
                }
            }
            dos.set(fecha1, cont);
            cont=0;
        }
        cont = 0;
        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        fecha1 = "";
        fecha2 = "";
        String fechaMinString;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -14);
        Date quitarDias = cal.getTime();
        DateFormat dateFormatMin = new SimpleDateFormat("yyyy-MM-dd");
        fechaMinString = dateFormatMin.format(quitarDias);
        String fechaMaxString;
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        Date ponerDias = cal.getTime();
        DateFormat dateFormatMax = new SimpleDateFormat("yyyy-MM-dd");
        fechaMaxString = dateFormatMax.format(ponerDias);
        dateModel1.setSeriesColors("dd4b39,f39c12,00c0ef,00a65a,605ca8,a8abb2");
        dateModel1.addSeries(uno);
        dateModel1.addSeries(dos);
        dateModel1.setLegendPosition("w");
        dateModel1.getAxis(AxisType.Y).setLabel("Cantidades");
        DateAxis axis = new DateAxis("Fechas");
        axis.setTickAngle(0);
        axis.setMax(fechaMaxString);
        axis.setMin(fechaMinString);
        axis.setTickFormat("%b %#d");
        dateModel1.setAnimate(true);
        dateModel1.getAxes().put(AxisType.X, axis);
        dateModel1.setExtender("customExtender");
        Axis xAxis = dateModel1.getAxis(AxisType.X);
        xAxis.setTickInterval("1 month");
        Axis yAxis = dateModel1.getAxis(AxisType.Y);
        yAxis.setTickInterval("5");
        yAxis.setMin(0);
    }
    private void createBarModel1() {
        barModel1 = initBarModel1();
        barModel1.setLegendPosition("ne");
        barModel1.setSeriesColors("f39c12,dd4b39,00c0ef,605ca8,f39c12,dd4b39,00c0ef,605ca8,dd4b39,f39c12");
        barModel1.setExtender("customExtender");
        Axis xAxis = barModel1.getAxis(AxisType.X);
        Axis yAxis = barModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        for (T004Documento cantidadMaximaBar : getDocumentosFacade().consultaTopDocumento()) { 
            int cantidadDecimal = cantidadMaximaBar.getContarVista();
            String itervalo = String.valueOf((cantidadDecimal+10)/5);
            yAxis.setTickInterval(itervalo);
            break;
        }
    }
    
    private BarChartModel initBarModel1() {
        int pasarItem = 1; 
        BarChartModel model = new BarChartModel();
        ChartSeries uno = new ChartSeries();
        ChartSeries dos = new ChartSeries();
        ChartSeries tres = new ChartSeries();
        ChartSeries cuatro = new ChartSeries();
        ChartSeries cinco = new ChartSeries();
        ChartSeries seis = new ChartSeries();
        ChartSeries siete = new ChartSeries();
        ChartSeries ocho = new ChartSeries();
        ChartSeries nueve = new ChartSeries();
        ChartSeries diez = new ChartSeries();
        for (T004Documento items2 : getDocumentosFacade().consultaTopDocumento()) {
            int max = 500000;
            switch(pasarItem){
                case 1: uno.setLabel(items2.getNombre());
                        if(items2.getContarVista()>max){
                            uno.set("Número de descargas o vistas",max);
                            pasarItem ++;
                            break;    
                        }
                        uno.set("Número de descargas o vistas",items2.getContarVista());
                        pasarItem ++;
                        break;  
                case 2: dos.setLabel(items2.getNombre());
                        if(items2.getContarVista()>max){
                            dos.set("Número de descargas o vistas",max);
                            pasarItem ++;
                            break;    
                        }
                        dos.set("Número de descargas o vistas",items2.getContarVista());
                        pasarItem ++;
                        break;     
                case 3: tres.setLabel(items2.getNombre());
                        if(items2.getContarVista()>max){
                            tres.set("Número de descargas o vistas",max);
                            pasarItem ++;
                            break;    
                        }
                        tres.set("Número de descargas o vistas",items2.getContarVista());
                        pasarItem ++;
                        break;  
                case 4: cuatro.setLabel(items2.getNombre());
                        if(items2.getContarVista()>max){
                            cuatro.set("Número de descargas o vistas",max);
                            pasarItem ++;
                            break;    
                        }
                        cuatro.set("Número de descargas o vistas",items2.getContarVista());
                        pasarItem ++;
                        break;  
                case 5: cinco.setLabel(items2.getNombre());
                        if(items2.getContarVista()>max){
                            cinco.set("Número de descargas o vistas",max);
                            pasarItem ++;
                            break;    
                        }
                        cinco.set("Número de descargas o vistas",items2.getContarVista());
                        pasarItem ++;
                        break;  
            }
        }
        model.addSeries(uno);
        model.addSeries(dos);
        model.addSeries(tres);
        model.addSeries(cuatro);
        model.addSeries(cinco);
        return model;
    }
    
    
    //CONTENIDO
    private void createDateModel2() {
        dateModel2= new LineChartModel();
        int cont = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String fecha1 = "";
        String fecha2 = "";
        LineChartSeries uno = new LineChartSeries();
        uno.setLabel("Contenido");
        for (T007Contenido items : getContenidoFacade().findAll()) { 
            fecha1 = dateFormat1.format(items.getFecha());
            for (T007Contenido items2 : getContenidoFacade().findAll()) { 
                fecha2 = dateFormat2.format(items2.getFecha());
                if(fecha1.equals(fecha2)){
                    cont++;
                } 
            }
            uno.set(fecha1, cont);
            cont=0;
        }
        
        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        fecha1 = "";
        fecha2 = "";
        String fechaMinString;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -12);
        Date quitarDias = cal.getTime();
        DateFormat dateFormatMin = new SimpleDateFormat("yyyy-MM-dd");
        fechaMinString = dateFormatMin.format(quitarDias);
        String fechaMaxString;
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        Date ponerDias = cal.getTime();
        DateFormat dateFormatMax = new SimpleDateFormat("yyyy-MM-dd");
        fechaMaxString = dateFormatMax.format(ponerDias);
        dateModel2.setSeriesColors("dd4b39,f39c12,00c0ef,00a65a,605ca8,a8abb2");
        dateModel2.addSeries(uno);
        dateModel2.setLegendPosition("w");
        dateModel2.getAxis(AxisType.Y).setLabel("Cantidades");
        DateAxis axis = new DateAxis("Fechas");
        axis.setTickAngle(0);
        axis.setMax(fechaMaxString);
        axis.setMin(fechaMinString);
        axis.setTickFormat("%b %#d");
        dateModel2.setAnimate(true);
        dateModel2.getAxes().put(AxisType.X, axis);
        dateModel2.setExtender("customExtender");
        Axis xAxis = dateModel2.getAxis(AxisType.X);
        xAxis.setTickInterval("1 month");
        Axis yAxis = dateModel2.getAxis(AxisType.Y);
        yAxis.setTickInterval("5");
        yAxis.setMin(0);
    }
    private MeterGaugeChartModel initMeterGaugeModel2() {
        List<Number> intervals = new ArrayList<Number>() {
            {
                add(50);
                add(100);
                add(150);
                add(200);
            }
        };
        int contarVisita = 0;
        for (Integer s : getContenidoFacade().contarVistaContenido()){
                contarVisita =s;
        }
        return new MeterGaugeChartModel(contarVisita, intervals);
    }
    private void createMeterGaugeModels2() {
        meterGaugeModel2 = initMeterGaugeModel2();
        meterGaugeModel2.setSeriesColors("66cc66,93b75f,E7E658,cc6666");
        meterGaugeModel2.setGaugeLabelPosition("bottom");
    }
    //TOP ESTADO ITEMS
    private void createDateModel3() {
        dateModel3= new LineChartModel();
        int cont = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String fecha1 = "";
        String fecha2 = "";
        LineChartSeries uno = new LineChartSeries();
        LineChartSeries dos = new LineChartSeries();
        uno.setLabel("Ítems evaluados como ESTADO 1");
        for (T010EstadoItem items : getEstadoItemFacade().estadoItemEvaluados(2)) { 
            fecha1 = dateFormat1.format(items.getFechaActualizacion());
            for (T010EstadoItem items2 : getEstadoItemFacade().estadoItemEvaluados(2)) { 
                fecha2 = dateFormat2.format(items2.getFechaActualizacion());
                if(fecha1.equals(fecha2)){
                    cont++;
                }   
            }
            uno.set(fecha1, cont);
            cont=0;
        }
        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        fecha1 = "";
        fecha2 = "";
        String fechaMinString;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -12);
        Date quitarDias = cal.getTime();
        DateFormat dateFormatMin = new SimpleDateFormat("yyyy-MM-dd");
        fechaMinString = dateFormatMin.format(quitarDias);
        String fechaMaxString;
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        Date ponerDias = cal.getTime();
        DateFormat dateFormatMax = new SimpleDateFormat("yyyy-MM-dd");
        fechaMaxString = dateFormatMax.format(ponerDias);
        dateModel3.setSeriesColors("dd4b39,f39c12,00c0ef,00a65a,605ca8,a8abb2");
        dateModel3.addSeries(uno);
        dateModel3.setLegendPosition("w");
        dateModel3.getAxis(AxisType.Y).setLabel("Cantidades");
        DateAxis axis = new DateAxis("Fechas");
        axis.setTickAngle(0);
        axis.setMax(fechaMaxString);
        axis.setMin(fechaMinString);
        axis.setTickFormat("%b %#d");
        dateModel3.setAnimate(true);
        dateModel3.getAxes().put(AxisType.X, axis);
        dateModel3.setExtender("customExtender");
        Axis xAxis = dateModel3.getAxis(AxisType.X);
        xAxis.setTickInterval("1 month");
        Axis yAxis = dateModel3.getAxis(AxisType.Y);
        yAxis.setTickInterval("100");
        yAxis.setMin(0);
    }
    private MeterGaugeChartModel initMeterGaugeModel3() {
        List<Number> intervals = new ArrayList<Number>() {
            {
                add(20);
                add(40);
                add(60);
                add(80);
            }
        };
        int contarEvaluado = 0;
        for (Integer s : getEstadoItemFacade().contarGrupoEvaluadoMes()){
                contarEvaluado =s;
        }
        return new MeterGaugeChartModel(contarEvaluado, intervals);
    }
    private void createMeterGaugeModels3() {
        meterGaugeModel3 = initMeterGaugeModel3();
        meterGaugeModel3.setSeriesColors("cc6666,E7E658,93b75f,66cc66");
        meterGaugeModel3.setGaugeLabelPosition("bottom");
    }
    //ENTREGAS DE PRODUCCIÓN KOS
    private void createDateModel4() {
        dateModel4= new LineChartModel();
        int cont = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String fecha1 = "";
        String fecha2 = "";
        LineChartSeries uno = new LineChartSeries();
        LineChartSeries dos = new LineChartSeries();
        uno.setLabel("Cargues realizados");
        for (T012Lecturas items : getLecturaFacade().itemsEstado((short)1)) { 
            fecha1 = dateFormat1.format(items.getF001Fecha());
            for (T012Lecturas items2 : getLecturaFacade().itemsEstado((short)1)) { 
                fecha2 = dateFormat2.format(items2.getF001Fecha());
                if(fecha1.equals(fecha2)){
                    cont++;
                }   
            }
            uno.set(fecha1, cont);
            cont=0;
        }
        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        fecha1 = "";
        fecha2 = "";
        String fechaMinString;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -12);
        Date quitarDias = cal.getTime();
        DateFormat dateFormatMin = new SimpleDateFormat("yyyy-MM-dd");
        fechaMinString = dateFormatMin.format(quitarDias);
        String fechaMaxString;
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        Date ponerDias = cal.getTime();
        DateFormat dateFormatMax = new SimpleDateFormat("yyyy-MM-dd");
        fechaMaxString = dateFormatMax.format(ponerDias);
        dateModel4.setSeriesColors("dd4b39,f39c12,00c0ef,00a65a,605ca8,a8abb2");
        dateModel4.addSeries(uno);
        dateModel4.setLegendPosition("w");
        dateModel4.getAxis(AxisType.Y).setLabel("Cantidades");
        DateAxis axis = new DateAxis("Fechas");
        axis.setTickAngle(0);
        axis.setMax(fechaMaxString);
        axis.setMin(fechaMinString);
        axis.setTickFormat("%b %#d");
        dateModel4.setAnimate(true);
        dateModel4.getAxes().put(AxisType.X, axis);
        dateModel4.setExtender("customExtender");
        Axis xAxis = dateModel4.getAxis(AxisType.X);
        xAxis.setTickInterval("1 month");
        Axis yAxis = dateModel4.getAxis(AxisType.Y);
        yAxis.setTickInterval("100");
        yAxis.setMin(0);
    }
    private MeterGaugeChartModel initMeterGaugeModel4() {
        int contarCarguesUnoee = 0;
        for (Integer s : getLecturaFacade().contarTotalEptMesUnoEE()){
                contarCarguesUnoee =s;
        }
        if (contarCarguesUnoee==0){
            List<Number> intervals = new ArrayList<Number>() {
                {
                    add(100);
                    add(200);
                    add(300);
                    add(400);
                }
            };
            return new MeterGaugeChartModel(0, intervals);
        }
        int dividir = contarCarguesUnoee / 4;
        final int intervalo1 = dividir;
        final int intervalo2 = dividir * 2;
        final int intervalo3 = dividir * 3;
        final int intervalo4 = dividir * 4;
        List<Number> intervals = new ArrayList<Number>() {
            {
                add(intervalo1);
                add(intervalo2);
                add(intervalo3);
                add(intervalo4);
            }
        };
        int contarLecturasEureka = 0;
        for (Integer s : getLecturaFacade().contarTotalEptMesEureka()){
                contarLecturasEureka =s;
        }
        
        return new MeterGaugeChartModel(contarLecturasEureka, intervals);
    }
    private void createMeterGaugeModels4() {
        meterGaugeModel4 = initMeterGaugeModel4();
        meterGaugeModel4.setSeriesColors("cc6666,E7E658,93b75f,66cc66");
        meterGaugeModel4.setGaugeLabelPosition("bottom");
    }
    //CREACION DE ITEMS
    private void createDateModel5() {
        dateModel5= new LineChartModel();
        int cont = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String fecha1 = "";
        String fecha2 = "";
        LineChartSeries uno = new LineChartSeries();
        LineChartSeries dos = new LineChartSeries();
        uno.setLabel("Cargues realizados");
        for (T013CreacionItem items : getCreacionItemFacade().estadoCreacionItemTodos((short)3)) { 
            fecha1 = dateFormat1.format(items.getFechaCreacion());
            for (T013CreacionItem items2 : getCreacionItemFacade().estadoCreacionItemTodos((short)3)) { 
                fecha2 = dateFormat2.format(items2.getFechaCreacion());
                if(fecha1.equals(fecha2)){
                    cont++;
                }   
            }
            uno.set(fecha1, cont);
            cont=0;
        }
        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        fecha1 = "";
        fecha2 = "";
        String fechaMinString;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -12);
        Date quitarDias = cal.getTime();
        DateFormat dateFormatMin = new SimpleDateFormat("yyyy-MM-dd");
        fechaMinString = dateFormatMin.format(quitarDias);
        String fechaMaxString;
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        Date ponerDias = cal.getTime();
        DateFormat dateFormatMax = new SimpleDateFormat("yyyy-MM-dd");
        fechaMaxString = dateFormatMax.format(ponerDias);
        dateModel5.setSeriesColors("dd4b39,f39c12,00c0ef,00a65a,605ca8,a8abb2");
        dateModel5.addSeries(uno);
        dateModel5.setLegendPosition("w");
        dateModel5.getAxis(AxisType.Y).setLabel("Cantidades");
        DateAxis axis = new DateAxis("Fechas");
        axis.setTickAngle(0);
        axis.setMax(fechaMaxString);
        axis.setMin(fechaMinString);
        axis.setTickFormat("%b %#d");
        dateModel5.setAnimate(true);
        dateModel5.getAxes().put(AxisType.X, axis);
        dateModel5.setExtender("customExtender");
        Axis xAxis = dateModel5.getAxis(AxisType.X);
        xAxis.setTickInterval("1 month");
        Axis yAxis = dateModel5.getAxis(AxisType.Y);
        yAxis.setTickInterval("100");
        yAxis.setMin(0);
    }
    private MeterGaugeChartModel initMeterGaugeModel5() {
        int contarCarguesUnoee = 0;
        for (Integer s : getCreacionItemFacade().contarItemCreadoMesUnoEE()){
                contarCarguesUnoee =s;
        }
        if (contarCarguesUnoee==0){
            List<Number> intervals = new ArrayList<Number>() {
                {
                    add(100);
                    add(200);
                    add(300);
                    add(400);
                }
            };
            return new MeterGaugeChartModel(0, intervals);
        }
        int dividir = contarCarguesUnoee / 4;
        final int intervalo1 = dividir;
        final int intervalo2 = dividir * 2;
        final int intervalo3 = dividir * 3;
        final int intervalo4 = dividir * 4;
        List<Number> intervals = new ArrayList<Number>() {
            {
                add(intervalo1);
                add(intervalo2);
                add(intervalo3);
                add(intervalo4);
            }
        };
        int contarCreadosEureka = 0;
        for (Integer s : getCreacionItemFacade().contarItemCreadoMesEureka()){
                contarCreadosEureka =s;
        }
        
        return new MeterGaugeChartModel(contarCreadosEureka, intervals);
    }
    private void createMeterGaugeModels5() {
        meterGaugeModel5 = initMeterGaugeModel5();
        meterGaugeModel5.setSeriesColors("cc6666,E7E658,93b75f,66cc66");
        meterGaugeModel5.setGaugeLabelPosition("bottom");
    }
    //BUZON DE SUGERENCIAS
    private void createDateModel6() {
        dateModel6= new LineChartModel();
        int cont = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String fecha1 = "";
        String fecha2 = "";
        LineChartSeries uno = new LineChartSeries();
        LineChartSeries dos = new LineChartSeries();
        uno.setLabel("Sugerencias o quejas creadas");
        for (T018BuzonSugerencia items : getBuzonSugerenciaFacade().findAll()) { 
            fecha1 = dateFormat1.format(items.getFechaCreacion());
            for (T018BuzonSugerencia items2 : getBuzonSugerenciaFacade().findAll()) { 
                fecha2 = dateFormat2.format(items2.getFechaCreacion());
                if(fecha1.equals(fecha2)){
                    cont++;
                }   
            }
            uno.set(fecha1, cont);
            cont=0;
        }
        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        fecha1 = "";
        fecha2 = "";
        String fechaMinString;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -12);
        Date quitarDias = cal.getTime();
        DateFormat dateFormatMin = new SimpleDateFormat("yyyy-MM-dd");
        fechaMinString = dateFormatMin.format(quitarDias);
        String fechaMaxString;
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        Date ponerDias = cal.getTime();
        DateFormat dateFormatMax = new SimpleDateFormat("yyyy-MM-dd");
        fechaMaxString = dateFormatMax.format(ponerDias);
        dateModel6.setSeriesColors("dd4b39,f39c12,00c0ef,00a65a,605ca8,a8abb2");
        dateModel6.addSeries(uno);
        dateModel6.setLegendPosition("w");
        dateModel6.getAxis(AxisType.Y).setLabel("Cantidades");
        DateAxis axis = new DateAxis("Fechas");
        axis.setTickAngle(0);
        axis.setMax(fechaMaxString);
        axis.setMin(fechaMinString);
        axis.setTickFormat("%b %#d");
        dateModel6.setAnimate(true);
        dateModel6.getAxes().put(AxisType.X, axis);
        dateModel6.setExtender("customExtender");
        Axis xAxis = dateModel6.getAxis(AxisType.X);
        xAxis.setTickInterval("1 month");
        Axis yAxis = dateModel6.getAxis(AxisType.Y);
        yAxis.setTickInterval("100");
        yAxis.setMin(0);
    }
    //COMPROBANTES DE PAGO
    private void createDateModel7() {
        dateModel7= new LineChartModel();
        int cont = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String fecha1 = "";
        String fecha2 = "";
        LineChartSeries uno = new LineChartSeries();
        LineChartSeries dos = new LineChartSeries();
        uno.setLabel("Volantes enviados por mes");
        for (T015VolanteNomina items : getVolanteNominaFacade().consultaVolanteEstado(true)) { 
            fecha1 = dateFormat1.format(items.getFechaCreacion());
            for (T015VolanteNomina items2 : getVolanteNominaFacade().consultaVolanteEstado(true)) { 
                fecha2 = dateFormat2.format(items2.getFechaCreacion());
                if(fecha1.equals(fecha2)){
                    cont++;
                }   
            }
            uno.set(fecha1, cont);
            cont=0;
        }
        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        fecha1 = "";
        fecha2 = "";
        String fechaMinString;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -12);
        Date quitarDias = cal.getTime();
        DateFormat dateFormatMin = new SimpleDateFormat("yyyy-MM-dd");
        fechaMinString = dateFormatMin.format(quitarDias);
        String fechaMaxString;
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        Date ponerDias = cal.getTime();
        DateFormat dateFormatMax = new SimpleDateFormat("yyyy-MM-dd");
        fechaMaxString = dateFormatMax.format(ponerDias);
        dateModel7.setSeriesColors("dd4b39,f39c12,00c0ef,00a65a,605ca8,a8abb2");
        dateModel7.addSeries(uno);
        dateModel7.setLegendPosition("w");
        dateModel7.getAxis(AxisType.Y).setLabel("Cantidades");
        DateAxis axis = new DateAxis("Fechas");
        axis.setTickAngle(0);
        axis.setMax(fechaMaxString);
        axis.setMin(fechaMinString);
        axis.setTickFormat("%b %#d");
        dateModel7.setAnimate(true);
        dateModel7.getAxes().put(AxisType.X, axis);
        dateModel7.setExtender("customExtender");
        Axis xAxis = dateModel7.getAxis(AxisType.X);
        xAxis.setTickInterval("1 month");
        Axis yAxis = dateModel7.getAxis(AxisType.Y);
        yAxis.setTickInterval("100");
        yAxis.setMin(0);
    }
    private MeterGaugeChartModel initMeterGaugeModel7() {
        int contarVolanteCreado = 0;
        for (Integer s : getVolanteNominaFacade().contarVolanteCreado()){
                contarVolanteCreado =s;
        }
        if (contarVolanteCreado==0){
            List<Number> intervals = new ArrayList<Number>() {
                {
                    add(100);
                    add(200);
                    add(300);
                    add(400);
                }
            };
            return new MeterGaugeChartModel(0, intervals);
        }
        int dividir = contarVolanteCreado / 4;
        final int intervalo1 = dividir;
        final int intervalo2 = dividir * 2;
        final int intervalo3 = dividir * 3;
        final int intervalo4 = dividir * 4;
        List<Number> intervals = new ArrayList<Number>() {
            {
                add(intervalo1);
                add(intervalo2);
                add(intervalo3);
                add(intervalo4);
            }
        };
        int contarCreadosEureka = 0;
        for (Integer s : getVolanteNominaFacade().contarVolanteEnviado()){
                contarCreadosEureka =s;
        }
        return new MeterGaugeChartModel(contarCreadosEureka, intervals);
    }
    private void createMeterGaugeModels7() {
        meterGaugeModel7 = initMeterGaugeModel7();
        meterGaugeModel7.setSeriesColors("cc6666,E7E658,93b75f,66cc66");
        meterGaugeModel7.setGaugeLabelPosition("bottom");
    }
    //PEDIDOS WEB
    public void consultarPedidosUnoee()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "select count(*) total from UnoEE.dbo.t430_cm_pv_docto  where datepart(mm, f430_fecha_ts_creacion) = datepart(mm,getdate()) and f430_id_tipo_docto = 'PC'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                setPedidosUnoEE(rs.getInt("total"));
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void consultarPedidosPortalWeb()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_ClientesEka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "select count(*) total from BD_ClientesEka.dbo.t003_pedidos where datepart(mm, fecha_pedido) = datepart(mm,getdate())";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                setPedidosPortalWeb(rs.getInt("total"));
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    private MeterGaugeChartModel initMeterGaugeModel8() {
        int contarCarguesUnoee = getPedidosUnoEE();
        int dividir = contarCarguesUnoee / 4;
        final int intervalo1 = dividir;
        final int intervalo2 = dividir * 2;
        final int intervalo3 = dividir * 3;
        final int intervalo4 = dividir * 4;
        List<Number> intervals = new ArrayList<Number>() {
            {
                add(intervalo1);
                add(intervalo2);
                add(intervalo3);
                add(intervalo4);
            }
        };
        int contarCreadosEureka = getPedidosPortalWeb();
        for (Integer s : getVolanteNominaFacade().contarVolanteEnviado()){
                contarCreadosEureka =s;
        }
        return new MeterGaugeChartModel(contarCreadosEureka, intervals);
    }
    private void createMeterGaugeModels8() {
        meterGaugeModel8 = initMeterGaugeModel8();
        meterGaugeModel8.setSeriesColors("cc6666,E7E658,93b75f,66cc66");
    }
    
     //PEDIDOS WEB
    public void consultarFacturasUnoEE()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "select count(*) total from UnoEE.dbo.t350_co_docto_contable where datepart(mm, f350_fecha_ts_creacion) = datepart(mm,getdate()) and ([f350_id_tipo_docto] = 'TA' OR [f350_id_tipo_docto] = 'TK') and f350_ind_estado = 1";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                setFacturaUnoEE(rs.getInt("total"));
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void consultarNotificacionDespacho()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=EkaCorp;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "select count(*) total from EkaCorp.dbo.t015_pp_docto_aviso_despacho where datepart(mm, f015_fecha_350) = datepart(mm,getdate())";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                setFacturaNotificacionDespacho(rs.getInt("total"));
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    private MeterGaugeChartModel initMeterGaugeModel9() {
        int contarFacturaUnoee = getFacturaUnoEE();
        int dividir = contarFacturaUnoee / 4;
        final int intervalo1 = dividir;
        final int intervalo2 = dividir * 2;
        final int intervalo3 = dividir * 3;
        final int intervalo4 = dividir * 4;
        List<Number> intervals = new ArrayList<Number>() {
            {
                add(intervalo1);
                add(intervalo2);
                add(intervalo3);
                add(intervalo4);
            }
        };
        int contarNotificacionDespacho = getFacturaNotificacionDespacho();
        for (Integer s : getVolanteNominaFacade().contarVolanteEnviado()){
                contarNotificacionDespacho =s;
        }
        return new MeterGaugeChartModel(contarNotificacionDespacho, intervals);
    }
    private void createMeterGaugeModels9() {
        meterGaugeModel9 = initMeterGaugeModel9();
        meterGaugeModel9.setSeriesColors("cc6666,E7E658,93b75f,66cc66");
    }
}
