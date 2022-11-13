/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.dao;

import br.edu.ifsp.pep.modelo.Agencia;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Akio
 */
@Stateless
public class AgenciaDAO extends GenericoDAO<Agencia> {

    public List<Agencia> findAll() {
        return getEntityManager()
                .createNamedQuery("Agencia.findAll", Agencia.class)
                .getResultList();
    }
    
    public Agencia findByNumero(Long numero) {
        return getEntityManager()
                .createNamedQuery("Agencia.findByNumero", Agencia.class)
                .setParameter("numero", numero)
                .getSingleResult();
    }
}
