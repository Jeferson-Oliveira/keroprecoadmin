
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
import keroprecoadmin.dao.SupermercadoDAO;
import keroprecoadmin.dto.DtoProduto;
import keroprecoadmin.dto.DtoSupermercado;


public class SupermercadosFXMLController extends Controller implements Initializable {
 
    private SupermercadoDAO supermercadoDAO;
    
    @FXML
    private TableView<DtoSupermercado> tbSupermercados;

    @FXML
    private TextField txLocalizacao;

    @FXML
    private TextField txNome;
    

    @FXML
    void adionarSupermecado(ActionEvent event) {
        
         // Verifica se os campos de nome e categoria foram preenchidos
        if(!txNome.getText().isEmpty() && !txLocalizacao.getText().isEmpty()){
            //IR no DTO e InserirRegistro no banco
            
             //Tenta Inserir o produto no banco de dados
            DtoSupermercado novoSupermercado = new DtoSupermercado (txNome.getText(), txLocalizacao.getText());
            if(supermercadoDAO.inserir(novoSupermercado)){
                
                //Adiciona o produto na tabela
                //tbProdutos.getItems().add(novoProduto);
               
                //Verificar retorno da id do elemento inserido
                tbSupermercados.setItems(obterSupermercadoCadastrados());
                
                // limpa os campos que antes estavam preenchidos
                txNome.clear();
                txLocalizacao.clear();
            }else {
                AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Não foi possível inserir o registro!");
            }
           
        }
    }

    @FXML
    void removerSupermercado(ActionEvent event) {
          // Se existe algum item selecionado
        if(!tbSupermercados.getSelectionModel().isEmpty()) {
            ObservableList<DtoSupermercado> itensDaLista = tbSupermercados.getItems();
            ObservableList<DtoSupermercado> itensSelecionados = tbSupermercados.getSelectionModel().getSelectedItems();
            
            //IR no DTO e remover os itens selecionados
            if(supermercadoDAO.remover(itensSelecionados.get(0))){
                //Remove os itens que estão selecionados da tabela
                itensSelecionados.forEach(itensDaLista::remove);
                tbSupermercados.getSelectionModel().clearSelection();
            }else {
                AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Não foi possível remover o registro!");
            }
            
        }
    }
    
    @Override   //metodo que é chamado antes da tela ser exibida
    public void initialize(URL url, ResourceBundle rb) {
       
        this.supermercadoDAO = new SupermercadoDAO();
        
       configurarTabela();
       tbSupermercados.setItems(obterSupermercadoCadastrados());
        
    }
        
        
    
    private ObservableList<DtoSupermercado> obterSupermercadoCadastrados() {
       ObservableList<DtoSupermercado> supermercado = FXCollections.observableArrayList();
       supermercado.addAll(this.supermercadoDAO.listarTodos());
       return supermercado;
    }
    
    
    private void configurarTabela(){
        tbSupermercados.setEditable(true);
                
      // Adicionando Colunas na tabela de produtos
        TableColumn<DtoSupermercado , String > colunaNome = new TableColumn("Nome");
        colunaNome.setMinWidth(200);
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaNome.setOnEditCommit((cell) ->{
            DtoSupermercado supermercadoEditado = ((DtoSupermercado) cell.getTableView().getItems().get(cell.getTablePosition().getRow()));
            supermercadoEditado.setNome(cell.getNewValue());
            
            if(!supermercadoDAO.editar(supermercadoEditado)){
             supermercadoEditado.setNome(cell.getOldValue());
             AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Não foi possível atualizar o registros");
            }
            
        });
        
        TableColumn<DtoSupermercado , String > colunaLocalizacao = new TableColumn("Localização");
        colunaLocalizacao.setMinWidth(200);
        colunaLocalizacao.setCellValueFactory(new PropertyValueFactory<>("localizacao"));
        colunaLocalizacao.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaLocalizacao.setOnEditCommit((cell) ->{
            DtoSupermercado supermercadoEditado = ((DtoSupermercado) cell.getTableView().getItems().get(cell.getTablePosition().getRow()));
            supermercadoEditado.setLocalizacao(cell.getNewValue());
            
            if(!supermercadoDAO.editar(supermercadoEditado)){
             supermercadoEditado.setLocalizacao(cell.getOldValue());
             AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Não foi possível atualizar o registros");
            }
            
        });
        tbSupermercados.getColumns().addAll(colunaNome , colunaLocalizacao);
        }
    }