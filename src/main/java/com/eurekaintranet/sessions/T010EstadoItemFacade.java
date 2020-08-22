/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T010EstadoItem;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
public class T010EstadoItemFacade extends AbstractFacade<T010EstadoItem> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T010EstadoItemFacade() {
        super(T010EstadoItem.class);
    }
    public List<T010EstadoItem> estadoItemPlan50ComercialEstadoCambio(String plan50) {
        Query q= getEntityManager().createNamedQuery("T010EstadoItem.findByFiltroPlanEstadoCambio");
        q.setParameter("plan50",plan50);
        q.setParameter("estadoItemCambio",(short)1);
        return q.getResultList();
    }


    public List<T010EstadoItem> estadoItemPlan50Comercial(String plan50) {
        Query q= getEntityManager().createNamedQuery("T010EstadoItem.findByFiltroPlanEstado");
        q.setParameter("plan50",plan50);
        q.setParameter("estado",(short)1);
        return q.getResultList();
    }


    public List<T010EstadoItem> estadoItemPlan50ComercialEstadoItem(String plan50,String estadoItem) {
        Query q= getEntityManager().createNamedQuery("T010EstadoItem.findByFiltroPlanEstadoItem");
        q.setParameter("plan50",plan50);
        q.setParameter("estado",(short)1);
        q.setParameter("estadoItem",estadoItem);
        return q.getResultList();
    }



    public List<T010EstadoItem> estadoItemPlan50Planeacion(String plan50) {
        Query q= getEntityManager().createNamedQuery("T010EstadoItem.findByFiltroPlanEstado");
        q.setParameter("plan50",plan50);
        q.setParameter("estado",(short)2);
        return q.getResultList();
    }


    public List<T010EstadoItem> estadoItemPlan50PlaneacionEstadoItem(String plan50,String estadoItem) {
        Query q= getEntityManager().createNamedQuery("T010EstadoItem.findByFiltroPlanNoiItem");
        q.setParameter("plan50",plan50);
        q.setParameter("estado",(short)2);
        q.setParameter("estadoItem",estadoItem);
        return q.getResultList();
    }
    public List<T010EstadoItem> estadoItemPlan50Unoee(String plan50) {
        Query q= getEntityManager().createNamedQuery("T010EstadoItem.findByFiltroPlanEstado");
        q.setParameter("plan50",plan50);
        q.setParameter("estado",(short)3);
        return q.getResultList();
    }
    public List<T010EstadoItem> estadoItemPlan50UnoeeEstado2(String plan50) {
        Query q= getEntityManager().createNamedQuery("T010EstadoItem.findByFiltroPlanEstado2");
        q.setParameter("plan50",plan50);
        q.setParameter("estado",(short)4);
        return q.getResultList();
    }
    public List<T010EstadoItem> estadoItemEvaluados(int evaluado) {
        Query q= getEntityManager().createNamedQuery("T010EstadoItem.findByEvaluado");
        q.setParameter("evaluado",evaluado);
        
        return q.getResultList();
    }
    public void estadoItemSp (String plan50, String usuario){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execEstadoItem");
        query.setParameter("plan50", plan50);
        query.setParameter("usuario", usuario);
        query.execute();
    }  
    public List<Integer> contarGrupoEvaluadoMes (){
        Query query = em.createNamedQuery("execGrupoEvaluadoMes");
        return query.getResultList(); 
    }
    
    //REPORTE DE PROGRAMACION
    
    public String fechaTerminacionOP (String referencia, String ext1, String ext2, int rowidPedido){
        Query query = em.createNamedQuery("execFechaTerminacionOp");
        query.setParameter("referencia", referencia);
        query.setParameter("ext1", ext1);
        query.setParameter("ext2", ext2);
        query.setParameter("rowidPedido", rowidPedido);
        try {
            Date fecha = (Date) query.getSingleResult();
            return (String) String.valueOf(fecha).substring(0,10);
        } catch (NoResultException ex){
            return "OP CUMPLIDA";
        } 
    }
    
    
}
