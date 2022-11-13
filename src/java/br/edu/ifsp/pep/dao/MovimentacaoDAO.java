/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.dao;

import br.edu.ifsp.pep.modelo.Movimentacao;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Akio
 */
@Stateless
public class MovimentacaoDAO extends GenericoDAO<Movimentacao> {

    public List<Movimentacao> findAll() {
        return getEntityManager()
                .createNamedQuery("Movimentacao.findAll", Movimentacao.class)
                .getResultList();
    }
}
