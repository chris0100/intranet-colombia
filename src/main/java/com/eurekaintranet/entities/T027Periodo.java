/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lccabalr
 */
@Entity
@Table(name = "t027_periodo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T027Periodo.findAll", query = "SELECT t FROM T027Periodo t ORDER BY t.anio, t.mes DESC")
    , @NamedQuery(name = "T027Periodo.findByIdPeriodo", query = "SELECT t FROM T027Periodo t WHERE t.idPeriodo = :idPeriodo")
    , @NamedQuery(name = "T027Periodo.findByMes", query = "SELECT t FROM T027Periodo t WHERE t.mes = :mes")
    , @NamedQuery(name = "T027Periodo.findByActual", query = "SELECT t FROM T027Periodo t WHERE t.mes = :mes and t.anio = :anio")
    , @NamedQuery(name = "T027Periodo.findByAnio", query = "SELECT t FROM T027Periodo t WHERE t.anio = :anio")})
public class T027Periodo implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "mes")
    private int mes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private int anio;

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_periodo")
    private Integer idPeriodo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPeriodo")
    private List<T029ProgramadorGantt> t029ProgramadorGanttList;

    public T027Periodo() {
    }

    public T027Periodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public T027Periodo(Integer idPeriodo, int mes, int anio) {
        this.idPeriodo = idPeriodo;
        this.mes = mes;
        this.anio = anio;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }


    @XmlTransient
    public List<T029ProgramadorGantt> getT029ProgramadorGanttList() {
        return t029ProgramadorGanttList;
    }

    public void setT029ProgramadorGanttList(List<T029ProgramadorGantt> t029ProgramadorGanttList) {
        this.t029ProgramadorGanttList = t029ProgramadorGanttList;
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
        if (!(object instanceof T027Periodo)) {
            return false;
        }
        T027Periodo other = (T027Periodo) object;
        if ((this.idPeriodo == null && other.idPeriodo != null) || (this.idPeriodo != null && !this.idPeriodo.equals(other.idPeriodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getMes()+"/"+getAnio();
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
    
}
