
package keroprecoadmin.dto;

import java.util.Objects;


public class DtoProdutoSupermercado {
    
    private int idProdutoSupermercado;
    private DtoProduto produto;
    private DtoSupermercado supermercado;
    private double preco;

    //Construtores
    public DtoProdutoSupermercado() {
    
    }

    public DtoProdutoSupermercado(DtoProduto produto, DtoSupermercado supermercado, double preco) {
        this.produto = produto;
        this.supermercado = supermercado;
        this.preco = preco;
    }
    
    public DtoProdutoSupermercado(int idProdutoSupermercado, DtoProduto produto, DtoSupermercado supermercado, double preco) {
        this(produto , supermercado , preco);
        this.idProdutoSupermercado = idProdutoSupermercado;
    }


    
    //Metodos
    
    public void setIdProdutoSupermercado(int idProdutoSupermercado) {
        this.idProdutoSupermercado = idProdutoSupermercado;
    }

    public int getIdProdutoSupermercado() {
        return idProdutoSupermercado;
    }

    public DtoProduto getProduto() {
        return produto;
    }

    public void setProduto(DtoProduto produto) {
        this.produto = produto;
    }

    public DtoSupermercado getSupermercado() {
        return supermercado;
    }

    public void setSupermercado(DtoSupermercado supermercado) {
        this.supermercado = supermercado;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public String getNomeProduto(){
        return this.produto.getNome();
    }

}
