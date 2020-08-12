/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T046ConfigOrdenDespacho;
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
public class T046ConfigOrdenDespachoFacade extends AbstractFacade<T046ConfigOrdenDespacho> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T046ConfigOrdenDespachoFacade() {
        super(T046ConfigOrdenDespacho.class);
    }
    public T046ConfigOrdenDespacho findByTipoDocumento(String query){
          Query q= getEntityManager().createNamedQuery("T046ConfigOrdenDespacho.findByValor");
          q.setParameter("valor",query);
          try {
            return (T046ConfigOrdenDespacho) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
    public T046ConfigOrdenDespacho findByTope(String query){
          Query q= getEntityManager().createNamedQuery("T046ConfigOrdenDespacho.findByTipo");
          q.setParameter("tipo",query);
          try {
            return (T046ConfigOrdenDespacho) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
    
    public List<T046ConfigOrdenDespacho> consultarDocumentos(String query){
        Query q= getEntityManager().createNamedQuery("T046ConfigOrdenDespacho.findByTipo");
        q.setParameter("tipo",query);
        return q.getResultList();
    }
}
