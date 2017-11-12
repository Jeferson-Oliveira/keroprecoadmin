/*
    Classe Responsável por validar a autenticação do usuário
 */
package keroprecoadmin.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import keroprecoadmin.AplicacaoUtil;
import keroprecoadmin.Perfil;
import keroprecoadmin.controllers.abstrato.Controller;
import keroprecoadmin.dao.UsuarioDAO;
import keroprecoadmin.dto.DtoUsuario;

/**
 *
 * @author Equipe Optimize
 */
public class LoginFXMLController extends Controller implements Initializable {
    
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
           
               if(AplicacaoUtil.getInstancia().getUsuarioLogado().getPerfil().getCodigoPerfil() == (Perfil.ADMINISTRADOR.getCodigoPerfil())){
                   super.irParaHome(event);
               } else {
                   super.irParaListarPrecos(event);
               }
           
           }else{
              AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.ERROR, "Usuário não encontrado!");
           }
           
       }
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO = new UsuarioDAO();
    }    
    
     @FXML
    void irParaCadastroUsuario(ActionEvent event) {
        AplicacaoUtil.getInstancia().irParaTela("CadastroUsuarioFXML.fxml");
    }
    
}
