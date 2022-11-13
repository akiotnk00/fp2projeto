/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.ClienteDAO;
import br.edu.ifsp.pep.modelo.Cliente;
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
public class ClienteController implements Serializable {

    @Inject
    private ClienteDAO clienteDAO;
    private Cliente clienteSelecionado;
    private Cliente clienteNovo = new Cliente();

    private List<Cliente> listaClientes;

    public ClienteController() {
        this.clienteNovo = new Cliente();
    }

    @PostConstruct
    public void init() {
        listaClientes = clienteDAO.findAll();
    }

    public void onRowEdit(RowEditEvent<Cliente> event) {
        FacesMessage msg = new FacesMessage("Cliente Editado", event.getObject().getCpf());
        clienteDAO.edit(event.getObject());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<Cliente> event) {
        FacesMessage msg = new FacesMessage("Cliente não editado", event.getObject().getCpf());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deletarCliente() {


        this.clienteSelecionado = null;
        clienteDAO.remove(clienteSelecionado);
        atualizaClientes();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente Removido com sucesso!"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-clientes");
    }

    public void salvarNovo() {
            clienteDAO.create(clienteNovo);
            atualizaClientes();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário criado com sucesso!"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-clientes");
            
        }    

    public void atualizaClientes() {
        listaClientes = clienteDAO.findAll();
    }

    public void criarNovo() {
        this.clienteNovo = new Cliente();
    }

    
}
