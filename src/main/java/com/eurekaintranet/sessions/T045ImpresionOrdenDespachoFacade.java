/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T045ImpresionOrdenDespacho;
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
public class T045ImpresionOrdenDespachoFacade extends AbstractFacade<T045ImpresionOrdenDespacho> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T045ImpresionOrdenDespachoFacade() {
        super(T045ImpresionOrdenDespacho.class);
    }
    
     public T045ImpresionOrdenDespacho consultaUltimaImpresion(int idMovimiento ){
        Query q = em.createNamedQuery("execObtenerUltimaImpresion");
        q.setParameter("idMovimiento", idMovimiento);
        try {
            if(q.getSingleResult()==null){
                return null;  
            }else{
                return (T045ImpresionOrdenDespacho) q.getSingleResult();
            }
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
    public List<T045ImpresionOrdenDespacho> consultaHistorialImpresion(int idMovimiento){
        Query q= getEntityManager().createNamedQuery("T045ImpresionOrdenDespacho.findByIdMovimiento");
        q.setParameter("idMovimiento", idMovimiento);
        return q.getResultList();
    }
    
}
