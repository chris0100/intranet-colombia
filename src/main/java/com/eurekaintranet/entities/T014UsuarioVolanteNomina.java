/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
@Cacheable(false)
@Entity
@Table(name = "t014_usuario_volante_nomina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T014UsuarioVolanteNomina.findAll", query = "SELECT t FROM T014UsuarioVolanteNomina t")
    , @NamedQuery(name = "T014UsuarioVolanteNomina.findByCodigo", query = "SELECT t FROM T014UsuarioVolanteNomina t WHERE t.codigo = :codigo")
    , @NamedQuery(name = "T014UsuarioVolanteNomina.findByNombre", query = "SELECT t FROM T014UsuarioVolanteNomina t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "T014UsuarioVolanteNomina.findByCedula", query = "SELECT t FROM T014UsuarioVolanteNomina t WHERE t.cedula = :cedula")
    , @NamedQuery(name = "T014UsuarioVolanteNomina.findByCorreo", query = "SELECT t FROM T014UsuarioVolanteNomina t WHERE t.correo = :correo")
    , @NamedQuery(name = "T014UsuarioVolanteNomina.findByUsuarioCreacion", query = "SELECT t FROM T014UsuarioVolanteNomina t WHERE t.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "T014UsuarioVolanteNomina.findByUsuarioLogin", query = "SELECT t FROM T014UsuarioVolanteNomina t WHERE t.cedula = :cedula and t.contrasena = :contrasena")
    , @NamedQuery(name = "T014UsuarioVolanteNomina.findByFechaCreacion", query = "SELECT t FROM T014UsuarioVolanteNomina t WHERE t.fechaCreacion = :fechaCreacion")})

@NamedNativeQueries({   
    @NamedNativeQuery(
    name = "execUsuarioLoginVolante",
    query = "SELECT DISTINCT cedula FROM t014_usuario_volante_nomina where cedula = #cedula and contrasena = #contrasena" 
    )})
public class T014UsuarioVolanteNomina implements Serializable {

    @Size(max = 200)
    @Column(name = "contrasena")
    private String contrasena;
    @Column(name = "primer_acceso")
    private Boolean primerAcceso;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 80)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 20)
    @Column(name = "cedula")
    private String cedula;
    @Size(max = 50)
    @Column(name = "correo")
    private String correo;
    @Size(max = 80)
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @OneToMany(mappedBy = "codigo")
    private List<T015VolanteNomina> t015VolanteNominaList;

    public T014UsuarioVolanteNomina() {
    }

    public T014UsuarioVolanteNomina(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @XmlTransient
    public List<T015VolanteNomina> getT015VolanteNominaList() {
        return t015VolanteNominaList;
    }

    public void setT015VolanteNominaList(List<T015VolanteNomina> t015VolanteNominaList) {
        this.t015VolanteNominaList = t015VolanteNominaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T014UsuarioVolanteNomina)) {
            return false;
        }
        T014UsuarioVolanteNomina other = (T014UsuarioVolanteNomina) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T014UsuarioVolanteNomina[ codigo=" + codigo + " ]";
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Boolean getPrimerAcceso() {
        return primerAcceso;
    }

    public void setPrimerAcceso(Boolean primerAcceso) {
        this.primerAcceso = primerAcceso;
    }
    
}
