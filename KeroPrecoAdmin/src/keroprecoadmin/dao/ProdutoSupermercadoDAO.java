
package keroprecoadmin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import keroprecoadmin.dto.DtoProduto;
import keroprecoadmin.dto.DtoProdutoSupermercado;
import keroprecoadmin.dto.DtoSupermercado;

public class ProdutoSupermercadoDAO extends DAO{
    
   public List<DtoProdutoSupermercado> listarProdutos(DtoSupermercado supermercado){
       
       List<DtoProdutoSupermercado> listaRetorno = new LinkedList<DtoProdutoSupermercado>();
       String sql = "SELECT idprodutosupermercado ,idproduto ,nome, categoria ,preco, validade_preco" +
       " FROM "+ super.getNomeSchemma() +"\"produtosSupermercado\" as tbps inner join " + 
        super.getNomeSchemma() +"produtos as tbp on tbp.idproduto = tbps.fkidproduto where tbps.fkidsupermercado = ?;";
        
       try {
            
            PreparedStatement pst = super.getPreparedStatement(sql);
            pst.setInt(1,supermercado.getIdSupermercado());
            
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()){
                DtoProduto produto = new DtoProduto(rs.getInt("idproduto"), rs.getString("nome"), rs.getString("categoria"));
                Date validade = new Date(); 
                if(rs.getDate("validade_preco") != null){
                    validade = new Date(rs.getDate("validade_preco").getTime());
                }
                listaRetorno.add(new DtoProdutoSupermercado(rs.getInt("idprodutosupermercado"), produto, supermercado, rs.getDouble("preco") , validade));
            }
            rs.close();
            super.destroyConnection();
        } catch (SQLException e) {
            System.out.println("Erro de Consulta: " + e.getMessage());
        }
       
        return listaRetorno;
   }
   
   public List<DtoProdutoSupermercado> listarPrecosPorPeriodo(Date dataInicio , Date dataFim){
       
       String sql = "SELECT idprodutosupermercado ,tbp.idproduto , tbp.nome as nomeproduto ,tbp.categoria, tbps.preco, tbps.validade_preco , tbs.nome as nomesupermercado , tbs.localizacao as localizacaosupermercado , tbs.idsupermercado "
               + " FROM " + this.getNomeSchemma()+ "\"produtosSupermercado\" as tbps inner join " + this.getNomeSchemma()+ "produtos as tbp on tbp.idproduto = tbps.fkidproduto "
               + " inner join " + this.getNomeSchemma() + "supermercados as tbs on tbs.idsupermercado = tbps.fkidsupermercado "
               + " WHERE tbps.validade_preco >= ? and tbps.validade_preco <= ?";      
       
       
       List<DtoProdutoSupermercado> listaRetorno = new LinkedList<DtoProdutoSupermercado>();
     
       try {
            
            PreparedStatement pst = super.getPreparedStatement(sql);
            pst.setDate(1,new java.sql.Date(dataInicio.getTime()));
            pst.setDate(2, new java.sql.Date(dataFim.getTime()));
            
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()){
                
                DtoProduto produto = new DtoProduto();
                produto.setIdProduto(rs.getInt("idproduto"));
                produto.setNome(rs.getString("nomeproduto"));
                produto.setCategoria(rs.getString("categoria"));
             
                DtoSupermercado supermrecado = new DtoSupermercado();
                supermrecado.setIdSupermercado(rs.getInt("idsupermercado"));
                supermrecado.setNome(rs.getString("nomesupermercado"));
                supermrecado.setLocalizacao(rs.getString("localizacaosupermercado"));
                
                DtoProdutoSupermercado produtoSupermercado = new DtoProdutoSupermercado();
                produtoSupermercado.setProduto(produto);
                produtoSupermercado.setSupermercado(supermrecado);
                produtoSupermercado.setPreco(rs.getDouble("preco"));
                produtoSupermercado.setDataValidade(new java.util.Date(rs.getDate("validade_preco").getTime()));
                
                listaRetorno.add(produtoSupermercado);
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
          

        String sql = "INSERT INTO "+ super.getNomeSchemma() +"\"produtosSupermercado\"(fkidproduto, fkidsupermercado, preco , validade_preco) VALUES (?, ?, ? , ?)";
         
            try {
                PreparedStatement ps = super.getPreparedStatement(sql);
                ps.setInt(1,novoPreco.getProduto().getIdProduto());
                ps.setInt(2, novoPreco.getSupermercado().getIdSupermercado());
                ps.setDouble(3, novoPreco.getPreco());
                ps.setDate(4, new java.sql.Date(novoPreco.getDataValidade().getTime()));
                
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
          
          String sql = "DELETE FROM " + super.getNomeSchemma()+"\"produtosSupermercado\" WHERE idprodutosupermercado = ?";
             
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
    
          String sql = "UPDATE " + super.getNomeSchemma() +"\"produtosSupermercado\" SET preco = ? WHERE idprodutosupermercado=?"; 
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
