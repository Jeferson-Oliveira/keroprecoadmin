/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keroprecoadmin;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import keroprecoadmin.dao.UsuarioDAO;
import keroprecoadmin.dto.DtoUsuario;

/**
 *
 * @author Equipe Optimize
 */
public class AplicacaoUtil {
    
    //Instância única que será guardada
    private static AplicacaoUtil instancia = null;
    
    private Stage telaAtual = null;
    private DtoUsuario usuarioLogado = null;

    private AplicacaoUtil() {
        this.telaAtual = new Stage();
    }
    
    public static AplicacaoUtil getInstancia()
    {
        if(instancia == null){
            instancia = new AplicacaoUtil();
        }
        return instancia;
    }
    
    public void irParaTela(String nomeTela) {
        try {
            // Carrega o arquivo da tela desejada
            Parent root = FXMLLoader.load(getClass().getResource("/keroprecoadmin/views/".concat(nomeTela)));
            
            //Cria uma nova cena para a tela e adiciona no "palco" (telaAtual)
            Scene scene = new Scene(root);
            this.telaAtual.setScene(scene);
            
            //Exibe o palco caso o mesmo não esteja sendo exibido
            if(!this.telaAtual.isShowing()){
                this.telaAtual.show();
            }
        }catch(IOException e){
            System.err.println("Ocorreu um erro ao tentar navegar para tela: ".concat(nomeTela));
            e.printStackTrace();
        }
    }

    public void setTelaAtual(Stage telaAtual) {
        this.telaAtual = telaAtual;
    }

    public Stage getTelaAtual() {
        return telaAtual;
    }

    public DtoUsuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(DtoUsuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
    
    
}
