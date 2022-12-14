/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.ContaDAO;
import br.edu.ifsp.pep.dao.MovimentacaoDAO;
import br.edu.ifsp.pep.modelo.Conta;
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
public class MovimentacaoController implements Serializable{
    
    @Inject
    private ContaDAO contaDAO;
    
    @Inject 
    private MovimentacaoDAO movimentacaoDAO;
    
    private String selectConta;
    private String selectContaDestino;
    private List<SelectItem> selectContas;
    
    private List<Conta> listaContas;
    private List<Movimentacao> listaMovimentacoes;

    private Movimentacao novaMovimentacao;
    private Movimentacao movimentacaoSelecionada;
    
    
    public MovimentacaoController() {
        this.novaMovimentacao = new Movimentacao();
    }
    
    @PostConstruct
    public void init(){
        selectContas= new ArrayList<>();
        
        this.listaContas = contaDAO.findAll();
        this.listaMovimentacoes = movimentacaoDAO.findAll();
        
         if (listaContas != null) {
            listaContas.forEach(c -> {
                selectContas.add(new SelectItem(c.getNumero()));
            });
        }
        
    }
    
    public void atualizaMovimentacoes(){
        this.listaMovimentacoes = movimentacaoDAO.findAll();
    }
    
    public void onRowEdit(RowEditEvent<Movimentacao> event) {
        FacesMessage msg = new FacesMessage("Movimenta????o Editada", "Movimenta????o: " + event.getObject().getCodigo());
        movimentacaoDAO.edit(event.getObject());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEditCancel(RowEditEvent<Movimentacao> event) {
        FacesMessage msg = new FacesMessage("Movimenta????o N??o Editada", "Movimenta????o: " + event.getObject().getCodigo());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
     public void deletarMovimentacao() {

        movimentacaoDAO.remove(movimentacaoSelecionada);
        this.movimentacaoSelecionada = null;
        atualizaMovimentacoes();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Conta Removida com sucesso!"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-movimentacoes");
    }

    public void salvarNova() {
 
        novaMovimentacao.setConta(contaDAO.findByNumero(Long.parseLong(selectConta)));
        novaMovimentacao.setConta(contaDAO.findByNumero(Long.parseLong(selectConta)));
        novaMovimentacao.setData(new Date());
        
        
        movimentacaoDAO.create(novaMovimentacao);
        
        atualizaMovimentacoes();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Movimenta????o registrada!"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-movimentacoes");
    }

    public void criarNova() {
        this.novaMovimentacao = new Movimentacao();
    } 
    
}
