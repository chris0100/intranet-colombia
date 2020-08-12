/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T0231ConfigImpresoraLabel;
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
public class T0231ConfigImpresoraLabelFacade extends AbstractFacade<T0231ConfigImpresoraLabel> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T0231ConfigImpresoraLabelFacade() {
        super(T0231ConfigImpresoraLabel.class);
    }
    public T0231ConfigImpresoraLabel findByEquipoAsignado(String query){
          Query q= getEntityManager().createNamedQuery("T0231ConfigImpresoraLabel.findByNombreEquipo");
          q.setParameter("nombreEquipo",query);
          try {
            return (T0231ConfigImpresoraLabel) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
    
    public List<T0231ConfigImpresoraLabel> obtenerEquipoAsignado(String query){
        Query q= getEntityManager().createNamedQuery("T0231ConfigImpresoraLabel.findByNombreEquipo");
        q.setParameter("nombreEquipo",query);
        return q.getResultList();
    }
    
}
