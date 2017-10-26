/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keroprecoadmin;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jeferson
 */
public class Tela {
    
    private static Tela instancia;
    private Stage telaAtual;

    private Tela() {
    }
    
    public static Tela getInstancia()
    {
        if(instancia == null){
            instancia = new Tela();
        }
        return instancia;
    }

    public void setTelaAtual(Stage telaAtual) {
        this.telaAtual = telaAtual;
    }

    public Stage getTelaAtual() {
        return telaAtual;
    }
    
    
}
