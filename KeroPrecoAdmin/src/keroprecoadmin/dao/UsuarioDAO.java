/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keroprecoadmin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import keroprecoadmin.AplicacaoUtil;
import keroprecoadmin.Perfil;
import keroprecoadmin.dto.DtoUsuario;

/**
 *
 * @author Equipe Optimize
 */
public class UsuarioDAO extends DAO {
    
    public DtoUsuario existe(DtoUsuario usuario){
        
        boolean existe = false;
        // Fazer Conexão com o banco de dados e verificar a existencia do registro na base
        String sql = "SELECT nome,login,perfil FROM " + super.getNomeSchemma() + "usuarios WHERE login = ? and senha = ?";
        DtoUsuario retorno = null;
        
        try {
            
            PreparedStatement pst = super.getPreparedStatement(sql);
            pst.setString(1,usuario.getLogin());
            pst.setString(2,usuario.getSenha());
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
              retorno = new DtoUsuario(rs.getString("nome"),rs.getString("login"),"");
              if(rs.getInt("perfil") == Perfil.ADMINISTRADOR.getCodigoPerfil()){
               retorno.setPerfil(Perfil.ADMINISTRADOR);
             }else{
               retorno.setPerfil(Perfil.USUARIO);
             }
            }
            rs.close();
            super.destroyConnection();
        } catch (SQLException e) {
            System.out.println("Erro de Consulta: " + e.getMessage());
        }
        
        // Caso haja um retorno na consulta com o banco de dados colocamos a variável como verdadeira e retornamos
        if(retorno != null){
            AplicacaoUtil.getInstancia().setUsuarioLogado(retorno);
            existe = true;
        }
        return retorno;
    }
    
    public boolean existeUserName(DtoUsuario usuario){
        
        boolean existe = false;
        // Fazer Conexão com o banco de dados e verificar a existencia do registro na base
        String sql = "SELECT login,nome,perfil FROM " + super.getNomeSchemma() + "usuarios WHERE login = ?";
        DtoUsuario retorno = null;
        
        try {
            
            PreparedStatement pst = super.getPreparedStatement(sql);
            pst.setString(1,usuario.getLogin());

            
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
              retorno = new DtoUsuario(rs.getString("nome"),rs.getString("login"),"");
              if(rs.getInt("perfil") == Perfil.ADMINISTRADOR.getCodigoPerfil()){
               retorno.setPerfil(Perfil.ADMINISTRADOR);
             }else{
               retorno.setPerfil(Perfil.USUARIO);
             }
              usuario = retorno;
            }
            rs.close();
            super.destroyConnection();
        } catch (SQLException e) {
            System.out.println("Erro de Consulta: " + e.getMessage());
        }
        
        // Caso haja um retorno na consulta com o banco de dados colocamos a variável como verdadeira e retornamos
        if(retorno != null){
            AplicacaoUtil.getInstancia().setUsuarioLogado(retorno);
            existe = true;
        }
        return existe;
    }
    
    public boolean inserir(DtoUsuario novoUsuario){
          boolean inseriuComSucesso = false;
          
          String sql = "INSERT INTO " + super.getNomeSchemma()+"usuarios (nome,login,senha,perfil) VALUES(?,?,?,?)";
           
            try {
                PreparedStatement ps = super.getPreparedStatement(sql);
                ps.setString(1,novoUsuario.getNome());
                ps.setString(2, novoUsuario.getLogin());
                ps.setString(3, novoUsuario.getSenha());
                ps.setInt(4, novoUsuario.getPerfil().getCodigoPerfil());
                
                ps.executeUpdate();
                
//                 try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        novoProduto.setIdProduto(generatedKeys.getInt(1));
//                    }
//                    else {
//                        throw new SQLException("Erro ao Inserir Produto.");
//                    }
//                }
                super.destroyConnection();
                inseriuComSucesso = true;

            } catch (SQLException se){
                System.out.println("Erro de Insert no SQL");
                System.out.println("Mensagem :" +se.getMessage());
                System.out.println("Cod.       :" +se.getErrorCode());
                System.out.println("SQL Sate:" +se.getSQLState());
            }
          return inseriuComSucesso;
      }
      
      public boolean remover(DtoUsuario usuario){
          boolean removeuComSucesso = false;
          
          String sql = "DELETE FROM " + super.getNomeSchemma()+"usuarios WHERE idusuario = ?";
           
            try {
                PreparedStatement ps = super.getPreparedStatement(sql);
                ps.setInt(1,usuario.getIdUsuario());
                
                removeuComSucesso = ps.executeUpdate() > 0;
                super.destroyConnection();

            } catch (SQLException se){
                System.out.println("Erro de Insert no SQL");
                System.out.println("Mensagem :" +se.getMessage());
                System.out.println("Cod.       :" +se.getErrorCode());
                System.out.println("SQL Sate:" +se.getSQLState());
            }
          return removeuComSucesso;
      }
      
      
      public boolean editar(DtoUsuario usuario){
          boolean editouComSucesso = false;
    
          String sql = "UPDATE " + super.getNomeSchemma()+"usuarios SET nome=? , login = ? WHERE idusuario = ?"; 
            try {
                PreparedStatement ps = super.getPreparedStatement(sql);
                ps.setString(1,usuario.getNome());
                ps.setString(2,usuario.getLogin());
                ps.setInt(3,usuario.getIdUsuario());
                
                editouComSucesso = ps.executeUpdate() > 0;
                super.destroyConnection();

            } catch (SQLException se){
                System.out.println("Erro de Insert no SQL");
                System.out.println("Mensagem :" +se.getMessage());
                System.out.println("Cod.       :" +se.getErrorCode());
                System.out.println("SQL Sate:" +se.getSQLState());
            }
          return editouComSucesso;
      }
      
      public List<DtoUsuario> listarTodos() {
          
        String sql = "SELECT idusuario,nome,login FROM " + super.getNomeSchemma()+"usuarios";
        List<DtoUsuario> lstret = new LinkedList<>();
                
        try {
            
            ResultSet rs = super.getStatement().executeQuery(sql);
            while (rs.next()){
                lstret.add(new DtoUsuario(rs.getInt("idusuario"), 
                                            rs.getString("nome"), 
                                            rs.getString("login") , ""));
            }
            rs.close();
            super.destroyConnection();
        } catch (SQLException e) {
            System.out.println("Erro de Consulta " + e);
        }
        return lstret;
    }
}
