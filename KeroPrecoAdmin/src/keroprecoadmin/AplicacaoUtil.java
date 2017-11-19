/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keroprecoadmin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
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
        }catch(Exception e){
            System.err.println("Ocorreu um erro ao tentar navegar para tela: ".concat(nomeTela).concat(" ".concat(e.getMessage())));
           // e.printStackTrace();
        }
    }
    
    public void adicionarMensagemSimples(Alert.AlertType tipo , String mensagem){
         Alert alerta = new Alert(tipo, mensagem, ButtonType.CLOSE);
         alerta.show();
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
