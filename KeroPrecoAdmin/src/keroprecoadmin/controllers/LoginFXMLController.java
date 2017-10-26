/*
    Classe Responsável por validar a autenticação do usuário
 */
package keroprecoadmin.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import keroprecoadmin.KeroPrecoAdmin;
import keroprecoadmin.Tela;
import keroprecoadmin.dao.UsuarioDAO;
import keroprecoadmin.dto.DtoUsuario;

/**
 *
 * @author Equipe Optmize
 */
public class LoginFXMLController implements Initializable {
    
    
    private UsuarioDAO usuarioDAO = null;
    
    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txSenha;

    @FXML
    public void efetuarLogin(ActionEvent event) {
        
       // Verificação se os campos de login e senha estão preenchidos 
       if(!txtLogin.getText().isEmpty() && !txSenha.getText().isEmpty()){
           DtoUsuario usuario = new DtoUsuario(); // Criação do DTO de usuário
          
           usuario.setLogin(txtLogin.getText());
           usuario.setSenha(txSenha.getText());
           
           if(usuarioDAO.existe(usuario)){
               irParaHome();
           }else{
               Alert alerta = new Alert(Alert.AlertType.ERROR, "Usuário Não Encontrado", ButtonType.CLOSE);
               alerta.show();
           }
           
       }
    }
    
    public void irParaHome(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/keroprecoadmin/views/HomeFXML.fxml"));
            Scene scene = new Scene(root);
            Tela.getInstancia().getTelaAtual().setScene(scene);
        }catch(Exception e) {
            System.out.println("Ocorreu um erro ao carregar a tela");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO = new UsuarioDAO();
    }    
    
}
