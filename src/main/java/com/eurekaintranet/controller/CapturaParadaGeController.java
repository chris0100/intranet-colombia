/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T035ParadasGe;
import com.eurekaintranet.entities.T036CapturaParadaGe;
import com.eurekaintranet.entities.T037DetalleParadaGe;
import com.eurekaintranet.sessions.T035ParadasGeFacade;
import com.eurekaintranet.sessions.T036CapturaParadaGeFacade;
import com.eurekaintranet.sessions.T037DetalleParadaGeFacade;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Lccabalr
 */
@Named(value = "capturaParadaGeController")
@SessionScoped
public class CapturaParadaGeController implements Serializable {

    @EJB
    private T036CapturaParadaGeFacade capturaParadaGeFacade;
    private T036CapturaParadaGe capturaParadaGeActual;
    private List<T036CapturaParadaGe> listaCapturaParadaGe;
    
    @EJB
    private T037DetalleParadaGeFacade detalleParadaGeFacade;
    private T037DetalleParadaGe detalleParadaGeActual;
    private List<T037DetalleParadaGe> listaDetalleParadaGe;
    private List<T037DetalleParadaGe> listaDetalleParadaGeEditar;
    
    @EJB
    private T035ParadasGeFacade ParadasGeFacade;
    private T035ParadasGe paradasGeActual;
    private List<T035ParadasGe> listaParadasGe;
    
    private List<String> listaOp;
    private Date fechaParametro1;
    private Date fechaParametro2;
    
    private Date fechaActual = new Date();
    private Date validarFechaAtras = new Date();
    private T001Usuario usuarioActual; 
    
    private String conOp;
    private List<String> listaTopParadas;
    private LineChartModel dateModel;
    private MeterGaugeChartModel meterGaugeModel; 
    private int totalHoraCaptura;
    private int totalHoraParada;
    private int totalHoraNoTrabajada;
    
    private Date horaInicio;
    private Date horaFin;
    
    private PieChartModel pieModel1;
    private final SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm:ss");
     
    public CapturaParadaGeController() {
    }

    public T036CapturaParadaGeFacade getCapturaParadaGeFacade() {
        return capturaParadaGeFacade;
    }

    public void setCapturaParadaGeFacade(T036CapturaParadaGeFacade capturaParadaGeFacade) {
        this.capturaParadaGeFacade = capturaParadaGeFacade;
    }

    public T036CapturaParadaGe getCapturaParadaGeActual() {
        return capturaParadaGeActual;
    }

    public void setCapturaParadaGeActual(T036CapturaParadaGe capturaParadaGeActual) {
        this.capturaParadaGeActual = capturaParadaGeActual;
    }

    public List<T036CapturaParadaGe> getListaCapturaParadaGe() {
        return listaCapturaParadaGe = getCapturaParadaGeFacade().consultarCapturas();
    }

    public void setListaCapturaParadaGe(List<T036CapturaParadaGe> listaCapturaParadaGe) {
        this.listaCapturaParadaGe = listaCapturaParadaGe;
    }

    public T037DetalleParadaGeFacade getDetalleParadaGeFacade() {
        return detalleParadaGeFacade;
    }

    public void setDetalleParadaGeFacade(T037DetalleParadaGeFacade detalleParadaGeFacade) {
        this.detalleParadaGeFacade = detalleParadaGeFacade;
    }

    public T037DetalleParadaGe getDetalleParadaGeActual() {
        return detalleParadaGeActual;
    }

    public void setDetalleParadaGeActual(T037DetalleParadaGe detalleParadaGeActual) {
        this.detalleParadaGeActual = detalleParadaGeActual;
    }

    public List<T037DetalleParadaGe> getListaDetalleParadaGe() {
        return listaDetalleParadaGe;
    }

    public void setListaDetalleParadaGe(List<T037DetalleParadaGe> listaDetalleParadaGe) {
        this.listaDetalleParadaGe = listaDetalleParadaGe;
    }

    public T035ParadasGeFacade getParadasGeFacade() {
        return ParadasGeFacade;
    }

    public void setParadasGeFacade(T035ParadasGeFacade ParadasGeFacade) {
        this.ParadasGeFacade = ParadasGeFacade;
    }

    public T035ParadasGe getParadasGeActual() {
        return paradasGeActual;
    }

    public void setParadasGeActual(T035ParadasGe paradasGeActual) {
        this.paradasGeActual = paradasGeActual;
    }

    public List<String> getListaOp() {
        return listaOp;
    }

    public void setListaOp(List<String> listaOp) {
        this.listaOp = listaOp;
    }
    public List<T035ParadasGe> getListaParadasGe() {
        return listaParadasGe = getParadasGeFacade().findAll();
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

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public Date getValidarFechaAtras() {
        return validarFechaAtras;
    }

    public void setValidarFechaAtras(Date validarFechaAtras) {
        this.validarFechaAtras = validarFechaAtras;
    }

    public String getConOp() {
        return conOp;
    }

    public void setConOp(String conOp) {
        this.conOp = conOp;
    }

    public List<String> getListaTopParadas() {
        return listaTopParadas;
    }

    public void setListaTopParadas(List<String> listaTopParadas) {
        this.listaTopParadas = listaTopParadas;
    }

    public LineChartModel getDateModel() {
        return dateModel;
    }

    public void setDateModel(LineChartModel dateModel) {
        this.dateModel = dateModel;
    }

    public int getTotalHoraCaptura() {
        return totalHoraCaptura;
    }

    public void setTotalHoraCaptura(int totalHoraCaptura) {
        this.totalHoraCaptura = totalHoraCaptura;
    }

    public int getTotalHoraParada() {
        return totalHoraParada;
    }

    public void setTotalHoraParada(int totalHoraParada) {
        this.totalHoraParada = totalHoraParada;
    }

    public MeterGaugeChartModel getMeterGaugeModel() {
        return meterGaugeModel;
    }

    public void setMeterGaugeModel(MeterGaugeChartModel meterGaugeModel) {
        this.meterGaugeModel = meterGaugeModel;
    }
    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public void setPieModel1(PieChartModel pieModel1) {
        this.pieModel1 = pieModel1;
    }

    public int getTotalHoraNoTrabajada() {
        return totalHoraNoTrabajada;
    }

    public void setTotalHoraNoTrabajada(int totalHoraNoTrabajada) {
        this.totalHoraNoTrabajada = totalHoraNoTrabajada;
    }

    public List<T037DetalleParadaGe> getListaDetalleParadaGeEditar() {
        return listaDetalleParadaGeEditar;
    }

    public void setListaDetalleParadaGeEditar(List<T037DetalleParadaGe> listaDetalleParadaGeEditar) {
        this.listaDetalleParadaGeEditar = listaDetalleParadaGeEditar;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    
    
     //Navigation
    public void recargarLista(){
        listaCapturaParadaGe=null;
        listaDetalleParadaGe=null;
    }
    public void recargarCamposNumerosCaptura(){
        capturaParadaGeActual.setPp(0);
        capturaParadaGeActual.setPuntas(0);
        capturaParadaGeActual.setPuntasProduccion(0);
        capturaParadaGeActual.setFactorConversion(new BigDecimal(0));
        capturaParadaGeActual.setIdItem(0);
        capturaParadaGeActual.setCantEntregada(new BigDecimal(0));
        capturaParadaGeActual.setCantProgramada(new BigDecimal(0));
        capturaParadaGeActual.setMtrsProducidos(null);
        capturaParadaGeActual.setPeso(null);
        capturaParadaGeActual.setClaseb(null);
        capturaParadaGeActual.setProductoRechazado(null);
        
    }
     public String prepareEdit() {
        detalleParadaGeActual = new T037DetalleParadaGe();
        listaDetalleParadaGe = getDetalleParadaGeFacade().consultarDetalleCaptura(capturaParadaGeActual);
        listaOp = getCapturaParadaGeFacade().consultaListaOp();
        validarFechaAtras = obtenerFechaAtras();
        horaInicio = capturaParadaGeActual.getHoraInicio();
        horaFin = capturaParadaGeActual.getHoraFinal();
        return "editar";
    }
    public String prepareCreate() {
        capturaParadaGeActual = new T036CapturaParadaGe();
        detalleParadaGeActual = new T037DetalleParadaGe();
        listaOp = getCapturaParadaGeFacade().consultaListaOp();
        listaDetalleParadaGe = new ArrayList<>();
        validarFechaAtras = obtenerFechaAtras();
        capturaParadaGeActual.setFechaCaptura(obtenerFechaSugerida());
        capturaParadaGeActual.setSinOp(false);
        recargarCamposNumerosCaptura();
        horaInicio = null;
        horaFin = null;
        conOp = "n";
        return "crear";
    }
    public String prepareDuplicar() {
        detalleParadaGeActual = new T037DetalleParadaGe();
        listaOp = getCapturaParadaGeFacade().consultaListaOp();
        listaDetalleParadaGe = new ArrayList<>();
        validarFechaAtras = obtenerFechaAtras();
        capturaParadaGeActual.setFechaCaptura(obtenerFechaSugerida());
        capturaParadaGeActual.setSinOp(false);
        capturaParadaGeActual.setCentroTrabajo("");
        capturaParadaGeActual.setExt1("");
        capturaParadaGeActual.setExt2("");
        capturaParadaGeActual.setExt1Detalle("");
        capturaParadaGeActual.setExt2Detalle("");
        capturaParadaGeActual.setDescripcionItem("");
        capturaParadaGeActual.setOp("");
        capturaParadaGeActual.setRuta("");
        capturaParadaGeActual.setUndMedida("");
        capturaParadaGeActual.setIdNominal(null);
        recargarCamposNumerosCaptura();
        conOp = "n";
        horaInicio = capturaParadaGeActual.getHoraInicio();
        horaFin = capturaParadaGeActual.getHoraFinal();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGeTelares/capturaParada/lista";
    }
    
    public Date obtenerFechaHoraFinTurno() throws ParseException{
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(capturaParadaGeActual.getFechaCaptura());
        Date fechaHoraFinTurno;
        DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");  
        String horaFinS = dateFormat1.format(horaFin);  
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");  
        String fechaCaptura = dateFormat2.format(calendar.getTime());
        if(capturaParadaGeActual.getIdTurno().getIdTurno()== 1 && capturaParadaGeActual.getIdTurno().getHoraFin().compareTo(capturaParadaGeActual.getIdTurno().getHoraInicio())<0){
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            fechaCaptura = dateFormat2.format(calendar.getTime()); 
            capturaParadaGeActual.setHoraFinal(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCaptura+" "+horaFinS));
        }else{
             capturaParadaGeActual.setHoraFinal(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCaptura+" "+horaFinS));
        }
        
        
        fechaHoraFinTurno = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCaptura+" "+horaFinS);
        System.out.println("Fecha fin turno: "+fechaHoraFinTurno.toLocaleString());
        return fechaHoraFinTurno;
    }
    
    
    public Date obtenerFechaHoraInicioTurno() throws ParseException{
        Date fechaHoraInicioTurno;
        DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");  
        String horaInicioS = dateFormat1.format(capturaParadaGeActual.getIdTurno().getHoraInicio());  
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");  
        String fechaCaptura = dateFormat2.format(capturaParadaGeActual.getFechaCaptura()); 
        fechaHoraInicioTurno = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCaptura+" "+horaInicioS);
        System.out.println("Fecha inicio turno: "+fechaHoraInicioTurno.toLocaleString());
        return fechaHoraInicioTurno;
    }
    public void obtenerHoraCaptura() throws ParseException{
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(capturaParadaGeActual.getFechaCaptura());
        DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");  
        String horaInicioS = dateFormat1.format(getHoraInicio());   
        String horaFinS = dateFormat1.format(getHoraFin());  
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");  
        String fechaCaptura = dateFormat2.format(calendar.getTime()); 
        capturaParadaGeActual.setHoraInicio(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCaptura+" "+horaInicioS));
        if(capturaParadaGeActual.getIdTurno().getIdTurno()== 1 && horaFin.compareTo(horaInicio)<0){
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            fechaCaptura = dateFormat2.format(calendar.getTime()); 
            capturaParadaGeActual.setHoraFinal(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCaptura+" "+horaFinS));
        }else{
             capturaParadaGeActual.setHoraFinal(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCaptura+" "+horaFinS));
        }
        System.out.println("Hora inicio "+horaInicioS+" Hora fin "+horaFinS+ "fecha "+fechaCaptura+" hora inicio real "+capturaParadaGeActual.getHoraInicio().toLocaleString()+" hora final real "+capturaParadaGeActual.getHoraFinal().toLocaleString());
    }
    public void obtenerHoraDetalle() throws ParseException{
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(capturaParadaGeActual.getFechaCaptura());
        DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");  
        String horaInicioS = dateFormat1.format(detalleParadaGeActual.getHoraInicio());   
        String horaFinS = dateFormat1.format(detalleParadaGeActual.getHoraFin());  
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");  
        String fechaCaptura = dateFormat2.format(capturaParadaGeActual.getFechaCaptura()); 
        detalleParadaGeActual.setHoraInicio(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCaptura+" "+horaInicioS));
        detalleParadaGeActual.setHoraFin(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCaptura+" "+horaFinS));
        if(capturaParadaGeActual.getIdTurno().getIdTurno()== 1 && detalleParadaGeActual.getHoraInicio().compareTo(obtenerHora1())>=0 && detalleParadaGeActual.getHoraInicio().compareTo(obtenerHora2())<=0){
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            fechaCaptura = dateFormat2.format(calendar.getTime()); 
            detalleParadaGeActual.setHoraInicio(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCaptura+" "+horaInicioS));
            detalleParadaGeActual.setHoraFin(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCaptura+" "+horaFinS));
            System.out.println("v4 hora cero "+obtenerHora1().toLocaleString()+" hora cero2 "+obtenerHora2().toLocaleString()+" Hora inicio "+detalleParadaGeActual.getHoraInicio().toLocaleString()+" Hora fin "+detalleParadaGeActual.getHoraFin().toLocaleString());
        }
        if(capturaParadaGeActual.getIdTurno().getIdTurno()== 1 && detalleParadaGeActual.getHoraFin().compareTo(detalleParadaGeActual.getHoraInicio())<0){
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            fechaCaptura = dateFormat2.format(calendar.getTime()); 
            detalleParadaGeActual.setHoraFin(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCaptura+" "+horaFinS));
            System.out.println("v1 Hora inicio "+detalleParadaGeActual.getHoraInicio().toLocaleString()+" Hora fin "+detalleParadaGeActual.getHoraFin().toLocaleString()+ " fecha "+fechaCaptura+" hora inicio real "+capturaParadaGeActual.getHoraInicio().toLocaleString()+" hora final real "+capturaParadaGeActual.getHoraFinal().toLocaleString());
        }else{
            System.out.println("v2 Hora inicio "+detalleParadaGeActual.getHoraInicio().toLocaleString()+" Hora fin "+detalleParadaGeActual.getHoraFin().toLocaleString()+ " fecha "+fechaCaptura+" hora inicio real "+capturaParadaGeActual.getHoraInicio().toLocaleString()+" hora final real "+capturaParadaGeActual.getHoraFinal().toLocaleString());
        }
    }
    public Date obtenerHora1() throws ParseException{
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.DATE,capturaParadaGeActual.getFechaCaptura().getDate());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");     
        String horaS = dateFormat1.format(calendar.getTime());  
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");  
        String fechaCaptura = dateFormat2.format(calendar.getTime()); 
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCaptura+" "+horaS);
    }
    public Date obtenerHora2() throws ParseException{
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(capturaParadaGeActual.getFechaCaptura());
        DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");     
        String horaS = dateFormat1.format(capturaParadaGeActual.getIdTurno().getHoraFin());  
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");  
        String fechaCaptura = dateFormat2.format(calendar.getTime()); 
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaCaptura+" "+horaS);
    }
    public String add() {
        try { 
            obtenerHoraCaptura();
            if(capturaParadaGeActual.getHoraFinal().compareTo(obtenerFechaHoraFinTurno())<=0){
                if(capturaParadaGeActual.getHoraInicio().compareTo(obtenerFechaHoraInicioTurno())>=0 ){
                    if(!formatoHora.format(capturaParadaGeActual.getHoraInicio().getTime()).equals(formatoHora.format(capturaParadaGeActual.getHoraFinal().getTime()))){          
                        capturaParadaGeActual.setEstado(true);
                        capturaParadaGeActual.setUsuarioCreacion(usuarioActual.getUsername());
                        capturaParadaGeActual.setFechaCreacion(new Date());
                        getCapturaParadaGeFacade().create(capturaParadaGeActual);
                        if(capturaParadaGeActual.getSinOp()==true){
                             detalleParadaGeActual = new T037DetalleParadaGe();
                             detalleParadaGeActual.setIdParada(new T035ParadasGe(1, "A001", "Falta de programación"));
                             detalleParadaGeActual.setHoraInicio(capturaParadaGeActual.getHoraInicio());
                             detalleParadaGeActual.setHoraFin(capturaParadaGeActual.getHoraFinal());
                             detalleParadaGeActual.setUsuarioCreacion(usuarioActual.getUsername());
                             detalleParadaGeActual.setFechaCreacion(new Date());
                             detalleParadaGeActual.setIdCaptura(capturaParadaGeActual);
                             detalleParadaGeFacade.create(detalleParadaGeActual);
                        }else{
                            for(T037DetalleParadaGe item:listaDetalleParadaGe){
                                 item.setIdCaptura(capturaParadaGeActual);
                                 detalleParadaGeFacade.create(item);
                             } 
                        }
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Captura creada ", "Captura creada correctamente");
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                        //recargarLista();
                        return "lista";
                     }else{
                         FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Captura no creada ", "La captura no fue creada debido a que la hora inicio debe estar entre la hora de inicio y fin del turno");
                         RequestContext.getCurrentInstance().showMessageInDialog(message);
                         capturaParadaGeActual.setHoraInicio(null);
                         return null;
                     } 
                }else{
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Captura no creada ", "a captura no fue creada debido a que la hora inicial no puede ser igual a la hora final");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                    capturaParadaGeActual.setHoraFinal(null);
                    return null;
                }
            }else{
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Captura no creada ", "La captura no fue creada debido a que la hora de final debe estar entre la hora de inicio y fin del turno");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                capturaParadaGeActual.setHoraFinal(null);
                return null; 
            }  
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String edit() {
        try { 
            obtenerHoraCaptura();
            if(capturaParadaGeActual.getHoraFinal().compareTo(obtenerFechaHoraFinTurno())<=0){
                if(capturaParadaGeActual.getHoraInicio().compareTo(obtenerFechaHoraInicioTurno())>=0 ){
                    if(!formatoHora.format(capturaParadaGeActual.getHoraInicio().getTime()).equals(formatoHora.format(capturaParadaGeActual.getHoraFinal().getTime()))){              
                        capturaParadaGeActual.setUsuarioActualizacion(usuarioActual.getUsername());
                        capturaParadaGeActual.setFechaActualizacion(new Date());
                        getCapturaParadaGeFacade().edit(capturaParadaGeActual);
                        for(T037DetalleParadaGe item: getDetalleParadaGeFacade().consultarDetalleCaptura(capturaParadaGeActual)){
                            detalleParadaGeFacade.remove(item);
                        }
                        for(T037DetalleParadaGe item:listaDetalleParadaGe){
                            item.setIdCaptura(capturaParadaGeActual);
                            detalleParadaGeFacade.create(item);
                        }
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Captura editada correctamente", "Captura editada correctamente");
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                        //recargarLista();
                        return "lista";
                    }else{
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Captura no creada ", "La captura no fue creada debido a que la hora inicial no puede ser igual a la hora final");
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                        capturaParadaGeActual.setHoraFinal(null);
                        return null;
                    }
                }else{
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Captura no editada correctamente", "La captura no fue editada debido a que la hora inicio debe estar entre la hora de inicio y fin del turno");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                    capturaParadaGeActual.setHoraInicio(null);
                    return null; 
                }
            }else{
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Captura no creada", "La captura no fue creada debido a que la hora de final debe estar entre la hora de inicio y fin del turno");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                capturaParadaGeActual.setHoraFinal(null);
                return null; 
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public void addDetalle() {
        try {
            obtenerHoraCaptura();
            obtenerHoraDetalle();
            if(capturaParadaGeActual.getHoraFinal().compareTo(obtenerFechaHoraFinTurno())<=0){
                if(capturaParadaGeActual.getHoraInicio().compareTo(obtenerFechaHoraInicioTurno())>=0 ){
                    if(!formatoHora.format(capturaParadaGeActual.getHoraInicio().getTime()).equals(formatoHora.format(capturaParadaGeActual.getHoraFinal().getTime()))){  
                        if(!formatoHora.format(detalleParadaGeActual.getHoraInicio().getTime()).equals(formatoHora.format(detalleParadaGeActual.getHoraFin().getTime()))){    
                            if(detalleParadaGeActual.getHoraFin().compareTo(capturaParadaGeActual.getHoraFinal())<=0){
                                if(detalleParadaGeActual.getHoraInicio().compareTo(capturaParadaGeActual.getHoraInicio())>=0){
                                    boolean horaEncontrada =false;
                                    if(!listaDetalleParadaGe.isEmpty()){
                                        for(T037DetalleParadaGe item : listaDetalleParadaGe){   
                                            if(detalleParadaGeActual.getHoraInicio().after(item.getHoraInicio()) && detalleParadaGeActual.getHoraInicio().before(item.getHoraFin())){
                                                horaEncontrada =true;
                                                break;
                                            }else if(detalleParadaGeActual.getHoraFin().after(item.getHoraInicio()) && detalleParadaGeActual.getHoraFin().before(item.getHoraFin())){
                                                horaEncontrada =true;
                                                break;
                                            }else if(detalleParadaGeActual.getHoraInicio().before(item.getHoraInicio()) && detalleParadaGeActual.getHoraFin().after(item.getHoraFin())){
                                                horaEncontrada =true;
                                                break;
                                            }else if(detalleParadaGeActual.getHoraInicio().equals(item.getHoraInicio()) && detalleParadaGeActual.getHoraFin().equals(item.getHoraFin())){
                                                horaEncontrada =true;
                                                break;
                                            }
                                            else if(detalleParadaGeActual.getHoraInicio().equals(item.getHoraInicio()) && detalleParadaGeActual.getHoraFin().after(item.getHoraFin())){
                                                horaEncontrada =true;
                                                break;
                                            }
                                            else if(detalleParadaGeActual.getHoraInicio().before(item.getHoraInicio()) && detalleParadaGeActual.getHoraFin().equals(item.getHoraFin())){
                                                horaEncontrada =true;
                                                break;
                                            }
                                            else{
                                                horaEncontrada =false;
                                            }
                                        }
                                        if(horaEncontrada==false){
                                            detalleParadaGeActual.setUsuarioCreacion(usuarioActual.getUsername());
                                            detalleParadaGeActual.setFechaCreacion(new Date());
                                            listaDetalleParadaGe.add(detalleParadaGeActual);
                                            //detalleParadaGeFacade.create(detalleParadaGeActual);
                                            detalleParadaGeActual = new T037DetalleParadaGe();
                                            addSuccessMessage("Parada creada correctamente", "Parada creada correctamente");
                                            //recargarLista();
                                        }else{
                                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Parada no creada ", "Ya se encuentra una para registrada en ese rango de horas");
                                            RequestContext.getCurrentInstance().showMessageInDialog(message);
                                            detalleParadaGeActual.setHoraInicio(null);
                                            detalleParadaGeActual.setHoraFin(null);
                                        }
                                    }else{
                                        detalleParadaGeActual.setUsuarioCreacion(usuarioActual.getUsername());
                                        detalleParadaGeActual.setFechaCreacion(new Date());
                                        listaDetalleParadaGe.add(detalleParadaGeActual);
                                        //detalleParadaGeFacade.create(detalleParadaGeActual);
                                        detalleParadaGeActual = new T037DetalleParadaGe();
                                        addSuccessMessage("Parada creada correctamente", "Parada creada correctamente");
                                    }

                                }else{
                                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Parada no creada", "La parada no fue creada debido a que la hora inicio debe estar entre la hora de inicio y fin de la captura");
                                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                                    detalleParadaGeActual.setHoraInicio(null);
                                }
                            }else{
                                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Parada no creada", "La parada no fue creada debido a que la hora de final debe estar entre la hora de inicio y fin de la captura");
                                RequestContext.getCurrentInstance().showMessageInDialog(message);
                                detalleParadaGeActual.setHoraFin(null);
                            }
                        }else{
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Parada no creada", "La parada no fue creada debido a que la hora inicial no puede ser igual a la hora final ");
                            RequestContext.getCurrentInstance().showMessageInDialog(message);
                            detalleParadaGeActual.setHoraFin(null);
                        }
                    }else{
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Captura no creada", "La captura no fue creada debido a que la hora inicial no puede ser igual a la hora final ");
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                        capturaParadaGeActual.setHoraFinal(null);
                    }
                }else{
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Captura no creada", "La captura no fue creada debido a que la hora inicio debe estar entre la hora de inicio y fin del turno");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                    capturaParadaGeActual.setHoraInicio(null);
                }
            }else{
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Captura no creada ", "La captura no fue creada debido a que la hora de final debe estar entre la hora de inicio y fin del turno");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                capturaParadaGeActual.setHoraFinal(null);
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
    }
    public String update() {
        try {
            getCapturaParadaGeFacade().create(capturaParadaGeActual);
            addSuccessMessage("Parada editada correctamente", "Parada editada correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public void deleteDetalle() {
        try {
            listaDetalleParadaGe.remove(detalleParadaGeActual);
            addErrorMessage("Parada no creado correctamente", "La parada no fue creada debido a que la hora final no puede ser mayor a la hora de inicio");
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
    }
    //validar fecha
    public Date obtenerFechaAtras(){	
 	Calendar calendar = Calendar.getInstance();	
        calendar.setTime(new Date()); // Configuramos la fecha que se recibe	
        calendar.add(Calendar.DATE, -7);  // numero de horas a añadir, o restar en caso de horas<0	
        return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas
    }
    public Date obtenerFechaSugerida(){	
 	Calendar calendar = Calendar.getInstance();	
        calendar.setTime(new Date()); // Configuramos la fecha que se recibe	
        calendar.add(Calendar.DATE, -1);  // numero de horas a añadir, o restar en caso de horas<0	
        return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas
    }
    //validar metros producidos buenos
    public void validarMtrosProducidos(){
        try {
            BigDecimal maximo= ((capturaParadaGeActual.getCantProgramada()).multiply(new BigDecimal(0.1))).add(capturaParadaGeActual.getCantProgramada()).setScale(1, BigDecimal.ROUND_HALF_EVEN);
            convertirMtrosKilos();
            if(capturaParadaGeActual.getMtrsProducidos().compareTo(maximo)>0 && capturaParadaGeActual.getIdNominal().getIdProceso().getIdProceso() != 33){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Captura no creada correctamente", "El campo de metros producidos buenos no puede superar la cantidad programada de "+maximo);
                RequestContext.getCurrentInstance().showMessageInDialog(message); 
                capturaParadaGeActual.setMtrsProducidos(new BigDecimal(0));
                capturaParadaGeActual.setPeso(new BigDecimal(0));
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Captura no creada correctamente", "Se debe diligenciar el campo de orden de producción");
            RequestContext.getCurrentInstance().showMessageInDialog(message); 
            capturaParadaGeActual.setMtrsProducidos(new BigDecimal(0));
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
    //metodos
    public List<String> completeOp(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : listaOp) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }
    public void consultarDatosOp() throws SQLException{
        String tipoOp = capturaParadaGeActual.getOp().substring(0,2);
        String consecutivo = capturaParadaGeActual.getOp().substring(4,capturaParadaGeActual.getOp().length());
        capturaParadaGeActual.setPp(0);
        capturaParadaGeActual.setPuntas(0);
        capturaParadaGeActual.setFactorConversion(new BigDecimal(0));
        consultarItemOp(tipoOp,consecutivo);
    }
    public void convertirKilosMtros() throws SQLException{
        try {
            capturaParadaGeActual.setMtrsProducidos(capturaParadaGeActual.getPeso().multiply(capturaParadaGeActual.getFactorConversion()));
            BigDecimal maximo= ((capturaParadaGeActual.getCantProgramada()).multiply(new BigDecimal(0.1))).add(capturaParadaGeActual.getCantProgramada()).setScale(1, BigDecimal.ROUND_HALF_EVEN);
            if(capturaParadaGeActual.getMtrsProducidos().compareTo(maximo)>0 && capturaParadaGeActual.getIdNominal().getIdProceso().getIdProceso() != 33){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Captura no creada correctamente", "El campo de metros producidos buenos no puede superar la cantidad programada de "+maximo);
                RequestContext.getCurrentInstance().showMessageInDialog(message); 
                capturaParadaGeActual.setMtrsProducidos(new BigDecimal(0));
                capturaParadaGeActual.setPeso(new BigDecimal(0));
            }
        } catch (Exception e) {
            capturaParadaGeActual.setMtrsProducidos(new BigDecimal(0));
        }
    }
    public void convertirMtrosKilos() throws SQLException{
        try {
            capturaParadaGeActual.setPeso((capturaParadaGeActual.getMtrsProducidos().divide(capturaParadaGeActual.getFactorConversion(),4, BigDecimal.ROUND_HALF_EVEN)));
        } catch (Exception e) {
            System.out.println("error "+e);
            capturaParadaGeActual.setPeso(new BigDecimal(0));
        }
    }
    public void llenarListaDetalleCaptura(T036CapturaParadaGe captura){
        listaDetalleParadaGe = getDetalleParadaGeFacade().consultarDetalleCaptura(captura);
    }
    public void llenarListaReporteCaptura(){
        listaDetalleParadaGe = getDetalleParadaGeFacade().consultarReporteCaptura(fechaParametro1, fechaParametro2);
    }
    public String prepareReporte() {
        listaDetalleParadaGe = new  ArrayList<>();
        return "listaDetalle";
    }
    public void limpiarCamposParadaSinOp(){
        if(conOp.equals("s")){
            capturaParadaGeActual.setOp(null);
            capturaParadaGeActual.setIdItem(0);
            capturaParadaGeActual.setCantEntregada(new BigDecimal(0));
            capturaParadaGeActual.setExt1(null);
            capturaParadaGeActual.setExt2(null);
            capturaParadaGeActual.setDescripcionItem(null);
            capturaParadaGeActual.setExt1Detalle(null);
            capturaParadaGeActual.setExt2Detalle(null);
            capturaParadaGeActual.setClaseb(new BigDecimal(0));
            capturaParadaGeActual.setRuta(null);
            capturaParadaGeActual.setCentroTrabajo(null);
            capturaParadaGeActual.setPuntas(0);
            capturaParadaGeActual.setPuntasProduccion(0);
            capturaParadaGeActual.setMtrsProducidos(new BigDecimal(0));
            capturaParadaGeActual.setCantProgramada(new BigDecimal(0));
            capturaParadaGeActual.setPeso(new BigDecimal(0));
            capturaParadaGeActual.setSinOp(true);
            capturaParadaGeActual.setProductoRechazado(null);
            capturaParadaGeActual.setPp(0);
            listaDetalleParadaGe = new ArrayList<>();
        } else{
            capturaParadaGeActual.setSinOp(false);
        }
    }
    
     public void consultarItemOp(String tipo, String consecutivo) throws SQLException{
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "exec sp_obtener_item_op_ge_telares '"+tipo+"',"+consecutivo;
                try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
               capturaParadaGeActual.setIdItem(rs.getInt("id_item"));
               capturaParadaGeActual.setDescripcionItem(rs.getString("id_item")+" - "+rs.getString("descripcion_item"));
               if(rs.getString("id_ext1")!=null){
                    capturaParadaGeActual.setExt1(rs.getString("id_ext1"));
                    capturaParadaGeActual.setExt1Detalle(rs.getString("id_ext1")+" - "+rs.getString("desc_ext1"));
               }
               if(rs.getString("id_ext2")!=null){
                    capturaParadaGeActual.setExt2(rs.getString("id_ext2"));
                    capturaParadaGeActual.setExt2Detalle(rs.getString("id_ext2")+" - "+rs.getString("desc_ext2"));
               }
               capturaParadaGeActual.setCantProgramada(rs.getBigDecimal("cant_programada"));
               capturaParadaGeActual.setCantEntregada(rs.getBigDecimal("cant_entregada"));
               capturaParadaGeActual.setRuta(rs.getString("ruta"));
               capturaParadaGeActual.setCentroTrabajo(rs.getString("ct"));
               capturaParadaGeActual.setUndMedida(rs.getString("und_medida"));
               consultarEntidadPP(capturaParadaGeActual.getIdItem(),capturaParadaGeActual.getExt1(),capturaParadaGeActual.getExt2());
               consultarEntidadPuntas(capturaParadaGeActual.getIdItem(),capturaParadaGeActual.getExt1(),capturaParadaGeActual.getExt2());
               consultarEntidadFactorConversion(capturaParadaGeActual.getIdItem(),capturaParadaGeActual.getExt1(),capturaParadaGeActual.getExt2());
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void consultarEntidadPP(int idItem, String ext1, String ext2) throws SQLException{
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        //String query = "select v743.v743_numero pp from  UnoEE.dbo.v121 left join UnoEE.dbo.v743  on v743_rowid = v121_rowid_entidad_item_ext  and v743_rowid_entidad_atributo = 1558 inner join UnoEE.dbo.t753_mm_movto_entidad_columna t753 ON t753.f753_rowid_movto_entidad = v121_rowid_entidad_item_ext  inner join UnoEE.dbo.t741_mm_maestro_detalle t741 ON t741.f741_rowid = f753_rowid_maestro_detalle where v121_id_item = "+capturaParadaGeActual.getIdItem()+ " and v121_id_ext1_detalle = '"+capturaParadaGeActual.getExt1()+"' and v121_id_ext2_detalle = '"+capturaParadaGeActual.getExt2()+"' ";                
        String query = "select v743.v743_numero pp from  UnoEE.dbo.v121 left join UnoEE.dbo.v743  on v743_rowid = v121_rowid_entidad_item_ext  and v743_rowid_entidad_atributo = 1553 inner join UnoEE.dbo.t753_mm_movto_entidad_columna t753 ON t753.f753_rowid_movto_entidad = v121_rowid_entidad_item_ext  inner join UnoEE.dbo.t741_mm_maestro_detalle t741 ON t741.f741_rowid = f753_rowid_maestro_detalle where v121_id_item = "+idItem+ " and v121_id_ext1_detalle = '"+ext1+"' and v121_id_ext2_detalle = '"+ext2+"' ";                
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if(rs.getBigDecimal("pp")!=null){
                    capturaParadaGeActual.setPp(rs.getBigDecimal("pp").intValue());
                }
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void consultarEntidadPuntas(int idItem, String ext1, String ext2) throws SQLException{
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "select v743.v743_numero puntas from  UnoEE.dbo.v121 left join UnoEE.dbo.v743  on v743_rowid = v121_rowid_entidad_item_ext  and v743_rowid_entidad_atributo = 1554 inner join UnoEE.dbo.t753_mm_movto_entidad_columna t753 ON t753.f753_rowid_movto_entidad = v121_rowid_entidad_item_ext  inner join UnoEE.dbo.t741_mm_maestro_detalle t741 ON t741.f741_rowid = f753_rowid_maestro_detalle where v121_id_item = "+idItem+ " and v121_id_ext1_detalle = '"+ext1+"' and v121_id_ext2_detalle = '"+ext2+"' ";  
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if(rs.getBigDecimal("puntas")!=null){
                    capturaParadaGeActual.setPuntas(rs.getBigDecimal("puntas").intValue());
                }
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void consultarEntidadFactorConversion(int idItem, String ext1, String ext2) throws SQLException{
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "select v743.v743_numero fc from  UnoEE.dbo.v121 left join UnoEE.dbo.v743  on v743_rowid = v121_rowid_entidad_item_ext  and v743_rowid_entidad_atributo = 30 inner join UnoEE.dbo.t753_mm_movto_entidad_columna t753 ON t753.f753_rowid_movto_entidad = v121_rowid_entidad_item_ext  inner join UnoEE.dbo.t741_mm_maestro_detalle t741 ON t741.f741_rowid = f753_rowid_maestro_detalle where v121_id_item = "+idItem+ " and v121_id_ext1_detalle = '"+ext1+"' and v121_id_ext2_detalle = '"+ext2+"' ";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if(rs.getBigDecimal("fc")!=null){
                    capturaParadaGeActual.setFactorConversion(rs.getBigDecimal("fc"));
                }
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public String prepareDashboard() throws SQLException{
        try {
            recargarConsultaParadaTop();
            //createDateModel();
            consultarTotalHoraCaptura();
            consultarTotalHoraParada();
            //createMeterGaugeModels();
            createPieModel();
            totalHoraNoTrabajada = totalHoraCaptura - totalHoraParada;
        } catch (Exception e) {
            System.out.println("error "+e);
        }
        return "/administrador/modGeTelares/principal";
    }
    public void recargarConsultaParadaTop()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "SELECT top 5 t035.codigo, t035.descripcion,sum(abs(cast ( datediff(mi, t037.hora_inicio, t037.hora_fin) / 60.0 as decimal (18,2)) )) hora FROM t037_detalle_parada_ge t037 inner join  t035_paradas_ge t035  on t035.id_parada = t037.id_parada  group by t035.codigo, t035.descripcion order by sum(abs(cast ( datediff(mi, t037.hora_inicio, t037.hora_fin) / 60.0 as decimal (18,2)))) desc";
        listaTopParadas= new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                detalleParadaGeActual = new T037DetalleParadaGe();
                listaTopParadas.add(rs.getString("codigo")+" | "+ rs.getString("descripcion")+" - "+rs.getDouble("hora")+" Horas");
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    
    
    private void createDateModel() {
        dateModel = new LineChartModel();
        int cont = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String fecha1 = "";
        String fecha2 = "";
        LineChartSeries uno = new LineChartSeries();
        uno.setLabel("Capturas creadas");
        for (T036CapturaParadaGe items : getCapturaParadaGeFacade().consultarCapturas()) { 
            fecha1 = dateFormat1.format(items.getFechaCreacion());
            for (T036CapturaParadaGe items2 : getCapturaParadaGeFacade().consultarCapturas()) { 
                fecha2 = dateFormat2.format(items2.getFechaCreacion());
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
        dos.setLabel("Paradas creadas");
        for (T037DetalleParadaGe items : getDetalleParadaGeFacade().findAll()) { 
            fecha1 = dateFormat1.format(items.getFechaCreacion());
            for (T037DetalleParadaGe items2 : getDetalleParadaGeFacade().findAll()) { 
                fecha2= dateFormat2.format(items2.getFechaCreacion());
                if(fecha1.equals(fecha2)){
                    cont++;
                }
            }
            dos.set(fecha1, cont);
            cont=0;
        }
        
        String fechaMinString;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -14);
        Date quitarDias = cal.getTime();
        DateFormat dateFormatMin = new SimpleDateFormat("yyyy-MM-dd");
        fechaMinString = dateFormatMin.format(quitarDias);
        String fechaMaxString;
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        Date ponerDias = cal.getTime();
        DateFormat dateFormatMax = new SimpleDateFormat("yyyy-MM-dd");
        fechaMaxString = dateFormatMax.format(ponerDias);
        dateModel.setSeriesColors("f39c12,dd4b39,00c0ef,00a65a,605ca8,a8abb2");
        dateModel.addSeries(uno);
       dateModel.addSeries(dos);
        dateModel.setLegendPosition("w");
        dateModel.getAxis(AxisType.Y).setLabel("Cantidades");
        DateAxis axis = new DateAxis("Fechas");
        axis.setTickAngle(0);
        axis.setMax(fechaMaxString);
        axis.setMin(fechaMinString);
        axis.setTickFormat("%b %#d");
        dateModel.setAnimate(true);
        dateModel.getAxes().put(AxisType.X, axis);
        dateModel.setExtender("customExtender");
        Axis xAxis = dateModel.getAxis(AxisType.X);
        xAxis.setTickInterval("1 day");
        Axis yAxis = dateModel.getAxis(AxisType.Y);
        yAxis.setTickInterval("5");
        yAxis.setMin(0);
    }
    private MeterGaugeChartModel initMeterGaugeModel() {
        int dividir = totalHoraCaptura / 4;
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
        
        return new MeterGaugeChartModel(totalHoraParada, intervals);
    }
    private void createMeterGaugeModels() {
        meterGaugeModel = initMeterGaugeModel();
        meterGaugeModel.setExtender("customExtender");
        meterGaugeModel.setSeriesColors("cc6666,E7E658,93b75f,66cc66");
    }
    public void consultarTotalHoraCaptura()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "SELECT sum(abs(cast ( datediff(mi, t036.hora_inicio, t036.hora_final) / 60.0 as decimal (18,2))) ) hora FROM t036_captura_parada_ge t036";
        totalHoraCaptura= 0;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                double hora = rs.getDouble("hora");
                totalHoraCaptura = (int)hora;
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void consultarTotalHoraParada()
        throws SQLException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "SELECT sum(abs(cast( datediff(mi, t037.hora_inicio, t037.hora_fin) / 60.0 as decimal (18,2)))) hora FROM t037_detalle_parada_ge t037";
        totalHoraParada= 0;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                double hora = rs.getDouble("hora");
                totalHoraParada = (int)hora;
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    private void createPieModel() {
        pieModel1 = new PieChartModel();
        pieModel1.set("Total horas trabajadas",totalHoraCaptura-totalHoraParada); 
        pieModel1.set("Total horas paradas",totalHoraParada); 
        pieModel1.setLegendPosition("");
        pieModel1.setShowDataLabels(true);
        pieModel1.setShadow(false);
        pieModel1.setSeriesColors("dd4b39,f39c12,00c0ef,00a65a,605ca8,a8abb2");
        pieModel1.setExtender("customExtender");
    }
}
