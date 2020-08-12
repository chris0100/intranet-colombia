/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;

import java.io.Serializable;
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
@Table(name = "t046_config_orden_despacho")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T046ConfigOrdenDespacho.findAll", query = "SELECT t FROM T046ConfigOrdenDespacho t")
    , @NamedQuery(name = "T046ConfigOrdenDespacho.findByIdConfiguracion", query = "SELECT t FROM T046ConfigOrdenDespacho t WHERE t.idConfiguracion = :idConfiguracion")
    , @NamedQuery(name = "T046ConfigOrdenDespacho.findByValor", query = "SELECT t FROM T046ConfigOrdenDespacho t WHERE t.valor = :valor")
    , @NamedQuery(name = "T046ConfigOrdenDespacho.findByNumerico", query = "SELECT t FROM T046ConfigOrdenDespacho t WHERE t.numerico = :numerico")
    , @NamedQuery(name = "T046ConfigOrdenDespacho.findByTipo", query = "SELECT t FROM T046ConfigOrdenDespacho t WHERE t.tipo = :tipo")})
public class T046ConfigOrdenDespacho implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_configuracion")
    private Integer idConfiguracion;    
    @Size(max = 2000)
    @Column(name = "valor")
    private String valor;
    @Column(name = "numerico")
    private Integer numerico;
    @Size(max = 30)
    @Column(name = "tipo")
    private String tipo;

    public T046ConfigOrdenDespacho() {
    }

    public T046ConfigOrdenDespacho(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public Integer getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getNumerico() {
        return numerico;
    }

    public void setNumerico(Integer numerico) {
        this.numerico = numerico;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfiguracion != null ? idConfiguracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T046ConfigOrdenDespacho)) {
            return false;
        }
        T046ConfigOrdenDespacho other = (T046ConfigOrdenDespacho) object;
        if ((this.idConfiguracion == null && other.idConfiguracion != null) || (this.idConfiguracion != null && !this.idConfiguracion.equals(other.idConfiguracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T046ConfigOrdenDespacho[ idConfiguracion=" + idConfiguracion + " ]";
    }

}
