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
@Cacheable(false)
@Entity
@Table(name = "t029_programador_gantt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "T029ProgramadorGantt.findAll", query = "SELECT t FROM T029ProgramadorGantt t")
    , @NamedQuery(name = "T029ProgramadorGantt.findByIdProgramador", query = "SELECT t FROM T029ProgramadorGantt t WHERE t.idProgramador = :idProgramador")
    , @NamedQuery(name = "T029ProgramadorGantt.findByFecha", query = "SELECT t FROM T029ProgramadorGantt t WHERE t.fecha = :fecha")
    , @NamedQuery(name = "T029ProgramadorGantt.findByDescripcionDia", query = "SELECT t FROM T029ProgramadorGantt t WHERE t.descripcionDia = :descripcionDia")
    , @NamedQuery(name = "T029ProgramadorGantt.findByIdPeriodo", query = "SELECT t FROM T029ProgramadorGantt t WHERE t.idPeriodo.idPeriodo = :idPeriodo ORDER BY t.idCentroTrabajo")
    , @NamedQuery(name = "T029ProgramadorGantt.findByIdPeriodoCentroTrabajo", query = "SELECT t FROM T029ProgramadorGantt t WHERE t.idPeriodo.idPeriodo = :idPeriodo and t.idCentroTrabajo.idCentroTrabajo = :idCentroTrabajo")
    , @NamedQuery(name = "T029ProgramadorGantt.findByIdGantt", query = "SELECT t FROM T029ProgramadorGantt t WHERE t.idCentroTrabajo.idCentroTrabajo = :idCentroTrabajo")
    , @NamedQuery(name = "T029ProgramadorGantt.findByCapacidad", query = "SELECT t FROM T029ProgramadorGantt t WHERE t.capacidad = :capacidad")})
@NamedNativeQueries({
    @NamedNativeQuery(
    name = "execPeriodoProgramadoFecha",
    query = "SELECT * FROM t029_programador_gantt t029 inner join t025_centro_trabajo t025 on t025.id_centro_trabajo = t029.id_centro_trabajo WHERE fecha BETWEEN #fechaInicio AND #fechaFin order by t025.nombre , fecha",
    resultClass = T029ProgramadorGantt.class
    )
})
public class T029ProgramadorGantt implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "capacidad")
    private BigDecimal capacidad;
    @Column(name = "cant_pendiente")
    private BigDecimal cantPendiente;

    @Size(max = 10)
    @Column(name = "color")
    private String color;


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_programador")
    private Integer idProgramador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "descripcion_dia")
    private String descripcionDia;
    @JoinColumn(name = "id_centro_trabajo", referencedColumnName = "id_centro_trabajo")
    @ManyToOne(optional = false)
    private T025CentroTrabajo idCentroTrabajo;
    @JoinColumn(name = "id_configuracion", referencedColumnName = "id_configuracion")
    @ManyToOne(optional = false)
    private T026ConfiguracionCentroTrabajo idConfiguracion;
    @JoinColumn(name = "id_periodo", referencedColumnName = "id_periodo")
    @ManyToOne(optional = false)
    private T027Periodo idPeriodo;

    public T029ProgramadorGantt() {
    }

    public T029ProgramadorGantt(Integer idProgramador) {
        this.idProgramador = idProgramador;
    }

    public T029ProgramadorGantt(Integer idProgramador, Date fecha, String descripcionDia) {
        this.idProgramador = idProgramador;
        this.fecha = fecha;
        this.descripcionDia = descripcionDia;
    }

    public Integer getIdProgramador() {
        return idProgramador;
    }

    public void setIdProgramador(Integer idProgramador) {
        this.idProgramador = idProgramador;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcionDia() {
        return descripcionDia;
    }

    public void setDescripcionDia(String descripcionDia) {
        this.descripcionDia = descripcionDia;
    }


    public T025CentroTrabajo getIdCentroTrabajo() {
        return idCentroTrabajo;
    }

    public void setIdCentroTrabajo(T025CentroTrabajo idCentroTrabajo) {
        this.idCentroTrabajo = idCentroTrabajo;
    }

    public T026ConfiguracionCentroTrabajo getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(T026ConfiguracionCentroTrabajo idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public T027Periodo getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(T027Periodo idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProgramador != null ? idProgramador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof T029ProgramadorGantt)) {
            return false;
        }
        T029ProgramadorGantt other = (T029ProgramadorGantt) object;
        if ((this.idProgramador == null && other.idProgramador != null) || (this.idProgramador != null && !this.idProgramador.equals(other.idProgramador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eurekaintranet.entities.T029ProgramadorGantt[ idProgramador=" + idProgramador + " ]";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(BigDecimal capacidad) {
        this.capacidad = capacidad;
    }

    public BigDecimal getCantPendiente() {
        return cantPendiente;
    }

    public void setCantPendiente(BigDecimal cantPendiente) {
        this.cantPendiente = cantPendiente;
    }
}
