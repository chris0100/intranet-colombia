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
@Table(name = "t036_captura_parada_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T036CapturaParadaGe.findAll", query = "SELECT t FROM T036CapturaParadaGe t order by t.fechaCreacion desc")
    , @NamedQuery(name = "T036CapturaParadaGe.findByIdCaptura", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.idCaptura = :idCaptura")
    , @NamedQuery(name = "T036CapturaParadaGe.findByFechaCaptura", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.fechaCaptura BETWEEN :fecha1 AND :fecha2 ORDER BY t.fechaCaptura")
    , @NamedQuery(name = "T036CapturaParadaGe.findByOp", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.op = :op")
    , @NamedQuery(name = "T036CapturaParadaGe.findByIdItem", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.idItem = :idItem")
    , @NamedQuery(name = "T036CapturaParadaGe.findByDescripcionItem", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.descripcionItem = :descripcionItem")
    , @NamedQuery(name = "T036CapturaParadaGe.findByHoraInicio", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.horaInicio = :horaInicio")
    , @NamedQuery(name = "T036CapturaParadaGe.findByHoraFinal", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.horaFinal = :horaFinal")
    , @NamedQuery(name = "T036CapturaParadaGe.findByValidarHoraCaptura", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.horaInicio <:horaFinal AND t.horaFinal >:horaInicio")
    , @NamedQuery(name = "T036CapturaParadaGe.findByCantProgramada", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.cantProgramada = :cantProgramada")
    , @NamedQuery(name = "T036CapturaParadaGe.findByCantEntregada", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.cantEntregada = :cantEntregada")
    , @NamedQuery(name = "T036CapturaParadaGe.findByMtrsProducidos", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.mtrsProducidos = :mtrsProducidos")
    , @NamedQuery(name = "T036CapturaParadaGe.findByPuntas", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.puntas = :puntas")
    , @NamedQuery(name = "T036CapturaParadaGe.findByPp", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.pp = :pp")
    , @NamedQuery(name = "T036CapturaParadaGe.findByClaseb", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.claseb = :claseb")
    , @NamedQuery(name = "T036CapturaParadaGe.findByUsuarioCreacion", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "T036CapturaParadaGe.findByFechaCreacion", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "T036CapturaParadaGe.findByUsuarioActualizacion", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.usuarioActualizacion = :usuarioActualizacion")
    , @NamedQuery(name = "T036CapturaParadaGe.findByFechaActualizacion", query = "SELECT t FROM T036CapturaParadaGe t WHERE t.fechaActualizacion = :fechaActualizacion")})
@NamedNativeQueries({
    @NamedNativeQuery(
    name = "execListaOp",
    query = "SELECT  [f850_id_tipo_docto]+'-'+cast([f850_consec_docto] as varchar(100)) FROM [UnoEE].[dbo].[t850_mf_op_docto] where (f850_ind_estado = 0 OR f850_ind_estado = 1 OR f850_ind_estado = 2 OR f850_ind_estado = 3) AND f850_id_tipo_docto = '06'"
    //0-ELABORACION 1-APROBADO 2-COMPROMETIDO 3-CUMPLIDO
    )
})

public class T036CapturaParadaGe implements Serializable {

    @Column(name = "sin_op")
    private Boolean sinOp;
    
    @Size(max = 100)
    @Column(name = "ext1")
    private String ext1;
    @Size(max = 100)
    @Column(name = "ext2")
    private String ext2;
    @Size(max = 200)
    @Column(name = "ext1_detalle")
    private String ext1Detalle;
    @Size(max = 200)
    @Column(name = "ext2_detalle")
    private String ext2Detalle;

    @Size(max = 150)
    @Column(name = "centro_trabajo")
    private String centroTrabajo;
    @Size(max = 150)
    @Column(name = "ruta")
    private String ruta;
    @Column(name = "producto_rechazado")
    private BigDecimal productoRechazado;
    @Column(name = "estado")
    private Boolean estado;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_captura")
    private Integer idCaptura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_captura")
    @Temporal(TemporalType.DATE)
    private Date fechaCaptura;
    @Basic(optional = false)
    
    @Size(min = 1, max = 30)
    @Column(name = "op")
    private String op;
    @Basic(optional = false)
    
    @Column(name = "id_item")
    private int idItem;
    @Basic(optional = false)
    
    @Size(min = 1, max = 300)
    @Column(name = "descripcion_item")
    private String descripcionItem;
    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaInicio;
    @Column(name = "hora_final")
    @Temporal(TemporalType.TIMESTAMP)
    private Date  horaFinal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cant_programada")
    private BigDecimal cantProgramada;
    @Column(name = "cant_entregada")
    private BigDecimal cantEntregada;
    @Column(name = "und_medida")
    private String undMedida;
    @Column(name = "factor_conversion")
    private BigDecimal factorConversion;
    @Column(name = "peso")
    private BigDecimal peso;
    @Column(name = "mtrs_producidos")
    private BigDecimal mtrsProducidos;
    @Column(name = "puntas")
    private int puntas;
    @Column(name = "puntas_produccion")
    private int puntasProduccion;
    @Column(name = "pp")
    private int pp;
    @Column(name = "claseb")
    private BigDecimal claseb;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCaptura")
    private List<T037DetalleParadaGe> t037DetalleParadaGeList;
    @JoinColumn(name = "id_operario", referencedColumnName = "id_operario")
    @ManyToOne(optional = false)
    private T030OperariosGe idOperario;
    @JoinColumn(name = "id_turno", referencedColumnName = "id_turno")
    @ManyToOne(optional = false)
    private T032TurnosGe idTurno;
    @JoinColumn(name = "id_nominal", referencedColumnName = "id_nominal")
    @ManyToOne(optional = false)
    private T033NominalGe idNominal;

    public T036CapturaParadaGe() {
    }

    public T036CapturaParadaGe(Integer idCaptura) {
        this.idCaptura = idCaptura;
    }

    public T036CapturaParadaGe(Integer idCaptura, Date fechaCaptura, String op, int idItem, String descripcionItem) {
        this.idCaptura = idCaptura;
        this.fechaCaptura = fechaCaptura;
        this.op = op;
        this.idItem = idItem;
        this.descripcionItem = descripcionItem;
    }

    public Integer getIdCaptura() {
        return idCaptura;
    }

    public void setIdCaptura(Integer idCaptura) {
        this.idCaptura = idCaptura;
    }

    public Date getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getDescripcionItem() {
        return descripcionItem;
    }

    public void setDescripcionItem(String descripcionItem) {
        this.descripcionItem = descripcionItem;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    public BigDecimal getCantProgramada() {
        return cantProgramada;
    }

    public void setCantProgramada(BigDecimal cantProgramada) {
        this.cantProgramada = cantProgramada;
    }

    public BigDecimal getCantEntregada() {
        return cantEntregada;
    }

    public void setCantEntregada(BigDecimal cantEntregada) {
        this.cantEntregada = cantEntregada;
    }

    public BigDecimal getMtrsProducidos() {
        return mtrsProducidos;
    }

    public void setMtrsProducidos(BigDecimal mtrsProducidos) {
        this.mtrsProducidos = mtrsProducidos;
    }

    public int getPuntas() {
        return puntas;
    }

    public void setPuntas(int puntas) {
        this.puntas = puntas;
    }

    
    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public BigDecimal getClaseb() {
        return claseb;
    }

    public void setClaseb(BigDecimal claseb) {
        this.claseb = claseb;
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

    @XmlTransient
    public List<T037DetalleParadaGe> getT037DetalleParadaGeList() {
        return t037DetalleParadaGeList;
    }

    public void setT037DetalleParadaGeList(List<T037DetalleParadaGe> t037DetalleParadaGeList) {
        this.t037DetalleParadaGeList = t037DetalleParadaGeList;
    }

    public T030OperariosGe getIdOperario() {
        return idOperario;
    }

    public void setIdOperario(T030OperariosGe idOperario) {
        this.idOperario = idOperario;
    }

    public T032TurnosGe getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(T032TurnosGe idTurno) {
        this.idTurno = idTurno;
    }

    public T033NominalGe getIdNominal() {
        return idNominal;
    }

    public void setIdNominal(T033NominalGe idNominal) {
        this.idNominal = idNominal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCaptura != null ? idCaptura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T036CapturaParadaGe)) {
            return false;
        }
        T036CapturaParadaGe other = (T036CapturaParadaGe) object;
        if ((this.idCaptura == null && other.idCaptura != null) || (this.idCaptura != null && !this.idCaptura.equals(other.idCaptura))) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T036CapturaParadaGe[ idCaptura=" + idCaptura + " ]";
    }

    public String getCentroTrabajo() {
        return centroTrabajo;
    }

    public void setCentroTrabajo(String centroTrabajo) {
        this.centroTrabajo = centroTrabajo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public BigDecimal getProductoRechazado() {
        return productoRechazado;
    }

    public void setProductoRechazado(BigDecimal productoRechazado) {
        this.productoRechazado = productoRechazado;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt1Detalle() {
        return ext1Detalle;
    }

    public void setExt1Detalle(String ext1Detalle) {
        this.ext1Detalle = ext1Detalle;
    }

    public String getExt2Detalle() {
        return ext2Detalle;
    }

    public void setExt2Detalle(String ext2Detalle) {
        this.ext2Detalle = ext2Detalle;
    }

    public String getUndMedida() {
        return undMedida;
    }

    public void setUndMedida(String undMedida) {
        this.undMedida = undMedida;
    }

    public int getPuntasProduccion() {
        return puntasProduccion;
    }

    public void setPuntasProduccion(int puntasProduccion) {
        this.puntasProduccion = puntasProduccion;
    }

    public BigDecimal getFactorConversion() {
        return factorConversion;
    }

    public void setFactorConversion(BigDecimal factorConversion) {
        this.factorConversion = factorConversion;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Boolean getSinOp() {
        return sinOp;
    }

    public void setSinOp(Boolean sinOp) {
        this.sinOp = sinOp;
    }
    
}
