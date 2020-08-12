/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T002Rol;
import com.eurekaintranet.sessions.T001UsuarioFacade;
import com.eurekaintranet.sessions.T002RolFacade;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Lccabalr
 */
@Named(value = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    @EJB
    private T001UsuarioFacade usuarioFacade;
    @EJB
    private T002RolFacade rolFacade;
    private List<T002Rol> listaRol = null;
    private T001Usuario usuarioActual;
    private List<T001Usuario> listaUsuarios;
    private boolean permisoCalidad;
    private Boolean tokenActivo = true;
    private Boolean correoEnviado = false;
    private String email;
    private EmailController emailController;
    private Boolean contrasenaBloqueada = false;
     
    public UsuarioController() {
    }

    public T001UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsuarioFacade(T001UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    public T002RolFacade getRolFacade() {
        return rolFacade;
    }

    public void setRolFacade(T002RolFacade rolFacade) {
        this.rolFacade = rolFacade;
    }

    public List<T002Rol> getListaRol() {
        return listaRol;
    }

    public void setListaRol(List<T002Rol> listaRol) {
        this.listaRol = listaRol;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public List<T001Usuario> getListaUsuarios() {
        return listaUsuarios = getUsuarioFacade().findAll();
    }

    public void setListaUsuarios(List<T001Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public boolean isPermisoCalidad() {
        return permisoCalidad;
    }

    public void setPermisoCalidad(boolean permisoCalidad) {
        this.permisoCalidad = permisoCalidad;
    }

    public Boolean getTokenActivo() {
        return tokenActivo;
    }

    public void setTokenActivo(Boolean tokenActivo) {
        this.tokenActivo = tokenActivo;
    }

    public Boolean getCorreoEnviado() {
        return correoEnviado;
    }

    public void setCorreoEnviado(Boolean correoEnviado) {
        this.correoEnviado = correoEnviado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailController getEmailController() {
        return emailController;
    }

    public void setEmailController(EmailController emailController) {
        this.emailController = emailController;
    }

   
    
    private void recargarLista() {
        listaUsuarios = null;
    }

    public String prepareCreate() {
        usuarioActual = new T001Usuario();
        return "crear";
    }
      public void prepareRolCalidad(ActionEvent event){
        usuarioActual = new T001Usuario();
        usuarioActual = (T001Usuario) event.getComponent().getAttributes().get("usuario");
    } 
    public void prepareEditRolCalidad(){
        for (T002Rol items : usuarioActual.getT002RolList()) { 
            if (items.getIdRol().equals("CALIDAD")){
                permisoCalidad = true;
                usuarioActual.getT002RolList().remove(new T002Rol("CALIDAD"));
                break;
            }else{
                permisoCalidad = false;
            }
        }
    }
    public String prepareEdit() {
        listaRol = usuarioActual.getT002RolList();
        return "editar";
    }

    public String prepareView() {
        return "";
    }

    public String prepareList() {
        recargarLista();
        return "/administrador/modGestorUsuarios/lista";
    }

    public String addUsuario() {
        try {
            listaRol.add(new T002Rol("USER"));
            usuarioActual.setT002RolList(listaRol);
            getUsuarioFacade().create(usuarioActual);
            recargarLista();
            addSuccessMessage("Nuevo usuario guardado", "El usuario "+usuarioActual.toString()+ " fue guardado exitosamente");
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public void validarCorreo(FacesContext contex, UIComponent component, Object value)throws ValidatorException{
        T001Usuario usuarioConsulta=getUsuarioFacade().findByCorreoElectronico((String)value);
        if(usuarioConsulta != null){
           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username repetido","El nombre de usuario ya se encuentra registrado"));   
        }else{
            String correo=(String) value;
            usuarioActual.setUsername(correo); 
        }
    }
    public String updateUsuarioCalidad() {
        try {
            if(permisoCalidad == true){
                usuarioActual.getT002RolList().add(new T002Rol("CALIDAD"));
                usuarioActual.setT002RolList(usuarioActual.getT002RolList());
                getUsuarioFacade().edit(usuarioActual);
                addSuccessMessage("Usuario modificado", "Al usuario "+usuarioActual.toString()+ " se le permitirá visualizar documentos de calidad");
            }else if (permisoCalidad == false){
                usuarioActual.getT002RolList().remove(new T002Rol("CALIDAD"));
                usuarioActual.setT002RolList(usuarioActual.getT002RolList());
                getUsuarioFacade().edit(usuarioActual);
                addSuccessMessage("Usuario modificado", "Al usuario "+usuarioActual.toString()+ " no se le permitirá visualizar documentos de calidad");
            }
            return "editarPermisoDocumento";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }

    public String updateUsuario() {
        try {
            usuarioActual.setT002RolList(listaRol);
            listaRol.add(new T002Rol("USER"));
            usuarioActual.setT002RolList(listaRol);
            getUsuarioFacade().edit(usuarioActual);
            recargarLista();
            addSuccessMessage("Usuario editado", "los datos del usuario "+usuarioActual.toString()+ " fueron actualizados exitosamente");
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String updateUsuarioPrimerLogin() {
        try {
            usuarioActual.setPrimerLogin(false);
            getUsuarioFacade().edit(usuarioActual);
            addSuccessMessage("Usuario editado", "los datos del usuario "+usuarioActual.toString()+ " fueron actualizados exitosamente");
            return "lista";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public String prepareEditContrasena(){
        return "editarContrasena";
    }
    public String prepareEditUsername(){
        return "editarUsername";
    }

    public String deleteUsuario() {
        try {
            getUsuarioFacade().remove(usuarioActual);
            recargarLista();
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
        return "List";
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

    public T001Usuario getUsuario(java.lang.Integer id) {
        return getUsuarioFacade().find(id);
    }

    @FacesConverter(forClass = T001Usuario.class, value="usuarioConverter")
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
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
            if (object instanceof T001Usuario) {
                T001Usuario o = (T001Usuario) object;
                return getStringKey(o.getIdUsuario());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T001Usuario.class.getName());
            }
        }
    }
    //RECUPERAR CONTRASEÑA
    public String updateUsuarioContrasenaRestablecida() {
        try {
            usuarioActual.setToken(null);
            usuarioActual.setEstadoToken(null);
            usuarioActual.setHoraToken(null);
            email = "";
            getUsuarioFacade().edit(usuarioActual);
            recargarLista();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña restablecida", "Su contraseña fue restablecida de forma exitosa.");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return "index";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    public void generarToken() throws FileNotFoundException{
        usuarioActual = getUsuarioFacade().findByCorreoRecuperarContra(email);
         if(usuarioActual != null){
            String token = UUID.randomUUID().toString().toUpperCase();
            usuarioActual.setToken(token);
            usuarioActual.setHoraToken(new Date());
            usuarioActual.setEstadoToken(true);
            usuarioFacade.edit(usuarioActual);
            sendMailRecuperarContrasena();
            correoEnviado = true;
            limpiarCampos();
        }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No hay resultados de búsqueda", "Ingresa tu correo electrónico para buscar tu cuenta.");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }  
    public void procesarToken() throws IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String actionToken = request.getParameter("id");
        try {
            usuarioActual = getUsuarioFacade().findByToken(actionToken);
            Date horaActual = new Date();
            int horaToken = Integer.parseInt(String.valueOf(usuarioActual.getHoraToken().getHours())+String.valueOf(usuarioActual.getHoraToken().getMinutes()));
            int hora = Integer.parseInt(String.valueOf(horaActual.getHours())+horaActual.getMinutes());
            int diferencia = hora - horaToken;
            if(usuarioActual!=null && usuarioActual.getEstadoToken()==true && diferencia <= 60){
                tokenActivo = true;
                System.out.println("usuario actual: "+usuarioActual.getCorreo());
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/faces/cambiarPassword.xhtml");
            }else{
                usuarioActual = null;
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/faces/recuperarPassword.xhtml");
                tokenActivo = true;
                correoEnviado = false;
            }
        } catch (Exception e) {
            usuarioActual = null;
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/faces/recuperarPassword.xhtml");
            tokenActivo = true;
            correoEnviado = false;
        }
    }
    public boolean isContraBloqueada(){
         return contrasenaBloqueada;
    }
    public String prepareRecuperarPassword(){
         email = "";
         tokenActivo = false;
         correoEnviado = false;
         limpiarCampos();
         return "recuperarPassword";
     }
    public String prepareRestablecerPasswordBloqueada(boolean contraBloaqueada){
         contrasenaBloqueada = contraBloaqueada;
         return "recuperarPassword";
     }
    //RECUPERAR CONTRASEÑA 
    public void sendMailRecuperarContrasena() throws FileNotFoundException{
        emailController = new EmailController();
        String asunto = "Solicitud de restablecimiento de contraseña de EUREKA";
        String cuerpo = "Hola "+usuarioActual.getNombre()+", Si solicitaste un restablecimiento de contraseña, haz clic en el enlace que aparece a continuación. Si no solicitaste esto, ignora este correo electrónico."
                + "\n"
                + "http://eureka:8080/faces/procesarToken.xhtml?id="+usuarioActual.getToken()
                + "\n \n \n";
       emailController.sendMail(usuarioActual.getCorreo(), asunto, cuerpo);  
    }
    public void limpiarCampos(){
         email = "";
         contrasenaBloqueada = false;
     }
}
