/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T007Contenido;
import com.eurekaintranet.sessions.T007ContenidoFacade;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Lccabalr
 */
@Named(value = "contenidoController")
@SessionScoped
public class ContenidoController implements Serializable {

    @EJB
    private T007ContenidoFacade contenidoFacade;
    private T007Contenido contenidoActual;
    private List<T007Contenido> listaContenido;
    
    private T001Usuario usuarioActual;
    
    private StreamedContent img1 = new DefaultStreamedContent();
    private StreamedContent img2 = new DefaultStreamedContent();
    private StreamedContent img3 = new DefaultStreamedContent();
    private StreamedContent img4 = new DefaultStreamedContent();
     
    String tipo = "";
    String nameImg;
    UploadedFile fileImg;
    byte[] dataImg;
    String newFileNameImg;
    
    String newFileNamePrevia;
    String nombreTipo;
    
    public ContenidoController() {
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
        return listaContenido = getContenidoFacade().findAll();
    }
     public List<T007Contenido> getListaContenidoFoorter() {
        return listaContenido = getContenidoFacade().contenidoFooter();
    }
    
    public List<T007Contenido> getListaContenidoNoticias() {
        return listaContenido = getContenidoFacade().tipoContenido("NOT");
    }
    public List<T007Contenido> getListaContenidoSlider() {
        return listaContenido = getContenidoFacade().tipoContenido("SLD");
    }
    public List<T007Contenido> getListaContenidoUser(){
        return listaContenido = getContenidoFacade().tipoContenido(getTipo());
    }
    
    public void setListaContenido(List<T007Contenido> listaContenido) {
        this.listaContenido = listaContenido;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }
    
    
     //Navigation
     public void recargarLista(){
        nameImg = null;
        newFileNameImg = null;
        dataImg = null;
        listaContenido=null;
    }
     public String prepareEdit() {
         newFileNamePrevia = contenidoActual.getImg1();
        return "editar";
    }
      public String prepareCreate() {
        newFileNamePrevia =null;
        contenidoActual = new T007Contenido();
        return "crear";
    }

    public String prepareView() {
        return "ver";
    }
    public String prepareViewUser() {
        getContenidoActual().setContarVista(getContenidoActual().getContarVista() + 1);
        getContenidoActual().setFechaUltimaVista(new Date());
        getContenidoFacade().edit(getContenidoActual());
        return "/usuario/modGestorContenido/verContenido";
    }   
    public String prepareListOportunidad() {
        setTipo("OPL");
        setNombreTipo("Oportunidades laborales");
        return "/usuario/modGestorContenido/listaContenido";
    }  
    public String prepareListResponsabilidad() {
        setTipo("RSO");
        setNombreTipo("Responsabilidad social");
        return "/usuario/modGestorContenido/listaContenido";
    }
    public String prepareListInnovate() {
        setTipo("PIN");
        setNombreTipo("Programa innóvate");
        return "/usuario/modGestorContenido/listaContenido";
    }
    public String prepareListReconocimientos() {
        setTipo("REM");
        setNombreTipo("Reconocimientos empresariales");
        return "/usuario/modGestorContenido/listaContenido";
    }
    
    public String prepareList() {
        recargarLista();
        return "/administrador/modGestorContenido/lista";
    }
    public String add() {
        try { 
            getContenidoActual().setIdUsuario(usuarioActual);
            getContenidoActual().setFecha(new Date());
            getContenidoActual().setFechaUltimaVista(new Date());
            getContenidoActual().setContarVista(0);
            getContenidoFacade().create(contenidoActual);
            if(nameImg !=null){
                nameImg = contenidoActual.getIdContenido()+"-img1.jpg";
                newFileNameImg = "intranet"+File.separatorChar+"GESTORCONTENIDO"+File.separatorChar+contenidoActual.getTipo().getIdTipo()+File.separatorChar+nameImg;
                contenidoActual.setImg1(newFileNameImg);
                transferirImg();
                getContenidoFacade().edit(contenidoActual);
            }
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contenido creado correctamente", "El contenido con titulo "+contenidoActual.getTitulo()+ " fue guardado con exito");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String update() {
        try {
            if(nameImg !=null){
                nameImg = contenidoActual.getIdContenido()+"-img1.jpg";
                newFileNameImg = "intranet"+File.separatorChar+"GESTORCONTENIDO"+File.separatorChar+contenidoActual.getTipo().getIdTipo()+File.separatorChar+nameImg;
                contenidoActual.setImg1(newFileNameImg);
                transferirImg();
            }
            getContenidoFacade().edit(contenidoActual);
            addSuccessMessage("El contenido fue editado correctamente", "El contenido fue editado correctamente");
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String delete() {
        try {   
            if(contenidoActual.getImg1()!=null){
                File img = new File(contenidoActual.getImg1());
                img.delete();
            }
            getContenidoFacade().remove(contenidoActual);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contenido eliminado correctamente", "El contenido fue eliminado con exito");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return "lista";
        } catch (Exception e) {
            return "lista";
        }
    }
    //carga de imagenes
    public void cargarImg1(FileUploadEvent event) throws IOException {
        try {
            fileImg = event.getFile();
            dataImg = IOUtils.toByteArray(fileImg.getInputstream());
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            nameImg = event.getFile().getFileName();
            transferirImgPrevia();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo guardado", "El archivo "+ fileImg+" se guardo correctamente."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Archivo no guardado", "El archivo "+ fileImg+" no se guardo correctamente."));
        }   
    }
    public void transferirImgPrevia() throws IOException{
        newFileNamePrevia =  "intranet"+File.separatorChar+"GESTORCONTENIDO"+File.separatorChar+"imgPrevia1.jpg";
        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileNamePrevia));
            imageOutput.write(dataImg, 0, dataImg.length);
            imageOutput.close();
        }
        catch(IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }
    }
    public void prepararImagenPrevia() throws FileNotFoundException {
        try {
            img1 = new DefaultStreamedContent(new FileInputStream(newFileNamePrevia), "image/jpg");
        } catch (FileNotFoundException e) {
            String rutaImg1 = "intranet"+File.separatorChar+"GESTORCONTENIDO"+File.separatorChar+"blanco.jpg";
            img1 = new DefaultStreamedContent(new FileInputStream(rutaImg1), "image/jpg");  
        }
    }
     public StreamedContent getImgPrevia() throws FileNotFoundException {
        if(newFileNamePrevia == null){
            return null; 
        }else{
            prepararImagenPrevia();
            return img1; 
        } 
    }
    public void transferirImg(){
        FileImageOutputStream imageOutputPdf;
            try {
                    imageOutputPdf = new FileImageOutputStream(new File(newFileNameImg));
                    imageOutputPdf.write(dataImg, 0, dataImg.length);
                    imageOutputPdf.close();
            }
            catch(IOException e) {
                throw new FacesException("Error in writing captured image.", e);
            }
    }
    public void prepararImagen1() throws FileNotFoundException {
        try {
            String fileImg = contenidoActual.getImg1();
            img1 = new DefaultStreamedContent(new FileInputStream(fileImg), "image/jpg");
        } catch (FileNotFoundException e) {
            String rutaImg1 = "intranet"+File.separatorChar+"GESTORCONTENIDO"+File.separatorChar+"blanco.jpg";
            img1 = new DefaultStreamedContent(new FileInputStream(rutaImg1), "image/jpg");  
        }
    }
    public StreamedContent getImg1() throws FileNotFoundException {
        if(contenidoActual.getImg1() == null){
            return null; 
        }else{
            prepararImagen1();
            return img1; 
        } 
    }
    public void setImg1(StreamedContent img1) {
        this.img1 = img1;
    }    
    public void borrarImg1() throws FileNotFoundException {
        contenidoActual.setImg1(null);
        getImg1();
    }
    public void borrarImgPrevia() throws FileNotFoundException{
        newFileNamePrevia = null;
        getImgPrevia();
    }
    public boolean isVacioImg1(){
        return contenidoActual.getImg1()== null? false : contenidoActual.getImg1()!= null;
    }
     public boolean isVacioDes1(){
        return contenidoActual.getDescripcion()== null? false : contenidoActual.getDescripcion()!= null;
    }
    public StreamedContent getImageLista() throws FileNotFoundException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Get ID value from actual request param.
            String id = context.getExternalContext().getRequestParameterMap().get("item");
            T007Contenido image = contenidoFacade.find(Integer.valueOf(id));
            return new DefaultStreamedContent(new FileInputStream(image.getImg1()), "image/jpg");
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

    
}

