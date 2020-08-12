/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T014UsuarioVolanteNomina;
import com.eurekaintranet.entities.T015VolanteNomina;
import com.eurekaintranet.entities.T016PeriodoVolanteNomina;
import com.eurekaintranet.entities.T017ErroresVolanteNomina;
import com.eurekaintranet.sessions.T014UsuarioVolanteNominaFacade;
import com.eurekaintranet.sessions.T015VolanteNominaFacade;
import com.eurekaintranet.sessions.T016PeriodoVolanteNominaFacade;
import com.eurekaintranet.sessions.T017ErroresVolanteNominaFacade;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.PdfWriter;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.imageio.stream.FileImageOutputStream;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@Named(value = "volantesNominaController")
@SessionScoped
public class VolantesNominaController implements Serializable {

    @EJB 
    private T014UsuarioVolanteNominaFacade usuarioVolanteNominaFacade;
    private T014UsuarioVolanteNomina usuarioVolanteNominaActual;
    @EJB 
    private T015VolanteNominaFacade volanteNominaFacade;
    private T015VolanteNomina volanteNominaActual;
    
    private List<T014UsuarioVolanteNomina> listaUsuarioVolanteNomina;
    private List<T015VolanteNomina> listaVolanteNomina;
    private List<T015VolanteNomina> listaVolanteNominaPorUsuario;
    
    private T001Usuario usuarioActual;
    private String cadenaCaracteresText1 = "";
    private String cadenaCaracteresText2 = "";
    private T016PeriodoVolanteNomina periodoVolante;
    
    private String correoUsuario;
    private String cedulaLogin;
    private String contrasenaLogin;
    
    private EmailController emailController;
    
    @EJB
    private T016PeriodoVolanteNominaFacade periodoVolanteFacade;
    private T016PeriodoVolanteNomina periodoVolanteActual;
    private List<T016PeriodoVolanteNomina> listaPeriodoVolante;
    
    @EJB
    private T017ErroresVolanteNominaFacade erroresVolanteFacade;
    private T017ErroresVolanteNomina erroresVolanteActual;
    private List<T017ErroresVolanteNomina> listaErroresVolante;
    
    private String tipoNomina;
    
    public VolantesNominaController() {
    }

    public T014UsuarioVolanteNominaFacade getUsuarioVolanteNominaFacade() {
        return usuarioVolanteNominaFacade;
    }

    public void setUsuarioVolanteNominaFacade(T014UsuarioVolanteNominaFacade usuarioVolanteNominaFacade) {
        this.usuarioVolanteNominaFacade = usuarioVolanteNominaFacade;
    }

    public T014UsuarioVolanteNomina getUsuarioVolanteNominaActual() {
        return usuarioVolanteNominaActual;
    }

    public void setUsuarioVolanteNominaActual(T014UsuarioVolanteNomina usuarioVolanteNominaActual) {
        this.usuarioVolanteNominaActual = usuarioVolanteNominaActual;
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

    public List<T014UsuarioVolanteNomina> getListaUsuarioVolanteNomina() {
        return listaUsuarioVolanteNomina = getUsuarioVolanteNominaFacade().findAll();
    }

    public void setListaUsuarioVolanteNomina(List<T014UsuarioVolanteNomina> listaUsuarioVolanteNomina) {
        this.listaUsuarioVolanteNomina = listaUsuarioVolanteNomina;
    }

    public List<T015VolanteNomina> getListaVolanteNomina() {
        return listaVolanteNomina = getVolanteNominaFacade().findAll();
    }
    
    public List<T015VolanteNomina> getListaVolanteNominaPeriodo() {
        return listaVolanteNomina;
    }
    public void setListaVolanteNomina(List<T015VolanteNomina> listaVolanteNomina) {
        this.listaVolanteNomina = listaVolanteNomina;
    }
    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public String getCadenaCaracteresText1() {
        return cadenaCaracteresText1;
    }
    public String getCadenaCaracteresText2(){ 
        return cadenaCaracteresText2;
    }
    public void setCadenaCaracteresText1(String cadenaCaracteresText1) {
        this.cadenaCaracteresText1 = cadenaCaracteresText1;
    }
    public void setCadenaCaracteresText2(String cadenaCaracteresText2) {
        this.cadenaCaracteresText2 = cadenaCaracteresText2;
    }

    public T016PeriodoVolanteNomina getPeriodoVolante() {
        return periodoVolante;
    }

    public void setPeriodoVolante(T016PeriodoVolanteNomina periodoVolante) {
        this.periodoVolante = periodoVolante;
    }
  

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getCedulaLogin() {
        return cedulaLogin;
    }

    public void setCedulaLogin(String cedulaLogin) {
        this.cedulaLogin = cedulaLogin;
    }

    public String getContrasenaLogin() {
        return contrasenaLogin;
    }

    public void setContrasenaLogin(String contrasenaLogin) {
        this.contrasenaLogin = contrasenaLogin;
    }

    public List<T015VolanteNomina> getListaVolanteNominaPorUsuario() {
        return listaVolanteNominaPorUsuario ;
    }

    public void setListaVolanteNominaPorUsuario(List<T015VolanteNomina> listaVolanteNominaPorUsuario) {
        this.listaVolanteNominaPorUsuario = listaVolanteNominaPorUsuario;
    }

    public T017ErroresVolanteNominaFacade getErroresVolanteFacade() {
        return erroresVolanteFacade;
    }

    public void setErroresVolanteFacade(T017ErroresVolanteNominaFacade erroresVolanteFacade) {
        this.erroresVolanteFacade = erroresVolanteFacade;
    }

    public T017ErroresVolanteNomina getErroresVolanteActual() {
        return erroresVolanteActual;
    }

    public void setErroresVolanteActual(T017ErroresVolanteNomina erroresVolanteActual) {
        this.erroresVolanteActual = erroresVolanteActual;
    }

    public List<T017ErroresVolanteNomina> getListaErroresVolante() {
        return listaErroresVolante = getErroresVolanteFacade().findAll();
    }

    public void setListaErroresVolante(List<T017ErroresVolanteNomina> listaErroresVolante) {
        this.listaErroresVolante = listaErroresVolante;
    }

    public String getTipoNomina() {
        return tipoNomina;
    }

    public void setTipoNomina(String tipoNomina) {
        this.tipoNomina = tipoNomina;
    }
    
    
    
    //Consultar periodo
    public String consultar(){
        listaVolanteNomina = getVolanteNominaFacade().consultaVolantePeriodo(periodoVolante.getIdPeriodo());
        return "listaVolante";
    }

    public T016PeriodoVolanteNominaFacade getPeriodoVolanteFacade() {
        return periodoVolanteFacade;
    }

    public void setPeriodoVolanteFacade(T016PeriodoVolanteNominaFacade periodoVolanteFacade) {
        this.periodoVolanteFacade = periodoVolanteFacade;
    }

    public T016PeriodoVolanteNomina getPeriodoVolanteActual() {
        return periodoVolanteActual;
    }

    public void setPeriodoVolanteActual(T016PeriodoVolanteNomina periodoVolanteActual) {
        this.periodoVolanteActual = periodoVolanteActual;
    }

    public List<T016PeriodoVolanteNomina> getListaPeriodoVolante() {
        return listaPeriodoVolante;
    }

    public void setListaPeriodoVolante(List<T016PeriodoVolanteNomina> listaPeriodoVolante) {
        this.listaPeriodoVolante = listaPeriodoVolante;
    }

  
    
    
    //NAVIGATION
    
    public String prepareListErrores(){
        erroresVolanteActual = new T017ErroresVolanteNomina();
        return "/administrador/modVolantesPago/volante/logErrores";
    }
     
    public void prepareCreatePeriodo(){
        periodoVolante = new T016PeriodoVolanteNomina();
    }
    public void addPeriodo() {
        try { 
            T016PeriodoVolanteNomina consulta=getPeriodoVolanteFacade().findByPeriodoDuplicado(periodoVolante.getAnio(),periodoVolante.getMes(),periodoVolante.getDia());
            if(consulta != null){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo duplicado", "Error en la creación del periodo");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }else{
                getPeriodoVolante().setUsuarioCreacion(usuarioActual.getUsername());
                getPeriodoVolante().setFechaCreacion(new Date());
                getPeriodoVolanteFacade().create(periodoVolante);
                addSuccessMessage("Periodo creado correctamente", "periodo creado correctamente");
                recargarLista();
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
    }
    
    
    public String prepareCreateUsuario(){
        cadenaCaracteresText1 = "";
        usuarioVolanteNominaActual = new T014UsuarioVolanteNomina();
        return "/administrador/modVolantesPago/usuario/crearUsuario";
    }
    public String prepareCreateVolante(){
        recargarLista();
        cadenaCaracteresText2 = "";
        volanteNominaActual= new T015VolanteNomina();
        periodoVolante = new T016PeriodoVolanteNomina();
        return "/administrador/modVolantesPago/volante/crearVolante";
    }
    public String prepareListUsuario(){
        return "/administrador/modVolantesPago/usuario/listaUsuario";
    }
    public String prepareListVolante(){
        recargarLista();
        return "/administrador/modVolantesPago/volante/listaVolante";
    }
    public String prepareList() throws ParseException{
        return "/administrador/modVolantesPago/principal";
    }
     public String correos(){
        return "lista";
    }
    public String addUsuario() throws IOException{
        try {
            procesarText1(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.P01"));
            recargarUsuario();
            return "listaUsuario";
        }catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al leer datos", "La estructura del archivo no fue reconocida");
            RequestContext.getCurrentInstance().showMessageInDialog(message); 
            return null;
        }
    }
    public String addVolante() throws IOException{
        try {
            List<T015VolanteNomina> consultaTipoNomina = new ArrayList();
            if(!tipoNomina.equals("Vacaciones")){
               consultaTipoNomina=getVolanteNominaFacade().findByTipoNominaPeriodo(tipoNomina, periodoVolante.getIdPeriodo());
            }
            if(!consultaTipoNomina.isEmpty()){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error en la creación de volantes", "Este tipo de nomina ya fue creada en este mismo periodo");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return null;
            }else{
                procesarText2(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text2.P01"));
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos procesados y creados", "Los volantes fueron creados correctamente");
                RequestContext.getCurrentInstance().showMessageInDialog(message); 
                recargarVolante();
                consultar();
                return "listaVolante"; 
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al leer datos", "La estructura del archivo no fue reconocida");
            RequestContext.getCurrentInstance().showMessageInDialog(message); 
            return null;
        }
    }
    //Leer archivo text1 - usuarios
    public String leerText1(String nombre){
        File f;
        FileReader lectorArchivo;
        try{
            f = new File(nombre);
            lectorArchivo = new FileReader(f);
            BufferedReader br = new BufferedReader(lectorArchivo);
            String l="";
            String aux="";
            while(true)
            {
               aux=br.readLine();
               if(aux!=null){
                   try {
                     int numero =  Integer.parseInt(aux.substring(1,2));
                        if(numero==0 || numero==1 || numero==2 || numero==3 || numero==4 || numero==5 || numero==6 || numero==7 || numero==8 || numero==9 ){
                        l=l+aux+"\n"; 
                      }
                   } catch (Exception e) {
                   }
               }   
               else{
                   break;
               }

            }
            br.close();
            lectorArchivo.close();
            return l;
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }

        return null;
    }
    //Leer archivo text2 - volantes
    public String leerText2(String nombre){
        File f;
        FileReader lectorArchivo;
        try{
            f = new File(nombre);
            lectorArchivo = new FileReader(f);
            BufferedReader br = new BufferedReader(lectorArchivo);
            String l="";
            String aux="";
            String duplicado="";
            while(true)
            {
               aux=br.readLine();
               if(aux!=null){
                    try { 
                        int numero =  Integer.parseInt(aux.substring(1,2));
                        if(!duplicado.equals(aux.substring(0, 45))){
                            if(numero==0 || numero==1 || numero==2 || numero==3 || numero==4 || numero==5 || numero==6 || numero==7 || numero==8 || numero==9 ){
                                l=l+aux.substring(0, 45)+"\n"; 
                                duplicado = aux.substring(0, 45);
                            }
                        }
                    } catch (Exception e) {
                        
                    }
                }   
               else{
                   break;
               }
            }
            br.close();
            lectorArchivo.close();
            return l;
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }

        return null;
    }
    //Guardar datos del archivo text1 - usuarios
    public String procesarText1(String nombre){
        String cadenaEncriptada;
        File f;
        FileReader lectorArchivo;
        try{
            f = new File(nombre);
            lectorArchivo = new FileReader(f);
            BufferedReader br = new BufferedReader(lectorArchivo);
            String l="";
            String aux="";
            while(true)
            {
               aux=br.readLine();
               if(aux!=null){
                   try {
                     int numero =  Integer.parseInt(aux.substring(1,2));
                        if(numero==0 || numero==1 || numero==2 || numero==3 || numero==4 || numero==5 || numero==6 || numero==7 || numero==8 || numero==9 ){
                        l=aux; 
                        int maxLength = (l.length());
                        usuarioVolanteNominaActual.setCodigo(l.substring(1, 5).trim());
                        usuarioVolanteNominaActual.setNombre(l.substring(16, 50).trim());
                        usuarioVolanteNominaActual.setCedula(l.substring(60, 70).trim());
                        usuarioVolanteNominaActual.setCorreo(l.substring(70, maxLength).trim());
                        usuarioVolanteNominaActual.setUsuarioCreacion(usuarioActual.getUsername());
                        usuarioVolanteNominaActual.setFechaCreacion(new Date());
                        cadenaEncriptada = encriptar(l.substring(60, 70).trim());
                        usuarioVolanteNominaActual.setContrasena(cadenaEncriptada);
                        getUsuarioVolanteNominaFacade().create(usuarioVolanteNominaActual);
                        usuarioVolanteNominaActual = new T014UsuarioVolanteNomina();
                        l=""; 
                      }
                   } catch (Exception e) {
                        for (T014UsuarioVolanteNomina item : getListaUsuarioVolanteNomina()) { 
                            if(item.getCodigo().equals(usuarioVolanteNominaActual.getCodigo())){
                                usuarioVolanteNominaActual.setContrasena(item.getContrasena());
                                 getUsuarioVolanteNominaFacade().edit(usuarioVolanteNominaActual);
                                 break;
                            }           
                        }
                        usuarioVolanteNominaActual = new T014UsuarioVolanteNomina();
                   }
               }   
               else{
                   break;
               }

            }
            br.close();
            lectorArchivo.close();
            return l;
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }

        return null;
    }
    //Guardar datos del archivo text2 - volantes
    public String procesarText2(String nombre){
        File f;
        FileReader lectorArchivo;
        try{
            f = new File(nombre);
            lectorArchivo = new FileReader(f);
            BufferedReader br = new BufferedReader(lectorArchivo);
            String l="";
            String n ="";
            String aux="";
            String duplicado="";
            int noEncontrado = 0;
            while(true){
                aux=br.readLine();
                if(aux!=null){
                    if(!aux.equals("====================================================================================================================================")){
                        try {
                            int maxLengthDuplicado = (duplicado.length());
                            int maxLengthAux = (aux.length());
                            try {
                                if(!duplicado.substring(0,maxLengthDuplicado).equals(aux.substring(0, maxLengthAux))){
                                    l=l+aux+"\n";  
                                    duplicado=aux;
                                } 
                            } catch (Exception e) {
                                l=l+aux+"\n"; 
                                duplicado=aux;
                            }
                            
                            int numero =  Integer.parseInt(aux.substring(1,2));
                            if(numero==0 || numero==1 || numero==2 || numero==3 || numero==4 || numero==5 || numero==6 || numero==7 || numero==8 || numero==9 ){
                                n=aux; 
                                volanteNominaActual.setCodigo(new T014UsuarioVolanteNomina(n.substring(0,4)));
                            }
                        }catch (Exception e) {
                           
                        } 
                    }else{
                        try {
                            if(volanteNominaActual.getCodigo()!=null ){
                                volanteNominaActual.setTipoNomina(tipoNomina);
                                volanteNominaActual.setIdPeriodo(new T016PeriodoVolanteNomina(periodoVolante.getIdPeriodo()));
                                volanteNominaActual.setVolante(l.trim());
                                volanteNominaActual.setUsuarioCreacion(usuarioActual.getUsername());
                                volanteNominaActual.setFechaCreacion(new Date());
                                volanteNominaActual.setVolanteEnviado(false);
                                getVolanteNominaFacade().create(volanteNominaActual);
                                volanteNominaActual = new T015VolanteNomina();
                                l=""; 
                            }
                        } catch (Exception e) {
                            erroresVolanteActual = new T017ErroresVolanteNomina();
                            erroresVolanteActual.setDescripcion("El usuario al que le pertenece el volante no se encuentra registrado");
                            erroresVolanteActual.setFechaCreacion(new Date());
                            erroresVolanteActual.setUsuarioCreacion(usuarioActual.getUsername());
                            erroresVolanteActual.setVolante(volanteNominaActual.getVolante());
                            erroresVolanteActual.setIdPeriodo(new T016PeriodoVolanteNomina(periodoVolante.getIdPeriodo()));
                            getErroresVolanteFacade().create(erroresVolanteActual);
                            erroresVolanteActual = new T017ErroresVolanteNomina();   
                        }
                    }  
                }   
                else{
                    volanteNominaActual.setTipoNomina(tipoNomina);
                    volanteNominaActual.setCodigo(new T014UsuarioVolanteNomina(n.substring(0,4)));
                    volanteNominaActual.setIdPeriodo(new T016PeriodoVolanteNomina(periodoVolante.getIdPeriodo()));
                    volanteNominaActual.setVolante(l.trim());
                    volanteNominaActual.setUsuarioCreacion(usuarioActual.getUsername());
                    volanteNominaActual.setFechaCreacion(new Date());
                    volanteNominaActual.setVolanteEnviado(false);
                    getVolanteNominaFacade().create(volanteNominaActual);
                    volanteNominaActual = new T015VolanteNomina();
                    break;
                }
                 
            }
            br.close();
            lectorArchivo.close();
            return l;
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
        return null;
    }
    
    
    //Email send
    public void sendMailVolante() throws FileNotFoundException{
        String descripcionQuincena;
        emailController = new EmailController();
        if(volanteNominaActual.getIdPeriodo().getDia().equals("15")){
                    descripcionQuincena = "1Q";
                }else{
                    descripcionQuincena = "2Q";
                }
                String asunto = "Comprobante de pago "+descripcionQuincena+" "+volanteNominaActual.getIdPeriodo().getMesDescripcion()+"-"+volanteNominaActual.getIdPeriodo().getAnio()+" EKA CORPORACIÓN S.A";
                String cuerpo = "Adjuntamos comprobante de pago por el concepto mencionado en el asunto, por seguridad de la información para abrir el archivo debes digitar tu número de cédula.\n" +
                                "\n" +
                                "Por favor no responda este mensaje ya que se genera automáticamente y es solo para fines informativos" +
                                "\n" +
                                "\n" +
                                "Cordialmente," +
                                "\n" +
                                "\n" +
                                "Asuntos Corporativos"+
                                "\n" +
                                "\n";
                String adjunto = "resources/plano/volante.pdf";
                String nombreAdjunto = "comprobanteEka.pdf";
        createPdf();
        emailController.sendMailAdjunto(volanteNominaActual.getCodigo().getCorreo(), asunto, cuerpo, adjunto, nombreAdjunto);  
    }
    
    public void sendMailVolanteMasivo() throws FileNotFoundException, IOException{
        try {
            if(listaVolanteNomina!=null){
                String descripcionQuincena;
                emailController = new EmailController();
                if(periodoVolante.getDia().equals("15")){
                    descripcionQuincena = "1Q";
                }else{
                    descripcionQuincena = "2Q";
                }
                String asunto = "Comprobante de pago "+descripcionQuincena+" "+periodoVolante.getMesDescripcion()+"-"+periodoVolante.getAnio()+" EKA CORPORACIÓN S.A";
                String cuerpo = "Adjuntamos comprobante de pago por el concepto mencionado en el asunto, por seguridad de la información para abrir el archivo debes digitar tu número de cédula.\n" +
                                "\n" +
                                "Por favor no responda este mensaje ya que se genera automáticamente y es solo para fines informativos" +
                                "\n" +
                                "\n" +
                                "Cordialmente," +
                                "\n" +
                                "\n" +
                                "Asuntos Corporativos"+
                                "\n" +
                                "\n";
                String adjunto = "resources/plano/volante.pdf";
                String nombreAdjunto = "comprobanteEka.pdf";
                consultar();
                int contEnviados = 0;
                int contNoEnviados = 0;
                 for (T015VolanteNomina item : listaVolanteNomina) { 
                    if(!item.isVolanteEnviado()){
                        volanteNominaActual = new T015VolanteNomina();
                        volanteNominaActual = item;
                         if(volanteNominaActual.getCodigo().getCorreo().trim().equals("")){
                             contNoEnviados ++;
                         }else{
                            contEnviados++;
                            createPdf();
                            emailController.sendMailAdjunto(volanteNominaActual.getCodigo().getCorreo(), asunto, cuerpo, adjunto, nombreAdjunto);
                            volanteNominaActual.setVolanteEnviado(true);
                            getVolanteNominaFacade().edit(volanteNominaActual);
                            recargarVolante();
                        }  
                    }
                }
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Volantes enviados correctamente", "Total volantes enviados: "+contEnviados+", Total volantes no enviados: "+contNoEnviados);
                RequestContext.getCurrentInstance().showMessageInDialog(message); 
            }else{
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Volantes no enviados correctamente", "Total volantes enviados: "+0);
                RequestContext.getCurrentInstance().showMessageInDialog(message); 
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error:"+e+"\n Numero de volantes enviados: "+0);
            RequestContext.getCurrentInstance().showMessageInDialog(message); 
        }
    }
    
    public void sendMailVolanteProgramado() throws FileNotFoundException {
        // Recipient's email ID needs to be mentioned.
        String to = "luisc@ekazipper.com";
        // Sender's email ID needs to be mentioned
        String from = "luisc@ekazipper.com";
        final String username = "luisc@ekazipper.com";//change accordingly
        final String password = "Juniorking478#";//change accordingly
        // Assuming you are sending email through relay.jangosmtp.net
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            // Set Subject: header fieldpd
            message.setSubject("Volante de pago EKA CORPORACIÓN");
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Now set the actual message
            messageBodyPart.setText("Volante de pago EKA CORPORACIÓN");
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Part two is attachment
            //messageBodyPart = new MimeBodyPart();
            //createPdf();
            //String filename = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/volante.pdf");
           // DataSource source = new FileDataSource(filename);
            //messageBodyPart.setDataHandler(new DataHandler(source));
            //messageBodyPart.setFileName("VolanteEka.pdf");
            //multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            //message.setContent(multipart);
            // Send message
            Transport.send(message);
            //FacesMessage messageConfirmacion = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correo enviado correctamente", "El volante fue enviado al siguiente correo: "+volanteNominaActual.getCodigo().getCorreo());
            //RequestContext.getCurrentInstance().showMessageInDialog(messageConfirmacion);
            //recargarLista();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void createPdf() throws FileNotFoundException{
        Document document = new Document(PageSize.LETTER, 20, 20, 50, 50);
        Font fuente= new Font();
        fuente.setFamily("COURIER");
        fuente.setSize(7);
            try {
            HtmlWriter.getInstance(document , System.out);
            PdfWriter writer =  PdfWriter.getInstance(document , new FileOutputStream(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/volante.pdf")));
            writer.setEncryption(volanteNominaActual.getCodigo().getCedula().getBytes(), "Admin123456".getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
            document.addAuthor("EKA CORPORACIÓN S.A"); 
            document.addSubject("Tu volante de nomina."); 
            document.open(); 
            document.add(new Paragraph("====================================================================================================================================", fuente));
            document.add(new Paragraph(volanteNominaActual.getVolante(), fuente));
            document.add(new Paragraph("====================================================================================================================================", fuente));
            
            } catch(DocumentException de) {
             System.err.println(de.getMessage());
            }
            document.close();
    }
    public void cargarPlanoUsuario(FileUploadEvent event) throws IOException {
        recargarUsuario();
        UploadedFile file = event.getFile();
        byte[] data = IOUtils.toByteArray(file.getInputstream());
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        
        String newFileName = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.P01");
        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            VolantesNominaController a = new VolantesNominaController();
            cadenaCaracteresText1 = a.leerText1(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.P01"));
        }
        catch(IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }  
    }
     public void cargarPlanoVolante(FileUploadEvent event2) throws IOException {
        recargarVolante();
        UploadedFile file = event2.getFile();
        byte[] data = IOUtils.toByteArray(file.getInputstream());
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        
        String newFileName = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text2.P01");
        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            VolantesNominaController a = new VolantesNominaController();
            cadenaCaracteresText2 = a.leerText2(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text2.P01"));

        }
        catch(IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }  
    }
     
    public void recargarLista(){
        periodoVolanteActual = new T016PeriodoVolanteNomina();
        periodoVolante = new T016PeriodoVolanteNomina();
        tipoNomina = "";
        volanteNominaActual = new T015VolanteNomina();
        usuarioVolanteNominaActual = new T014UsuarioVolanteNomina();
        listaPeriodoVolante = new ArrayList<>();
        listaVolanteNomina = new ArrayList<>();
    }
    public void recargarVolante() throws IOException{
        try {
            BufferedWriter volante = new BufferedWriter(new FileWriter(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text2.P01")));
            volante.write("");
            volante.close(); 
        } catch (Exception e) {
            System.out.println("No se pudo limpiar archivo "+e);
        } 
    }
    public void recargarUsuario() throws IOException{
        try {
            BufferedWriter usuario = new BufferedWriter(new FileWriter(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.P01")));
            usuario.write("");
            usuario.close(); 
        } catch (Exception e) {
            System.out.println("No se pudo limpiar archivo "+e);
        } 
    }
    public StreamedContent getDescargaVolantePDF() throws FileNotFoundException {
        try {
            createPdf();
            InputStream stream =  new FileInputStream(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/volante.pdf"));
            return new DefaultStreamedContent(stream, "application/pdf", volanteNominaActual.getCodigo().getCodigo()+"-"+volanteNominaActual.getCodigo().getNombre()+".pdf");
        } catch (Exception e) {
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
    
    //encriptacion de contraseña
    private static String encriptar(String s) throws UnsupportedEncodingException{
        return Base64.getEncoder().encodeToString(s.getBytes("utf-8"));
    }
    //encriptacion de contraseña
    private static String desencriptar(String s) throws UnsupportedEncodingException{
        byte[] decode = Base64.getDecoder().decode(s.getBytes());
        return new String(decode, "utf-8");
    }
    
 
    
    
    
    
    public String loginVolante()throws ValidatorException, UnsupportedEncodingException{
        String cadenaEncriptada = encriptar(contrasenaLogin);
        usuarioVolanteNominaActual=getUsuarioVolanteNominaFacade().usuarioVolanteLogin(cedulaLogin,cadenaEncriptada);
        if(usuarioVolanteNominaActual == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede iniciar sesión"  , "Los datos ingresados no son correctos, por favor intentalo nuevamente"));
            return null;
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuarioVolanteNominaActual.getNombre()));
            listaVolanteNominaPorUsuario = getVolanteNominaFacade().volantePorUsuario(usuarioVolanteNominaActual.getCodigo());
            return "volantePago/volanteUsuario";  
        }
    }
    public String logoutUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.invalidate();
            usuarioVolanteNominaActual=null;
            listaVolanteNominaPorUsuario=null;
            cedulaLogin="";
            contrasenaLogin="";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Haz cerrado sesión correctamente", "Gracias por usar los servicios de Eureka, hasta pronto"));
            return "/usuario/modZonaAgil/talentoHumano/principal";
        } catch (ServletException e) {
            return "/index";
        }
    }
    /*
    public void sendMailVolante() throws FileNotFoundException {
        // Recipient's email ID needs to be mentioned.
        String to = volanteNominaActual.getCodigo().getCorreo();
        // Sender's email ID needs to be mentioned
        String from = "luisc@ekazipper.com";
        final String username = "luisc@ekazipper.com";//change accordingly
        final String password = "Juniorking478#";//change accordingly
        // Assuming you are sending email through relay.jangosmtp.net
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            // Set Subject: header field
            message.setSubject("Volante de pago EKA CORPORACIÓN");
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Now set the actual message
            messageBodyPart.setText("Volante de pago EKA CORPORACIÓN");
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            createPdf();
            String filename = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/volante.pdf");
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("VolanteEka.pdf");
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            message.setContent(multipart);
            // Send message
            Transport.send(message);
            FacesMessage messageConfirmacion = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correo enviado correctamente", "El volante fue enviado al siguiente correo: "+volanteNominaActual.getCodigo().getCorreo());
            RequestContext.getCurrentInstance().showMessageInDialog(messageConfirmacion);
            recargarLista();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
       public void programarTarea() {
            ScheduledExecutorService execService
                                            =	Executors.newScheduledThreadPool(5);
            execService.scheduleAtFixedRate(()->{

                    //The repetitive task, say to update Database
                    System.out.println("Tarea programada: "+ new java.util.Date());

            }, 0, 10000L, TimeUnit.MILLISECONDS);
    }
    
    public void runDemo() throws ParseException {
	  try {

         // specify the job' s details..
         JobDetail job = JobBuilder.newJob(TestJob.class)
                                   .withIdentity("testJob1")
                                   .build();

         // CronTrigger the job to run on the every 20 seconds
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                             .withIdentity("crontrigger","crontriggergroup1")
                             .withSchedule(CronScheduleBuilder.cronSchedule("10 * * * * ?"))
                             .build();

         //schedule the job
         SchedulerFactory schFactory = new StdSchedulerFactory();
         Scheduler sch = schFactory.getScheduler();
         sch.start();
         sch.scheduleJob(job, cronTrigger);

      } catch (SchedulerException e) {
         e.printStackTrace();
      }
    }
    */
    
}
