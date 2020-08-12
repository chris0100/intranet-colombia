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
import javax.persistence.NamedNativeQueries;
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
 * @author Lccabalr
 */
@Entity
@Table(name = "t019_tipo_sugerencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T019TipoSugerencia.findAll", query = "SELECT t FROM T019TipoSugerencia t order by t.idTipoSugerencia ASC")
    , @NamedQuery(name = "T019TipoSugerencia.findByIdTipoSugerencia", query = "SELECT t FROM T019TipoSugerencia t WHERE t.idTipoSugerencia = :idTipoSugerencia")
    , @NamedQuery(name = "T019TipoSugerencia.findByNombre", query = "SELECT t FROM T019TipoSugerencia t WHERE t.nombre = :nombre")})
@NamedNativeQueries({
    @NamedNativeQuery(
    name = "execObtenerCorreoEnvio",
    query = "SELECT top 1 [correo_envio] FROM [BD_Eureka_2].[dbo].[t019_tipo_sugerencia] where id_tipo_sugerencia = #tipo"
    )
})
public class T019TipoSugerencia implements Serializable {

    @Size(max = 300)
    @Column(name = "correo_envio")
    private String correoEnvio;

   
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "id_tipo_sugerencia")
    private String idTipoSugerencia;
    
    
    @Size(max = 80)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idTipoSugerencia")
    private List<T018BuzonSugerencia> t018BuzonSugerenciaList;

    public T019TipoSugerencia() {
    }

    public T019TipoSugerencia(String idTipoSugerencia) {
        this.idTipoSugerencia = idTipoSugerencia;
    }

    public String getIdTipoSugerencia() {
        return idTipoSugerencia;
    }

    public void setIdTipoSugerencia(String idTipoSugerencia) {
        this.idTipoSugerencia = idTipoSugerencia;
    }

   

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<T018BuzonSugerencia> getT018BuzonSugerenciaList() {
        return t018BuzonSugerenciaList;
    }

    public void setT018BuzonSugerenciaList(List<T018BuzonSugerencia> t018BuzonSugerenciaList) {
        this.t018BuzonSugerenciaList = t018BuzonSugerenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoSugerencia != null ? idTipoSugerencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T019TipoSugerencia)) {
            return false;
        }
        T019TipoSugerencia other = (T019TipoSugerencia) object;
        if ((this.idTipoSugerencia == null && other.idTipoSugerencia != null) || (this.idTipoSugerencia != null && !this.idTipoSugerencia.equals(other.idTipoSugerencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre().toUpperCase();
    }

    public String getCorreoEnvio() {
        return correoEnvio;
    }

    public void setCorreoEnvio(String correoEnvio) {
        this.correoEnvio = correoEnvio;
    }
    
}
