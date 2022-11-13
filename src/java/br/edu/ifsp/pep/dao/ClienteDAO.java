/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.dao;

import br.edu.ifsp.pep.modelo.Cliente;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Akio
 */

@Stateless
public class ClienteDAO extends GenericoDAO<Cliente> {

    public List<Cliente> findAll() {
        return getEntityManager()
                .createNamedQuery("Cliente.findAll", Cliente.class)
                .getResultList();
    }
    
     public Cliente findByCpf(String cpf) {
        return getEntityManager()
                .createNamedQuery("Cliente.findByCpf", Cliente.class)
                .setParameter("cpf", cpf)
                .getSingleResult();
    }

}
