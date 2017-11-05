/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keroprecoadmin.controllers.abstrato;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import keroprecoadmin.AplicacaoUtil;

/**
 *
 * @author Jeferson
 */
public abstract class Controller {
    
    @FXML
    public void irParaHome(ActionEvent event) {
       AplicacaoUtil.getInstancia().irParaTela("HomeFXML.fxml");
    }
    
    @FXML
    public void irParaLogin(ActionEvent event) {
        AplicacaoUtil.getInstancia().irParaTela("LoginFXML.fxml");
        AplicacaoUtil.getInstancia().setUsuarioLogado(null);
    }
    
    @FXML
    public void irParaListarProdutos(ActionEvent event) {
        AplicacaoUtil.getInstancia().irParaTela("ProdutosFXML.fxml");
    }
    
 
    @FXML
    public void irParaListarSupermercados(ActionEvent event) {
        AplicacaoUtil.getInstancia().irParaTela("SupermercadosFXML.fxml");
    }
    
    @FXML
    public void irParaListarUsuarios(ActionEvent event) {
        AplicacaoUtil.getInstancia().irParaTela("UsuarioFXML.fxml");
    }
}
