/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T014UsuarioVolanteNomina;
import com.eurekaintranet.entities.T015VolanteNomina;
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
public class T015VolanteNominaFacade extends AbstractFacade<T015VolanteNomina> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T015VolanteNominaFacade() {
        super(T015VolanteNomina.class);
    }
    public List<T015VolanteNomina> consultaVolantePeriodo (int idPeriodo){
        Query query = em.createNamedQuery("T015VolanteNomina.findByPeriodo");
        query.setParameter("idPeriodo", idPeriodo);
        return query.getResultList();
    } 
    
    public List<T015VolanteNomina> volantePorUsuario (String codigo){
        Query query = em.createNamedQuery("execVolantesPorUsuario");
        query.setParameter("codigo", codigo);
        return query.getResultList();
    }
    
    public List<T015VolanteNomina> findByTipoNominaPeriodo(String tipoNomina, int idPeriodo){
        Query q= getEntityManager().createNamedQuery("T015VolanteNomina.findByTipoNominaPeriodo");
        q.setParameter("tipoNomina",tipoNomina);
        q.setParameter("idPeriodo",idPeriodo);
        return q.getResultList();
    }
    
    public List<T015VolanteNomina> consultaVolanteEstado (boolean volanteEnviado){
        Query query = getEntityManager().createNamedQuery("T015VolanteNomina.findByEstado");
        query.setParameter("volanteEnviado", volanteEnviado);
        return query.getResultList();
    } 
    public List<Integer> contarVolanteEnviado(){
        Query query = em.createNamedQuery("execVolanteEnviado");
        return query.getResultList(); 
    }
    public List<Integer> contarVolanteCreado(){
        Query query = em.createNamedQuery("execVolanteCreado");
        return query.getResultList(); 
    }
}
