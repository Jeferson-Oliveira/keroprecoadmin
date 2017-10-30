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
import keroprecoadmin.dto.DtoProduto;

/**
 *
 * @author Jeferson
 */
 public class ProdutoDAO extends DAO {
     
      public boolean inserir(DtoProduto novoProduto){
          boolean inseriuComSucesso = false;
          
          String sql = "INSERT INTO " + super.getNomeSchemma()+"produtos (nome,categoria) VALUES(?,?)";
           
            try {
                PreparedStatement ps = super.getPreparedStatement(sql);
                ps.setString(1,novoProduto.getNome());
                ps.setString(2, novoProduto.getCategoria());
                
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
      
      public boolean remover(DtoProduto novoProduto){
          boolean removeuComSucesso = false;
          
          String sql = "DELETE FROM " + super.getNomeSchemma()+"produtos WHERE idproduto = ?";
           
            try {
                PreparedStatement ps = super.getPreparedStatement(sql);
                ps.setInt(1,novoProduto.getIdProduto());
                
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
      
      
      public boolean editar(DtoProduto novoProduto){
          boolean editouComSucesso = false;
    
          String sql = "UPDATE " + super.getNomeSchemma()+"produtos SET nome=? , categoria = ? WHERE idproduto = ?"; 
            try {
                PreparedStatement ps = super.getPreparedStatement(sql);
                ps.setString(1,novoProduto.getNome());
                ps.setString(2,novoProduto.getCategoria());
                ps.setInt(3,novoProduto.getIdProduto());
                
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
      
      public List<DtoProduto> listarTodos() {
          
        String sql = "SELECT idproduto,nome,categoria FROM " + super.getNomeSchemma()+"produtos";
        List<DtoProduto> lstret = new LinkedList<>();
                
        try {
            
            ResultSet rs = super.getStatement().executeQuery(sql);
            while (rs.next()){
                lstret.add(new DtoProduto(rs.getInt("idproduto"), 
                                            rs.getString("nome"), 
                                            rs.getString("categoria")));
            }
            rs.close();
            super.destroyConnection();
        } catch (SQLException e) {
            System.out.println("Erro de Consulta " + e);
        }
        return lstret;
    }
}
