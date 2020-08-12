/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T032TurnosGe;
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
public class T032TurnosGeFacade extends AbstractFacade<T032TurnosGe> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T032TurnosGeFacade() {
        super(T032TurnosGe.class);
    }
    public List<T032TurnosGe> consultarEstadoTurno(boolean estado){
        Query query = em.createNamedQuery("T032TurnosGe.findByEstado");
        query.setParameter("estado", estado);
        return query.getResultList();
    }
}
