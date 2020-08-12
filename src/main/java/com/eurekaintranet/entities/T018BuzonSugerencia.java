/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
 * @author Lccabalr
 */
@Entity
@Table(name = "t018_buzon_sugerencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T018BuzonSugerencia.findAll", query = "SELECT t FROM T018BuzonSugerencia t order by t.fechaCreacion DESC")
    , @NamedQuery(name = "T018BuzonSugerencia.findByIdBuzonSugerencia", query = "SELECT t FROM T018BuzonSugerencia t WHERE t.idBuzonSugerencia = :idBuzonSugerencia")
    , @NamedQuery(name = "T018BuzonSugerencia.findByNombreUsuario", query = "SELECT t FROM T018BuzonSugerencia t WHERE t.nombreUsuario = :nombreUsuario")
    , @NamedQuery(name = "T018BuzonSugerencia.findByArea", query = "SELECT t FROM T018BuzonSugerencia t WHERE t.area = :area")
    , @NamedQuery(name = "T018BuzonSugerencia.findByIdTipoSugerencia", query = "SELECT t FROM T018BuzonSugerencia t WHERE t.idTipoSugerencia.idTipoSugerencia = :idTipoSugerencia order by t.fechaCreacion DESC")
    , @NamedQuery(name = "T018BuzonSugerencia.findByCalificacion", query = "SELECT t FROM T018BuzonSugerencia t WHERE t.calificacion = :calificacion and t.idTipoSugerencia.idTipoSugerencia = :idSugerencia")})

@NamedNativeQueries({
	@NamedNativeQuery(
	name = "execTotalCalificacion",
	query = "select count(*) from dbo.t018_buzon_sugerencia where calificacion = #calificacion and id_tipo_sugerencia = #idTipoSugerencia"
	),
        @NamedNativeQuery(
	name = "execTotalTipoSugerencia",
	query = "select count(*) from dbo.t018_buzon_sugerencia where id_tipo_sugerencia = #idTipoSugerencia"
	)
})




public class T018BuzonSugerencia implements Serializable {

    @Column(name = "estado")
    private Boolean estado;
    @Size(max = 2147483647)
    @Column(name = "seguimiento")
    private String seguimiento;
    @Column(name = "fecha_seguimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSeguimiento;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_buzon_sugerencia")
    private Integer idBuzonSugerencia;
    
    @Size(max = 80)
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Size(max = 50)
    @Column(name = "area")
    private String area;
    @Column(name = "calificacion")
    private Short calificacion;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_tipo_sugerencia", referencedColumnName = "id_tipo_sugerencia")
    @ManyToOne
    private T019TipoSugerencia idTipoSugerencia;

    public T018BuzonSugerencia() {
    }

    public T018BuzonSugerencia(Integer idBuzonSugerencia) {
        this.idBuzonSugerencia = idBuzonSugerencia;
    }

    public Integer getIdBuzonSugerencia() {
        return idBuzonSugerencia;
    }

    public void setIdBuzonSugerencia(Integer idBuzonSugerencia) {
        this.idBuzonSugerencia = idBuzonSugerencia;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Short getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Short calificacion) {
        this.calificacion = calificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public T019TipoSugerencia getIdTipoSugerencia() {
        return idTipoSugerencia;
    }

    public void setIdTipoSugerencia(T019TipoSugerencia idTipoSugerencia) {
        this.idTipoSugerencia = idTipoSugerencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBuzonSugerencia != null ? idBuzonSugerencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T018BuzonSugerencia)) {
            return false;
        }
        T018BuzonSugerencia other = (T018BuzonSugerencia) object;
        if ((this.idBuzonSugerencia == null && other.idBuzonSugerencia != null) || (this.idBuzonSugerencia != null && !this.idBuzonSugerencia.equals(other.idBuzonSugerencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T018BuzonSugerencia[ idBuzonSugerencia=" + idBuzonSugerencia + " ]";
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getEstado() {
        return estado;
    }
    public String getEstadoString(){
        if(estado){
            return "TERMINADO";
        }else{
            return "ABIERTO";
        }
    }
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

    public Date getFechaSeguimiento() {
        return fechaSeguimiento;
    }

    public void setFechaSeguimiento(Date fechaSeguimiento) {
        this.fechaSeguimiento = fechaSeguimiento;
    }
    
}
