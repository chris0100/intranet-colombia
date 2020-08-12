/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T036CapturaParadaGe;
import com.eurekaintranet.entities.T037DetalleParadaGe;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lccabalr
 */
@Stateless
public class T037DetalleParadaGeFacade extends AbstractFacade<T037DetalleParadaGe> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T037DetalleParadaGeFacade() {
        super(T037DetalleParadaGe.class);
    }
    public List<T037DetalleParadaGe> consultarDetalleCaptura(T036CapturaParadaGe idCaptura){
        Query query = em.createNamedQuery("T037DetalleParadaGe.findByCaptura");
        query.setParameter("idCaptura", idCaptura);
        return query.getResultList();
    }
    public List<T037DetalleParadaGe> consultarReporteCaptura(Date fecha1 , Date fecha2){
        Query query = em.createNamedQuery("T037DetalleParadaGe.findByReporteDetalle");
        query.setParameter("fecha1", fecha1);
        query.setParameter("fecha2", fecha2);
        return query.getResultList();
    }
    public T037DetalleParadaGe findByValidarHora(Date horaInicio,Date horaFin){
        Query q= getEntityManager().createNamedQuery("T037DetalleParadaGe.findByValidarHora");
        q.setParameter("horaInicio",horaInicio);
        q.setParameter("horaFin",horaFin);
        try {
            return (T037DetalleParadaGe) q.getSingleResult() ;
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
}
