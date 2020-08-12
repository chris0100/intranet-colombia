/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T012Lecturas;
import com.eurekaintranet.sessions.T012LecturasFacade;
import javax.inject.Named;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.xml.rpc.holders.ShortHolder;
import org.apache.axis.message.MessageElement;
import org.primefaces.context.RequestContext;
import org.tempuri.ImportarXMLResponseImportarXMLResult;
import org.tempuri.WSUNOEESoapProxy;

/**
 *
 * @author Lccabalr
 */
@Named (value = "lecturasController")
@ManagedBean
@javax.faces.bean.SessionScoped
public class LecturasController implements Serializable{

    @EJB
    private T012LecturasFacade lecturasFacade;
    private T012Lecturas lecturasActual;
    private T012Lecturas lecturasActualLista;
    private T001Usuario usuarioActual;
    private List<T012Lecturas> listaLecturas;
    private List<T012Lecturas> listaLecturasOp;
    private boolean aplicarTodo;
    private String estado;
    private String op;
    
    List<String> total1;
    List<String> total2;
    List<String> total3;
    List<String> total4;
    List<String> total5;
    List<String> total6;
    List<String> total7;
    List<String> total8;
    
    private String mensajeConector = "";
    private int valorConector = 0;
    
    String[] cadenaDividida= new String[5];
    
    public LecturasController() {
    }

    public T012LecturasFacade getLecturasFacade() {
        return lecturasFacade;
    }

    public void setLecturasFacade(T012LecturasFacade lecturasFacade) {
        this.lecturasFacade = lecturasFacade;
    }

    public T012Lecturas getLecturasActual() {
        return lecturasActual;
    }

    public void setLecturasActual(T012Lecturas lecturasActual) {
        this.lecturasActual = lecturasActual;
    }
    
    public List<T012Lecturas> getListaLecturas() {
        return listaLecturas = getLecturasFacade().findAll();
    }

    public List<T012Lecturas> getListaLecturasSinEnviar() {
            return listaLecturas;
    }
    
   public String refrescar() {
        listaLecturas = getLecturasFacade().itemsSinEnviarSp();
        return "crearCodigoBarra";
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public List<String> getTotal1() {
        return total1;
    }

    public void setTotal1(List<String> total1) {
        this.total1 = total1;
    }

    public List<String> getTotal2() {
        return total2;
    }

    public void setTotal2(List<String> total2) {
        this.total2 = total2;
    }

    public List<String> getTotal3() {
        return total3;
    }

    public void setTotal3(List<String> total3) {
        this.total3 = total3;
    }

    public List<String> getTotal4() {
        return total4;
    }

    public void setTotal4(List<String> total4) {
        this.total4 = total4;
    }

    public List<String> getTotal5() {
        return total5;
    }

    public void setTotal5(List<String> total5) {
        this.total5 = total5;
    }

    public List<String> getTotal6() {
        return total6;
    }

    public void setTotal6(List<String> total6) {
        this.total6 = total6;
    }

    public List<String> getTotal7() {
        return total7;
    }

    public void setTotal7(List<String> total7) {
        this.total7 = total7;
    }

    public List<String> getTotal8() {
        return total8;
    }

    public void setTotal8(List<String> total8) {
        this.total8 = total8;
    }

    

    public String[] getCadenaDividida() {
        return cadenaDividida;
    }

    public void setCadenaDividida(String[] cadenaDividida) {
        this.cadenaDividida = cadenaDividida;
    }
    
    
    public List<T012Lecturas> getListaLecturasEnviado() {
        return getLecturasFacade().itemsEstado((short)1);
    }
    public List<T012Lecturas> getListaLecturasOP() {
        return getLecturasFacade().itemsOp(op);
    }
    public List<T012Lecturas> getListaLecturasNoEnviado() {
        return getLecturasFacade().itemsEstado((short)2);
    }
    public void setListaLecturas(List<T012Lecturas> listaLecturas) {
        this.listaLecturas = listaLecturas;
    }

    public T012Lecturas getLecturasActualLista() {
        return lecturasActualLista;
    }

    public void setLecturasActualLista(T012Lecturas lecturasActualLista) {
        this.lecturasActualLista = lecturasActualLista;
    }

    public boolean isAplicarTodo() {
        return aplicarTodo;
    }

    public void setAplicarTodo(boolean aplicarTodo) {
        this.aplicarTodo = aplicarTodo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<T012Lecturas> getListaLecturasOp() {
        return listaLecturasOp;
    }

    public void setListaLecturasOp(List<T012Lecturas> listaLecturasOp) {
        this.listaLecturasOp = listaLecturasOp;
    }
    
    public List<String> getListaOPCargar(){
        return getLecturasFacade().obtenerOPCargar();
    }
    public String consultarOp(){
        listaLecturasOp = getLecturasFacade().itemsOp(op);
        total1 = getLecturasFacade().totalCajasOp(op);
        total2 = getLecturasFacade().totalCajasUnoEEOp(op);
        total3 = getLecturasFacade().totalCajasNoUnoEEOp(op);
        total4 = getLecturasFacade().totalCajasNoUnoEEOp(op);
        total5 = getLecturasFacade().totalUnidadesOp(op);
        total6 = getLecturasFacade().totalUnidadesUnoEEOp(op);
        total7 = getLecturasFacade().totalUnidadesErrorUnoEEOp(op);
        total8 = getLecturasFacade().totalUnidadesNoUnoEEOp(op);
        
        return "logOp";
    }
    public List<String> completeOp(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : getLecturasFacade().listaOp()) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }
    public void divideCadena(){
        getLecturasActual().setF001CodBarraItem(getLecturasActual().getF001IdLectura().substring(0,10));
        getLecturasActual().setF001IdOp(getLecturasActual().getF001IdLectura().substring(11,14));
        getLecturasActual().setF001Lote(getLecturasActual().getF001IdLectura().substring(15,29));
        getLecturasActual().setF001Lote(getLecturasActual().getF001IdLectura().substring(30,34));
        //getLecturasActual().setF001Lote(getLecturasActual().getF001IdLectura().substring(35,36));
    }
    private void recargarLista(){
        op = null;
        total1= null;
        total2= null;
        total3= null;
        total4= null;
        total5= null;
        total6= null;
        total7= null;
        total8= null;
        listaLecturas = new ArrayList<>();
        listaLecturas = getLecturasFacade().itemsSinEnviarSp();
        lecturasActual = new T012Lecturas();
        listaLecturasOp = new ArrayList<>();
    }
    public String add(){
        try {
            if(getLecturasActual().getF001IdLectura().length()==45){
                getLecturasActual().setF001CodBarraItem(getLecturasActual().getF001IdLectura().substring(0,15));
                getLecturasActual().setF001IdOp(getLecturasActual().getF001IdLectura().substring(15,20));
                getLecturasActual().setF001Lote(getLecturasActual().getF001IdLectura().substring(20,35));
                getLecturasActual().setF001Unidad(getLecturasActual().getF001IdLectura().substring(35,40));
                getLecturasActual().setF001Consecutivo(getLecturasActual().getF001IdLectura().substring(40,45));
                getLecturasActual().setF001Fecha(new Date());
                getLecturasActual().setF001Usuario(usuarioActual);
                getLecturasActual().setF001RegistroConector((short)0);
                getLecturasActual().setF001RecargarLista(false);
                getLecturasActual().setF001Observaciones("PENDIENTE POR ENVIAR");
                if(getLecturasFacade().obtenerOPEstadoUnoEE(getLecturasActual().getF001IdOp()).isEmpty()){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al leer codigo de barras", "El docto OP debe estar en estado aprobado o comprometido");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                    return null;
                }
                getLecturasFacade().create(lecturasActual);
                addSuccessMessage("Codigo guardado correctamente", "El codigo fue guardado correctamente");
                recargarLista();
            }else{
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Codigo incompleto", "Codigo incompleto");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                recargarLista();
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Codigo repetido", "Codigo repetido");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
        return "/administrador/modGestorCodigoBarraKos/crearCodigoBarra";
    }
    public void delete() {
        try {   
            getLecturasFacade().remove(lecturasActual);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Código eliminado", "El código fue eliminado correctamente");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } catch (Exception e) {
        }
    }
    public String prepareView(){
        return "detalleCarga";
    }
    public String prepareCreate(){
        recargarLista();
        return "/administrador/modGestorCodigoBarraKos/crearCodigoBarra";
    }
    public String prepareLogOp(){
        recargarLista();
        return "/administrador/modGestorCodigoBarraKos/logOp";
    }
    public void prepareOp(){
        op = null;
    }
    public String addVolverCarga() {
        try {
            if(isAplicarTodo()==true){
                for (T012Lecturas item : getListaLecturasNoEnviado()) {
                    if(estado.equals("r")){
                       item.setF001RegistroConector((short)0);
                    }
                    getLecturasFacade().edit(item);
                }
                addSuccessMessage("Códigos editados", "Los códigos están listos para volver a cargarse");
                recargarLista();
            }else{
                if(estado.equals("r")){
                    lecturasActual.setF001RegistroConector((short)0);
                }  
                getLecturasFacade().edit(lecturasActual);
                addSuccessMessage("Código editado", "El código está listo para volver a cargarse");
                recargarLista();
            }  
            aplicarTodo = false;
            return null;
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
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
     public String enviarCodigoBarrasKos() throws InterruptedException {
        try {
            if(getLecturasFacade().itemsSinEnviarSp().isEmpty()){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datos no procesados", "Los datos no fueron ejecutados correctamente, debido a que la lista se encuentra vacía");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return "crearCodigoBarra";
            }else{
                ejecutarWebService();
                if(valorConector==1){
                    valorConector = 2;
                }else{
                    valorConector = 1;
                }
                for(T012Lecturas item : getLecturasFacade().itemsOpEstado(op,(short) 0)){
                    item.setF001RegistroConector((short)valorConector);
                    item.setF001Observaciones(mensajeConector);
                    getLecturasFacade().edit(item);
                }
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos procesados", "Los datos fueron ejecutados correctamente, verificar en el UnoEE");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                recargarLista();
                return "crearCodigoBarra";
            }
            
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la ejecución", "Los datos no fueron ejecutados correctamente");
             return "crearCodigoBarra";
        }
    } 
    //SOAP WEB SERVICE
    public void ejecutarWebService() throws RemoteException, Exception{
        mensajeConector = "";
        valorConector = 0;
        String pvstrDatos = getLecturasFacade().obtenerConectorCodigoBarraKos(op);
        // TODO Auto-generated method stub
        WSUNOEESoapProxy proxy = new WSUNOEESoapProxy();
        ShortHolder holder = new ShortHolder((short) 0);
        ImportarXMLResponseImportarXMLResult result = proxy.importarXML(pvstrDatos, holder);
        MessageElement[] elements = result.get_any();
        for (MessageElement element : elements) {
            mensajeConector = mensajeConector + element.getAsString();
            valorConector = element.getLength();
        }
    }
    /*WEB SERVICE SISS
    public String ejecutarSissCodigoBarrasKos() throws InterruptedException {
        try {
            if(getLecturasFacade().itemsSinEnviarSp().isEmpty()){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datos no procesados", "Los datos no fueron ejecutados correctamente, debido a que la lista se encuentra vacía");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return "crearCodigoBarra";
            }else{
                getLecturasFacade().spEjecutarSsisCodigoBarrasKos();
                Thread.sleep(35000);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos procesados", "Los datos fueron ejecutados correctamente, verificar en el UnoEE");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                recargarLista();
                return "crearCodigoBarra";
            }
            
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la ejecución", "Los datos no fueron ejecutados correctamente");
             return "crearCodigoBarra";
        }
    } ¨*/
}
