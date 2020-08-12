/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.eurekaintranet.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Entity
@Table(name = "t030_operarios_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T030OperariosGe.findAll", query = "SELECT t FROM T030OperariosGe t")
    , @NamedQuery(name = "T030OperariosGe.findByIdOperario", query = "SELECT t FROM T030OperariosGe t WHERE t.idOperario = :idOperario")
    , @NamedQuery(name = "T030OperariosGe.findByCodigo", query = "SELECT t FROM T030OperariosGe t WHERE t.codigo = :codigo")
    , @NamedQuery(name = "T030OperariosGe.findByNombre", query = "SELECT t FROM T030OperariosGe t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "T030OperariosGe.findByApellido", query = "SELECT t FROM T030OperariosGe t WHERE t.apellido = :apellido")
    , @NamedQuery(name = "T030OperariosGe.findByEstado", query = "SELECT t FROM T030OperariosGe t WHERE t.estado = :estado")
    , @NamedQuery(name = "T030OperariosGe.findByUsuarioCreacion", query = "SELECT t FROM T030OperariosGe t WHERE t.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "T030OperariosGe.findByFechaCreacion", query = "SELECT t FROM T030OperariosGe t WHERE t.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "T030OperariosGe.findByUsuarioActualizacion", query = "SELECT t FROM T030OperariosGe t WHERE t.usuarioActualizacion = :usuarioActualizacion")
    , @NamedQuery(name = "T030OperariosGe.findByFechaActualizacion", query = "SELECT t FROM T030OperariosGe t WHERE t.fechaActualizacion = :fechaActualizacion")
    , @NamedQuery(name = "T030OperariosGe.findByEquipoLog", query = "SELECT t FROM T030OperariosGe t WHERE t.equipoLog = :equipoLog")})
public class T030OperariosGe implements Serializable {

    @Size(max = 30)
    @Column(name = "cedula")
    private String cedula;
    @Size(max = 60)
    @Column(name = "tipo_contrato")
    private String tipoContrato;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operario")
    private Integer idOperario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "apellido")
    private String apellido;
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
    @Size(max = 60)
    @Column(name = "equipo_log")
    private String equipoLog;
    @JoinColumn(name = "id_equipo", referencedColumnName = "id_equipo")
    @ManyToOne
    private T039EquipoGe idEquipo;

    public T030OperariosGe() {
    }

    public T030OperariosGe(Integer idOperario) {
        this.idOperario = idOperario;
    }

    public T030OperariosGe(Integer idOperario, String codigo, String nombre, String apellido) {
        this.idOperario = idOperario;
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Integer getIdOperario() {
        return idOperario;
    }

    public void setIdOperario(Integer idOperario) {
        this.idOperario = idOperario;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Boolean getEstado() {
        return estado;
    }
    public int getEstadoColor() {
        if (estado==false){
            return 0;
        }else if(obtenerFechaCreado()<30 && obtenerFechaModificado()>30){
            return 2;
        }else if(obtenerFechaModificado()<30){
            return 1;
        }else{
            return 3;
        }
    }
    public int obtenerFechaModificado(){
        if(fechaActualizacion!=null){
            Date fechaHoy = new Date();
            int dias;
            return dias=(int) ((fechaHoy.getTime()-fechaActualizacion.getTime())/86400000);
        }else{
            return 60;
        }
    }
    public int obtenerFechaCreado(){
        if(fechaCreacion!=null){
            Date fechaHoy = new Date();
            int dias;
            return dias=(int) ((fechaHoy.getTime()-fechaCreacion.getTime())/86400000);
        }else{
            return 60;
        }
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
    public void setEstado(Boolean estado) {
        this.estado = estado;
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

    public String getEquipoLog() {
        return equipoLog;
    }

    public void setEquipoLog(String equipoLog) {
        this.equipoLog = equipoLog;
    }

    public T039EquipoGe getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(T039EquipoGe idEquipo) {
        this.idEquipo = idEquipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperario != null ? idOperario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T030OperariosGe)) {
            return false;
        }
        T030OperariosGe other = (T030OperariosGe) object;
        if ((this.idOperario == null && other.idOperario != null) || (this.idOperario != null && !this.idOperario.equals(other.idOperario))) {
            return false;
        }
        return true;
    }
    

    @Override
    public String toString() {
        return getCodigo().toUpperCase()+ " - "+getNombre().toUpperCase()+ " "+getApellido().toUpperCase();
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

}
