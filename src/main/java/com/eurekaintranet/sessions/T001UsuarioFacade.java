/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import com.eurekaintranet.entities.T001Usuario;
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
public class T001UsuarioFacade extends AbstractFacade<T001Usuario> {

    @PersistenceContext(unitName = "EurekaIntranetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public T001UsuarioFacade() {
        super(T001Usuario.class);
    }
     public T001Usuario findByCorreoElectronico(String query){
          Query q= getEntityManager().createNamedQuery("T001Usuario.findByUsername");
          q.setParameter("username",query);
          try {
            return (T001Usuario) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
     public T001Usuario findByToken(String token){
          Query q= getEntityManager().createNamedQuery("T001Usuario.findByToken");
          q.setParameter("token",token);
          try {
            return (T001Usuario) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }
    public T001Usuario findByCorreoRecuperarContra(String correo){
        Query q= getEntityManager().createNamedQuery("T001Usuario.findByCorreo");
        q.setParameter("correo",correo);
        try {
            return (T001Usuario) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex){
            return null;
        }
    }    
}
