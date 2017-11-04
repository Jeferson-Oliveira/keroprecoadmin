
package keroprecoadmin.dto;

public class DtoSupermercado {
    private int idSupermercado;
    private String nome;
    private String localizacao;

    public DtoSupermercado() {//construtor 
        this.idSupermercado = 0;
        this.nome = "";
        this.localizacao = "";
    }

    public DtoSupermercado(String nome, String localizacao) {//construtor
        this.nome = nome;
        this.localizacao = localizacao;
    }

    public DtoSupermercado(int idSupermercado, String nome, String localizacao) {//construtor
        this(nome,localizacao);//chamada para construtor da propria classe
        this.idSupermercado = idSupermercado;
       
    }
    
    //metodos
    public int getIdSupermercado() {
        return idSupermercado;
    }

    public void setIdSupermercado(int idSupermercado) {
        this.idSupermercado = idSupermercado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
    
    
    
}
