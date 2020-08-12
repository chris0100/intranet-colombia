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
@Table(name = "t042_planta_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T042PlantaGe.findAll", query = "SELECT t FROM T042PlantaGe t")
    , @NamedQuery(name = "T042PlantaGe.findByIdPlanta", query = "SELECT t FROM T042PlantaGe t WHERE t.idPlanta = :idPlanta")
    , @NamedQuery(name = "T042PlantaGe.findByDescripcion", query = "SELECT t FROM T042PlantaGe t WHERE t.descripcion = :descripcion")})
public class T042PlantaGe implements Serializable {

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_planta")
    private Integer idPlanta;
    @Size(max = 40)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "idPlanta")
    private List<T043LineaProduccionGe> t043LineaProduccionGeList;
    @JoinColumn(name = "id_unidad_negocio", referencedColumnName = "id_unidad_negocio")
    @ManyToOne
    private T041UnidadNegocioGe idUnidadNegocio;

    public T042PlantaGe() {
    }

    public T042PlantaGe(Integer idPlanta) {
        this.idPlanta = idPlanta;
    }

    public Integer getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(Integer idPlanta) {
        this.idPlanta = idPlanta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<T043LineaProduccionGe> getT043LineaProduccionGeList() {
        return t043LineaProduccionGeList;
    }

    public void setT043LineaProduccionGeList(List<T043LineaProduccionGe> t043LineaProduccionGeList) {
        this.t043LineaProduccionGeList = t043LineaProduccionGeList;
    }

    public T041UnidadNegocioGe getIdUnidadNegocio() {
        return idUnidadNegocio;
    }

    public void setIdUnidadNegocio(T041UnidadNegocioGe idUnidadNegocio) {
        this.idUnidadNegocio = idUnidadNegocio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlanta != null ? idPlanta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T042PlantaGe)) {
            return false;
        }
        T042PlantaGe other = (T042PlantaGe) object;
        if ((this.idPlanta == null && other.idPlanta != null) || (this.idPlanta != null && !this.idPlanta.equals(other.idPlanta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescripcion();
    }

}
