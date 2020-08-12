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
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Entity
@Table(name = "t034_esquemas_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T034EsquemasGe.findAll", query = "SELECT t FROM T034EsquemasGe t")
    , @NamedQuery(name = "T034EsquemasGe.findByIdEsquema", query = "SELECT t FROM T034EsquemasGe t WHERE t.idEsquema = :idEsquema")
    , @NamedQuery(name = "T034EsquemasGe.findByDescripcion", query = "SELECT t FROM T034EsquemasGe t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "T034EsquemasGe.findByLetra", query = "SELECT t FROM T034EsquemasGe t WHERE t.letra = :letra")})
public class T034EsquemasGe implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_esquema")
    private Integer idEsquema;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "letra")
    private String letra;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEsquema")
    private List<T035ParadasGe> t035ParadasGeList;

    public T034EsquemasGe() {
    }

    public T034EsquemasGe(Integer idEsquema) {
        this.idEsquema = idEsquema;
    }

    public T034EsquemasGe(Integer idEsquema, String descripcion, String letra) {
        this.idEsquema = idEsquema;
        this.descripcion = descripcion;
        this.letra = letra;
    }

    public Integer getIdEsquema() {
        return idEsquema;
    }

    public void setIdEsquema(Integer idEsquema) {
        this.idEsquema = idEsquema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    @XmlTransient
    public List<T035ParadasGe> getT035ParadasGeList() {
        return t035ParadasGeList;
    }

    public void setT035ParadasGeList(List<T035ParadasGe> t035ParadasGeList) {
        this.t035ParadasGeList = t035ParadasGeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEsquema != null ? idEsquema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T034EsquemasGe)) {
            return false;
        }
        T034EsquemasGe other = (T034EsquemasGe) object;
        if ((this.idEsquema == null && other.idEsquema != null) || (this.idEsquema != null && !this.idEsquema.equals(other.idEsquema))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getLetra().toUpperCase()+ " - "+getDescripcion().toUpperCase();
    }

}
