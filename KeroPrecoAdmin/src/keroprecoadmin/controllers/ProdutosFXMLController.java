/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keroprecoadmin.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import keroprecoadmin.AplicacaoUtil;
import keroprecoadmin.controllers.abstrato.Controller;
import keroprecoadmin.dao.ProdutoDAO;
import keroprecoadmin.dto.DtoProduto;

/**
 * FXML Controller class
 *
 * @author Jeferson
 */
public class ProdutosFXMLController extends Controller implements Initializable {

    private ProdutoDAO produtoDAO;
    
    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtNome;

    @FXML
    private TableView<DtoProduto> tbProdutos;


    @FXML
    void adicionarNovoProduto(ActionEvent event) {
        
        // Verifica se os campos de nome e categoria foram preenchidos
        if(!txtNome.getText().isEmpty() && !txtCategoria.getText().isEmpty()){
            //IR no DTO e InserirRegistro no banco
            
             //Tenta Inserir o produto no banco de dados
            DtoProduto novoProduto = new DtoProduto(txtNome.getText(), txtCategoria.getText());
            if(produtoDAO.inserir(novoProduto)){
                
                //Adiciona o produto na tabela
                //tbProdutos.getItems().add(novoProduto);
               
                //Verificar retorno da id do elemento inserido
                tbProdutos.setItems(obterProdutosCadastrados());
                
                // limpa os campos que antes estavam preenchidos
                txtNome.clear();
                txtCategoria.clear();
            }else {
                AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Não foi possível inserir o registro!");
            }
           
        }
    }

    @FXML
    void removerProdutoSelecionado(ActionEvent event) {
        
        // Se existe algum item selecionado
        if(!tbProdutos.getSelectionModel().isEmpty()) {
            ObservableList<DtoProduto> itensDaLista = tbProdutos.getItems();
            ObservableList<DtoProduto> itensSelecionados = tbProdutos.getSelectionModel().getSelectedItems();
            
            //IR no DTO e remover os itens selecionados
            if(produtoDAO.remover(itensSelecionados.get(0))){
                //Remove os itens que estão selecionados da tabela
                itensSelecionados.forEach(itensDaLista::remove);
                tbProdutos.getSelectionModel().clearSelection();
            }else {
                AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Não foi possível remover o registro!");
            }
            
        }
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.produtoDAO = new ProdutoDAO();
        
        configurarTabela();
        tbProdutos.setItems(obterProdutosCadastrados());
        
    }


    private ObservableList<DtoProduto> obterProdutosCadastrados() {
       ObservableList<DtoProduto> produtos = FXCollections.observableArrayList();
       produtos.addAll(this.produtoDAO.listarTodos());
       return  produtos;
    }
    
    
    private void configurarTabela(){
        tbProdutos.setEditable(true);
                
      // Adicionando Colunas na tabela de produtos
        TableColumn<DtoProduto , String > colunaNome = new TableColumn("Nome");
        colunaNome.setMinWidth(200);
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaNome.setOnEditCommit((cell) ->{
            DtoProduto produtoEditado = ((DtoProduto) cell.getTableView().getItems().get(cell.getTablePosition().getRow()));
            produtoEditado.setNome(cell.getNewValue());
            
            if(!produtoDAO.editar(produtoEditado)){
             produtoEditado.setNome(cell.getOldValue());
             AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Não foi possível atualizar o registro!s");
            }
            
        });
        
        TableColumn<DtoProduto , String > colunaCategoria = new TableColumn("Categoria");
        colunaCategoria.setMinWidth(200);
        colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colunaCategoria.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaCategoria.setOnEditCommit((cell) ->{
            DtoProduto produtoEditado = ((DtoProduto) cell.getTableView().getItems().get(cell.getTablePosition().getRow()));
            produtoEditado.setCategoria(cell.getNewValue());
            
            if(!produtoDAO.editar(produtoEditado)){
             produtoEditado.setCategoria(cell.getOldValue());
             AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Não foi possível atualizar o registros");
            }
            
        });
        tbProdutos.getColumns().addAll(colunaNome , colunaCategoria);
    }
    
}
