/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T025CentroTrabajo;
import com.eurekaintranet.sessions.T025CentroTrabajoFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lccabalr
 */
@Named(value = "centroTrabajoController")
@SessionScoped
public class CentroTrabajoController implements Serializable {

    /**
     * Creates a new instance of CentroTrabajoController
     */
    
    @EJB
    private T025CentroTrabajoFacade centroTrabajoFacade;
    private T025CentroTrabajo centroTrabajoActual;
    private List<T025CentroTrabajo> listaCentroTrabajo;
    
    private String idCentroTrabajo;
    private String descripcionCentroTrabajo;
    public CentroTrabajoController() {
    }

    public T025CentroTrabajoFacade getCentroTrabajoFacade() {
        return centroTrabajoFacade;
    }

    public void setCentroTrabajoFacade(T025CentroTrabajoFacade centroTrabajoFacade) {
        this.centroTrabajoFacade = centroTrabajoFacade;
    }

    public T025CentroTrabajo getCentroTrabajoActual() {
        return centroTrabajoActual;
    }

    public void setCentroTrabajoActual(T025CentroTrabajo centroTrabajoActual) {
        this.centroTrabajoActual = centroTrabajoActual;
    }

    public List<T025CentroTrabajo> getListaCentroTrabajo() {
        return listaCentroTrabajo = getCentroTrabajoFacade().findAll();
    }

    public void setListaCentroTrabajo(List<T025CentroTrabajo> listaCentroTrabajo) {
        this.listaCentroTrabajo = listaCentroTrabajo;
    }

    public String getIdCentroTrabajo() {
        return idCentroTrabajo;
    }

    public void setIdCentroTrabajo(String idCentroTrabajo) {
        this.idCentroTrabajo = idCentroTrabajo;
    }

    public String getDescripcionCentroTrabajo() {
        return descripcionCentroTrabajo;
    }

    public void setDescripcionCentroTrabajo(String descripcionCentroTrabajo) {
        this.descripcionCentroTrabajo = descripcionCentroTrabajo;
    }

   
     //Navegacion
     public void recargarLista(){
        listaCentroTrabajo=null;
    }
     public String prepareEdit() {
        return "editar";
    }
      public String prepareCreate() {
        centroTrabajoActual = new T025CentroTrabajo();
        add();
        return "lista";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modPlaneacionKos/centroTrabajo/lista";
    }
    public void add() {
        try { 
            consultarCentroTrabajo();
            addSuccessMessage("Centro de trabajo creado correctamente", "Centro de trabajo creado correctamente");
            recargarLista();
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
    }
    public String update() {
        try {
            getCentroTrabajoFacade().edit(centroTrabajoActual);
            addSuccessMessage("Centro de trabajo editado correctamente", "Centro de trabajo editado correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
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
    //Centros de trabajo
    public void consultarCentroTrabajo() throws SQLException{
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        
        String query = "SELECT [f806_id] id ,[f806_descripcion] nombre FROM [UnoEE].[dbo].[t806_mf_centros_trabajo] t806 WHERE f806_ind_estado = 1 and f806_id_instalacion = '003' and not exists (select [id_centro_trabajo] FROM [BD_Eureka].[dbo].[t025_centro_trabajo] t025 WHERE t806.f806_id = t025.id_centro_trabajo)";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                centroTrabajoActual = new T025CentroTrabajo();
                centroTrabajoActual.setIdCentroTrabajo(rs.getString("id").trim());
                centroTrabajoActual.setNombre(rs.getString("nombre").trim());
                getCentroTrabajoFacade().create(centroTrabajoActual);
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    
}
