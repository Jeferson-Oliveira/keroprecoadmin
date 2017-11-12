/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keroprecoadmin.dto;

import keroprecoadmin.Perfil;

/**
 *
 * @author Equipe Optimize
 */
public class DtoUsuario {
    
    private int idUsuario;
    private Perfil perfil;
    private String nome;
    private String login;
    private String usuario;
    private String senha;



    public DtoUsuario() {
    }

    public DtoUsuario(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        
    }
    
    public DtoUsuario(int idUsuario, String nome, String login, String senha) {
        this(nome, login, senha);        
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
        login = usuario;
    }
        public String getUsuario() {
        return usuario;
    }
    public String setUsuario(){
        this.usuario = usuario;
        return null;
    }    

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    
}
