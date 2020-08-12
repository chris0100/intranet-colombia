/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T025CentroTrabajo;
import com.eurekaintranet.entities.T026ConfiguracionCentroTrabajo;
import com.eurekaintranet.entities.T027Periodo;
import com.eurekaintranet.entities.T029ProgramadorGantt;
import com.eurekaintranet.sessions.T027PeriodoFacade;
import com.eurekaintranet.sessions.T029ProgramadorGanttFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lccabalr
 */
@Named(value = "programadorGanttController")
@SessionScoped
public class ProgramadorGanttController implements Serializable {

    @EJB
    private T029ProgramadorGanttFacade programadorGanttFacade;
    private T029ProgramadorGantt programadorGanttActual;
    private List<T029ProgramadorGantt> listaProgramadorGantt;
    @EJB
    private T027PeriodoFacade periodoFacade;
    private T027Periodo periodoActual;
    
    private T025CentroTrabajo centroTrabajoActual;
    private T026ConfiguracionCentroTrabajo configuracionActual;
    private int periodo;
    
    private String[] diasSinProgramar;
    private List<String> listaDias;
    
    private List<String> listaCentroTrabajoProgramado;
    
    private List< Map<String, ColumnModel> > tableData; 
    private List<ColumnModel> tableHeaderNames; 
    private String[] detalle ;
    private String[] centroTrabajoDescripcion ;
    
    
    private DetalleProgramadorGanttController detalleProgramadorGanttActual;
    private List<DetalleProgramadorGanttController> listaDetalleProgramadorGantt;
    private String centroTrabajo;
    private int contarDiasPeriodo = 0;
    
    BigDecimal ca= new BigDecimal(0); //contador de cantidad pendiente dias anteriores
    BigDecimal ch= new BigDecimal(0); //contador de cantidad pendiente hoy
    BigDecimal cm= new BigDecimal(0); //contador de cantidad pendiente dias posteriores
    
    BigDecimal ia= new BigDecimal(0); //contador de capacidad dias anteriores
    BigDecimal ih= new BigDecimal(0); //contador de capacidad hoy
    BigDecimal im= new BigDecimal(0); //contador de capacidad dias posteriores
    int da =0;  //contador de dias anteriores
    int dh =0;  //contador de dias hoy
    int dm= 0;  //contador de dias mañana
    
    
    public ProgramadorGanttController() {
    }

    public T029ProgramadorGanttFacade getProgramadorGanttFacade() {
        return programadorGanttFacade;
    }

    public void setProgramadorGanttFacade(T029ProgramadorGanttFacade programadorGanttFacade) {
        this.programadorGanttFacade = programadorGanttFacade;
    }

    public T029ProgramadorGantt getProgramadorGanttActual() {
        return programadorGanttActual;
    }

    public void setProgramadorGanttActual(T029ProgramadorGantt programadorGanttActual) {
        this.programadorGanttActual = programadorGanttActual;
    }

    public List<T029ProgramadorGantt> getListaProgramadorGantt() {
        return listaProgramadorGantt;
    }
    
    public List<T029ProgramadorGantt> getListaProgramadorGanttCentroTrabajo(String centroTrabajo) {
        return listaProgramadorGantt = getProgramadorGanttFacade().periodoProgramadoGantt(centroTrabajo);
    }

    public void setListaProgramadorGantt(List<T029ProgramadorGantt> listaProgramadorGantt) {
        this.listaProgramadorGantt = listaProgramadorGantt;
    }

    public T025CentroTrabajo getCentroTrabajoActual() {
        return centroTrabajoActual;
    }

    public void setCentroTrabajoActual(T025CentroTrabajo centroTrabajoActual) {
        this.centroTrabajoActual = centroTrabajoActual;
    }

    public T027Periodo getPeriodoActual() {
        return periodoActual;
    }

    public void setPeriodoActual(T027Periodo periodoActual) {
        this.periodoActual = periodoActual;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }
    public void consultarPeriodoProgramado(){
        listaProgramadorGantt = getProgramadorGanttFacade().periodoProgramadoCentroTrabajo(periodoActual.getIdPeriodo(), centroTrabajoActual.getIdCentroTrabajo());
    }

    public T026ConfiguracionCentroTrabajo getConfiguracionActual() {
        return configuracionActual;
    }

    public void setConfiguracionActual(T026ConfiguracionCentroTrabajo configuracionActual) {
        this.configuracionActual = configuracionActual;
    }

    public String[] getDiasSinProgramar() {
        return diasSinProgramar;
    }

    public void setDiasSinProgramar(String[] diasSinProgramar) {
        this.diasSinProgramar = diasSinProgramar;
    }

    public List<String> getListaDias() {
        return listaDias;
    }

    public void setListaDias(List<String> listaDias) {
        this.listaDias = listaDias;
    }

    public List<String> getListaCentroTrabajoProgramado() {
        return listaCentroTrabajoProgramado;
    }

    public void setListaCentroTrabajoProgramado(List<String> listaCentroTrabajoProgramado) {
        this.listaCentroTrabajoProgramado = listaCentroTrabajoProgramado;
    }

    public String[] getCentroTrabajoDescripcion() {
        return centroTrabajoDescripcion;
    }

    public void setCentroTrabajoDescripcion(String[] centroTrabajoDescripcion) {
        this.centroTrabajoDescripcion = centroTrabajoDescripcion;
    }

    public List<DetalleProgramadorGanttController> getListaDetalleProgramadorGantt() {
        return listaDetalleProgramadorGantt;
    }
   

    public void setListaDetalleProgramadorGantt(List<DetalleProgramadorGanttController> listaDetalleProgramadorGantt) {
        this.listaDetalleProgramadorGantt = listaDetalleProgramadorGantt;
    }    

    public String getCentroTrabajo() {
        return centroTrabajo;
    }

    public void setCentroTrabajo(String centroTrabajo) {
        this.centroTrabajo = centroTrabajo;
    }

    public T027PeriodoFacade getPeriodoFacade() {
        return periodoFacade;
    }

    public void setPeriodoFacade(T027PeriodoFacade periodoFacade) {
        this.periodoFacade = periodoFacade;
    }
    
    //Navegacion
     public void recargarLista(){
        listaProgramadorGantt=null;
        listaDias = new ArrayList<>();
        listaCentroTrabajoProgramado = new ArrayList<>();
    }
     public void prepareEdit() {
        diasSinProgramar = null;
        configuracionActual = null;
    }
    public String prepareCreate() {
        programadorGanttActual = new T029ProgramadorGantt();
        periodoActual = new T027Periodo();
        listaProgramadorGantt = new ArrayList<>();
        return "programarFechas";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() throws SQLException {
        recargarLista();
        listaProgramadorGantt = getProgramadorGanttFacade().periodoProgramadoFecha(obtenerPeriodoInicialGantt(), obtenerPeriodoFinGantt());
        consultarFechasPeriodoGantt();
        consultarCentroTrabajoProgramado();
        detalle = new String[listaProgramadorGantt.size()];
        centroTrabajoDescripcion = new String[listaCentroTrabajoProgramado.size()];
        for (int i = 0; i < listaProgramadorGantt.size(); i++) {
             detalle[i] = listaProgramadorGantt.get(i).getColor();
        }
        try {
           crearTablaGantt(); 
        } catch (Exception e) {
            addErrorMessage("Error en la generación del Gantt","Los centros de trabajo no cuentan con los días del mes configurados");
        }
        return "/administrador/modPlaneacionKos/gantt";
    }
    public String add() {
        try { 
            getProgramadorGanttFacade().create(programadorGanttActual);
            addSuccessMessage("Periodo creado correctamente", "Periodo creado correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public void update() {
        try {
            getProgramadorGanttFacade().edit(programadorGanttActual);
            addSuccessMessage("Periodo editado correctamente", "Periodo editado correctamente");
            consultarPeriodoProgramado();
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
    }
    public String prepareDetalle() throws SQLException{
        consultarDetalleGantt();
        return "detalleGantt";
    }
    public void updateMes() {
        try {
            boolean diaEncontrado ;
            for(T029ProgramadorGantt item:listaProgramadorGantt){
                diaEncontrado = false;
                for (int i = 0; i < diasSinProgramar.length; i++){
                   if(diasSinProgramar[i].equals(item.getDescripcionDia())){
                       diaEncontrado = true;
                   }
                }
                if(!diaEncontrado){
                    item.setIdConfiguracion(configuracionActual);
                    getProgramadorGanttFacade().edit(item);
                }
            }
            diasSinProgramar=null;
            addSuccessMessage("Periodo editado correctamente", "Periodo editado correctamente");
            consultarPeriodoProgramado();
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
    }
    public void calcularFechas(){
        if(listaProgramadorGantt.isEmpty()){
            Calendar c1 = Calendar.getInstance();
            int anio = getPeriodoActual().getAnio();
            int mes = getPeriodoActual().getMes();
            Date fecha;
            String dia;
            for(int i=1;i<=diasDelMes(mes,anio);i+=1){
                c1.set(anio, mes-1, i);
                fecha = c1.getTime();
                switch(fecha.getDay()){
                    case 1 : dia = "Lunes";
                            break;
                    case 2 : dia = "Martes";
                            break;
                    case 3 : dia = "Miercoles";
                            break;
                    case 4 : dia = "Jueves";
                            break;
                    case 5 : dia = "Viernes";
                            break;
                    case 6 : dia = "Sabado";
                            break;
                    default : dia = "Domingo";
                            break;
                }
                programadorGanttActual = new T029ProgramadorGantt();
                programadorGanttActual.setIdPeriodo((T027Periodo)periodoActual);
                programadorGanttActual.setIdCentroTrabajo((T025CentroTrabajo)centroTrabajoActual);
                programadorGanttActual.setFecha(fecha);
                programadorGanttActual.setDescripcionDia(dia);
                programadorGanttActual.setColor("GRIS");
                getProgramadorGanttFacade().create(programadorGanttActual);
                consultarPeriodoProgramado();
            } 
        }else{
            addErrorMessage("Error", "Ya se guardaron las fechas");
        }
    }
    public int diasDelMes(int mes, int año){
        switch(mes){
            case 1:  // Enero
            case 3:  // Marzo
            case 5:  // Mayo
            case 7:  // Julio
            case 8:  // Agosto
            case 10:  // Octubre
            case 12: // Diciembre
                return 31;
            case 4:  // Abril
            case 6:  // Junio
            case 9:  // Septiembre
            case 11: // Noviembre
                return 30;
            case 2:  // Febrero
                if ( ((año%100 == 0) && (año%400 == 0)) ||
                        ((año%100 != 0) && (año%  4 == 0))   )
                    return 29;  // Año Bisiesto
                else
                    return 28;
            default:
                throw new java.lang.IllegalArgumentException(
                "El mes debe estar entre 1 y 12");
        }
    }
    //Centros de trabajo
    public void consultarCentroTrabajoProgramado() throws SQLException{
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "SELECT t025.nombre centro_trabajo FROM [BD_Eureka].[dbo].[t029_programador_gantt] t029 inner join  [BD_Eureka].[dbo].[t025_centro_trabajo] t025 on t025.id_centro_trabajo = t029.id_centro_trabajo group by t029.[id_centro_trabajo], t025.nombre order by t025.nombre";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                centroTrabajo = rs.getString("centro_trabajo").trim();
                listaCentroTrabajoProgramado.add(centroTrabajo);
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    //fechas ganctt
    public String obtenerPeriodoInicialGantt(){
        Calendar ci = Calendar.getInstance();
        String diaInicio = String.format("%2s", String.valueOf(ci.get(Calendar.DAY_OF_MONTH))).replace(' ','0');
        String mesInicio = String.format("%2s",String.valueOf(ci.get(Calendar.MONTH)+1)).replace(' ','0');
        String anioInicio = String.valueOf(ci.get(Calendar.YEAR));
        return anioInicio+mesInicio+diaInicio;
    }
    public String obtenerPeriodoFinGantt(){
        Calendar cf = Calendar.getInstance();
        cf.setTime(new Date()); 
        cf.add(Calendar.DAY_OF_YEAR, 30);
        String diaFin = String.format("%2s",String.valueOf(cf.get(Calendar.DAY_OF_MONTH))).replace(' ','0');
        String mesFin = String.format("%2s",String.valueOf(cf.get(Calendar.MONTH)+1)).replace(' ','0');
        String anioFin = String.valueOf(cf.get(Calendar.YEAR));
        return anioFin+mesFin+diaFin;
    }
    public void consultarFechasPeriodoGantt(){
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(new Date());
        for(int i=0;i<31;i++){
            listaDias.add(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
            contarDiasPeriodo ++;
            cal.add(Calendar.DATE, 1);
        }
    }
    //obtener periodo actual
    public int obtenerPeridoActual(){
        Calendar c1 = Calendar.getInstance();
        int mes = c1.get(Calendar.MONTH)+1;
        int anio = c1.get(Calendar.YEAR);
        return getPeriodoFacade().consultaPeriodoActual(mes, anio).getIdPeriodo();
    }
    //Detalle Gantt
    public void consultarDetalleGantt() throws SQLException{
        String connectionUrl ="jdbc:sqlserver://kosconnect.siesacloud.com:20446;databaseName=UnoEE_KosColombia_Real;user=kos;password=Kos$12$%";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "SELECT t808.f808_id ruta_operacion " +
        "	  ,t851.f851_fecha_inicio fecha_inicio " +
        "	  ,t851.f851_fecha_terminacion fecha_terminacion " +
        "	  ,t850.f850_id_tipo_docto tipo_documento " +
        "	  ,t850.f850_consec_docto consecutivo " +
        "	  ,v121.v121_referencia referencia " +
        "	  ,v121.v121_descripcion descripcion_item " +
        "	  ,v121.v121_id_ext1_detalle ext1 " +
        "	  ,t117.f117_descripcion desc_ext1 " +
        "	  ,v121.v121_id_ext2_detalle ext2 " +
        "	  ,t119.f119_descripcion desc_ext2 " +
        "	  ,v121.v121_id_unidad_inventario und " +
        "	  ,t851.f851_cant_planeada_1 cant_planeada " +
        "	  ,t851.f851_cant_planeada_1 - t851.f851_cant_completa_1 as cant_pendiente " +
        "  FROM [UnoEE_KosColombia_Real].[dbo].[t851_mf_op_docto_item] t851 " +
        "  inner join [UnoEE_KosColombia_Real].[dbo].[t850_mf_op_docto] t850 on t850.[f850_rowid] = t851.[f851_rowid_op_docto] " +
        "  inner join [UnoEE_KosColombia_Real].[dbo].v121 v121 on v121.v121_rowid_item_ext = t851.f851_rowid_item_ext_padre " +
        "  inner join [UnoEE_KosColombia_Real].[dbo].[t132_mc_items_instalacion] t132 on t132.f132_rowid_item_ext = t851.f851_rowid_item_ext_padre and t132.f132_id_instalacion =  t851.f851_id_instalacion " +
        "  inner join [UnoEE_KosColombia_Real].[dbo].[t809_mf_rutas_operacion] t809 on t809.f809_rowid_rutas = t132.f132_mf_rowid_ruta and f809_numero_operacion = '10' and f809_id_metodo = 1 " +
        "  inner join [UnoEE_KosColombia_Real].[dbo].t808_mf_rutas t808 on t808.f808_rowid = t809.f809_rowid_rutas " +
        "  inner join [UnoEE_KosColombia_Real].[dbo].[t806_mf_centros_trabajo] t806 on t806.f806_rowid = t809.f809_rowid_ctrabajo " +
        "  inner join [UnoEE_KosColombia_Real].[dbo].[t117_mc_extensiones1_detalle] t117 on t117.[f117_id] = v121.v121_id_ext1_detalle and f117_id_extension1 = 'MK' " +
        "  inner join [UnoEE_KosColombia_Real].[dbo].[t119_mc_extensiones2_detalle] t119 on t119.[f119_id] = v121.v121_id_ext2_detalle and f119_id_extension2 = 'CK' " +
        "  WHERE t806.f806_descripcion = '"+centroTrabajo.trim()+"' AND t850.f850_id_tipo_docto = 'OPK' AND (t851.f851_ind_estado = 1 OR t851.f851_ind_estado = 0 OR t851.f851_ind_estado = 2) " +
        "  AND (t851.f851_cant_planeada_1 - t851.f851_cant_completa_1) >0" +
        "  ORDER BY t851.f851_fecha_terminacion";
        
        //String query = "exec sp_obtener_detalle_gantt_kos '"+centroTrabajo.trim()+"'";
        listaDetalleProgramadorGantt = new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                detalleProgramadorGanttActual = new DetalleProgramadorGanttController();
                detalleProgramadorGanttActual.setRutaOperacion(rs.getString("ruta_operacion"));
                detalleProgramadorGanttActual.setFechaInicio(rs.getDate("fecha_inicio"));
                detalleProgramadorGanttActual.setFechaTerminacion(rs.getDate("fecha_terminacion"));
                detalleProgramadorGanttActual.setTipoDocumento(rs.getString("tipo_documento"));
                detalleProgramadorGanttActual.setNumeroDocumento(rs.getInt("consecutivo"));
                detalleProgramadorGanttActual.setReferencia(rs.getString("referencia"));
                detalleProgramadorGanttActual.setDescItem(rs.getString("descripcion_item"));
                detalleProgramadorGanttActual.setIdExt1(rs.getString("ext1"));
                detalleProgramadorGanttActual.setDescExt1(rs.getString("desc_ext1"));
                detalleProgramadorGanttActual.setIdExt2(rs.getString("ext2"));
                detalleProgramadorGanttActual.setDescExt2(rs.getString("desc_ext2"));
                detalleProgramadorGanttActual.setUnd(rs.getString("und"));
                detalleProgramadorGanttActual.setCantPlaneada(rs.getBigDecimal("cant_planeada"));
                detalleProgramadorGanttActual.setCantPendiente(rs.getBigDecimal("cant_pendiente"));
                
                Date fechaActual = new Date();
                Date fechaConsulta = detalleProgramadorGanttActual.getFechaTerminacion(); 
                DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");  
                String fechaActualFormato = dateFormat1.format(new Date());
                String fechaConsultaFormato = dateFormat1.format(fechaConsulta);
                if(fechaConsulta.before(fechaActual)){
                    detalleProgramadorGanttActual.setColor("ROJO");
                    if(fechaConsultaFormato.equals(fechaActualFormato)){
                        detalleProgramadorGanttActual.setColor("NARANJA");
                    }
                }else{
                    detalleProgramadorGanttActual.setColor("VERDE");
                }
                listaDetalleProgramadorGantt.add(detalleProgramadorGanttActual);
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    //Sincronizar Gantt
    public void consultarDatosGantt() throws SQLException{
        String connectionUrl ="jdbc:sqlserver://kosconnect.siesacloud.com:20446;databaseName=UnoEE_KosColombia_Real;user=kos;password=Kos$12$%";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        for(T029ProgramadorGantt item: listaProgramadorGantt){
            DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");  
            String fecha = dateFormat1.format(item.getFecha());
            String query = "SELECT sum(t851.f851_cant_planeada_1 - t851.f851_cant_completa_1) as cant_pendiente " +
            "	  FROM [UnoEE_KosColombia_Real].[dbo].[t851_mf_op_docto_item] t851 " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t850_mf_op_docto] t850 on t850.[f850_rowid] = t851.[f851_rowid_op_docto] " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].v121 v121 on v121.v121_rowid_item_ext = t851.f851_rowid_item_ext_padre " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t132_mc_items_instalacion] t132 on t132.f132_rowid_item_ext = t851.f851_rowid_item_ext_padre and t132.f132_id_instalacion =  t851.f851_id_instalacion " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t809_mf_rutas_operacion] t809 on t809.f809_rowid_rutas = t132.f132_mf_rowid_ruta and f809_numero_operacion = '10' and f809_id_metodo = 1 " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].t808_mf_rutas t808 on t808.f808_rowid = t809.f809_rowid_rutas " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t806_mf_centros_trabajo] t806 on t806.f806_rowid = t809.f809_rowid_ctrabajo " +
            "	  where t806.f806_descripcion = '"+item.getIdCentroTrabajo().getNombre()+"' " +
            "	  AND t850.f850_id_tipo_docto = 'OPK' " +
            "	  AND (t851.f851_ind_estado = 1 OR t851.f851_ind_estado = 0 OR t851.f851_ind_estado = 2) " +
            "	  AND t851.f851_fecha_terminacion = '"+fecha+"' " +
            "	  GROUP BY t851.f851_fecha_terminacion " +
            "	  HAVING sum(t851.f851_cant_planeada_1 - t851.f851_cant_completa_1) > 0 " +
            "	  order by t851.f851_fecha_terminacion";
            //String query = "exec sp_obtener_datos_gantt_kos '"+item.getIdCentroTrabajo().getNombre()+"' , '"+fecha+"'";
            listaDetalleProgramadorGantt = new ArrayList<>();   
            try {
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    item.setCantPendiente(rs.getBigDecimal("cant_pendiente"));
                    getProgramadorGanttFacade().edit(item);
                }
            } catch (SQLException e ) {
                System.out.println("error "+e);
            } finally {
                if (stmt != null) { stmt.close(); }
            }
        }
    }
    //Sincronizar Gantt
    public void consultarCantAnteriorGantt(String centroTrabajo) throws SQLException{
        String connectionUrl ="jdbc:sqlserver://kosconnect.siesacloud.com:20446;databaseName=UnoEE_KosColombia_Real;user=kos;password=Kos$12$%";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
            DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");  
            String fecha = dateFormat1.format(new Date());
            String query = "SELECT sum(t851.f851_cant_planeada_1 - t851.f851_cant_completa_1) as cant_pendiente " +
            "	  FROM [UnoEE_KosColombia_Real].[dbo].[t851_mf_op_docto_item] t851 " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t850_mf_op_docto] t850 on t850.[f850_rowid] = t851.[f851_rowid_op_docto] " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].v121 v121 on v121.v121_rowid_item_ext = t851.f851_rowid_item_ext_padre " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t132_mc_items_instalacion] t132 on t132.f132_rowid_item_ext = t851.f851_rowid_item_ext_padre and t132.f132_id_instalacion =  t851.f851_id_instalacion " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t809_mf_rutas_operacion] t809 on t809.f809_rowid_rutas = t132.f132_mf_rowid_ruta and f809_numero_operacion = '10' and f809_id_metodo = 1 " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].t808_mf_rutas t808 on t808.f808_rowid = t809.f809_rowid_rutas " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t806_mf_centros_trabajo] t806 on t806.f806_rowid = t809.f809_rowid_ctrabajo " +
            "	  WHERE t806.f806_descripcion = '"+centroTrabajo+"' AND t850.f850_id_tipo_docto = 'OPK' AND (t851.f851_ind_estado = 1 OR t851.f851_ind_estado = 0 OR t851.f851_ind_estado = 2) " +
            "	  AND (t851.f851_cant_planeada_1 - t851.f851_cant_completa_1) >0 " +
            "	  AND t851.f851_fecha_terminacion < '"+fecha+"'";
            
            //String query = "exec sp_obtener_cant_anterior_gantt_kos '"+centroTrabajo+"','"+fecha+"'";    
            try {
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    ca = (rs.getBigDecimal("cant_pendiente"));
                }
            } catch (SQLException e ) {
                System.out.println("error "+e);
            } finally {
                if (stmt != null) { stmt.close(); }
            }
    }
    public void consultarCantHoyGantt(String centroTrabajo) throws SQLException{
        String connectionUrl ="jdbc:sqlserver://kosconnect.siesacloud.com:20446;databaseName=UnoEE_KosColombia_Real;user=kos;password=Kos$12$%";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
            DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");  
            String fecha = dateFormat1.format(new Date());
            
            String query = "SELECT sum(t851.f851_cant_planeada_1 - t851.f851_cant_completa_1) as cant_pendiente " +
            "	  FROM [UnoEE_KosColombia_Real].[dbo].[t851_mf_op_docto_item] t851 " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t850_mf_op_docto] t850 on t850.[f850_rowid] = t851.[f851_rowid_op_docto] " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].v121 v121 on v121.v121_rowid_item_ext = t851.f851_rowid_item_ext_padre " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t132_mc_items_instalacion] t132 on t132.f132_rowid_item_ext = t851.f851_rowid_item_ext_padre and t132.f132_id_instalacion =  t851.f851_id_instalacion " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t809_mf_rutas_operacion] t809 on t809.f809_rowid_rutas = t132.f132_mf_rowid_ruta and f809_numero_operacion = '10' and f809_id_metodo = 1 " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].t808_mf_rutas t808 on t808.f808_rowid = t809.f809_rowid_rutas " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t806_mf_centros_trabajo] t806 on t806.f806_rowid = t809.f809_rowid_ctrabajo " +
            "	  WHERE t806.f806_descripcion = '"+centroTrabajo+"' AND t850.f850_id_tipo_docto = 'OPK' AND (t851.f851_ind_estado = 1 OR t851.f851_ind_estado = 0 OR t851.f851_ind_estado = 2) " +
            "	  AND (t851.f851_cant_planeada_1 - t851.f851_cant_completa_1) >0 " +
            "	  AND t851.f851_fecha_terminacion = '"+fecha+"'";
            
            //String query = "exec sp_obtener_cant_hoy_gantt_kos '"+centroTrabajo+"','"+fecha+"'";   
            try {
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    ch = (rs.getBigDecimal("cant_pendiente"));
                }
            } catch (SQLException e ) {
                System.out.println("error "+e);
            } finally {
                if (stmt != null) { stmt.close(); }
            }
    }
    public void consultarCantMananaGantt(String centroTrabajo) throws SQLException{
        String connectionUrl ="jdbc:sqlserver://kosconnect.siesacloud.com:20446;databaseName=UnoEE_KosColombia_Real;user=kos;password=Kos$12$%";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
            DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");  
            String fecha = dateFormat1.format(new Date());
            
            String query = "SELECT sum(t851.f851_cant_planeada_1 - t851.f851_cant_completa_1) as cant_pendiente " +
            "	  FROM [UnoEE_KosColombia_Real].[dbo].[t851_mf_op_docto_item] t851 " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t850_mf_op_docto] t850 on t850.[f850_rowid] = t851.[f851_rowid_op_docto] " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].v121 v121 on v121.v121_rowid_item_ext = t851.f851_rowid_item_ext_padre " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t132_mc_items_instalacion] t132 on t132.f132_rowid_item_ext = t851.f851_rowid_item_ext_padre and t132.f132_id_instalacion =  t851.f851_id_instalacion " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t809_mf_rutas_operacion] t809 on t809.f809_rowid_rutas = t132.f132_mf_rowid_ruta and f809_numero_operacion = '10' and f809_id_metodo = 1 " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].t808_mf_rutas t808 on t808.f808_rowid = t809.f809_rowid_rutas " +
            "	  inner join [UnoEE_KosColombia_Real].[dbo].[t806_mf_centros_trabajo] t806 on t806.f806_rowid = t809.f809_rowid_ctrabajo " +
            "	  WHERE t806.f806_descripcion = '"+centroTrabajo+"' AND t850.f850_id_tipo_docto = 'OPK' AND (t851.f851_ind_estado = 1 OR t851.f851_ind_estado = 0 OR t851.f851_ind_estado = 2) " +
            "	  AND (t851.f851_cant_planeada_1 - t851.f851_cant_completa_1) >0 " +
            "	  AND t851.f851_fecha_terminacion > '"+fecha+"'";

            //String query = "exec sp_obtener_cant_manana_gantt_kos '"+centroTrabajo+"','"+fecha+"'";   
            try {
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    cm = (rs.getBigDecimal("cant_pendiente"));
                }
            } catch (SQLException e ) {
                System.out.println("error "+e);
            } finally {
                if (stmt != null) { stmt.close(); }
            }
    }
    //obtener total cantidades pendientes 
    public void obtenerCantidadesPendientesGantt(String nombreCentroTrabajo) throws SQLException{
        ca= new BigDecimal(0);
        ch= new BigDecimal(0);
        cm= new BigDecimal(0);
        //ayer
        consultarCantAnteriorGantt(nombreCentroTrabajo);
        //hoy
        consultarCantHoyGantt(nombreCentroTrabajo);
        //mañana
        consultarCantMananaGantt(nombreCentroTrabajo);
        if(ca==null){
           ca= new BigDecimal(0); 
        }
        if(ch==null){
            ch= new BigDecimal(0);
        }
        if(cm==null){
            cm= new BigDecimal(0);
        }
        ////System.out.println("PRUEBA CA ="+ca +  " CH = "+ch + "  CM = "+cm);
    }
    //obtener total cantidades pendientes
     public void obtenerDiasGantt() throws SQLException{
        Calendar calendar =Calendar.getInstance(); //obtiene la fecha de hoy 
        calendar.add(Calendar.DATE, -1); //el -1 indica que se le restaran 1 dia
        int contarDias = 1;
        for(T029ProgramadorGantt item:listaProgramadorGantt){
            //System.out.println("PRUEBA LISTA "+item.getFecha().toLocaleString()+ " ---- "+item.getIdCentroTrabajo().getIdCentroTrabajo());
        }
        for(T029ProgramadorGantt item: listaProgramadorGantt){
            if(contarDias > contarDiasPeriodo){
                //System.out.println("PRUEBA DIAS PERIODO "+contarDias+" > "+contarDiasPeriodo+ "CENTRO TRABAJO "+item.getIdCentroTrabajo().getNombre());
                contarDias = 1;
            }
            if(contarDias==1){
                obtenerCantidadesPendientesGantt(item.getIdCentroTrabajo().getNombre());
                //System.out.println("PRUEBA DIAS PERIODO "+contarDias+" = "+contarDiasPeriodo);
            }
            contarDias ++;
            if(item.getFecha().before(calendar.getTime())){
               item.setColor("NEGRO");
               getProgramadorGanttFacade().edit(item);
            }else
            if(ca.compareTo(new BigDecimal(0))>0 && item.getIdConfiguracion()!=null){
                ia = ca.divide(new BigDecimal(item.getIdConfiguracion().getCargaProyectada()), 4, RoundingMode.HALF_UP);
                if(ia.compareTo(new BigDecimal(1))>0){
                    da ++;
                    ca = ca.subtract(new BigDecimal(item.getIdConfiguracion().getCargaProyectada()));
                    item.setColor("ROJO");
                    getProgramadorGanttFacade().edit(item);
                    //System.out.println("PRUEBA ayer ca > 1: "+ ca +" ------ia = "+ia+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color "+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                 }else if(ia.compareTo(new BigDecimal(0.5))>0){
                    da ++;
                    ca = new BigDecimal(0);
                    item.setColor("ROJO");
                    getProgramadorGanttFacade().edit(item);
                    //System.out.println("PRUEBA ayer ca > 0.5: "+ ca+" ------ia = "+ia+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color "+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                }else{
                    ca = new BigDecimal(0);
                    //System.out.println("PRUEBA ayer ca < 0.5: "+ ca+" ------ia = "+ia+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color "+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                    //iniciar 
                    if(ch.compareTo(new BigDecimal(0))>0 && item.getIdConfiguracion()!=null && item.getIdConfiguracion()!=null){
                        ih = ch.divide(new BigDecimal(item.getIdConfiguracion().getCargaProyectada()), 4, RoundingMode.HALF_UP);
                        if(ih.compareTo(new BigDecimal(1))>0){
                            dh ++;
                            ch = ch.subtract(new BigDecimal(item.getIdConfiguracion().getCargaProyectada()));
                            item.setColor("NARANJA");
                            getProgramadorGanttFacade().edit(item);
                            //System.out.println("PRUEBA hoy ch < 0.5: "+ ch+" ------ih = "+ih+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia HOYY"+item.getDescripcionDia()+"----color "+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                        }else if(ih.compareTo(new BigDecimal(0.5))>0){
                            dh ++;
                            ch = new BigDecimal(0);
                            item.setColor("NARANJA");
                            getProgramadorGanttFacade().edit(item);
                            //System.out.println("PRUEBA hoy ch < 0.5: "+ ch+" ------ih = "+ih+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia HOYY"+item.getDescripcionDia()+"----color "+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                        }else{
                            ch = new BigDecimal(0);
                            //System.out.println("PRUEBA hoy ch < 0.5: "+ ch+" ------ih = "+ih+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia HOYY"+item.getDescripcionDia()+"----color "+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                             //iniciar
                            if(cm.compareTo(new BigDecimal(0))>0 && item.getIdConfiguracion()!=null && contarDias <= contarDiasPeriodo){
                                im = cm.divide(new BigDecimal(item.getIdConfiguracion().getCargaProyectada()), 4, RoundingMode.HALF_UP);
                                if(im.compareTo(new BigDecimal(1))>0){
                                    dm ++;
                                    cm = cm.subtract(new BigDecimal(item.getIdConfiguracion().getCargaProyectada()));
                                    item.setColor("VERDE");
                                    getProgramadorGanttFacade().edit(item);
                                    //System.out.println("PRUEBA mañana cm < 0.5: "+ cm+" ------im = "+im +"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color 0"+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                                }else if(im.compareTo(new BigDecimal(0.5))>0){
                                    dm ++;
                                    cm = new BigDecimal(0);
                                    item.setColor("VERDE");
                                    getProgramadorGanttFacade().edit(item);
                                    //System.out.println("PRUEBA mañana cm < 0.5: "+ cm+" ------im = "+im+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color 1"+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                                }else{
                                    //System.out.println("PRUEBA mañana cm < 0.5: "+ cm+" ------im = "+im+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color 2"+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                                    cm = new BigDecimal(0);
                                }
                            }
                        }
                    }else
                    if(cm.compareTo(new BigDecimal(0))>0 && item.getIdConfiguracion()!=null && contarDias <= contarDiasPeriodo){
                        im = cm.divide(new BigDecimal(item.getIdConfiguracion().getCargaProyectada()), 4, RoundingMode.HALF_UP);
                        if(im.compareTo(new BigDecimal(1))>0){
                            dm ++;
                            cm = cm.subtract(new BigDecimal(item.getIdConfiguracion().getCargaProyectada()));
                            item.setColor("VERDE");
                            getProgramadorGanttFacade().edit(item);
                            //System.out.println("PRUEBA mañana cm < 0.5: "+ cm+" ------im = "+im +"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color "+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                        }else if(im.compareTo(new BigDecimal(0.5))>0){
                            dm ++;
                            cm = new BigDecimal(0);
                            item.setColor("VERDE");
                            getProgramadorGanttFacade().edit(item);
                            //System.out.println("PRUEBA mañana cm < 0.5: "+ cm+" ------im = "+im+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color "+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                        }else{
                            //System.out.println("PRUEBA mañana cm < 0.5: "+ cm+" ------im = "+im+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color "+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                            cm = new BigDecimal(0);
                        }
                    }
                }
            }else
            if(ch.compareTo(new BigDecimal(0))>0 && item.getIdConfiguracion()!=null && item.getIdConfiguracion()!=null){
                ih = ch.divide(new BigDecimal(item.getIdConfiguracion().getCargaProyectada()), 4, RoundingMode.HALF_UP);
                if(ih.compareTo(new BigDecimal(1))>0){
                    dh ++;
                    ch = ch.subtract(new BigDecimal(item.getIdConfiguracion().getCargaProyectada()));
                    item.setColor("NARANJA");
                    getProgramadorGanttFacade().edit(item);
                    //System.out.println("PRUEBA hoy ch < 0.5: "+ ch+" ------ih = "+ih+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color "+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                }else if(ih.compareTo(new BigDecimal(0.5))>0){
                    dh ++;
                    ch = new BigDecimal(0);
                    item.setColor("NARANJA");
                    getProgramadorGanttFacade().edit(item);
                    //System.out.println("PRUEBA hoy ch < 0.5: "+ ch+" ------ih = "+ih+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color "+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                }else{
                    ch = new BigDecimal(0);
                    //System.out.println("PRUEBA hoy ch < 0.5: "+ ch+" ------ih = "+ih+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color "+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                    //iniciar
                    if(cm.compareTo(new BigDecimal(0))>0 && item.getIdConfiguracion()!=null && contarDias <= contarDiasPeriodo){
                        im = cm.divide(new BigDecimal(item.getIdConfiguracion().getCargaProyectada()), 4, RoundingMode.HALF_UP);
                        if(im.compareTo(new BigDecimal(1))>0){
                            dm ++;
                            cm = cm.subtract(new BigDecimal(item.getIdConfiguracion().getCargaProyectada()));
                            item.setColor("VERDE");
                            getProgramadorGanttFacade().edit(item);
                            //System.out.println("PRUEBA mañana cm < 0.5: "+ cm+" ------im = "+im +"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color 0"+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                        }else if(im.compareTo(new BigDecimal(0.5))>0){
                            dm ++;
                            cm = new BigDecimal(0);
                            item.setColor("VERDE");
                            getProgramadorGanttFacade().edit(item);
                            //System.out.println("PRUEBA mañana cm < 0.5: "+ cm+" ------im = "+im+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color 1"+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                        }else{
                            //System.out.println("PRUEBA mañana cm < 0.5: "+ cm+" ------im = "+im+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color 2"+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                            cm = new BigDecimal(0);
                        }
                    }
                }
            }else
                if(cm.compareTo(new BigDecimal(0))>0 && item.getIdConfiguracion()!=null && contarDias <= contarDiasPeriodo){
                im = cm.divide(new BigDecimal(item.getIdConfiguracion().getCargaProyectada()), 4, RoundingMode.HALF_UP);
                if(im.compareTo(new BigDecimal(1))>0){
                    dm ++;
                    cm = cm.subtract(new BigDecimal(item.getIdConfiguracion().getCargaProyectada()));
                    item.setColor("VERDE");
                    getProgramadorGanttFacade().edit(item);
                    //System.out.println("PRUEBA mañana cm < 0.5: "+ cm+" ------im = "+im +"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color 5"+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                }else if(im.compareTo(new BigDecimal(0.5))>0){
                    dm ++;
                    cm = new BigDecimal(0);
                    item.setColor("VERDE");
                    getProgramadorGanttFacade().edit(item);
                    //System.out.println("PRUEBA mañana cm < 0.5: "+ cm+" ------im = "+im+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color 4"+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                }else{
                    item.setColor("BLANCO");
                    getProgramadorGanttFacade().edit(item);
                    //System.out.println("PRUEBA mañana cm < 0.5: "+ cm+" ------im = "+im+"-----cp "+item.getIdConfiguracion().getCargaProyectada()+"----dia "+item.getDescripcionDia()+"----color 3"+item.getColor()+"----fecha "+item.getFecha().toLocaleString());
                    cm = new BigDecimal(0);
                }
            }
        }        
    }
   
    public void actualizarCantPendienteGantt(){
        for(T029ProgramadorGantt item: listaProgramadorGantt){
            item.setCantPendiente(new BigDecimal(0));
            item.setColor("BLANCO");
            getProgramadorGanttFacade().edit(item);
        }
    }
    public void prepareSincronizarGantt() throws SQLException{
        contarDiasPeriodo = 0;
        consultarFechasPeriodoGantt();
        actualizarCantPendienteGantt();
        consultarDatosGantt();
        obtenerDiasGantt();
        prepareList();
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
    //Generación de tabla horizontal gantt
    public List<Map<String, ColumnModel>> getTableData() { 
        return tableData; 
    } 
    public List<ColumnModel> getTableHeaderNames() { 
        return tableHeaderNames; 
    } 
    public void crearTablaGantt() { 
        tableData = new ArrayList< Map<String, ColumnModel> >(); 
        //Generate table header. 
        tableHeaderNames = new ArrayList<ColumnModel>(); 
        int header =0;
        for (String item: listaDias) { 
            header++;
            tableHeaderNames.add(new ColumnModel("header "+header, item));
        } 
        //Generate table data. 
        int a= 0;
        for (String item: listaCentroTrabajoProgramado) { 
            String i= item;   
            Map<String, ColumnModel> playlist = new HashMap<String, ColumnModel>(); 
            for (int j = 0; j < header; j++) {
                if(j>=header){
                    j=0;
                }else{
                        playlist.put(tableHeaderNames.get(j).key,new ColumnModel(tableHeaderNames.get(j).key,detalle[a])); 
                        a ++; 
                    }
                } 
            tableData.add(playlist); 
        } 
    } 

    static public class ColumnModel implements Serializable { 

        private String key; 
        private String value;
        
        public String getKey() {
            return key;
        }
        public void setKey(String key) {
            this.key = key;
        }
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
        public ColumnModel(String key, String value) { 
            this.key = key; 
            this.value = value; 
        } 
    }
    //Reporte de programacion kos
     public String prepareReporteProgramacionKos() throws SQLException{
        getListaDetalleProgramadorR1();
        //getListaDetalleProgramadorR2();
        getListaDetalleProgramadorR3();
        getListaDetalleProgramadorR4();
        getListaDetalleProgramadorR5();
        getListaDetalleProgramadorR6();
        getListaDetalleProgramadorR7();
        getListaDetalleProgramadorR8();
        getListaDetalleProgramadorR9();
        getListaDetalleProgramadorR10();
        getListaDetalleProgramadorR11();                                                      
        return "/usuario/modReportes/programacionKos/pantallaCompleta";
    }
    public List<DetalleProgramadorGanttController> getListaDetalleProgramadorR1() throws SQLException {
        centroTrabajo = "FORMADORA 4 OZ";
        consultarDetalleGantt();
        return listaDetalleProgramadorGantt;
    }
    public List<DetalleProgramadorGanttController> getListaDetalleProgramadorR2() throws SQLException {
        centroTrabajo = "FORMADORA 6 OZ";
        consultarDetalleGantt();
        return listaDetalleProgramadorGantt;
    }
    public List<DetalleProgramadorGanttController> getListaDetalleProgramadorR3() throws SQLException {
        centroTrabajo = "FORMADORA 7 OZ";
        consultarDetalleGantt();
        return listaDetalleProgramadorGantt;
    }
    public List<DetalleProgramadorGanttController> getListaDetalleProgramadorR4() throws SQLException {
        centroTrabajo = "FORMADORA 9 OZ";
        consultarDetalleGantt();
        return listaDetalleProgramadorGantt;
    }
    public List<DetalleProgramadorGanttController> getListaDetalleProgramadorR5() throws SQLException {
        centroTrabajo = "FORMADORA VASO 9/14 OZ";
        consultarDetalleGantt();
        return listaDetalleProgramadorGantt;
    }
    public List<DetalleProgramadorGanttController> getListaDetalleProgramadorR6() throws SQLException {
        centroTrabajo = "FORMADORA VASO 12 OZ";
        consultarDetalleGantt();
        return listaDetalleProgramadorGantt;
    }
    public List<DetalleProgramadorGanttController> getListaDetalleProgramadorR7() throws SQLException {
        centroTrabajo = "FORMADORA VASO 16 OZ";
        consultarDetalleGantt();
        return listaDetalleProgramadorGantt;
    }
    public List<DetalleProgramadorGanttController> getListaDetalleProgramadorR8() throws SQLException {
        centroTrabajo = "FORMADORA VASO 21/32/42 OZ";
        consultarDetalleGantt();
        return listaDetalleProgramadorGantt;
    }
    public List<DetalleProgramadorGanttController> getListaDetalleProgramadorR9() throws SQLException {
        centroTrabajo = "FORMADORA CONTENEDOR 7 OZ";
        consultarDetalleGantt();
        return listaDetalleProgramadorGantt;
    }
    public List<DetalleProgramadorGanttController> getListaDetalleProgramadorR10() throws SQLException {
        centroTrabajo = "FORMADORA CONTENEDOR 12 OZ";
        consultarDetalleGantt();
        return listaDetalleProgramadorGantt;
    }
    public List<DetalleProgramadorGanttController> getListaDetalleProgramadorR11() throws SQLException {
        centroTrabajo = "FORMADORA CONTENEDOR 16 OZ";
        consultarDetalleGantt();
        return listaDetalleProgramadorGantt;
    }
}
