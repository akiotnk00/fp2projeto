/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Akio
 */
@Entity
@Table(name = "conta")
@NamedQueries({
    @NamedQuery(name = "Conta.findAll", query = "SELECT c FROM Conta c"),
    @NamedQuery(name = "Conta.findByNumero", query = "SELECT c FROM Conta c WHERE c.numero = :numero")
})
public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero")
    private Long numero;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAbertura;
    @Column(name = "data_encerramento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEncerramento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @ManyToOne
    @JoinColumn(name = "agencia", nullable = false)
    private Agencia agencia = new Agencia();

    @ManyToOne
    @JoinColumn(name = "cliente", nullable = false)
    private Cliente cliente = new Cliente();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conta")
    private List<Movimentacao> listaMovimentacoes;

    @Column(name = "limite")
    private Double limite;

    @Column(name = "saldo")
    private Double saldo;

    public Conta() {
    }

    public String possuiMovimentacoes() {
        if (this.listaMovimentacoes == null || this.listaMovimentacoes.size() < 1) {
            return "Não";
        } else {
            return "" + this.listaMovimentacoes.size();
        }
    }

    public Double calculaSaldo(){     
            
        if (this.listaMovimentacoes != null) {
            listaMovimentacoes.forEach(m -> {
                if(m.getTipo().equals("Transferência")){
                    this.saldo = this.saldo - m.getValor();
                }
                if(m.getTipo().equals("Deposito")){
                    this.saldo = this.saldo + m.getValor();
                }
                if(m.getTipo().equals("Saque")){
                    this.saldo = this.saldo - m.getValor();
                }
            });
        }
        
        return this.saldo;
    }
    
    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Date dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Movimentacao> getListaMovimentacoes() {
        return listaMovimentacoes;
    }

    public void setListaMovimentacoes(List<Movimentacao> listaMovimentacoes) {
        this.listaMovimentacoes = listaMovimentacoes;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

}
