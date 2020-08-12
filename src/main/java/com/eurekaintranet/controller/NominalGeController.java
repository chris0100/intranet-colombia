/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T033NominalGe;
import com.eurekaintranet.sessions.T033NominalGeFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Lccabalr
 */
@Named(value = "nominalGeController")
@SessionScoped
public class NominalGeController implements Serializable {

    @EJB
    private T033NominalGeFacade nominalGeFacade;
    private T033NominalGe nominalGeActual;
    private List<T033NominalGe> listaNominalGe;
    
    private List<String> listaCodMaquina;
    private List<String> listaDescMaquina;
    private List<String> listaHomologadoMaquina;
    
    private boolean estadoMaquinaEncontrada;
    
    private T001Usuario usuarioActual; 
    
    public NominalGeController() {
    }

    public T033NominalGeFacade getNominalGeFacade() {
        return nominalGeFacade;
    }

    public void setNominalGeFacade(T033NominalGeFacade nominalGeFacade) {
        this.nominalGeFacade = nominalGeFacade;
    }

    public T033NominalGe getNominalGeActual() {
        return nominalGeActual;
    }

    public void setNominalGeActual(T033NominalGe nominalGeActual) {
        this.nominalGeActual = nominalGeActual;
    }

    public List<T033NominalGe> getListaNominalGe() {
        return listaNominalGe = getNominalGeFacade().findAll();
    }
    public List<T033NominalGe> getListaNominalGeEstadoActivo() {
        return listaNominalGe = getNominalGeFacade().consultarEstadoNominal(true);
    }
    public void setListaNominalGe(List<T033NominalGe> listaNominalGe) {
        this.listaNominalGe = listaNominalGe;
    }

    public List<String> getListaCodMaquina() {
        return listaCodMaquina;
    }

    public void setListaCodMaquina(List<String> listaCodMaquina) {
        this.listaCodMaquina = listaCodMaquina;
    }

    public List<String> getListaDescMaquina() {
        return listaDescMaquina;
    }

    public void setListaDescMaquina(List<String> listaDescMaquina) {
        this.listaDescMaquina = listaDescMaquina;
    }

    public List<String> getListaHomologadoMaquina() {
        return listaHomologadoMaquina;
    }

    public void setListaHomologadoMaquina(List<String> listaHomologadoMaquina) {
        this.listaHomologadoMaquina = listaHomologadoMaquina;
    }

    public boolean isEstadoMaquinaEncontrada() {
        return estadoMaquinaEncontrada;
    }

    public void setEstadoMaquinaEncontrada(boolean estadoMaquinaEncontrada) {
        this.estadoMaquinaEncontrada = estadoMaquinaEncontrada;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
    //Navigation
    private void consultarMaquinas(){
        listaCodMaquina = getNominalGeFacade().consultarCodMaquina();
        listaDescMaquina = getNominalGeFacade().consultarDescMaquina();
        listaHomologadoMaquina = getNominalGeFacade().consultarHomologadoMaquina();
    }
    public void recargarLista(){
        listaNominalGe=null;
    }
     public String prepareEdit() {
         estadoMaquinaEncontrada = true;
        consultarMaquinas();
        return "editar";
    }
      public String prepareCreate() {
        estadoMaquinaEncontrada = false;
        consultarMaquinas();
        nominalGeActual = new T033NominalGe();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGeTelares/nominal/lista";
    }
    public String add() {
        try { 
            nominalGeActual.setEstado(true);
            nominalGeActual.setUsuarioCreacion(usuarioActual.getUsername());
            nominalGeActual.setFechaCreacion(new Date());
            nominalGeActual.setFcCalculo(new BigDecimal(nominalGeActual.getRpm()*nominalGeActual.getPuntas()));
            getNominalGeFacade().create(nominalGeActual);
            addSuccessMessage("Nominal creado correctamente", "Nominal creado correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            nominalGeActual.setUsuarioActualizacion(usuarioActual.getUsername());
            nominalGeActual.setFechaActualizacion(new Date());
            nominalGeActual.setFcCalculo(new BigDecimal(nominalGeActual.getRpm()*nominalGeActual.getPuntas()));
            getNominalGeFacade().edit(nominalGeActual);
            addSuccessMessage("Nominal editado correctamente", "Nominal editado correctamente");
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
    //metodos
    public void activarCamposMaquina(){
        estadoMaquinaEncontrada =false;
        nominalGeActual.setCodigoMaquina("");
        nominalGeActual.setNombreMaquina("");
        nominalGeActual.setNombreHomologado("");
    }
    
    public void desactivarCamposMaquina(){
        if(!nominalGeActual.getCodigoMaquina().equals("") && !nominalGeActual.getNombreMaquina().equals("")){
            estadoMaquinaEncontrada =true;
        }else{
            estadoMaquinaEncontrada =false;
        }  
    }
    public void consultarDatosMaquinaCodigo() throws SQLException{
        consultarCodMaquinaEncontrada(nominalGeActual.getCodigoMaquina());
        desactivarCamposMaquina();
    }
    public void consultarDatosMaquinaDesc() throws SQLException{
        consultarDescMaquinaEncontrada(nominalGeActual.getNombreMaquina());
        desactivarCamposMaquina();
    }
    public void consultarDatosMaquinaHomologado() throws SQLException{
        consultarHomologadoMaquinaEncontrada(nominalGeActual.getNombreHomologado());
        desactivarCamposMaquina(); 
    }
    public List<String> completeCodMaquina(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : listaCodMaquina) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }
     public List<String> completeDescMaquina(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : listaDescMaquina) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }
      public List<String> completeHomologadoMaquina(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : listaHomologadoMaquina) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }
    public void consultarCodMaquinaEncontrada(String codMaquina) throws SQLException{
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "SELECT top 1 [c0800_referencia], [c0800_descripcion], isnull(c0834_valor,'') c0834_valor FROM [UnoEE].[dbo].[w0800_equipos] w800 left join [UnoEE].[dbo].[w0834_equipos_desc_tecnicas] w0834 on w0834.c0834_id_equipo = w800.c0800_id and w0834.c0834_rowid_campo = 24 where [c0800_referencia] = '"+codMaquina+"'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
               nominalGeActual.setCodigoMaquina(rs.getString("c0800_referencia"));
               nominalGeActual.setNombreMaquina(rs.getString("c0800_descripcion"));
               nominalGeActual.setNombreHomologado(rs.getString("c0834_valor"));
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void consultarDescMaquinaEncontrada(String descMaquina) throws SQLException{
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "SELECT top 1 [c0800_referencia], [c0800_descripcion], isnull(c0834_valor,'') c0834_valor FROM [UnoEE].[dbo].[w0800_equipos] w800 left join [UnoEE].[dbo].[w0834_equipos_desc_tecnicas] w0834 on w0834.c0834_id_equipo = w800.c0800_id and w0834.c0834_rowid_campo = 24 where [c0800_descripcion] = '"+descMaquina+"'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
               nominalGeActual.setCodigoMaquina(rs.getString("c0800_referencia"));
               nominalGeActual.setNombreMaquina(rs.getString("c0800_descripcion"));
               nominalGeActual.setNombreHomologado(rs.getString("c0834_valor"));
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public void consultarHomologadoMaquinaEncontrada(String descHomologado) throws SQLException{
        String connectionUrl ="jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "SELECT top 1 [c0800_referencia], [c0800_descripcion], isnull(c0834_valor,'') c0834_valor FROM [UnoEE].[dbo].[w0800_equipos] w800 left join [UnoEE].[dbo].[w0834_equipos_desc_tecnicas] w0834 on w0834.c0834_id_equipo = w800.c0800_id and w0834.c0834_rowid_campo = 24 where [c0834_valor] = '"+descHomologado+"'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
               nominalGeActual.setCodigoMaquina(rs.getString("c0800_referencia"));
               nominalGeActual.setNombreMaquina(rs.getString("c0800_descripcion"));
               nominalGeActual.setNombreHomologado(rs.getString("c0834_valor"));
            }
        } catch (SQLException e ) {
            System.out.println("error "+e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    //converter
    public T033NominalGe getNominalGe(java.lang.Integer id) {
        return getNominalGeFacade().find(id);
    }
    
    @FacesConverter(forClass = T033NominalGe.class)
    public static class NominalGeControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NominalGeController controller = (NominalGeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "nominalGeController");
            return controller.getNominalGe(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof T033NominalGe) {
                T033NominalGe o = (T033NominalGe) object;
                return getStringKey(o.getIdNominal());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T033NominalGe.class.getName());
            }
        }
    }
}
