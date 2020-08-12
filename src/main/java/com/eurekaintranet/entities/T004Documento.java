/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;

import java.io.FileNotFoundException;
import java.io.Serializable;
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
import org.primefaces.model.StreamedContent;

/**
 *
 * @author junio
 */
@Entity
@Table(name = "t004_documento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T004Documento.findAll", query = "SELECT t FROM T004Documento t")
    , @NamedQuery(name = "T004Documento.findByIdDocumento", query = "SELECT t FROM T004Documento t WHERE t.idDocumento = :idDocumento")
    , @NamedQuery(name = "T004Documento.findByCodigo", query = "SELECT t FROM T004Documento t WHERE t.codigo = :codigo")
    , @NamedQuery(name = "T004Documento.findByNombre", query = "SELECT t FROM T004Documento t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "T004Documento.findByNombreProceso", query = "SELECT t FROM T004Documento t WHERE t.nombre = :nombre and t.idProceso = :idProceso")
    , @NamedQuery(name = "T004Documento.findByFecha", query = "SELECT t FROM T004Documento t WHERE t.fecha = :fecha")
    , @NamedQuery(name = "T004Documento.findByEstado", query = "SELECT t FROM T004Documento t WHERE t.estado = :estado")
    , @NamedQuery(name = "T004Documento.findByHistorial", query = "SELECT t FROM T004Documento t WHERE t.codigo = :codigo")
    , @NamedQuery(name = "T004Documento.findByConsecutivo", query = "SELECT t FROM T004Documento t WHERE t.idProceso = :idProceso")
    , @NamedQuery(name = "T004Documento.findByProcesoDoc", query = "SELECT t FROM T004Documento t WHERE t.idProceso = :idProceso AND t.idTipoDocumento != :idTipoDocumento AND t.estado = :estado")
    , @NamedQuery(name = "T004Documento.findByProcesoFto", query = "SELECT t FROM T004Documento t WHERE t.idProceso = :idProceso AND t.idTipoDocumento = :idTipoDocumento AND t.estado = :estado")
    , @NamedQuery(name = "T004Documento.findByUbicacionPdf", query = "SELECT t FROM T004Documento t WHERE t.ubicacionPdf = :ubicacionPdf")
    , @NamedQuery(name = "T004Documento.findByUbicacionDoc", query = "SELECT t FROM T004Documento t WHERE t.ubicacionDoc = :ubicacionDoc")
    , @NamedQuery(name = "T004Documento.findByTipo", query = "SELECT t FROM T004Documento t WHERE t.idTipoDocumento = :idTipoDocumento")
    , @NamedQuery(name = "T004Documento.findByFormatoProcedimiento", query = "SELECT t FROM T004Documento t WHERE t.idTipoDocumento != :idTipoDocumento")
    , @NamedQuery(name = "T004Documento.findByProceso", query = "SELECT t FROM T004Documento t WHERE t.idProceso = :idProceso")
    , @NamedQuery(name = "T004Documento.findByProcesoTipoDocumento", query = "SELECT t FROM T004Documento t WHERE t.idProceso = :idProceso AND t.idTipoDocumento = :idTipoDocumento AND t.estado = 'VIGENTE'")
    , @NamedQuery(name = "T004Documento.findByTipoDocumentoUser", query = "SELECT t FROM T004Documento t WHERE  t.idTipoDocumento != :idTipoDocumento AND t.estado = 'VIGENTE'")
    , @NamedQuery(name = "T004Documento.findByTipoFormatoUser", query = "SELECT t FROM T004Documento t WHERE  t.idTipoDocumento = :idTipoDocumento AND t.estado = 'VIGENTE'")
    , @NamedQuery(name = "T004Documento.findByVersion", query = "SELECT t FROM T004Documento t WHERE t.version = :version")})
@NamedNativeQueries({
    @NamedNativeQuery(
    name = "execTopDocumento",
    query = "SELECT top 5 id_documento,[codigo] ,[nombre] , sum(contar_vista) contar_vista , [fecha_ultima_vista] FROM [t004_documento] where estado = 'VIGENTE' GROUP BY id_documento,[codigo] ,[nombre] , [fecha_ultima_vista] ORDER BY sum(contar_vista) DESC",
    resultClass = T004Documento.class
    ) 
})
public class T004Documento implements Serializable {

    @Column(name = "fecha_ultima_vista")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimaVista;

    @Column(name = "contar_vista")
    private Integer contarVista;

    @Size(max = 3)
    @Column(name = "consecutivo")
    private String consecutivo;

    @OneToMany(mappedBy = "procedimiento")
    private List<T004Documento> t004DocumentoList;
    @JoinColumn(name = "procedimiento", referencedColumnName = "id_documento")
    @ManyToOne
    private T004Documento procedimiento;

    @Size(max = 1073741823)
    @Column(name = "descripcion")
    private String descripcion;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_documento")
    private Integer idDocumento;
    @Size(min = 1, max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 150)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 50)
    @Column(name = "estado")
    private String estado;
    @Size(max = 150)
    @Column(name = "ubicacion_pdf")
    private String ubicacionPdf;
    @Size(max = 150)
    @Column(name = "ubicacion_doc")
    private String ubicacionDoc;
    @Size(max = 50)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "version")
    private Integer version;
    @JoinColumn(name = "id_proceso", referencedColumnName = "id_proceso")
    @ManyToOne
    private T005Proceso idProceso;
    @JoinColumn(name = "id_tipo_documento", referencedColumnName = "id_tipo_documento")
    @ManyToOne
    private T006TipoDocumento idTipoDocumento;
    
    
    
    
    public T004Documento() {
    }

    public T004Documento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUbicacionPdf() {
        return ubicacionPdf;
    }

    public void setUbicacionPdf(String ubicacionPdf) {
        this.ubicacionPdf = ubicacionPdf;
    }

    public String getUbicacionDoc() {
        return ubicacionDoc;
    }

    public void setUbicacionDoc(String ubicacionDoc) {
        this.ubicacionDoc = ubicacionDoc;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public T005Proceso getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(T005Proceso idProceso) {
        this.idProceso = idProceso;
    }

    public T006TipoDocumento getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(T006TipoDocumento idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocumento != null ? idDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T004Documento)) {
            return false;
        }
        T004Documento other = (T004Documento) object;
        if ((this.idDocumento == null && other.idDocumento != null) || (this.idDocumento != null && !this.idDocumento.equals(other.idDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getCodigo()+" - "+getNombre().toUpperCase();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<T004Documento> getT004DocumentoList() {
        return t004DocumentoList;
    }

    public void setT004DocumentoList(List<T004Documento> t004DocumentoList) {
        this.t004DocumentoList = t004DocumentoList;
    }

    public T004Documento getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(T004Documento procedimiento) {
        this.procedimiento = procedimiento;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Integer getContarVista() {
        return contarVista;
    }

    public void setContarVista(Integer contarVista) {
        this.contarVista = contarVista;
    }

    public Date getFechaUltimaVista() {
        return fechaUltimaVista;
    }

    public void setFechaUltimaVista(Date fechaUltimaVista) {
        this.fechaUltimaVista = fechaUltimaVista;
    }
}
