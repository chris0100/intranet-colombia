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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Entity
@Table(name = "t043_linea_produccion_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T043LineaProduccionGe.findAll", query = "SELECT t FROM T043LineaProduccionGe t")
    , @NamedQuery(name = "T043LineaProduccionGe.findByIdLineaProduccion", query = "SELECT t FROM T043LineaProduccionGe t WHERE t.idLineaProduccion = :idLineaProduccion")
    , @NamedQuery(name = "T043LineaProduccionGe.findByDescripcion", query = "SELECT t FROM T043LineaProduccionGe t WHERE t.descripcion = :descripcion")})
public class T043LineaProduccionGe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_linea_produccion")
    private Integer idLineaProduccion;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_equipo", referencedColumnName = "id_equipo")
    @ManyToOne
    private T039EquipoGe idEquipo;
    @JoinColumn(name = "id_planta", referencedColumnName = "id_planta")
    @ManyToOne
    private T042PlantaGe idPlanta;

    public T043LineaProduccionGe() {
    }

    public T043LineaProduccionGe(Integer idLineaProduccion) {
        this.idLineaProduccion = idLineaProduccion;
    }

    public Integer getIdLineaProduccion() {
        return idLineaProduccion;
    }

    public void setIdLineaProduccion(Integer idLineaProduccion) {
        this.idLineaProduccion = idLineaProduccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public T039EquipoGe getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(T039EquipoGe idEquipo) {
        this.idEquipo = idEquipo;
    }

    public T042PlantaGe getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(T042PlantaGe idPlanta) {
        this.idPlanta = idPlanta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLineaProduccion != null ? idLineaProduccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T043LineaProduccionGe)) {
            return false;
        }
        T043LineaProduccionGe other = (T043LineaProduccionGe) object;
        if ((this.idLineaProduccion == null && other.idLineaProduccion != null) || (this.idLineaProduccion != null && !this.idLineaProduccion.equals(other.idLineaProduccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescripcion();
    }

}
