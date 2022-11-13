/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.dao;

import br.edu.ifsp.pep.modelo.Conta;
import br.edu.ifsp.pep.modelo.Usuario;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Akio
 */
@Stateless
public class ContaDAO extends GenericoDAO<Conta> {

    public List<Conta> findAll() {
        return getEntityManager()
                .createNamedQuery("Conta.findAll", Conta.class)
                .getResultList();
    }
    
    public Conta findByNumero(Long numero) {
        return getEntityManager()
                .createNamedQuery("Conta.findByNumero", Conta.class)
                .setParameter("numero", numero)
                .getSingleResult();
    }
}
