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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "t009_param_estado_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T009ParamEstadoItem.findAll", query = "SELECT t FROM T009ParamEstadoItem t order by t.estado DESC ")
    , @NamedQuery(name = "T009ParamEstadoItem.findByIdParamEstadoItem", query = "SELECT t FROM T009ParamEstadoItem t WHERE t.idParamEstadoItem = :idParamEstadoItem and t.estado = :estado")
    , @NamedQuery(name = "T009ParamEstadoItem.findByFechaInicial", query = "SELECT t FROM T009ParamEstadoItem t WHERE t.fechaInicial = :fechaInicial")
    , @NamedQuery(name = "T009ParamEstadoItem.findByFechaFinal", query = "SELECT t FROM T009ParamEstadoItem t WHERE t.fechaFinal = :fechaFinal")
    , @NamedQuery(name = "T009ParamEstadoItem.findByCantidadClientes", query = "SELECT t FROM T009ParamEstadoItem t WHERE t.cantidadClientes = :cantidadClientes")
    , @NamedQuery(name = "T009ParamEstadoItem.findByRotacion", query = "SELECT t FROM T009ParamEstadoItem t WHERE t.rotacion = :rotacion")
    , @NamedQuery(name = "T009ParamEstadoItem.findByDiasHabiles", query = "SELECT t FROM T009ParamEstadoItem t WHERE t.diasHabiles = :diasHabiles")
    , @NamedQuery(name = "T009ParamEstadoItem.findByUsuario", query = "SELECT t FROM T009ParamEstadoItem t WHERE t.usuario = :usuario")
    , @NamedQuery(name = "T009ParamEstadoItem.findByFechaModificacion", query = "SELECT t FROM T009ParamEstadoItem t WHERE t.fechaModificacion = :fechaModificacion")
    , @NamedQuery(name = "T009ParamEstadoItem.findByEstado", query = "SELECT t FROM T009ParamEstadoItem t WHERE t.estado = :estado")})
public class T009ParamEstadoItem implements Serializable {

    @Size(max = 50)
    @Column(name = "nombre_parametro")
    private String nombreParametro;

    @Size(max = 200)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idParamEstadoItem")
    private List<T011GruposItems> t011GruposItemsList;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "partSumada")
    private BigDecimal partSumada;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaInicial")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicial;
    @Column(name = "fechaFinal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_param_estado_item")
    private Integer idParamEstadoItem;
    @Column(name = "cantidadClientes")
    private Short cantidadClientes;
    @Column(name = "rotacion")
    private Short rotacion;
    @Column(name = "diasHabiles")
    private Integer diasHabiles;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
   
    @Size(max = 50)
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "fechaModificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Size(max = 50)
    @Column(name = "estado")
    private String estado;

    public T009ParamEstadoItem() {
    }

    public T009ParamEstadoItem(Integer idParamEstadoItem) {
        this.idParamEstadoItem = idParamEstadoItem;
    }

    public Integer getIdParamEstadoItem() {
        return idParamEstadoItem;
    }

    public void setIdParamEstadoItem(Integer idParamEstadoItem) {
        this.idParamEstadoItem = idParamEstadoItem;
    }


    public Short getCantidadClientes() {
        return cantidadClientes;
    }

    public void setCantidadClientes(Short cantidadClientes) {
        this.cantidadClientes = cantidadClientes;
    }

    public Short getRotacion() {
        return rotacion;
    }

    public void setRotacion(Short rotacion) {
        this.rotacion = rotacion;
    }

    public Integer getDiasHabiles() {
        return diasHabiles;
    }

    public void setDiasHabiles(Integer diasHabiles) {
        this.diasHabiles = diasHabiles;
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParamEstadoItem != null ? idParamEstadoItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T009ParamEstadoItem)) {
            return false;
        }
        T009ParamEstadoItem other = (T009ParamEstadoItem) object;
        if ((this.idParamEstadoItem == null && other.idParamEstadoItem != null) || (this.idParamEstadoItem != null && !this.idParamEstadoItem.equals(other.idParamEstadoItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombreParametro();
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public BigDecimal getPartSumada() {
        return partSumada;
    }

    public void setPartSumada(BigDecimal partSumada) {
        this.partSumada = partSumada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<T011GruposItems> getT011GruposItemsList() {
        return t011GruposItemsList;
    }

    public void setT011GruposItemsList(List<T011GruposItems> t011GruposItemsList) {
        this.t011GruposItemsList = t011GruposItemsList;
    }

    public String getNombreParametro() {
        return nombreParametro;
    }

    public void setNombreParametro(String nombreParametro) {
        this.nombreParametro = nombreParametro;
    }
}
