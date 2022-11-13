/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.ContaDAO;
import br.edu.ifsp.pep.dao.EnvelopeDAO;
import br.edu.ifsp.pep.dao.MovimentacaoDAO;
import br.edu.ifsp.pep.modelo.Conta;
import br.edu.ifsp.pep.modelo.Envelope;
import br.edu.ifsp.pep.modelo.Movimentacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Akio
 */
@Named
@ViewScoped
public class EnvelopeController implements Serializable {

    @Inject
    private ContaDAO contaDAO;

    @Inject
    private EnvelopeDAO envelopeDAO;

    @Inject
    private MovimentacaoDAO movimentacaoDAO;
    
    private Movimentacao novaMovimentacao;
    
    private String selectContaDestino;
    private List<SelectItem> selectContas;

    private List<Conta> listaContas;
    private List<Envelope> listaEnvelopes;

    private Envelope novoEnvelope;
    private Envelope envelopeSelecionado;

    public EnvelopeController() {
        this.novoEnvelope = new Envelope();
    }

    @PostConstruct
    public void init() {
        selectContas = new ArrayList<>();

        this.listaContas = contaDAO.findAll();
        this.listaEnvelopes = envelopeDAO.findAll();

        if (listaContas != null) {
            listaContas.forEach(c -> {
                selectContas.add(new SelectItem(c.getNumero()));
            });
        }

    }

    public void atualizaEnvelopes() {
        this.listaEnvelopes = envelopeDAO.findAll();
    }

    public void onRowEdit(RowEditEvent<Envelope> event) {
        FacesMessage msg = new FacesMessage("Envelope Editado", "Envelope: " + event.getObject().getCodigo());
        envelopeDAO.edit(event.getObject());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<Envelope> event) {
        FacesMessage msg = new FacesMessage("Envelope Não Editado", "Envelope: " + event.getObject().getCodigo());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deletarEnvelope() {

        envelopeDAO.remove(envelopeSelecionado);
        this.envelopeSelecionado = null;
        atualizaEnvelopes();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Envelope Removido com sucesso!"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-envelopes");
    }

    public void confirmarEnvelope() {
        this.envelopeSelecionado.setVerificacao(new Date());
        envelopeDAO.edit(envelopeSelecionado);
        
        atualizaEnvelopes();
        
        novaMovimentacao.setValor(this.envelopeSelecionado.getValor());
        novaMovimentacao.setTipo("Deposito");
        novaMovimentacao.setConta(this.envelopeSelecionado.getContaDestino());
        novaMovimentacao.setData(new Date());
        movimentacaoDAO.create(novaMovimentacao);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Envelope Confirmado!"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-envelopes");

    }

    public void salvarNovo() {

        novoEnvelope.setContaDestino(contaDAO.findByNumero(Long.parseLong(selectContaDestino)));

        novoEnvelope.setData(new Date());

        envelopeDAO.create(novoEnvelope);

        atualizaEnvelopes();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Envelope registrado!"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-envelopes");
    }

    public void criarNovo() {
        this.novoEnvelope = new Envelope();
    }

    public ContaDAO getContaDAO() {
        return contaDAO;
    }

    public void setContaDAO(ContaDAO contaDAO) {
        this.contaDAO = contaDAO;
    }

    public EnvelopeDAO getEnvelopeDAO() {
        return envelopeDAO;
    }

    public void setEnvelopeDAO(EnvelopeDAO envelopeDAO) {
        this.envelopeDAO = envelopeDAO;
    }

    public String getSelectContaDestino() {
        return selectContaDestino;
    }

    public void setSelectContaDestino(String selectContaDestino) {
        this.selectContaDestino = selectContaDestino;
    }

    public List<SelectItem> getSelectContas() {
        return selectContas;
    }

    public void setSelectContas(List<SelectItem> selectContas) {
        this.selectContas = selectContas;
    }

    public List<Conta> getListaContas() {
        return listaContas;
    }

    public void setListaContas(List<Conta> listaContas) {
        this.listaContas = listaContas;
    }

    public List<Envelope> getListaEnvelopes() {
        return listaEnvelopes;
    }

    public void setListaEnvelopes(List<Envelope> listaEnvelopes) {
        this.listaEnvelopes = listaEnvelopes;
    }

    public Envelope getNovoEnvelope() {
        return novoEnvelope;
    }

    public void setNovoEnvelope(Envelope novoEnvelope) {
        this.novoEnvelope = novoEnvelope;
    }

    public Envelope getEnvelopeSelecionado() {
        return envelopeSelecionado;
    }

    public void setEnvelopeSelecionado(Envelope envelopeSelecionado) {
        this.envelopeSelecionado = envelopeSelecionado;
    }

}
