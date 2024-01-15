package ca.ulaval.glo2004.domain;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Door extends Accessory implements Serializable{

    public Door(ValeurImperiale width, ValeurImperiale height, ValeurImperiale x, Wall associatedWall){
        super(width, height, new Position(x, associatedWall.getHeight().substract(height).substract(associatedWall.getMinimumAccessoryDistance())), associatedWall);
        this.color = new Color(100, 42, 42);
    }

    public Door(Door other) {
        super(other);
    }

    protected void setPositionList(Position position) {
        positionList.clear();
        Position pt1 = new Position(position.getX(), wall.getHeight().substract(height));
        Position pt2 = new Position(position.getX().add(width), wall.getHeight().substract(height));
        Position pt3 = new Position(position.getX().add(width), wall.getHeight());
        Position pt4 = new Position(position.getX(), wall.getHeight());
        positionList.add(pt1);
        positionList.add(pt2);
        positionList.add(pt3);
        positionList.add(pt4);
        position.setY(wall.getHeight().substract(height));
    }


    /**
     * Constructor for a default width and height of 38" by 88"
     *
     * @param x The x coordintate of the door
     * @param associatedWall The Wall it is placed on.
     */
    public Door(ValeurImperiale x, Wall associatedWall){
        super(new ValeurImperiale(0,38,12), new ValeurImperiale(0,88,12), new Position(x, associatedWall.getHeight().substract(associatedWall.getMinimumAccessoryDistance()).substract(new ValeurImperiale(0,88,12))), associatedWall);
        this.color = new Color(100, 42, 42);
    }

//    public Polygone equivPoly(ValeurImperiale width, ValeurImperiale height, Position position, Wall associatedWall){
//        Polygone door_poly = new Polygone(this.list_pos_sommets, Color.red);
//        return door_poly;
//    }

    public void move(Position newPosition) {
        super.move(new Position(newPosition.getX(), wall.getHeight().substract(this.height)));
   }

    @Override
    public Door deepCopy() {
        return new Door(this);
    }
}