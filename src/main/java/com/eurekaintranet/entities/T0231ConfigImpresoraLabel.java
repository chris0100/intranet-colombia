/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lccabalr
 */
@Entity
@Table(name = "t0231_config_impresora_label")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T0231ConfigImpresoraLabel.findAll", query = "SELECT t FROM T0231ConfigImpresoraLabel t")
    , @NamedQuery(name = "T0231ConfigImpresoraLabel.findByIdConfigImpresora", query = "SELECT t FROM T0231ConfigImpresoraLabel t WHERE t.idConfigImpresora = :idConfigImpresora")
    , @NamedQuery(name = "T0231ConfigImpresoraLabel.findByNombreImpresora", query = "SELECT t FROM T0231ConfigImpresoraLabel t WHERE t.nombreImpresora = :nombreImpresora")
    , @NamedQuery(name = "T0231ConfigImpresoraLabel.findByDescripcionImpresora", query = "SELECT t FROM T0231ConfigImpresoraLabel t WHERE t.descripcionImpresora = :descripcionImpresora")
    , @NamedQuery(name = "T0231ConfigImpresoraLabel.findByNombreEquipo", query = "SELECT t FROM T0231ConfigImpresoraLabel t WHERE t.nombreEquipo = :nombreEquipo")})
public class T0231ConfigImpresoraLabel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_config_impresora")
    private Integer idConfigImpresora;
    
    @Size(max = 50)
    @Column(name = "nombre_impresora")
    private String nombreImpresora;
    @Size(max = 200)
    @Column(name = "descripcion_impresora")
    private String descripcionImpresora;
    @Size(max = 50)
    @Column(name = "nombre_equipo")
    private String nombreEquipo;

    public T0231ConfigImpresoraLabel() {
    }

    public T0231ConfigImpresoraLabel(Integer idConfigImpresora) {
        this.idConfigImpresora = idConfigImpresora;
    }

    public Integer getIdConfigImpresora() {
        return idConfigImpresora;
    }

    public void setIdConfigImpresora(Integer idConfigImpresora) {
        this.idConfigImpresora = idConfigImpresora;
    }

    public String getNombreImpresora() {
        return nombreImpresora;
    }

    public void setNombreImpresora(String nombreImpresora) {
        this.nombreImpresora = nombreImpresora;
    }

    public String getDescripcionImpresora() {
        return descripcionImpresora;
    }

    public void setDescripcionImpresora(String descripcionImpresora) {
        this.descripcionImpresora = descripcionImpresora;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfigImpresora != null ? idConfigImpresora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T0231ConfigImpresoraLabel)) {
            return false;
        }
        T0231ConfigImpresoraLabel other = (T0231ConfigImpresoraLabel) object;
        if ((this.idConfigImpresora == null && other.idConfigImpresora != null) || (this.idConfigImpresora != null && !this.idConfigImpresora.equals(other.idConfigImpresora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T0231ConfigImpresoraLabel[ idConfigImpresora=" + idConfigImpresora + " ]";
    }
    
}
