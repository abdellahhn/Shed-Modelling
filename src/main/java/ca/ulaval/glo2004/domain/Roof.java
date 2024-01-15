/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author abdellahhanane
 */
public class Roof implements Serializable{
    private ToitDirection directionToit;
    private ValeurImperiale width;
    private double angle;
    private  ValeurImperiale depth;
    public Facade facade;
    private Map<String, ArrayList<ArrayList<Position>>> viewPoints = new HashMap<>();
    private static ValeurImperiale BASE_X = new ValeurImperiale(0);
    private static ValeurImperiale BASE_Y = new ValeurImperiale(0);

    public Roof(Shed chalet, Facade facade, ValeurImperiale saftG, double angle, ToitDirection directionToit){
        this.directionToit = directionToit;
        this.facade = facade;
        this.depth = chalet.getDepth();
        ArrayList<ArrayList<Position>> view = new ArrayList<>();

        switch (directionToit){
            case BACK_TO_FRONT:
                if (facade == Facade.DROITE){
                    this.width = chalet.getLargeur();
                    createCompRoof_Pignon(view, angle, saftG, directionToit);
                    createCompRoof_RallongeSide(view, saftG, angle, directionToit);
                    createCompRoof_ToitSide(view, saftG, angle, directionToit);

                } else if (facade == Facade.AVANT){
                    this.width = chalet.getLongueur();
                    createCompRoof_Rallonge(view, saftG, angle);

                } else if (facade == Facade.GAUCHE){
                    this.width = chalet.getLargeur();
                    createCompRoof_Pignon(view, angle, saftG, directionToit);
                    createCompRoof_RallongeSide(view, saftG, angle, directionToit);
                    createCompRoof_ToitSide(view, saftG, angle, directionToit);

                } else if (facade == Facade.ARRIERE){
                    this.width = chalet.getLongueur();
                    createCompRoof_Toit(view, saftG, angle);
                }
                break;

            case FRONT_TO_BACK:
                if (facade == Facade.DROITE){
                    this.width = chalet.getLargeur();
                    createCompRoof_Pignon(view, angle, saftG, directionToit);
                    createCompRoof_RallongeSide(view, saftG, angle, directionToit);
                    createCompRoof_ToitSide(view, saftG, angle, directionToit);

                } else if (facade == Facade.ARRIERE){
                    this.width = chalet.getLongueur();
                    createCompRoof_Rallonge(view, saftG, angle);

                } else if (facade == Facade.GAUCHE){
                    this.width = chalet.getLargeur();
                    createCompRoof_Pignon(view, angle, saftG, directionToit);
                    createCompRoof_RallongeSide(view, saftG, angle, directionToit);
                    createCompRoof_ToitSide(view, saftG, angle, directionToit);

                } else if (facade == Facade.AVANT){
                    this.width = chalet.getLongueur();
                    createCompRoof_Toit(view, saftG, angle);
                }
                break;

            case LEFT_TO_RIGHT:
                if (facade == Facade.AVANT){
                    this.width = chalet.getLongueur();
                    createCompRoof_Pignon(view, angle, saftG, directionToit);
                    createCompRoof_RallongeSide(view, saftG, angle, directionToit);
                    createCompRoof_ToitSide(view, saftG, angle, directionToit);

                } else if (facade == Facade.DROITE){
                    this.width = chalet.getLargeur();
                    createCompRoof_Toit(view, saftG, angle);

                } else if (facade == Facade.ARRIERE){
                    this.width = chalet.getLongueur();
                    createCompRoof_Pignon(view, angle, saftG, directionToit);
                    createCompRoof_RallongeSide(view, saftG, angle, directionToit);
                    createCompRoof_ToitSide(view, saftG, angle, directionToit);

                } else if (facade == Facade.GAUCHE){
                    this.width = chalet.getLargeur();
                    createCompRoof_Rallonge(view, saftG, angle);
                }
                break;

            case RIGHT_TO_LEFT:
                if (facade == Facade.AVANT){
                    this.width = chalet.getLongueur();
                    createCompRoof_Pignon(view, angle, saftG, directionToit);
                    createCompRoof_RallongeSide(view, saftG, angle, directionToit);
                    createCompRoof_ToitSide(view, saftG, angle, directionToit);

                } else if (facade == Facade.GAUCHE){
                    this.width = chalet.getLargeur();
                    createCompRoof_Toit(view, saftG, angle);

                } else if (facade == Facade.ARRIERE){
                    this.width = chalet.getLongueur();
                    createCompRoof_Pignon(view, angle, saftG, directionToit);
                    createCompRoof_RallongeSide(view, saftG, angle, directionToit);
                    createCompRoof_ToitSide(view, saftG, angle, directionToit);

                } else if (facade == Facade.DROITE){
                    this.width = chalet.getLargeur();
                    createCompRoof_Rallonge(view, saftG, angle);
                }
                break;
        }
        viewPoints.put("roof", view);
    }

//    public Roof(Roof other) {
//        this.directionToit = other.directionToit;
//        this.width = new ValeurImperiale(other.width);
//        this.depth = new ValeurImperiale(other.depth);
//        this.facade = other.facade;
//
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

    public Roof(Roof other) {
        this.directionToit = other.directionToit;
        this.width = new ValeurImperiale(other.width);
        this.depth = new ValeurImperiale(other.depth);
        this.facade = other.facade;
        this.angle = other.angle;

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

    private void createCompRoof_Rallonge(ArrayList<ArrayList<Position>> view, ValeurImperiale saftG, double angle) {
        double angleRadians = Math.toRadians(angle);
        double baseWidth = width.convertToDouble();

        double rallongeHeight = Math.tan(angleRadians) * baseWidth;

        ValeurImperiale heightValeurImperiale = ValeurImperiale.convertDoubleToValeurImperiale(rallongeHeight);

        Position topLeft = new Position(BASE_X, BASE_Y.substract(heightValeurImperiale));
        Position topRight = new Position(BASE_X.add(width), BASE_Y.substract(heightValeurImperiale));
        Position botLeft = new Position(BASE_X, BASE_Y.substract(depth.divideByInt(2)));
        Position botRight = new Position(BASE_X.add(width), BASE_Y.substract(depth.divideByInt(2)));

        Position secRTopLeft = new Position(BASE_X, BASE_Y.substract(depth.divideByInt(2)));
        Position secRTopRight = new Position(BASE_X.add(width), BASE_Y.substract(depth.divideByInt(2)));
        Position secRBotLeft = new Position(BASE_X, BASE_Y);
        Position secRBotRight = new Position(BASE_X.add(width), BASE_Y);

        ArrayList<Position> rectangleO = new ArrayList<>();
        rectangleO.add(secRTopLeft);
        rectangleO.add(secRTopRight);
        rectangleO.add(secRBotRight);
        rectangleO.add(secRBotLeft);

        ArrayList<Position> rectangle = new ArrayList<>();
        rectangle.add(topLeft);
        rectangle.add(topRight);
        rectangle.add(botRight);
        rectangle.add(botLeft);

        view.add(rectangle);
        view.add(rectangleO);
    }

    private void createCompRoof_Pignon(ArrayList<ArrayList<Position>> view, double angle, ValeurImperiale saftG, ToitDirection direction) {
        Position peak = calculatePignonAngle(angle, saftG, direction);
        Position botLeft = new Position(BASE_X.add((depth).divideByInt(2).
                add(saftG.divideByInt(2))),
                BASE_Y);

        Position botRight = new Position((BASE_X.add((depth).
                divideByInt(2)).add(width).substract(saftG.divideByInt(2))).substract(depth),
                BASE_Y);

        ArrayList<Position> pignon = new ArrayList<>();
        pignon.add(peak);
        pignon.add(botLeft);
        pignon.add(botRight);
        view.add(pignon);
    }

    private void createCompRoof_RallongeSide(ArrayList<ArrayList<Position>> view, ValeurImperiale saftG, double angle, ToitDirection direction) {
        double angleRadians = Math.toRadians(angle);
        double baseWidth = width.convertToDouble();
        double baseDepth = (depth.divideByInt(2)).convertToDouble();
        double pignonHeight = Math.tan(angleRadians) * (baseWidth);
        double rallongeHeight = Math.tan(angleRadians) * (baseWidth+baseDepth);
        ValeurImperiale heightValeurImperiale = ValeurImperiale.convertDoubleToValeurImperiale(rallongeHeight);
        ValeurImperiale heightValeurImperialeP = ValeurImperiale.convertDoubleToValeurImperiale(pignonHeight);

        ArrayList<Position> rallonge = new ArrayList<>();
        Position topLeft, topRight, botLeft, botRight;

        switch (direction){
            case BACK_TO_FRONT:
                if (this.facade == Facade.DROITE) {
                    // For GAUCHE, la rallonge est a droite du pignon
                    topLeft = new Position((BASE_X.add(depth).divideByInt(2).add(width).add(saftG.divideByInt(2))).substract(depth)
                            , BASE_Y.substract(heightValeurImperialeP));
                    topRight = new Position(BASE_X.add(width), BASE_Y.substract(heightValeurImperiale));
                    botLeft = new Position((BASE_X.add(depth).divideByInt(2).add(width).add(saftG.divideByInt(2))).substract(depth)
                            , BASE_Y);
                    botRight = new Position(BASE_X.add(width), BASE_Y);
                } else {
                    // For DROITE, la rallonge est a gauche du pignon
                    topLeft = new Position(BASE_X, BASE_Y.substract(heightValeurImperiale));
                    topRight = new Position((BASE_X).add((this.depth).divideByInt(2).substract((saftG).divideByInt(2)))
                            , BASE_Y.substract(heightValeurImperialeP));
                    botRight = new Position((BASE_X).add((this.depth).divideByInt(2).substract((saftG).divideByInt(2)))
                            , BASE_Y);
                    botLeft = new Position(BASE_X, BASE_Y);
                }
                rallonge.add(topLeft);
                rallonge.add(topRight);
                rallonge.add(botRight);
                rallonge.add(botLeft);
                break;

            case FRONT_TO_BACK:
                if (this.facade == Facade.GAUCHE) {
                    // For DROITE, la rallonge est a gauche du pignon
                    topLeft = new Position((BASE_X.add(depth).divideByInt(2).add(width).add(saftG.divideByInt(2))).substract(depth)
                            , BASE_Y.substract(heightValeurImperialeP));
                    topRight = new Position(BASE_X.add(width), BASE_Y.substract(heightValeurImperiale));
                    botLeft = new Position((BASE_X.add(depth).divideByInt(2).add(width).add(saftG.divideByInt(2))).substract(depth)
                            , BASE_Y);
                    botRight = new Position(BASE_X.add(width), BASE_Y);
                } else {
                    // For GAUCHE, la rallonge est a droite du pignon
                    topLeft = new Position(BASE_X, BASE_Y.substract(heightValeurImperiale));
                    topRight = new Position((BASE_X).add((this.depth).divideByInt(2).substract((saftG).divideByInt(2)))
                            , BASE_Y.substract(heightValeurImperialeP));
                    botRight = new Position((BASE_X).add((this.depth).divideByInt(2).substract((saftG).divideByInt(2)))
                            , BASE_Y);
                    botLeft = new Position(BASE_X, BASE_Y);
                }
                rallonge.add(topLeft);
                rallonge.add(topRight);
                rallonge.add(botRight);
                rallonge.add(botLeft);
                break;

            case RIGHT_TO_LEFT:
                if (this.facade == Facade.ARRIERE) {
                    // For GAUCHE, la rallonge est a droite du pignon
                    topLeft = new Position((BASE_X.add(depth).divideByInt(2).add(width).add(saftG.divideByInt(2))).substract(depth)
                            , BASE_Y.substract(heightValeurImperialeP));
                    topRight = new Position(BASE_X.add(width), BASE_Y.substract(heightValeurImperiale));
                    botLeft = new Position((BASE_X.add(depth).divideByInt(2).add(width).add(saftG.divideByInt(2))).substract(depth)
                            , BASE_Y);
                    botRight = new Position(BASE_X.add(width), BASE_Y);
                } else {
                    // For DROITE, la rallonge est a gauche du pignon
                    topLeft = new Position(BASE_X, BASE_Y.substract(heightValeurImperiale));
                    topRight = new Position((BASE_X).add((this.depth).divideByInt(2).substract((saftG).divideByInt(2)))
                            , BASE_Y.substract(heightValeurImperialeP));
                    botRight = new Position((BASE_X).add((this.depth).divideByInt(2).substract((saftG).divideByInt(2)))
                            , BASE_Y);
                    botLeft = new Position(BASE_X, BASE_Y);
                }
                rallonge.add(topLeft);
                rallonge.add(topRight);
                rallonge.add(botRight);
                rallonge.add(botLeft);
                break;

            case LEFT_TO_RIGHT:
                if (this.facade == Facade.ARRIERE) {
                    // For DROITE, la rallonge est a gauche du pignon
                    topLeft = new Position(BASE_X, BASE_Y.substract(heightValeurImperiale));
                    topRight = new Position((BASE_X).add((this.depth).divideByInt(2).substract((saftG).divideByInt(2)))
                            , BASE_Y.substract(heightValeurImperialeP));
                    botRight = new Position((BASE_X).add((this.depth).divideByInt(2).substract((saftG).divideByInt(2)))
                            , BASE_Y);
                    botLeft = new Position(BASE_X, BASE_Y);
                } else {
                    // For GAUCHE, la rallonge est a droite du pignon
                    topLeft = new Position((BASE_X.add(depth).divideByInt(2).add(width).add(saftG.divideByInt(2))).substract(depth)
                            , BASE_Y.substract(heightValeurImperialeP));
                    topRight = new Position(BASE_X.add(width), BASE_Y.substract(heightValeurImperiale));
                    botLeft = new Position((BASE_X.add(depth).divideByInt(2).add(width).add(saftG.divideByInt(2))).substract(depth)
                            , BASE_Y);
                    botRight = new Position(BASE_X.add(width), BASE_Y);
                }
                rallonge.add(topLeft);
                rallonge.add(topRight);
                rallonge.add(botRight);
                rallonge.add(botLeft);
        }
        view.add(rallonge);
    }

    private void createCompRoof_Toit(ArrayList<ArrayList<Position>> view, ValeurImperiale saftG, double angle){
        double angleRadians = Math.toRadians(angle);
        double baseWidth = width.convertToDouble();

        double toitHeight = Math.tan(angleRadians) * baseWidth;

        ValeurImperiale heightValeurImperiale = ValeurImperiale.convertDoubleToValeurImperiale(toitHeight);

        Position topLeft = new Position(BASE_X, BASE_Y.substract(heightValeurImperiale));
        Position topRight = new Position(BASE_X.add(width), BASE_Y.substract(heightValeurImperiale));
        Position botLeft = new Position(BASE_X, BASE_Y);
        Position botRight = new Position(BASE_X.add(width), BASE_Y);

        ArrayList<Position> rectangle = new ArrayList<>();
        rectangle.add(topLeft);
        rectangle.add(topRight);
        rectangle.add(botRight);
        rectangle.add(botLeft);

        view.add(rectangle);
    }
    private void createCompRoof_ToitSide(ArrayList<ArrayList<Position>> view, ValeurImperiale saftG, double angle, ToitDirection directionToit) {
        double angleRadians = Math.toRadians(angle);
        double baseWidth = width.convertToDouble();
        double baseDepth = (depth.divideByInt(2)).convertToDouble();
        double pignonHeight = Math.tan(angleRadians) * (baseWidth);
        double rallongeHeight = Math.tan(angleRadians) * (baseWidth+baseDepth);
        ValeurImperiale heightValue = ValeurImperiale.convertDoubleToValeurImperiale(rallongeHeight);
        ValeurImperiale heightValeurImperialeP = ValeurImperiale.convertDoubleToValeurImperiale(pignonHeight);

        Position topLeft, topRight, botLeft, botRight, midRight;
        ArrayList<Position> extension = new ArrayList<>();

        switch (directionToit) {
            case BACK_TO_FRONT:
                if (this.facade == Facade.GAUCHE) {
                    topLeft = calculatePositionRight(depth, width, saftG, heightValue, true, true,false);
                    topRight = calculatePositionRight(depth, width, saftG, heightValue, true, false,false);
                    botLeft = calculatePositionRight(depth, width, saftG, heightValue, false, true,false);
                    botRight = calculatePositionRight(depth, width, saftG, heightValue, false, false,false);
                    midRight = calculatePositionRight(depth, width, saftG, heightValue, false, false, true);
                    extension.add(topLeft);
                    extension.add(topRight);
                    extension.add(midRight);
                    extension.add(botRight);
                    extension.add(botLeft);
                } else {
                    topLeft = calculatePositionLeft(depth, width, saftG, heightValue, true, true,false);
                    topRight = calculatePositionLeft(depth, width, saftG, heightValue, true, false,false);
                    botLeft = calculatePositionLeft(depth, width, saftG, heightValue, false, true,false);
                    botRight = calculatePositionLeft(depth, width, saftG, heightValue, false, false,false);
                    midRight = calculatePositionLeft(depth, width, saftG, heightValue, false, false, true);
                    extension.add(topLeft);
                    extension.add(topRight);
                    extension.add(botRight);
                    extension.add(botLeft);
                    extension.add(midRight);
                }
                break;
            case FRONT_TO_BACK:
                if (this.facade == Facade.DROITE) {
                    topLeft = calculatePositionRight(depth, width, saftG, heightValue, true, true,false);
                    topRight = calculatePositionRight(depth, width, saftG, heightValue, true, false,false);
                    botLeft = calculatePositionRight(depth, width, saftG, heightValue, false, true,false);
                    botRight = calculatePositionRight(depth, width, saftG, heightValue, false, false,false);
                    midRight = calculatePositionRight(depth, width, saftG, heightValue, false, false, true);
                    extension.add(topLeft);
                    extension.add(topRight);
                    extension.add(midRight);
                    extension.add(botRight);
                    extension.add(botLeft);
                } else {
                    topLeft = calculatePositionLeft(depth, width, saftG, heightValue, true, true,false);
                    topRight = calculatePositionLeft(depth, width, saftG, heightValue, true, false,false);
                    botLeft = calculatePositionLeft(depth, width, saftG, heightValue, false, true,false);
                    botRight = calculatePositionLeft(depth, width, saftG, heightValue, false, false,false);
                    midRight = calculatePositionLeft(depth, width, saftG, heightValue, false, false, true);
                    extension.add(topLeft);
                    extension.add(topRight);
                    extension.add(botRight);
                    extension.add(botLeft);
                    extension.add(midRight);
                }
                break;
            case RIGHT_TO_LEFT:
                if (this.facade == Facade.AVANT) {
                    topLeft = calculatePositionRight(depth, width, saftG, heightValue, true, true,false);
                    topRight = calculatePositionRight(depth, width, saftG, heightValue, true, false,false);
                    botLeft = calculatePositionRight(depth, width, saftG, heightValue, false, true,false);
                    botRight = calculatePositionRight(depth, width, saftG, heightValue, false, false,false);
                    midRight = calculatePositionRight(depth, width, saftG, heightValue, false, false, true);
                    extension.add(topLeft);
                    extension.add(topRight);
                    extension.add(midRight);
                    extension.add(botRight);
                    extension.add(botLeft);
                } else {
                    topLeft = calculatePositionLeft(depth, width, saftG, heightValue, true, true,false);
                    topRight = calculatePositionLeft(depth, width, saftG, heightValue, true, false,false);
                    botLeft = calculatePositionLeft(depth, width, saftG, heightValue, false, true,false);
                    botRight = calculatePositionLeft(depth, width, saftG, heightValue, false, false,false);
                    midRight = calculatePositionLeft(depth, width, saftG, heightValue, false, false, true);
                    extension.add(topLeft);
                    extension.add(topRight);
                    extension.add(botRight);
                    extension.add(botLeft);
                    extension.add(midRight);
                }
                break;
            case LEFT_TO_RIGHT:
                if (this.facade == Facade.ARRIERE) {
                    topLeft = calculatePositionRight(depth, width, saftG, heightValue, true, true, false);
                    topRight = calculatePositionRight(depth, width, saftG, heightValue, true, false, false);
                    botLeft = calculatePositionRight(depth, width, saftG, heightValue, false, true, false);
                    botRight = calculatePositionRight(depth, width, saftG, heightValue, false, false, false);
                    midRight = calculatePositionRight(depth, width, saftG, heightValue, false, false, true);
                    extension.add(topLeft);
                    extension.add(topRight);
                    extension.add(midRight);
                    extension.add(botRight);
                    extension.add(botLeft);
                } else {
                    topLeft = calculatePositionLeft(depth, width, saftG, heightValue, true, true, false);
                    topRight = calculatePositionLeft(depth, width, saftG, heightValue, true, false, false);
                    botLeft = calculatePositionLeft(depth, width, saftG, heightValue, false, true, false);
                    botRight = calculatePositionLeft(depth, width, saftG, heightValue, false, false, false);
                    midRight = calculatePositionLeft(depth, width, saftG, heightValue, false, false, true);
                    extension.add(topLeft);
                    extension.add(topRight);
                    extension.add(botRight);
                    extension.add(botLeft);
                    extension.add(midRight);
                }
                break;
        }
        view.add(extension);
    }


    private Position calculatePositionLeft(ValeurImperiale depth, ValeurImperiale width, ValeurImperiale saftG, ValeurImperiale heightValue, boolean isTop, boolean isLeft, boolean isMid) {
        ValeurImperiale x, y;
        if (isTop) {
            x = (BASE_X.add(width));
            y = BASE_Y.substract(heightValue).substract(saftG.divideByInt(2));

            if (isLeft) {
                y = (BASE_Y.substract(heightValue).substract(depth.divideByInt(2))).substract(saftG.divideByInt(2));
            }
        } else if (isMid){
            x = BASE_X;
            y = BASE_Y.substract(depth.divideByInt(2));
        } else {
            x = isLeft ? BASE_X : BASE_X.add(depth.divideByInt(2).substract(saftG.divideByInt(2)));
            y = BASE_Y;
        }
        return new Position(x, y);
    }

    private Position calculatePositionRight(ValeurImperiale depth, ValeurImperiale width, ValeurImperiale saftG, ValeurImperiale heightValue, boolean isTop, boolean isLeft, boolean isMid) {
        ValeurImperiale x, y;
        if (isTop) {
            x = (BASE_X);
            y = BASE_Y.substract(heightValue).substract(saftG.divideByInt(2));

            if (!isLeft) {
                y = (BASE_Y.substract(heightValue).substract(depth.divideByInt(2))).substract(saftG.divideByInt(2));
            }
        }
        else if (isMid){
            x = BASE_X.add(width);
            y = BASE_Y.substract(depth.divideByInt(2));
        }else {
            x = isLeft ? BASE_X.add(depth).divideByInt(2).add(width).add(saftG.divideByInt(2)).substract(depth) : BASE_X.add(width);
            y = BASE_Y;
        }
        return new Position(x, y);
    }


    public Map<String, ArrayList<ArrayList<Position>>> getViewPoints() {return this.viewPoints;}

    private Position calculatePignonAngle(double angle, ValeurImperiale saftG, ToitDirection direction) {
        double angleRadians = Math.toRadians(angle);

        double baseWidth = width.convertToDouble();

        double roofHeight = Math.tan(angleRadians) * baseWidth;
        ValeurImperiale heightValeurImperiale = ValeurImperiale.convertDoubleToValeurImperiale(roofHeight);


        switch (direction){
            case FRONT_TO_BACK:
                if (this.facade == Facade.DROITE) {
                    ValeurImperiale peakX = (BASE_X.add((depth).divideByInt(2).
                            add(saftG.divideByInt(2))));
                    return new Position(peakX, BASE_Y.substract(heightValeurImperiale).add(saftG.divideByInt(2)));
                } else {
                    ValeurImperiale peakX = ((BASE_X.add((depth).divideByInt(2)).add(width).
                            substract(saftG.divideByInt(2))).substract(depth));
                    return new Position(peakX, BASE_Y.substract(heightValeurImperiale).add(saftG.divideByInt(2)));
                }
            case BACK_TO_FRONT:
                if (this.facade == Facade.GAUCHE) {
                    ValeurImperiale peakX = (BASE_X.add((depth).divideByInt(2).
                            add(saftG.divideByInt(2))));
                    return new Position(peakX, BASE_Y.substract(heightValeurImperiale).add(saftG.divideByInt(2)));
                } else {
                    ValeurImperiale peakX = ((BASE_X.add((depth).divideByInt(2)).add(width).
                            substract(saftG.divideByInt(2))).substract(depth));
                    return new Position(peakX, BASE_Y.substract(heightValeurImperiale).add(saftG.divideByInt(2)));
                }
            case RIGHT_TO_LEFT:
                if (this.facade == Facade.AVANT) {
                    ValeurImperiale peakX = (BASE_X.add((depth).divideByInt(2).
                            add(saftG.divideByInt(2))));
                    return new Position(peakX, BASE_Y.substract(heightValeurImperiale).add(saftG.divideByInt(2)));
                } else {
                    ValeurImperiale peakX = ((BASE_X.add((depth).divideByInt(2)).add(width).
                            substract(saftG.divideByInt(2))).substract(depth));
                    return new Position(peakX, BASE_Y.substract(heightValeurImperiale).add(saftG.divideByInt(2)));
                }
            case LEFT_TO_RIGHT:
                if (this.facade == Facade.ARRIERE) {
                    ValeurImperiale peakX = (BASE_X.add((depth).divideByInt(2).
                            add(saftG.divideByInt(2))));
                    return new Position(peakX, BASE_Y.substract(heightValeurImperiale).add(saftG.divideByInt(2)));
                } else {
                    ValeurImperiale peakX = ((BASE_X.add((depth).divideByInt(2)).add(width).
                            substract(saftG.divideByInt(2))).substract(depth));
                    return new Position(peakX, BASE_Y.substract(heightValeurImperiale).add(saftG.divideByInt(2)));
                }
        }
        return null;
    }
}
