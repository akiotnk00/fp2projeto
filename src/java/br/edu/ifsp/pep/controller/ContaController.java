/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.AgenciaDAO;
import br.edu.ifsp.pep.dao.ClienteDAO;
import br.edu.ifsp.pep.dao.ContaDAO;
import br.edu.ifsp.pep.modelo.Agencia;
import br.edu.ifsp.pep.modelo.Cliente;
import br.edu.ifsp.pep.modelo.Conta;
import java.io.Serializable;
import java.util.ArrayList;
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
public class ContaController implements Serializable {

    @Inject
    private ContaDAO contaDAO;

    @Inject
    private AgenciaDAO agenciaDAO;

    @Inject
    private ClienteDAO clienteDAO;

    private String selectCliente;
    private String selectAgencia;
    private List<SelectItem> selectAgencias;
    private List<SelectItem> selectClientes;

    private List<Conta> listaContas;
    private List<Agencia> listaAgencias;
    private List<Cliente> listaClientes;
    private Conta contaSelecionada;
    private Conta contaNova;
    private Cliente clienteSelecionado;
    private Agencia agenciaSelecionada;

    public ContaController() {
        this.contaNova = new Conta();
    }

    @PostConstruct
    public void init() {
        selectAgencias = new ArrayList<>();
        selectClientes = new ArrayList<>();

        this.listaContas = contaDAO.findAll();
        this.listaAgencias = agenciaDAO.findAll();
        this.listaClientes = clienteDAO.findAll();

        if (listaAgencias != null) {
            listaAgencias.forEach(a -> {
                selectAgencias.add(new SelectItem(a.getNumero()));
            });
        }

        if (listaClientes != null) {
            listaClientes.forEach(c -> {
                selectClientes.add(new SelectItem(c.getCpf()));
            });
        }
    }

    public void atualizaContas() {
        this.listaContas = contaDAO.findAll();
    }

    public void onRowEdit(RowEditEvent<Conta> event) {
        FacesMessage msg = new FacesMessage("Conta Editada", "Conta: " + event.getObject().getNumero() + " Ag??ncia: " + event.getObject().getAgencia().getNumero());
        contaDAO.edit(event.getObject());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<Conta> event) {
        FacesMessage msg = new FacesMessage("Conta N??o Editada", "Conta: " + event.getObject().getNumero() + " Ag??ncia: " + event.getObject().getAgencia().getNumero());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deletarConta() {

        contaDAO.remove(contaSelecionada);
        this.contaSelecionada = null;
        atualizaContas();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Conta Removida com sucesso!"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-contas");
    }

    public void salvarNova() {
        this.clienteSelecionado = clienteDAO.findByCpf(selectCliente);
        this.agenciaSelecionada = agenciaDAO.findByNumero(Long.parseLong(selectAgencia.toString()));
        contaNova.setAgencia(this.agenciaSelecionada);
        contaNova.setCliente(this.clienteSelecionado);

        contaDAO.create(contaNova);
        atualizaContas();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Conta criada com sucesso!"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-contas");
    }

    public void criarNovo() {
        this.contaNova = new Conta();
    }
    
}
