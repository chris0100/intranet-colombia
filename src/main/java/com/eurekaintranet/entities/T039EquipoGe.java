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
@Table(name = "t039_equipo_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T039EquipoGe.findAll", query = "SELECT t FROM T039EquipoGe t")
    , @NamedQuery(name = "T039EquipoGe.findByIdEquipo", query = "SELECT t FROM T039EquipoGe t WHERE t.idEquipo = :idEquipo")
    , @NamedQuery(name = "T039EquipoGe.findByDescripcion", query = "SELECT t FROM T039EquipoGe t WHERE t.descripcion = :descripcion")})
public class T039EquipoGe implements Serializable {

    @OneToMany(mappedBy = "idEquipo")
    private List<T043LineaProduccionGe> t043LineaProduccionGeList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    private Integer idEquipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "idEquipo")
    private List<T030OperariosGe> t030OperariosGeList;

    public T039EquipoGe() {
    }

    public T039EquipoGe(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public T039EquipoGe(Integer idEquipo, String descripcion) {
        this.idEquipo = idEquipo;
        this.descripcion = descripcion;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<T030OperariosGe> getT030OperariosGeList() {
        return t030OperariosGeList;
    }

    public void setT030OperariosGeList(List<T030OperariosGe> t030OperariosGeList) {
        this.t030OperariosGeList = t030OperariosGeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEquipo != null ? idEquipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T039EquipoGe)) {
            return false;
        }
        T039EquipoGe other = (T039EquipoGe) object;
        if ((this.idEquipo == null && other.idEquipo != null) || (this.idEquipo != null && !this.idEquipo.equals(other.idEquipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescripcion();
    }

    @XmlTransient
    public List<T043LineaProduccionGe> getT043LineaProduccionGeList() {
        return t043LineaProduccionGeList;
    }

    public void setT043LineaProduccionGeList(List<T043LineaProduccionGe> t043LineaProduccionGeList) {
        this.t043LineaProduccionGeList = t043LineaProduccionGeList;
    }

}
