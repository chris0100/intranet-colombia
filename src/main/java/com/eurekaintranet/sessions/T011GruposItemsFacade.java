/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T011GruposItems;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author Lccabalr
 */
@Stateless
public class T011GruposItemsFacade extends AbstractFacade<T011GruposItems> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T011GruposItemsFacade() {
        super(T011GruposItems.class);
    }
    public void envioDatosSp (String grupo, String usuario, Date fechaInicial, Date fechaFinal, int cantidadClientes, int rotacion, BigDecimal PartSumada, int diasHabiles){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execEnvioDatos");
        query.setParameter("grupo",grupo); 
        query.setParameter("usuario",usuario);
        query.setParameter("fechaInicial",fechaInicial);
        query.setParameter("fechaFinal",fechaFinal);
        query.setParameter("cantidadClientes",cantidadClientes);
        query.setParameter("rotacion",rotacion);
        query.setParameter("PartSumada",PartSumada);
        query.setParameter("diasHabiles",diasHabiles);
        query.execute();
    }


    public List<T011GruposItems> grupoPendienteComercial() {
        Query q= getEntityManager().createNamedQuery("T011GruposItems.findByGrupoPendiente");
        q.setParameter("grupoPendiente",(short)1);
        return q.getResultList();
    }


    public List<T011GruposItems> grupoPendientePlaneacion() {
        Query q= getEntityManager().createNamedQuery("T011GruposItems.findByGrupoPendiente");
        q.setParameter("grupoPendiente",(short)2);
        return q.getResultList();
    }
}
