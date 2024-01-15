package ca.ulaval.glo2004.domain;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * la fenetre doit etre un trou dans le mur so first of all find a way to make it un trou dans le mur a des dimension que we want
 *
 */

public class Window extends Accessory implements Serializable{
    ArrayList<Position> Window_l = new ArrayList<>();
    public Window(ValeurImperiale width, ValeurImperiale height, Position position,Wall associatedWall){
        super(width,height,position, associatedWall);
        this.color = new Color(173, 216, 230);
    }

    public Window(Position position,Wall associatedWall){
        super(new ValeurImperiale(5),new ValeurImperiale(4),position, associatedWall);
        this.color = new Color(173, 216, 230);
    }

    public Window(Window other) {
        super(other);
        this.Window_l = new ArrayList<>(other.Window_l);
    }


    public ArrayList<Polygone> Window_default(Position position){
        ArrayList<Polygone> polygones = new ArrayList<>();
        ValeurImperiale defaultWidth = new ValeurImperiale(3);
        ValeurImperiale defaultHeight = new ValeurImperiale(5);
        ValeurImperiale x = position.getX();
        ValeurImperiale y = position.getY();
        Window_l.add(new Position(x,y));
        Window_l.add(new Position(x.add( defaultWidth),y));
        Window_l.add(new Position(x.add( defaultWidth), y.add(defaultHeight)));
        Window_l.add(new Position(x,y.add(defaultHeight)));
        polygones.add(new Polygone(Window_l, Color.red));
        return  polygones;

    }
    @Override
    public Window deepCopy() {
        return new Window(this);
    }
}
