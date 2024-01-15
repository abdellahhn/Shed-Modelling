/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain;

/**
 *
 * @author abdellahhanane
 */
public enum Facade {
    
    AVANT,
    ARRIERE,
    GAUCHE,
    DROITE;

    public static Facade fromString(String str){
        switch (str){
            case "MurDroite":
                return DROITE;
            case "MurGauche":
                return GAUCHE;
            case "MurFace":
                return AVANT;
            case "MurArriere":
                return ARRIERE;
            default:
                throw new IllegalArgumentException("Introuvable: " + str);
        }
    }
   
}
