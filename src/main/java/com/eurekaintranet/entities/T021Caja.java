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
@Cacheable(false)
@Entity
@Table(name = "t021_caja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T021Caja.findAll", query = "SELECT t FROM T021Caja t")
    , @NamedQuery(name = "T021Caja.findByIdCaja", query = "SELECT t FROM T021Caja t WHERE t.idCaja = :idCaja")
    , @NamedQuery(name = "T021Caja.findByFechaCreacion", query = "SELECT t FROM T021Caja t WHERE t.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "T021Caja.findByFechaCierre", query = "SELECT t FROM T021Caja t WHERE t.fechaCierre = :fechaCierre")
    , @NamedQuery(name = "T021Caja.findByResponsable", query = "SELECT t FROM T021Caja t WHERE t.responsable = :responsable")
    , @NamedQuery(name = "T021Caja.findByPesoNeto", query = "SELECT t FROM T021Caja t WHERE t.pesoNeto = :pesoNeto")
    , @NamedQuery(name = "T021Caja.findByPesoBruto", query = "SELECT t FROM T021Caja t WHERE t.pesoBruto = :pesoBruto")
    , @NamedQuery(name = "T021Caja.findByNumeroCaja", query = "SELECT t FROM T021Caja t WHERE t.numeroCaja = :numeroCaja")
    , @NamedQuery(name = "T021Caja.findByIdListaEmpaque", query = "SELECT t FROM T021Caja t WHERE t.idListaEmpaque = :idListaEmpaque")
    , @NamedQuery(name = "T021Caja.findByPartidaArancelaria", query = "SELECT t FROM T021Caja t WHERE t.partidaArancelaria = :partidaArancelaria")})
public class T021Caja implements Serializable {

    @Size(max = 300)
    @Column(name = "oc")
    private String oc;

    @Size(max = 300)
    @Column(name = "pedidos")
    private String pedidos;

    @JoinColumn(name = "id_lista_empaque", referencedColumnName = "id_lista_empaque")
    @ManyToOne
    private T020ListaEmpaque idListaEmpaque;

    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private short estado;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_caja")
    private Integer idCaja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "responsable")
    private String responsable;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "peso_neto")
    private BigDecimal pesoNeto;
    @Column(name = "peso_bruto")
    private BigDecimal pesoBruto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_caja")
    private int numeroCaja;
    @Size(max = 50)
    @Column(name = "partida_arancelaria")
    private String partidaArancelaria;
    @JoinColumn(name = "id_dimension", referencedColumnName = "id_dimension")
    @ManyToOne(optional = false)
    private T023DimensionCaja idDimension;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCaja")
    private List<T022ListaEmpaqueHasCaja> t022ListaEmpaqueHasCajaList;

    public T021Caja() {
    }

    public T021Caja(Integer idCaja) {
        this.idCaja = idCaja;
    }

    public T021Caja(Integer idCaja, Date fechaCreacion, String responsable, int numeroCaja) {
        this.idCaja = idCaja;
        this.fechaCreacion = fechaCreacion;
        this.responsable = responsable;
        this.numeroCaja = numeroCaja;
    }

    public Integer getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(Integer idCaja) {
        this.idCaja = idCaja;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public BigDecimal getPesoNeto() {
        return pesoNeto;
    }

    public void setPesoNeto(BigDecimal pesoNeto) {
        this.pesoNeto = pesoNeto;
    }

    public BigDecimal getPesoBruto() {
        return pesoBruto;
    }

    public void setPesoBruto(BigDecimal pesoBruto) {
        this.pesoBruto = pesoBruto;
    }

    public int getNumeroCaja() {
        return numeroCaja;
    }

    public void setNumeroCaja(int numeroCaja) {
        this.numeroCaja = numeroCaja;
    }

    public String getPartidaArancelaria() {
        return partidaArancelaria;
    }

    public void setPartidaArancelaria(String partidaArancelaria) {
        this.partidaArancelaria = partidaArancelaria;
    }

    public T023DimensionCaja getIdDimension() {
        return idDimension;
    }

    public void setIdDimension(T023DimensionCaja idDimension) {
        this.idDimension = idDimension;
    }

    @XmlTransient
    public List<T022ListaEmpaqueHasCaja> getT022ListaEmpaqueHasCajaList() {
        return t022ListaEmpaqueHasCajaList;
    }

    public void setT022ListaEmpaqueHasCajaList(List<T022ListaEmpaqueHasCaja> t022ListaEmpaqueHasCajaList) {
        this.t022ListaEmpaqueHasCajaList = t022ListaEmpaqueHasCajaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCaja != null ? idCaja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T021Caja)) {
            return false;
        }
        T021Caja other = (T021Caja) object;
        if ((this.idCaja == null && other.idCaja != null) || (this.idCaja != null && !this.idCaja.equals(other.idCaja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T021Caja[ idCaja=" + idCaja + " ]";
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public T020ListaEmpaque getIdListaEmpaque() {
        return idListaEmpaque;
    }

    public void setIdListaEmpaque(T020ListaEmpaque idListaEmpaque) {
        this.idListaEmpaque = idListaEmpaque;
    }

    public String getPedidos() {
        return pedidos;
    }

    public void setPedidos(String pedidos) {
        this.pedidos = pedidos;
    }

    public String getOc() {
        return oc;
    }

    public void setOc(String oc) {
        this.oc = oc;
    }
    
}
