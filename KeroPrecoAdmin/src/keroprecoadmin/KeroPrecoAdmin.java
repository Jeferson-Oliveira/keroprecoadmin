/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keroprecoadmin;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Equipe Optimize
 */
public class KeroPrecoAdmin extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icoKP3.png")));
        //stage.getIcons().add(new Image("file:icoKP3.png"));
        AplicacaoUtil.getInstancia().setTelaAtual(stage);
        AplicacaoUtil.getInstancia().irParaTela("LoginFXML.fxml");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
