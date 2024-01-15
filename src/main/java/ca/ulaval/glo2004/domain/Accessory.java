/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
/**
 *
 * @author Imane Baqri
 */
/**
 *dans cette class i need to put :
 * la distance minimale entre 2 accessoire est 3
 * la distance minimale entre un accessoire et bord du mur est 3
 *deplacer accessoire
 * supprimer accessoire
 * selectionner un accessoire
 */
public abstract class Accessory implements Serializable{
    protected Position position;
    protected ValeurImperiale width;
    protected ValeurImperiale height;
    protected Color color;
    protected boolean isSelected;
    protected Wall wall;
    protected ArrayList<Position> positionList = new ArrayList<>();
    protected boolean isValid = false;
    private UUID uuid;
    public Accessory(ValeurImperiale width, ValeurImperiale height, Position position, Wall associatedWall) {
        setPosition(position);
        setWall(associatedWall);
        setDimensions(width, height);
        setPositionList(position);
    }

    public Accessory(Accessory other) {
        this.position = new Position(other.position);
        this.width = new ValeurImperiale(other.width);
        this.height = new ValeurImperiale(other.height);
        this.color = new Color(other.color.getRGB());
        this.isSelected = other.isSelected;
        // wall is set separately by the Wall copy constructor
        this.wall = null;

        // Deep copy of positionList
        this.positionList = new ArrayList<>();
        for (Position pos : other.positionList) {
            this.positionList.add(new Position(pos));
        }

        // Do not copy UUID to ensure uniqueness
        this.uuid = UUID.randomUUID();
    }

    // Assuming you have a deepCopy method in Accessory
    public abstract Accessory deepCopy();

//    public Accessory(Accessory other) {
//        // Copying position manually
//        if (other.position != null) {
//            this.position = new Position(other.position.getX(), other.position.getY());
//        } else {
//            this.position = null;
//        }
//        this.width = new ValeurImperiale(other.width);
//        this.height = new ValeurImperiale(other.height);
//        this.color = new Color(other.color.getRGB());
//        this.isSelected = other.isSelected;
//        this.wall = null;
//
//        // Deep copy of positionList
//        this.positionList = new ArrayList<>();
//        for (Position pos : other.positionList) {
//            this.positionList.add(new Position(pos.getX(), pos.getY()));
//        }
//
//        this.uuid = UUID.fromString(other.uuid.toString());
//    }

    public void move(Position newPosition){
        setPosition(newPosition);
        setPositionList(newPosition);
        System.out.println(positionList);
    }

    protected void setPosition(Position newPosition) {
        this.position = newPosition;
    }
    protected void setPositionList(Position position) {
        positionList.clear();
        Position pt1 = new Position(position.getX(), position.getY());
        Position pt2 = new Position(position.getX().add(width), position.getY());
        Position pt3 = new Position(position.getX().add(width), position.getY().add(height));
        Position pt4 = new Position(position.getX(), position.getY().add(height));
        positionList.add(pt1);
        positionList.add(pt2);
        positionList.add(pt3);
        positionList.add(pt4);
    }

    public ArrayList<ValeurImperiale> getDimensions(){
        ArrayList<ValeurImperiale> dimensions = new ArrayList<>();
        dimensions.add(this.width);
        dimensions.add(this.height);
        return dimensions;
    }

    public void setDimensions(ValeurImperiale width, ValeurImperiale height){
        this.width = width;
        this.height = height;
        setPositionList(this.position);
    }
    public void setWall(Wall associatedWall) {
        this.wall = associatedWall;
    }

    public boolean selected(Position selection) {
        int comparisonResultMinX = selection.getX().compareTo(this.position.getX());
        int comparisonResultMaxX = selection.getX().compareTo(this.position.getX().add(width));
        int comparisonResultMinY = selection.getY().compareTo(this.position.getY());
        int comparisonResultMaxY = selection.getY().compareTo(this.position.getY().add(height));
        return comparisonResultMinX >= 0 && comparisonResultMaxX <= 0 && comparisonResultMinY >= 0 && comparisonResultMaxY <= 0;
    }
    public ValeurImperiale calculerDistanceEntreAccessoires(Position positionAccessoire1, Position positionAccessoire2) {
        // Calculez la distance entre les deux accessoires sur l'axe horizontal et vertical
        ValeurImperiale distanceX = positionAccessoire1.getX().substract(positionAccessoire2.getX());
        ValeurImperiale distanceY = positionAccessoire1.getY().substract(positionAccessoire2.getY());

        // Retournez la distance calculée
        return new ValeurImperiale(distanceX.getValeurEntiere(), distanceX.getFractionTop(), distanceX.getFractionBot());
    }
    public void deleted(){
        if (wall != null){
            wall.removeAccessory(this);
        }
    }
    public ValeurImperiale getWidth(){return this.width;}
    public ValeurImperiale getHeight(){return this.height;}
    public  Position getPosition (){return this.position;}
    public UUID getUUID() {
        return uuid;
    }
    public ArrayList<Position> getPositionList() {
        return positionList;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Accessory otherAccessory = (Accessory) obj;
        // Compare the positionList of this accessory with the other accessory
        return Objects.equals(this.positionList, otherAccessory.positionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionList);
    }

}



