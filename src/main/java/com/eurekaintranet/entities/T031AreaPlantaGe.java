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
 * @author Lccabalr
 */
@Entity
@Table(name = "t031_area_planta_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T031AreaPlantaGe.findAll", query = "SELECT t FROM T031AreaPlantaGe t")
    , @NamedQuery(name = "T031AreaPlantaGe.findByIdArea", query = "SELECT t FROM T031AreaPlantaGe t WHERE t.idArea = :idArea")
    , @NamedQuery(name = "T031AreaPlantaGe.findByNombre", query = "SELECT t FROM T031AreaPlantaGe t WHERE t.nombre = :nombre")})
public class T031AreaPlantaGe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_area")
    private Integer idArea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;

    public T031AreaPlantaGe() {
    }

    public T031AreaPlantaGe(Integer idArea) {
        this.idArea = idArea;
    }

    public T031AreaPlantaGe(Integer idArea, String nombre) {
        this.idArea = idArea;
        this.nombre = nombre;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArea != null ? idArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T031AreaPlantaGe)) {
            return false;
        }
        T031AreaPlantaGe other = (T031AreaPlantaGe) object;
        if ((this.idArea == null && other.idArea != null) || (this.idArea != null && !this.idArea.equals(other.idArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre();
    }
    
}
