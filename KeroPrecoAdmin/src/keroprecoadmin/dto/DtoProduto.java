/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keroprecoadmin.dto;

/**
 *
 * @author Jeferson
 */
public class DtoProduto {
    
    private int idProduto;
    private String nome;
    private String categoria;

    public DtoProduto() {
        this.idProduto = 0;
        this.nome = "";
        this.categoria = ""; 
    }

    public DtoProduto(String nome, String categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    public DtoProduto(int idProduto, String nome, String categoria) {
        this(nome,categoria);
        this.idProduto = idProduto;
       
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
}
