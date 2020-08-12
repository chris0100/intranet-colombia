/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lccabalr
 */
@Cacheable(false)
@Entity
@Table(name = "t023_dimension_caja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T023DimensionCaja.findAll", query = "SELECT t FROM T023DimensionCaja t")
    , @NamedQuery(name = "T023DimensionCaja.findByIdDimension", query = "SELECT t FROM T023DimensionCaja t WHERE t.idDimension = :idDimension")
    , @NamedQuery(name = "T023DimensionCaja.findByDescripciones", query = "SELECT t FROM T023DimensionCaja t WHERE t.descripciones = :descripciones")})
public class T023DimensionCaja implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "peso_corrugada")
    private BigDecimal pesoCorrugada;

    @Column(name = "alto")
    private Integer alto;
    @Column(name = "largo")
    private Integer largo;
    @Column(name = "ancho")
    private Integer ancho;
    @Size(max = 60)
    @Column(name = "responsable")
    private String responsable;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "peso")
    private BigDecimal peso;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dimension")
    private Integer idDimension;
    @Size(max = 100)
    @Column(name = "descripciones")
    private String descripciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDimension")
    private List<T021Caja> t021CajaList;

    public T023DimensionCaja() {
    }

    public T023DimensionCaja(Integer idDimension) {
        this.idDimension = idDimension;
    }

    public Integer getIdDimension() {
        return idDimension;
    }

    public void setIdDimension(Integer idDimension) {
        this.idDimension = idDimension;
    }

    public String getDescripciones() {
        return descripciones;
    }

    public void setDescripciones(String descripciones) {
        this.descripciones = descripciones;
    }

    @XmlTransient
    public List<T021Caja> getT021CajaList() {
        return t021CajaList;
    }

    public void setT021CajaList(List<T021Caja> t021CajaList) {
        this.t021CajaList = t021CajaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDimension != null ? idDimension.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T023DimensionCaja)) {
            return false;
        }
        T023DimensionCaja other = (T023DimensionCaja) object;
        if ((this.idDimension == null && other.idDimension != null) || (this.idDimension != null && !this.idDimension.equals(other.idDimension))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescripciones()+ " Peso: "+getPeso().setScale(1, BigDecimal.ROUND_HALF_EVEN)+" Kg Corrugada: " +getPesoCorrugada().setScale(1, BigDecimal.ROUND_HALF_EVEN)+ "kg";
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Integer getAlto() {
        return alto;
    }

    public void setAlto(Integer alto) {
        this.alto = alto;
    }

    public Integer getLargo() {
        return largo;
    }

    public void setLargo(Integer largo) {
        this.largo = largo;
    }

    public Integer getAncho() {
        return ancho;
    }

    public void setAncho(Integer ancho) {
        this.ancho = ancho;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public BigDecimal getPesoCorrugada() {
        return pesoCorrugada;
    }

    public void setPesoCorrugada(BigDecimal pesoCorrugada) {
        this.pesoCorrugada = pesoCorrugada;
    }
    
}
