/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package ca.ulaval.glo2004.domain;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.UUID;

///**
// *
// * @author abdellahhanane
// */
//public class Polygone {
//    
//    private ArrayList<Position> m_listPosition = new ArrayList<>();
//    private Color color;
//    
//    
//    public Polygone(ArrayList<Position> p_listPoint,
//            Color couleur){
//        m_listPosition = p_listPoint;
//        color = couleur;
//    }
//    
//    public ArrayList<Position> getListPoint(){
//        return m_listPosition;
//    }
//    
//        public Color getColor()
//    {
//        return color;
//    }
//    
//    public void setColor(Color color){
//        this.color = color;
//    }
//    
//}

public class Polygone
{
    private ArrayList<Position> m_listePositionImpériaux = new ArrayList<>(); 
    private Color m_couleur;
    private UUID uuid;
    

    
    public Polygone(ArrayList<Position> p_listePointsImpriaux, Color p_couleur)
    {
        m_listePositionImpériaux = p_listePointsImpriaux;
        m_couleur = p_couleur;
        this.uuid = UUID.randomUUID();
    }
    

    public ArrayList<Position> getListePointsImperiaux()
    {
        return m_listePositionImpériaux;
    }
    

    public Color getColor()
    {
        return m_couleur;
    }
    
    public void setColor(Color color){
        this.m_couleur = color;
    }

    public UUID getUUID() {
        return uuid;
    }
}
