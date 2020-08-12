/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "t045_impresion_orden_despacho")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T045ImpresionOrdenDespacho.findAll", query = "SELECT t FROM T045ImpresionOrdenDespacho t")
    , @NamedQuery(name = "T045ImpresionOrdenDespacho.findByIdImpresion", query = "SELECT t FROM T045ImpresionOrdenDespacho t WHERE t.idImpresion = :idImpresion")
    , @NamedQuery(name = "T045ImpresionOrdenDespacho.findByIdMovimiento", query = "SELECT t FROM T045ImpresionOrdenDespacho t WHERE t.idMovimiento = :idMovimiento")
    , @NamedQuery(name = "T045ImpresionOrdenDespacho.findByCantComp", query = "SELECT t FROM T045ImpresionOrdenDespacho t WHERE t.cantComp = :cantComp")
    , @NamedQuery(name = "T045ImpresionOrdenDespacho.findByCantCompParcial", query = "SELECT t FROM T045ImpresionOrdenDespacho t WHERE t.cantCompParcial = :cantCompParcial")
    , @NamedQuery(name = "T045ImpresionOrdenDespacho.findByEstado", query = "SELECT t FROM T045ImpresionOrdenDespacho t WHERE t.estado = :estado")})

@NamedNativeQueries({
    @NamedNativeQuery(
    name = "execObtenerUltimaImpresion",
    query = "SELECT TOP 1 * FROM t045_impresion_orden_despacho WHERE id_movimiento = #idMovimiento ORDER BY id_impresion ASC" ,
    resultClass = T045ImpresionOrdenDespacho.class
   )      
})
public class T045ImpresionOrdenDespacho implements Serializable {

    @Column(name = "fecha_impresion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaImpresion;
    @Size(max = 10)
    @Column(name = "usuario")
    private String usuario;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_impresion")
    private Integer idImpresion;
    @Column(name = "id_movimiento")
    private Integer idMovimiento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cant_comp")
    private BigDecimal cantComp;
    @Column(name = "cant_comp_parcial")
    private BigDecimal cantCompParcial;
    @Column(name = "estado")
    private String estado;

    public T045ImpresionOrdenDespacho() {
    }

    public T045ImpresionOrdenDespacho(Integer idImpresion) {
        this.idImpresion = idImpresion;
    }

    public Integer getIdImpresion() {
        return idImpresion;
    }

    public void setIdImpresion(Integer idImpresion) {
        this.idImpresion = idImpresion;
    }

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public BigDecimal getCantComp() {
        return cantComp;
    }

    public void setCantComp(BigDecimal cantComp) {
        this.cantComp = cantComp;
    }

    public BigDecimal getCantCompParcial() {
        return cantCompParcial;
    }

    public void setCantCompParcial(BigDecimal cantCompParcial) {
        this.cantCompParcial = cantCompParcial;
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
        hash += (idImpresion != null ? idImpresion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T045ImpresionOrdenDespacho)) {
            return false;
        }
        T045ImpresionOrdenDespacho other = (T045ImpresionOrdenDespacho) object;
        if ((this.idImpresion == null && other.idImpresion != null) || (this.idImpresion != null && !this.idImpresion.equals(other.idImpresion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T045ImpresionOrdenDespacho[ idImpresion=" + idImpresion + " ]";
    }

    public Date getFechaImpresion() {
        return fechaImpresion;
    }

    public void setFechaImpresion(Date fechaImpresion) {
        this.fechaImpresion = fechaImpresion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
}
