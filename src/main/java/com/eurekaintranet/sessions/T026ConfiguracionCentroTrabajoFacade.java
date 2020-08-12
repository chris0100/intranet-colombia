/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T026ConfiguracionCentroTrabajo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lccabalr
 */
@Stateless
public class T026ConfiguracionCentroTrabajoFacade extends AbstractFacade<T026ConfiguracionCentroTrabajo> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T026ConfiguracionCentroTrabajoFacade() {
        super(T026ConfiguracionCentroTrabajo.class);
    }
    
}
