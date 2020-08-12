/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T029ProgramadorGantt;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lccabalr
 */
@Stateless
public class T029ProgramadorGanttFacade extends AbstractFacade<T029ProgramadorGantt> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T029ProgramadorGanttFacade() {
        super(T029ProgramadorGantt.class);
    }
    public List<T029ProgramadorGantt> periodoProgramado(int idPeriodo){
        Query query = em.createNamedQuery("T029ProgramadorGantt.findByIdPeriodo");
        query.setParameter("idPeriodo", idPeriodo);
        return query.getResultList();
    }
    public List<T029ProgramadorGantt> periodoProgramadoCentroTrabajo(int idPeriodo, String idCentroTrabajo){
        Query query = em.createNamedQuery("T029ProgramadorGantt.findByIdPeriodoCentroTrabajo");
        query.setParameter("idPeriodo", idPeriodo);
        query.setParameter("idCentroTrabajo", idCentroTrabajo);
        return query.getResultList();
    }
    public List<T029ProgramadorGantt> periodoProgramadoGantt(String idCentroTrabajo){
        Query query = em.createNamedQuery("T029ProgramadorGantt.findByIdGantt");
        query.setParameter("idCentroTrabajo", idCentroTrabajo);
        return query.getResultList();
    }
    public List<T029ProgramadorGantt> periodoProgramadoFecha (String fechaInicio, String fechaFin){
        Query query = em.createNamedQuery("execPeriodoProgramadoFecha");
        query.setParameter("fechaInicio",fechaInicio);
        query.setParameter("fechaFin",fechaFin);
        return query.getResultList(); 
    } 
}
