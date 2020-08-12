/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author Lccabalr
 */
@Entity
@Table(name = "t025_centro_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T025CentroTrabajo.findAll", query = "SELECT t FROM T025CentroTrabajo t")
    , @NamedQuery(name = "T025CentroTrabajo.findByIdCentroTrabajo", query = "SELECT t FROM T025CentroTrabajo t WHERE t.idCentroTrabajo = :idCentroTrabajo")
    , @NamedQuery(name = "T025CentroTrabajo.findByNombre", query = "SELECT t FROM T025CentroTrabajo t WHERE t.nombre = :nombre")})
public class T025CentroTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_centro_trabajo")
    private String idCentroTrabajo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCentroTrabajo")
    private List<T029ProgramadorGantt> t029ProgramadorGanttList;

    public T025CentroTrabajo() {
    }

    public T025CentroTrabajo(String idCentroTrabajo) {
        this.idCentroTrabajo = idCentroTrabajo;
    }

    public T025CentroTrabajo(String idCentroTrabajo, String nombre) {
        this.idCentroTrabajo = idCentroTrabajo;
        this.nombre = nombre;
    }

    public String getIdCentroTrabajo() {
        return idCentroTrabajo;
    }

    public void setIdCentroTrabajo(String idCentroTrabajo) {
        this.idCentroTrabajo = idCentroTrabajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<T029ProgramadorGantt> getT029ProgramadorGanttList() {
        return t029ProgramadorGanttList;
    }

    public void setT029ProgramadorGanttList(List<T029ProgramadorGantt> t029ProgramadorGanttList) {
        this.t029ProgramadorGanttList = t029ProgramadorGanttList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCentroTrabajo != null ? idCentroTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T025CentroTrabajo)) {
            return false;
        }
        T025CentroTrabajo other = (T025CentroTrabajo) object;
        if ((this.idCentroTrabajo == null && other.idCentroTrabajo != null) || (this.idCentroTrabajo != null && !this.idCentroTrabajo.equals(other.idCentroTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T025CentroTrabajo[ idCentroTrabajo=" + idCentroTrabajo + " ]";
    }
    
}
