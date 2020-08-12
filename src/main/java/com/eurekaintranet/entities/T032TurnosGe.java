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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "t032_turnos_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T032TurnosGe.findAll", query = "SELECT t FROM T032TurnosGe t")
    , @NamedQuery(name = "T032TurnosGe.findByIdTurno", query = "SELECT t FROM T032TurnosGe t WHERE t.idTurno = :idTurno")
    , @NamedQuery(name = "T032TurnosGe.findByCodigoTurno", query = "SELECT t FROM T032TurnosGe t WHERE t.codigoTurno = :codigoTurno")
    , @NamedQuery(name = "T032TurnosGe.findByNombre", query = "SELECT t FROM T032TurnosGe t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "T032TurnosGe.findByHoraInicio", query = "SELECT t FROM T032TurnosGe t WHERE t.horaInicio = :horaInicio")
    , @NamedQuery(name = "T032TurnosGe.findByHoraFin", query = "SELECT t FROM T032TurnosGe t WHERE t.horaFin = :horaFin")
    , @NamedQuery(name = "T032TurnosGe.findByEstado", query = "SELECT t FROM T032TurnosGe t WHERE t.estado = :estado")})
public class T032TurnosGe implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTurno")
    private List<T036CapturaParadaGe> t036CapturaParadaGeList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turno")
    private Integer idTurno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo_turno")
    private String codigoTurno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_fin")
    @Temporal(TemporalType.TIME)
    private Date horaFin;
    @Column(name = "estado")
    private Boolean estado;
    @Size(max = 100)
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 100)
    @Column(name = "usuario_actualizacion")
    private String usuarioActualizacion;
    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    public T032TurnosGe() {
    }

    public T032TurnosGe(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public T032TurnosGe(Integer idTurno, String codigoTurno, String nombre, Date horaInicio, Date horaFin) {
        this.idTurno = idTurno;
        this.codigoTurno = codigoTurno;
        this.nombre = nombre;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public String getCodigoTurno() {
        return codigoTurno;
    }

    public void setCodigoTurno(String codigoTurno) {
        this.codigoTurno = codigoTurno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTurno != null ? idTurno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T032TurnosGe)) {
            return false;
        }
        T032TurnosGe other = (T032TurnosGe) object;
        if ((this.idTurno == null && other.idTurno != null) || (this.idTurno != null && !this.idTurno.equals(other.idTurno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getCodigoTurno()+" - "+getNombre()+" - Inicia: "+String.format("%02d",getHoraInicio().getHours())+":"+String.format("%02d",getHoraInicio().getMinutes())+" termina: "+String.format("%02d",getHoraFin().getHours())+":"+String.format("%02d",getHoraFin().getMinutes());
    }

    @XmlTransient
    public List<T036CapturaParadaGe> getT036CapturaParadaGeList() {
        return t036CapturaParadaGeList;
    }

    public void setT036CapturaParadaGeList(List<T036CapturaParadaGe> t036CapturaParadaGeList) {
        this.t036CapturaParadaGeList = t036CapturaParadaGeList;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    public String getEstadoString(){
        if(estado!=null){
           if(estado){
                return "SI";
            }else{
                return "NO";
            } 
        }else{
            return "NO";
        }
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

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
}
