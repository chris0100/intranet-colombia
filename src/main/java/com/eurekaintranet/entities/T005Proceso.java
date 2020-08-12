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
import javax.persistence.NamedNativeQuery;
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
@Table(name = "t005_proceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T005Proceso.findAll", query = "SELECT t FROM T005Proceso t")
    , @NamedQuery(name = "T005Proceso.findByIdProceso", query = "SELECT t FROM T005Proceso t WHERE t.idProceso = :idProceso")
    , @NamedQuery(name = "T005Proceso.findByNombre", query = "SELECT t FROM T005Proceso t WHERE t.nombre = :nombre")})

@NamedNativeQuery(
    name = "execProcesosString",
    query = "SELECT [nombre] FROM [dbo].[t005_proceso] order by nombre"
    )
public class T005Proceso implements Serializable {

    @OneToMany(mappedBy = "proceso")
    private List<T001Usuario> t001UsuarioList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "id_proceso")
    private String idProceso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idProceso")
    private List<T004Documento> t004DocumentoList;

    public T005Proceso() {
    }

    public T005Proceso(String idProceso) {
        this.idProceso = idProceso;
    }

    public T005Proceso(String idProceso, String nombre) {
        this.idProceso = idProceso;
        this.nombre = nombre;
    }

    public String getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
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
        hash += (idProceso != null ? idProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T005Proceso)) {
            return false;
        }
        T005Proceso other = (T005Proceso) object;
        if ((this.idProceso == null && other.idProceso != null) || (this.idProceso != null && !this.idProceso.equals(other.idProceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getIdProceso().toUpperCase()+" - "+ getNombre().toUpperCase();
    }

    @XmlTransient
    public List<T001Usuario> getT001UsuarioList() {
        return t001UsuarioList;
    }

    public void setT001UsuarioList(List<T001Usuario> t001UsuarioList) {
        this.t001UsuarioList = t001UsuarioList;
    }
    
}
