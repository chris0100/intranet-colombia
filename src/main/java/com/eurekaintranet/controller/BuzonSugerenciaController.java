/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T018BuzonSugerencia;
import com.eurekaintranet.entities.T019TipoSugerencia;
import com.eurekaintranet.sessions.T018BuzonSugerenciaFacade;
import com.eurekaintranet.sessions.T019TipoSugerenciaFacade;
import java.io.FileNotFoundException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Lccabalr
 */
@Named(value = "buzonSugerenciaController")
@SessionScoped
public class BuzonSugerenciaController implements Serializable {

    /**
     * Creates a new instance of BuzonSugerenciaController
     */
   
    
    @EJB
    private T018BuzonSugerenciaFacade buzonSugerenciaFacade;
    private T018BuzonSugerencia buzonSugerenciaActual;
    private List<T018BuzonSugerencia> listaBuzonSugerencia;
    @EJB
    private T019TipoSugerenciaFacade tipoSugerenciaFacade;
    private T019TipoSugerencia tipoSugerenciaActual;
    
    private EmailController emailController;
    private PieChartModel pieModel1;    
    private BarChartModel barModel1;
    private LineChartModel dateModel;
    
    private String descripcionTipo;
    private String codigoTipo;
    private T001Usuario usuarioActual;
    public BuzonSugerenciaController() {
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
    
    //Graficos
    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public void setPieModel1(PieChartModel pieModel1) {
        this.pieModel1 = pieModel1;
    }

    public BarChartModel getBarModel1() {
        return barModel1;
    }

    public void setBarModel1(BarChartModel barModel1) {
        this.barModel1 = barModel1;
    }

    public String getDescripcionTipo() {
        return descripcionTipo;
    }

    public void setDescripcionTipo(String descripcionTipo) {
        this.descripcionTipo = descripcionTipo;
    }

    public String getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipo(String codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    public LineChartModel getDateModel() {
        return dateModel;
    }

    public void setDateModel(LineChartModel dateModel) {
        this.dateModel = dateModel;
    }

    public boolean isPCO(){
        return buzonSugerenciaActual.getIdTipoSugerencia()== null? false : buzonSugerenciaActual.getIdTipoSugerencia().getIdTipoSugerencia().equals("PCO");
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public T019TipoSugerencia getTipoSugerenciaActual() {
        return tipoSugerenciaActual;
    }

    public void setTipoSugerenciaActual(T019TipoSugerencia tipoSugerenciaActual) {
        this.tipoSugerenciaActual = tipoSugerenciaActual;
    }

    public T019TipoSugerenciaFacade getTipoSugerenciaFacade() {
        return tipoSugerenciaFacade;
    }

    public void setTipoSugerenciaFacade(T019TipoSugerenciaFacade tipoSugerenciaFacade) {
        this.tipoSugerenciaFacade = tipoSugerenciaFacade;
    }

    

    //Email send
    
    public void sendMail() throws FileNotFoundException{
        emailController = new EmailController();
        String asunto = "BUZÓN DE SUGERENCIAS, "+buzonSugerenciaActual.getIdTipoSugerencia().getNombre();
        String cuerpo = 
                "Código: "+buzonSugerenciaActual.getIdBuzonSugerencia()
                +buzonSugerenciaActual.getIdTipoSugerencia().getIdTipoSugerencia()
                +"\nTipo de asunto: "
                +buzonSugerenciaActual.getIdTipoSugerencia().getNombre()
                +"\nNombre del usuario: "
                +buzonSugerenciaActual.getNombreUsuario()
                +"\nÁrea: "
                +buzonSugerenciaActual.getArea()
                +"\nDescripción: "
                +buzonSugerenciaActual.getDescripcion()
                +"\nFecha: "
                +buzonSugerenciaActual.getFechaCreacion().toLocaleString()
                +"\n"
                +"\n______________________________"
                +"\n";
        String adjunto = "";
        String nombreAdjunto = "";
        emailController.sendMail(buzonSugerenciaActual.getIdTipoSugerencia().getCorreoEnvio(), asunto, cuerpo);
    }

    //Navigation
    public void recargarLista(){
        listaBuzonSugerencia=null;
    }
     public String prepareEdit() {
        return "seguimiento";
    }
      public String prepareCreate() {
        buzonSugerenciaActual = new T018BuzonSugerencia();
        buzonSugerenciaActual.setCalificacion((short) 1);
        return "/usuario/modZonaAgil/buzonSugerencia";
    }

    public String prepareView() {
        descripcionTipo = buzonSugerenciaActual.getIdTipoSugerencia().getNombre();
        return "ver";
    }
    public String prepareDashboard() {
        recargarLista();
        createPieModel();
        createBarModel();
        createDateModel();
        return "/administrador/modGestorBuzonSugerencia/dashboard";
    }
    public String prepareList() {
        recargarLista();
        listaBuzonSugerencia = getBuzonSugerenciaFacade().sugerenciaTodos();
        return "/administrador/modGestorBuzonSugerencia/lista";
    }
    public String prepareListTipo(String tipo, String desTipo) {
        List<String> correoEnvio = getTipoSugerenciaFacade().obtenerCorreoEnvio(tipo);
        String correoString ="";
        for (String s : correoEnvio){
            correoString= s;
        }
        if(usuarioActual.getCorreo().trim().equals(correoString.trim())){
            recargarLista();
            listaBuzonSugerencia = getBuzonSugerenciaFacade().sugerenciaPorTipo(tipo);
            descripcionTipo = desTipo;
            codigoTipo = tipo;
            return "/administrador/modGestorBuzonSugerencia/listaTipo";
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acceso denegado", "No tiene permitido acceder a este tipo de sugerencias");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        }
    }
    public String add() {
        try { 
            if(buzonSugerenciaActual.getNombreUsuario().equals("")){
                buzonSugerenciaActual.setNombreUsuario("Anónimo");
            }
            buzonSugerenciaActual.setFechaCreacion(new Date());
            buzonSugerenciaActual.setEstado(false);
            getBuzonSugerenciaFacade().create(buzonSugerenciaActual);
            sendMail();
            addSuccessMessage("Sugerencia enviada", "La sugerencia fue enviada correctamente");
            recargarLista();
            return "/index";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            buzonSugerenciaActual.setFechaSeguimiento(new Date());
            getBuzonSugerenciaFacade().edit(buzonSugerenciaActual);
            addSuccessMessage("Seguimiento realizado", "Seguimiento realizado correctamente");
            recargarLista();
            return prepareListTipo(buzonSugerenciaActual.getIdTipoSugerencia().getIdTipoSugerencia(), buzonSugerenciaActual.getIdTipoSugerencia().getNombre());
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    private void createPieModel() {
        pieModel1 = new PieChartModel();
        pieModel1.set("Sugerencias o quejas a Gerencia",getBuzonSugerenciaFacade().contarTipoSugerencia("SGE")); 
        pieModel1.set("Sugerencias o quejas a Gestión Humana",getBuzonSugerenciaFacade().contarTipoSugerencia("SGH")); 
        pieModel1.set("Sugerencias o quejas relacionado con Planta KOS",getBuzonSugerenciaFacade().contarTipoSugerencia("SKO")); 
        pieModel1.set("Sugerencias o quejas a Operaciones",getBuzonSugerenciaFacade().contarTipoSugerencia("SOP")); 
        pieModel1.set("Sugerencias o quejas de riesgo y prevención",getBuzonSugerenciaFacade().contarTipoSugerencia("SRP")); 
        pieModel1.set("Sugerencias o quejas acerca de: temas varios - Servicios de la compañia",getBuzonSugerenciaFacade().contarTipoSugerencia("STS")); 
        pieModel1.setLegendPosition("");
        pieModel1.setShowDataLabels(true);
        pieModel1.setShadow(false);
        pieModel1.setSeriesColors("dd4b39,f39c12,00c0ef,00a65a,605ca8,a8abb2");
        pieModel1.setExtender("customExtender");
    }
    private void createBarModel() {
        
        barModel1 = initBarModel();
        barModel1.setAnimate(true);
        barModel1.setLegendPosition("");
        barModel1.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        barModel1.setSeriesColors("f39c12,dd4b39,00c0ef,605ca8");
        Axis yAxis = barModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setTickInterval("1");
        barModel1.setExtender("customExtender");
    }
    
     private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries uno = new ChartSeries();
        uno.setLabel("PQRS acerca de Eka Corporación");
        uno.set("1 estrella", getBuzonSugerenciaFacade().contarCalificaciones(1, "PEC")); 
        uno.set("2 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(2, "PEC"));
        uno.set("3 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(3, "PEC"));
        uno.set("4 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(4, "PEC"));
        uno.set("5 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(5, "PEC"));
 
        ChartSeries tres = new ChartSeries();
        tres.setLabel("Sugerencias sobre servicios de la intranet");
        tres.set("1 estrella", getBuzonSugerenciaFacade().contarCalificaciones(1, "SIN")); 
        tres.set("2 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(2, "SIN"));
        tres.set("3 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(3, "SIN"));
        tres.set("4 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(4, "SIN"));
        tres.set("5 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(5, "SIN"));
        
        ChartSeries cuatro = new ChartSeries();
        cuatro.setLabel("Comentarios o felicitaciones acerca de los servicios de intranet");
        cuatro.set("1 estrella", getBuzonSugerenciaFacade().contarCalificaciones(1, "CIN")); 
        cuatro.set("2 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(2, "CIN"));
        cuatro.set("3 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(3, "CIN"));
        cuatro.set("4 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(4, "CIN"));
        cuatro.set("5 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(5, "CIN"));
        
        ChartSeries cinco = new ChartSeries();
        cinco.setLabel("Comentarios adicionales y otros");
        cinco.set("1 estrella", getBuzonSugerenciaFacade().contarCalificaciones(1, "CAO")); 
        cinco.set("2 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(2, "CAO"));
        cinco.set("3 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(3, "CAO"));
        cinco.set("4 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(4, "CAO"));
        cinco.set("5 estrellas", getBuzonSugerenciaFacade().contarCalificaciones(5, "CAO"));
 
        model.addSeries(uno);
        model.addSeries(tres);
        model.addSeries(cuatro);
        model.addSeries(cinco);
        
        return model;
    }
    
    private void createDateModel() {
        dateModel = new LineChartModel();
        int cont = 0;
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String fecha1 = "";
        String fecha2 = "";
        LineChartSeries uno = new LineChartSeries();
        uno.setLabel("Sugerencias o quejas a Gerencia");
        for (T018BuzonSugerencia items : getBuzonSugerenciaFacade().sugerenciaPorTipo("SGE")) { 
            fecha1 = dateFormat1.format(items.getFechaCreacion());
            for (T018BuzonSugerencia items2 : getBuzonSugerenciaFacade().sugerenciaPorTipo("SGE")) { 
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
        dos.setLabel("Sugerencias o quejas a Gestión Humana");
        for (T018BuzonSugerencia items : getBuzonSugerenciaFacade().sugerenciaPorTipo("SGH")) { 
            fecha1 = dateFormat1.format(items.getFechaCreacion());
            for (T018BuzonSugerencia items2 : getBuzonSugerenciaFacade().sugerenciaPorTipo("SGH")) { 
                fecha2= dateFormat2.format(items2.getFechaCreacion());
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
        LineChartSeries tres = new LineChartSeries();
        tres.setLabel("Sugerencias o quejas relacionado con Planta KOS");
        for (T018BuzonSugerencia items : getBuzonSugerenciaFacade().sugerenciaPorTipo("SKO")) { 
            fecha1 = dateFormat1.format(items.getFechaCreacion());
            for (T018BuzonSugerencia items2 : getBuzonSugerenciaFacade().sugerenciaPorTipo("SKO")) { 
                fecha2= dateFormat2.format(items2.getFechaCreacion());
                if(fecha1.equals(fecha2)){
                    cont++;
                }
            }
            tres.set(fecha1, cont);
            cont=0;
        }
        cont = 0;
        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        fecha1 = "";
        fecha2 = "";
        LineChartSeries cuatro = new LineChartSeries();
        cuatro.setLabel("Sugerencias o quejas a Operaciones");
        for (T018BuzonSugerencia items : getBuzonSugerenciaFacade().sugerenciaPorTipo("SOP")) { 
            fecha1 = dateFormat1.format(items.getFechaCreacion());
            for (T018BuzonSugerencia items2 : getBuzonSugerenciaFacade().sugerenciaPorTipo("SOP")) { 
                fecha2= dateFormat2.format(items2.getFechaCreacion());
                if(fecha1.equals(fecha2)){
                    cont++;
                }
            }
            cuatro.set(fecha1, cont);
            cont=0;
        }
        cont = 0;
        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        fecha1 = "";
        fecha2 = "";
        LineChartSeries cinco = new LineChartSeries();
        cinco.setLabel("Sugerencias o quejas de riesgo y prevención");
        for (T018BuzonSugerencia items : getBuzonSugerenciaFacade().sugerenciaPorTipo("SRP")) { 
            fecha1 = dateFormat1.format(items.getFechaCreacion());
            for (T018BuzonSugerencia items2 : getBuzonSugerenciaFacade().sugerenciaPorTipo("SRP")) { 
                fecha2= dateFormat2.format(items2.getFechaCreacion());
                if(fecha1.equals(fecha2)){
                    cont++;
                }
            }
            cinco.set(fecha1, cont);
            cont=0;
        }
        cont = 0;
        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        fecha1 = "";
        fecha2 = "";
        LineChartSeries seis = new LineChartSeries();
        seis.setLabel("Sugerencias o quejas acerca de: temas varios - Servicios de la compañia");
        for (T018BuzonSugerencia items : getBuzonSugerenciaFacade().sugerenciaPorTipo("STS")) { 
            fecha1 = dateFormat1.format(items.getFechaCreacion());
            for (T018BuzonSugerencia items2 : getBuzonSugerenciaFacade().sugerenciaPorTipo("STS")) { 
                fecha2= dateFormat2.format(items2.getFechaCreacion());
                if(fecha1.equals(fecha2)){
                    cont++;
                }
            }
            seis.set(fecha1, cont);
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
        dateModel.setSeriesColors("dd4b39,f39c12,00c0ef,00a65a,605ca8,a8abb2");
        dateModel.addSeries(uno);
        dateModel.addSeries(dos);
        dateModel.addSeries(tres);
        dateModel.addSeries(cuatro);
        dateModel.addSeries(cinco);
        dateModel.addSeries(seis);
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
   
}
