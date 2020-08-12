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
 * @author junio
 */
@Entity
@Table(name = "t006_tipo_documento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T006TipoDocumento.findAll", query = "SELECT t FROM T006TipoDocumento t")
    , @NamedQuery(name = "T006TipoDocumento.findByIdTipoDocumento", query = "SELECT t FROM T006TipoDocumento t WHERE t.idTipoDocumento = :idTipoDocumento")
    , @NamedQuery(name = "T006TipoDocumento.findByNombre", query = "SELECT t FROM T006TipoDocumento t WHERE t.nombre = :nombre")})
public class T006TipoDocumento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "id_tipo_documento")
    private String idTipoDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idTipoDocumento")
    private List<T004Documento> t004DocumentoList;

    public T006TipoDocumento() {
    }

    public T006TipoDocumento(String idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public T006TipoDocumento(String idTipoDocumento, String nombre) {
        this.idTipoDocumento = idTipoDocumento;
        this.nombre = nombre;
    }

    public String getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(String idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<T004Documento> getT004DocumentoList() {
        return t004DocumentoList;
    }

    public void setT004DocumentoList(List<T004Documento> t004DocumentoList) {
        this.t004DocumentoList = t004DocumentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoDocumento != null ? idTipoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T006TipoDocumento)) {
            return false;
        }
        T006TipoDocumento other = (T006TipoDocumento) object;
        if ((this.idTipoDocumento == null && other.idTipoDocumento != null) || (this.idTipoDocumento != null && !this.idTipoDocumento.equals(other.idTipoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getIdTipoDocumento().toUpperCase()+" - "+ getNombre().toUpperCase();
    }
    
}
