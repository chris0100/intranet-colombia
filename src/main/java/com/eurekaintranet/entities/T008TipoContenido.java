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
 * @author Lccabalr
 */
@Entity
@Table(name = "t008_tipo_contenido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T008TipoContenido.findAll", query = "SELECT t FROM T008TipoContenido t")
    , @NamedQuery(name = "T008TipoContenido.findByIdTipo", query = "SELECT t FROM T008TipoContenido t WHERE t.idTipo = :idTipo")
    , @NamedQuery(name = "T008TipoContenido.findByNombre", query = "SELECT t FROM T008TipoContenido t WHERE t.nombre = :nombre")})
public class T008TipoContenido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "id_tipo")
    private String idTipo;
    @Size(max = 200)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "tipo")
    private List<T007Contenido> t007ContenidoList;

    public T008TipoContenido() {
    }

    public T008TipoContenido(String idTipo) {
        this.idTipo = idTipo;
    }

    public String getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<T007Contenido> getT007ContenidoList() {
        return t007ContenidoList;
    }

    public void setT007ContenidoList(List<T007Contenido> t007ContenidoList) {
        this.t007ContenidoList = t007ContenidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipo != null ? idTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T008TipoContenido)) {
            return false;
        }
        T008TipoContenido other = (T008TipoContenido) object;
        if ((this.idTipo == null && other.idTipo != null) || (this.idTipo != null && !this.idTipo.equals(other.idTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre();
    }
    
}
