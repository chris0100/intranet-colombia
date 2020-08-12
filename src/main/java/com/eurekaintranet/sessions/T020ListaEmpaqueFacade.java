/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T020ListaEmpaque;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author Lccabalr
 */
@Stateless
public class T020ListaEmpaqueFacade extends AbstractFacade<T020ListaEmpaque> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T020ListaEmpaqueFacade() {
        super(T020ListaEmpaque.class);
    }
    public List<String> consultarNit(){
        Query q= getEntityManager().createNamedQuery("execNit");
        return q.getResultList();
    }
    public List<String> consultarPedido(String nit){
        Query q= getEntityManager().createNamedQuery("execPedido");
        q.setParameter("nit", nit);
        return q.getResultList();
    }
    public int consultarRowidItem(String codBarra){
        Query q= getEntityManager().createNamedQuery("execRowidItem");
        q.setParameter("codBarra", codBarra);
        try {
            return (int) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return 0;
        }
    }
    
    public String obtenerConectorListaEmpaque (String idListaEmpaque){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execObtenerConectorListaEmpaque");
        query.setParameter("idListaEmpaque", idListaEmpaque);
        String resultado="";
        List<Object> result = query.getResultList();
        Iterator itr = result.iterator();
        while(itr.hasNext()){
           Object[] obj = (Object[]) itr.next();
           //now you have one array of Object for each row
           resultado = resultado + String.valueOf(obj[0]); // don't know the type of column CLIENT assuming String 
          
        }
        return resultado;
    }
    public List<T020ListaEmpaque> consultarListaEmpaque(){
        Query q= getEntityManager().createNamedQuery("T020ListaEmpaque.findAll");
        return q.getResultList();
    }
    public List<T020ListaEmpaque> consultarListaEmpaqueUsuario(T001Usuario idUsuarioCreacion){
        Query q= getEntityManager().createNamedQuery("T020ListaEmpaque.findByIdUsuarioCreacion");
        q.setParameter("idUsuarioCreacion", idUsuarioCreacion);
        return q.getResultList();
    }
    public List<T020ListaEmpaque> consultarListaEmpaqueTipo(String tipoListaEmpaque){
        Query q= getEntityManager().createNamedQuery("T020ListaEmpaque.findByTipo");
        q.setParameter("tipoListaEmpaque", tipoListaEmpaque);
        return q.getResultList();
    }
}
