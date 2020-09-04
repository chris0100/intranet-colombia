/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T004Documento;
import com.eurekaintranet.entities.T005Proceso;
import com.eurekaintranet.entities.T006TipoDocumento;
import com.eurekaintranet.sessions.T004DocumentoFacade;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.ValidatorException;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author junio
 */
@Named(value = "documentoController")
@SessionScoped
public class DocumentoController implements Serializable {

    @EJB 
    private T004DocumentoFacade documentosFacade;
    private T004Documento documentosActual;
    private T004Documento documentoVersion;
    private List<T004Documento> listaDocumentos;
    
    private T006TipoDocumento tipoDocumento;
    private T005Proceso procesoActual;
    
    private String codigoVersion;
    private String nombreVersion;
    private Date fechaVersion;
    private String estadoVersion;
    private String ubicacionPdfVersion;
    private String ubicacionDocVersion;
    private String tipoVersion;
    private String procesoVersion;
    private String tipoDocumentoVersion;
    private String descripcionVersion;
    private int version;
     
    UploadedFile fileDoc;
    byte[] dataDoc;
    String nameDoc;
    String newFileNameDoc;
    String nameDocExt;
    
    UploadedFile filePdf;
    byte[] dataPdf;
    String namePdf;
    String newFileNamePdf;
    String comparaCC = "CC - CONTROL CALIDAD";


    public String getComparaCC() {
        return comparaCC;
    }

    public void setComparaCC(String comparaCC) {
        this.comparaCC = comparaCC;
    }

    public DocumentoController() {
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
        return listaDocumentos = getDocumentosFacade().findAll();
    }
    public List<T004Documento> getListaDocumentosFormatoProcedimiento() {
        tipoDocumento = new T006TipoDocumento("FTO");
        return listaDocumentos = getDocumentosFacade().documentosFormatoProcedimiento(tipoDocumento);
    }
    public List<T004Documento> getListaDocumentosProcedimiento() {
        tipoDocumento = new T006TipoDocumento("PRO");
        return listaDocumentos = getDocumentosFacade().documentosProcedimiento(tipoDocumento);
    }
    public List<T004Documento> getListaDocumentosProcesoDoc() {
        tipoDocumento = new T006TipoDocumento("FTO");
        return listaDocumentos = getDocumentosFacade().documentosProcesoDoc(procesoActual, tipoDocumento, "VIGENTE");
    }
    public List<T004Documento> getListaDocumentosProcesoFtp() {
        tipoDocumento = new T006TipoDocumento("FTO");
        return listaDocumentos = getDocumentosFacade().documentosProcesoFto(procesoActual, tipoDocumento, "VIGENTE");
    }
    public List<T004Documento> getListaDocumentosEstado() {
        //return listaDocumentos = getDocumentosFacade().findAll();
        return listaDocumentos = getDocumentosFacade().documentosEstado("VIGENTE");
    }
    public List<T004Documento> getListaDocumentosHistorial() {
        
        return getDocumentosFacade().documentosHistorial(documentosActual.getCodigo());
        //return listaDocumentos = getDocumentosFacade().findAll();
    }
    public void setListaDocumentos(List<T004Documento> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }
    public List<T004Documento> getListaCompletaUser() {
        return listaDocumentos;
    }
    public List<T004Documento> getDocumentosProceso() {
        return getDocumentosFacade().documentosProcesoTipoDocumento(documentosActual.getIdProceso(), documentosActual.getIdTipoDocumento());
    }
    public boolean isFichaTecnica(){
        return documentosActual.getIdTipoDocumento()== null? false : documentosActual.getIdTipoDocumento().getIdTipoDocumento().equals("FTC");
    }
    public boolean isFormato(){
        return documentosActual.getIdTipoDocumento()== null? false : documentosActual.getIdTipoDocumento().getIdTipoDocumento().equals("FTO");
    }
    public boolean isProcedimiento(){
        return documentosActual.getIdTipoDocumento()== null? false : documentosActual.getIdTipoDocumento().getIdTipoDocumento().equals("PRO");
    }

    public T004Documento getDocumentoVersion() {
        return documentoVersion;
    }

    public void setDocumentoVersion(T004Documento documentoVersion) {
        this.documentoVersion = documentoVersion;
    }

    public String getCodigoVersion() {
        return codigoVersion;
    }

    public void setCodigoVersion(String codigoVersion) {
        this.codigoVersion = codigoVersion;
    }

    public String getNombreVersion() {
        return nombreVersion;
    }

    public void setNombreVersion(String nombreVersion) {
        this.nombreVersion = nombreVersion;
    }

    public Date getFechaVersion() {
        return fechaVersion;
    }

    public void setFechaVersion(Date fechaVersion) {
        this.fechaVersion = fechaVersion;
    }

    public String getEstadoVersion() {
        return estadoVersion;
    }

    public void setEstadoVersion(String estadoVersion) {
        this.estadoVersion = estadoVersion;
    }

    public String getUbicacionPdfVersion() {
        return ubicacionPdfVersion;
    }

    public void setUbicacionPdfVersion(String ubicacionPdfVersion) {
        this.ubicacionPdfVersion = ubicacionPdfVersion;
    }

    public String getUbicacionDocVersion() {
        return ubicacionDocVersion;
    }

    public void setUbicacionDocVersion(String ubicacionDocVersion) {
        this.ubicacionDocVersion = ubicacionDocVersion;
    }

    public String getTipoVersion() {
        return tipoVersion;
    }

    public void setTipoVersion(String tipoVersion) {
        this.tipoVersion = tipoVersion;
    }

    public String getProcesoVersion() {
        return procesoVersion;
    }

    public void setProcesoVersion(String procesoVersion) {
        this.procesoVersion = procesoVersion;
    }

    public String getTipoDocumentoVersion() {
        return tipoDocumentoVersion;
    }

    public void setTipoDocumentoVersion(String tipoDocumentoVersion) {
        this.tipoDocumentoVersion = tipoDocumentoVersion;
    }

    public String getDescripcionVersion() {
        return descripcionVersion;
    }

    public void setDescripcionVersion(String descripcionVersion) {
        this.descripcionVersion = descripcionVersion;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public StreamedContent getArchivoDescarga() {
        return archivoDescarga;
    }

    public void setArchivoDescarga(StreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
    }

    public T006TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(T006TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public T005Proceso getProcesoActual() {
        return procesoActual;
    }

    public void setProcesoActual(T005Proceso procesoActual) {
        this.procesoActual = procesoActual;
    }
    
    
    //Navigation
    public void recargarLista(){
        nameDoc = null;
        nameDocExt = null;
        namePdf = null;
        newFileNameDoc = null;
        newFileNamePdf = null;
        listaDocumentos=null;
    }
    public String prepareEdit() {
        codigoVersion = documentosActual.getCodigo();
        nombreVersion= documentosActual.getNombre();
        fechaVersion= documentosActual.getFecha();
        estadoVersion= documentosActual.getEstado();
        ubicacionPdfVersion= documentosActual.getUbicacionPdf();
        ubicacionDocVersion= documentosActual.getUbicacionDoc();
        tipoVersion= documentosActual.getTipo();
        procesoVersion= documentosActual.getIdProceso().getIdProceso();
        tipoDocumentoVersion= documentosActual.getIdTipoDocumento().getIdTipoDocumento();
        descripcionVersion= documentosActual.getDescripcion();
        version= documentosActual.getVersion();
        return "/administrador/modGestorDocumental/documento/editar";
    }
      public String prepareCreate() {
        nameDoc = null;
        nameDocExt = null;
        namePdf = null;
        newFileNameDoc = null;
        newFileNamePdf = null;
        documentosActual = new T004Documento();
        return "/administrador/modGestorDocumental/documento/crear";
    }

    public String prepareView() throws IOException {
        if(documentosActual.getIdTipoDocumento().getIdTipoDocumento().equals("FTO")){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Archivo inaccesible", "Los formatos no tienen archivos .pdf anexos");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        }else{
            copiarPDF();
            documentosActual.setContarVista(documentosActual.getContarVista()+1);
            getDocumentosFacade().edit(documentosActual);
            return "/administrador/modGestorDocumental/documento/verpdf";
        }
    }
    public String prepareViewPdfUser() throws IOException {
        if(documentosActual.getIdTipoDocumento().getIdTipoDocumento().equals("FTO")){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Archivo inaccesible", "Los formatos no tienen archivos .pdf anexos");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        }else{
            copiarPDF();
            documentosActual.setContarVista(documentosActual.getContarVista()+1);
            getDocumentosFacade().edit(documentosActual);
            return "verDocumento";
        }
    }
    public String prepareHistorial(){
        return "listaHistorial";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGestorDocumental/documento/lista";
    }
    public String prepareModuloUser() {
        recargarLista();
        return "/usuario/modGestorDocumental/documento/principal";
    }
    public String prepareModuloAdmin() {
        recargarLista();
        return "/administrador/modulos";
    }
    public String prepareListUser() {
        recargarLista();
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    
    public String delete() {
        try {   
            if(documentosActual.getIdTipoDocumento().getIdTipoDocumento().equals("FTO")){
                File deleteDoc = new File(documentosActual.getUbicacionDoc());
                deleteDoc.delete();
            }else{
                File deleteDoc = new File(documentosActual.getUbicacionDoc());
                File deletePdf = new File(documentosActual.getUbicacionPdf());
                deleteDoc.delete();
                deletePdf.delete();
            }
            getDocumentosFacade().remove(documentosActual);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo eliminado correctamente", "El archivo "+documentosActual.getIdTipoDocumento().getNombre()+ " con codigo " +documentosActual.getCodigo()+" fue eliminado con exito");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return "listaHistorial";
        } catch (Exception e) {
            return "listaHistorial";
        }
    }
    public String add() {
        //int numero = getDocumentosProceso().size() + 1; codigo automatico
        try { 
            String codigo = documentosActual.getIdTipoDocumento().getIdTipoDocumento()+"-"+documentosActual.getIdProceso().getIdProceso()+"-"+documentosActual.getConsecutivo();
            T004Documento codigoValidador =getDocumentosFacade().findByCodigo(codigo);
            if(codigoValidador != null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Código duplicado", "Es posible que este archivo ya se encuentre guardado con el mismo código");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return null;
            }else{
                documentosActual.setCodigo(codigo); 
                documentosActual.setEstado("VIGENTE");
                documentosActual.setFecha(new Date());
                if(documentosActual.getIdTipoDocumento().getIdTipoDocumento().equals("FTO")){
                    codigo = documentosActual.getIdTipoDocumento().getIdTipoDocumento()+"-"+documentosActual.getProcedimiento().getCodigo()+"-"+documentosActual.getConsecutivo();
                    codigoValidador =getDocumentosFacade().findByCodigo(codigo);
                    if(codigoValidador != null){
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Código duplicado", "Es posible que este archivo ya se encuentre guardado con el mismo código");
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                        return null;
                    }else{
                        if(nameDoc ==null){
                            FacesMessage messageError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar archivo", "Debes cargar los archivos en el formulario");
                            RequestContext.getCurrentInstance().showMessageInDialog(messageError);
                            return null;
                        }else{
                            documentosActual.setCodigo(codigo);
                            nameDoc = "V"+documentosActual.getVersion()+"_"+documentosActual.getNombre().toUpperCase()+"."+nameDocExt;
                            newFileNameDoc = "intranet"+File.separatorChar+"GESTORDOCUMENTAL"+File.separatorChar+documentosActual.getIdProceso().getIdProceso().toUpperCase()+File.separatorChar+documentosActual.getCodigo()+"_"+nameDoc;
                            documentosActual.setUbicacionDoc(newFileNameDoc);
                            transferirDoc();
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo creado correctamente", "El archivo "+documentosActual.getIdTipoDocumento().getNombre()+ " con codigo " +documentosActual.getCodigo()+" fue guardado con exito");
                            RequestContext.getCurrentInstance().showMessageInDialog(message);
                            documentosActual.setContarVista(0);
                            getDocumentosFacade().create(documentosActual);
                            recargarLista();
                            return "/administrador/modGestorDocumental/documento/lista";
                        }
                    }
                } else{
                    if(nameDoc ==null || namePdf == null){
                        FacesMessage messageError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar archivo", "Debes cargar los archivos en el formulario");
                        RequestContext.getCurrentInstance().showMessageInDialog(messageError);
                        return null;
                    }else{
                        codigo = documentosActual.getIdTipoDocumento().getIdTipoDocumento()+"-"+documentosActual.getIdProceso().getIdProceso()+"-"+documentosActual.getConsecutivo();
                        nameDoc = "V"+documentosActual.getVersion()+"_"+documentosActual.getNombre().toUpperCase()+"."+nameDocExt;
                        newFileNameDoc = "intranet"+File.separatorChar+"GESTORDOCUMENTAL"+File.separatorChar+documentosActual.getIdProceso().getIdProceso().toUpperCase()+File.separatorChar+documentosActual.getCodigo()+"_"+nameDoc;
                        documentosActual.setUbicacionDoc(newFileNameDoc);
                        namePdf = "V"+documentosActual.getVersion()+"_"+documentosActual.getNombre().toUpperCase()+".pdf";
                        newFileNamePdf = "intranet"+File.separatorChar+"GESTORDOCUMENTAL"+File.separatorChar+documentosActual.getIdProceso().getIdProceso().toUpperCase()+File.separatorChar+documentosActual.getCodigo()+"_"+namePdf;
                        documentosActual.setUbicacionPdf(newFileNamePdf);
                        transferirDoc();
                        transferirPdf();
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo creado correctamente", "El archivo "+documentosActual.getIdTipoDocumento().getNombre()+ " con codigo " +documentosActual.getCodigo()+" fue guardado con exito");
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                        documentosActual.setContarVista(0);
                        getDocumentosFacade().create(documentosActual);
                        recargarLista();
                        return "/administrador/modGestorDocumental/documento/lista";
                    }
                }
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    
    
    
    public String update() {
        try {
            if (documentosActual.getVersion() > version){
                //Llenado de informacion restante
                documentosActual.setEstado("VIGENTE");
                documentosActual.setFecha(new Date());
                if(documentosActual.getIdTipoDocumento().getIdTipoDocumento().equals("FTO")){
                    if(nameDoc ==null){
                            FacesMessage messageError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al editar archivo", "Esta es una nueva versión del documento, debes cargar los archivos en el formulario");
                            RequestContext.getCurrentInstance().showMessageInDialog(messageError);
                            return null;
                        }else{
                            nameDoc = "V"+documentosActual.getVersion()+"_"+documentosActual.getNombre().toUpperCase()+"."+nameDocExt;
                            newFileNameDoc = "intranet"+File.separatorChar+"GESTORDOCUMENTAL"+File.separatorChar+documentosActual.getIdProceso().getIdProceso().toUpperCase()+File.separatorChar+documentosActual.getCodigo()+"_"+nameDoc;
                            documentosActual.setUbicacionDoc(newFileNameDoc);
                            transferirDoc();
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo editado correctamente", "El archivo "+documentosActual.getIdTipoDocumento().getNombre()+ " con codigo " +documentosActual.getCodigo()+" fue editado con exito");
                            RequestContext.getCurrentInstance().showMessageInDialog(message);
                            getDocumentosFacade().edit(documentosActual);
                            recargarLista();
                            documentosActual = new T004Documento();
                            documentosActual.setCodigo(codigoVersion);
                            documentosActual.setDescripcion(descripcionVersion);
                            documentosActual.setEstado("NO VIGENTE");
                            documentosActual.setFecha(fechaVersion);
                            documentosActual.setIdTipoDocumento(new T006TipoDocumento(tipoDocumentoVersion));
                            documentosActual.setIdProceso(new T005Proceso(procesoVersion));
                            documentosActual.setNombre(nombreVersion);
                            documentosActual.setTipo(tipoVersion);
                            documentosActual.setUbicacionPdf(ubicacionPdfVersion);
                            documentosActual.setUbicacionDoc(ubicacionDocVersion);
                            documentosActual.setVersion(version);
                            getDocumentosFacade().create(documentosActual);
                            return "lista";
                        }
                }else{
                    if(nameDoc ==null || namePdf == null){
                        FacesMessage messageError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al editar archivo", "Esta es una nueva versión del documento, debes cargar los archivos en el formulario");
                        RequestContext.getCurrentInstance().showMessageInDialog(messageError);
                        return null;
                    }else{
                        nameDoc = "V"+documentosActual.getVersion()+"_"+documentosActual.getNombre().toUpperCase()+"."+nameDocExt;
                        newFileNameDoc = "intranet"+File.separatorChar+"GESTORDOCUMENTAL"+File.separatorChar+documentosActual.getIdProceso().getIdProceso().toUpperCase()+File.separatorChar+documentosActual.getCodigo()+"_"+nameDoc;
                        documentosActual.setUbicacionDoc(newFileNameDoc);
                        transferirDoc();
                        namePdf = "V"+documentosActual.getVersion()+"_"+documentosActual.getNombre().toUpperCase()+".pdf";
                        newFileNamePdf = "intranet"+File.separatorChar+"GESTORDOCUMENTAL"+File.separatorChar+documentosActual.getIdProceso().getIdProceso().toUpperCase()+File.separatorChar+documentosActual.getCodigo()+"_"+namePdf;
                        documentosActual.setUbicacionPdf(newFileNamePdf);
                        transferirPdf();
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo editado correctamente", "El archivo "+documentosActual.getIdTipoDocumento().getNombre()+ " con codigo " +documentosActual.getCodigo()+" fue editado con exito");
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                        getDocumentosFacade().edit(documentosActual);
                        recargarLista();
                        documentosActual = new T004Documento();
                        documentosActual.setCodigo(codigoVersion);
                        documentosActual.setDescripcion(descripcionVersion);
                        documentosActual.setEstado("NO VIGENTE");
                        documentosActual.setFecha(fechaVersion);
                        documentosActual.setIdTipoDocumento(new T006TipoDocumento(tipoDocumentoVersion));
                        documentosActual.setIdProceso(new T005Proceso(procesoVersion));
                        documentosActual.setNombre(nombreVersion);
                        documentosActual.setTipo(tipoVersion);
                        documentosActual.setUbicacionPdf(ubicacionPdfVersion);
                        documentosActual.setUbicacionDoc(ubicacionDocVersion);
                        documentosActual.setVersion(version);
                        getDocumentosFacade().create(documentosActual);
                        return "lista";
                    }
                } 
            }else if (documentosActual.getVersion() == version){
                if(documentosActual.getIdTipoDocumento().getIdTipoDocumento().equals("FTO")){
                    if(nameDoc ==null){
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo editado correctamente", "El archivo "+documentosActual.getIdTipoDocumento().getNombre()+ " con codigo " +documentosActual.getCodigo()+" fue editado con exito");
                            RequestContext.getCurrentInstance().showMessageInDialog(message);
                            getDocumentosFacade().edit(documentosActual);
                            recargarLista();
                            return "lista";
                        }else{
                            nameDoc = "V"+documentosActual.getVersion()+"_"+documentosActual.getNombre().toUpperCase()+"."+nameDocExt;
                            newFileNameDoc = "intranet"+File.separatorChar+"GESTORDOCUMENTAL"+File.separatorChar+documentosActual.getIdProceso().getIdProceso().toUpperCase()+File.separatorChar+documentosActual.getCodigo()+"_"+nameDoc;
                            documentosActual.setUbicacionDoc(newFileNameDoc);
                            transferirDoc();
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo editado correctamente", "El archivo "+documentosActual.getIdTipoDocumento().getNombre()+ " con codigo " +documentosActual.getCodigo()+" fue editado con exito");
                            RequestContext.getCurrentInstance().showMessageInDialog(message);
                            getDocumentosFacade().edit(documentosActual);
                            recargarLista();
                            return "lista";
                        }
                }else{
                    if(nameDoc ==null || namePdf == null){
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo editado correctamente", "El archivo "+documentosActual.getIdTipoDocumento().getNombre()+ " con codigo " +documentosActual.getCodigo()+" fue editado con exito");
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                        getDocumentosFacade().edit(documentosActual);
                        recargarLista();
                        return "lista";
                    }else{
                        nameDoc = "V"+documentosActual.getVersion()+"_"+documentosActual.getNombre().toUpperCase()+"."+nameDocExt;
                        newFileNameDoc = "intranet"+File.separatorChar+"GESTORDOCUMENTAL"+File.separatorChar+documentosActual.getIdProceso().getIdProceso().toUpperCase()+File.separatorChar+documentosActual.getCodigo()+"_"+nameDoc;
                        documentosActual.setUbicacionDoc(newFileNameDoc);
                        transferirDoc();
                        namePdf = "V"+documentosActual.getVersion()+"_"+documentosActual.getNombre().toUpperCase()+".pdf";
                        newFileNamePdf = "intranet"+File.separatorChar+"GESTORDOCUMENTAL"+File.separatorChar+documentosActual.getIdProceso().getIdProceso().toUpperCase()+File.separatorChar+documentosActual.getCodigo()+"_"+namePdf;
                        documentosActual.setUbicacionPdf(newFileNamePdf);
                        transferirPdf();
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo editado correctamente", "El archivo "+documentosActual.getIdTipoDocumento().getNombre()+ " con codigo " +documentosActual.getCodigo()+" fue editado con exito");
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                        getDocumentosFacade().edit(documentosActual);
                        recargarLista();
                        return "lista";
                    }
                } 
            }else if(documentosActual.getVersion() < version){
                FacesMessage messageError = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al editar archivo", "La versión no puede ser menor a la actual");
                RequestContext.getCurrentInstance().showMessageInDialog(messageError);
                return null;
            }
            recargarLista();
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    
    public void transferirDoc(){
        FileImageOutputStream imageOutputDoc;
        try {
                imageOutputDoc = new FileImageOutputStream(new File(newFileNameDoc));
                imageOutputDoc.write(dataDoc, 0, dataDoc.length);
                imageOutputDoc.close();
        }
        catch(IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        } 
    }
    public void transferirPdf(){
        FileImageOutputStream imageOutputPdf;
            try {
                    imageOutputPdf = new FileImageOutputStream(new File(newFileNamePdf));
                    imageOutputPdf.write(dataPdf, 0, dataPdf.length);
                    imageOutputPdf.close();
            }
            catch(IOException e) {
                throw new FacesException("Error in writing captured image.", e);
            }
    }
    
    public void cargarDocumentoPDF(FileUploadEvent event) throws IOException {
        if(namePdf==null){
            filePdf = event.getFile();
            dataPdf = IOUtils.toByteArray(filePdf.getInputstream());
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            if(documentosActual.getNombre()==null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al guardar el archivo", "Primero debes llenar todos los campos del formulario que se encuentra a la izquierda de la pantalla");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }else if(documentosActual.getIdTipoDocumento()==null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al guardar el archivo", "Primero debes llenar todos los campos del formulario que se encuentra a la izquierda de la pantalla");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }else if(documentosActual.getIdProceso()==null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al guardar el archivo", "Primero debes llenar todos los campos del formulario que se encuentra a la izquierda de la pantalla");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }else if(documentosActual.getVersion()==null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al guardar el archivo", "Primero debes llenar todos los campos del formulario que se encuentra a la izquierda de la pantalla");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }
            else{
                namePdf = event.getFile().getFileName();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo guardado", "El archivo "+ namePdf+" se guardo correctamente."));
            }       
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al guardar el archivo", "Solo se puede adjuntar un solo archivo, si necesitas cambiar el archivo actual debes dar clic en el icono de eliminar archivo");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo guardado", "El archivo "+ namePdf+" se guardo correctamente."));
        }
    }
    public void cargarDocumentoDOC(FileUploadEvent event) throws IOException {
        if(nameDoc==null){
            fileDoc = event.getFile();
            dataDoc = IOUtils.toByteArray(fileDoc.getInputstream());
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            if(documentosActual.getNombre()==null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al guardar el archivo", "Primero debes llenar todos los campos del formulario que se encuentra a la izquierda de la pantalla");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }else if(documentosActual.getIdTipoDocumento()==null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al guardar el archivo", "Primero debes llenar todos los campos del formulario que se encuentra a la izquierda de la pantalla");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }else if(documentosActual.getIdProceso()==null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al guardar el archivo", "DPrimero debes llenar todos los campos del formulario que se encuentra a la izquierda de la pantalla");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }else if(documentosActual.getVersion()==null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al guardar el archivo", "Primero debes llenar todos los campos del formulario que se encuentra a la izquierda de la pantalla");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }
            else{
                nameDocExt = FilenameUtils.getExtension(event.getFile().getFileName());
                nameDoc = event.getFile().getFileName();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo guardado", "El archivo "+ nameDoc+" se guardo correctamente."));
            }
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al guardar el archivo", "Solo se puede adjuntar un solo archivo, si necesitas cambiar el archivo actual debes dar clic en el icono de eliminar archivo");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo guardado", "El archivo "+ nameDoc+" se guardo correctamente."));
        }
    }
    
    public StreamedContent getImgPdf() throws FileNotFoundException {
        if(namePdf==null){
           String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/icons/sinsubir.png");
           return new DefaultStreamedContent(new FileInputStream(ruta), "image/png");   
        }else{
            String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/icons/pdf.png");
            return new DefaultStreamedContent(new FileInputStream(ruta), "image/png");    
        } 
    }
    public StreamedContent getImgDoc() throws FileNotFoundException {
        if(nameDoc==null){
            String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/icons/sinsubir.png");
            return new DefaultStreamedContent(new FileInputStream(ruta), "image/png");
        }else{
            String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/images/icons/doc.png");
            return new DefaultStreamedContent(new FileInputStream(ruta), "image/png"); 
        } 
    }
    public void borrarImgDoc() {
        if(nameDoc==null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No existen archivos para borrar")); 
        }else{
            nameDoc = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Archivo borrado", "El archivo se borro correctamente.")); 
        }
       
    }
    public void borrarImgPdf() {
        if(namePdf==null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No existen archivos para borrar")); 
        }else{
            namePdf = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Archivo borrado", "El archivo se borro correctamente."));   
        } 
    }
    public void borrarNombreDocSelect() {
        nameDoc = null;
        namePdf=null;
        documentosActual.setProcedimiento(null);
    }
    
    private StreamedContent archivoDescarga;

    public StreamedContent getDescargaPDF() throws FileNotFoundException {
        try {
            if(documentosActual.getUbicacionPdf()==null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "No existe documento", "El documento no tiene acceso");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return null;
            }else{
                InputStream stream =  new FileInputStream(documentosActual.getUbicacionPdf());
                return archivoDescarga = new DefaultStreamedContent(stream, "application/pdf", documentosActual.getCodigo()+"-"+documentosActual.getNombre()+".pdf");
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No existe documento", "El documento no tiene acceso");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        }
    }   
     public StreamedContent getDescargaDOC() throws FileNotFoundException {
        try {
            if(documentosActual.getUbicacionDoc()==null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "No existe documento", "El documento no tiene acceso");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return null;
            }else{
                InputStream stream =  new FileInputStream(documentosActual.getUbicacionDoc());
                String ext = FilenameUtils.getExtension(documentosActual.getUbicacionDoc());
                documentosActual.setContarVista(documentosActual.getContarVista()+1);
                getDocumentosFacade().edit(documentosActual);
                return archivoDescarga = new DefaultStreamedContent(stream, "application/"+ext, documentosActual.getCodigo()+"-"+documentosActual.getNombre()+"."+ext);
            }
        } catch (FileNotFoundException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No existe documento", "El documento no tiene acceso");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        }
    }   
    public StreamedContent getDescargaFormato() throws FileNotFoundException {
        try {
            if(documentosActual.getUbicacionDoc()==null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "No existe documento", "El documento no tiene acceso");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return null;
            }else{
                InputStream stream =  new FileInputStream(documentosActual.getUbicacionDoc());
                return archivoDescarga = new DefaultStreamedContent(stream, "application/pdf", documentosActual.getCodigo()+"-"+documentosActual.getNombre()+".pdf");
            }
        } catch (FileNotFoundException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No existe documento", "El documento no tiene acceso");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
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
    private static final long serialVersionUID = 1L;

    private StreamedContent streamedContent;

    
    public void init() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(new File("prueba.pdf"));
        streamedContent = new DefaultStreamedContent(fis, "application/pdf");
    }

    public StreamedContent getStreamedContent() {
        if (FacesContext.getCurrentInstance().getRenderResponse()) {
            return new DefaultStreamedContent();
        } else {
            return streamedContent;
        }
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
             if(documentosActual.getUbicacionDoc().equals("sd")){
                in = new FileInputStream(ruta);
                out = new FileOutputStream(archivoDestino);
                int c;
                while( (c = in.read() ) != -1) {
                    out.write(c);
                } 
            }
            else{
                archivoOrigen = new File(documentosActual.getUbicacionPdf());
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
        //Prepare View Formatos
     public String listaFormatosUser(){
        listaDocumentos =  getDocumentosFacade().documentosTipoFormatoUser(new T006TipoDocumento("FTO"));
        return "/usuario/modGestorDocumental/documento/listaCompletaFormatos";
    }
    public String listaDocumentosUser(){
        listaDocumentos =  getDocumentosFacade().documentosTipoDocumentoUser(new T006TipoDocumento("FTO"));
        return "/usuario/modGestorDocumental/documento/listaCompletaDocumentos";
    }
    
    public String GC(){
        procesoActual = new T005Proceso("GC", "Gestión Calidad");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String PP(){
        procesoActual = new T005Proceso("PP", "Planeación y Programación");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String CM(){
        procesoActual = new T005Proceso("CM", "Compras");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String GH(){
        procesoActual = new T005Proceso("GH", "Gestión Humana");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String ATE(){
        procesoActual = new T005Proceso("ATE", "Administración Tecnológica de Equipos");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String MTTO(){
        procesoActual = new T005Proceso("MTTO", "Mantenimiento");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String GI(){
        procesoActual = new T005Proceso("GI", "Gestión de Inventarios");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String CA(){
        procesoActual = new T005Proceso("CA", "Cartera");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String CO(){
        procesoActual = new T005Proceso("CO", "Costos");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String CC(){
        procesoActual = new T005Proceso("CC", "Control Calidad");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String TP(){
        procesoActual = new T005Proceso("TP", "Terminación Poliéster");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String TI(){
        procesoActual = new T005Proceso("TI","Tintorería");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String DE(){
        procesoActual = new T005Proceso("DE", "Deslizadores");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String TE(){
        procesoActual = new T005Proceso("TE", "Telares");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String TM(){
        procesoActual = new T005Proceso("TM", "Terminación Metálica");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String COT(){
        procesoActual = new T005Proceso("COT", "Contabilidad");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String ALM(){
        procesoActual = new T005Proceso("ALM", "Cedi y Bodegas");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }public String MDO(){
        procesoActual = new T005Proceso("MDO" ,"Mercadeo");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String VTS(){
        procesoActual = new T005Proceso("VTS" , "Ventas y Servicio al Cliente");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String TES(){
        procesoActual = new T005Proceso("TES" , "Tesorería");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String AC(){
        procesoActual = new T005Proceso("AC" , "Asuntos Corporativos");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String SMA(){
        procesoActual = new T005Proceso("SMA" , "Sostenibilidad y Medio Ambiente");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }public String FI(){
        procesoActual = new T005Proceso("FI" , "Financiero");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }public String SSTA(){
        procesoActual = new T005Proceso("SSTA" , "Seguridad y Salud en el Trabajo y Ambiental");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }
    public String ING(){
        procesoActual = new T005Proceso("ING", "Ingeniería y Proyectos" );
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }public String TIC(){
        recargarLista();
        procesoActual = new T005Proceso("TIC", "Tecnología Información" );
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }public String IDI(){
        procesoActual = new T005Proceso("IDI" , "Investigación, Desarrollo e Innovación");
        return "/usuario/modGestorDocumental/documento/listaProceso";
    }    
    public void validarNombre(FacesContext contex, UIComponent component, Object value)throws ValidatorException{
        T004Documento nombreArchivo =getDocumentosFacade().findByNombreArchivo((String)value, documentosActual.getIdProceso());
        if(nombreArchivo != null){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Archivo duplicado", "Es posible que este archivo ya se encuentre guardado en el mismo proceso");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Archivo duplicado","Es posible que este archivo ya se encuentre guardado en la base de datos con el mismo nombre y proceso, por favor verifica e intenta con un nombre distinto"));   
        }else{
            String nombre=(String) value;
            documentosActual.setNombre(nombre); 
        }
    }
    public void borrarNombreArchivo() {
        documentosActual.setNombre(null);
    }
    
    // Converter
    public T004Documento getDocumento(java.lang.Integer id) {
        return documentosFacade.find(id);
    }
    
    @FacesConverter(forClass = T004Documento.class)
    public static class DocumentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DocumentoController controller = (DocumentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "documentoController");
            return controller.getDocumento(getKey(value));
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
            if (object instanceof T004Documento) {
                T004Documento o = (T004Documento) object;
                return getStringKey(o.getIdDocumento());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T004Documento.class.getName());
            }
        }
    }
}
