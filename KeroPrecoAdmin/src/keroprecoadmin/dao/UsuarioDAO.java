/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keroprecoadmin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import keroprecoadmin.AplicacaoUtil;
import keroprecoadmin.dto.DtoUsuario;

/**
 *
 * @author Equipe Optimize
 */
public class UsuarioDAO extends DAO {
    
    public boolean existe(DtoUsuario usuario){
        
        boolean existe = true;
        // Fazer Conexão com o banco de dados e verificar a existencia do registro na base
//        String sql = "SELECT idUsuario,nome,login, FROM " + super.getNomeSchemma() + "tbtUsuarios WHERE login = ? and senha = ?";
//        DtoUsuario retorno = null;
//        
//        try {
//            
//            PreparedStatement pst = super.getPreparedStatement(sql);
//            pst.setString(1,usuario.getLogin());
//            pst.setString(2,usuario.getSenha());
//            
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()){
//              retorno = new DtoUsuario(rs.getInt("idUsuario"),rs.getString("nome"),rs.getString("login"),"");
//            }
//            rs.close();
//            super.destroyConnection();
//        } catch (SQLException e) {
//            System.out.println("Erro de Consulta " + e);
//        }
//        
//        // Caso haja um retorno na consulta com o banco de dados colocamos a variável como verdadeira e retornamos
//        if(retorno != null){
//            AplicacaoUtil.getInstancia().setUsuarioLogado(retorno);
//            existe = true;
//        }
        AplicacaoUtil.getInstancia().setUsuarioLogado(new DtoUsuario(0, "Administrador", "admin", "admin"));
        return existe;
    }
    
}
