/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
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
@Cacheable(false)
@Entity
@Table(name = "t020_lista_empaque")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T020ListaEmpaque.findAll", query = "SELECT t FROM T020ListaEmpaque t ORDER BY t.idListaEmpaque DESC")
    , @NamedQuery(name = "T020ListaEmpaque.findByIdListaEmpaque", query = "SELECT t FROM T020ListaEmpaque t WHERE t.idListaEmpaque = :idListaEmpaque")
    , @NamedQuery(name = "T020ListaEmpaque.findByCodigoLista", query = "SELECT t FROM T020ListaEmpaque t WHERE t.codigoLista = :codigoLista")
    , @NamedQuery(name = "T020ListaEmpaque.findByNit", query = "SELECT t FROM T020ListaEmpaque t WHERE t.nit = :nit")
    , @NamedQuery(name = "T020ListaEmpaque.findByRazonSocial", query = "SELECT t FROM T020ListaEmpaque t WHERE t.razonSocial = :razonSocial")
    , @NamedQuery(name = "T020ListaEmpaque.findByTipo", query = "SELECT t FROM T020ListaEmpaque t WHERE t.tipoListaEmpaque = :tipoListaEmpaque ORDER BY t.idListaEmpaque DESC")
    , @NamedQuery(name = "T020ListaEmpaque.findByEstado", query = "SELECT t FROM T020ListaEmpaque t WHERE t.estado = :estado")
    , @NamedQuery(name = "T020ListaEmpaque.findByFechaCreacion", query = "SELECT t FROM T020ListaEmpaque t WHERE t.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "T020ListaEmpaque.findByFechaCierre", query = "SELECT t FROM T020ListaEmpaque t WHERE t.fechaCierre = :fechaCierre")
    , @NamedQuery(name = "T020ListaEmpaque.findByIdUsuarioCreacion", query = "SELECT t FROM T020ListaEmpaque t WHERE t.idUsuarioCreacion = :idUsuarioCreacion ORDER BY t.idListaEmpaque DESC")})
@NamedNativeQueries({
    @NamedNativeQuery(
    name = "execNit",
    query = "select distinct RIGHT('000000000000000' + ltrim(Rtrim(f200_nit)),15)  + ' - '+f200_razon_social from [UnoEE].[dbo].t200_mm_terceros t200 inner join [UnoEE].[dbo].t201_mm_clientes t201 on t201.f201_rowid_tercero = t200.f200_rowid where f200_ind_estado = 1 "
    ),
    @NamedNativeQuery(
    name = "execPedido",
    query = "select RIGHT('   ' + RTRIM(LTRIM(f430_id_tipo_docto)),3) +'-'+CONVERT(varchar(20),f430_consec_docto) from UnoEE.dbo.t430_cm_pv_docto t430 inner join UnoEE.dbo.t200_mm_terceros t200 on t200.f200_rowid = t430.f430_rowid_tercero_fact where t200.f200_nit = #nit and t430.f430_ind_estado = 3 and t430.f430_id_tipo_docto <> 'PNF' order by t430.f430_id_tipo_docto, t430.f430_consec_docto"
    )
    ,
    @NamedNativeQuery(
    name = "execRowidItem",
    query = "select f121_rowid_item from UnoEE.dbo.t121_mc_items_extensiones where f121_id_barras_principal = #codBarra"
    )
})
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
            name = "execObtenerConectorListaEmpaque", 
            procedureName = "dbo.sp_conector_lista_empaque", 
            parameters = { 
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "idListaEmpaque")
            }  
        )
    }
)
public class T020ListaEmpaque implements Serializable {

    @JoinColumn(name = "id_usuario_creacion", referencedColumnName = "id_usuario")
    @ManyToOne
    private T001Usuario idUsuarioCreacion;

    @Size(max = 20)
    @Column(name = "tipo_lista_empaque")
    private String tipoListaEmpaque;
    
    @Column(name = "estado_wb")
    private Boolean estadoWb;
    @Size(max = 2147483647)
    @Column(name = "mensaje_wb")
    private String mensajeWb;

    @Size(max = 1000)
    @Column(name = "cod_factura")
    private String codFactura;

    @OneToMany(mappedBy = "idListaEmpaque")
    private List<T021Caja> t021CajaList;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lista_empaque")
    private Integer idListaEmpaque;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "codigo_lista")
    private String codigoLista;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nit")
    private String nit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "razon_social")
    private String razonSocial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private short estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idListaEmpaque")
    private List<T022ListaEmpaqueHasCaja> t022ListaEmpaqueHasCajaList;

    public T020ListaEmpaque() {
    }

    public T020ListaEmpaque(Integer idListaEmpaque) {
        this.idListaEmpaque = idListaEmpaque;
    }

    public T020ListaEmpaque(Integer idListaEmpaque, String codigoLista, String nit, String razonSocial, short estado, Date fechaCreacion, String responsable) {
        this.idListaEmpaque = idListaEmpaque;
        this.codigoLista = codigoLista;
        this.nit = nit;
        this.razonSocial = razonSocial;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdListaEmpaque() {
        return idListaEmpaque;
    }

    public void setIdListaEmpaque(Integer idListaEmpaque) {
        this.idListaEmpaque = idListaEmpaque;
    }

    public String getCodigoLista() {
        return codigoLista;
    }

    public void setCodigoLista(String codigoLista) {
        this.codigoLista = codigoLista;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public short getEstado() {
        return estado;
    }
    public String getEstadoString() {
        switch(estado){
            case 0:
               return "Borrador";
            case 1:
                return "Proceso de remisión y facturación";
            default:
                return "Terminada";
        }
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    

    @XmlTransient
    public List<T022ListaEmpaqueHasCaja> getT022ListaEmpaqueHasCajaList() {
        return t022ListaEmpaqueHasCajaList;
    }

    public void setT022ListaEmpaqueHasCajaList(List<T022ListaEmpaqueHasCaja> t022ListaEmpaqueHasCajaList) {
        this.t022ListaEmpaqueHasCajaList = t022ListaEmpaqueHasCajaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idListaEmpaque != null ? idListaEmpaque.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T020ListaEmpaque)) {
            return false;
        }
        T020ListaEmpaque other = (T020ListaEmpaque) object;
        if ((this.idListaEmpaque == null && other.idListaEmpaque != null) || (this.idListaEmpaque != null && !this.idListaEmpaque.equals(other.idListaEmpaque))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T020ListaEmpaque[ idListaEmpaque=" + idListaEmpaque + " ]";
    }

    @XmlTransient
    public List<T021Caja> getT021CajaList() {
        return t021CajaList;
    }

    public void setT021CajaList(List<T021Caja> t021CajaList) {
        this.t021CajaList = t021CajaList;
    }

    public String getCodFactura() {
        return codFactura;
    }

    public void setCodFactura(String codFactura) {
        this.codFactura = codFactura;
    }

    public boolean getEstadoWs() {
        return estadoWb;
    }

    public void setEstadoWb(boolean estadoWb) {
        this.estadoWb = estadoWb;
    }

    public String getMensajeWb() {
        return mensajeWb;
    }

    public void setMensajeWb(String mensajeWb) {
        this.mensajeWb = mensajeWb;
    }

    public Boolean getEstadoWb() {
        return estadoWb;
    }

    public void setEstadoWb(Boolean estadoWb) {
        this.estadoWb = estadoWb;
    }

    public String getTipoListaEmpaque() {
        return tipoListaEmpaque;
    }

    public void setTipoListaEmpaque(String tipoListaEmpaque) {
        this.tipoListaEmpaque = tipoListaEmpaque;
    }

    public T001Usuario getIdUsuarioCreacion() {
        return idUsuarioCreacion;
    }

    public void setIdUsuarioCreacion(T001Usuario idUsuarioCreacion) {
        this.idUsuarioCreacion = idUsuarioCreacion;
    }

   
}
