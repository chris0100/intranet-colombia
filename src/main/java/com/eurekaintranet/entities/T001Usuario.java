/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;

import com.eurekaintranet.controller.util.DigestUtil;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lccabalr
 */
@Entity
@Table(name = "t001_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T001Usuario.findAll", query = "SELECT t FROM T001Usuario t")
    , @NamedQuery(name = "T001Usuario.findByIdUsuario", query = "SELECT t FROM T001Usuario t WHERE t.idUsuario = :idUsuario")
    , @NamedQuery(name = "T001Usuario.findByNombre", query = "SELECT t FROM T001Usuario t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "T001Usuario.findByCorreo", query = "SELECT t FROM T001Usuario t WHERE t.correo = :correo")
    , @NamedQuery(name = "T001Usuario.findByContrasena", query = "SELECT t FROM T001Usuario t WHERE t.contrasena = :contrasena")
    , @NamedQuery(name = "T001Usuario.findByToken", query = "SELECT t FROM T001Usuario t WHERE t.token = :token")
    , @NamedQuery(name = "T001Usuario.findByUsername", query = "SELECT t FROM T001Usuario t WHERE t.username = :username")})
public class T001Usuario implements Serializable {

 

    @OneToMany(mappedBy = "idUsuarioCreacion")
    private List<T020ListaEmpaque> t020ListaEmpaqueList;

    @Size(max = 50)
    @Column(name = "token")
    private String token;
    @Column(name = "estado_token")
    private Boolean estadoToken;
    @Column(name = "hora_token")
    @Temporal(TemporalType.TIME)
    private Date horaToken;

    @OneToMany(mappedBy = "f001Usuario")
    private List<T012Lecturas> t012LecturasList;

    @Column(name = "primer_login")
    private Boolean primerLogin;

    @OneToMany(mappedBy = "idUsuario")
    private List<T007Contenido> t007ContenidoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Size(max = 200)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "correo")
    private String correo;
    @Size(max = 200)
    @Column(name = "contrasena")
    private String contrasena;
    @Size(max = 100)
    @Column(name = "username")
    private String username;
    @JoinTable(name = "usuario_has_roles", joinColumns = {
        @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")}, inverseJoinColumns = {
        @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")})
    @ManyToMany
    private List<T002Rol> t002RolList;
    @JoinColumn(name = "proceso", referencedColumnName = "id_proceso")
    @ManyToOne
    private T005Proceso proceso;

    public T001Usuario() {
    }

    public T001Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        try {
            this.contrasena = DigestUtil.generateDigest(contrasena);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(T001Usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(T001Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlTransient
    public List<T002Rol> getT002RolList() {
        return t002RolList;
    }

    public void setT002RolList(List<T002Rol> t002RolList) {
        this.t002RolList = t002RolList;
    }

    public T005Proceso getProceso() {
        return proceso;
    }

    public void setProceso(T005Proceso proceso) {
        this.proceso = proceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T001Usuario)) {
            return false;
        }
        T001Usuario other = (T001Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre();
    }

    @XmlTransient
    public List<T007Contenido> getT007ContenidoList() {
        return t007ContenidoList;
    }

    public void setT007ContenidoList(List<T007Contenido> t007ContenidoList) {
        this.t007ContenidoList = t007ContenidoList;
    }
    public Boolean getPrimerLogin() {
        return primerLogin;
    }

    public void setPrimerLogin(Boolean primerLogin) {
        this.primerLogin = primerLogin;
    }

    @XmlTransient
    public List<T012Lecturas> getT012LecturasList() {
        return t012LecturasList;
    }

    public void setT012LecturasList(List<T012Lecturas> t012LecturasList) {
        this.t012LecturasList = t012LecturasList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getEstadoToken() {
        return estadoToken;
    }

    public void setEstadoToken(Boolean estadoToken) {
        this.estadoToken = estadoToken;
    }

    public Date getHoraToken() {
        return horaToken;
    }

    public void setHoraToken(Date horaToken) {
        this.horaToken = horaToken;
    }

   

    @XmlTransient
    public List<T020ListaEmpaque> getT020ListaEmpaqueList() {
        return t020ListaEmpaqueList;
    }

    public void setT020ListaEmpaqueList(List<T020ListaEmpaque> t020ListaEmpaqueList) {
        this.t020ListaEmpaqueList = t020ListaEmpaqueList;
    }
    
}
