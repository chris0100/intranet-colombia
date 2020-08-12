/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T016PeriodoVolanteNomina;
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
public class T016PeriodoVolanteNominaFacade extends AbstractFacade<T016PeriodoVolanteNomina> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T016PeriodoVolanteNominaFacade() {
        super(T016PeriodoVolanteNomina.class);
    }
    public List<T016PeriodoVolanteNomina> consultaListaPeriodo (){
        Query query = em.createNamedQuery("T016PeriodoVolanteNomina.findAll");
        return query.getResultList();
    } 
    
    public T016PeriodoVolanteNomina findByPeriodoDuplicado(String anio, String mes, String dia){
          Query q= getEntityManager().createNamedQuery("T016PeriodoVolanteNomina.findByPeriodoDuplicado");
          q.setParameter("anio",anio);
          q.setParameter("mes",mes);
          q.setParameter("dia",dia);
          try {
            return (T016PeriodoVolanteNomina) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
}
