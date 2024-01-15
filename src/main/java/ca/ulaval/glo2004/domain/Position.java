/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain;

//import java.
import ca.ulaval.glo2004.gui.DrawingPanel;
import java.io.Serializable;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author abdellahhanane
 */
public class Position implements Serializable{
    private UUID uuid;
    private ValeurImperiale m_x;
    private ValeurImperiale m_y;

    

    
    public Position()
    {
        m_x = new ValeurImperiale(0);
        m_y = new ValeurImperiale(0);
        this.uuid = UUID.randomUUID();
    }
    

    
    public Position(ValeurImperiale p_x, ValeurImperiale p_y)
    {
//        if mainWi.zoom = true je scale en utilisant la methode presente ds cette classe
//        sinon je continue normale  
//        if (drP.isZoom() == true){
//            double oldZoom = 1.0;
//            double newZoom = drP.getFacteur();
//            
//            m_x = this.updatePosition(p_x, oldZoom, newZoom);
//            m_y = this.updatePosition(p_y, oldZoom, newZoom);
//        } else {
//            m_x = p_x;
//            m_y = p_y;
//        }
        m_x = p_x;
        m_y = p_y;
        this.uuid = UUID.randomUUID();
    }

    public Position(Position other) {
        this.m_x = new ValeurImperiale(other.m_x);
        this.m_y = new ValeurImperiale(other.m_y);

        this.uuid = other.uuid;
    }

    @Override
    public String toString() {
        return "x: " + m_x + ", y: " + m_y;
    }
    
    
    public ValeurImperiale getX()
    {
        return m_x;
    }
    

    public void setX(ValeurImperiale x) 
    {
        this.m_x = x;
    }



//    public PositionImperiale clone(){
//        ValeurImperiale x = this.getX().clone() ;
//        ValeurImperiale y = this.getY().clone() ;
//        PositionImperiale position = new PositionImperiale(x,y) ;
//
//        return position ;
//    }
    public ValeurImperiale getY()
    {
        return m_y;
    }
    

    public void setY(ValeurImperiale y)
    {
        this.m_y = y;
    }
    
    public UUID getUUID() {return uuid;}
    
    public ValeurImperiale updatePosition(double position, double newScale, double offset) {

        double adjusted = (int)((position - offset) / newScale);

        ValeurImperiale viAdjusted = ValeurImperiale.convertDoubleToValeurImperiale(adjusted);

        return viAdjusted;
       }
}

