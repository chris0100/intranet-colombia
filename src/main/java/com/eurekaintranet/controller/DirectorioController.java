/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Lccabalr
 */
@Named(value = "directorioController")
@ApplicationScoped
public class DirectorioController {

    private int fila;
    private String area;
    private String nombreColaborador;
    private String telefono;
    private List<DirectorioController> listaDirectorio;
    public DirectorioController() {
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNombreColaborador() {
        return nombreColaborador;
    }

    public void setNombreColaborador(String nombreColaborador) {
        this.nombreColaborador = nombreColaborador;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<DirectorioController> getListaDirectorio() {
        return listaDirectorio;
    }

    public void setListaDirectorio(List<DirectorioController> listaDirectorio) {
        this.listaDirectorio = listaDirectorio;
    }
    
    public void obtenerDirectorio()
        throws SQLException, ParseException {
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        String query ="exec BD_Eureka.dbo.sp_obtener_directorio_ldap";
        Statement stmt = null;
        listaDirectorio = new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                DirectorioController directorioActual =new DirectorioController();
                directorioActual.setArea(rs.getString("area"));
                directorioActual.setNombreColaborador(rs.getString("NameValues"));
                directorioActual.setTelefono(rs.getString("telephoneNumber"));
                System.out.print("Prueba "+directorioActual.getArea());
                listaDirectorio.add(directorioActual);
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public String prepareList() throws SQLException, ParseException{
        obtenerDirectorio();
        return "/usuario/modZonaAgil/directorio.xhtml";
    }
}
