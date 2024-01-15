package ca.ulaval.glo2004.domain;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class Wall implements Serializable{
    private Shed shed;
    private List<Accessory> accessoryList;
    public Accessory selectedAccessory;
    private ValeurImperiale width;
    private ValeurImperiale depth;
    private ValeurImperiale height;
    public Facade facade;
    private Map<String, ArrayList<ArrayList<Position>>> viewPoints = new HashMap<>();
    public static ValeurImperiale DEFAULT_MINIMUM_DISTANCE = new ValeurImperiale(0, 3, 12);
    private ValeurImperiale minimumAccessoryDistance = new ValeurImperiale(0, 3, 12);
    private static ValeurImperiale BASE_X = new ValeurImperiale(0);
    private static ValeurImperiale BASE_Y = new ValeurImperiale(0);




    public Wall(Shed chalet, Facade facade, ValeurImperiale safetyGuard) {
        this.facade = facade;
        this.accessoryList = new LinkedList<Accessory>();
        this.height = chalet.getHauteur();
        this.depth = chalet.getDepth();
        this.shed = chalet;

        Position topLeft = null;
        Position botLeft = null;
        Position topRight = null;
        Position botRight = null;
        ArrayList<Position> frontView1 = new ArrayList<>();
        ArrayList<Position> frontView2 = new ArrayList<>();
        ArrayList<Position> frontView3 = new ArrayList<>();
        ArrayList<ArrayList<Position>> view = new ArrayList<>();

        if (chalet.direction.equals(ToitDirection.FRONT_TO_BACK) || chalet.direction.equals(ToitDirection.BACK_TO_FRONT)){
            if (facade == Facade.AVANT || facade == Facade.ARRIERE) {
                this.width = chalet.getLongueur();
                topLeft = new Position(BASE_X, BASE_Y);
                botLeft = new Position(BASE_X, this.height.add(BASE_Y));
                topRight = new Position(this.width.add(BASE_X), BASE_Y);
                botRight = new Position(this.width.add(BASE_X), this.height.add(BASE_Y));

                frontView1.add(topLeft);
                frontView1.add(topRight);
                frontView1.add(botRight);
                frontView1.add(botLeft);
                view.add(frontView1);

            } else {
                this.width = chalet.getLargeur().substract(chalet.getDepth());
                Position p1_rec1 = new Position(BASE_X, BASE_Y);
                Position p2_rec1 = new Position((BASE_X).add((this.depth).divideByInt(2).substract((safetyGuard).divideByInt(2))), BASE_Y);
                Position p3_rec1 = new Position((BASE_X).add((this.depth).divideByInt(2).substract((safetyGuard).divideByInt(2))), (BASE_Y.add(chalet.getHauteur())));
                Position p4_rec1 = new Position((BASE_X), BASE_Y.add(chalet.getHauteur()));

                frontView1.add(p1_rec1);
                frontView1.add(p2_rec1);
                frontView1.add(p3_rec1);
                frontView1.add(p4_rec1);
                view.add(frontView1);

                Position p1_rec2 = new Position(BASE_X.add((this.depth).divideByInt(2).add((safetyGuard).divideByInt(2))), BASE_Y);
                Position p2_rec2 = new Position(BASE_X.add((this.depth).divideByInt(2)).add(this.width).substract(safetyGuard.divideByInt(2)), BASE_Y);
                Position p3_rec2 = new Position(BASE_X.add((this.depth).divideByInt(2)).add(this.width).substract(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getHauteur()));
                Position p4_rec2 = new Position(BASE_X.add((this.depth).divideByInt(2).add((safetyGuard).divideByInt(2))), BASE_Y.add(chalet.getHauteur()));


                frontView2.add(p1_rec2);
                frontView2.add(p2_rec2);
                frontView2.add(p3_rec2);
                frontView2.add(p4_rec2);
                view.add(frontView2);

                Position p1_rec3 = new Position(BASE_X.add(this.depth).divideByInt(2).add(this.width).add(safetyGuard.divideByInt(2)), BASE_Y);
                Position p2_rec3 = new Position(BASE_X.add(chalet.getLargeur()), BASE_Y);
                Position p3_rec3 = new Position(BASE_X.add(chalet.getLargeur()), BASE_Y.add(chalet.getHauteur()));
                Position p4_rec3 = new Position(BASE_X.add(this.depth).divideByInt(2).add(this.width).add(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getHauteur()));


                frontView3.add(p1_rec3);
                frontView3.add(p2_rec3);
                frontView3.add(p3_rec3);
                frontView3.add(p4_rec3);
                view.add(frontView3);

            }
        } else {
            if (facade == Facade.GAUCHE || facade == Facade.DROITE) {
                this.width = chalet.getLargeur();
                topLeft = new Position(BASE_X, BASE_Y);
                botLeft = new Position(BASE_X, this.height.add(BASE_Y));
                topRight = new Position(this.width.add(BASE_X), BASE_Y);
                botRight = new Position(this.width.add(BASE_X), this.height.add(BASE_Y));

                frontView1.add(topLeft);
                frontView1.add(topRight);
                frontView1.add(botRight);
                frontView1.add(botLeft);
                view.add(frontView1);

            } else {
                this.width = chalet.getLongueur().substract(chalet.getDepth());
                Position p1_rec1 = new Position(BASE_X, BASE_Y);
                Position p2_rec1 = new Position((BASE_X).add((this.depth).divideByInt(2).substract((safetyGuard).divideByInt(2))), BASE_Y);
                Position p3_rec1 = new Position((BASE_X).add((this.depth).divideByInt(2).substract((safetyGuard).divideByInt(2))), (BASE_Y.add(chalet.getHauteur())));
                Position p4_rec1 = new Position((BASE_X), BASE_Y.add(chalet.getHauteur()));

                frontView1.add(p1_rec1);
                frontView1.add(p2_rec1);
                frontView1.add(p3_rec1);
                frontView1.add(p4_rec1);
                view.add(frontView1);

                Position p1_rec2 = new Position(BASE_X.add((this.depth).divideByInt(2).add((safetyGuard).divideByInt(2))), BASE_Y);
                Position p2_rec2 = new Position(BASE_X.add((this.depth).divideByInt(2)).add(this.width).substract(safetyGuard.divideByInt(2)), BASE_Y);
                Position p3_rec2 = new Position(BASE_X.add((this.depth).divideByInt(2)).add(this.width).substract(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getHauteur()));
                Position p4_rec2 = new Position(BASE_X.add((this.depth).divideByInt(2).add((safetyGuard).divideByInt(2))), BASE_Y.add(chalet.getHauteur()));


                frontView2.add(p1_rec2);
                frontView2.add(p2_rec2);
                frontView2.add(p3_rec2);
                frontView2.add(p4_rec2);
                view.add(frontView2);

                Position p1_rec3 = new Position(BASE_X.add(this.depth).divideByInt(2).add(width).add(safetyGuard.divideByInt(2)), BASE_Y);
                Position p2_rec3 = new Position(BASE_X.add(chalet.getLongueur()), BASE_Y);
                Position p3_rec3 = new Position(BASE_X.add(chalet.getLongueur()), BASE_Y.add(chalet.getHauteur()));
                Position p4_rec3 = new Position(BASE_X.add(this.depth).divideByInt(2).add(width).add(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getHauteur()));


                frontView3.add(p1_rec3);
                frontView3.add(p2_rec3);
                frontView3.add(p3_rec3);
                frontView3.add(p4_rec3);
                view.add(frontView3);

            }
        }
        viewPoints.put("front", view);
        removeGroove(chalet, facade, safetyGuard);
    }

    // constructeur copy imanes stuff
    public Wall(Wall other) {
        this.width = new ValeurImperiale(other.width);
        this.depth = new ValeurImperiale(other.depth);
        this.height = new ValeurImperiale(other.height);
        this.facade = other.facade;
        this.shed = other.shed; // This can reference the original Shed object as Shed is not being changed.
        this.minimumAccessoryDistance = new ValeurImperiale(other.minimumAccessoryDistance);

        // Deep copy of accessoryList
        this.accessoryList = new ArrayList<>();
        for (Accessory accessory : other.accessoryList) {
            Accessory copiedAccessory = accessory.deepCopy();
            copiedAccessory.setWall(this); // Associate the accessory with the new wall
            this.accessoryList.add(copiedAccessory);
        }

        // Deep copy of viewPoints
        this.viewPoints = new HashMap<>();
        for (Map.Entry<String, ArrayList<ArrayList<Position>>> entry : other.viewPoints.entrySet()) {
            ArrayList<ArrayList<Position>> copiedViews = new ArrayList<>();
            for (ArrayList<Position> viewGroup : entry.getValue()) {
                ArrayList<Position> copiedViewGroup = new ArrayList<>();
                for (Position position : viewGroup) {
                    copiedViewGroup.add(new Position(position));
                }
                copiedViews.add(copiedViewGroup);
            }
            this.viewPoints.put(entry.getKey(), copiedViews);
        }
    }

//    public Wall(Wall other) {
//        this.width = new ValeurImperiale(other.width);
//        this.depth = new ValeurImperiale(other.depth);
//        this.height = new ValeurImperiale(other.height);
//        this.facade = other.facade;
//        this.minimumAccessoryDistance = new ValeurImperiale(other.minimumAccessoryDistance);
//
//        // Deep copy of accessoryList
////        this.accessoryList = new LinkedList<>();
////        for (Accessory accessory : other.accessoryList) {
////            this.accessoryList.add(new Accessory(accessory)); // Assuming Accessory has a copy constructor
////        }
//
//        // Deep copy of viewPoints
//        this.viewPoints = new HashMap<>();
//        for (Map.Entry<String, ArrayList<ArrayList<Position>>> entry : other.viewPoints.entrySet()) {
//            ArrayList<ArrayList<Position>> copiedList = new ArrayList<>();
//            for (ArrayList<Position> positions : entry.getValue()) {
//                ArrayList<Position> copiedPositions = new ArrayList<>(positions); // Assuming Position is immutable
//                copiedList.add(copiedPositions);
//            }
//            this.viewPoints.put(entry.getKey(), copiedList);
//        }
//    }

    public void removeGroove(Shed chalet, Facade facade) {
        ValeurImperiale halfDepth = chalet.getDepth().divideByInt(2);
        ArrayList<Position> topView = new ArrayList<>();
        ArrayList<ArrayList<Position>> view = new ArrayList<ArrayList<Position>>();

        Position pt1 = null;
        Position pt2 = null;
        Position pt3 = null;
        Position pt4 = null;
        Position pt5 = null;
        Position pt6 = null;
        Position pt7 = null;
        Position pt8 = null;


        switch (facade) {
            case ARRIERE:
                pt1 = new Position(BASE_X, BASE_Y);
                pt2 = new Position(BASE_X, BASE_Y.add(halfDepth));
                pt3 = new Position(BASE_X.add(halfDepth), BASE_Y.add(halfDepth));
                pt4 = new Position(BASE_X.add(halfDepth), BASE_Y.add(chalet.getDepth()));
                pt5 = new Position(BASE_X.add(chalet.getLongueur()), BASE_Y);
                pt6 = new Position(BASE_X.add(chalet.getLongueur()), BASE_Y.add(halfDepth));
                pt7 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth), BASE_Y.add(halfDepth));
                pt8 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth), BASE_Y.add(chalet.getDepth()));
                break;

            case AVANT:
                pt1 = new Position(BASE_X, BASE_Y.add(chalet.getLargeur()));
                pt2 = new Position(BASE_X, BASE_Y.add(chalet.getLargeur()).substract(halfDepth));
                pt3 = new Position(BASE_X.add(halfDepth), BASE_Y.add(chalet.getLargeur()).substract(halfDepth));
                pt4 = new Position(BASE_X.add(halfDepth), BASE_Y.add(chalet.getLargeur()).substract(chalet.getDepth()));
                pt5 = new Position(BASE_X.add(chalet.getLongueur()), BASE_Y.add(chalet.getLargeur()));
                pt6 = new Position(BASE_X.add(chalet.getLongueur()), BASE_Y.add(chalet.getLargeur()).substract(halfDepth));
                pt7 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth), BASE_Y.add(chalet.getLargeur()).substract(halfDepth));
                pt8 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth), BASE_Y.add(chalet.getLargeur()).substract(chalet.getDepth()));
                break;

            case GAUCHE:
                pt1 = new Position(BASE_X, BASE_Y.add(halfDepth));
                pt2 = new Position(BASE_X.add(halfDepth), BASE_Y.add(halfDepth));
                pt3 = new Position(BASE_X.add(halfDepth), BASE_Y.add(chalet.getDepth()));
                pt4 = new Position(BASE_X.add(chalet.getDepth()), BASE_Y.add(chalet.getDepth()));
                pt5 = new Position(BASE_X, BASE_Y.add(chalet.getLargeur()).substract(halfDepth));
                pt6 = new Position(BASE_X.add(halfDepth), BASE_Y.add(chalet.getLargeur()).substract(halfDepth));
                pt7 = new Position(BASE_X.add(halfDepth), BASE_Y.add(chalet.getLargeur()).substract(chalet.getDepth()));
                pt8 = new Position(BASE_X.add(chalet.getDepth()), BASE_Y.add(chalet.getLargeur()).substract(chalet.getDepth()));
                break;

            case DROITE:
                pt1 = new Position(BASE_X.add(chalet.getLongueur()), BASE_Y.add(halfDepth));
                pt2 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth), BASE_Y.add(halfDepth));
                pt3 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth), BASE_Y.add(chalet.getDepth()));
                pt4 = new Position(BASE_X.add(chalet.getLongueur()).substract(chalet.getDepth()), BASE_Y.add(chalet.getDepth()));
                pt5 = new Position(BASE_X.add(chalet.getLongueur()), BASE_Y.add(chalet.getLargeur()).substract(halfDepth));
                pt6 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth), BASE_Y.add(chalet.getLargeur()).substract(halfDepth));
                pt7 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth), BASE_Y.add(chalet.getLargeur()).substract(chalet.getDepth()));
                pt8 = new Position(BASE_X.add(chalet.getLongueur()).substract(chalet.getDepth()), BASE_Y.add(chalet.getLargeur()).substract(chalet.getDepth()));
                break;
        }
        topView.add(pt1);
        topView.add(pt5);
        topView.add(pt6);
        topView.add(pt7);
        topView.add(pt8);
        topView.add(pt4);
        topView.add(pt3);
        topView.add(pt2);
        view.add(topView);
        viewPoints.put("top", view);
    }

    public void removeGroove(Shed chalet, Facade facade, ValeurImperiale safetyGuard) {
        ValeurImperiale halfDepth = chalet.getDepth().divideByInt(2);
        ArrayList<Position> topView = new ArrayList<>();
        ArrayList<ArrayList<Position>> view = new ArrayList<>();

        Position pt1 = null;
        Position pt2 = null;
        Position pt3 = null;
        Position pt4 = null;
        Position pt5 = null;
        Position pt6 = null;
        Position pt7 = null;
        Position pt8 = null;


        switch (facade) {
            case ARRIERE:
                pt1 = new Position(BASE_X, BASE_Y);
                pt2 = new Position(BASE_X, BASE_Y.add(halfDepth).substract(safetyGuard.divideByInt(2)));
                pt3 = new Position(BASE_X.add(halfDepth).add(safetyGuard.divideByInt(2)), BASE_Y.add(halfDepth).substract(safetyGuard.divideByInt(2)));
                pt4 = new Position(BASE_X.add(halfDepth).add(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getDepth()).substract(safetyGuard.divideByInt(2)));
                pt5 = new Position(BASE_X.add(chalet.getLongueur()), BASE_Y);
                pt6 = new Position(BASE_X.add(chalet.getLongueur()), BASE_Y.add(halfDepth).substract(safetyGuard.divideByInt(2)));
                pt7 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth).substract(safetyGuard.divideByInt(2)), BASE_Y.add(halfDepth).substract(safetyGuard.divideByInt(2)));
                pt8 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth).substract(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getDepth()).substract(safetyGuard.divideByInt(2)));
                break;

            case AVANT:
                pt1 = new Position(BASE_X, BASE_Y.add(chalet.getLargeur()));
                pt2 = new Position(BASE_X, BASE_Y.add(chalet.getLargeur()).substract(halfDepth).add(safetyGuard.divideByInt(2)));
                pt3 = new Position(BASE_X.add(halfDepth).add(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getLargeur()).substract(halfDepth).add(safetyGuard.divideByInt(2)));
                pt4 = new Position(BASE_X.add(halfDepth).add(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getLargeur()).substract(chalet.getDepth()).add(safetyGuard.divideByInt(2)));
                pt5 = new Position(BASE_X.add(chalet.getLongueur()), BASE_Y.add(chalet.getLargeur()));
                pt6 = new Position(BASE_X.add(chalet.getLongueur()), BASE_Y.add(chalet.getLargeur()).substract(halfDepth).add(safetyGuard.divideByInt(2)));
                pt7 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth).substract(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getLargeur()).substract(halfDepth).add(safetyGuard.divideByInt(2)));
                pt8 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth).substract(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getLargeur()).substract(chalet.getDepth()).add(safetyGuard.divideByInt(2)));
                break;

            case GAUCHE:
                pt1 = new Position(BASE_X, BASE_Y.add(halfDepth).add(safetyGuard.divideByInt(2)));
                pt2 = new Position(BASE_X.add(halfDepth).substract(safetyGuard.divideByInt(2)), BASE_Y.add(halfDepth).add(safetyGuard.divideByInt(2)));
                pt3 = new Position(BASE_X.add(halfDepth).substract(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getDepth()).add(safetyGuard.divideByInt(2)));
                pt4 = new Position(BASE_X.add(chalet.getDepth()), BASE_Y.add(chalet.getDepth()).add(safetyGuard.divideByInt(2)));
                pt5 = new Position(BASE_X, BASE_Y.add(chalet.getLargeur()).substract(halfDepth).substract(safetyGuard.divideByInt(2)));
                pt6 = new Position(BASE_X.add(halfDepth).substract(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getLargeur()).substract(halfDepth).substract(safetyGuard.divideByInt(2)));
                pt7 = new Position(BASE_X.add(halfDepth).substract(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getLargeur()).substract(chalet.getDepth()).substract(safetyGuard.divideByInt(2)));
                pt8 = new Position(BASE_X.add(chalet.getDepth()), BASE_Y.add(chalet.getLargeur()).substract(chalet.getDepth()).substract(safetyGuard.divideByInt(2)));
                break;

            case DROITE:
                pt1 = new Position(BASE_X.add(chalet.getLongueur()), BASE_Y.add(halfDepth).add(safetyGuard.divideByInt(2)));
                pt2 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth).add(safetyGuard.divideByInt(2)), BASE_Y.add(halfDepth).add(safetyGuard.divideByInt(2)));
                pt3 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth).add(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getDepth()).add(safetyGuard.divideByInt(2)));
                pt4 = new Position(BASE_X.add(chalet.getLongueur()).substract(chalet.getDepth()), BASE_Y.add(chalet.getDepth()).add(safetyGuard.divideByInt(2)));
                pt5 = new Position(BASE_X.add(chalet.getLongueur()), BASE_Y.add(chalet.getLargeur()).substract(halfDepth).substract(safetyGuard.divideByInt(2)));
                pt6 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth).add(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getLargeur()).substract(halfDepth).substract(safetyGuard.divideByInt(2)));
                pt7 = new Position(BASE_X.add(chalet.getLongueur()).substract(halfDepth).add(safetyGuard.divideByInt(2)), BASE_Y.add(chalet.getLargeur()).substract(chalet.getDepth()).substract(safetyGuard.divideByInt(2)));
                pt8 = new Position(BASE_X.add(chalet.getLongueur()).substract(chalet.getDepth()), BASE_Y.add(chalet.getLargeur()).substract(chalet.getDepth()).substract(safetyGuard.divideByInt(2)));
                break;
        }
        topView.add(pt1);
        topView.add(pt5);
        topView.add(pt6);
        topView.add(pt7);
        topView.add(pt8);
        topView.add(pt4);
        topView.add(pt3);
        topView.add(pt2);
        view.add(topView);
        viewPoints.put("top", view);
    }

    public ValeurImperiale getWidth() {
        return this.width;
    }
    public ValeurImperiale getDepth() {
        return this.depth;
    }
    public ValeurImperiale getHeight() {
        return this.height;
    }
    public List<Accessory> getAccessories() {
        return this.accessoryList;
    }
    public Map<String, ArrayList<ArrayList<Position>>> getViewPoints() {return this.viewPoints;}
    public ValeurImperiale getMinimumAccessoryDistance() {return minimumAccessoryDistance;}
    public void setMinimumAccessoryDistance(ValeurImperiale newMinimumAccessoryDistance) {
        if(newMinimumAccessoryDistance.compareTo(DEFAULT_MINIMUM_DISTANCE) >= 0) {
            this.minimumAccessoryDistance = newMinimumAccessoryDistance;
        } else {
            System.out.println("La distance minimale entre les accessoires et avec les murs doit etre au minimum de 3 pouces");
        }
    }
    public void addAccessory(Accessory accessory) {
        if (!doesNewPositionInterfere(accessory, accessory.getPosition()) && doesAccessoryFit(accessory)) {
            accessory.isValid = true;
            accessoryList.add(accessory);
            accessory.setWall(this);
        }
    }

    public void changeAccessorySize(Accessory accessory, ValeurImperiale newWidth, ValeurImperiale newHeight){
        if(!doesNewPositionInterfere(accessory, newWidth, newHeight) && doesAccessoryFit(accessory, newWidth, newHeight)){
            accessory.setDimensions(newWidth, newHeight);
        }
    }

    public void moveAccessory(Accessory accessory, Position newPosition) {
        if (!doesNewPositionInterfere(accessory, newPosition) && doesAccessoryFit(accessory, newPosition)) {
            accessory.move(newPosition);
        }
    }

    public void removeAccessory(Accessory accessory) {
        if(accessoryList.contains(accessory)){
            accessoryList.remove(accessory);
            accessory.setWall(null);
        }
    }

    public Accessory findAccessory(Position position) {

        for (Accessory accessory : accessoryList) {
            if (accessory.selected(position)) {
                return accessory;
            }
        }
        return null;
    }
    

    public boolean isPositionWithinWall(Position position) {
        ArrayList<ArrayList<Position>> frontView = viewPoints.get("front");
        for (ArrayList<Position> viewPointsList : frontView) {
            // Assuming the viewPointsList contains points in the order: topLeft, topRight, bottomRight, bottomLeft
            Position topLeft = viewPointsList.get(0);
            Position bottomRight = viewPointsList.get(2);

            ValeurImperiale minX = topLeft.getX();
            ValeurImperiale maxX = bottomRight.getX();
            ValeurImperiale minY = topLeft.getY();
            ValeurImperiale maxY = bottomRight.getY();

            boolean isWithinXBounds = position.getX().compareTo(minX) >= 0 && position.getX().compareTo(maxX) <= 0;
            boolean isWithinYBounds = position.getY().compareTo(minY) >= 0 && position.getY().compareTo(maxY) <= 0;

            if (isWithinXBounds && isWithinYBounds) {
                return true;
            }
        }
        return false;
    }


    /**
     * This is a method that checks if an accessory interferes with another accessory already placed on the wall
     * in another position than the one it is currently in.
     *
     * @param movedAccessory The accessory we want to MOVE.
     * @param newPosition The new position where we want to move the accessory.
     * @return A boolean representing whether the accessory interferes with another accessory or not, returns true if it does.
     */
    public boolean doesNewPositionInterfere(Accessory movedAccessory, Position newPosition){
        for (Accessory existingAccessory : accessoryList) {
            if (existingAccessory.getPositionList().equals(movedAccessory.getPositionList())) {
                continue;
            }

            Rectangle2D existingRectangle = createRectangle(existingAccessory);
            Rectangle2D newRectangle = createRectangle(movedAccessory, newPosition);

            if (existingRectangle.intersects(newRectangle)) {
                System.out.println("On ne peut pas empiler les accessoires");
                return true;
            }
        }
        return false;
    }

    /**
     * This is a method that checks if an accessory interferes with another accessory already placed on the wall
     * in another position than the one it is currently in.
     *
     * @param resizedAccessory The accessory we want to MOVE.
     * @param newWidth The new newWidth of the accessory.
     * @param newHeight The new newHeight of the accessory.
     * @return A boolean representing whether the accessory interferes with another accessory or not, returns true if it does.
     */
    public boolean doesNewPositionInterfere(Accessory resizedAccessory, ValeurImperiale newWidth, ValeurImperiale newHeight){
        for (Accessory existingAccessory : accessoryList) {
            if (existingAccessory.getPositionList().equals(resizedAccessory.getPositionList())) {
                continue;
            }

            Rectangle2D existingRectangle = createRectangle(existingAccessory);
            Rectangle2D newRectangle = createRectangle(resizedAccessory, newWidth, newHeight);

            if (existingRectangle.intersects(newRectangle)) {
                System.out.println("On ne peut pas empiler les accessoires");
                return true;
            }
        }
        return false;
    }

    /**
     * This is a method that checks if an accessory fits inside the walls.
     *
     * @param accessory The accessory we want to PLACE.
     * @return A boolean representing whether the accessory fits inside the walls or not, returns true if it does.
     */
    private boolean doesAccessoryFit(Accessory accessory) {
        ValeurImperiale minX;
        ValeurImperiale minY;
        ValeurImperiale maxX;
        ValeurImperiale maxY;
        if (shed.direction.equals(ToitDirection.FRONT_TO_BACK) || shed.direction.equals(ToitDirection.BACK_TO_FRONT)){
            if (this.facade == Facade.DROITE || this.facade == Facade.GAUCHE) {
                minX = this.viewPoints.get("front").get(1).get(0).getX().add(minimumAccessoryDistance); // le premier point 2e rect
                minY = this.viewPoints.get("front").get(1).get(0).getY().add(minimumAccessoryDistance); // idem ^
                maxX = this.viewPoints.get("front").get(1).get(1).getX().substract(minimumAccessoryDistance); // le deuxieme point du 2e rect
                maxY = this.viewPoints.get("front").get(1).get(3).getY().substract(minimumAccessoryDistance); // le quatrieme point du 2e rect
                if (accessory instanceof Door){
                    maxY = this.viewPoints.get("front").get(1).get(3).getY();
                }

            } else {
                minX = this.viewPoints.get("front").get(0).get(0).getX().add(this.depth).add(minimumAccessoryDistance); // le premier point seul rect
                minY = this.viewPoints.get("front").get(0).get(0).getY().add(minimumAccessoryDistance); // le premier point seul rect
                maxX = this.viewPoints.get("front").get(0).get(1).getX().substract(this.depth).substract(minimumAccessoryDistance); // le deuxieme point seul rect
                maxY = this.viewPoints.get("front").get(0).get(3).getY().substract(minimumAccessoryDistance); // le quatrieme point seul rect
                if (accessory instanceof Door){
                    maxY = this.viewPoints.get("front").get(0).get(3).getY();
                }

            }

            int comparisonResultMinX = accessory.position.getX().compareTo(minX);
            int comparisonResultMaxX = (accessory.position.getX().add(accessory.width)).compareTo(maxX);
            int comparisonResultMinY = accessory.position.getY().compareTo(minY);
            int comparisonResultMaxY = (accessory.position.getY().add(accessory.height)).compareTo(maxY);

            if(comparisonResultMinX >= 0 && comparisonResultMaxX <= 0 && comparisonResultMinY >= 0 && comparisonResultMaxY <= 0) {
                return true;
            }
            else {
                System.out.println("L'accessoire est trop gros ou en dehors du mur");
                return false;
            }
        } else {
            if (this.facade == Facade.AVANT || this.facade == Facade.ARRIERE) {
                minX = this.viewPoints.get("front").get(1).get(0).getX().add(minimumAccessoryDistance); // le premier point 2e rect
                minY = this.viewPoints.get("front").get(1).get(0).getY().add(minimumAccessoryDistance); // idem ^
                maxX = this.viewPoints.get("front").get(1).get(1).getX().substract(minimumAccessoryDistance); // le deuxieme point du 2e rect
                maxY = this.viewPoints.get("front").get(1).get(3).getY().substract(minimumAccessoryDistance); // le quatrieme point du 2e rect
                if (accessory instanceof Door){
                    maxY = this.viewPoints.get("front").get(1).get(3).getY();
                }

            } else {
                minX = this.viewPoints.get("front").get(0).get(0).getX().add(this.depth).add(minimumAccessoryDistance); // le premier point seul rect
                minY = this.viewPoints.get("front").get(0).get(0).getY().add(minimumAccessoryDistance); // le premier point seul rect
                maxX = this.viewPoints.get("front").get(0).get(1).getX().substract(this.depth).substract(minimumAccessoryDistance); // le deuxieme point seul rect
                maxY = this.viewPoints.get("front").get(0).get(3).getY().substract(minimumAccessoryDistance); // le quatrieme point seul rect
                if (accessory instanceof Door){
                    maxY = this.viewPoints.get("front").get(0).get(3).getY();
                }

            }

            int comparisonResultMinX = accessory.position.getX().compareTo(minX);
            int comparisonResultMaxX = (accessory.position.getX().add(accessory.width)).compareTo(maxX);
            int comparisonResultMinY = accessory.position.getY().compareTo(minY);
            int comparisonResultMaxY = (accessory.position.getY().add(accessory.height)).compareTo(maxY);

            if(comparisonResultMinX >= 0 && comparisonResultMaxX <= 0 && comparisonResultMinY >= 0 && comparisonResultMaxY <= 0) {
                return true;
            }
            else {
                System.out.println("L'accessoire est trop gros ou en dehors du mur");
                return false;
            }
        }
    }

    /**
     * This is a method that checks if an accessory fits inside the wall given a new size.
     *
     * @param accessory The accessory we want to RESIZE.
     * @param newWidth The new newWidth of the accessory.
     * @param newHeight The new newHeight of the accessory.
     * @return A boolean representing whether the accessory fits inside the wall or not, returns true if it does.
     */
    public boolean doesAccessoryFit(Accessory accessory, ValeurImperiale newWidth, ValeurImperiale newHeight) {
        ValeurImperiale minX;
        ValeurImperiale minY;
        ValeurImperiale maxX;
        ValeurImperiale maxY;
        int comparisonResultMinX;
        int comparisonResultMaxX;
        int comparisonResultMinY;
        int comparisonResultMaxY;
        if (shed.direction.equals(ToitDirection.FRONT_TO_BACK) || shed.direction.equals(ToitDirection.BACK_TO_FRONT)){
            if (this.facade == Facade.DROITE || this.facade == Facade.GAUCHE) {
                minX = this.viewPoints.get("front").get(1).get(0).getX().add(minimumAccessoryDistance); // le premier point 2e rect
                minY = this.viewPoints.get("front").get(1).get(0).getY().add(minimumAccessoryDistance); // idem ^
                maxX = this.viewPoints.get("front").get(1).get(1).getX().substract(minimumAccessoryDistance); // le deuxieme point du 2e rect
                maxY = this.viewPoints.get("front").get(1).get(3).getY().substract(minimumAccessoryDistance); // le quatrieme point du 2e rect
                if(accessory instanceof Door) {
                    maxY = this.viewPoints.get("front").get(1).get(3).getY();
                }
            } else {
                minX = this.viewPoints.get("front").get(0).get(0).getX().add(this.depth).add(minimumAccessoryDistance); // le premier point seul rect
                minY = this.viewPoints.get("front").get(0).get(0).getY().add(minimumAccessoryDistance); // le premier point seul rect
                maxX = this.viewPoints.get("front").get(0).get(1).getX().substract(this.depth).substract(minimumAccessoryDistance); // le deuxieme point seul rect
                maxY = this.viewPoints.get("front").get(0).get(3).getY().substract(minimumAccessoryDistance); // le quatrieme point seul rect
                if(accessory instanceof Door) {
                    maxY = this.viewPoints.get("front").get(0).get(3).getY();
                }
            }

            if (accessory instanceof Door) {
                comparisonResultMinX = accessory.position.getX().compareTo(minX);
                comparisonResultMaxX = (accessory.position.getX().add(newWidth)).compareTo(maxX);
                comparisonResultMinY = (accessory.position.getY().add(accessory.getHeight()).substract(newHeight)).compareTo(minY);
                comparisonResultMaxY = minY.add(newHeight).compareTo(maxY);
            } else {
                comparisonResultMinX = accessory.position.getX().compareTo(minX);
                comparisonResultMaxX = (accessory.position.getX().add(newWidth)).compareTo(maxX);
                comparisonResultMinY = accessory.position.getY().compareTo(minY);
                comparisonResultMaxY = (accessory.position.getY().add(newHeight)).compareTo(maxY);
            }

            if(comparisonResultMinX >= 0 && comparisonResultMaxX <= 0 && comparisonResultMinY >= 0 && comparisonResultMaxY <= 0) {
                return true;
            }
            else {
                System.out.println("L'accessoire est trop gros ou en dehors du mur");
                return false;
            }
        } else {
            if (this.facade == Facade.AVANT || this.facade == Facade.ARRIERE) {
                minX = this.viewPoints.get("front").get(1).get(0).getX().add(minimumAccessoryDistance); // le premier point 2e rect
                minY = this.viewPoints.get("front").get(1).get(0).getY().add(minimumAccessoryDistance); // idem ^
                maxX = this.viewPoints.get("front").get(1).get(1).getX().substract(minimumAccessoryDistance); // le deuxieme point du 2e rect
                maxY = this.viewPoints.get("front").get(1).get(3).getY().substract(minimumAccessoryDistance); // le quatrieme point du 2e rect
                if (accessory instanceof Door){
                    maxY = this.viewPoints.get("front").get(1).get(3).getY();
                }


            } else {
                minX = this.viewPoints.get("front").get(0).get(0).getX().add(this.depth).add(minimumAccessoryDistance); // le premier point seul rect
                minY = this.viewPoints.get("front").get(0).get(0).getY().add(minimumAccessoryDistance); // le premier point seul rect
                maxX = this.viewPoints.get("front").get(0).get(1).getX().substract(this.depth).substract(minimumAccessoryDistance); // le deuxieme point seul rect
                maxY = this.viewPoints.get("front").get(0).get(3).getY().substract(minimumAccessoryDistance); // le quatrieme point seul rect
                if (accessory instanceof Door){
                    maxY = this.viewPoints.get("front").get(0).get(3).getY();
                }
            }

            if (accessory instanceof Door) {
                comparisonResultMinX = accessory.position.getX().compareTo(minX);
                comparisonResultMaxX = (accessory.position.getX().add(newWidth)).compareTo(maxX);
                comparisonResultMinY = (accessory.position.getY().add(accessory.getHeight()).substract(newHeight)).compareTo(minY);
                comparisonResultMaxY = minY.add(newHeight).compareTo(maxY);
            } else {
                comparisonResultMinX = accessory.position.getX().compareTo(minX);
                comparisonResultMaxX = (accessory.position.getX().add(newWidth)).compareTo(maxX);
                comparisonResultMinY = accessory.position.getY().compareTo(minY);
                comparisonResultMaxY = (accessory.position.getY().add(newHeight)).compareTo(maxY);
            }

            if(comparisonResultMinX >= 0 && comparisonResultMaxX <= 0 && comparisonResultMinY >= 0 && comparisonResultMaxY <= 0) {
                return true;
            }
            else {
                System.out.println("L'accessoire est trop gros ou en dehors du mur");
                return false;
            }
        }
    }

    /**
     * This is a method that checks if an accessory fits inside the walls in another position
     * than the one it is currently in.
     *
     * @param accessory The accessory we want to MOVE.
     * @param newPosition The new position where we want to move the accessory.
     * @return A boolean representing whether the accessory fits inside the walls or not, returns true if it does.
     */
    private boolean doesAccessoryFit(Accessory accessory, Position newPosition) {
        ValeurImperiale minX;
        ValeurImperiale minY;
        ValeurImperiale maxX;
        ValeurImperiale maxY;
        if (shed.direction.equals(ToitDirection.FRONT_TO_BACK) || shed.direction.equals(ToitDirection.BACK_TO_FRONT)){
            if (this.facade == Facade.DROITE || this.facade == Facade.GAUCHE) {
                minX = this.viewPoints.get("front").get(1).get(0).getX().add(minimumAccessoryDistance); // le premier point 2e rect
                minY = this.viewPoints.get("front").get(1).get(0).getY().add(minimumAccessoryDistance); // idem ^
                maxX = this.viewPoints.get("front").get(1).get(1).getX().substract(minimumAccessoryDistance); // le deuxieme point du 2e rect
                maxY = this.viewPoints.get("front").get(1).get(3).getY().substract(minimumAccessoryDistance); // le quatrieme point du 2e rect
                if (accessory instanceof Door) {
                    maxY = this.viewPoints.get("front").get(1).get(3).getY();
                }
            } else {
                minX = this.viewPoints.get("front").get(0).get(0).getX().add(this.depth).add(minimumAccessoryDistance); // le premier point seul rect
                minY = this.viewPoints.get("front").get(0).get(0).getY().add(minimumAccessoryDistance); // le premier point seul rect
                maxX = this.viewPoints.get("front").get(0).get(1).getX().substract(this.depth).substract(minimumAccessoryDistance); // le deuxieme point seul rect
                maxY = this.viewPoints.get("front").get(0).get(3).getY().substract(minimumAccessoryDistance); // le quatrieme point seul rect
                if (accessory instanceof Door) {
                    maxY = this.viewPoints.get("front").get(0).get(3).getY();
                }
            }

            int comparisonResultMinX = newPosition.getX().compareTo(minX);
            int comparisonResultMaxX = (newPosition.getX().add(accessory.width)).compareTo(maxX);
            int comparisonResultMinY = newPosition.getY().compareTo(minY);
            int comparisonResultMaxY = (newPosition.getY().add(accessory.height)).compareTo(maxY);

            if(comparisonResultMinX >= 0 && comparisonResultMaxX <= 0 && comparisonResultMinY >= 0 && comparisonResultMaxY <= 0) {
                return true;
            }
            else {
                System.out.println("L'accessoire est trop gros ou en dehors du mur");
                return false;
            }
        } else{
            if (this.facade == Facade.AVANT || this.facade == Facade.ARRIERE) {
                minX = this.viewPoints.get("front").get(1).get(0).getX().add(minimumAccessoryDistance); // le premier point 2e rect
                minY = this.viewPoints.get("front").get(1).get(0).getY().add(minimumAccessoryDistance); // idem ^
                maxX = this.viewPoints.get("front").get(1).get(1).getX().substract(minimumAccessoryDistance); // le deuxieme point du 2e rect
                maxY = this.viewPoints.get("front").get(1).get(3).getY().substract(minimumAccessoryDistance); // le quatrieme point du 2e rect
                if (accessory instanceof Door) {
                    maxY = this.viewPoints.get("front").get(1).get(3).getY();
                }
            } else {
                minX = this.viewPoints.get("front").get(0).get(0).getX().add(this.depth).add(minimumAccessoryDistance); // le premier point seul rect
                minY = this.viewPoints.get("front").get(0).get(0).getY().add(minimumAccessoryDistance); // le premier point seul rect
                maxX = this.viewPoints.get("front").get(0).get(1).getX().substract(this.depth).substract(minimumAccessoryDistance); // le deuxieme point seul rect
                maxY = this.viewPoints.get("front").get(0).get(3).getY().substract(minimumAccessoryDistance); // le quatrieme point seul rect
                if (accessory instanceof Door) {
                    maxY = this.viewPoints.get("front").get(0).get(3).getY();
                }
            }

            int comparisonResultMinX = newPosition.getX().compareTo(minX);
            int comparisonResultMaxX = (newPosition.getX().add(accessory.width)).compareTo(maxX);
            int comparisonResultMinY = newPosition.getY().compareTo(minY);
            int comparisonResultMaxY = (newPosition.getY().add(accessory.height)).compareTo(maxY);

            if(comparisonResultMinX >= 0 && comparisonResultMaxX <= 0 && comparisonResultMinY >= 0 && comparisonResultMaxY <= 0) {
                return true;
            }
            else {
                System.out.println("L'accessoire est trop gros ou en dehors du mur");
                return false;
            }
        }
    }

    /**
     * This is a method that creates a rectangle based on an accessory
     *
     * @param accessory The accessory we want to translate to a rectangle.
     * @return The Rectangle2D that represents the accessory.
     */
    private Rectangle2D createRectangle(Accessory accessory){
        double X = accessory.getPosition().getX().convertToDouble();
        double Y = accessory.getPosition().getY().convertToDouble();
        double width = accessory.getWidth().convertToDouble();
        double height = accessory.getHeight().convertToDouble();

        return new Rectangle2D.Double(X, Y, width, height);
    }

    /**
     * This is a method that creates a rectangle based on an accessory and it's new position
     *
     * @param accessory The accessory we want to translate to a rectangle.
     * @param position The new position of the accessory
     * @return The Rectangle2D that represents the accessory at the given position.
     */
    private Rectangle2D createRectangle(Accessory accessory, Position position) {
        double X = position.getX().substract(minimumAccessoryDistance).convertToDouble();
        double Y = position.getY().substract(minimumAccessoryDistance).convertToDouble();
        double width = accessory.getWidth().add(minimumAccessoryDistance).add(minimumAccessoryDistance).convertToDouble();
        double height = accessory.getHeight().add(minimumAccessoryDistance).add(minimumAccessoryDistance).convertToDouble();

        return new Rectangle2D.Double(X, Y, width, height);
    }

    /**
     * This is a method that creates a rectangle based on an accessory with a new size
     *
     * @param accessory The accessory we want to translate to a rectangle.
     * @param newWidth The new width of the accessory
     * @param newHeight The new height of the accessory
     * @return The Rectangle2D that represents the accessory at the new size.
     */
    private Rectangle2D createRectangle(Accessory accessory, ValeurImperiale newWidth, ValeurImperiale newHeight){
        double X;
        double Y;
        double width;
        double height;
        if (accessory instanceof Door) {
            X = accessory.getPosition().getX().substract(minimumAccessoryDistance).convertToDouble();
            Y = this.height.substract(newHeight).convertToDouble();
            width = newWidth.add(minimumAccessoryDistance).add(minimumAccessoryDistance).convertToDouble();
            height = newHeight.add(minimumAccessoryDistance).add(minimumAccessoryDistance).convertToDouble();
        } else {
            X = accessory.getPosition().getX().substract(minimumAccessoryDistance).convertToDouble();
            Y = accessory.getPosition().getY().substract(minimumAccessoryDistance).convertToDouble();
            width = newWidth.add(minimumAccessoryDistance).add(minimumAccessoryDistance).convertToDouble();
            height = newHeight.add(minimumAccessoryDistance).add(minimumAccessoryDistance).convertToDouble();
        }
        return new Rectangle2D.Double(X, Y, width, height);
    }


    public Facade getFacade(){return this.facade;}
}
