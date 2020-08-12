/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T013CreacionItem;
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
public class T013CreacionItemFacade extends AbstractFacade<T013CreacionItem> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T013CreacionItemFacade() {
        super(T013CreacionItem.class);
    }
    public List<T013CreacionItem> estadoCreacionItem(short estado, short itemNuevo) {
        Query q= getEntityManager().createNamedQuery("T013CreacionItem.findByEstado");
        q.setParameter("estado", estado);
        q.setParameter("itemNuevo", itemNuevo);
        return q.getResultList();
    }
    
     public List<T013CreacionItem> estadoCreacionItemTodos(short estado) {
        Query q= getEntityManager().createNamedQuery("T013CreacionItem.findByEstadoTodos");
        q.setParameter("estado", estado);
        return q.getResultList();
    }
    public List<T013CreacionItem> recargarListaItemsCreados() {
        Query q= em.createNamedQuery("execRecargarListaItemsCreados");
        return q.getResultList();
    }
     public List<T013CreacionItem> estadoCreacionItemRespuesta(short estado, short itemNuevo) {
        Query q= getEntityManager().createNamedQuery("T013CreacionItem.findByEstadoRespuesta");
        q.setParameter("estado", estado);
        q.setParameter("itemNuevo", itemNuevo);
        return q.getResultList();
    }
       public List<T013CreacionItem> estadoCreacionItemRespuestaTodos(short estado) {
        Query q= getEntityManager().createNamedQuery("T013CreacionItem.findByEstadoRespuestaTodos");
        q.setParameter("estado", estado);
        return q.getResultList();
    }
     public List<String> consultaExt1 ( String tipoExt){
        Query query = em.createNamedQuery("execListaExt1");
        query.setParameter("tipoExt", tipoExt);
        return query.getResultList();
    } 
    public List<String> consultaExt2 (String tipoExt){
        Query query = em.createNamedQuery("execListaExt2");
        query.setParameter("tipoExt", tipoExt);
        return query.getResultList();
    } 
    public List<String> consultaListaItemPatron (){
        Query query = em.createNamedQuery("execListaItemPatron");
        return query.getResultList();
    } 
    public List<String> consultaListaTipoInventario (){
        Query query = em.createNamedQuery("execListaTipoInventario");
        return query.getResultList();
    }
    public List<String> consultaListaPrecio (){
        Query query = em.createNamedQuery("execListaPrecio");
        return query.getResultList();
    }
    public List<String> consultaListaItemsRepetidosUnoEE(int codigoItem, String ext1, String ext2){
        Query query = em.createNamedQuery("execListaItemRepetido");
        query.setParameter("codigoItem", codigoItem);
        query.setParameter("ext1", ext1);
        query.setParameter("ext2", ext2);
        return query.getResultList();
    }
    public List<String> consultaListaItemsInactivosUnoEE(int codigoItem, String ext1, String ext2){
        Query query = em.createNamedQuery("execListaItemInactivo");
        query.setParameter("codigoItem", codigoItem);
        query.setParameter("ext1", ext1);
        query.setParameter("ext2", ext2);
        return query.getResultList();
    }
    public List<String> consultaListaItemsRepetidosEureka(int codigoItem, String ext1, String ext2, short estado){
        Query query = em.createNamedQuery("T013CreacionItem.findByItemRepetido");
        query.setParameter("codigoItem", codigoItem);
        query.setParameter("idExt1Detalle", ext1);
        query.setParameter("idExt2Detalle", ext2);
        query.setParameter("estado", estado);
        return query.getResultList();
    }
    public List<T013CreacionItem> consultaReporteTiempo(Date fecha1, Date fecha2 ){
        Query q = em.createNamedQuery("execReporteCreados");
        q.setParameter("estado", (short)3);
        q.setParameter("fecha1", fecha1);
        q.setParameter("fecha2", fecha2);
        return q.getResultList();
    }
    public List<String> consultaListaUndInventario (){
        Query query = em.createNamedQuery("execUndInventario");
        return query.getResultList();
    } 
    
    public void spEjecutarSsisItemExt (){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execEjecutarSsisItemExt");
        query.execute();
    } 
    public void spEjecutarActualizarItemsCreados (){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execEjecutarActualizarItemsCreados");
        query.execute();
    } 
    public void spEjecutarActualizarItemsCreadosPortafolio (){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execEjecutarActualizarItemsCreadosPortafolio");
        query.execute();
    } 
    public List<Integer> contarItemCreadoMesUnoEE(){
        Query query = em.createNamedQuery("execItemCreadoUnoEE");
        return query.getResultList(); 
    }
    public List<Integer> contarItemCreadoMesEureka(){
        Query query = em.createNamedQuery("execItemCreadoEureka");
        return query.getResultList(); 
    }
    public List<String> consultaPosArancelaria (){
        Query query = em.createNamedQuery("execPosArancelaria");
        return query.getResultList();
    } 
}
