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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
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
@Table(name = "t013_creacion_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T013CreacionItem.findAll", query = "SELECT t FROM T013CreacionItem t")
    , @NamedQuery(name = "T013CreacionItem.findByIdCreacionItem", query = "SELECT t FROM T013CreacionItem t WHERE t.idCreacionItem = :idCreacionItem")
    , @NamedQuery(name = "T013CreacionItem.findByCodigoItem", query = "SELECT t FROM T013CreacionItem t WHERE t.codigoItem = :codigoItem")
    , @NamedQuery(name = "T013CreacionItem.findByDescripcion", query = "SELECT t FROM T013CreacionItem t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "T013CreacionItem.findByIdExt1Detalle", query = "SELECT t FROM T013CreacionItem t WHERE t.idExt1Detalle = :idExt1Detalle")
    , @NamedQuery(name = "T013CreacionItem.findByIdExt2Detalle", query = "SELECT t FROM T013CreacionItem t WHERE t.idExt2Detalle = :idExt2Detalle")
    , @NamedQuery(name = "T013CreacionItem.findByUsuarioSolicitud", query = "SELECT t FROM T013CreacionItem t WHERE t.usuarioSolicitud = :usuarioSolicitud")
    , @NamedQuery(name = "T013CreacionItem.findByFechaSolicitud", query = "SELECT t FROM T013CreacionItem t WHERE t.fechaSolicitud = :fechaSolicitud")
    , @NamedQuery(name = "T013CreacionItem.findByUsuarioCreacion", query = "SELECT t FROM T013CreacionItem t WHERE t.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "T013CreacionItem.findByFechaCreacion", query = "SELECT t FROM T013CreacionItem t WHERE t.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "T013CreacionItem.findByItemRepetido", query = "SELECT t FROM T013CreacionItem t WHERE t.codigoItem = :codigoItem and t.idExt1Detalle = :idExt1Detalle and t.idExt2Detalle = :idExt2Detalle and t.estado <= :estado")
    , @NamedQuery(name = "T013CreacionItem.findByReporteCreados", query = "SELECT t FROM T013CreacionItem t WHERE t.estado = :estado AND t.fechaCreacion BETWEEN :fecha1 AND :fecha2 ORDER BY t.fechaCreacion")
    , @NamedQuery(name = "T013CreacionItem.findByEstado", query = "SELECT t FROM T013CreacionItem t WHERE t.estado = :estado and t.itemNuevo = :itemNuevo ORDER BY t.fechaSolicitud,t.estado,t.codigoItem,t.idExt1Detalle,t.idExt2Detalle ASC")
    , @NamedQuery(name = "T013CreacionItem.findByEstadoTodos", query = "SELECT t FROM T013CreacionItem t WHERE t.estado = :estado ORDER BY t.fechaSolicitud,t.estado,t.codigoItem,t.idExt1Detalle,t.idExt2Detalle ASC")
    , @NamedQuery(name = "T013CreacionItem.findByEstadoRespuestaTodos", query = "SELECT t FROM T013CreacionItem t WHERE t.estado <= :estado ORDER BY t.fechaSolicitud,t.estado,t.codigoItem,t.idExt1Detalle,t.idExt2Detalle ASC")
    , @NamedQuery(name = "T013CreacionItem.findByEstadoRespuesta", query = "SELECT t FROM T013CreacionItem t WHERE t.estado <= :estado and t.itemNuevo = :itemNuevo ORDER BY t.fechaSolicitud,t.estado,t.codigoItem,t.idExt1Detalle,t.idExt2Detalle ASC")})

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "execEjecutarSsisItemExt", 
	procedureName = "dbo.sp_siss_crear_item_ext"),
        @NamedStoredProcedureQuery(name = "execEjecutarActualizarItemsCreados", 
	procedureName = "dbo.sp_actualizar_tabla_items_creados"),
        @NamedStoredProcedureQuery(name = "execEjecutarActualizarItemsCreadosPortafolio", 
	procedureName = "dbo.sp_actualizar_tabla_items_creados_portafolio")
})

@NamedNativeQueries({
    @NamedNativeQuery(
    name = "execListaExt1",
    query = "SELECT CAST( LEFT(LTRIM(RTRIM(f117_id)) + '    ', 4)as nvarchar(4)) + ' -'+LTRIM(RTRIM(f117_descripcion)) FROM UnoEE.dbo.t117_mc_extensiones1_detalle WHERE f117_id_extension1 = #tipoExt"
    ),
    @NamedNativeQuery(
    name = "execListaExt2",
    query = "SELECT LTRIM(RTRIM(f119_id)) + ' -' + LTRIM(RTRIM(f119_descripcion)) FROM UnoEE.dbo.t119_mc_extensiones2_detalle WHERE f119_id_extension2 = #tipoExt"
    ),
    @NamedNativeQuery(
    name = "execListaItemPatron",
    query = "SELECT CAST( RIGHT('0000000'+convert(varchar,isnull( f120_id ,'')),7)as nvarchar(7)) +' | ' + CAST( LEFT(f120_descripcion + '                                        ', 40)as nvarchar(40))   +' | ' + + CAST( RIGHT('0000000000'+convert(varchar,isnull( f149_id ,'')),10)as nvarchar(10))  + CAST( RIGHT('  '+convert(varchar,isnull( f149_id_extension1 ,'')),2)as nvarchar(2))  + CAST( RIGHT('  '+convert(varchar,isnull( f149_id_extension2 ,'')),2)as nvarchar(2)) FROM UnoEE.dbo.t120_mc_items t120 inner join UnoEE.dbo.t149_mc_tipo_inv_serv t149 ON t149.f149_id = t120.f120_id_tipo_inv_serv where t120.f120_id_tipo_inv_serv like '%.ex%'"                             
    ),
    @NamedNativeQuery(
    name = "execListaTipoInventario",
    query = "SELECT  CAST( RIGHT('0000000000'+convert(varchar,isnull( f149_id ,'')),10)as nvarchar(10))  + CAST( RIGHT('  '+convert(varchar,isnull( f149_id_extension1 ,'')),2)as nvarchar(2))  + CAST( RIGHT('  '+convert(varchar,isnull( f149_id_extension2 ,'')),2)as nvarchar(2))  +f149_descripcion FROM UnoEE.dbo.t149_mc_tipo_inv_serv WHERE f149_id like '%.ex%'"
    ),
    @NamedNativeQuery(
    name = "execListaItemRepetido",
    query = "SELECT f120_id,f121_id_ext1_detalle ,f121_id_ext2_detalle FROM [UnoEE].[dbo].[t121_mc_items_extensiones] t121 inner join [UnoEE].[dbo].[t120_mc_items] t120 on t120.f120_rowid = t121.f121_rowid_item where T120.f120_id = #codigoItem and t121.f121_id_ext1_detalle = #ext1 and t121.f121_id_ext2_detalle = #ext2 and t121.f121_ind_estado = 1"
    ),
    @NamedNativeQuery(
    name = "execListaItemInactivo",
    query = "SELECT f120_id,f121_id_ext1_detalle ,f121_id_ext2_detalle FROM [UnoEE].[dbo].[t121_mc_items_extensiones] t121 inner join [UnoEE].[dbo].[t120_mc_items] t120 on t120.f120_rowid = t121.f121_rowid_item where T120.f120_id = #codigoItem and t121.f121_id_ext1_detalle = #ext1 and t121.f121_id_ext2_detalle = #ext2 and t121.f121_ind_estado = 0"
    )
    ,
    @NamedNativeQuery(
    name = "execListaPrecio",
    query = "SELECT f112_id+'-'+f112_id_moneda+'-'+CAST( LEFT(f112_descripcion + '                                        ', 40)as nvarchar(40)) FROM [UnoEE].[dbo].[t112_mc_listas_precios] where f112_ind_estado = 1 ORDER BY f112_id" 
    ) 
    ,
    @NamedNativeQuery(
    name = "execRecargarListaItemsCreados",
    query = "SELECT * FROM t013_creacion_item WHERE estado = 3 and item_nuevo = 0 and (unoee_codigo_barra = 0 or unoee_parametros_planeacion = 0 or unoee_listas_materiales = 0 or unoee_precios = 0) ORDER BY fecha_creacion DESC" ,
    resultClass = T013CreacionItem.class
    ) 
    ,
    @NamedNativeQuery(
    name = "execReporteCreados",
    query = "SELECT * FROM t013_creacion_item WHERE estado = #estado AND CONVERT(date,[fecha_creacion]) BETWEEN CONVERT(date,#fecha1)  AND CONVERT(date,#fecha2) ORDER BY fecha_creacion ASC" ,
    resultClass = T013CreacionItem.class
    )     
    ,
    @NamedNativeQuery(
    name = "execUndInventario",
    query = "SELECT [f101_id]+' - '+[f101_descripcion] FROM [UnoEE].[dbo].[t101_mc_unidades_medida] WHERE [f101_ind_estado] = 1 ORDER BY [f101_id] DESC" 
    )  
     ,
    @NamedNativeQuery(
    name = "execItemCreadoUnoEE",
    query = "select count(*) from [UnoEE].dbo.t121_mc_items_extensiones where datepart(mm, f121_fecha_creacion) = datepart(mm,getdate()) and datepart(yyyy, f121_fecha_creacion) = datepart(yyyy,getdate()) " 
    )  
    ,@NamedNativeQuery(
    name = "execItemCreadoEureka",
    query = "select count(*) from t013_creacion_item where datepart(mm, fecha_creacion) = datepart(mm, getdate()) and datepart(yyyy, fecha_creacion) = datepart(yyyy,getdate()) and id_ext1_detalle is not null" 
    ) 
    ,
    @NamedNativeQuery(
    name = "execPosArancelaria",
    query = "SELECT ltrim(rtrim([f102_id]))+' - '+ltrim(rtrim([f102_descripcion])) FROM [UnoEE].[dbo].[t102_mc_pos_arancelarias]" 
    )  
        
})


public class T013CreacionItem implements Serializable {

    @Size(max = 100)
    @Column(name = "posicion_arancelaria")
    private String posicionArancelaria;
    @Column(name = "ind_compra")
    private boolean indCompra;
    @Column(name = "ind_venta")
    private boolean indVenta;
    @Column(name = "ind_manufactura")
    private boolean indManufactura;
    @Size(max = 15)
    @Column(name = "tipo_item")
    private String tipoItem;
    @Size(max = 50)
    @Column(name = "unidad_inventario")
    private String unidadInventario;

    @Basic(optional = false)
    @NotNull
    @Column(name = "unoee_precios")
    private short unoeePrecios;
    @Size(max = 500)
    @Column(name = "notas_comercial")
    private String notasComercial;

    @Column(name = "unoee_reactivado")
    private Short unoeeReactivado;
    @Size(max = 30)
    @Column(name = "cod_cliente_nit")
    private String codClienteNit;
    @Size(max = 30)
    @Column(name = "cod_cliente_numero")
    private String codClienteNumero;
    @Size(max = 100)
    @Column(name = "cod_cliente_descripcion")
    private String codClienteDescripcion;

    @Column(name = "unoee_portafolio")
    private Short unoeePortafolio;

    @Size(max = 70)
    @Column(name = "descripcion_lista_precio")
    private String descripcionListaPrecio;

    @Size(max = 3)
    @Column(name = "moneda")
    private String moneda;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private BigDecimal precio;

 
   
    @Size(max = 3)
    @Column(name = "id_lista_precio")
    private String idListaPrecio;
    @Column(name = "fecha_activacion_precio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActivacionPrecio;

    @Size(max = 100)
    @Column(name = "descripcion_completa_item")
    private String descripcionCompletaItem;

    @Size(max = 60)
    @Column(name = "descripcion_ext1")
    private String descripcionExt1;

    @Column(name = "unoee_codigo_barra")
    private Short unoeeCodigoBarra;
    @Column(name = "unoee_parametros_planeacion")
    private Short unoeeParametrosPlaneacion;
    @Column(name = "unoee_listas_materiales")
    private Short unoeeListasMateriales;

    @Size(max = 60)
    @Column(name = "descripcion_ext2")
    private String descripcionExt2;

    @Column(name = "descripcion_respuesta")
    private String descripcionRespuesta;

    @Column(name = "item_nuevo")
    private Short itemNuevo;

    @Size(max = 20)
    @Column(name = "grupo")
    private String grupo;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_creacion_item")
    private Integer idCreacionItem;
    
    
    
    @Column(name = "codigo_item")
    private Integer codigoItem;
    @Size(max = 70)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 20)
    @Column(name = "id_ext1_detalle")
    private String idExt1Detalle;
    @Size(max = 20)
    @Column(name = "id_ext2_detalle")
    private String idExt2Detalle;
    @Size(max = 40)
    @Column(name = "usuario_solicitud")
    private String usuarioSolicitud;
    @Column(name = "fecha_solicitud")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitud;
    @Size(max = 40)
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "estado")
    private Short estado;

    public T013CreacionItem() {
    }

    public T013CreacionItem(Integer idCreacionItem) {
        this.idCreacionItem = idCreacionItem;
    }

    public Integer getIdCreacionItem() {
        return idCreacionItem;
    }

    public void setIdCreacionItem(Integer idCreacionItem) {
        this.idCreacionItem = idCreacionItem;
    }

    public Integer getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(Integer codigoItem) {
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

    public String getUsuarioSolicitud() {
        return usuarioSolicitud;
    }

    public void setUsuarioSolicitud(String usuarioSolicitud) {
        this.usuarioSolicitud = usuarioSolicitud;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
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

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCreacionItem != null ? idCreacionItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T013CreacionItem)) {
            return false;
        }
        T013CreacionItem other = (T013CreacionItem) object;
        if ((this.idCreacionItem == null && other.idCreacionItem != null) || (this.idCreacionItem != null && !this.idCreacionItem.equals(other.idCreacionItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T013CreacionItem[ idCreacionItem=" + idCreacionItem + " ]";
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Short getItemNuevo() {
        return itemNuevo;
    }

    public void setItemNuevo(Short itemNuevo) {
        this.itemNuevo = itemNuevo;
    }

    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }

    public void setDescripcionRespuesta(String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }

    public String getDescripcionExt2() {
        return descripcionExt2;
    }

    public void setDescripcionExt2(String descripcionExt2) {
        this.descripcionExt2 = descripcionExt2;
    }

    public Short getUnoeeCodigoBarra() {
        return unoeeCodigoBarra;
    }

    public void setUnoeeCodigoBarra(Short unoeeCodigoBarra) {
        this.unoeeCodigoBarra = unoeeCodigoBarra;
    }

    public Short getUnoeeParametrosPlaneacion() {
        return unoeeParametrosPlaneacion;
    }

    public void setUnoeeParametrosPlaneacion(Short unoeeParametrosPlaneacion) {
        this.unoeeParametrosPlaneacion = unoeeParametrosPlaneacion;
    }

    public Short getUnoeeListasMateriales() {
        return unoeeListasMateriales;
    }

    public void setUnoeeListasMateriales(Short unoeeListasMateriales) {
        this.unoeeListasMateriales = unoeeListasMateriales;
    }

    public Short getUnoeePrecios() {
        return unoeePrecios;
    }

    public void setUnoeePrecios(Short unoeePrecios) {
        this.unoeePrecios = unoeePrecios;
    }

    public String getDescripcionExt1() {
        return descripcionExt1;
    }

    public void setDescripcionExt1(String descripcionExt1) {
        this.descripcionExt1 = descripcionExt1;
    }
    public String getDescripcionCompletaItem() {
        return descripcionCompletaItem;
    }

    public void setDescripcionCompletaItem(String descripcionCompletaItem) {
        this.descripcionCompletaItem = descripcionCompletaItem;
    }  

  

    public String getIdListaPrecio() {
        return idListaPrecio;
    }

    public void setIdListaPrecio(String idListaPrecio) {
        this.idListaPrecio = idListaPrecio;
    }

    public Date getFechaActivacionPrecio() {
        return fechaActivacionPrecio;
    }

    public void setFechaActivacionPrecio(Date fechaActivacionPrecio) {
        this.fechaActivacionPrecio = fechaActivacionPrecio;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    public String getLabelPP(){
        switch (getUnoeeParametrosPlaneacion()){
            case 1:
                return "SI";

            case 0:
                return "NO";

            default: 
                return "NO";   
        }
    }
    public String getLabelLM(){
        switch (getUnoeeListasMateriales()){
            case 1:
                return "SI";

            case 0:
                return "NO";

            default: 
                return "NO";   
        }
    }
    public String getLabelCB(){
        switch (getUnoeeCodigoBarra()){
            case 1:
                return "SI";

            case 0:
                return "NO";

            default: 
                return "NO";   
        }
    }
    public String getLabelLP(){
        switch (getUnoeePrecios()){
            case 1:
                return "SI";

            case 0:
                return "NO";

            default: 
                return "NO";   
        }
    }
    public String getLabelPOR(){
        switch (getUnoeePortafolio()){
            case 1:
                return "SI";

            case 0:
                return "NO";

            default: 
                return "NO";   
        }
    }
    public String getLabelRA(){
        switch (getUnoeeReactivado()){
            case 1:
                return "SI";

            case 0:
                return "NO";

            default: 
                return "NO";   
        }
    }
    
    public String getLabelVenta(){
        if (isIndVenta()){
            return "SI";
        }else{
            return "NO";
        }
    }
    public String getLabelCompra(){
        if (isIndCompra()){
            return "SI";
        }else{
            return "NO";
        }
    }
    public String getLabelManufactura(){
        if (isIndManufactura()){
            return "SI";
        }else{
            return "NO";
        }
    }
    public String getDescripcionListaPrecio() {
        return descripcionListaPrecio;
    }

    public void setDescripcionListaPrecio(String descripcionListaPrecio) {
        this.descripcionListaPrecio = descripcionListaPrecio;
    }

    public Short getUnoeePortafolio() {
        return unoeePortafolio;
    }

    public void setUnoeePortafolio(Short unoeePortafolio) {
        this.unoeePortafolio = unoeePortafolio;
    }

    public Short getUnoeeReactivado() {
        return unoeeReactivado;
    }

    public void setUnoeeReactivado(Short unoeeReactivado) {
        this.unoeeReactivado = unoeeReactivado;
    }

    public String getCodClienteNit() {
        return codClienteNit;
    }

    public void setCodClienteNit(String codClienteNit) {
        this.codClienteNit = codClienteNit;
    }

    public String getCodClienteNumero() {
        return codClienteNumero;
    }

    public void setCodClienteNumero(String codClienteNumero) {
        this.codClienteNumero = codClienteNumero;
    }

    public String getCodClienteDescripcion() {
        return codClienteDescripcion;
    }

    public void setCodClienteDescripcion(String codClienteDescripcion) {
        this.codClienteDescripcion = codClienteDescripcion;
    }

    public String getNotasComercial() {
        return notasComercial;
    }

    public void setNotasComercial(String notasComercial) {
        this.notasComercial = notasComercial;
    }

    public boolean isIndCompra() {
        return indCompra;
    }

    public void setIndCompra(boolean indCompra) {
        this.indCompra = indCompra;
    }

    public boolean isIndVenta() {
        return indVenta;
    }

    public void setIndVenta(boolean indVenta) {
        this.indVenta = indVenta;
    }

    public boolean isIndManufactura() {
        return indManufactura;
    }

    public void setIndManufactura(boolean indManufactura) {
        this.indManufactura = indManufactura;
    }

    

    public String getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }

    public String getUnidadInventario() {
        return unidadInventario;
    }

    public void setUnidadInventario(String unidadInventario) {
        this.unidadInventario = unidadInventario;
    }

    public String getPosicionArancelaria() {
        return posicionArancelaria;
    }

    public void setPosicionArancelaria(String posicionArancelaria) {
        this.posicionArancelaria = posicionArancelaria;
    }
}
