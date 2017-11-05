
package keroprecoadmin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import keroprecoadmin.dto.DtoProduto;
import keroprecoadmin.dto.DtoProdutoSupermercado;
import keroprecoadmin.dto.DtoSupermercado;
import keroprecoadmin.dto.DtoUsuario;

public class ProdutoSupermercadoDAO extends DAO{
    
   public List<DtoProdutoSupermercado> listarProdutos(DtoSupermercado supermercado){
       
       List<DtoProdutoSupermercado> listaRetorno = new LinkedList<DtoProdutoSupermercado>();
       String sql = "SELECT idprodutosupermercado ,idproduto,nome, categoria ,preco" +
       "FROM "+ super.getNomeSchemma() +"produtosSupermercado inner join " + 
        super.getNomeSchemma() +"produtos on " + super.getNomeSchemma() +"produtos.'idproduto' = " 
       + super.getNomeSchemma() + "produtosSupermercado.'fkidproduto' where "+ super.getNomeSchemma() +"produtosSupermercado.'fkidsupermercado' = ?";
        
       try {
            
            PreparedStatement pst = super.getPreparedStatement(sql);
            pst.setInt(1,supermercado.getIdSupermercado());
            
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()){
                DtoProduto produto = new DtoProduto(rs.getInt("idproduto"), rs.getString("nome"), rs.getString("categoria"));
                listaRetorno.add(new DtoProdutoSupermercado(rs.getInt("idprodutosupermercado"), produto, supermercado, rs.getDouble("preco")));
            }
            rs.close();
            super.destroyConnection();
        } catch (SQLException e) {
            System.out.println("Erro de Consulta: " + e.getMessage());
        }
       
        return listaRetorno;
   }
    
   public boolean inserir(DtoProdutoSupermercado novoPreco){
          boolean inseriuComSucesso = false;
          

        String sql = "INSERT INTO "+ super.getNomeSchemma() +"produtosSupermercado(fkidproduto, fkidsupermercado, preco) VALUES (?, ?, ?)";
         
            try {
                PreparedStatement ps = super.getPreparedStatement(sql);
                ps.setInt(1,novoPreco.getProduto().getIdProduto());
                ps.setInt(2, novoPreco.getSupermercado().getIdSupermercado());
                ps.setDouble(3, novoPreco.getPreco());
                
                inseriuComSucesso = ps.executeUpdate()>0;
                
//                 try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        novoProduto.setIdProduto(generatedKeys.getInt(1));
//                    }
//                    else {
//                        throw new SQLException("Erro ao Inserir Produto.");
//                    }
//                }
                super.destroyConnection();
               

            } catch (SQLException se){
                System.out.println("Erro de Insert no SQL");
                System.out.println("Mensagem :" +se.getMessage());
                System.out.println("Cod.       :" +se.getErrorCode());
                System.out.println("SQL Sate:" +se.getSQLState());
            }
          return inseriuComSucesso;
      }
      
      public boolean remover(DtoProdutoSupermercado preco){
          boolean removeuComSucesso = false;
          
          String sql = "DELETE FROM " + super.getNomeSchemma()+"produtosSupermercados WHERE idprodutosupermercado = ?";
           
            try {
                PreparedStatement ps = super.getPreparedStatement(sql);
                ps.setInt(1,preco.getIdProdutoSupermercado());
                
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
      
      
      public boolean editar(DtoProdutoSupermercado preco    ){
          boolean editouComSucesso = false;
    
          String sql = "UPDATE " + super.getNomeSchemma() +"produtosSupermercado SET preco = ? WHERE idprodutosupermercado=?"; 
            try {
                PreparedStatement ps = super.getPreparedStatement(sql);
                ps.setDouble(1,preco.getPreco());
                ps.setInt(1,preco.getIdProdutoSupermercado());
    
                
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
}
