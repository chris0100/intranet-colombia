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
@Table(name = "t044_proceso_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T044ProcesoGe.findAll", query = "SELECT t FROM T044ProcesoGe t")
    , @NamedQuery(name = "T044ProcesoGe.findByIdProceso", query = "SELECT t FROM T044ProcesoGe t WHERE t.idProceso = :idProceso")
    , @NamedQuery(name = "T044ProcesoGe.findByDescripcion", query = "SELECT t FROM T044ProcesoGe t WHERE t.descripcion = :descripcion")})
public class T044ProcesoGe implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion")
    private String descripcion;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proceso")
    private int idProceso;
    @OneToMany(mappedBy = "idProceso")
    private List<T033NominalGe> t033NominalGeList;
    @JoinColumn(name = "id_linea_produccion", referencedColumnName = "id_linea_produccion")
    @ManyToOne
    private T043LineaProduccionGe idLineaProduccion;

    public T044ProcesoGe() {
    }

    

    public int getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(int idProceso) {
        this.idProceso = idProceso;
    }


    @XmlTransient
    public List<T033NominalGe> getT033NominalGeList() {
        return t033NominalGeList;
    }

    public void setT033NominalGeList(List<T033NominalGe> t033NominalGeList) {
        this.t033NominalGeList = t033NominalGeList;
    }

    public T043LineaProduccionGe getIdLineaProduccion() {
        return idLineaProduccion;
    }

    public void setIdLineaProduccion(T043LineaProduccionGe idLineaProduccion) {
        this.idLineaProduccion = idLineaProduccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (descripcion != null ? descripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T044ProcesoGe)) {
            return false;
        }
        T044ProcesoGe other = (T044ProcesoGe) object;
        if ((this.descripcion == null && other.descripcion != null) || (this.descripcion != null && !this.descripcion.equals(other.descripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescripcion().toUpperCase() + " - "+getIdLineaProduccion();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
