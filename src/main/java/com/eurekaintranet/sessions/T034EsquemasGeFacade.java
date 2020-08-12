/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T034EsquemasGe;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lccabalr
 */
@Stateless
public class T034EsquemasGeFacade extends AbstractFacade<T034EsquemasGe> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T034EsquemasGeFacade() {
        super(T034EsquemasGe.class);
    }
    
}
