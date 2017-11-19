/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * FXML Controller class
 *
 * @author henri
 */
public class CadastroUsuarioFXMLController extends Controller implements Initializable {
    
    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private PasswordField txtConfSenha;

    @FXML
    private TextField txtNome;
    
    private UsuarioDAO usuarioDAO;

    @FXML
    void efetuarCadastro(ActionEvent event) {
        if(dadosValidos()){
            DtoUsuario novoUsuario = new DtoUsuario(txtNome.getText(), txtLogin.getText(), txtSenha.getText());
            novoUsuario.setPerfil(Perfil.USUARIO);
            
            if(usuarioDAO.inserir(novoUsuario)){
                AplicacaoUtil.getInstancia().setUsuarioLogado(novoUsuario);
                irParaListarPrecos(event);
            }else{
                AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.ERROR, "Não foi possível efetuar o cadastro");
            }
          
        }else{
            AplicacaoUtil.getInstancia().adicionarMensagemSimples(Alert.AlertType.INFORMATION, "Preencha os campos corretamente");
        }
    }
    
    private boolean dadosValidos(){
        if(!txtSenha.getText().isEmpty() && !txtConfSenha.getText().isEmpty()){
            if(txtSenha.getText().equals(txtConfSenha.getText())){
                if(!txtNome.getText().isEmpty() && !txtLogin.getText().isEmpty()){
                    return true;
                }
            }
        } 
        
        return false;
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        usuarioDAO = new UsuarioDAO();
    }    
    
}
