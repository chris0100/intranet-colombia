/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;


import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
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
@Table(name = "t022_lista_empaque_has_caja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T022ListaEmpaqueHasCaja.findAll", query = "SELECT t FROM T022ListaEmpaqueHasCaja t")
    , @NamedQuery(name = "T022ListaEmpaqueHasCaja.findByIdPedido", query = "SELECT t FROM T022ListaEmpaqueHasCaja t WHERE t.idPedido = :idPedido")
    , @NamedQuery(name = "T022ListaEmpaqueHasCaja.findByCodigoItem", query = "SELECT t FROM T022ListaEmpaqueHasCaja t WHERE t.codigoItem = :codigoItem")
    , @NamedQuery(name = "T022ListaEmpaqueHasCaja.findByDescripcion", query = "SELECT t FROM T022ListaEmpaqueHasCaja t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "T022ListaEmpaqueHasCaja.findByIdExt1Detalle", query = "SELECT t FROM T022ListaEmpaqueHasCaja t WHERE t.idExt1Detalle = :idExt1Detalle")
    , @NamedQuery(name = "T022ListaEmpaqueHasCaja.findByIdExt2Detalle", query = "SELECT t FROM T022ListaEmpaqueHasCaja t WHERE t.idExt2Detalle = :idExt2Detalle")
    , @NamedQuery(name = "T022ListaEmpaqueHasCaja.findByDescripcionExt1", query = "SELECT t FROM T022ListaEmpaqueHasCaja t WHERE t.descripcionExt1 = :descripcionExt1")
    , @NamedQuery(name = "T022ListaEmpaqueHasCaja.findByDescripcionExt2", query = "SELECT t FROM T022ListaEmpaqueHasCaja t WHERE t.descripcionExt2 = :descripcionExt2")
    , @NamedQuery(name = "T022ListaEmpaqueHasCaja.findByPeso", query = "SELECT t FROM T022ListaEmpaqueHasCaja t WHERE t.peso = :peso")
    , @NamedQuery(name = "T022ListaEmpaqueHasCaja.findByPartidaArancelaria", query = "SELECT t FROM T022ListaEmpaqueHasCaja t WHERE t.partidaArancelaria = :partidaArancelaria")
    , @NamedQuery(name = "T022ListaEmpaqueHasCaja.findByCodBarras", query = "SELECT t FROM T022ListaEmpaqueHasCaja t WHERE t.codBarras = :codBarras")
    , @NamedQuery(name = "T022ListaEmpaqueHasCaja.findByFechaCreacion", query = "SELECT t FROM T022ListaEmpaqueHasCaja t WHERE t.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "T022ListaEmpaqueHasCaja.findByIdListaEmpaqueCaja", query = "SELECT t FROM T022ListaEmpaqueHasCaja t WHERE t.idListaEmpaque = :idListaEmpaque and t.idCaja = :idCaja")
    , @NamedQuery(name = "T022ListaEmpaqueHasCaja.findByIdListaEmpaque", query = "SELECT t FROM T022ListaEmpaqueHasCaja t WHERE t.idListaEmpaque = :idListaEmpaque")
    , @NamedQuery(name = "T022ListaEmpaqueHasCaja.findById", query = "SELECT t FROM T022ListaEmpaqueHasCaja t WHERE t.id = :id")})
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
            name = "execObtenerDescItemPais", 
            procedureName = "dbo.sp_desc_item_lista_empaque", 
            parameters = { 
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "descPais"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "idItem")
            }  
        )
    }
)
public class T022ListaEmpaqueHasCaja implements Serializable {
    @Size(max = 1000)
    @Column(name = "notas_item")
    private String notasItem;
    
    @Size(max = 1000)
    @Column(name = "notas_docto")
    private String notasDocto;
    
    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;
    
    @Column(name = "precio_neto")
    private BigDecimal precioNeto;
     
    @Size(max = 100)
    @Column(name = "desc_pais")
    private String descPais;
    
    @Column(name = "cant_paquete")
    private int cantPaquete;
   
    @Size(max = 100)
    @Column(name = "oc")
    private String oc;

    @Size(max = 4)
    @Column(name = "id_bodega")
    private String idBodega;

    @Column(name = "estado")
    private Short estado;

    @Basic(optional = false)
    @NotNull
    @Column(name = "cant_cod_barra")
    private int cantCodBarra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cant_cod_barra_caja")
    private int cantCodBarraCaja;
    @Size(max = 40)
    @Column(name = "desc_partida_arancelaria")
    private String descPartidaArancelaria;

    @Basic(optional = false)
    @NotNull
    @Column(name = "cant_pedida")
    private BigDecimal cantPedida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cant_pendiente")
    private BigDecimal cantPendiente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cant_comprometida")
    private BigDecimal cantComprometida;

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_movimiento")
    private int idMovimiento;

    @Size(max = 10)
    @Column(name = "unidad")
    private String unidad;

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Size(min = 1, max = 12)
    @Column(name = "id_pedido")
    private String idPedido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo_item")
    private int codigoItem;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_ext1_detalle")
    private String idExt1Detalle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_ext2_detalle")
    private String idExt2Detalle;
    @Size(max = 60)
    @Column(name = "descripcion_ext1")
    private String descripcionExt1;
    @Size(max = 60)
    @Column(name = "descripcion_ext2")
    private String descripcionExt2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "peso")
    private BigDecimal peso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "partida_arancelaria")
    private String partidaArancelaria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "cod_barras")
    private String codBarras;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_lista_empaque", referencedColumnName = "id_lista_empaque")
    @ManyToOne(optional = false)
    private T020ListaEmpaque idListaEmpaque;
    @JoinColumn(name = "id_caja", referencedColumnName = "id_caja")
    @ManyToOne(optional = false)
    private T021Caja idCaja;
    public T022ListaEmpaqueHasCaja() {
    }

    public T022ListaEmpaqueHasCaja(Integer id) {
        this.id = id;
    }

    public T022ListaEmpaqueHasCaja(Integer id, String idPedido, int codigoItem, String descripcion, String idExt1Detalle, String idExt2Detalle, String partidaArancelaria, String codBarras, Date fechaCreacion) {
        this.id = id;
        this.idPedido = idPedido;
        this.codigoItem = codigoItem;
        this.descripcion = descripcion;
        this.idExt1Detalle = idExt1Detalle;
        this.idExt2Detalle = idExt2Detalle;
        this.partidaArancelaria = partidaArancelaria;
        this.codBarras = codBarras;
        this.fechaCreacion = fechaCreacion;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public int getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(int codigoItem) {
        this.codigoItem = codigoItem;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdExt1Detalle() {
        return idExt1Detalle;
    }

    public void setIdExt1Detalle(String idExt1Detalle) {
        this.idExt1Detalle = idExt1Detalle;
    }

    public String getIdExt2Detalle() {
        return idExt2Detalle;
    }

    public void setIdExt2Detalle(String idExt2Detalle) {
        this.idExt2Detalle = idExt2Detalle;
    }

    public String getDescripcionExt1() {
        return descripcionExt1;
    }

    public void setDescripcionExt1(String descripcionExt1) {
        this.descripcionExt1 = descripcionExt1;
    }

    public String getDescripcionExt2() {
        return descripcionExt2;
    }

    public void setDescripcionExt2(String descripcionExt2) {
        this.descripcionExt2 = descripcionExt2;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getPartidaArancelaria() {
        return partidaArancelaria;
    }

    public void setPartidaArancelaria(String partidaArancelaria) {
        this.partidaArancelaria = partidaArancelaria;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public T020ListaEmpaque getIdListaEmpaque() {
        return idListaEmpaque;
    }

    public void setIdListaEmpaque(T020ListaEmpaque idListaEmpaque) {
        this.idListaEmpaque = idListaEmpaque;
    }

    public T021Caja getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(T021Caja idCaja) {
        this.idCaja = idCaja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T022ListaEmpaqueHasCaja)) {
            return false;
        }
        T022ListaEmpaqueHasCaja other = (T022ListaEmpaqueHasCaja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T022ListaEmpaqueHasCaja[ id=" + id + " ]";
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public BigDecimal getCantPedida() {
        return cantPedida;
    }

    public void setCantPedida(BigDecimal cantPedida) {
        this.cantPedida = cantPedida;
    }

    public BigDecimal getCantPendiente() {
        return cantPendiente;
    }

    public void setCantPendiente(BigDecimal cantPendiente) {
        this.cantPendiente = cantPendiente;
    }

    public BigDecimal getCantComprometida() {
        return cantComprometida;
    }

    public void setCantComprometida(BigDecimal cantComprometida) {
        this.cantComprometida = cantComprometida;
    }

    public String getDescPartidaArancelaria() {
        return descPartidaArancelaria;
    }

    public void setDescPartidaArancelaria(String descPartidaArancelaria) {
        this.descPartidaArancelaria = descPartidaArancelaria;
    }

    public int getCantCodBarra() {
        return cantCodBarra;
    }
    public void setCantCodBarra(int cantCodBarra) {
        this.cantCodBarra = cantCodBarra;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public String getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(String idBodega) {
        this.idBodega = idBodega;
    }

    public int getCantCodBarraCaja() {
        return cantCodBarraCaja;
    }

    public void setCantCodBarraCaja(int cantCodBarraCaja) {
        this.cantCodBarraCaja = cantCodBarraCaja;
    }

    public String getOc() {
        return oc;
    }

    public void setOc(String oc) {
        this.oc = oc;
    }

    public int getCantPaquete() {
        return cantPaquete;
    }

    public void setCantPaquete(int cantPaquete) {
        this.cantPaquete = cantPaquete;
    }

    public String getDescPais() {
        return descPais;
    }

    public void setDescPais(String descPais) {
        this.descPais = descPais;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getPrecioNeto() {
        return precioNeto;
    }

    public void setPrecioNeto(BigDecimal precioNeto) {
        this.precioNeto = precioNeto;
    }

    public String getNotasDocto() {
        return notasDocto;
    }

    public void setNotasDocto(String notasDocto) {
        this.notasDocto = notasDocto;
    }

    public String getNotasItem() {
        return notasItem;
    }

    public void setNotasItem(String notasItem) {
        this.notasItem = notasItem;
    }

    
    
}
