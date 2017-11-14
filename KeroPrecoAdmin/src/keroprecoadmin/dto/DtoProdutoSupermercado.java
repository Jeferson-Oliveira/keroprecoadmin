
package keroprecoadmin.dto;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DtoProdutoSupermercado {
    
    private int idProdutoSupermercado;
    private DtoProduto produto;
    private DtoSupermercado supermercado;
    private double preco;
    private Date dataValidade = new Date();

    //Construtores
    public DtoProdutoSupermercado() {
    
    }

    public DtoProdutoSupermercado(DtoProduto produto, DtoSupermercado supermercado, double preco , Date validade) {
        this.produto = produto;
        this.supermercado = supermercado;
        this.preco = preco;
        this.dataValidade = validade;
    }
    
    public DtoProdutoSupermercado(int idProdutoSupermercado, DtoProduto produto, DtoSupermercado supermercado, double preco , Date validade) {
        this(produto , supermercado , preco , validade);
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

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Date getDataValidade() {
        return dataValidade;
    }
    
    public String getDataValidadeFormatada() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = dateFormat.format(dataValidade);
        return dataFormatada;
    }
    

}
