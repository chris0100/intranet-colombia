/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T007Contenido;
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
public class T007ContenidoFacade extends AbstractFacade<T007Contenido> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T007ContenidoFacade() {
        super(T007Contenido.class);
    }
    public List<T007Contenido> tipoContenido(String tipo) {
        Query q= getEntityManager().createNamedQuery("T007Contenido.findByTipo");
        q.setParameter("tipo",tipo);
        q.setMaxResults(4);
        return q.getResultList();
    }
     public List<T007Contenido> contenidoFooter() {
        Query q= getEntityManager().createNamedQuery("T007Contenido.findAllFooter");
        q.setMaxResults(5);
        return q.getResultList();
    }
    public List<Integer> contarVistaContenido (){
        Query query = em.createNamedQuery("execContarContenidos");
        return query.getResultList(); 
    }
}
