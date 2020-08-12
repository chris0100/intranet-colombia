/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T035ParadasGe;
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
public class T035ParadasGeFacade extends AbstractFacade<T035ParadasGe> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T035ParadasGeFacade() {
        super(T035ParadasGe.class);
    }
    public List<T035ParadasGe> consultarEstadoParada(boolean estado){
        Query query = em.createNamedQuery("T035ParadasGe.findByEstado");
        query.setParameter("estado", estado);
        return query.getResultList();
    }
}
