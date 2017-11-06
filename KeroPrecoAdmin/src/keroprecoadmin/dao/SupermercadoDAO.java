
package keroprecoadmin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import keroprecoadmin.dto.DtoSupermercado;



public class SupermercadoDAO extends DAO{
    public boolean inserir(DtoSupermercado novoSupermercado){
          boolean inseriuComSucesso = false;
          
          String sql = "INSERT INTO " + super.getNomeSchemma()+"supermercados (nome,localizacao) VALUES(?,?)";
           
            try {
                PreparedStatement ps = super.getPreparedStatement(sql);
                ps.setString(1,novoSupermercado.getNome());
                ps.setString(2, novoSupermercado.getLocalizacao());
                
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
      
      public boolean remover(DtoSupermercado supermercado){
          boolean removeuComSucesso = false;
          
          String sql = "DELETE FROM " + super.getNomeSchemma()+"supermercados WHERE idsupermercado = ?";
           
            try {
                PreparedStatement ps = super.getPreparedStatement(sql);
                ps.setInt(1,supermercado.getIdSupermercado());
                
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
      
      
      public boolean editar(DtoSupermercado supermercado){
          boolean editouComSucesso = false;
    
          String sql = "UPDATE " + super.getNomeSchemma()+"supermercados SET nome=? , localizacao = ? WHERE idsupermercado = ?"; 
            try {
                PreparedStatement ps = super.getPreparedStatement(sql);
                ps.setString(1,supermercado.getNome());
                ps.setString(2,supermercado.getLocalizacao());
                ps.setInt(3,supermercado.getIdSupermercado());
                
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
      
      public List<DtoSupermercado> listarTodos() {
          
        String sql = "SELECT idsupermercado,nome,localizacao FROM " + super.getNomeSchemma()+"supermercados";
        List<DtoSupermercado> lstret = new LinkedList<>();
                
        try {
            
            ResultSet rs = super.getStatement().executeQuery(sql);
            while (rs.next()){
                lstret.add(new DtoSupermercado(rs.getInt("idsupermercado"), 
                                            rs.getString("nome"), 
                                            rs.getString("localizacao")));
            }
            rs.close();
            super.destroyConnection();
        } catch (SQLException e) {
            System.out.println("Erro de Consulta " + e);
        }
        return lstret;
    }
}
