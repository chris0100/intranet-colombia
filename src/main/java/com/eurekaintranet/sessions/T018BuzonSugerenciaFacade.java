/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T018BuzonSugerencia;
import java.math.BigInteger;
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
public class T018BuzonSugerenciaFacade extends AbstractFacade<T018BuzonSugerencia> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T018BuzonSugerenciaFacade() {
        super(T018BuzonSugerencia.class);
    }
    public int contarCalificaciones(int calificacion, String idTipoSugerencia){
        Query query = em.createNamedQuery("execTotalCalificacion");
        query.setParameter("calificacion", calificacion);
        query.setParameter("idTipoSugerencia", idTipoSugerencia);
        int result = ((Number) query.getSingleResult()).intValue();
        return result;
    }
    public int contarTipoSugerencia(String idTipoSugerencia){
        Query query = em.createNamedQuery("execTotalTipoSugerencia");
        query.setParameter("idTipoSugerencia", idTipoSugerencia);
        int result = ((Number) query.getSingleResult()).intValue();
        return result;
    }
    public List<T018BuzonSugerencia> sugerenciaPorTipo(String idTipoSugerencia){
        Query query = em.createNamedQuery("T018BuzonSugerencia.findByIdTipoSugerencia");
        query.setParameter("idTipoSugerencia", idTipoSugerencia);
        return query.getResultList();
    }
    public List<T018BuzonSugerencia> sugerenciaTodos(){
        Query query = em.createNamedQuery("T018BuzonSugerencia.findAll");
        return query.getResultList();
    }
    
}
