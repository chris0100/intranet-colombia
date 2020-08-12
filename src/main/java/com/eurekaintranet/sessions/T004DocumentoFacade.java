/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T004Documento;
import com.eurekaintranet.entities.T005Proceso;
import com.eurekaintranet.entities.T006TipoDocumento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author junio
 */
@Stateless
public class T004DocumentoFacade extends AbstractFacade<T004Documento> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T004DocumentoFacade() {
        super(T004Documento.class);
    }
    public List<T004Documento> documentosProceso(T005Proceso idProceso) {
        Query q= getEntityManager().createNamedQuery("T004Documento.findByProceso");
        q.setParameter("idProceso",idProceso);
        return q.getResultList();
    }
    public List<T004Documento> documentosProcesoTipoDocumento(T005Proceso idProceso , T006TipoDocumento idTipoDocumento) {
        Query q= getEntityManager().createNamedQuery("T004Documento.findByProcesoTipoDocumento");
        q.setParameter("idProceso",idProceso);
        q.setParameter("idTipoDocumento",idTipoDocumento);
        return q.getResultList();
    }
    public List<T004Documento> documentosTipoDocumentoUser( T006TipoDocumento idTipoDocumento) {
        Query q= getEntityManager().createNamedQuery("T004Documento.findByTipoDocumentoUser");
        q.setParameter("idTipoDocumento",idTipoDocumento);
        return q.getResultList();
    }
    public List<T004Documento> documentosTipoFormatoUser( T006TipoDocumento idTipoDocumento) {
        Query q= getEntityManager().createNamedQuery("T004Documento.findByTipoFormatoUser");
        q.setParameter("idTipoDocumento",idTipoDocumento);
        return q.getResultList();
    }
    public List<T004Documento> documentosEstado(String estado) {
        Query q= getEntityManager().createNamedQuery("T004Documento.findByEstado");
        q.setParameter("estado",estado);
        return q.getResultList();
    }
    public List<T004Documento> documentosHistorial(String codigo ) {
        Query q= getEntityManager().createNamedQuery("T004Documento.findByHistorial");
        q.setParameter("codigo",codigo);
        return q.getResultList();
    }
    public List<T004Documento> documentosProcesoDoc(T005Proceso idProceso, T006TipoDocumento idTipoDocumento , String estado) {
        Query q= getEntityManager().createNamedQuery("T004Documento.findByProcesoDoc");
        q.setParameter("idProceso",idProceso);
        q.setParameter("idTipoDocumento",idTipoDocumento);
        q.setParameter("estado",estado);
        return q.getResultList();
    }
    public List<T004Documento> documentosProcesoFto(T005Proceso idProceso, T006TipoDocumento idTipoDocumento, String estado) {
        Query q= getEntityManager().createNamedQuery("T004Documento.findByProcesoFto");
        q.setParameter("idProceso",idProceso);
        q.setParameter("idTipoDocumento",idTipoDocumento);
        q.setParameter("estado",estado);
        return q.getResultList();
    }
    public List<T004Documento> documentosFormatoProcedimiento(T006TipoDocumento idTipoDocumento) {
        Query q= getEntityManager().createNamedQuery("T004Documento.findByFormatoProcedimiento");
        q.setParameter("idTipoDocumento",idTipoDocumento);
        return q.getResultList();
    }
    public List<T004Documento> documentosProcedimiento(T006TipoDocumento idTipoDocumento) {
        Query q= getEntityManager().createNamedQuery("T004Documento.findByTipo");
        q.setParameter("idTipoDocumento",idTipoDocumento);
        return q.getResultList();
    }
     public T004Documento findByNombreArchivo(String query,T005Proceso idProceso){
          Query q= getEntityManager().createNamedQuery("T004Documento.findByNombreProceso");
          q.setParameter("nombre",query);
          q.setParameter("idProceso",idProceso);
          try {
            return (T004Documento) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
     
     public T004Documento findByCodigo(String codigo){
          Query q= getEntityManager().createNamedQuery("T004Documento.findByCodigo");
          q.setParameter("codigo",codigo);
          try {
            return (T004Documento) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
     public List<T004Documento> consultaTopDocumento (){
        Query query = em.createNamedQuery("execTopDocumento");
        return query.getResultList(); 
    } 
}
