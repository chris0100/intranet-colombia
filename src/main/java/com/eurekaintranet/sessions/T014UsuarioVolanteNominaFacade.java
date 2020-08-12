/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T014UsuarioVolanteNomina;
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
public class T014UsuarioVolanteNominaFacade extends AbstractFacade<T014UsuarioVolanteNomina> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public T014UsuarioVolanteNominaFacade() {
        super(T014UsuarioVolanteNomina.class);
    }
    public T014UsuarioVolanteNomina usuarioVolanteLogin(String cedula, String contrasena) {
        Query q= getEntityManager().createNamedQuery("T014UsuarioVolanteNomina.findByUsuarioLogin");
        q.setParameter("cedula", cedula);
        q.setParameter("contrasena", contrasena);
        try {
            return (T014UsuarioVolanteNomina) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
}
