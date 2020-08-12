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
@Table(name = "t017_errores_volante_nomina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T017ErroresVolanteNomina.findAll", query = "SELECT t FROM T017ErroresVolanteNomina t")
    , @NamedQuery(name = "T017ErroresVolanteNomina.findByIdError", query = "SELECT t FROM T017ErroresVolanteNomina t WHERE t.idError = :idError")
    , @NamedQuery(name = "T017ErroresVolanteNomina.findByDescripcion", query = "SELECT t FROM T017ErroresVolanteNomina t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "T017ErroresVolanteNomina.findByUsuarioCreacion", query = "SELECT t FROM T017ErroresVolanteNomina t WHERE t.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "T017ErroresVolanteNomina.findByFechaCreacion", query = "SELECT t FROM T017ErroresVolanteNomina t WHERE t.fechaCreacion = :fechaCreacion")})
public class T017ErroresVolanteNomina implements Serializable {
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_error")
    private Integer idError;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "volante")
    private String volante;
    @Size(max = 300)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 80)
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "id_periodo", referencedColumnName = "id_periodo")
    @ManyToOne
    private T016PeriodoVolanteNomina idPeriodo;

    public T017ErroresVolanteNomina() {
    }

    public T017ErroresVolanteNomina(Integer idError) {
        this.idError = idError;
    }

    public Integer getIdError() {
        return idError;
    }

    public void setIdError(Integer idError) {
        this.idError = idError;
    }

    public String getVolante() {
        return volante;
    }

    public void setVolante(String volante) {
        this.volante = volante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public T016PeriodoVolanteNomina getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(T016PeriodoVolanteNomina idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idError != null ? idError.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T017ErroresVolanteNomina)) {
            return false;
        }
        T017ErroresVolanteNomina other = (T017ErroresVolanteNomina) object;
        if ((this.idError == null && other.idError != null) || (this.idError != null && !this.idError.equals(other.idError))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T017ErroresVolanteNomina[ idError=" + idError + " ]";
    }
    
}
