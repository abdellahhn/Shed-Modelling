package ca.ulaval.glo2004.domain;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class AcessoryDTO implements Serializable {

    protected Position position;
    protected ValeurImperiale width;
    protected ValeurImperiale height;
    protected Color color = Color.RED;
    protected ArrayList<Position> positionList;
    public UUID Uuid;

    public AcessoryDTO(Accessory ac) {
        this.position = ac.getPosition();
        this.width = ac.getWidth();
        this.height = ac.getHeight();

//        Position pt1 = new Position(position.getX(), position.getY());
//        Position pt2 = new Position(position.getX().add(width), position.getY());
//        Position pt3 = new Position(position.getX().add(width), position.getY().add(height));
//        Position pt4 = new Position(position.getX(), position.getY().add(width));
//        ac.positionList.add(pt1);
//        ac.positionList.add(pt2);
//        ac.positionList.add(pt3);
//        ac.positionList.add(pt4);
        //this one im doubting it
        this.positionList = ac.positionList;
        Uuid = ac.getUUID();
    }
    public boolean selected(PositionDTO selection) {
        int comparisonResultMinX = selection.getX().compareTo(this.position.getX());
        int comparisonResultMaxX = selection.getX().compareTo(this.position.getX().add(width));
        int comparisonResultMinY = selection.getY().compareTo(this.position.getY());
        int comparisonResultMaxY = selection.getY().compareTo(this.position.getY().add(height));
        return comparisonResultMinX >= 0 && comparisonResultMaxX <= 0 && comparisonResultMinY >= 0 && comparisonResultMaxY <= 0;
    }
    
}
