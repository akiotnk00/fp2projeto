/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.dao;

import br.edu.ifsp.pep.modelo.Usuario;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Akio
 */

@Stateless
public class UsuarioDAO extends GenericoDAO<Usuario> {

    public List<Usuario> findAll() {
        return getEntityManager()
                .createNamedQuery("Usuario.findAll", Usuario.class)
                .getResultList();
    }

    public Usuario findByNomeUsuario(String usuario) {
        return getEntityManager()
                .createNamedQuery("Usuario.findByNomeUsuario", Usuario.class)
                .setParameter("nomeUsuario", usuario)
                .getSingleResult();
    }

}
