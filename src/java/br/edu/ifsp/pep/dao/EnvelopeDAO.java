/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.dao;

import br.edu.ifsp.pep.modelo.Envelope;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Akio
 */
@Stateless
public class EnvelopeDAO extends GenericoDAO<Envelope> {

    public List<Envelope> findAll() {
        return getEntityManager()
                .createNamedQuery("Envelope.findAll", Envelope.class)
                .getResultList();
    }
}
