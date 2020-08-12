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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "t016_periodo_volante_nomina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T016PeriodoVolanteNomina.findAll", query = "SELECT t FROM T016PeriodoVolanteNomina t ORDER BY t.idPeriodo DESC")
    , @NamedQuery(name = "T016PeriodoVolanteNomina.findByIdPeriodo", query = "SELECT t FROM T016PeriodoVolanteNomina t WHERE t.idPeriodo = :idPeriodo")
    , @NamedQuery(name = "T016PeriodoVolanteNomina.findByDia", query = "SELECT t FROM T016PeriodoVolanteNomina t WHERE t.dia = :dia")
    , @NamedQuery(name = "T016PeriodoVolanteNomina.findByMes", query = "SELECT t FROM T016PeriodoVolanteNomina t WHERE t.mes = :mes")
    , @NamedQuery(name = "T016PeriodoVolanteNomina.findByAnio", query = "SELECT t FROM T016PeriodoVolanteNomina t WHERE t.anio = :anio")
    , @NamedQuery(name = "T016PeriodoVolanteNomina.findByUsuarioCreacion", query = "SELECT t FROM T016PeriodoVolanteNomina t WHERE t.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "T016PeriodoVolanteNomina.findByPeriodoDuplicado", query = "SELECT t FROM T016PeriodoVolanteNomina t WHERE t.mes = :mes and t.anio =:anio and t.dia =:dia")
    , @NamedQuery(name = "T016PeriodoVolanteNomina.findByFechaCreacion", query = "SELECT t FROM T016PeriodoVolanteNomina t WHERE t.fechaCreacion = :fechaCreacion")})
public class T016PeriodoVolanteNomina implements Serializable {

    @OneToMany(mappedBy = "idPeriodo")
    private List<T017ErroresVolanteNomina> t017ErroresVolanteNominaList;

    @OneToMany(mappedBy = "idPeriodo")
    private List<T015VolanteNomina> t015VolanteNominaList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_periodo")
    private Integer idPeriodo;
    @Size(max = 2)
    @Column(name = "dia")
    private String dia;
    @Size(max = 20)
    @Column(name = "mes")
    private String mes;
    @Size(max = 4)
    @Column(name = "anio")
    private String anio;
    @Size(max = 80)
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    public T016PeriodoVolanteNomina() {
    }

    public T016PeriodoVolanteNomina(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPeriodo != null ? idPeriodo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T016PeriodoVolanteNomina)) {
            return false;
        }
        T016PeriodoVolanteNomina other = (T016PeriodoVolanteNomina) object;
        if ((this.idPeriodo == null && other.idPeriodo != null) || (this.idPeriodo != null && !this.idPeriodo.equals(other.idPeriodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getMesDescripcion()+" "+getDia()+" del "+getAnio();
    }

    @XmlTransient
    public List<T015VolanteNomina> getT015VolanteNominaList() {
        return t015VolanteNominaList;
    }

    public void setT015VolanteNominaList(List<T015VolanteNomina> t015VolanteNominaList) {
        this.t015VolanteNominaList = t015VolanteNominaList;
    }

    @XmlTransient
    public List<T017ErroresVolanteNomina> getT017ErroresVolanteNominaList() {
        return t017ErroresVolanteNominaList;
    }

    public void setT017ErroresVolanteNominaList(List<T017ErroresVolanteNomina> t017ErroresVolanteNominaList) {
        this.t017ErroresVolanteNominaList = t017ErroresVolanteNominaList;
    }
    public String getMesDescripcion(){
        int mes = Integer.parseInt(getMes());
        switch (mes){
            case 1:
                return "Enero";
            case 2:
                return "Febrero";
            case 3:
                return "Marzo";
            case 4:
                return "Abril";
            case 5:
                return "Mayo";
            case 6:
                return "Junio";
            case 7:
                return "Julio";
            case 8:
                return "Agosto";
            case 9:
                return "Septiembre";
            case 10:
                return "Octubre";
            case 11:
                return "Noviembre";
            case 12:
                return "Diciembre"; 
            default: 
                return " ";   
        }
    }
}
