/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T009ParamEstadoItem;
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
public class T009ParamEstadoItemFacade extends AbstractFacade<T009ParamEstadoItem> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T009ParamEstadoItemFacade() {
        super(T009ParamEstadoItem.class);
    }
    public List<T009ParamEstadoItem> parametrosVigentes(String estado) {
        Query q= getEntityManager().createNamedQuery("T009ParamEstadoItem.findByEstado");
        q.setParameter("estado",estado);
        return q.getResultList();
    }
    public List<T009ParamEstadoItem> parametrosSelect(int idParamEstadoItem, String estado) {
        Query q= getEntityManager().createNamedQuery("T009ParamEstadoItem.findByIdParamEstadoItem");
        q.setParameter("idParamEstadoItem",idParamEstadoItem);
        q.setParameter("estado",estado);
        return q.getResultList();
    }
    public List<T009ParamEstadoItem> parametrosTotalesOrdenados() {
        Query q= getEntityManager().createNamedQuery("T009ParamEstadoItem.findAll");
        return q.getResultList();
    }
}
