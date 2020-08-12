/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lccabalr
 */
@Cacheable(false)
@Entity
@Table(name = "t015_volante_nomina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T015VolanteNomina.findAll", query = "SELECT t FROM T015VolanteNomina t")
    , @NamedQuery(name = "T015VolanteNomina.findByIdVolanteNomina", query = "SELECT t FROM T015VolanteNomina t WHERE t.idVolanteNomina = :idVolanteNomina")
    , @NamedQuery(name = "T015VolanteNomina.findByVolante", query = "SELECT t FROM T015VolanteNomina t WHERE t.volante = :volante")
    , @NamedQuery(name = "T015VolanteNomina.findByUsuarioCreacion", query = "SELECT t FROM T015VolanteNomina t WHERE t.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "T015VolanteNomina.findByCodigo", query = "SELECT t FROM T015VolanteNomina t WHERE t.codigo = :codigo")
    , @NamedQuery(name = "T015VolanteNomina.findByPeriodo", query = "SELECT t FROM T015VolanteNomina t WHERE t.idPeriodo.idPeriodo = :idPeriodo ORDER BY t.volanteEnviado ASC")
    , @NamedQuery(name = "T015VolanteNomina.findByEstado", query = "SELECT t FROM T015VolanteNomina t WHERE t.volanteEnviado = :volanteEnviado")
    , @NamedQuery(name = "T015VolanteNomina.findByTipoNominaPeriodo", query = "SELECT t FROM T015VolanteNomina t WHERE t.tipoNomina =:tipoNomina and t.idPeriodo.idPeriodo = :idPeriodo")
    , @NamedQuery(name = "T015VolanteNomina.findByFechaCreacion", query = "SELECT t FROM T015VolanteNomina t WHERE t.fechaCreacion = :fechaCreacion")})
@NamedNativeQueries({   
    @NamedNativeQuery(
    name = "execVolantesPeriodo",
    query = "SELECT [id_volante_nomina],[codigo],[id_periodo],[usuario_creacion],[fecha_creacion],[volante] FROM [t015_volante_nomina] WHERE id_periodo = #periodo ORDER BY volante_enviado ASC" ,
    resultClass = T015VolanteNomina.class
    ),
    @NamedNativeQuery(
    name = "execVolantesPorUsuario",
    query = "SELECT [id_volante_nomina],[codigo],[id_periodo],[usuario_creacion],[fecha_creacion],[volante] FROM [t015_volante_nomina] WHERE codigo = #codigo" ,
    resultClass = T015VolanteNomina.class
    )
    ,
    @NamedNativeQuery(
    name = "execVolanteCreado",
    query = "select count(*) from t015_volante_nomina where datepart(mm, fecha_creacion) = datepart(mm,getdate())" 
    )  
    ,@NamedNativeQuery(
    name = "execVolanteEnviado",
    query = "select count(*) from t015_volante_nomina where datepart(mm, fecha_creacion) = datepart(mm,getdate()) and volante_enviado = 1" 
    )  

})

public class T015VolanteNomina implements Serializable {

    @Size(max = 100)
    @Column(name = "tipo_nomina")
    private String tipoNomina;

    @Column(name = "volante_enviado")
    private Boolean volanteEnviado;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_volante_nomina")
    private Integer idVolanteNomina;
    @Size(max = 1073741823)
    @NotNull
    @Column(name = "volante")
    private String volante;
    @Size(max = 80)
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "codigo", referencedColumnName = "codigo")
    @ManyToOne
    private T014UsuarioVolanteNomina codigo;
    @JoinColumn(name = "id_periodo", referencedColumnName = "id_periodo")
    @ManyToOne
    private T016PeriodoVolanteNomina idPeriodo;

    public T015VolanteNomina() {
    }

    public T015VolanteNomina(Integer idVolanteNomina) {
        this.idVolanteNomina = idVolanteNomina;
    }

    public Integer getIdVolanteNomina() {
        return idVolanteNomina;
    }

    public void setIdVolanteNomina(Integer idVolanteNomina) {
        this.idVolanteNomina = idVolanteNomina;
    }

    public String getVolante() {
        return volante;
    }

    public void setVolante(String volante) {
        this.volante = volante;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public T014UsuarioVolanteNomina getCodigo() {
        return codigo;
    }

    public void setCodigo(T014UsuarioVolanteNomina codigo) {
        this.codigo = codigo;
    }

    public T016PeriodoVolanteNomina getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(T016PeriodoVolanteNomina idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVolanteNomina != null ? idVolanteNomina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T015VolanteNomina)) {
            return false;
        }
        T015VolanteNomina other = (T015VolanteNomina) object;
        if ((this.idVolanteNomina == null && other.idVolanteNomina != null) || (this.idVolanteNomina != null && !this.idVolanteNomina.equals(other.idVolanteNomina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T015VolanteNomina[ idVolanteNomina=" + idVolanteNomina + " ]";
    }

    public Boolean getVolanteEnviado() {
        return volanteEnviado;
    }

    public void setVolanteEnviado(Boolean volanteEnviado) {
        this.volanteEnviado = volanteEnviado;
    }
    public boolean isVolanteEnviado() {
        return volanteEnviado;
    }
    public String getColorEnviado(){
        if(isVolanteEnviado()){
            return "SI";
        }else{
            return "NO";
        }  
    }

    public String getTipoNomina() {
        return tipoNomina;
    }

    public void setTipoNomina(String tipoNomina) {
        this.tipoNomina = tipoNomina;
    }
    
}
