/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keroprecoadmin;

/**
 *
 * @author Jeferson
 */
public enum Perfil {
    ADMINISTRADOR(2), USUARIO(1);
    
    private final int codigoPerfil;
    
    Perfil(int codigoPerfil){
        this.codigoPerfil = codigoPerfil;
    }

    public int getCodigoPerfil() {
        return codigoPerfil;
    }
    
}
