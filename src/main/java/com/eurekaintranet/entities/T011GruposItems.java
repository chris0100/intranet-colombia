 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
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
@Table(name = "t011_grupos_items")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T011GruposItems.findAll", query = "SELECT t FROM T011GruposItems t")
    , @NamedQuery(name = "T011GruposItems.findByIdGrupo", query = "SELECT t FROM T011GruposItems t WHERE t.idGrupo = :idGrupo")
    , @NamedQuery(name = "T011GruposItems.findByGrupoPendiente", query = "SELECT t FROM T011GruposItems t WHERE t.grupoPendiente = :grupoPendiente")
    , @NamedQuery(name = "T011GruposItems.findByDescripcion", query = "SELECT t FROM T011GruposItems t WHERE t.descripcion = :descripcion")})

@NamedStoredProcedureQuery(
	name = "execEnvioDatos", 
	procedureName = "dbo.sp_envio_datos_comercial", 
	parameters = { 
		@StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "grupo"), 
		@StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "usuario"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaInicial"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "fechaFinal"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "cantidadClientes"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "rotacion"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = BigDecimal.class, name = "PartSumada"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "diasHabiles")
	}
)
public class T011GruposItems implements Serializable {

    @Column(name = "grupo_pendiente")
    private Short grupoPendiente;

    @JoinColumn(name = "id_param_estado_item", referencedColumnName = "id_param_estado_item")
    @ManyToOne
    private T009ParamEstadoItem idParamEstadoItem;

        
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_grupo")
    private Integer idGrupo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion")
    private String descripcion;

    public T011GruposItems() {
    }

    public T011GruposItems(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public T011GruposItems(Integer idGrupo, String descripcion) {
        this.idGrupo = idGrupo;
        this.descripcion = descripcion;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
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
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T011GruposItems)) {
            return false;
        }
        T011GruposItems other = (T011GruposItems) object;
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescripcion();
    }

    public T009ParamEstadoItem getIdParamEstadoItem() {
        return idParamEstadoItem;
    }

    public void setIdParamEstadoItem(T009ParamEstadoItem idParamEstadoItem) {
        this.idParamEstadoItem = idParamEstadoItem;
    }

    public Short getGrupoPendiente() {
        return grupoPendiente;
    }

    public void setGrupoPendiente(Short grupoPendiente) {
        this.grupoPendiente = grupoPendiente;
    }
    
}
