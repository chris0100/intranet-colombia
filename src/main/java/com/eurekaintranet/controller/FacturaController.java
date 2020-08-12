/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Lccabalr
 */
@ManagedBean
@SessionScoped
public class FacturaController {
    private String facturaActual = "1";
    private String nombreArchivo;
    public FacturaController() {
    }

    public String getFacturaActual() {
        return facturaActual;
    }

    public void setFacturaActual(String facturaActual) {
        this.facturaActual = facturaActual;
    }
    
    public String[] getListaFactura() {
            File carpeta=new File("intranet"+File.separatorChar+"GESTORFACTURAS");
            ArrayList<String> listaArchivos=new ArrayList<String>();
            for(File archivo:carpeta.listFiles())
                    listaArchivos.add(archivo.getName());
            return(listaArchivos.toArray(new String[0]));   
     }
    public void copiarPDF() throws FileNotFoundException, IOException{
        File archivoOrigen;
        File archivoDestino;
        FileInputStream in = null;
        FileOutputStream out = null;
        boolean b;
        try{
            String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/file.pdf");
            archivoDestino = new File(ruta);
            archivoOrigen = new File("intranet"+File.separatorChar+"GESTORFACTURAS"+File.separatorChar+facturaActual);
            if(b = archivoOrigen.exists()) {
                if(b = archivoOrigen.canRead()) {
                    in = new FileInputStream(archivoOrigen);
                    out = new FileOutputStream(archivoDestino);
                    int c;
                    while( (c = in.read() ) != -1) {
                        out.write(c);
                    }
                }
            }
        } catch(IOException ex){
            ex.printStackTrace(System.out);
            b = false;
        } finally {
            try{
                if(in != null) {
                    in.close();
                }
                if(out != null) {
                    out.close();
                }
            } catch(IOException ex) {
                ex.printStackTrace(System.out);
                b = false;
            }
        }
    }  
    public void onRowSelect(SelectEvent event) {
       facturaActual =(String)event.getComponent().getAttributes().get("item");
    }
    public String prepareList(){
        return "/administrador/modFacturas/lista";
    }
    public String prepareView() throws IOException{
        copiarPDF();
        return "ver";
    }
    public StreamedContent getDescargaPDF() throws FileNotFoundException {
        try {
            InputStream stream =  new FileInputStream("intranet"+File.separatorChar+"GESTORFACTURAS"+File.separatorChar+facturaActual);
            return new DefaultStreamedContent(stream, "application/pdf", "intranet"+File.separatorChar+"GESTORFACTURAS"+File.separatorChar+facturaActual);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No existe documento", "El documento no tiene acceso");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        }
    }  
}
