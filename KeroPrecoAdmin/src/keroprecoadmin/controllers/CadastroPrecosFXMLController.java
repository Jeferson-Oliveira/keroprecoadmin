
package keroprecoadmin.controllers;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import keroprecoadmin.AplicacaoUtil;
import keroprecoadmin.controllers.abstrato.Controller;
import keroprecoadmin.dao.ProdutoDAO;
import keroprecoadmin.dao.ProdutoSupermercadoDAO;
import keroprecoadmin.dao.SupermercadoDAO;
import keroprecoadmin.dto.DtoProduto;
import keroprecoadmin.dto.DtoProdutoSupermercado;
import keroprecoadmin.dto.DtoSupermercado;



public class CadastroPrecosFXMLController extends Controller implements  Initializable {
   
    private ProdutoDAO produtoDAO;
    private SupermercadoDAO supermercadoDAO;
    private ProdutoSupermercadoDAO produtoSupermercadoDAO;
    
    @FXML
    private ComboBox<DtoSupermercado> cbSupermercados;

    @FXML
    private TableView<DtoProdutoSupermercado> tbProdutosSupermercados;

    @FXML
    private ComboBox<DtoProduto> cbProdutos;

    @FXML
    private TextField txPreco;

    
    @FXML
    void carregarProdutosSupermercado(ActionEvent event) {
        DtoSupermercado supermercadoSelecionado = (DtoSupermercado)cbSupermercados.getValue();
        if(supermercadoSelecionado != null && supermercadoSelecionado.getIdSupermercado()>0){
            ObservableList<DtoProdutoSupermercado> precos = FXCollections.observableArrayList();
            precos.addAll(produtoSupermercadoDAO.listarProdutos(supermercadoSelecionado));
            tbProdutosSupermercados.setItems(precos);
        }
    }
    
    
    @FXML
    void cadastrarPreco(ActionEvent event) {
        
        String tx = txPreco.getText();
        tx = tx.replace(',',   '.');
       
        if(cbSupermercados.getValue() != null && cbProdutos.getValue() != null && precoDigitadoValido(tx)) {
        
            DtoSupermercado supermercadoSelecionado = cbSupermercados.getValue();
            DtoProduto produtoSelecionado = cbProdutos.getValue();
            
            Double preco = Double.parseDouble(tx); /// Validar conteudo de texto
            
            if(existeProdutoNoSupermercado(produtoSelecionado)){
                AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Este Produto já foi cadastrado para o supermercado selecionado.");
            }else {
                DtoProdutoSupermercado novoPreco = new DtoProdutoSupermercado(produtoSelecionado, supermercadoSelecionado, preco);
                if(produtoSupermercadoDAO.inserir(novoPreco)){
                    carregarProdutosSupermercado(null);
                }
            }
        }
    }
    
    private boolean precoDigitadoValido(String preco){
        boolean valido = true; 
        int quantidadeNumeros = 0;
        
        
        if(preco.isEmpty()){
            return false;
        }
        
        for(Character c : preco.toCharArray()){
            if(Character.isAlphabetic(c) && c != '.'){
                valido = false;
            }
            if(Character.isDigit(c)){
                quantidadeNumeros++;
            }
        }
        
        if(quantidadeNumeros == 0){
            return false;
        }
        
        return valido;
    }  
    private boolean existeProdutoNoSupermercado(DtoProduto produto){
        
        for (DtoProdutoSupermercado produtoSupermercado : tbProdutosSupermercados.getItems()) {
            if(produtoSupermercado.getProduto().getIdProduto() == produto.getIdProduto()){
                return true;
            }
        }
        
        return false;
    }

    @FXML
    void removerPreco(ActionEvent event) {
         // Se existe algum item selecionado
        if(!tbProdutosSupermercados.getSelectionModel().isEmpty()) {
            ObservableList<DtoProdutoSupermercado> itensDaLista = tbProdutosSupermercados.getItems();
            ObservableList<DtoProdutoSupermercado> itensSelecionados = tbProdutosSupermercados.getSelectionModel().getSelectedItems();
            
            //IR no DTO e remover os itens selecionados
            if(produtoSupermercadoDAO.remover(itensSelecionados.get(0))){
                //Remove os itens que estão selecionados da tabela
                itensSelecionados.forEach(itensDaLista::remove);
                tbProdutosSupermercados.getSelectionModel().clearSelection();
            }else {
                AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Não foi possível remover o registro!");
            }
            
        }
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoDAO = new ProdutoDAO();
        supermercadoDAO = new SupermercadoDAO();
        produtoSupermercadoDAO = new ProdutoSupermercadoDAO();
        configurarCombos();
        configurarTabela();
    }    
    
   public void configurarTabela(){
       tbProdutosSupermercados.setEditable(true);
                
      // Adicionando Colunas na tabela de produtos
        TableColumn<DtoProdutoSupermercado , String > colunaNome = new TableColumn("Nome");
        colunaNome.setMinWidth(100);
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
    
        TableColumn<DtoProdutoSupermercado , Double > colunaPreco = new TableColumn("Preco");
        colunaPreco.setMinWidth(100);
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        //colunaPreco.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaPreco.setOnEditCommit((cell) ->{
            DtoProdutoSupermercado produtoEditado = ((DtoProdutoSupermercado) cell.getTableView().getItems().get(cell.getTablePosition().getRow()));
            produtoEditado.setPreco(cell.getNewValue());
            
            if(!produtoSupermercadoDAO.editar(produtoEditado)){
             produtoEditado.setPreco(cell.getOldValue());
             AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Não foi possível atualizar o registros");
            }
            
        });
        tbProdutosSupermercados.getColumns().addAll(colunaNome , colunaPreco);
    }
   
    
    public void configurarCombos(){
        
        //configuracao cbSpermercados
        cbSupermercados.getItems().addAll(obterListaSupermercados());
        cbSupermercados.setConverter(new StringConverter <DtoSupermercado>() {
           public String toString(DtoSupermercado object) {
               if(object!=null){
                   return object.getNome() + " " + object.getLocalizacao();
               }
               return null;
           }

           @Override
           public DtoSupermercado fromString(String string) {
               return null; //To change body of generated methods, choose Tools | Templates.
           }

         
       });
                
        cbProdutos.getItems().addAll(obterListaProdutos());
       
            cbProdutos.setConverter(new StringConverter<DtoProduto>() {
            public String toString(DtoProduto object) {
               if(object!=null){
                   return object.getNome();
                }
               return null;
           }

           @Override
           public DtoProduto fromString(String string) {
               return null; //To change body of generated methods, choose Tools | Templates.
           }
       });
        

    }
    //retornando valores para copular o combo box
    private ObservableList <DtoProduto> obterListaProdutos() {
       ObservableList<DtoProduto> listaRetorno = FXCollections.observableArrayList();
       listaRetorno.addAll(produtoDAO.listarTodos());
       return listaRetorno;
        
    }

    private ObservableList <DtoSupermercado> obterListaSupermercados() {
       ObservableList<DtoSupermercado> listaRetorno = FXCollections.observableArrayList();
       listaRetorno.addAll(supermercadoDAO.listarTodos());
       return listaRetorno;
    }
}

