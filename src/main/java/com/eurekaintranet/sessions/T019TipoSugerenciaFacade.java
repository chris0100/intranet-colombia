/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T019TipoSugerencia;
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
public class T019TipoSugerenciaFacade extends AbstractFacade<T019TipoSugerencia> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T019TipoSugerenciaFacade() {
        super(T019TipoSugerencia.class);
    }
    public List<T019TipoSugerencia> tipoSugerenciaOrdenado(){
        Query query = em.createNamedQuery("T019TipoSugerencia.findAll");
        return query.getResultList();
    }
    public List<String> obtenerCorreoEnvio (String tipo){
        Query query = em.createNamedQuery("execObtenerCorreoEnvio");
        query.setParameter("tipo", tipo);
        return query.getResultList();
    }
}
