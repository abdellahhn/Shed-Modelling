package ca.ulaval.glo2004.domain;

import ca.ulaval.glo2004.gui.MainWindow;

import java.awt.*;
//import java.awt.List;
import java.io.Serializable;
import java.util.*;
import java.util.List;


public class Shed implements Serializable{
    public ArrayList<Position> positionList;
    private Map<ToitDirection, List<ToitDirection>> directionMap;
    public MainWindow main;
    public ToitDirection direction = ToitDirection.BACK_TO_FRONT;
    private ValeurImperiale longueur;
    private ValeurImperiale largeur;
    private ValeurImperiale hauteur;
    private ValeurImperiale depth;
    private ValeurImperiale safetyGuard;
    private Map<Facade, Wall> wallList = new HashMap<>();
    private Map<Facade, Roof> roofList = new HashMap<>();
    public Color couleurMateriel = new Color(153, 153, 153);
    public Color couleurToit = new Color(0, 0,255);
    public Color couleurPignon = new Color(255, 255, 153);

    private UUID uuid;
    private double angle = 15;

    public Shed(ValeurImperiale longueur, ValeurImperiale largeur, ValeurImperiale hauteur, ValeurImperiale depth, ValeurImperiale safetyGuard) {
        this.safetyGuard = safetyGuard;
        setSize(longueur, largeur, hauteur, depth);
        resetWalls();
        resetRoofs(this.direction);
        this.uuid = UUID.randomUUID();
    }

    public Shed(Shed other) {
        this.longueur = new ValeurImperiale(other.longueur);
        this.largeur = new ValeurImperiale(other.largeur);
        this.hauteur = new ValeurImperiale(other.hauteur);
        this.depth = new ValeurImperiale(other.depth);
        this.safetyGuard = new ValeurImperiale(other.safetyGuard);
        this.couleurMateriel = new Color(other.couleurMateriel.getRGB());
        this.couleurToit = new Color(other.couleurToit.getRGB());
        this.couleurPignon = new Color(other.couleurPignon.getRGB());
        this.direction = other.direction;
        this.angle = other.angle;
        this.uuid = UUID.randomUUID(); // Generate new UUID for the copy
        this.directionMap = other.directionMap;

        // Deep copy of wallList
        this.wallList = new HashMap<>();
        for (Map.Entry<Facade, Wall> entry : other.wallList.entrySet()) {
            this.wallList.put(entry.getKey(), new Wall(entry.getValue()));
        }

        // Deep copy of roofList
        this.roofList = new HashMap<>();
        for (Map.Entry<Facade, Roof> entry : other.roofList.entrySet()) {
            this.roofList.put(entry.getKey(), new Roof(entry.getValue()));
        }
    }

    public void modifySize(ValeurImperiale length, ValeurImperiale width, ValeurImperiale height, ValeurImperiale depth) {
    Map<Facade, Wall> oldWallList = new HashMap<>(wallList);

    setSize(length, width, height, depth);
    resetRoofs(this.direction);
//    resetWalls();

//    for (Map.Entry<Facade, Wall> entry : oldWallList.entrySet()) {
//        Facade facade = entry.getKey();
//        Wall oldWall = entry.getValue();
//        Wall newWall = wallList.get(facade);
//
//        for (Accessory accessory : oldWall.getAccessories()) {
//
//            newWall.addAccessory(accessory);
//            if (accessory instanceof Door) {
//                ((Door) accessory).setPositionList(new Position(((Door) accessory).getPosition().getX(), ((Door) accessory).getPosition().getY()));
//            }
//        }
//    }
}

    private void resetWalls() {
        wallList.clear();
        Wall frontWall = new Wall(this, Facade.AVANT, safetyGuard);
        Wall backWall = new Wall(this, Facade.ARRIERE, safetyGuard);
        Wall leftWall = new Wall(this, Facade.GAUCHE, safetyGuard);
        Wall rightWall = new Wall(this, Facade.DROITE, safetyGuard);
        wallList.put(Facade.AVANT, frontWall);
        wallList.put(Facade.ARRIERE,backWall);
        wallList.put(Facade.GAUCHE, leftWall);
        wallList.put(Facade.DROITE,rightWall);
    }

    public void resetRoofs(ToitDirection directionN) {
        roofList.clear();
        Roof roofFront = new Roof(this, Facade.AVANT, safetyGuard, angle, directionN);
        Roof roofBack = new Roof(this, Facade.ARRIERE, safetyGuard, angle, directionN);
        Roof roofRight = new Roof(this, Facade.DROITE, safetyGuard, angle, directionN);
        Roof roofLeft = new Roof(this, Facade.GAUCHE, safetyGuard, angle, directionN);

        roofList.put(Facade.AVANT, roofFront);
        roofList.put(Facade.ARRIERE, roofBack);
        roofList.put(Facade.GAUCHE, roofLeft);
        roofList.put(Facade.DROITE, roofRight);


        rotateWalls(directionN);

    }


    public void rotateWalls(ToitDirection direction) {
        Map<Facade, Wall> oldWallList = new HashMap<>(wallList);
        setDirectionMap();

        if (directionMap.get(this.direction).contains(direction)) {
//            ValeurImperiale temp = this.longueur;
//            this.longueur = this.largeur;
//            this.largeur = temp;
            //resetWalls();
            this.setDirectionToit(direction);
            resetWalls();
//            this.largeur = this.longueur;
//            this.longueur = temp;
        } else {
            this.setDirectionToit(direction);
            resetWalls();
        }


        for (Map.Entry<Facade, Wall> entry : oldWallList.entrySet()) {
            Facade facade = entry.getKey();
            Wall oldWall = entry.getValue();
            Wall newWall = wallList.get(facade);

            for (Accessory accessory : oldWall.getAccessories()) {

                newWall.addAccessory(accessory);
                if (accessory instanceof Door) {
                    ((Door) accessory).setPositionList(new Position(((Door) accessory).getPosition().getX(), ((Door) accessory).getPosition().getY()));
                }
            }
        }
    }

    public void setDirectionToit(ToitDirection other){
        this.direction = other;
    }
    
    public void setAngleToit(double nvxAngle){
        this.angle = nvxAngle;
        resetRoofs(this.direction);
    }

    private void setSize(ValeurImperiale length, ValeurImperiale width, ValeurImperiale height, ValeurImperiale depth){
        this.longueur = length;
        this.largeur = width;
        this.hauteur = height;
        this.depth = depth;
    }
    
    public void setSafetyGuard(ValeurImperiale newSafetyGuard) {
        Map<Facade, Wall> oldWallList = new HashMap<>(wallList);

        this.safetyGuard = newSafetyGuard;
        resetWalls();
        resetRoofs(this.direction);
        
        for (Map.Entry<Facade, Wall> entry : oldWallList.entrySet()) {
            Facade facade = entry.getKey();
            Wall oldWall = entry.getValue();
            Wall newWall = wallList.get(facade);

            for (Accessory accessory : oldWall.getAccessories()) {
                if (newWall.doesAccessoryFit(accessory, accessory.getWidth(), accessory.getHeight())) {
                    newWall.addAccessory(accessory);
                }
            }
        }
    }

    public Wall findWall(Position pos, Facade facade) {
        Wall wall = wallList.get(facade);
        if (wall != null && wall.isPositionWithinWall(pos)) {
            return wall;
        }
        return null;
    }

    public String getFacadeAsString(Position position, Facade facade) {
        Wall foundWall = findWall(position, facade);
        if (foundWall != null) {
            return foundWall.getFacade().toString();
        } else {
            return "Not Found";
        }
    }

    public String getHeightAsString(Position position, Facade facade) {
        Wall foundWall = findWall(position, facade);
        if (foundWall != null) {
            return foundWall.getHeight().toString();
        } else {
            return "Not Found";
        }
    }

    public String getWidthAsString(Position position, Facade facade) {
        Wall foundWall = findWall(position, facade);
        if (foundWall != null) {
            return foundWall.getWidth().toString();
        } else {
            return "Not Found";
        }
    }

    public String getDepthAsString(Position position, Facade facade) {
        Wall foundWall = findWall(position, facade);
        if (foundWall != null) {
            return foundWall.getDepth().toString();
        } else {
            return "Not Found";
        }
    }

    public ToitDirection getDirection(){return direction;};
    public ValeurImperiale getLongueur() {return longueur;}
    public ValeurImperiale getLargeur() {return largeur;}
    public ValeurImperiale getHauteur() {return hauteur;}
    public double getAngle(){ return angle;}
    public ValeurImperiale getDepth() {return depth;}
    public ValeurImperiale getSafetyGuard() {return safetyGuard;}
    public Map<Facade, Wall> getWallList() {
        return wallList;
    }
    public Map<Facade, Roof> getRoofList(){
        return roofList;
    }
//    public ArrayList<Pignon> getPignonList() {return pignonList;}
    public UUID getUUID() {
        return uuid;
    }
    private void setDirectionMap(){
        directionMap = new HashMap<>();
        directionMap.put(ToitDirection.BACK_TO_FRONT, Arrays.asList(ToitDirection.LEFT_TO_RIGHT, ToitDirection.RIGHT_TO_LEFT));
        directionMap.put(ToitDirection.FRONT_TO_BACK, Arrays.asList(ToitDirection.LEFT_TO_RIGHT, ToitDirection.RIGHT_TO_LEFT));
        directionMap.put(ToitDirection.LEFT_TO_RIGHT, Arrays.asList(ToitDirection.BACK_TO_FRONT, ToitDirection.FRONT_TO_BACK));
        directionMap.put(ToitDirection.RIGHT_TO_LEFT, Arrays.asList(ToitDirection.BACK_TO_FRONT, ToitDirection.FRONT_TO_BACK));
    }
    
}




