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
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lccabalr
 */
@Entity
@Table(name = "t033_nominal_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T033NominalGe.findAll", query = "SELECT t FROM T033NominalGe t")
    , @NamedQuery(name = "T033NominalGe.findByIdNominal", query = "SELECT t FROM T033NominalGe t WHERE t.idNominal = :idNominal")
    , @NamedQuery(name = "T033NominalGe.findByCodigoMaquina", query = "SELECT t FROM T033NominalGe t WHERE t.codigoMaquina = :codigoMaquina")
    , @NamedQuery(name = "T033NominalGe.findByNombreMaquina", query = "SELECT t FROM T033NominalGe t WHERE t.nombreMaquina = :nombreMaquina")
    , @NamedQuery(name = "T033NominalGe.findByRpm", query = "SELECT t FROM T033NominalGe t WHERE t.rpm = :rpm")
    , @NamedQuery(name = "T033NominalGe.findByPuntas", query = "SELECT t FROM T033NominalGe t WHERE t.puntas = :puntas")
    , @NamedQuery(name = "T033NominalGe.findByFcCalculo", query = "SELECT t FROM T033NominalGe t WHERE t.fcCalculo = :fcCalculo")
    , @NamedQuery(name = "T033NominalGe.findByFactorConversion", query = "SELECT t FROM T033NominalGe t WHERE t.factorConversion = :factorConversion")
    , @NamedQuery(name = "T033NominalGe.findByEstado", query = "SELECT t FROM T033NominalGe t WHERE t.estado = :estado")})
@NamedNativeQueries({
    @NamedNativeQuery(
    name = "execCodMaquina",
    query = "SELECT [c0800_referencia] FROM [UnoEE].[dbo].[w0800_equipos]"
    ),
    @NamedNativeQuery(
    name = "execDescMaquina",
    query = "SELECT [c0800_descripcion] FROM [UnoEE].[dbo].[w0800_equipos]"
    )
    ,
    @NamedNativeQuery(
    name = "execDescHomologado",
    query = "SELECT [c0834_valor] FROM [UnoEE].[dbo].[w0834_equipos_desc_tecnicas] WHERE [c0834_rowid_campo] = 24"
    )
})
public class T033NominalGe implements Serializable {

    @JoinColumn(name = "id_proceso", referencedColumnName = "id_proceso")
    @ManyToOne
    private T044ProcesoGe idProceso;

    @Size(max = 60)
    @Column(name = "nombre_homologado")
    private String nombreHomologado;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNominal")
    private List<T036CapturaParadaGe> t036CapturaParadaGeList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nominal")
    private Integer idNominal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo_maquina")
    private String codigoMaquina;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_maquina")
    private String nombreMaquina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rpm")
    private int rpm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "puntas")
    private int puntas;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "fc_calculo")
    private BigDecimal fcCalculo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "factor_conversion")
    private BigDecimal factorConversion;
    
    @Column(name = "estado")
    private Boolean estado;
    @Size(max = 100)
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 100)
    @Column(name = "usuario_actualizacion")
    private String usuarioActualizacion;
    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    public T033NominalGe() {
    }

    public T033NominalGe(Integer idNominal) {
        this.idNominal = idNominal;
    }

    public T033NominalGe(Integer idNominal, String codigoMaquina, String nombreMaquina,int rpm, int puntas, BigDecimal fcCalculo, BigDecimal factorConversion) {
        this.idNominal = idNominal;
        this.codigoMaquina = codigoMaquina;
        this.nombreMaquina = nombreMaquina;
        this.rpm = rpm;
        this.puntas = puntas;
        this.fcCalculo = fcCalculo;
        this.factorConversion = factorConversion;
    }

    public Integer getIdNominal() {
        return idNominal;
    }

    public void setIdNominal(Integer idNominal) {
        this.idNominal = idNominal;
    }

    public String getCodigoMaquina() {
        return codigoMaquina;
    }

    public void setCodigoMaquina(String codigoMaquina) {
        this.codigoMaquina = codigoMaquina;
    }

    public String getNombreMaquina() {
        return nombreMaquina;
    }

    public void setNombreMaquina(String nombreMaquina) {
        this.nombreMaquina = nombreMaquina;
    }

    public int getRpm() {
        return rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public int getPuntas() {
        return puntas;
    }

    public void setPuntas(int puntas) {
        this.puntas = puntas;
    }

    public BigDecimal getFcCalculo() {
        return fcCalculo;
    }

    public void setFcCalculo(BigDecimal fcCalculo) {
        this.fcCalculo = fcCalculo;
    }

    public BigDecimal getFactorConversion() {
        return factorConversion;
    }

    public void setFactorConversion(BigDecimal factorConversion) {
        this.factorConversion = factorConversion;
    }

  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNominal != null ? idNominal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T033NominalGe)) {
            return false;
        }
        T033NominalGe other = (T033NominalGe) object;
        if ((this.idNominal == null && other.idNominal != null) || (this.idNominal != null && !this.idNominal.equals(other.idNominal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombreHomologado();
    }

    @XmlTransient
    public List<T036CapturaParadaGe> getT036CapturaParadaGeList() {
        return t036CapturaParadaGeList;
    }

    public void setT036CapturaParadaGeList(List<T036CapturaParadaGe> t036CapturaParadaGeList) {
        this.t036CapturaParadaGeList = t036CapturaParadaGeList;
    }

    public String getNombreHomologado() {
        return nombreHomologado;
    }

    public void setNombreHomologado(String nombreHomologado) {
        this.nombreHomologado = nombreHomologado;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    public String getEstadoString(){
        if(estado!=null){
           if(estado){
                return "SI";
            }else{
                return "NO";
            } 
        }else{
            return "NO";
        }
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

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public T044ProcesoGe getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(T044ProcesoGe idProceso) {
        this.idProceso = idProceso;
    }
    
}
