package ca.ulaval.glo2004.domain.export;
import ca.ulaval.glo2004.domain.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//  retrait;: 4 rainure l, ' rainure normal et accs hard coded

public class ExportRetrait extends ExportSTL {
    private int Y = 0;
    public ExportRetrait(Shed chalet) {
        super(chalet);
    }

    public void export(String path) {
        exportMur(path);
        exportPignon(path);
        exportRallongeToit(path);
        exportDessusToit(path);
    }


    protected void exportMur(String path){
        // really more like exportAccessoires
        for (Map.Entry<Facade, Wall> wall : chalet.getWallList().entrySet()) {
            String facadePath;
            if (wall.getValue().facade.equals(Facade.AVANT)) {
                facadePath = "F";
            } else if (wall.getValue().facade.equals(Facade.ARRIERE)){
                facadePath = "A";
            } else if (wall.getValue().facade.equals(Facade.GAUCHE)) {
                facadePath = "G";
            } else {
                facadePath = "D";
            }
            Y+=1;

            // exporter les rainures
            exportRainures(path, facadePath, wall);
            // exporter les accessoires
            exportAccessories(path, facadePath, wall);

            // add the triangles

        }

        Facade facadeDroite = Facade.ARRIERE;
        if(chalet.getDirection().equals(ToitDirection.BACK_TO_FRONT) || chalet.getDirection().equals(ToitDirection.FRONT_TO_BACK)){
            facadeDroite = Facade.DROITE;
        }
        Roof leftRoof = chalet.getRoofList().get(facadeDroite);
        ArrayList<ArrayList<Position>> leftRoofPoints = leftRoof.getViewPoints().get("roof");

        ToitDirection orientation = chalet.getDirection();
        for (ArrayList<Position> roofView : leftRoofPoints) {
            System.out.println("list of points: ");
            System.out.println(facadeDroite);
            System.out.println(orientation);
            for (Position pos : roofView) {
                double x = pos.getX().convertToDouble();
                double y = pos.getY().convertToDouble();
                System.out.println("("+ x +" ; "+y+")");
            }
        }

        List<Triangle> trianglesRallonge = exportRainureRallonge(leftRoofPoints.get(1));
    }
    private List<Triangle> exportRainureRallonge(ArrayList<Position> positions){

        List<Triangle> rallongeRainuTrg = new ArrayList<>();
        int index = 0;
        Vertex[] vertices = new Vertex[4];
        for (Position pos : positions){
            double x = pos.getX().convertToDouble();
            double y = 0;
            double z = pos.getY().convertToDouble();
            System.out.println("("+ x +" ; "+y+";"+z+")");
            vertices[index] = new Vertex(x, y, z);
            index++;
        }
        return rallongeRainuTrg;
    }
    private void exportRainures(String path, String facadePath, Map.Entry<Facade, Wall> wall) {
        List<Triangle> triangles = new ArrayList<>();

        Facade facade = wall.getKey();

        // get the rainures out there
        ArrayList<ArrayList<Position>> front = wall.getValue().getViewPoints().get("front");
        ArrayList<Position> positions;
        double wallLargeur = wall.getValue().getWidth().convertToDouble();
        double wallDepth = wall.getValue().getDepth().convertToDouble();
        double wallHeight = wall.getValue().getHeight().convertToDouble();
        double chaletLongueur = chalet.getLongueur().convertToDouble();
        double chaletLargeur = chalet.getLargeur().convertToDouble();

        Vertex[] frontLeftVertices;
        Vertex[] backLeftVertices;
        Vertex[] frontRightVertices;
        Vertex[] backRightVertices;
        if (facade.equals(Facade.AVANT)) {

            frontLeftVertices = new Vertex[]{
                    new Vertex(wallLargeur - wallDepth, 0, wallHeight),
                    new Vertex(wallLargeur - wallDepth, chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble(), wallHeight),
                    new Vertex(wallLargeur - wallDepth, chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard()).convertToDouble(), 0),
                    new Vertex(wallLargeur - wallDepth, 0, 0)
            };
            backLeftVertices = new Vertex[]{
                    new Vertex(wallLargeur - wallDepth + (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), 0, wallHeight),
                    new Vertex(wallLargeur - wallDepth + (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble(), wallHeight),
                    new Vertex(wallLargeur - wallDepth + (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble(), 0),
                    new Vertex(wallLargeur - wallDepth + (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), 0, 0)
            };
            frontRightVertices = new Vertex[]{
                    new Vertex(wallLargeur - wallDepth, chaletLongueur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble()), wallHeight),
                    new Vertex(wallLargeur - wallDepth, chaletLongueur, wallHeight),
                    new Vertex(wallLargeur - wallDepth, chaletLongueur, 0),
                    new Vertex(wallLargeur - wallDepth, chaletLongueur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble()), 0)
            };
            backRightVertices = new Vertex[]{
                    new Vertex(wallLargeur - wallDepth + (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble()), wallHeight),
                    new Vertex(wallLargeur - wallDepth + (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur, wallHeight),
                    new Vertex(wallLargeur - wallDepth + (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur, 0),
                    new Vertex(wallLargeur - wallDepth + (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble()), 0)
            };

        } else if (facade.equals(Facade.ARRIERE)){

            frontLeftVertices = new Vertex[]{
                    new Vertex(wallDepth, 0, wallHeight),
                    new Vertex(wallDepth, chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble(), wallHeight),
                    new Vertex(wallDepth, chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble(), 0),
                    new Vertex(wallDepth, 0, 0)
            };
            backLeftVertices = new Vertex[]{
                    new Vertex(wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), 0, wallHeight),
                    new Vertex(wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble(), wallHeight),
                    new Vertex(wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble(), 0),
                    new Vertex(wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), 0, 0)
            };
            frontRightVertices = new Vertex[]{
                    new Vertex(wallDepth, chaletLongueur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble()), wallHeight),
                    new Vertex(wallDepth, chaletLongueur, wallHeight),
                    new Vertex(wallDepth, chaletLongueur, 0),
                    new Vertex(wallDepth, chaletLongueur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble()), 0)
            };
            backRightVertices = new Vertex[]{
                    new Vertex(wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble()), wallHeight),
                    new Vertex(wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur, wallHeight),
                    new Vertex(wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur, 0),
                    new Vertex(wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2)).convertToDouble()), 0)
            };
        }

        else if (facade.equals(Facade.GAUCHE)){
            frontLeftVertices = new Vertex[]{
                    new Vertex(chaletLargeur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth, wallHeight),
                    new Vertex(chaletLargeur - (chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth, wallHeight),
                    new Vertex(chaletLargeur - (chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth, 0),
                    new Vertex(chaletLargeur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth, 0)
            };
            backLeftVertices = new Vertex[]{
                    new Vertex(chaletLargeur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallHeight),
                    new Vertex(chaletLargeur - (chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallHeight),
                    new Vertex(chaletLargeur - (chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), 0),
                    new Vertex(chaletLargeur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), 0)
            };
            frontRightVertices = new Vertex[]{
                    new Vertex((chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth, wallHeight),
                    new Vertex((chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth, wallHeight),
                    new Vertex((chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth, 0),
                    new Vertex((chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth, 0)
            };
            backRightVertices = new Vertex[]{
                    new Vertex((chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallHeight),
                    new Vertex((chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallHeight),
                    new Vertex((chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), 0),
                    new Vertex((chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallDepth - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), 0)
            };

        } else {
            frontLeftVertices = new Vertex[]{
                    new Vertex(chaletLargeur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - wallDepth, wallHeight),
                    new Vertex(chaletLargeur - (chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - wallDepth, wallHeight),
                    new Vertex(chaletLargeur - (chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - wallDepth, 0),
                    new Vertex(chaletLargeur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - wallDepth, 0)
            };
            backLeftVertices = new Vertex[]{
                    new Vertex(chaletLargeur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - (chalet.getDepth().divideByInt(2).substract(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallHeight),
                    new Vertex(chaletLargeur - (chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - (chalet.getDepth().divideByInt(2).substract(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallHeight),
                    new Vertex(chaletLargeur - (chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - (chalet.getDepth().divideByInt(2).substract(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), 0),
                    new Vertex(chaletLargeur - (chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - (chalet.getDepth().divideByInt(2).substract(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), 0)
            };
            frontRightVertices = new Vertex[]{
                    new Vertex((chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - wallDepth, wallHeight),
                    new Vertex((chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - wallDepth, wallHeight),
                    new Vertex((chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - wallDepth, 0),
                    new Vertex((chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - wallDepth, 0)
            };
            backRightVertices = new Vertex[]{
                    new Vertex((chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - (chalet.getDepth().divideByInt(2).substract(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallHeight),
                    new Vertex((chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - (chalet.getDepth().divideByInt(2).substract(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), wallHeight),
                    new Vertex((chalet.getDepth().divideByInt(2).add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - (chalet.getDepth().divideByInt(2).substract(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), 0),
                    new Vertex((chalet.getDepth().add(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), chaletLongueur - (chalet.getDepth().divideByInt(2).substract(chalet.getSafetyGuard().divideByInt(2))).convertToDouble(), 0)
            };
        }
        List<Triangle> leftTriangles = writeRectangleToTriangle(frontLeftVertices, backLeftVertices);
        List<Triangle> rightTriangles = writeRectangleToTriangle(frontRightVertices, backRightVertices);
        Triangle[] leftSideTriangles = createSideTriangles(frontLeftVertices, backLeftVertices);
        Triangle[] rightSideTriangles = createSideTriangles(frontRightVertices, backRightVertices);
        leftTriangles.addAll(Arrays.asList(leftSideTriangles));
        rightTriangles.addAll(Arrays.asList(rightSideTriangles));

        try {
            String fileName = path + facadePath + "_" + Y + ".stl";
            STLWriter.writeSTLFile(fileName, leftTriangles);
            System.out.println("STL file created successfully at : " + path);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Y++;

        try {
            String fileName = path + facadePath + "_" + Y + ".stl";
            STLWriter.writeSTLFile(fileName, rightTriangles);
            System.out.println("STL file created successfully at : " + path);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<Triangle> exportRainurePignon(){
        List<Triangle> pignonRainuTrg = new ArrayList<>();
        Vertex[] vertices = new Vertex[4];

        return pignonRainuTrg;

    }
    private List<Triangle> exportRainureCover(){
        List<Triangle> coverRainuTrg = new ArrayList<>();
        return coverRainuTrg;
    }


    private void exportAccessories(String path, String facadePath, Map.Entry<Facade, Wall> wall){
        List<Triangle> triangles = new ArrayList<>();
        for (Accessory accessory : wall.getValue().getAccessories()) {
            Y++;
            triangles.addAll(convertAccessoryToSTL(accessory, wall.getKey()));
            System.out.println(wall.getValue().facade.toString());

            try {
                String fileName = path + facadePath + "_" + Y + ".stl";
                STLWriter.writeSTLFile(fileName, triangles);
                System.out.println("STL file created successfully at : " + path);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    protected void exportRallongeToit(String path){

    }
    protected void exportPignon(String path){

    }
    protected void exportDessusToit(String path){

    }
    //private static List<Triangle> allTriangles = new ArrayList<>();
    //private static List<Triangle> allTriangles_back = new ArrayList<>();


    private static void writeTriangleToSTL(Triangle triangle, StringBuilder stlContent){
        stlContent.append("  facet normal 0 0 1\n");
        stlContent.append("    outer loop\n");
        for (Vertex vertex : triangle.getVertices()) {
            stlContent.append("      vertex ").append(vertex.getX())
                    .append(" ").append(vertex.getY())
                    .append(" ").append(vertex.getZ()).append("\n");
        }
        stlContent.append("    endloop\n").append("  endfacet\n");
    }


    public List<Triangle> convertAccessoryToSTL(Accessory accessory, Facade facade){
           double accessoryLargeur = accessory.getWidth().convertToDouble();
           double wallDepth = chalet.getDepth().convertToDouble();
           double wallLongeur = chalet.getLongueur().convertToDouble();
           double wallLargeur = chalet.getLargeur().convertToDouble();
           double wallHauteur = chalet.getHauteur().convertToDouble();

           ArrayList<Position> positions = accessory.getPositionList();

           Vertex[] vertices = generateVertices(positions, facade, wallLongeur, wallLargeur, wallHauteur, wallDepth);
           Vertex[] vertices_back = generateVerticesBack(positions, facade, wallLongeur, wallLargeur, wallHauteur, wallDepth);
           List<Triangle> allTriangles = writeRectangleToTriangle(vertices, vertices_back);
           Triangle[] sideTriangles =createSideTriangles(vertices, vertices_back);
           allTriangles.addAll(Arrays.asList(sideTriangles));
           return allTriangles;
    }
    protected Vertex[] generateVertices(ArrayList<Position> positions, Facade facade, double wallLongueur, double wallLargeur, double wallHauteur, double wallDepth){
        //ArrayList<Vertex[]> all_vertices= new ArrayList<Vertex[]>();
        Vertex[] vertices = new Vertex[positions.size()];
        int index = 0;
        for (Position pos : positions){
            double x = 0;
            double y = pos.getX().convertToDouble();
            double z = wallHauteur - pos.getY().convertToDouble();
            // plan x vers moi, y vers droite, z vers haut
            // Avant et Arriere sur le plan (y, z)
            // Gauche et Droite sur le plan (x, z)
            if(facade.equals(Facade.AVANT) ){
                x = wallLargeur;
            }
            if (facade.equals(Facade.GAUCHE) || facade.equals(Facade.DROITE)){
                x = y;
                y = 0;
            }
            if (facade.equals(Facade.DROITE)){

                y = wallLongueur - wallDepth;

            }
            vertices[index] = new Vertex(x, y, z);
            index ++;
        }
        return vertices;
    }
    protected Vertex[] generateVerticesBack(ArrayList<Position> positions, Facade facade, double wallLongueur, double wallLargeur, double wallHauteur, double wallDepth){

        Vertex[] vertices_back = new Vertex[positions.size()];
        int index = 0;
        for (Position pos : positions){

            double x = wallDepth;
            double y = pos.getX().convertToDouble();
            double z = wallHauteur - pos.getY().convertToDouble();
            // plan x vers moi, y vers droite, z vers haut
            // Avant et Arriere sur le plan (y, z)
            // Gauche et Droite sur le plan (x, z)
            if(facade.name().equals("AVANT") ){
                x = wallLargeur - wallDepth;
            }
            if (facade.name().equals("GAUCHE") || facade.name().equals("DROITE")){
                x = y;
                y = wallDepth;
            }
            if (facade.name().equals("DROITE")){

                y = wallLongueur;
            }
            vertices_back[index] = new Vertex(x, y, z);
            index++;
        }
        return vertices_back;
    }

    public List<Triangle> convertRainuresToSTL(){
        List<Triangle> allTriangles =  new ArrayList<Triangle>();
        return allTriangles;
    }

}
