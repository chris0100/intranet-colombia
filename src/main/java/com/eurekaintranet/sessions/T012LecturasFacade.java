/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T012Lecturas;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author Lccabalr
 */
@Stateless
public class T012LecturasFacade extends AbstractFacade<T012Lecturas> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T012LecturasFacade() {
        super(T012Lecturas.class);
    }
    public List<T012Lecturas> itemsEstado(short estado) {
        Query q= getEntityManager().createNamedQuery("T012Lecturas.findByF001RegistroConector");
        q.setParameter("f001RegistroConector",estado);
        return q.getResultList();
    }
    public List<T012Lecturas> itemsOp(String f001IdOp) {
        Query q= getEntityManager().createNamedQuery("T012Lecturas.findByF001IdOp");
        q.setParameter("f001IdOp",f001IdOp);
        return q.getResultList();
    }
    public List<T012Lecturas> itemsOpEstado(String f001IdOp, short f001RegistroConector) {
        Query q= getEntityManager().createNamedQuery("T012Lecturas.findByF001IdOpEstado");
        q.setParameter("f001IdOp",f001IdOp);
        q.setParameter("f001RegistroConector",f001RegistroConector);    
        return q.getResultList();
    }
    public List<T012Lecturas> itemsSinEnviar(short noEnvio,short errorEnvio) {
        Query q= getEntityManager().createNamedQuery("T012Lecturas.findByEstadoConectorNoEnvio");
        q.setParameter("noEnvio",noEnvio);
        q.setParameter("errorEnvio",errorEnvio);
        return q.getResultList();
    }
    public List<T012Lecturas> itemsSinEnviarSp (){
        Query query = em.createNamedQuery("execKosListaNoEnviados");
        return query.getResultList();
    } 
     public List<String> listaOp (){
        Query query = em.createNamedQuery("execKosListaOp");
        return query.getResultList();
    } 
     
    public List<String> totalCajasOp (String op){
        Query query = em.createNamedQuery("totalCajasOp");
        query.setParameter("op", op);
        return query.getResultList();
    }
    public List<String> totalCajasUnoEEOp(String op){
        Query query = em.createNamedQuery("totalCajasUnoEEOp");
        query.setParameter("op", op);
        return query.getResultList();
    } 
    public List<String> totalCajasNoUnoEEOp(String op){
        Query query = em.createNamedQuery("totalCajasNoUnoEEOp");
        query.setParameter("op", op);
        return query.getResultList();
    } 
    public List<String> totalCajasErrorUnoEEOp(String op){
        Query query = em.createNamedQuery("totalCajasErrorUnoEEOp");
        query.setParameter("op", op);
        return query.getResultList();
    } 
    public List<String> totalUnidadesOp(String op){
        Query query = em.createNamedQuery("totalUnidadesOp");
        query.setParameter("op", op);
        return query.getResultList();
    }
    public List<String> totalUnidadesUnoEEOp(String op){
        Query query = em.createNamedQuery("totalUnidadesUnoEEOp");
        query.setParameter("op", op);
        return query.getResultList();
    } 
    public List<String> totalUnidadesNoUnoEEOp(String op){
        Query query = em.createNamedQuery("totalUnidadesNoUnoEEOp");
        query.setParameter("op", op);
        return query.getResultList();
    } 
    public List<String> totalUnidadesErrorUnoEEOp (String op){
        Query query = em.createNamedQuery("totalUnidadesErrorUnoEEOp");
        query.setParameter("op", op);
        return query.getResultList();
    } 
     public void spEjecutarSsisCodigoBarrasKos (){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execEjecutarSsisCodigoBarrasKos");
        query.execute();
    }  
    
    public List<String> obtenerOPEstadoUnoEE(String op){
        Query query = em.createNamedQuery("execOPEstadoUnoEE");
        query.setParameter("op", op);
        return query.getResultList(); 
    }
     
    public String obtenerConectorCodigoBarraKos (String op){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execEjecutarCodigoBarraKos");
        query.setParameter("op", op);
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
     
    public List<Integer> contarTotalEptMesUnoEE(){
        Query query = em.createNamedQuery("execTotalEptMesUnoEE");
        return query.getResultList(); 
    }
    public List<Integer> contarTotalEptMesEureka(){
        Query query = em.createNamedQuery("execTotalEptMesEureka");
        return query.getResultList(); 
    }
     public List<String> obtenerOPCargar(){
        Query query = em.createNamedQuery("execOPCargar");
        return query.getResultList(); 
    }
}
