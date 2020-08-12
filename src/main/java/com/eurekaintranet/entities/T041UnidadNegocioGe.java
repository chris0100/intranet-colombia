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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "t041_unidad_negocio_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T041UnidadNegocioGe.findAll", query = "SELECT t FROM T041UnidadNegocioGe t")
    , @NamedQuery(name = "T041UnidadNegocioGe.findByIdUnidadNegocio", query = "SELECT t FROM T041UnidadNegocioGe t WHERE t.idUnidadNegocio = :idUnidadNegocio")
    , @NamedQuery(name = "T041UnidadNegocioGe.findByDescripcion", query = "SELECT t FROM T041UnidadNegocioGe t WHERE t.descripcion = :descripcion")})
public class T041UnidadNegocioGe implements Serializable {

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidad_negocio")
    private Integer idUnidadNegocio;
    @Size(max = 40)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_corporacion", referencedColumnName = "id_corporacion")
    @ManyToOne
    private T040CorporacionGe idCorporacion;
    @OneToMany(mappedBy = "idUnidadNegocio")
    private List<T042PlantaGe> t042PlantaGeList;

    public T041UnidadNegocioGe() {
    }

    public T041UnidadNegocioGe(Integer idUnidadNegocio) {
        this.idUnidadNegocio = idUnidadNegocio;
    }

    public Integer getIdUnidadNegocio() {
        return idUnidadNegocio;
    }

    public void setIdUnidadNegocio(Integer idUnidadNegocio) {
        this.idUnidadNegocio = idUnidadNegocio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public T040CorporacionGe getIdCorporacion() {
        return idCorporacion;
    }

    public void setIdCorporacion(T040CorporacionGe idCorporacion) {
        this.idCorporacion = idCorporacion;
    }

    @XmlTransient
    public List<T042PlantaGe> getT042PlantaGeList() {
        return t042PlantaGeList;
    }

    public void setT042PlantaGeList(List<T042PlantaGe> t042PlantaGeList) {
        this.t042PlantaGeList = t042PlantaGeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidadNegocio != null ? idUnidadNegocio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T041UnidadNegocioGe)) {
            return false;
        }
        T041UnidadNegocioGe other = (T041UnidadNegocioGe) object;
        if ((this.idUnidadNegocio == null && other.idUnidadNegocio != null) || (this.idUnidadNegocio != null && !this.idUnidadNegocio.equals(other.idUnidadNegocio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescripcion();
    }

}
