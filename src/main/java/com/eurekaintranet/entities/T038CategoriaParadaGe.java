/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.eurekaintranet.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author Luis carlos cabal
 */
@Entity
@Table(name = "t038_categoria_parada_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T038CategoriaParadaGe.findAll", query = "SELECT t FROM T038CategoriaParadaGe t")
    , @NamedQuery(name = "T038CategoriaParadaGe.findByIdCategoria", query = "SELECT t FROM T038CategoriaParadaGe t WHERE t.idCategoria = :idCategoria")
    , @NamedQuery(name = "T038CategoriaParadaGe.findByDescripcion", query = "SELECT t FROM T038CategoriaParadaGe t WHERE t.descripcion = :descripcion")})
public class T038CategoriaParadaGe implements Serializable {

    @OneToMany(mappedBy = "idCategoria")
    private List<T035ParadasGe> t035ParadasGeList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;

    public T038CategoriaParadaGe() {
    }

    public T038CategoriaParadaGe(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoria != null ? idCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T038CategoriaParadaGe)) {
            return false;
        }
        T038CategoriaParadaGe other = (T038CategoriaParadaGe) object;
        if ((this.idCategoria == null && other.idCategoria != null) || (this.idCategoria != null && !this.idCategoria.equals(other.idCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescripcion();
    }

    @XmlTransient
    public List<T035ParadasGe> getT035ParadasGeList() {
        return t035ParadasGeList;
    }

    public void setT035ParadasGeList(List<T035ParadasGe> t035ParadasGeList) {
        this.t035ParadasGeList = t035ParadasGeList;
    }

}
