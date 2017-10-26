/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keroprecoadmin.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import keroprecoadmin.AplicacaoUtil;

/**
 * FXML Controller class
 *
 * @author Equipe Optimize
 */
public class HomeFXMLController implements Initializable {

    @FXML
    private Label lblSaudacao;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblSaudacao.setText("Olá, " + AplicacaoUtil.getInstancia().getUsuarioLogado().getNome());
    }    
    
}
