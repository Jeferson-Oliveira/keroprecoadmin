/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keroprecoadmin.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Equipe Optimize
 */
public abstract class DAO {
    
    private Connection con = null;
    private String nomeSchemma = "db_keropreco.";
    
    public Statement getStatement() throws SQLException {
        return this.startConnection().createStatement();
    }
    
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return this.startConnection().prepareStatement(sql);
    }
    
    private Connection startConnection(){
       
        String user = "postgres";
        String pws = "sql1433";
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5432/postgres";
      
        try {
            
            if(this.con == null){
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, pws);
            }
            
        } catch (ClassNotFoundException cnfex) {
            System.out.println("Erro de Driver");
        } catch (SQLException sqlex){
            System.out.println("Erro SQL");
        } catch(Exception e){
            System.out.println("Erro Geral");
        }
        
        return con;
    }
    
    public void destroyConnection(){
        try {
            if(this.con != null){
                con.close();
                this.con = null;
            }
        } catch (SQLException e) {
            System.out.println("Erro Ao Fechar A Conexão");
            e.printStackTrace();
        }
    }

    public String getNomeSchemma() {
        return nomeSchemma;
    }
    
}
