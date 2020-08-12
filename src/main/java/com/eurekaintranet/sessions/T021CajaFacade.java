/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T020ListaEmpaque;
import com.eurekaintranet.entities.T021Caja;
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
public class T021CajaFacade extends AbstractFacade<T021Caja> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T021CajaFacade() {
        super(T021Caja.class);
    }
    public List<T021Caja> cajaListaEmpaque(T020ListaEmpaque idListaEmpaque){
        Query query = em.createNamedQuery("T021Caja.findByIdListaEmpaque");
        query.setParameter("idListaEmpaque", idListaEmpaque);
        return query.getResultList();
    }
}
