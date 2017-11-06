
package keroprecoadmin.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import keroprecoadmin.AplicacaoUtil;
import keroprecoadmin.controllers.abstrato.Controller;
import keroprecoadmin.dao.UsuarioDAO;
import keroprecoadmin.dto.DtoUsuario;


public class UsuarioFXMLController extends Controller implements Initializable {
    
    private UsuarioDAO usuarioDAO;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    
    @FXML
    private Button btnCriarUsuario;

    @FXML
    private Button btnRemUsuario;

    @FXML
    private PasswordField pswConfSenha;

    @FXML
    private PasswordField pswSenha;

    @FXML
    private TableView<DtoUsuario> tbUsuarios;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtUsuario;

    @FXML
    void adicionarNovoUsuario(ActionEvent event) {
        
        // Verifica se os campos de nome,usuario,senha e confirmação de senha foram preenchidos
        if(!txtNome.getText().isEmpty() && !txtUsuario.getText().isEmpty() && !pswSenha.getText().isEmpty() && !pswConfSenha.getText().isEmpty()){ 
            //if (pswSenha.getText() == pswConfSenha.getText()){
                //IR no DTO e InserirRegistro no banco
            
                 //Tenta Inserir o usuario no banco de dados
                 DtoUsuario novoUsuario = new DtoUsuario(txtNome.getText(), txtUsuario.getText(),pswSenha.getText());
                    if(usuarioDAO.inserir(novoUsuario)){
                
               
               
                    //Verificar retorno da id do elemento inserido
                    tbUsuarios.setItems(obterUsuariosCadastrados());
                
                    // limpa os campos que antes estavam preenchidos
                    txtNome.clear();
                    txtUsuario.clear();
                    pswSenha.clear();
                    pswConfSenha.clear();
                
                    }//fim do if (inserirNovoUsuario)
                     else{
                    AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Não foi possível criar usuario");
                    }
                //}//fim do if (conferindo as senhas) 
                // else{
               // AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "As senhas não conferem!");
               // }//fim do else(conferindo as senhas)
            }//fim do if (campos vazios)
        else {
        AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Campos Vazios! favor preencher os campos");
        }
           
    }
    
    
    private ObservableList<DtoUsuario> obterUsuariosCadastrados(){
        ObservableList<DtoUsuario> usuarios = FXCollections.observableArrayList();
        usuarios.addAll(this.usuarioDAO.listarTodos());
        return usuarios;
    }
    
    
    @FXML
    void removerUsuarioSelecionado(ActionEvent event) {
        
        // Se existe algum item selecionado
        if(!tbUsuarios.getSelectionModel().isEmpty()) {
            ObservableList<DtoUsuario> itensDaLista = (ObservableList<DtoUsuario>) tbUsuarios.getItems();
            ObservableList<DtoUsuario> itensSelecionados = (ObservableList<DtoUsuario>) tbUsuarios.getSelectionModel().getSelectedItems();
            
            //IR no DTO e remover os itens selecionados
            if(usuarioDAO.remover(itensSelecionados.get(0))){
                //Remove os itens que estão selecionados da tabela
                itensSelecionados.forEach(itensDaLista::remove);
                tbUsuarios.getSelectionModel().clearSelection();
            }else {
                AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Não foi possível remover o registro!");
            }
            
        }
    }

    
    
    public void initialize() {
        assert btnCriarUsuario != null : "fx:id=\"btnCriarUsuario\" was not injected: check your FXML file 'UsuarioFXML.fxml'.";
        assert btnRemUsuario != null : "fx:id=\"btnRemUsuario\" was not injected: check your FXML file 'UsuarioFXML.fxml'.";
        assert pswConfSenha != null : "fx:id=\"pswConfSenha\" was not injected: check your FXML file 'UsuarioFXML.fxml'.";
        assert pswSenha != null : "fx:id=\"pswSenha\" was not injected: check your FXML file 'UsuarioFXML.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'UsuarioFXML.fxml'.";
        assert txtUsuario != null : "fx:id=\"txtUsuario\" was not injected: check your FXML file 'UsuarioFXML.fxml'.";


    }  

    @Override
     public void initialize(URL url, ResourceBundle rb) {
        
        this.usuarioDAO = new UsuarioDAO();
        
        configurarTabela();
        tbUsuarios.setItems(obterUsuariosCadastrados());
        
    }

     
    
    
private void configurarTabela(){
        tbUsuarios.setEditable(true);
                
      // Adicionando Colunas na tabela de produtos
     
        
        TableColumn<DtoUsuario , String > colunaNome = new TableColumn("Nome");
        colunaNome.setMinWidth(200);
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaNome.setOnEditCommit((cell) ->{
            DtoUsuario usuarioEditado = ((DtoUsuario) cell.getTableView().getItems().get(cell.getTablePosition().getRow()));
            usuarioEditado.setNome(cell.getNewValue());
            
            if(!usuarioDAO.editar(usuarioEditado)){
             usuarioEditado.setNome(cell.getOldValue());
             AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Não foi possível atualizar o registros!");
            }
            
        });
        
        TableColumn<DtoUsuario , String > colunaUsuario = new TableColumn("Usuario");
        colunaUsuario.setMinWidth(200);
        colunaUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colunaUsuario.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaUsuario.setOnEditCommit((cell) ->{
            DtoUsuario usuarioEditado = ((DtoUsuario) cell.getTableView().getItems().get(cell.getTablePosition().getRow()));
            usuarioEditado.setUsuario(cell.getNewValue());
            
            if(!usuarioDAO.editar(usuarioEditado)){
             usuarioEditado.setUsuario(cell.getOldValue());
             AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Não foi possível atualizar o registros");
            }
            
           
        });
        
        tbUsuarios.getColumns().addAll(colunaNome , colunaUsuario);
    }
}
