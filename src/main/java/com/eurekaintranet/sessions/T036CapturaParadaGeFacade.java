/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T036CapturaParadaGe;
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
public class T036CapturaParadaGeFacade extends AbstractFacade<T036CapturaParadaGe> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T036CapturaParadaGeFacade() {
        super(T036CapturaParadaGe.class);
    }
     public List<String> consultaListaOp (){
        Query query = em.createNamedQuery("execListaOp");
        return query.getResultList(); 
    } 
    public List<T036CapturaParadaGe> consultarCapturas(){
        Query query = em.createNamedQuery("T036CapturaParadaGe.findAll");
        return query.getResultList();
    }
    public List<T036CapturaParadaGe> consultarReporteCaptura(Date fecha1 , Date fecha2){
        Query query = em.createNamedQuery("T036CapturaParadaGe.findByFechaCaptura");
        query.setParameter("fecha1", fecha1);
        query.setParameter("fecha2", fecha2);
        return query.getResultList();
    }
    public T036CapturaParadaGe findByValidarFechaCaptura(Date horaInicio, Date horaFinal){
          Query q= getEntityManager().createNamedQuery("T036CapturaParadaGe.findByValidarHoraCaptura");
          q.setParameter("horaInicio",horaInicio);
          q.setParameter("horaFinal",horaFinal);
          try {
            return (T036CapturaParadaGe) q.getSingleResult() ;
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
}
