/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lccabalr
 */
@Entity
@Table(name = "t037_detalle_parada_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T037DetalleParadaGe.findAll", query = "SELECT t FROM T037DetalleParadaGe t")
    , @NamedQuery(name = "T037DetalleParadaGe.findByIdDetalleParada", query = "SELECT t FROM T037DetalleParadaGe t WHERE t.idDetalleParada = :idDetalleParada")
    , @NamedQuery(name = "T037DetalleParadaGe.findByHoraInicio", query = "SELECT t FROM T037DetalleParadaGe t WHERE t.horaInicio = :horaInicio")
    , @NamedQuery(name = "T037DetalleParadaGe.findByHoraFin", query = "SELECT t FROM T037DetalleParadaGe t WHERE t.horaFin = :horaFin")
    , @NamedQuery(name = "T037DetalleParadaGe.findByValidarHora", query = "SELECT t FROM T037DetalleParadaGe t WHERE t.horaInicio <:horaFin AND t.horaFin >:horaInicio ")
    , @NamedQuery(name = "T037DetalleParadaGe.findByUsuarioCreacion", query = "SELECT t FROM T037DetalleParadaGe t WHERE t.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "T037DetalleParadaGe.findByFechaCreacion", query = "SELECT t FROM T037DetalleParadaGe t WHERE t.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "T037DetalleParadaGe.findByUsuarioActualizacion", query = "SELECT t FROM T037DetalleParadaGe t WHERE t.usuarioActualizacion = :usuarioActualizacion")
    , @NamedQuery(name = "T037DetalleParadaGe.findByCaptura", query = "SELECT t FROM T037DetalleParadaGe t WHERE t.idCaptura =:idCaptura")
    , @NamedQuery(name = "T037DetalleParadaGe.findByReporteDetalle", query = "SELECT t FROM T037DetalleParadaGe t WHERE t.idCaptura.fechaCaptura BETWEEN :fecha1 AND :fecha2 ORDER BY t.idCaptura.fechaCaptura")
    , @NamedQuery(name = "T037DetalleParadaGe.findByFechaActualizacion", query = "SELECT t FROM T037DetalleParadaGe t WHERE t.fechaActualizacion = :fechaActualizacion")})
public class T037DetalleParadaGe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_parada")
    private Integer idDetalleParada;
    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaInicio;
    @Column(name = "hora_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaFin;
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
    @JoinColumn(name = "id_parada", referencedColumnName = "id_parada")
    @ManyToOne(optional = false)
    private T035ParadasGe idParada;
    @JoinColumn(name = "id_captura", referencedColumnName = "id_captura")
    @ManyToOne(optional = false)
    private T036CapturaParadaGe idCaptura;
    @Column(name = "estado")
    private boolean estado;

    public T037DetalleParadaGe() {
    }

    public T037DetalleParadaGe(Integer idDetalleParada) {
        this.idDetalleParada = idDetalleParada;
    }

    public Integer getIdDetalleParada() {
        return idDetalleParada;
    }

    public void setIdDetalleParada(Integer idDetalleParada) {
        this.idDetalleParada = idDetalleParada;
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

    public T035ParadasGe getIdParada() {
        return idParada;
    }

    public void setIdParada(T035ParadasGe idParada) {
        this.idParada = idParada;
    }

    public T036CapturaParadaGe getIdCaptura() {
        return idCaptura;
    }

    public void setIdCaptura(T036CapturaParadaGe idCaptura) {
        this.idCaptura = idCaptura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleParada != null ? idDetalleParada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T037DetalleParadaGe)) {
            return false;
        }
        T037DetalleParadaGe other = (T037DetalleParadaGe) object;
        if ((this.idDetalleParada == null && other.idDetalleParada != null) || (this.idDetalleParada != null && !this.idDetalleParada.equals(other.idDetalleParada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T037DetalleParadaGe[ idDetalleParada=" + idDetalleParada + " ]";
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
}
