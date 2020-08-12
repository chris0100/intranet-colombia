/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;


import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T002Rol;
import com.eurekaintranet.sessions.T001UsuarioFacade;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named(value = "loginController")
@javax.enterprise.context.SessionScoped
public class LoginController implements Serializable {

    private static final Logger log = Logger.getLogger(LoginController.class.getName());
    private String username;
    private String password;
    private T001Usuario usuario;
    private T001Usuario usuarioActual;
    @EJB
    private T001UsuarioFacade usuarioFacade;

    /*public LoginController() {
     HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }*/

    
    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public T001Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(T001Usuario usuario) {
        this.usuario = usuario;
    }

    
    public String getUsername() {
        return username;
    }

    private T001UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuthenticated() {
        return getRequest().getUserPrincipal() != null;
    }

    public Principal getPrincipal() {
        return getRequest().getUserPrincipal();
    }

    private HttpServletRequest getRequest() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Object request = externalContext.getRequest();
        return request instanceof HttpServletRequest ? (HttpServletRequest) request : null;
    }

    private String getLogueado() {
        return getPrincipal().getName();
    }
    
     public T001Usuario getUserLogueado() {  
        return getUsuarioFacade().findByCorreoElectronico(getLogueado());
    }
     public boolean isGestorContenido() {
        for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("gcon")){
                return true;
            }           
        }
        return false;
    }
     
    public boolean isGestorDocumental() {
        for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("gdoc")){
                return true;
            }           
        }
        return false;
    }
    public boolean isDescargaDoc() {
        for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("desdoc")){
                return true;
            }           
        }
        return false;
    }
     public boolean isPlaneacionToc() {
        for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("partoc")){
                return true;
            }           
        }
        return false;
    }
    public boolean isComercialToc() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("comtoc")){
                return true;
            }           
        }
        return false;
    }
    public boolean isAdmin() {
        for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("admin")){
                return true;
            }           
        }
        return false;
    }
    public boolean isGestorFactura() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("gfac")){
                return true;
            }           
        }
        return false;
    }
    
    public boolean isDescargarFactura() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("desfac")){
                return true;
            }           
        }
        return false;
    }
    
     public boolean isCodigoBarrasKos() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("barkos")){
                return true;
            }           
        }
        return false;
    }
    public boolean isGestorCreacionItemsComercial() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("cci")){
                return true;
            }           
        }
        return false;
    }
    
    public boolean isGestorCreacionItemsIngenieria() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("ici")){
                return true;
            }           
        }
        return false;
    }
    public boolean isGestorBuzonSugerencias() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("bsg")){
                return true;
            }           
        }
        return false;
    }
    public boolean isGestorVolantesNomina() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("vno")){
                return true;
            }           
        }
        return false;
    }
    
    public boolean isGestorLpAdmin() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("lpadmin")){
                return true;
            }           
        }
        return false;
    }
    public boolean isGestorLpCedi() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("lpcedi")){
                return true;
            }           
        }
        return false;
    }
    public boolean isGestorLpCediNacional() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("lpnacio")){
                return true;
            }           
        }
        return false;
    }
    public boolean isGestorLpCediInternacional() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("lpinter")){
                return true;
            }           
        }
        return false;
    }
    public boolean isGestorLpVentas() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("lpventa")){
                return true;
            }           
        }
        return false;
    }
    public boolean isReporteProgramacion() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("rpro")){
                return true;
            }           
        }
        return false;
    }
    public boolean isGestorGanttKos() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("kgantt")){
                return true;
            }           
        }
        return false;
    }
     public boolean isTableroControlKos() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("tckos")){
                return true;
            }           
        }
        return false;
    }
    public boolean isVerGanttKos() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("kvgantt")){
                return true;
            }           
        }
        return false;
    }
    
    public boolean isAdminGe() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("age")){
                return true;
            }           
        }
        return false;
    }
    public boolean isConsultaGe() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("cge")){
                return true;
            }           
        }
        return false;
    }
    public boolean isDigitadorGeTelares() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("dget")){
                return true;
            }           
        }
        return false;
    }
    
    public boolean isAdminOrdenDespacho() {
        for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("gda")){
                return true;
            }           
        }
        return false;
    }
    public boolean isConsultaOrdenDespacho() {
        for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("gdc")){
                return true;
            }           
        }
        return false;
    }
    
    
    
    public boolean isUser() {
         for (T002Rol rol : getUserLogueado().getT002RolList()) { 
            if(rol.getIdRol().equals("user")){
                return true;
            }           
        }
        return false;
    }
    public boolean isUserAdminRol() {
        if (getUserLogueado() == null){
            return false;
        }else{
            for (T002Rol rol : getUserLogueado().getT002RolList()) { 
               if(rol.getIdRol().equals("gcon")){
                   return true;
               }
               if(rol.getIdRol().equals("gdoc")){
                   return true;
               }   
               if(rol.getIdRol().equals("admin")){
                   return true;
               } 
               if(rol.getIdRol().equals("partoc")){
                   return true;
               }   
               if(rol.getIdRol().equals("comtoc")){
                   return true;
               } 
               if(rol.getIdRol().equals("barkos")){
                   return true;
               } 
                if(rol.getIdRol().equals("gfac")){
                   return true;
               } 
                if(rol.getIdRol().equals("desfac")){
                   return true;
               } 
                 if(rol.getIdRol().equals("cci")){
                   return true;
               } 
                if(rol.getIdRol().equals("ici")){
                   return true;
               } 
                if(rol.getIdRol().equals("bsg")){
                   return true;
               }
                if(rol.getIdRol().equals("vno")){
                   return true;
               } 
                if(rol.getIdRol().equals("lpadmin")){
                   return true;
               } 
                if(rol.getIdRol().equals("lpcedi")){
                   return true;
               } 
                if(rol.getIdRol().equals("lpnacio")){
                   return true;
               } 
                if(rol.getIdRol().equals("lpinter")){
                   return true;
               } 
                if(rol.getIdRol().equals("lpventa")){
                   return true;
                } 
                if(rol.getIdRol().equals("rpro")){
                   return true;
                } 
                if(rol.getIdRol().equals("kgantt")){
                   return true;
                } 
                if(rol.getIdRol().equals("kvgantt")){
                   return true;
                }
                if(rol.getIdRol().equals("age")){
                   return true;
                } 
                if(rol.getIdRol().equals("cge")){
                   return true;
                } 
                if(rol.getIdRol().equals("dget")){
                   return true;
                } 
                 if(rol.getIdRol().equals("gda")){
                   return true;
                } 
                if(rol.getIdRol().equals("gdc")){
                   return true;
                } 
                if(rol.getIdRol().equals("tckos")){
                   return true;
                } 
           }
            return false;
        }
    }

    public String login() {
        try {
            //Login via the Servlet Context
            getRequest().login(username, password);
            usuario = getUserLogueado();
            setUsuario(usuario);
            for (T002Rol rol : getUserLogueado().getT002RolList()) { 
                if(rol.getIdRol().equals("gdoc")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                    
                }
                if(rol.getIdRol().equals("gcon")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }  
                if(rol.getIdRol().equals("admin")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                } 
                if(rol.getIdRol().equals("partoc")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }  
                if(rol.getIdRol().equals("comtoc")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                 if(rol.getIdRol().equals("barkos")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                 if(rol.getIdRol().equals("gfac")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                 if(rol.getIdRol().equals("desfac")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                 if(rol.getIdRol().equals("cci")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                 if(rol.getIdRol().equals("bsg")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                 if(rol.getIdRol().equals("ici")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                  if(rol.getIdRol().equals("vno")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                if(rol.getIdRol().equals("lpadmin")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                if(rol.getIdRol().equals("lpcedi")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                if(rol.getIdRol().equals("lpventa")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                if(rol.getIdRol().equals("rpla")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                if(rol.getIdRol().equals("kgantt")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                if(rol.getIdRol().equals("tckos")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                if(rol.getIdRol().equals("kvgantt")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                if(rol.getIdRol().equals("age")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                if(rol.getIdRol().equals("cge")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                if(rol.getIdRol().equals("dget")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
            }
            limpiar();
            /*if (usuario.getEstadoUsuario()== false) {
                 logout();
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Inactivo", null));
              return "/index";
            }*/
            //Redirigir a la página de portada
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
            if (usuario.getPrimerLogin()== true) {
                return "cambiarContrasena";
            }else{
                return null;  
            }
           
        } catch (ServletException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede iniciar sesión"  , "Los datos ingresados no son correctos, por favor intentalo nuevamente"));
            return null;
        }
    }
    public String loginPage() {
        try {
            //Login via the Servlet Context
            getRequest().login(username, password);
            usuario = getUserLogueado();
            limpiar();
            if (usuario.getPrimerLogin()== true) {
               return "cambiarContrasena";
            }
            /*if (usuario.getEstadoUsuario()== false) {
                 logout();
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Inactivo", null));
              return "/index";
            }*/
            //Redirigir a la página de portada
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
           
        } catch (ServletException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede iniciar sesión"  , "Los datos ingresados no son correctos, por favor intentalo nuevamente"));
        }
        return null;
    }
    public String logoutAdmin() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.invalidate();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Haz cerrado sesión correctamente", "Gracias por usar los servicios de Eureka, hasta pronto"));
            return "/index";
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to logout user!", e);
            return "/AccessDenied";
        }
    }

    public String logoutUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.invalidate();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Haz cerrado sesión correctamente", "Gracias por usar los servicios de Eureka, hasta pronto"));
            return "/index";
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to logout user!", e);
            return "/AccessDenied";
        }
    }
    public String loginModCodigoBarraKos() {
        try {
            //Login via the Servlet Context
            getRequest().login(username, password);
            usuario = getUserLogueado();
            setUsuario(usuario);
            for (T002Rol rol : getUserLogueado().getT002RolList()) { 
                if(rol.getIdRol().equals("gdoc")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modGestorCodigoBarraKos/crearCodigoBarra";  
                    }
                    
                }
                if(rol.getIdRol().equals("gcon")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modGestorCodigoBarraKos/crearCodigoBarra";  
                    }
                }  
                if(rol.getIdRol().equals("admin")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modGestorCodigoBarraKos/crearCodigoBarra";  
                    }
                } 
                if(rol.getIdRol().equals("partoc")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modGestorCodigoBarraKos/crearCodigoBarra";  
                    }
                }  
                if(rol.getIdRol().equals("comtoc")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modGestorCodigoBarraKos/crearCodigoBarra";  
                    }
                }
                if(rol.getIdRol().equals("ici")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modGestorCodigoBarraKos/crearCodigoBarra";  
                    }
                }
                if(rol.getIdRol().equals("bsg")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modulos";  
                    }
                }
                if(rol.getIdRol().equals("cci")){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
                    if (usuario.getPrimerLogin()== true) {
                        return "cambiarContrasena";
                    }else{
                        return "/administrador/modGestorCodigoBarraKos/crearCodigoBarra";  
                    }
                }
            }
            limpiar();
            /*if (usuario.getEstadoUsuario()== false) {
                 logout();
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Inactivo", null));
              return "/index";
            }*/
            //Redirigir a la página de portada
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ "  , usuario.toString()));
            if (usuario.getPrimerLogin()== true) {
                return "cambiarContrasena";
            }else{
                return null;  
            }
           
        } catch (ServletException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede iniciar sesión"  , "Los datos ingresados no son correctos, por favor intentalo nuevamente"));
            return null;
        }
    }
    public String updateUsuario() {
        try {
            usuario.setPrimerLogin(false);
            getUsuarioFacade().edit(usuario);
            addSuccessMessage("Usuario editado", "los datos fueron actualizados exitosamente");
            return "index";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }
    
     public String preparePaginaPrincipalCalidad() {
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Acceso Denegado", "Nos encontramos trabajando en este módulo, pronto se encontrara habilitado");
        //RequestContext.getCurrentInstance().showMessageInDialog(message);
        //return "eleccionModulo";
        return "/usuario/modCalidad/paginaPrincipal";
    }
    public String preparePaginaPrincipalSiesa() {
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Acceso Denegado", "Nos encontramos trabajando en este módulo, pronto se encontrara habilitado");
        //RequestContext.getCurrentInstance().showMessageInDialog(message);
        return "/usuario/modDocumentoSiesa/listaDocumentoSiesa";
    }
    public String ayuda(){
        return "/ayuda";
    }
    public String preparePaginaSaludOcupacional(){
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede ingresar a este modulo", "Este modulo se encuentra desactivado por el momento");
        //RequestContext.getCurrentInstance().showMessageInDialog(message);
        return "modSaludOcupacional/paginaPrincipal";
    }
    public String preparePaginaFT(){
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede ingresar a este modulo", "Este modulo se encuentra desactivado por el momento");
        //RequestContext.getCurrentInstance().showMessageInDialog(message);
        return "modFichaTecnica/lista";
    }
    public String preparePaginaAuditoria(){
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede ingresar a este modulo", "Este modulo se encuentra desactivado por el momento");
        //RequestContext.getCurrentInstance().showMessageInDialog(message);
        return "modAuditoria/planAuditoria/lista";
    }
    public String preparePaginaMasHerramientas() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Acceso Denegado", "Modulo no disponible");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        //return "masHerramientas/paginaPrincipalMasHerramientas";
        return null;
    }
  
    
    private void limpiar() {
        username = "";
        password = "";
    }
    
    public String logueado(){
        if(isAuthenticated() ==false){
            return "/index";
        }else{
            if(isUserAdminRol()){
              return "/administrador/modulos";  
            }else{
                return null;
            }
            
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
    
    //correo cambio de contraseña//
  
    public String volverIndex(){
        if(isAuthenticated() ==false){
            return "/index";
        }else{
            return "/usuario/modulos";
        }
    }
     
}
