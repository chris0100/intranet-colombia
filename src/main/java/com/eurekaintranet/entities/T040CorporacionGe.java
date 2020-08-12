/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.eurekaintranet.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Entity
@Table(name = "t040_corporacion_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T040CorporacionGe.findAll", query = "SELECT t FROM T040CorporacionGe t")
    , @NamedQuery(name = "T040CorporacionGe.findByIdCorporacion", query = "SELECT t FROM T040CorporacionGe t WHERE t.idCorporacion = :idCorporacion")
    , @NamedQuery(name = "T040CorporacionGe.findByDescripcion", query = "SELECT t FROM T040CorporacionGe t WHERE t.descripcion = :descripcion")})
public class T040CorporacionGe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_corporacion")
    private Integer idCorporacion;
    @Size(max = 40)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "idCorporacion")
    private List<T041UnidadNegocioGe> t041UnidadNegocioGeList;

    public T040CorporacionGe() {
    }

    public T040CorporacionGe(Integer idCorporacion) {
        this.idCorporacion = idCorporacion;
    }

    public Integer getIdCorporacion() {
        return idCorporacion;
    }

    public void setIdCorporacion(Integer idCorporacion) {
        this.idCorporacion = idCorporacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<T041UnidadNegocioGe> getT041UnidadNegocioGeList() {
        return t041UnidadNegocioGeList;
    }

    public void setT041UnidadNegocioGeList(List<T041UnidadNegocioGe> t041UnidadNegocioGeList) {
        this.t041UnidadNegocioGeList = t041UnidadNegocioGeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCorporacion != null ? idCorporacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T040CorporacionGe)) {
            return false;
        }
        T040CorporacionGe other = (T040CorporacionGe) object;
        if ((this.idCorporacion == null && other.idCorporacion != null) || (this.idCorporacion != null && !this.idCorporacion.equals(other.idCorporacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescripcion();
    }

}
