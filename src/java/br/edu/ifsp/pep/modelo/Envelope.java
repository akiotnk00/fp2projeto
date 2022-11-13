/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Akio
 */
@Entity
@Table(name = "envelope")
@NamedQueries({
    @NamedQuery(name = "Envelope.findAll", query = "SELECT e FROM Envelope e"),
    @NamedQuery(name = "Envelope.findByCodigo", query = "SELECT e FROM Envelope e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "Envelope.findByValor", query = "SELECT e FROM Envelope e WHERE e.valor = :valor"),
    @NamedQuery(name = "Envelope.findByData", query = "SELECT e FROM Envelope e WHERE e.data = :data"),
    @NamedQuery(name = "Envelope.findByVerificacao", query = "SELECT e FROM Envelope e WHERE e.verificacao = :verificacao")})
public class Envelope implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer codigo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(name = "verificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date verificacao;
    
    @OneToOne
    @JoinColumn(name = "contaDestino",nullable = false)
    private Conta contaDestino = new Conta();
    
    public Envelope() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getVerificacao() {
        return verificacao;
    }

    public void setVerificacao(Date verificacao) {
        this.verificacao = verificacao;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }


  

}
