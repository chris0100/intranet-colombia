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
@Table(name = "t012_lecturas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T012Lecturas.findAll", query = "SELECT t FROM T012Lecturas t")
    , @NamedQuery(name = "T012Lecturas.findByF001CodBarraItem", query = "SELECT t FROM T012Lecturas t WHERE t.f001CodBarraItem = :f001CodBarraItem")
    , @NamedQuery(name = "T012Lecturas.findByF001IdOp", query = "SELECT t FROM T012Lecturas t WHERE t.f001IdOp = :f001IdOp order by t.f001Consecutivo ASC")
    , @NamedQuery(name = "T012Lecturas.findByF001IdOpEstado", query = "SELECT t FROM T012Lecturas t WHERE t.f001IdOp = :f001IdOp and t.f001RegistroConector = :f001RegistroConector order by t.f001Consecutivo ASC")
    , @NamedQuery(name = "T012Lecturas.findByF001Lote", query = "SELECT t FROM T012Lecturas t WHERE t.f001Lote = :f001Lote")
    , @NamedQuery(name = "T012Lecturas.findByF001Unidad", query = "SELECT t FROM T012Lecturas t WHERE t.f001Unidad = :f001Unidad")
    , @NamedQuery(name = "T012Lecturas.findByF001Consecutivo", query = "SELECT t FROM T012Lecturas t WHERE t.f001Consecutivo = :f001Consecutivo")
    , @NamedQuery(name = "T012Lecturas.findByF001IdLectura", query = "SELECT t FROM T012Lecturas t WHERE t.f001IdLectura = :f001IdLectura")
    , @NamedQuery(name = "T012Lecturas.findByF001Fecha", query = "SELECT t FROM T012Lecturas t WHERE t.f001Fecha = :f001Fecha")
    , @NamedQuery(name = "T012Lecturas.findByF001RegistroConector", query = "SELECT t FROM T012Lecturas t WHERE t.f001RegistroConector = :f001RegistroConector order by t.f001Fecha DESC")
    , @NamedQuery(name = "T012Lecturas.findByEstadoConectorNoEnvio", query = "SELECT t FROM T012Lecturas t  WHERE t.f001RegistroConector = :noEnvio or t.f001RegistroConector = :errorEnvio ORDER BY  t.f001RegistroConector ASC")})

@NamedNativeQueries({
	@NamedNativeQuery(
	name = "execKosListaNoEnviados",
	query = "select * from dbo.t012_lecturas WHERE f001_registro_conector = 2 or f001_registro_conector = 0 ORDER BY  f001_registro_conector ASC",
	resultClass = T012Lecturas.class
	),
        @NamedNativeQuery(
	name = "execKosListaOp",
	query = "select distinct f001_id_op from dbo.t012_lecturas"
	)
        ,
        @NamedNativeQuery(
	name = "execKosListaOp",
	query = "select distinct f001_id_op from dbo.t012_lecturas"
	)
        //totales cargados
        ,
        @NamedNativeQuery(
	name = "totalCajasOp",
	query = "select count(*) from dbo.t012_lecturas where f001_id_op = #op"
	)
        ,
        @NamedNativeQuery(
	name = "totalCajasUnoEEOp",
	query = "select count(*) from dbo.t012_lecturas where f001_registro_conector = 1 and f001_id_op = #op"
	)
        ,
        @NamedNativeQuery(
	name = "totalCajasNoUnoEEOp",
	query = "select count(*) from dbo.t012_lecturas where f001_registro_conector = 0 and f001_id_op = #op"
	)
        
        ,
        @NamedNativeQuery(
	name = "totalCajasErrorUnoEEOp",
	query = "select count(*) from dbo.t012_lecturas where f001_registro_conector = 2 and f001_id_op = #op"
	)
        ,
        @NamedNativeQuery(
	name = "totalUnidadesOp",
	query = "SELECT isnull(rtrim(SUM(convert(int,f001_unidad))),'0') FROM dbo.t012_lecturas where f001_id_op = #op"
	)
        ,
        @NamedNativeQuery(
	name = "totalUnidadesUnoEEOp",
	query = "SELECT isnull(rtrim(SUM(convert(int,f001_unidad))),'0') FROM dbo.t012_lecturas where f001_registro_conector = 1 and f001_id_op = #op"
	)
        ,
        @NamedNativeQuery(
	name = "totalUnidadesNoUnoEEOp",
	query = "SELECT isnull(rtrim(SUM(convert(int,f001_unidad))),'0') FROM dbo.t012_lecturas where f001_registro_conector = 0 and f001_id_op = #op"
	)
        
        ,
        @NamedNativeQuery(
	name = "totalUnidadesErrorUnoEEOp",
	query = "SELECT isnull(rtrim(SUM(convert(int,f001_unidad))),'0') FROM dbo.t012_lecturas where f001_registro_conector = 2 and f001_id_op = #op"
	)
        ,
        @NamedNativeQuery(
	name = "execTotalEptMesEureka",
	query = "SELECT count(*) FROM t012_lecturas where f001_registro_conector = 1 and datepart(mm, f001_fecha) = datepart(mm, getdate()) "
        )
        ,
        @NamedNativeQuery(
	name = "execTotalEptMesUnoEE",
	query = "SELECT count(*) f470_rowid_op_docto FROM [UnoEE].[dbo].[t350_co_docto_contable] t350 inner join [UnoEE].[dbo].t470_cm_movto_invent t470 on t470.f470_rowid_docto = t350.f350_rowid inner join [UnoEE].[dbo].t121_mc_items_extensiones t121 on t121.f121_rowid = t470.f470_rowid_item_ext inner join [UnoEE].[dbo].t120_mc_items t120 on t120.f120_rowid = t121.f121_rowid_item inner join [UnoEE].[dbo].[t850_mf_op_docto] t850 on t850.f850_rowid = t470.f470_rowid_op_docto where f350_id_tipo_docto = 'EPT' and t120.f120_ind_venta = 1 and t850.f850_id_tipo_docto ='OPK' and datepart(mm, t470.f470_ts) = datepart(mm, getdate())"
        )
        ,
        @NamedNativeQuery(
	name = "execOPCargar",
	query = "SELECT [f001_id_op] FROM [t012_lecturas] WHERE f001_registro_conector = 0 GROUP BY [f001_id_op]"
        )
        , 
        @NamedNativeQuery(
	name = "execOPEstadoUnoEE",
	query = "select * from unoee.dbo.t850_mf_op_docto where (f850_ind_estado = 1 or f850_ind_estado = 2) and f850_id_tipo_docto = 'OPK' and f850_consec_docto = #op"
        )
})

@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
    name = "execKosLista", 
    procedureName = "dbo.sp_kos_lista_no_enviados"),
    
    @NamedStoredProcedureQuery(name = "execEjecutarSsisCodigoBarrasKos", 
    procedureName = "dbo.sp_ejecutar_siss_codigo_barras_kos"),
    
    @NamedStoredProcedureQuery(name = "execEjecutarCodigoBarraKos", 
    procedureName = "dbo.sp_cod_barra_kos",
    parameters = { 
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "op")
    })
})
/*@NamedStoredProcedureQuery(
	name = "execKosListaNoEnviados", 
	procedureName = "dbo.sp_kos_lista_no_enviados"
)*/
public class T012Lecturas implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Size(min = 1, max = 45)
    @Column(name = "f001_id_lectura")
    private String f001IdLectura;
    @Size(max = 15)
    @Column(name = "f001_cod_barra_item")
    private String f001CodBarraItem;
    @Size(max = 5)
    @Column(name = "f001_id_op")
    private String f001IdOp;
    @Size(max = 15)
    @Column(name = "f001_lote")
    private String f001Lote;
    @Size(max = 5)
    @Column(name = "f001_unidad")
    private String f001Unidad;
    @Column(name = "f001_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date f001Fecha;
    @Size(max = 5)
    @Column(name = "f001_consecutivo")
    private String f001Consecutivo;
    @Size(max = 1073741823)
    @Column(name = "f001_observaciones")
    private String f001Observaciones;
    @Column(name = "f001_registro_conector")
    private Short f001RegistroConector;
    @Column(name = "f001_recargar_lista")
    private Boolean f001RecargarLista;
    @Size(max = 1073741823)
    @Column(name = "f001_error_xml")
    private String f001ErrorXml;
    @JoinColumn(name = "f001_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private T001Usuario f001Usuario;

    
    public T012Lecturas() {
    }

    

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T012Lecturas[ f001IdLectura=" + f001IdLectura + " ]";
    }

    public T012Lecturas(String f001IdLectura) {
        this.f001IdLectura = f001IdLectura;
    }

    public String getF001IdLectura() {
        return f001IdLectura;
    }

    public void setF001IdLectura(String f001IdLectura) {
        this.f001IdLectura = f001IdLectura;
    }

    public String getF001CodBarraItem() {
        return f001CodBarraItem;
    }

    public void setF001CodBarraItem(String f001CodBarraItem) {
        this.f001CodBarraItem = f001CodBarraItem;
    }

    public String getF001IdOp() {
        return f001IdOp;
    }

    public void setF001IdOp(String f001IdOp) {
        this.f001IdOp = f001IdOp;
    }

    public String getF001Lote() {
        return f001Lote;
    }

    public void setF001Lote(String f001Lote) {
        this.f001Lote = f001Lote;
    }

    public String getF001Unidad() {
        return f001Unidad;
    }

    public void setF001Unidad(String f001Unidad) {
        this.f001Unidad = f001Unidad;
    }

    public Date getF001Fecha() {
        return f001Fecha;
    }

    public void setF001Fecha(Date f001Fecha) {
        this.f001Fecha = f001Fecha;
    }

    public String getF001Consecutivo() {
        return f001Consecutivo;
    }

    public void setF001Consecutivo(String f001Consecutivo) {
        this.f001Consecutivo = f001Consecutivo;
    }

    public String getF001Observaciones() {
        return f001Observaciones;
    }

    public void setF001Observaciones(String f001Observaciones) {
        this.f001Observaciones = f001Observaciones;
    }

    public Short getF001RegistroConector() {
        return f001RegistroConector;
    }

    public void setF001RegistroConector(Short f001RegistroConector) {
        this.f001RegistroConector = f001RegistroConector;
    }

    public Boolean getF001RecargarLista() {
        return f001RecargarLista;
    }

    public void setF001RecargarLista(Boolean f001RecargarLista) {
        this.f001RecargarLista = f001RecargarLista;
    }

    public String getF001ErrorXml() {
        return f001ErrorXml;
    }

    public void setF001ErrorXml(String f001ErrorXml) {
        this.f001ErrorXml = f001ErrorXml;
    }

    public T001Usuario getF001Usuario() {
        return f001Usuario;
    }

    public void setF001Usuario(T001Usuario f001Usuario) {
        this.f001Usuario = f001Usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (f001IdLectura != null ? f001IdLectura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T012Lecturas)) {
            return false;
        }
        T012Lecturas other = (T012Lecturas) object;
        if ((this.f001IdLectura == null && other.f001IdLectura != null) || (this.f001IdLectura != null && !this.f001IdLectura.equals(other.f001IdLectura))) {
            return false;
        }
        return true;
    }


    
    
}
