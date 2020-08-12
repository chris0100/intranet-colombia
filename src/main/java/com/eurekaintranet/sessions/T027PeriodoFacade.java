/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T027Periodo;
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
public class T027PeriodoFacade extends AbstractFacade<T027Periodo> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T027PeriodoFacade() {
        super(T027Periodo.class);
    }
    
    public List<T027Periodo> consultaPeriodo(){
        Query query = em.createNamedQuery("T027Periodo.findAll");
        return query.getResultList();
    }
    public T027Periodo consultaPeriodoActual(int mes , int anio){
        Query query = em.createNamedQuery("T027Periodo.findByActual");
        query.setParameter("mes", mes);
        query.setParameter("anio", anio);
        try {
            return (T027Periodo) query.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
    
}
