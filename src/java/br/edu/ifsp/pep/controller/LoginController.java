/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsp.pep.controller;

import br.edu.ifsp.pep.dao.ClienteDAO;
import br.edu.ifsp.pep.dao.UsuarioDAO;
import br.edu.ifsp.pep.modelo.Cliente;
import br.edu.ifsp.pep.modelo.Conta;
import br.edu.ifsp.pep.modelo.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Akio
 */
@Named
@SessionScoped
public class LoginController implements Serializable {

    private String login;
    private String senha;

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    ClienteDAO clienteDAO;

    private Usuario usuarioPesquisado = new Usuario();
    private Cliente clientePesquisado = new Cliente();

    private Conta contaSelecionada = new Conta();

    public LoginController() {
        this.usuarioDAO = new UsuarioDAO();

    }

    @PostConstruct
    public void init() {

    }

    // Tentativa de login pelo usuario.
    public String autenticar() {

        FacesMessage message = null;
        boolean loggedIn = false;

        try {
            if (usuarioDAO.findByNomeUsuario(login) != null) {
                usuarioPesquisado = usuarioDAO.findByNomeUsuario(login);

                if (usuarioPesquisado.getSenha().equals(senha)) {

                    usuarioPesquisado.setUltimoAcesso(new Date());

                    usuarioDAO.edit(usuarioPesquisado);

                    switch (usuarioPesquisado.getNivelAcesso()) {
                        case "Administrador":
                            return "/administrador/home?faces-redirect=true";
                        default:
                            return "/funcionario/home?faces-redirect=true";
                    }
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro de autenticação", "Senha inválida!");
                }
            }
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro de autenticação", "Login inválido!");
        }

        try{
            if(clienteDAO.findByCpf(login)!= null){
                clientePesquisado = clienteDAO.findByCpf(login);
                if (clientePesquisado.getSenha().equals(senha)) {
                        return "/cliente/home?faces-redirect=true";
                    } else {
                        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro de autenticação", "Senha inválida!");
                    }
                
            }
        }catch (Exception e){
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro de autenticação", "Login inválido!");
        }

               
        
        FacesContext.getCurrentInstance()
                .addMessage(null, message);
        PrimeFaces.current()
                .ajax().addCallbackParam("loggedIn", loggedIn);

        return null;

    }
    // Usuario desloga do sistema.

    public String deslogar() {
        this.usuarioPesquisado = new Usuario();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Informação", "Usuário deslogado com sucesso!"));
        return "/index?faces-redirect=true";

    }

}
