/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.eurekaintranet.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author LUIS CARLOS CABAL ROJAS
 */
@Entity
@Table(name = "t035_paradas_ge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T035ParadasGe.findAll", query = "SELECT t FROM T035ParadasGe t")
    , @NamedQuery(name = "T035ParadasGe.findByIdParada", query = "SELECT t FROM T035ParadasGe t WHERE t.idParada = :idParada")
    , @NamedQuery(name = "T035ParadasGe.findByCodigo", query = "SELECT t FROM T035ParadasGe t WHERE t.codigo = :codigo")
    , @NamedQuery(name = "T035ParadasGe.findByDescripcion", query = "SELECT t FROM T035ParadasGe t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "T035ParadasGe.findByUrdido", query = "SELECT t FROM T035ParadasGe t WHERE t.urdido = :urdido")
    , @NamedQuery(name = "T035ParadasGe.findBySatinadas", query = "SELECT t FROM T035ParadasGe t WHERE t.satinadas = :satinadas")
    , @NamedQuery(name = "T035ParadasGe.findByReata", query = "SELECT t FROM T035ParadasGe t WHERE t.reata = :reata")
    , @NamedQuery(name = "T035ParadasGe.findByCordones", query = "SELECT t FROM T035ParadasGe t WHERE t.cordones = :cordones")
    , @NamedQuery(name = "T035ParadasGe.findByContinuas", query = "SELECT t FROM T035ParadasGe t WHERE t.continuas = :continuas")
    , @NamedQuery(name = "T035ParadasGe.findByAutoclaves", query = "SELECT t FROM T035ParadasGe t WHERE t.autoclaves = :autoclaves")
    , @NamedQuery(name = "T035ParadasGe.findByComplementarios", query = "SELECT t FROM T035ParadasGe t WHERE t.complementarios = :complementarios")
    , @NamedQuery(name = "T035ParadasGe.findByEmpaque", query = "SELECT t FROM T035ParadasGe t WHERE t.empaque = :empaque")
    , @NamedQuery(name = "T035ParadasGe.findByCadena", query = "SELECT t FROM T035ParadasGe t WHERE t.cadena = :cadena")
    , @NamedQuery(name = "T035ParadasGe.findByMetalicasFijas", query = "SELECT t FROM T035ParadasGe t WHERE t.metalicasFijas = :metalicasFijas")
    , @NamedQuery(name = "T035ParadasGe.findByMetalicasSeparable", query = "SELECT t FROM T035ParadasGe t WHERE t.metalicasSeparable = :metalicasSeparable")
    , @NamedQuery(name = "T035ParadasGe.findByPoliesterInvisible", query = "SELECT t FROM T035ParadasGe t WHERE t.poliesterInvisible = :poliesterInvisible")
    , @NamedQuery(name = "T035ParadasGe.findByPoliester3", query = "SELECT t FROM T035ParadasGe t WHERE t.poliester3 = :poliester3")
    , @NamedQuery(name = "T035ParadasGe.findByPoliesterSep4", query = "SELECT t FROM T035ParadasGe t WHERE t.poliesterSep4 = :poliesterSep4")
    , @NamedQuery(name = "T035ParadasGe.findByPoliesterSep6", query = "SELECT t FROM T035ParadasGe t WHERE t.poliesterSep6 = :poliesterSep6")
    , @NamedQuery(name = "T035ParadasGe.findByTroquelado", query = "SELECT t FROM T035ParadasGe t WHERE t.troquelado = :troquelado")
    , @NamedQuery(name = "T035ParadasGe.findByEnsamble", query = "SELECT t FROM T035ParadasGe t WHERE t.ensamble = :ensamble")
    , @NamedQuery(name = "T035ParadasGe.findByGalvano", query = "SELECT t FROM T035ParadasGe t WHERE t.galvano = :galvano")
    , @NamedQuery(name = "T035ParadasGe.findByEmpaqueDesliz", query = "SELECT t FROM T035ParadasGe t WHERE t.empaqueDesliz = :empaqueDesliz")
    , @NamedQuery(name = "T035ParadasGe.findByEpoxica", query = "SELECT t FROM T035ParadasGe t WHERE t.epoxica = :epoxica")
    , @NamedQuery(name = "T035ParadasGe.findByEstado", query = "SELECT t FROM T035ParadasGe t WHERE t.estado = :estado")})
public class T035ParadasGe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parada")
    private Integer idParada;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "urdido")
    private Boolean urdido;
    @Column(name = "satinadas")
    private Boolean satinadas;
    @Column(name = "reata")
    private Boolean reata;
    @Column(name = "cordones")
    private Boolean cordones;
    @Column(name = "continuas")
    private Boolean continuas;
    @Column(name = "autoclaves")
    private Boolean autoclaves;
    @Column(name = "complementarios")
    private Boolean complementarios;
    @Column(name = "empaque")
    private Boolean empaque;
    @Column(name = "cadena")
    private Boolean cadena;
    @Column(name = "metalicas_fijas")
    private Boolean metalicasFijas;
    @Column(name = "metalicas_separable")
    private Boolean metalicasSeparable;
    @Column(name = "poliester_invisible")
    private Boolean poliesterInvisible;
    @Column(name = "poliester_3")
    private Boolean poliester3;
    @Column(name = "poliester_sep_4")
    private Boolean poliesterSep4;
    @Column(name = "poliester_sep_6")
    private Boolean poliesterSep6;
    @Column(name = "troquelado")
    private Boolean troquelado;
    @Column(name = "ensamble")
    private Boolean ensamble;
    @Column(name = "galvano")
    private Boolean galvano;
    @Column(name = "empaque_desliz")
    private Boolean empaqueDesliz;
    @Column(name = "epoxica")
    private Boolean epoxica;
    @JoinColumn(name = "id_esquema", referencedColumnName = "id_esquema")
    @ManyToOne(optional = false)
    private T034EsquemasGe idEsquema;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    @ManyToOne
    private T038CategoriaParadaGe idCategoria;
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
    public T035ParadasGe() {
    }

    public T035ParadasGe(Integer idParada) {
        this.idParada = idParada;
    }

    public T035ParadasGe(Integer idParada, String codigo, String descripcion) {
        this.idParada = idParada;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Integer getIdParada() {
        return idParada;
    }

    public void setIdParada(Integer idParada) {
        this.idParada = idParada;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getUrdido() {
        return urdido;
    }

    public void setUrdido(Boolean urdido) {
        this.urdido = urdido;
    }

    public Boolean getSatinadas() {
        return satinadas;
    }

    public void setSatinadas(Boolean satinadas) {
        this.satinadas = satinadas;
    }

    public Boolean getReata() {
        return reata;
    }

    public void setReata(Boolean reata) {
        this.reata = reata;
    }

    public Boolean getCordones() {
        return cordones;
    }

    public void setCordones(Boolean cordones) {
        this.cordones = cordones;
    }

    public Boolean getContinuas() {
        return continuas;
    }

    public void setContinuas(Boolean continuas) {
        this.continuas = continuas;
    }

    public Boolean getAutoclaves() {
        return autoclaves;
    }

    public void setAutoclaves(Boolean autoclaves) {
        this.autoclaves = autoclaves;
    }

    public Boolean getComplementarios() {
        return complementarios;
    }

    public void setComplementarios(Boolean complementarios) {
        this.complementarios = complementarios;
    }

    public Boolean getEmpaque() {
        return empaque;
    }

    public void setEmpaque(Boolean empaque) {
        this.empaque = empaque;
    }

    public Boolean getCadena() {
        return cadena;
    }

    public void setCadena(Boolean cadena) {
        this.cadena = cadena;
    }

    public Boolean getMetalicasFijas() {
        return metalicasFijas;
    }

    public void setMetalicasFijas(Boolean metalicasFijas) {
        this.metalicasFijas = metalicasFijas;
    }

    public Boolean getMetalicasSeparable() {
        return metalicasSeparable;
    }

    public void setMetalicasSeparable(Boolean metalicasSeparable) {
        this.metalicasSeparable = metalicasSeparable;
    }

    public Boolean getPoliesterInvisible() {
        return poliesterInvisible;
    }

    public void setPoliesterInvisible(Boolean poliesterInvisible) {
        this.poliesterInvisible = poliesterInvisible;
    }

    public Boolean getPoliester3() {
        return poliester3;
    }

    public void setPoliester3(Boolean poliester3) {
        this.poliester3 = poliester3;
    }

    public Boolean getPoliesterSep4() {
        return poliesterSep4;
    }

    public void setPoliesterSep4(Boolean poliesterSep4) {
        this.poliesterSep4 = poliesterSep4;
    }

    public Boolean getPoliesterSep6() {
        return poliesterSep6;
    }

    public void setPoliesterSep6(Boolean poliesterSep6) {
        this.poliesterSep6 = poliesterSep6;
    }

    public Boolean getTroquelado() {
        return troquelado;
    }

    public void setTroquelado(Boolean troquelado) {
        this.troquelado = troquelado;
    }

    public Boolean getEnsamble() {
        return ensamble;
    }

    public void setEnsamble(Boolean ensamble) {
        this.ensamble = ensamble;
    }

    public Boolean getGalvano() {
        return galvano;
    }

    public void setGalvano(Boolean galvano) {
        this.galvano = galvano;
    }

    public Boolean getEmpaqueDesliz() {
        return empaqueDesliz;
    }

    public void setEmpaqueDesliz(Boolean empaqueDesliz) {
        this.empaqueDesliz = empaqueDesliz;
    }

    public Boolean getEpoxica() {
        return epoxica;
    }

    public void setEpoxica(Boolean epoxica) {
        this.epoxica = epoxica;
    }

    public T034EsquemasGe getIdEsquema() {
        return idEsquema;
    }

    public void setIdEsquema(T034EsquemasGe idEsquema) {
        this.idEsquema = idEsquema;
    }

    public T038CategoriaParadaGe getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(T038CategoriaParadaGe idCategoria) {
        this.idCategoria = idCategoria;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParada != null ? idParada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T035ParadasGe)) {
            return false;
        }
        T035ParadasGe other = (T035ParadasGe) object;
        if ((this.idParada == null && other.idParada != null) || (this.idParada != null && !this.idParada.equals(other.idParada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getCodigo().toUpperCase()+" - "+getDescripcion().toUpperCase();
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

}
