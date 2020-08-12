/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T033NominalGe;
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
public class T033NominalGeFacade extends AbstractFacade<T033NominalGe> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T033NominalGeFacade() {
        super(T033NominalGe.class);
    }
    public List<String> consultarCodMaquina(){
        Query q= getEntityManager().createNamedQuery("execCodMaquina");
        return q.getResultList();
    }
    public List<String> consultarDescMaquina(){
        Query q= getEntityManager().createNamedQuery("execDescMaquina");
        return q.getResultList();
    }
    public List<String> consultarHomologadoMaquina(){
        Query q= getEntityManager().createNamedQuery("execDescHomologado");
        return q.getResultList();
    }
    public List<T033NominalGe> consultarEstadoNominal(boolean estado){
        Query query = em.createNamedQuery("T033NominalGe.findByEstado");
        query.setParameter("estado", estado);
        return query.getResultList();
    }
}
