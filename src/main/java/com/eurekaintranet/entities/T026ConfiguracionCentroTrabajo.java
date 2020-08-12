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
@Table(name = "t026_configuracion_centro_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T026ConfiguracionCentroTrabajo.findAll", query = "SELECT t FROM T026ConfiguracionCentroTrabajo t")
    , @NamedQuery(name = "T026ConfiguracionCentroTrabajo.findByIdConfiguracion", query = "SELECT t FROM T026ConfiguracionCentroTrabajo t WHERE t.idConfiguracion = :idConfiguracion")
    , @NamedQuery(name = "T026ConfiguracionCentroTrabajo.findByNombre", query = "SELECT t FROM T026ConfiguracionCentroTrabajo t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "T026ConfiguracionCentroTrabajo.findByNumeroTurnos", query = "SELECT t FROM T026ConfiguracionCentroTrabajo t WHERE t.numeroTurnos = :numeroTurnos")
    , @NamedQuery(name = "T026ConfiguracionCentroTrabajo.findByUndHora", query = "SELECT t FROM T026ConfiguracionCentroTrabajo t WHERE t.undHora = :undHora")
    , @NamedQuery(name = "T026ConfiguracionCentroTrabajo.findByHoraTurno", query = "SELECT t FROM T026ConfiguracionCentroTrabajo t WHERE t.horaTurno = :horaTurno")
    , @NamedQuery(name = "T026ConfiguracionCentroTrabajo.findByCargaProyectada", query = "SELECT t FROM T026ConfiguracionCentroTrabajo t WHERE t.cargaProyectada = :cargaProyectada")
    , @NamedQuery(name = "T026ConfiguracionCentroTrabajo.findByNumeroMaquinas", query = "SELECT t FROM T026ConfiguracionCentroTrabajo t WHERE t.numeroMaquinas = :numeroMaquinas")})
public class T026ConfiguracionCentroTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_configuracion")
    private Integer idConfiguracion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_turnos")
    private int numeroTurnos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "und_hora")
    private int undHora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_turno")
    private int horaTurno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "carga_proyectada")
    private int cargaProyectada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_maquinas")
    private int numeroMaquinas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idConfiguracion")
    private List<T029ProgramadorGantt> t029ProgramadorGanttList;

    public T026ConfiguracionCentroTrabajo() {
    }

    public T026ConfiguracionCentroTrabajo(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public T026ConfiguracionCentroTrabajo(Integer idConfiguracion, String nombre, int numeroTurnos, int undHora, int horaTurno, int cargaProyectada, int numeroMaquinas) {
        this.idConfiguracion = idConfiguracion;
        this.nombre = nombre;
        this.numeroTurnos = numeroTurnos;
        this.undHora = undHora;
        this.horaTurno = horaTurno;
        this.cargaProyectada = cargaProyectada;
        this.numeroMaquinas = numeroMaquinas;
    }

    public Integer getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroTurnos() {
        return numeroTurnos;
    }

    public void setNumeroTurnos(int numeroTurnos) {
        this.numeroTurnos = numeroTurnos;
    }

    public int getUndHora() {
        return undHora;
    }

    public void setUndHora(int undHora) {
        this.undHora = undHora;
    }

    public int getHoraTurno() {
        return horaTurno;
    }

    public void setHoraTurno(int horaTurno) {
        this.horaTurno = horaTurno;
    }

    public int getCargaProyectada() {
        return cargaProyectada;
    }

    public void setCargaProyectada(int cargaProyectada) {
        this.cargaProyectada = cargaProyectada;
    }

    public int getNumeroMaquinas() {
        return numeroMaquinas;
    }

    public void setNumeroMaquinas(int numeroMaquinas) {
        this.numeroMaquinas = numeroMaquinas;
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
        hash += (idConfiguracion != null ? idConfiguracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T026ConfiguracionCentroTrabajo)) {
            return false;
        }
        T026ConfiguracionCentroTrabajo other = (T026ConfiguracionCentroTrabajo) object;
        if ((this.idConfiguracion == null && other.idConfiguracion != null) || (this.idConfiguracion != null && !this.idConfiguracion.equals(other.idConfiguracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Nombre: "+getNombre() +" - Carga proyectada: "+getCargaProyectada();
    }
    
}
