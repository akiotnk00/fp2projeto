/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.UsuarioDAO;
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
public class UsuarioController implements Serializable {

    @Inject
    private UsuarioDAO usuarioDAO;
    private Usuario usuarioSelecionado;
    private Usuario usuarioNovo = new Usuario();

    private List<Usuario> listaUsuarios;

    public UsuarioController() {
        this.usuarioNovo = new Usuario();
    }

    @PostConstruct
    public void init() {
        listaUsuarios = usuarioDAO.findAll();
    }

    public void onRowEdit(RowEditEvent<Usuario> event) {
        FacesMessage msg = new FacesMessage("Usuário Editado", event.getObject().getNomeUsuario());
        usuarioDAO.edit(event.getObject());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<Usuario> event) {
        FacesMessage msg = new FacesMessage("Usuário não editado", event.getObject().getNomeUsuario());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deletarUsuario() {

        System.out.println(this.usuarioSelecionado.getNomeUsuario());

        this.usuarioSelecionado = null;
        usuarioDAO.remove(usuarioSelecionado);
        atualizaUsuarios();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário Removido com sucesso!"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
    }

    public void salvarNovo() {
        this.usuarioNovo.setUltimoAcesso(null);
        if (this.usuarioNovo.getNivelAcesso()==null || this.usuarioNovo.getNivelAcesso().equals("")) {
            this.usuarioNovo = new Usuario();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Selecione um nível de acesso!"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
        } else {

            usuarioDAO.create(usuarioNovo);
            atualizaUsuarios();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário criado com sucesso!"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
            
        }
        

    }

    public void atualizaUsuarios() {
        listaUsuarios = usuarioDAO.findAll();
    }

    public void criarNovo() {
        this.usuarioNovo = new Usuario();
    }

    public Usuario getUsuarioNovo() {
        return usuarioNovo;
    }

    public void setUsuarioNovo(Usuario usuarioNovo) {
        this.usuarioNovo = usuarioNovo;
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

}
