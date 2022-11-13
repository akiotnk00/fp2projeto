/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.AgenciaDAO;
import br.edu.ifsp.pep.modelo.Agencia;
import br.edu.ifsp.pep.modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
public class AgenciaController implements Serializable{
    
    @Inject
    private AgenciaDAO agenciaDAO;
    
    private List<Agencia> listaAgencias;
    private Agencia agenciaSelecionada;
    private Agencia agenciaNova;

    public AgenciaController() {
        this.agenciaNova = new Agencia();
    }
    
    @PostConstruct
    public void init() {
        this.listaAgencias = agenciaDAO.findAll();
    }
    
    public void atualizaAgencias(){
        this.listaAgencias = agenciaDAO.findAll();
    }
    
    public void onRowEdit(RowEditEvent<Agencia> event) {
        FacesMessage msg = new FacesMessage("Agência Editada",""+event.getObject().getNumero());
        agenciaDAO.edit(event.getObject());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<Agencia> event) {
        FacesMessage msg = new FacesMessage("Agência não editada",""+event.getObject().getNumero());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void deletarAgencia() {

        agenciaDAO.remove(agenciaSelecionada);
        this.agenciaSelecionada = null;
        atualizaAgencias();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Agência Removida com sucesso!"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-agencias");
    }

    public void salvarNova() {
        agenciaDAO.create(agenciaNova);
        atualizaAgencias();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Agência criada com sucesso!"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-agencias");
    }
    
    public void criarNova(){
        this.agenciaNova = new Agencia();
    }
}
