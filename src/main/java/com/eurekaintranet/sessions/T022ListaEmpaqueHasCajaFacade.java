/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T020ListaEmpaque;
import com.eurekaintranet.entities.T021Caja;
import com.eurekaintranet.entities.T022ListaEmpaqueHasCaja;
import java.util.Iterator;
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
public class T022ListaEmpaqueHasCajaFacade extends AbstractFacade<T022ListaEmpaqueHasCaja> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T022ListaEmpaqueHasCajaFacade() {
        super(T022ListaEmpaqueHasCaja.class);
    }
    public List<T022ListaEmpaqueHasCaja> consultarItemListaEmpaqueCaja(T020ListaEmpaque idListaEmpaque, T021Caja idCaja){
        Query q= getEntityManager().createNamedQuery("T022ListaEmpaqueHasCaja.findByIdListaEmpaqueCaja");
        q.setParameter("idListaEmpaque", idListaEmpaque);
        q.setParameter("idCaja", idCaja);
        return q.getResultList();
    }
    public List<T022ListaEmpaqueHasCaja> consultarItemListaEmpaque(T020ListaEmpaque idListaEmpaque){
        Query q= getEntityManager().createNamedQuery("T022ListaEmpaqueHasCaja.findByIdListaEmpaque");
        q.setParameter("idListaEmpaque", idListaEmpaque);
        return q.getResultList();
    }
    public String obtenerDescItemPais (String descPais, int idItem){
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("execObtenerDescItemPais");
        query.setParameter("descPais", descPais);
        query.setParameter("idItem", idItem);
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
}
