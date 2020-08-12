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
@Entity
@Table(name = "t007_contenido")
@XmlRootElement
@NamedQueries({
@NamedQuery(name = "T007Contenido.findAll", query = "SELECT t FROM T007Contenido t")
    , @NamedQuery(name = "T007Contenido.findAllFooter", query = "SELECT t FROM T007Contenido t ORDER BY t.fecha DESC")
    , @NamedQuery(name = "T007Contenido.findByIdContenido", query = "SELECT t FROM T007Contenido t WHERE t.idContenido = :idContenido")
    , @NamedQuery(name = "T007Contenido.findByTitulo", query = "SELECT t FROM T007Contenido t WHERE t.titulo = :titulo")
    , @NamedQuery(name = "T007Contenido.findByTipo", query = "SELECT t FROM T007Contenido t WHERE t.tipo.idTipo = :tipo ORDER BY t.fecha DESC")
    , @NamedQuery(name = "T007Contenido.findByDescripcion", query = "SELECT t FROM T007Contenido t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "T007Contenido.findByFecha", query = "SELECT t FROM T007Contenido t WHERE t.fecha = :fecha")
    , @NamedQuery(name = "T007Contenido.findByImg1", query = "SELECT t FROM T007Contenido t WHERE t.img1 = :img1")
    , @NamedQuery(name = "T007Contenido.findByImg2", query = "SELECT t FROM T007Contenido t WHERE t.img2 = :img2")
    , @NamedQuery(name = "T007Contenido.findByImg3", query = "SELECT t FROM T007Contenido t WHERE t.img3 = :img3")
    , @NamedQuery(name = "T007Contenido.findByImg4", query = "SELECT t FROM T007Contenido t WHERE t.img4 = :img4")
    , @NamedQuery(name = "T007Contenido.findByVideo", query = "SELECT t FROM T007Contenido t WHERE t.video = :video")})
@NamedNativeQueries({
    @NamedNativeQuery(
    name = "execContarContenidos",
    query = "select top 4 sum(contar_vista) contar_vista from t007_contenido"
    ) 
})
public class T007Contenido implements Serializable {

    @Column(name = "contar_vista")
    private Integer contarVista;
    @Column(name = "fecha_ultima_vista")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimaVista;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contenido")
    private Integer idContenido;
    @Size(max = 150)
    @Column(name = "titulo")
    private String titulo;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 200)
    @Column(name = "img1")
    private String img1;
    @Size(max = 200)
    @Column(name = "img2")
    private String img2;
    @Size(max = 200)
    @Column(name = "img3")
    private String img3;
    @Size(max = 200)
    @Column(name = "img4")
    private String img4;
    @Size(max = 200)
    @Column(name = "video")
    private String video;
    @Size(max = 2147483647)
    @Column(name = "descripcion2")
    private String descripcion2;
    @Size(max = 2147483647)
    @Column(name = "descripcion3")
    private String descripcion3;
    @Size(max = 2147483647)
    @Column(name = "descripcion4")
    private String descripcion4;
    @Size(max = 150)
    @Column(name = "descripcion5")
    private String descripcion5;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private T001Usuario idUsuario;
    @JoinColumn(name = "tipo", referencedColumnName = "id_tipo")
    @ManyToOne
    private T008TipoContenido tipo;

    public T007Contenido() {
    }

    public T007Contenido(Integer idContenido) {
        this.idContenido = idContenido;
    }

    public T007Contenido(Integer idContenido, Date fecha) {
        this.idContenido = idContenido;
        this.fecha = fecha;
    }

    public Integer getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(Integer idContenido) {
        this.idContenido = idContenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion2(String descripcion2) {
        this.descripcion2 = descripcion2;
    }

    public String getDescripcion3() {
        return descripcion3;
    }

    public void setDescripcion3(String descripcion3) {
        this.descripcion3 = descripcion3;
    }

    public String getDescripcion4() {
        return descripcion4;
    }

    public void setDescripcion4(String descripcion4) {
        this.descripcion4 = descripcion4;
    }

    public String getDescripcion5() {
        return descripcion5;
    }

    public void setDescripcion5(String descripcion5) {
        this.descripcion5 = descripcion5;
    }

    public T001Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(T001Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public T008TipoContenido getTipo() {
        return tipo;
    }

    public void setTipo(T008TipoContenido tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContenido != null ? idContenido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T007Contenido)) {
            return false;
        }
        T007Contenido other = (T007Contenido) object;
        if ((this.idContenido == null && other.idContenido != null) || (this.idContenido != null && !this.idContenido.equals(other.idContenido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T007Contenido[ idContenido=" + idContenido + " ]";
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
