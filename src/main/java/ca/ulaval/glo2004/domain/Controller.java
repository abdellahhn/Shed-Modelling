/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain;

import ca.ulaval.glo2004.domain.export.ExportBrut;
import ca.ulaval.glo2004.domain.export.ExportFini;
import ca.ulaval.glo2004.domain.export.ExportRetrait;
import ca.ulaval.glo2004.domain.export.ExportSTL;
import ca.ulaval.glo2004.gui.DrawingPanel;
import ca.ulaval.glo2004.gui.MainWindow;

import java.awt.Point;
import java.awt.*;
import java.util.*;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;


public class Controller implements Serializable{
    private DrawingPanel drawingPanel;
    private MainWindow mainWindow;
    private String accessoirString;
    public Position pos = new Position();
    public Dimension initialDimension;
    public ToitDirection direction;
    public Object murSelectionne;
    private final int[] translation;
    private int vueSelectionne;
    private Shed chalet;
    private double offsetX;
    private double offsetY;
    public String msgEr;
    private Accessory foundAccessory;
    private AcessoryDTO foundAccessoryDTO;
    private double echelle;
    private Point2D mousePos;
    private Facade currentFacade;
    private ExportSTL exportSTL;

    //imane's add:
    private Stack<Shed> shedHistory = new Stack<>();
    private Stack<Shed> shedHistoryAnl = new Stack<>();
    // end add


    public final static Map<String, Facade> mapping = new HashMap<String, Facade>() {{
        put("AVANT", Facade.AVANT);
        put("ARRIERE", Facade.ARRIERE);
        put("GAUCHE", Facade.GAUCHE);
        put("DROITE", Facade.DROITE);
    }};


    public Controller() {


        translation = new int[]{0, 100};

        murSelectionne = null;

        ValeurImperiale length = new ValeurImperiale(10, 0, 0);
        ValeurImperiale width = new ValeurImperiale(10, 0, 0);
        ValeurImperiale height = new ValeurImperiale(8, 0, 0);
        ValeurImperiale depth = new ValeurImperiale(1, 0, 0);
        ValeurImperiale safetyGuard = new ValeurImperiale(0, 0, 0);
        chalet = new Shed(length, width, height, depth, safetyGuard);
        Save_state();
    }

    public void modifyShedSize(ValeurImperiale length, ValeurImperiale width, ValeurImperiale height, ValeurImperiale depth) {
        chalet.modifySize(length, width, height, depth);

        Save_state();
    }

    public void changeSafetyGuard(ValeurImperiale newSafetyGuard) {
        chalet.setSafetyGuard(newSafetyGuard);
        Save_state();
    }

    public void changeMinimumDistanceBetweenAccessories(ValeurImperiale newMinimumDistance) {
        for (Map.Entry<Facade, Wall> entry: chalet.getWallList().entrySet()){
            Wall wall = entry.getValue();
            wall.setMinimumAccessoryDistance(newMinimumDistance);
        }
        //maybe i should add something here to fix the issue?
    }

    public void addWindow(Position position, ValeurImperiale width, ValeurImperiale height, String currentFacade) {
        setCurrentWall(currentFacade);
        Wall associatedWall = getAssociatedWall();
        Window window = new Window(width, height, position, associatedWall);
        associatedWall.addAccessory(window);
        //here add an if statement if window is valide when they add an isvalide operator

        Save_state();
    }


    public void addDoor(ValeurImperiale x, ValeurImperiale width, ValeurImperiale height, String currentFacade) {
        setCurrentWall(currentFacade);
        Wall associatedWall = getAssociatedWall();
        Door door = new Door(width, height, x, associatedWall);
        associatedWall.addAccessory(door);
        Save_state();
    }

    // dimensions standard d’une porte sont de 38’’ de large par 88’’ de haut
    public void addDoor(ValeurImperiale x, String currentFacade) {
        setCurrentWall(currentFacade);
        Wall associatedWall = getAssociatedWall();
        Door door = new Door(x, associatedWall);
        associatedWall.addAccessory(door);
        Save_state();
    }

    public void changeWindowSize(Position currentPosition, ValeurImperiale newWidth, ValeurImperiale newHeight, String currentFacade) {
        setCurrentWall(currentFacade);
        if (findAccessory(currentPosition)) {
            Wall associatedWall = getAssociatedWall();
            Accessory foundAccessory = associatedWall.findAccessory(currentPosition);
            associatedWall.changeAccessorySize(foundAccessory, newWidth, newHeight);
            Save_state();
        } else {
            this.msgEr = "No accessory found at the given position";
            System.out.println("No accessory found at the given position");
        }
    }

    public void changeDoorSize(Position currentPosition, ValeurImperiale newWidth, ValeurImperiale newHeight, String currentFacade) {
        setCurrentWall(currentFacade);
        if (findAccessory(currentPosition)) {
            Wall associatedWall = getAssociatedWall();
            Accessory foundAccessory = associatedWall.findAccessory(currentPosition);
            associatedWall.changeAccessorySize(foundAccessory, newWidth, newHeight);
            Save_state();
        } else {
            this.msgEr = "No accessory found at the given position";
            System.out.println("No accessory found at the given position");
        }


    }

    public void moveWindow(Position currentPosition, Position newPosition, String currentFacade) {
        setCurrentWall(currentFacade);
        if (findAccessory(currentPosition)) {
            Wall associatedWall = getAssociatedWall();
            Accessory foundAccessory = associatedWall.findAccessory(currentPosition);
            associatedWall.moveAccessory(foundAccessory, newPosition);
            Save_state();
        } else {
            this.msgEr = "No accessory found at the given position";
            System.out.println("No accessory found at the given position");
        }
    }

    public void moveDoor(Position currentPosition, Position newPosition, String currentFacade) {
        setCurrentWall(currentFacade);
        if (findAccessory(currentPosition)) {
            Wall associatedWall = getAssociatedWall();
            Accessory foundAccessory = associatedWall.findAccessory(currentPosition);
            newPosition.setY(associatedWall.getHeight().substract(foundAccessory.height));
            associatedWall.moveAccessory(foundAccessory, newPosition);
            Save_state();
        } else {
            this.msgEr = "No accessory found at the given position";
            System.out.println("No accessory found at the given position");
        }

    }
    
    public boolean findAccessory(Position position) {
        Wall associatedWall = getAssociatedWall();
        return associatedWall.findAccessory(position) != null;
    }

    public void removeAccessory(Position position, String currentFacade) {
        setCurrentWall(currentFacade);
        if (findAccessory(position)) {
            Wall associatedWall = getAssociatedWall();
            Accessory foundAccessory = associatedWall.findAccessory(position);
            associatedWall.removeAccessory(foundAccessory);
            Save_state();
        } else {
            this.msgEr = "No accessory found at the given position";
            System.out.println("No accessory found at the given position");
        }
    }
    
    public boolean isAccessoryFound() {
        return this.foundAccessory != null;
    }
    
    public String getFoundAccessoryType() {
        return accessoirString; // Already set in setAccessory method
    }

    public boolean setAccessoryy(Position position, String currFacade) {
        setCurrentWall(currFacade);
        Wall associatedWall = getAssociatedWall();
        Accessory accessory = associatedWall.findAccessory(position);

        if (accessory != null) {
            this.foundAccessory = accessory;
            this.accessoirString = toString(accessory);
            associatedWall.selectedAccessory = accessory;
            System.out.println(accessory);
            return true;
        }
        return false;
    }

    public Position getSelectedAccessoryPosition() {
        if (this.foundAccessory != null) {
            return this.foundAccessory.getPosition();
        }
        return null;
    }

    private String toString(Accessory accessory) {
        if (accessory instanceof Door) {
            return "Door";
        } else if (accessory instanceof Window) {
            return "Window";
        }
        return "Unknown";
    }

    public void setCurrentWall(String currentFacade) {
        Facade current = convertCurrentFacade(currentFacade);
        setCurrentFacade(current);
    }

    private Wall getAssociatedWall() {
        return chalet.getWallList().get(this.currentFacade);
    }

    private ValeurImperiale valeurImperiale(int i) {
        return new ValeurImperiale(i);
    }

    public Point conversionPositionVersPixel(Position point)

    // 1. On prend la PositionImperiale qui est un point x,y
    // 2. On prend cette position et on la converti en Pixel avec la fonction .imperialeToPixel()
    // 3. On soustrait la translation présente dans le constructeur de la salle, qui est un delta en X et en Y
    // 4. On multiplie pas notre echelleé La règle est la suivante : (Point - translation) * échelle) on doit le faire en X et en Y

    {
        int x = (int) Math.ceil((point.getX().imperialeToPixel() - translation[0]) * getEchelle());
        int y = (int) Math.ceil((point.getY().imperialeToPixel() - translation[1]) * getEchelle());
        return new Point(x, y);
    }

    /**
     * This is a method that exports the shed in a STL format at a given path
     *
     * @param exportType The type of export the user wants (brut, fini, retrait).
     * @param filePath   The selected path.
     */
    public void export(String exportType, String filePath) { // we take in as parameter the type of export we want to do (brut, fini, retrait)
        exportSTL = exportTypeMapper(exportType, chalet);
        exportSTL.export(filePath);
    }

    public double getEchelle() {
        return echelle;
    }

    public ArrayList<Polygone> infoPourAfficherLaVue(int vue) {
        ArrayList<Polygone> polygones = new ArrayList<>();
        Polygone polygon;
        switch (vue) {
            case 0://vue de dessus
                for (Facade facade : chalet.getWallList().keySet()) {
                    Wall wall = chalet.getWallList().get(facade);
                    ArrayList<ArrayList<Position>> topPoints = wall.getViewPoints().get("top");
                    polygones.add(new Polygone(topPoints.get(0), chalet.couleurMateriel));
                }
                break;
            case 1://vue avant externe
                if (chalet.direction.equals(ToitDirection.BACK_TO_FRONT) || chalet.direction.equals(ToitDirection.FRONT_TO_BACK)) {
                    polygon = createPolygonFromView(Facade.AVANT, "front");
                    polygones.add(polygon);

                } else {
                    Polygone rectAv1 = new Polygone(chalet.getWallList().get(Facade.AVANT).getViewPoints().get("front").get(0), chalet.couleurMateriel);
                    Polygone rectAv2 = new Polygone(chalet.getWallList().get(Facade.AVANT).getViewPoints().get("front").get(1), chalet.couleurMateriel);
                    Polygone rectAv3 = new Polygone(chalet.getWallList().get(Facade.AVANT).getViewPoints().get("front").get(2), chalet.couleurMateriel);
                    polygones.add(rectAv1);
                    polygones.add(rectAv2);
                    polygones.add(rectAv3);
                }

                if (chalet.getRoofList().containsKey(Facade.AVANT)) {
                    Roof frontRoof = chalet.getRoofList().get(Facade.AVANT);
                    ArrayList<ArrayList<Position>> frontRoofPoints = frontRoof.getViewPoints().get("roof");
                    if (frontRoofPoints != null && !frontRoofPoints.isEmpty()) {
                        for (ArrayList<Position> roofView : frontRoofPoints) {
                            polygones.add(new Polygone(roofView, chalet.couleurToit));
                        }
                    }
                }

                // accessories
                polygones.addAll(createPolygonsForAccessories(Facade.AVANT));
                break;
            case 2://vue arriere
                if (chalet.direction.equals(ToitDirection.BACK_TO_FRONT) || chalet.direction.equals(ToitDirection.FRONT_TO_BACK)) {
                    polygon = createPolygonFromView(Facade.ARRIERE, "front");
                    polygones.add(polygon);

                } else {
                    Polygone rectAr1 = new Polygone(chalet.getWallList().get(Facade.ARRIERE).getViewPoints().get("front").get(0), chalet.couleurMateriel);
                    Polygone rectAr2 = new Polygone(chalet.getWallList().get(Facade.ARRIERE).getViewPoints().get("front").get(1), chalet.couleurMateriel);
                    Polygone rectAr3 = new Polygone(chalet.getWallList().get(Facade.ARRIERE).getViewPoints().get("front").get(2), chalet.couleurMateriel);
                    polygones.add(rectAr1);
                    polygones.add(rectAr2);
                    polygones.add(rectAr3);
                }

                if (chalet.getRoofList().containsKey(Facade.ARRIERE)) {
                    Roof rearRoof = chalet.getRoofList().get(Facade.ARRIERE);
                    ArrayList<ArrayList<Position>> rearRoofPoints = rearRoof.getViewPoints().get("roof");
                    if (rearRoofPoints != null && !rearRoofPoints.isEmpty()) {
                        for (ArrayList<Position> roofView : rearRoofPoints) {
                            polygones.add(new Polygone(roofView, chalet.couleurToit));
                        }
                    }
                }

                // accessories
                polygones.addAll(createPolygonsForAccessories(Facade.ARRIERE));
                break;
            case 3://vue gauche
                // walls
                Polygone rectL1;
                Polygone rectL2;
                Polygone rectL3;
                if (chalet.direction.equals(ToitDirection.FRONT_TO_BACK) || chalet.direction.equals(ToitDirection.BACK_TO_FRONT)) {
                    rectL1 = new Polygone(chalet.getWallList().get(Facade.GAUCHE).getViewPoints().get("front").get(0), chalet.couleurMateriel);
                    rectL2 = new Polygone(chalet.getWallList().get(Facade.GAUCHE).getViewPoints().get("front").get(1), chalet.couleurMateriel);
                    rectL3 = new Polygone(chalet.getWallList().get(Facade.GAUCHE).getViewPoints().get("front").get(2), chalet.couleurMateriel);
                    polygones.add(rectL1);
                    polygones.add(rectL2);
                    polygones.add(rectL3);
                } else {
                    polygon = createPolygonFromView(Facade.GAUCHE, "front");
                    polygones.add(polygon);
                }

                if (chalet.getRoofList().containsKey(Facade.GAUCHE)) {
                    Roof leftRoof = chalet.getRoofList().get(Facade.GAUCHE);
                    ArrayList<ArrayList<Position>> leftRoofPoints = leftRoof.getViewPoints().get("roof");
                    if (leftRoofPoints != null && !leftRoofPoints.isEmpty()) {
                        for (ArrayList<Position> roofView : leftRoofPoints) {
                            polygones.add(new Polygone(roofView, chalet.couleurPignon));
                        }
                    }
                }

                // accessories
                polygones.addAll(createPolygonsForAccessories(Facade.GAUCHE));
                break;
            case 4://vue droite
                Polygone rectD1;
                Polygone rectD2;
                Polygone rectD3;
                if (chalet.direction.equals(ToitDirection.FRONT_TO_BACK) || chalet.direction.equals(ToitDirection.BACK_TO_FRONT)) {
                    rectD1 = new Polygone(chalet.getWallList().get(Facade.DROITE).getViewPoints().get("front").get(0), chalet.couleurMateriel);
                    rectD2 = new Polygone(chalet.getWallList().get(Facade.DROITE).getViewPoints().get("front").get(1), chalet.couleurMateriel);
                    rectD3 = new Polygone(chalet.getWallList().get(Facade.DROITE).getViewPoints().get("front").get(2), chalet.couleurMateriel);
                    polygones.add(rectD1);
                    polygones.add(rectD2);
                    polygones.add(rectD3);
                } else {
                    polygon = createPolygonFromView(Facade.DROITE, "front");
                    polygones.add(polygon);
                }

                if (chalet.getRoofList().containsKey(Facade.DROITE)) {
                    Roof rightRoof = chalet.getRoofList().get(Facade.DROITE);
                    ArrayList<ArrayList<Position>> rightRoofPoints = rightRoof.getViewPoints().get("roof");
                    if (rightRoofPoints != null && !rightRoofPoints.isEmpty()) {
                        for (ArrayList<Position> roofView : rightRoofPoints) {
                            polygones.add(new Polygone(roofView, chalet.couleurPignon));
                        }
                    }
                }

                // accessories
                polygones.addAll(createPolygonsForAccessories(Facade.DROITE));
                break;
        }
        return polygones;
    }

    private List<Polygone> createPolygonsForAccessories(Facade facade) {
        List<Accessory> wallAccessories = chalet.getWallList().get(facade).getAccessories();
        List<Polygone> accessoryPolygons = new ArrayList<>();

        for (Accessory accessory : wallAccessories) {
            Polygone acc = new Polygone(accessory.positionList, accessory.color);
            accessoryPolygons.add(acc);
        }
        return accessoryPolygons;
    }

    private Polygone createPolygonFromView(Facade facade, String key) {
        Map<String, ArrayList<ArrayList<Position>>> viewPoints = chalet.getWallList().get(facade).getViewPoints();
        return new Polygone(viewPoints.get(key).get(0), chalet.couleurMateriel);
    }

    public Controller getController() {
        return this;
    }

    private Facade getCurrentFacade() {
        return this.currentFacade;
    }


    private void setCurrentFacade(Facade newCurrentFacade) {
        this.currentFacade = newCurrentFacade;
    }

    private static Facade convertCurrentFacade(String current) {
        return mapping.get(current);
    }

    public ValeurImperiale getChaletLongeur() {
        return this.chalet.getLongueur();
    }

    public ValeurImperiale getChaletLargeur() {
        return this.chalet.getLargeur();
    }

    public ValeurImperiale getChaletHauteur() {
        return this.chalet.getHauteur();
    }

    public ValeurImperiale getChaletEpaisseur() {
        return this.chalet.getDepth();
    }

    public ToitDirection getToitDirection(){
        return mainWindow.getDirection();
    }

    public void setDirectionToit(ToitDirection other){
        this.direction = other;
    }

    public void changeDirection(ToitDirection nvx){
        chalet.resetRoofs(nvx);
        chalet.rotateWalls(nvx); // Ensure this reflects the new direction
        this.Save_state();
    }

    public ValeurImperiale resecale(double pos, double fact, double offSet) {
        return this.pos.updatePosition(pos, fact, offSet);
    }
    

    public String getFacadeWallMouse(Position position, String facadeStr) {
        Facade facadeEnum = Facade.fromString(facadeStr);
        return chalet.getFacadeAsString(position, facadeEnum);
    }

    public String getHeightWallMouse(Position position, String facadeStr){
        Facade facadeEnum = Facade.fromString(facadeStr);
        return chalet.getHeightAsString(position, facadeEnum);
    }

    public String getWidthWallMouse(Position position, String facadeStr){
        Facade facadeEnum = Facade.fromString(facadeStr);
        return chalet.getWidthAsString(position, facadeEnum);
    }

    public String getDepthWallMouse(Position position, String facadeStr){
        Facade facadeEnum = Facade.fromString(facadeStr);
        return chalet.getDepthAsString(position, facadeEnum);
    }

    private ExportSTL exportTypeMapper(String exportType, Shed chalet) {
        if (exportType.equals("Brut")) {
            return new ExportBrut(chalet);
        } else if (exportType.equals("Fini")) {
            return new ExportFini(chalet);
        } else {
            return new ExportRetrait(chalet);
        }
    }

    public void Save_state() {
        Shed shedCopy = new Shed(this.chalet);
        this.shedHistory.push(shedCopy);
        this.shedHistoryAnl.clear(); // Clear redo history after a new action
    }

    public void Undo() {
        if (this.shedHistory.size() > 1) {
            Shed currentShed = this.shedHistory.pop();
            this.shedHistoryAnl.push(currentShed);
            this.chalet = new Shed(this.shedHistory.peek()); // Deep copy for a new current state
        }
    }

    public void Redo() {
        if (!this.shedHistoryAnl.isEmpty()) {
            Shed redoShed = this.shedHistoryAnl.pop();
            this.shedHistory.push(new Shed(this.chalet)); // Save current state before redo
            this.chalet = redoShed;
        }
    }


    public void changeAngleToit(double nvlAngle){
        chalet.setAngleToit(nvlAngle);
        this.Save_state();
    }

    public AcessoryDTO convertToAccessoryDTO(Accessory accessory) {
        if (accessory == null) {
            return null;
        }

        AcessoryDTO dto = new AcessoryDTO(accessory);


        return dto;
    }

    public void saveState(String path) throws FileNotFoundException, IOException{
        FileOutputStream fileO = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fileO);
        out.writeObject(this.chalet);
        out.close();
        fileO.close();
        System.out.println("Fichier Sauvgardé");
    }

    public void loadState(String path) throws FileNotFoundException, IOException, ClassNotFoundException{
        Shed loadedShed = null;
        FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        loadedShed = (Shed) in.readObject();
        this.chalet = loadedShed;
        in.close();
        fileIn.close();
        System.out.println("Le fichier a été chargé");
    }


}           