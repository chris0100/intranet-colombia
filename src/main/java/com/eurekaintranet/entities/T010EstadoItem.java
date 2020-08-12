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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lccabalr
 */
@Cacheable(false)
@Entity
@Table(name = "t010_estado_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T010EstadoItem.findAll", query = "SELECT t FROM T010EstadoItem t")
    , @NamedQuery(name = "T010EstadoItem.findByIdEstadoItem", query = "SELECT t FROM T010EstadoItem t WHERE t.idEstadoItem = :idEstadoItem")
    , @NamedQuery(name = "T010EstadoItem.findByReferencia", query = "SELECT t FROM T010EstadoItem t WHERE t.referencia = :referencia")
    , @NamedQuery(name = "T010EstadoItem.findByFDescItem", query = "SELECT t FROM T010EstadoItem t WHERE t.fDescItem = :fDescItem")
    , @NamedQuery(name = "T010EstadoItem.findByExtension1", query = "SELECT t FROM T010EstadoItem t WHERE t.extension1 = :extension1")
    , @NamedQuery(name = "T010EstadoItem.findByExtension2", query = "SELECT t FROM T010EstadoItem t WHERE t.extension2 = :extension2")
    , @NamedQuery(name = "T010EstadoItem.findByComprado", query = "SELECT t FROM T010EstadoItem t WHERE t.comprado = :comprado")
    , @NamedQuery(name = "T010EstadoItem.findByMaxCantidadComprada", query = "SELECT t FROM T010EstadoItem t WHERE t.maxCantidadComprada = :maxCantidadComprada")
    , @NamedQuery(name = "T010EstadoItem.findByClientes", query = "SELECT t FROM T010EstadoItem t WHERE t.clientes = :clientes")
    , @NamedQuery(name = "T010EstadoItem.findByRotacion", query = "SELECT t FROM T010EstadoItem t WHERE t.rotacion = :rotacion")
    , @NamedQuery(name = "T010EstadoItem.findByPlan50", query = "SELECT t FROM T010EstadoItem t WHERE t.plan50 = :plan50")
    , @NamedQuery(name = "T010EstadoItem.findByFiltroPlanEstadoCambio", query = "SELECT t FROM T010EstadoItem t WHERE t.plan50 = :plan50 and t.estadoItemCambio = :estadoItemCambio ORDER BY t.participacionSumada,t.rotacion,t.participacion,t.fechaActualizacion")
    , @NamedQuery(name = "T010EstadoItem.findByFiltroPlanEstado", query = "SELECT t FROM T010EstadoItem t WHERE t.plan50 = :plan50 and t.estado = :estado ORDER BY t.participacionSumada,t.rotacion,t.participacion,t.fechaActualizacion")
    , @NamedQuery(name = "T010EstadoItem.findByFiltroPlanEstado2", query = "SELECT t FROM T010EstadoItem t WHERE t.plan50 = :plan50 and t.estado = :estado ORDER BY t.participacionSumada,t.rotacion,t.participacion,t.fechaActualizacion")
    , @NamedQuery(name = "T010EstadoItem.findByFiltroPlanEstadoItem", query = "SELECT t FROM T010EstadoItem t WHERE t.plan50 = :plan50 and t.estado = :estado and t.estadoItem = :estadoItem")
    , @NamedQuery(name = "T010EstadoItem.findByFiltroPlanNoiItem", query = "SELECT t FROM T010EstadoItem t WHERE t.plan50 = :plan50 and t.estado = :estado and t.estadoItem = :estadoItem and t.noi IS NOT NULL")
    , @NamedQuery(name = "T010EstadoItem.findByParticipacion", query = "SELECT t FROM T010EstadoItem t WHERE t.participacion = :participacion")
    , @NamedQuery(name = "T010EstadoItem.findByParticipacionSumada", query = "SELECT t FROM T010EstadoItem t WHERE t.participacionSumada = :participacionSumada")
    , @NamedQuery(name = "T010EstadoItem.findByEstadoItem", query = "SELECT t FROM T010EstadoItem t WHERE t.estadoItem = :estadoItem")
    , @NamedQuery(name = "T010EstadoItem.findByUsuarioActualizacion", query = "SELECT t FROM T010EstadoItem t WHERE t.usuarioActualizacion = :usuarioActualizacion")
    , @NamedQuery(name = "T010EstadoItem.findByNoi", query = "SELECT t FROM T010EstadoItem t WHERE t.noi = :noi")
    , @NamedQuery(name = "T010EstadoItem.findByEvaluado", query = "SELECT t FROM T010EstadoItem t WHERE t.evaluado = :evaluado")
    , @NamedQuery(name = "T010EstadoItem.findBySeguridad", query = "SELECT t FROM T010EstadoItem t WHERE t.seguridad = :seguridad")
    , @NamedQuery(name = "T010EstadoItem.findByEstado", query = "SELECT t FROM T010EstadoItem t WHERE t.estado = :estado")})

@NamedNativeQueries({
	@NamedNativeQuery(
	name = "execGrupoEvaluadoMes",
	query = "select COUNT(*) from t011_grupos_items t011 where exists (select * from t010_estado_item  t010 where t010.plan_50= t011.descripcion and datepart(mm, fecha_actualizacion) = datepart(mm, getdate()))"
	),
        @NamedNativeQuery(
	name = "execFechaTerminacionOp",
	query = "SELECT TOP 1 f851_fecha_terminacion FROM [UnoEE].[dbo].[t851_mf_op_docto_item] t851 inner join [UnoEE].[dbo].t850_mf_op_docto t850 on t850.f850_rowid = t851.f851_rowid_op_docto  inner join [UnoEE].[dbo].v121 on v121.v121_rowid_item_ext = t851.f851_rowid_item_ext_padre inner join [UnoEE].[dbo].t430_cm_pv_docto t430 on  t850.f850_rowid_pv_docto = t430.f430_rowid where V121.v121_referencia = #referencia AND v121.v121_id_ext1_detalle = #ext1 and v121.v121_id_ext2_detalle = #ext2  AND t430.f430_rowid = #rowidPedido and t851.f851_ind_estado = 1 order by f851_fecha_terminacion DESC"
        )
})
@NamedStoredProcedureQuery(
	name = "execEstadoItem", 
	procedureName = "dbo.sp_update_unoee_param_pla", 
	parameters = { 
		@StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "plan50"), 
		@StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "usuario")
	}
)

public class T010EstadoItem implements Serializable {

    @Size(max = 50)
    @Column(name = "estado_unoee")
    private String estadoUnoee;

    @Column(name = "id_item")
    private Integer idItem;
    @Column(name = "id_item_ext")
    private Integer idItemExt;
    @Column(name = "tamano_lote")
    private Double tamanoLote;
    @Column(name = "tasa_produccion_dia")
    private Double tasaProduccionDia;

    @Column(name = "noi")
    private Double noi;

    @Column(name = "evaluado")
    private Short evaluado;
    
    @Column(name = "estado_item_cambio")
    private Short estadoItemCambio;

    @Column(name = "tiempo_reposicion")
    private Integer tiempoReposicion;
    @Column(name = "seguridad")
    private Integer seguridad;
    @Column(name = "estado")
    private Short estado;

    @Size(max = 100)
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_item")
    private Integer idEstadoItem;
    @Size(max = 60)
    @Column(name = "referencia")
    private String referencia;
    @Size(max = 200)
    @Column(name = "f_desc_item")
    private String fDescItem;
    @Size(max = 50)
    @Column(name = "extension_1")
    private String extension1;
    @Size(max = 50)
    @Column(name = "extension_2")
    private String extension2;
    @Column(name = "comprado")
    private Integer comprado;
    @Column(name = "max_cantidad_comprada")
    private Integer maxCantidadComprada;
    @Column(name = "clientes")
    private Integer clientes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rotacion")
    private Double rotacion;
    @Size(max = 200)
    @Column(name = "plan_50")
    private String plan50;
    @Column(name = "participacion")
    private Double participacion;
    @Column(name = "participacion_sumada")
    private Double participacionSumada;
    @Size(max = 10)
    @Column(name = "estado_item")
    private String estadoItem;
    @Size(max = 100)
    @Column(name = "usuario_actualizacion")
    private String usuarioActualizacion;

    public T010EstadoItem() {
    }

    public T010EstadoItem(Integer idEstadoItem) {
        this.idEstadoItem = idEstadoItem;
    }

    public Integer getIdEstadoItem() {
        return idEstadoItem;
    }

    public void setIdEstadoItem(Integer idEstadoItem) {
        this.idEstadoItem = idEstadoItem;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getFDescItem() {
        return fDescItem;
    }

    public void setFDescItem(String fDescItem) {
        this.fDescItem = fDescItem;
    }

    public String getExtension1() {
        return extension1;
    }

    public void setExtension1(String extension1) {
        this.extension1 = extension1;
    }

    public String getExtension2() {
        return extension2;
    }

    public void setExtension2(String extension2) {
        this.extension2 = extension2;
    }

    public Integer getComprado() {
        return comprado;
    }

    public void setComprado(Integer comprado) {
        this.comprado = comprado;
    }

    public Integer getMaxCantidadComprada() {
        return maxCantidadComprada;
    }

    public void setMaxCantidadComprada(Integer maxCantidadComprada) {
        this.maxCantidadComprada = maxCantidadComprada;
    }

    public Integer getClientes() {
        return clientes;
    }

    public void setClientes(Integer clientes) {
        this.clientes = clientes;
    }

    public Double getRotacion() {
        return rotacion;
    }

    public void setRotacion(Double rotacion) {
        this.rotacion = rotacion;
    }

    public String getPlan50() {
        return plan50;
    }

    public void setPlan50(String plan50) {
        this.plan50 = plan50;
    }

    public Double getParticipacion() {
        return participacion;
    }

    public void setParticipacion(Double participacion) {
        this.participacion = participacion;
    }

    public Double getParticipacionSumada() {
        return participacionSumada;
    }

    public void setParticipacionSumada(Double participacionSumada) {
        this.participacionSumada = participacionSumada;
    }

    public String getEstadoItem() {
        return estadoItem;
    }
    public String getEstadoItemCambioString() {
        if(estadoItem.equals("ESTADO 1")){
            return "ESTADO 2";
        }else{
             return "ESTADO 1";
        }
    }

    public void setEstadoItem(String estadoItem) {
        this.estadoItem = estadoItem;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoItem != null ? idEstadoItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T010EstadoItem)) {
            return false;
        }
        T010EstadoItem other = (T010EstadoItem) object;
        if ((this.idEstadoItem == null && other.idEstadoItem != null) || (this.idEstadoItem != null && !this.idEstadoItem.equals(other.idEstadoItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getReferencia();
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getTiempoReposicion() {
        return tiempoReposicion;
    }

    public void setTiempoReposicion(Integer tiempoReposicion) {
        this.tiempoReposicion = tiempoReposicion;
    }


    public Integer getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(Integer seguridad) {
        this.seguridad = seguridad;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public String getEvaluado() {
        switch (evaluado) {
            case 0:
                return "NO EVALUADO";
            case 1:   
                return "PENDIENTE";
            default:
                return "EVALUADO";
        }
    }

    public void setEvaluado(Short evaluado) {
        this.evaluado = evaluado;
    }

    public Double getNoi() {
        return noi;
    }

    public void setNoi(Double noi) {
        this.noi = noi;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public Integer getIdItemExt() {
        return idItemExt;
    }

    public void setIdItemExt(Integer idItemExt) {
        this.idItemExt = idItemExt;
    }

    public Double getTamanoLote() {
        return tamanoLote;
    }

    public void setTamanoLote(Double tamanoLote) {
        this.tamanoLote = tamanoLote;
    }

    public Double getTasaProduccionDia() {
        return tasaProduccionDia;
    }

    public void setTasaProduccionDia(Double tasaProduccionDia) {
        this.tasaProduccionDia = tasaProduccionDia;
    }

    public Short getEstadoItemCambio() {
        return estadoItemCambio;
    }

    public void setEstadoItemCambio(Short estadoItemCambio) {
        this.estadoItemCambio = estadoItemCambio;
    }

    public String getEstadoUnoee() {
        return estadoUnoee;
    }

    public void setEstadoUnoee(String estadoUnoee) {
        this.estadoUnoee = estadoUnoee;
    }
}
